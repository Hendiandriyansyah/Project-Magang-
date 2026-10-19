package data;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import model.Karyawan;
import model.Penilaian;
import service.PerhitunganNilai;

/** Penyimpanan lokal XML, aman dari nama file path relatif / beda komputer. */
public final class DataStore {
    private final Path file;
    private final LinkedHashMap<String, Karyawan> karyawan = new LinkedHashMap<String, Karyawan>();
    private final LinkedHashMap<String, Penilaian> penilaian = new LinkedHashMap<String, Penilaian>();

    public static final class Ranking {
        public final Karyawan karyawan;
        public final Penilaian penilaian;
        public final double skor;
        public Ranking(Karyawan k, Penilaian p) {
            karyawan = k; penilaian = p; skor = PerhitunganNilai.hitung(p);
        }
    }

    public DataStore(Path path) throws IOException {
        file = path;
        load();
    }

    public static Path defaultPath() {
        String home = System.getProperty("user.home", ".");
        return Paths.get(home, ".artha-utama-plasindo", "data.xml");
    }

    public synchronized List<Karyawan> employees() {
        return new ArrayList<Karyawan>(karyawan.values());
    }

    public synchronized List<Penilaian> scores() {
        return new ArrayList<Penilaian>(penilaian.values());
    }

    public synchronized Karyawan employee(String id) { return karyawan.get(id); }

    public synchronized Penilaian score(String id, int year) {
        return penilaian.get(scoreKey(id, year));
    }

    public synchronized void putEmployee(Karyawan k, String previousId) throws IOException {
        String old = previousId == null ? "" : previousId.trim();
        if (!old.isEmpty() && !karyawan.containsKey(old)) throw new IllegalArgumentException("Data karyawan lama tidak ditemukan.");
        if ((!old.equals(k.id)) && karyawan.containsKey(k.id)) throw new IllegalArgumentException("ID Karyawan sudah digunakan.");
        LinkedHashMap<String,Karyawan> backupK = new LinkedHashMap<String,Karyawan>(karyawan);
        LinkedHashMap<String,Penilaian> backupP = new LinkedHashMap<String,Penilaian>(penilaian);
        try {
            if (!old.isEmpty() && !old.equals(k.id)) {
                karyawan.remove(old);
                List<Penilaian> oldScores = new ArrayList<Penilaian>(penilaian.values());
                penilaian.clear();
                for (Penilaian p : oldScores) {
                    if (p.idKaryawan.equals(old)) {
                        p = new Penilaian(k.id, p.tahun, p.disiplin, p.kinerja, p.kerjasama, p.tanggungJawab, p.inisiatif);
                    }
                    penilaian.put(scoreKey(p.idKaryawan, p.tahun), p);
                }
            }
            karyawan.put(k.id, k);
            save();
        } catch (IOException | RuntimeException ex) {
            karyawan.clear(); karyawan.putAll(backupK);
            penilaian.clear(); penilaian.putAll(backupP);
            throw ex;
        }
    }

    public synchronized void removeEmployee(String id) throws IOException {
        LinkedHashMap<String,Karyawan> bk = new LinkedHashMap<String,Karyawan>(karyawan);
        LinkedHashMap<String,Penilaian> bp = new LinkedHashMap<String,Penilaian>(penilaian);
        try {
            karyawan.remove(id);
            Iterator<Map.Entry<String, Penilaian>> it = penilaian.entrySet().iterator();
            while (it.hasNext()) if (it.next().getValue().idKaryawan.equals(id)) it.remove();
            save();
        } catch (IOException | RuntimeException ex) {
            karyawan.clear(); karyawan.putAll(bk);
            penilaian.clear(); penilaian.putAll(bp);
            throw ex;
        }
    }

    public synchronized void putScore(Penilaian p) throws IOException {
        if (!karyawan.containsKey(p.idKaryawan)) throw new IllegalArgumentException("Karyawan tidak ditemukan.");
        String key = scoreKey(p.idKaryawan, p.tahun);
        Penilaian before = penilaian.put(key, p);
        try { save(); }
        catch (IOException | RuntimeException ex) {
            if (before == null) penilaian.remove(key); else penilaian.put(key, before);
            throw ex;
        }
    }

    public synchronized void removeScore(String id, int year) throws IOException {
        String key = scoreKey(id, year);
        Penilaian before = penilaian.remove(key);
        try { save(); }
        catch (IOException | RuntimeException ex) {
            if (before != null) penilaian.put(key, before);
            throw ex;
        }
    }

    public synchronized List<Ranking> rankings(int year) {
        List<Ranking> list = new ArrayList<Ranking>();
        for (Penilaian p : penilaian.values()) {
            Karyawan k = karyawan.get(p.idKaryawan);
            if (k != null && p.tahun == year) list.add(new Ranking(k, p));
        }
        Collections.sort(list, new Comparator<Ranking>() {
            public int compare(Ranking a, Ranking b) {
                int n = Double.compare(b.skor, a.skor);
                return n != 0 ? n : a.karyawan.nama.compareToIgnoreCase(b.karyawan.nama);
            }
        });
        return list;
    }

    public synchronized List<Integer> years() {
        SortedSet<Integer> years = new TreeSet<Integer>(Collections.reverseOrder());
        years.add(java.time.Year.now().getValue());
        for (Penilaian p : penilaian.values()) years.add(p.tahun);
        return new ArrayList<Integer>(years);
    }

    private static String scoreKey(String id, int year) { return id + "|" + year; }

    private synchronized void load() throws IOException {
        if (!Files.isRegularFile(file)) return;
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(file)) { props.loadFromXML(in); }
        int numEmp = parseCount(props, "employee.count");
        int numScore = parseCount(props, "score.count");
        for (int i=0; i<numEmp; i++) {
            String base="employee."+i+".";
            Karyawan k = new Karyawan(props.getProperty(base+"id"), props.getProperty(base+"name"), props.getProperty(base+"dept"), props.getProperty(base+"position"));
            if (karyawan.put(k.id, k)!=null) throw new IOException("ID karyawan ganda dalam berkas data.");
        }
        for (int i=0; i<numScore; i++) {
            String base="score."+i+".";
            try {
                Penilaian p = new Penilaian(props.getProperty(base+"id"), Integer.parseInt(props.getProperty(base+"year")),
                    Integer.parseInt(props.getProperty(base+"d")), Integer.parseInt(props.getProperty(base+"k")),
                    Integer.parseInt(props.getProperty(base+"s")), Integer.parseInt(props.getProperty(base+"t")),
                    Integer.parseInt(props.getProperty(base+"i")));
                if (!karyawan.containsKey(p.idKaryawan)) throw new IOException("Penilaian merujuk karyawan yang tidak ada.");
                if (penilaian.put(scoreKey(p.idKaryawan,p.tahun),p)!=null) throw new IOException("Penilaian ganda dalam berkas data.");
            } catch (IllegalArgumentException ex) { throw new IOException("Data penilaian tidak valid pada indeks "+i,ex); }
        }
    }
    private static int parseCount(Properties p, String key) throws IOException {
        try { int n = Integer.parseInt(p.getProperty(key,"0")); if(n<0 || n>100000) throw new NumberFormatException(); return n; }
        catch (NumberFormatException ex) { throw new IOException("Jumlah data tidak sah: "+key, ex); }
    }

    private synchronized void save() throws IOException {
        Properties p = new Properties();
        int i=0;
        for(Karyawan k:karyawan.values()) {
            String x="employee."+(i++)+".";
            p.setProperty(x+"id",k.id);p.setProperty(x+"name",k.nama);
            p.setProperty(x+"dept",k.bagian);p.setProperty(x+"position",k.jabatan);
        }
        p.setProperty("employee.count",String.valueOf(i)); i=0;
        for(Penilaian s:penilaian.values()) {
            String x="score."+(i++)+".";
            p.setProperty(x+"id",s.idKaryawan);p.setProperty(x+"year",String.valueOf(s.tahun));
            p.setProperty(x+"d",String.valueOf(s.disiplin));p.setProperty(x+"k",String.valueOf(s.kinerja));
            p.setProperty(x+"s",String.valueOf(s.kerjasama));p.setProperty(x+"t",String.valueOf(s.tanggungJawab));
            p.setProperty(x+"i",String.valueOf(s.inisiatif));
        }
        p.setProperty("score.count",String.valueOf(i));
        Path dir = file.toAbsolutePath().getParent(); Files.createDirectories(dir);
        Path temp = Files.createTempFile(dir,"data-",".tmp");
        try {
            try(OutputStream out = Files.newOutputStream(temp)) { p.storeToXML(out, "PT Artha Utama Plasindo - data lokal", "UTF-8"); }
            try { Files.move(temp,file,StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.ATOMIC_MOVE); }
            catch (AtomicMoveNotSupportedException ex) { Files.move(temp,file,StandardCopyOption.REPLACE_EXISTING); }
        } finally { Files.deleteIfExists(temp); }
    }
}
