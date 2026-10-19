package service;

import model.Penilaian;

/** Bobot: kinerja 30%, disiplin 25%, kerja sama 20%, tanggung jawab 15%, inisiatif 10%. */
public final class PerhitunganNilai {
    private PerhitunganNilai() { }

    public static double hitung(Penilaian p) {
        return (p.kinerja * 0.30 + p.disiplin * 0.25 + p.kerjasama * 0.20
                + p.tanggungJawab * 0.15 + p.inisiatif * 0.10) / 100.0;
    }

    public static String predikat(double skor) {
        if (skor >= 0.90) return "Istimewa";
        if (skor >= 0.80) return "Sangat Baik";
        if (skor >= 0.70) return "Baik";
        if (skor >= 0.60) return "Cukup";
        return "Perlu Pembinaan";
    }
}
