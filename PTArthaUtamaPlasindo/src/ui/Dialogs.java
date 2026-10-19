package ui;

import java.awt.*;
import javax.swing.*;
import data.DataStore;
import model.Karyawan;
import model.Penilaian;

public final class Dialogs {
    private Dialogs() { }
    public static Karyawan employee(Component parent, Karyawan old) {
        JTextField id=new JTextField(old==null?"":old.id,22);
        JTextField nama=new JTextField(old==null?"":old.nama,22);
        JTextField bagian=new JTextField(old==null?"":old.bagian,22);
        JTextField jabatan=new JTextField(old==null?"":old.jabatan,22);
        JPanel p=grid(new String[]{"ID Karyawan","Nama Lengkap","Bagian / Divisi","Jabatan"},new JComponent[]{id,nama,bagian,jabatan});
        while (JOptionPane.showConfirmDialog(parent,p,old==null?"Tambah Karyawan":"Edit Karyawan",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE)==JOptionPane.OK_OPTION) {
            try {return new Karyawan(id.getText(),nama.getText(),bagian.getText(),jabatan.getText());}
            catch (IllegalArgumentException ex) { JOptionPane.showMessageDialog(parent,ex.getMessage(),"Validasi",JOptionPane.WARNING_MESSAGE); }
        }
        return null;
    }
    public static Penilaian score(Component parent,DataStore store,Penilaian old,int year) {
        java.util.List<Karyawan> people=store.employees();
        if(people.isEmpty()){JOptionPane.showMessageDialog(parent,"Tambahkan karyawan sebelum memberikan penilaian.");return null;}
        JComboBox<String> sel=new JComboBox<String>();
        for(Karyawan k:people)sel.addItem(k.id+" - "+k.nama);
        if(old!=null)for(int i=0;i<people.size();i++)if(people.get(i).id.equals(old.idKaryawan))sel.setSelectedIndex(i);
        sel.setEnabled(old==null); // Edit hanya mengubah nilainya, bukan karyawan
        JSpinner tahun=new JSpinner(new SpinnerNumberModel(old==null?year:old.tahun,2000,2100,1));
        tahun.setEnabled(old==null);
        JSpinner d=spinner(old==null?80:old.disiplin),k=spinner(old==null?80:old.kinerja),s=spinner(old==null?80:old.kerjasama),t=spinner(old==null?80:old.tanggungJawab),n=spinner(old==null?80:old.inisiatif);
        JPanel p=grid(new String[]{"Karyawan","Tahun Penilaian","Disiplin (25%)","Kinerja (30%)","Kerja Sama (20%)","Tanggung Jawab (15%)","Inisiatif (10%)"},new JComponent[]{sel,tahun,d,k,s,t,n});
        while(JOptionPane.showConfirmDialog(parent,p,old==null?"Tambah Penilaian":"Edit Penilaian",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE)==JOptionPane.OK_OPTION) {
            try {
                String id=people.get(sel.getSelectedIndex()).id;
                return new Penilaian(id,(Integer)tahun.getValue(),(Integer)d.getValue(),(Integer)k.getValue(),(Integer)s.getValue(),(Integer)t.getValue(),(Integer)n.getValue());
            } catch(RuntimeException ex) {JOptionPane.showMessageDialog(parent,ex.getMessage(),"Validasi",JOptionPane.WARNING_MESSAGE);}
        }
        return null;
    }
    private static JSpinner spinner(int n) { return new JSpinner(new SpinnerNumberModel(n,0,100,1)); }
    private static JPanel grid(String[] labels,JComponent[] fields) {
        JPanel p=new JPanel(new GridLayout(labels.length,2,14,12));
        p.setBorder(BorderFactory.createEmptyBorder(14,12,14,12));
        for(int i=0;i<labels.length;i++){p.add(new JLabel(labels[i]));p.add(fields[i]);}
        return p;
    }
}
