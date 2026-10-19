import data.DataStore;
import model.*;
import service.*;
import java.nio.file.*;
import java.util.*;

/** Jalankan: javac -cp build/classes -d build/test/classes test/ProjectSelfTest.java;
 * java -cp build/classes:build/test/classes ProjectSelfTest */
public class ProjectSelfTest {
    static void yes(boolean b,String msg){if(!b)throw new AssertionError(msg);}
    public static void main(String[] args) throws Exception {
        Path folder=Files.createTempDirectory("artha-test-");Path data=folder.resolve("data.xml");
        DataStore s=new DataStore(data);
        yes(s.employees().isEmpty(),"start harus kosong");
        s.putEmployee(new Karyawan("K001","Budi & Santoso","Produksi","Operator"),null);
        s.putEmployee(new Karyawan("K002","Dewi Lestari","Keuangan","Staff"),null);
        s.putScore(new Penilaian("K001",2026,80,90,70,100,80));
        s.putScore(new Penilaian("K002",2026,95,95,95,95,95));
        yes(s.rankings(2026).size()==2,"ranking size");
        yes("K002".equals(s.rankings(2026).get(0).karyawan.id),"sorting skor tinggi");
        yes(Math.abs(PerhitunganNilai.hitung(s.score("K001",2026))-0.84)<0.00001,"bobot nilai");
        yes(s.rankings(2025).isEmpty(),"filter tahun");
        DataStore restarted=new DataStore(data);
        yes(restarted.employees().size()==2 && restarted.scores().size()==2,"persist restart");
        yes(restarted.employee("K001").nama.equals("Budi & Santoso"),"UTF8 XML &");
        restarted.putEmployee(new Karyawan("K101","Budi Santoso","Produksi","Operator"),"K001");
        yes(restarted.score("K101",2026)!=null && restarted.score("K001",2026)==null,"migrasi ID");
        boolean duplicate=false;
        try{restarted.putEmployee(new Karyawan("K002","Tumpang Tindih","HQ","X"),"K101");}catch(IllegalArgumentException ex){duplicate=true;}
        yes(duplicate,"mencegah ID ganda");
        boolean bad=false;
        try{new Penilaian("K002",2026,110,20,40,60,80);}catch(IllegalArgumentException ex){bad=true;}
        yes(bad,"validasi nilai 0..100");
        Path csv=folder.resolve("hasil.csv");CsvExporter.write(restarted,2026,csv);
        String exported=new String(Files.readAllBytes(csv),java.nio.charset.StandardCharsets.UTF_8);
        yes(exported.contains("Budi Santoso") && exported.contains("0.840") && exported.contains("Dewi Lestari"),"ekspor CSV");
        restarted.removeScore("K101",2026);
        yes(restarted.rankings(2026).size()==1,"hapus penilaian");
        restarted.removeEmployee("K002");
        yes(restarted.scores().isEmpty() && restarted.employees().size()==1,"hapus karyawan cascade");
        DataStore last=new DataStore(data);
        yes(last.employees().size()==1 && last.scores().isEmpty(),"reload setelah hapus");
        System.out.println("TEST OK: 12 pemeriksaan penyimpanan, validasi, skor, peringkat, ekspor, dan integritas data.");
    }
}
