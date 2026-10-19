package model;

public final class Karyawan {
    public final String id;
    public final String nama;
    public final String bagian;
    public final String jabatan;

    public Karyawan(String id, String nama, String bagian, String jabatan) {
        this.id = check(id, "ID Karyawan");
        this.nama = check(nama, "Nama");
        this.bagian = check(bagian, "Bagian");
        this.jabatan = check(jabatan, "Jabatan");
    }

    private static String check(String input, String label) {
        String v = input == null ? "" : input.trim();
        if (v.isEmpty()) throw new IllegalArgumentException(label + " wajib diisi.");
        if (v.length() > 100) throw new IllegalArgumentException(label + " maksimal 100 karakter.");
        return v;
    }
}
