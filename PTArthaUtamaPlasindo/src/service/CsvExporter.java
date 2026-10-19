package service;

import data.DataStore;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import model.Karyawan;

public final class CsvExporter {
    private CsvExporter() { }
    private static String quote(String s) { return "\""+(s==null?"":s.replace("\"","\"\"").replace("\r"," ").replace("\n"," "))+"\""; }

    public static void write(DataStore store,int year,Path output) throws IOException {
        try (BufferedWriter w=Files.newBufferedWriter(output,StandardCharsets.UTF_8)) {
            w.write('\ufeff'); // BOM: Excel Windows mengenali UTF-8
            w.write("Peringkat,ID Karyawan,Nama,Bagian,Jabatan,Periode,Disiplin,Kinerja,Kerja Sama,Tanggung Jawab,Inisiatif,Nilai Akhir,Predikat\r\n");
            List<DataStore.Ranking> rankings=store.rankings(year);
            for(int i=0;i<rankings.size();i++) {
                DataStore.Ranking x=rankings.get(i);Karyawan k=x.karyawan;
                String[] cols={String.valueOf(i+1),k.id,k.nama,k.bagian,k.jabatan,String.valueOf(year),
                    String.valueOf(x.penilaian.disiplin),String.valueOf(x.penilaian.kinerja),
                    String.valueOf(x.penilaian.kerjasama),String.valueOf(x.penilaian.tanggungJawab),
                    String.valueOf(x.penilaian.inisiatif),String.format(Locale.US,"%.3f",x.skor),PerhitunganNilai.predikat(x.skor)};
                for(int j=0;j<cols.length;j++){if(j>0)w.write(',');w.write(quote(cols[j]));}
                w.write("\r\n");
            }
        }
    }
}
