package model;

public final class Penilaian {
    public final String idKaryawan;
    public final int tahun;
    public final int disiplin, kinerja, kerjasama, tanggungJawab, inisiatif;

    public Penilaian(String id, int tahun, int disiplin, int kinerja,
            int kerjasama, int tanggungJawab, int inisiatif) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("ID tidak valid.");
        if (tahun < 2000 || tahun > 2100) throw new IllegalArgumentException("Periode harus antara 2000-2100.");
        this.idKaryawan = id.trim();
        this.tahun = tahun;
        this.disiplin = valid(disiplin);
        this.kinerja = valid(kinerja);
        this.kerjasama = valid(kerjasama);
        this.tanggungJawab = valid(tanggungJawab);
        this.inisiatif = valid(inisiatif);
    }

    private static int valid(int n) {
        if (n < 0 || n > 100) throw new IllegalArgumentException("Nilai harus 0 sampai 100.");
        return n;
    }
}
