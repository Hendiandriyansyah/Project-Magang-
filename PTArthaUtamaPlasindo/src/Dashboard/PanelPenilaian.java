package Dashboard;

import data.DataStore;
import model.*;
import service.*;
import ui.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/** Halaman dapat diedit melalui tab Design di NetBeans 8.2. */
public class PanelPenilaian extends javax.swing.JPanel {
    private DataStore store;
    private Runnable onChange;
    private int year = java.time.Year.now().getValue();

    public PanelPenilaian() {
        initComponents();
        setupView();
    }

    public void bind(DataStore store, Runnable onChange) {
        this.store = store;
        this.onChange = onChange;
        refreshData();
    }

    private void changed() {
        if(onChange != null) onChange.run();
        refreshData();
    }
    private void error(Exception e) { Styles.error(this,e); }

    private void setupView() {
        setBorder(BorderFactory.createEmptyBorder(12,20,22,20));
        headerArea.setPreferredSize(new Dimension(700,82));
        headerArea.setBorder(BorderFactory.createEmptyBorder(0,2,12,0));
        Styles.heading(headingLabel,subtitleLabel);
        contentArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(218,228,242)),
            BorderFactory.createEmptyBorder(12,12,12,12)));
        toolBar.setBorder(BorderFactory.createEmptyBorder(4,0,14,0));
        footerLabel.setBorder(BorderFactory.createEmptyBorder(12,3,0,3));
        footerLabel.setForeground(Styles.MUTED);
        Styles.table(dataTable);
        dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableScroll.setBorder(BorderFactory.createEmptyBorder());
        Styles.button(btnTambah, true);
        Styles.button(btnEdit, false);
        Styles.button(btnHapus, false);
        Styles.button(btnRefresh, false);
        attachEvents();
    }

    private void attachEvents() {
        btnTambah.addActionListener(e -> {
            if(store==null)return;
            Penilaian p=Dialogs.score(this,store,null,year);
            if(p!=null)try{
                if(store.score(p.idKaryawan,p.tahun)!=null && JOptionPane.showConfirmDialog(this,"Nilai untuk karyawan dan tahun ini sudah ada. Timpa?","Konfirmasi",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION)return;
                store.putScore(p);changed();
            }catch(Exception ex){error(ex);}
        });
        btnEdit.addActionListener(e -> {
            Penilaian p=selected();if(p==null)return;
            Penilaian update=Dialogs.score(this,store,p,p.tahun);
            if(update!=null)try {store.putScore(update);changed();}catch(Exception ex){error(ex);}
        });
        btnHapus.addActionListener(e -> {
            Penilaian p=selected();if(p==null)return;
            if(JOptionPane.showConfirmDialog(this,"Hapus penilaian tahun "+p.tahun+"?","Konfirmasi",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
                try{store.removeScore(p.idKaryawan,p.tahun);changed();}catch(Exception ex){error(ex);}
        });
        btnRefresh.addActionListener(e->refreshData());
    }
    private Penilaian selected(){
        if(store==null)return null;
        int row=dataTable.getSelectedRow();
        if(row<0){JOptionPane.showMessageDialog(this,"Pilih baris penilaian terlebih dahulu.");return null;}
        String id=String.valueOf(dataTable.getValueAt(row,0));
        int y=(Integer)dataTable.getValueAt(row,2);
        return store.score(id,y);
    }
    public void refreshData() {
        if(store==null)return;
        DefaultTableModel m=new DefaultTableModel(new Object[]{"ID","Nama Karyawan","Tahun","Disiplin","Kinerja","Kerja Sama","Tanggung Jawab","Inisiatif","Nilai Akhir"},0){
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        java.util.List<Penilaian> scores=store.scores();
        java.util.Collections.sort(scores,new java.util.Comparator<Penilaian>(){
            public int compare(Penilaian a,Penilaian b){int x=Integer.compare(b.tahun,a.tahun);return x!=0?x:a.idKaryawan.compareTo(b.idKaryawan);}
        });
        for(Penilaian p:scores){
            Karyawan k=store.employee(p.idKaryawan);
            if(k!=null)m.addRow(new Object[]{p.idKaryawan,k.nama,p.tahun,p.disiplin,p.kinerja,p.kerjasama,p.tanggungJawab,p.inisiatif,String.format(java.util.Locale.US,"%.3f",PerhitunganNilai.hitung(p))});
        }
        dataTable.setModel(m);
        footerLabel.setText(m.getRowCount()+" catatan penilaian • Disiplin 25% · Kinerja 30% · Kerja Sama 20% · Tanggung Jawab 15% · Inisiatif 10%");
    }

    @SuppressWarnings("unchecked")
    //GEN-BEGIN:initComponents
    private void initComponents() {
        headingLabel = new javax.swing.JLabel();
        subtitleLabel = new javax.swing.JLabel();
        footerLabel = new javax.swing.JLabel();
        btnTambah = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        headerArea = new javax.swing.JPanel();
        contentArea = new javax.swing.JPanel();
        toolBar = new javax.swing.JPanel();
        tableScroll = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();

        setBackground(new java.awt.Color(241,245,251));
        setLayout(new java.awt.BorderLayout());
        headerArea.setBackground(new java.awt.Color(241,245,251));
        headerArea.setLayout(new java.awt.GridLayout(2,1));
        headingLabel.setText("Penilaian Kinerja");
        headingLabel.setFont(new java.awt.Font("SansSerif",1,23));
        subtitleLabel.setText("Beri nilai 0–100 dengan pembobotan konsisten per tahun.");
        subtitleLabel.setFont(new java.awt.Font("SansSerif",0,12));
        headerArea.add(headingLabel);
        headerArea.add(subtitleLabel);
        add(headerArea, java.awt.BorderLayout.NORTH);
        contentArea.setBackground(new java.awt.Color(255,255,255));
        contentArea.setLayout(new java.awt.BorderLayout());
        toolBar.setBackground(new java.awt.Color(255,255,255));
        toolBar.setLayout(new java.awt.GridLayout(1,4,8,0));
        btnTambah.setText("+ Input Nilai");
        toolBar.add(btnTambah);
        btnEdit.setText("Ubah Nilai");
        toolBar.add(btnEdit);
        btnHapus.setText("Hapus Nilai");
        toolBar.add(btnHapus);
        btnRefresh.setText("Muat Ulang");
        toolBar.add(btnRefresh);
        contentArea.add(toolBar, java.awt.BorderLayout.NORTH);
        tableScroll.setViewportView(dataTable);
        contentArea.add(tableScroll,java.awt.BorderLayout.CENTER);
        add(contentArea,java.awt.BorderLayout.CENTER);
        footerLabel.setText("Bobot: Kinerja 30% · Disiplin 25% · Kerja Sama 20% · Tanggung Jawab 15% · Inisiatif 10%.");
        footerLabel.setFont(new java.awt.Font("SansSerif",0,12));
        add(footerLabel,java.awt.BorderLayout.SOUTH);
    }
    //GEN-END:initComponents

    //GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnTambah;
    private javax.swing.JPanel contentArea;
    private javax.swing.JTable dataTable;
    private javax.swing.JLabel footerLabel;
    private javax.swing.JPanel headerArea;
    private javax.swing.JLabel headingLabel;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JScrollPane tableScroll;
    private javax.swing.JPanel toolBar;
    //GEN-END:variables
}
