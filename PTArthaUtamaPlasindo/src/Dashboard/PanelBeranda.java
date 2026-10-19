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
public class PanelBeranda extends javax.swing.JPanel {
    private DataStore store;
    private Runnable onChange;
    private int year = java.time.Year.now().getValue();

    public PanelBeranda() {
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
        lblMulai.setHorizontalAlignment(SwingConstants.CENTER);
        lblMulai.setFont(new Font("SansSerif", Font.BOLD, 15));
        attachEvents();
    }

    private void attachEvents() { }
    public void refreshData() {
        DefaultTableModel m=new DefaultTableModel(new Object[]{"Tahap","Langkah","Penjelasan"},0){
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        m.addRow(new Object[]{"01","Karyawan","Tambah data karyawan lalu simpan"});
        m.addRow(new Object[]{"02","Penilaian","Input nilai untuk tiap karyawan dan tahun"});
        m.addRow(new Object[]{"03","Ranking","Periksa peringkat berdasarkan nilai tertimbang"});
        m.addRow(new Object[]{"04","Laporan","Ekspor rekap nilai sebagai CSV"});
        dataTable.setModel(m);
    }

    @SuppressWarnings("unchecked")
    //GEN-BEGIN:initComponents
    private void initComponents() {
        headingLabel = new javax.swing.JLabel();
        subtitleLabel = new javax.swing.JLabel();
        footerLabel = new javax.swing.JLabel();
        lblMulai = new javax.swing.JLabel();
        headerArea = new javax.swing.JPanel();
        contentArea = new javax.swing.JPanel();
        toolBar = new javax.swing.JPanel();
        tableScroll = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();

        setBackground(new java.awt.Color(241,245,251));
        setLayout(new java.awt.BorderLayout());
        headerArea.setBackground(new java.awt.Color(241,245,251));
        headerArea.setLayout(new java.awt.GridLayout(2,1));
        headingLabel.setText("Panduan Penggunaan");
        headingLabel.setFont(new java.awt.Font("SansSerif",1,23));
        subtitleLabel.setText("Informasi aplikasi penilaian kinerja PT Artha Utama Plasindo.");
        subtitleLabel.setFont(new java.awt.Font("SansSerif",0,12));
        headerArea.add(headingLabel);
        headerArea.add(subtitleLabel);
        add(headerArea, java.awt.BorderLayout.NORTH);
        contentArea.setBackground(new java.awt.Color(255,255,255));
        contentArea.setLayout(new java.awt.BorderLayout());
        toolBar.setBackground(new java.awt.Color(255,255,255));
        toolBar.setLayout(new java.awt.GridLayout(1,1,8,0));
        lblMulai.setText("Mulai: input data karyawan → beri penilaian → lihat ranking");
        toolBar.add(lblMulai);
        contentArea.add(toolBar, java.awt.BorderLayout.NORTH);
        tableScroll.setViewportView(dataTable);
        contentArea.add(tableScroll,java.awt.BorderLayout.CENTER);
        add(contentArea,java.awt.BorderLayout.CENTER);
        footerLabel.setText("Dashboard utama menampilkan ringkasan data nyata, bukan angka contoh.");
        footerLabel.setFont(new java.awt.Font("SansSerif",0,12));
        add(footerLabel,java.awt.BorderLayout.SOUTH);
    }
    //GEN-END:initComponents

    //GEN-BEGIN:variables
    private javax.swing.JPanel contentArea;
    private javax.swing.JTable dataTable;
    private javax.swing.JLabel footerLabel;
    private javax.swing.JPanel headerArea;
    private javax.swing.JLabel headingLabel;
    private javax.swing.JLabel lblMulai;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JScrollPane tableScroll;
    private javax.swing.JPanel toolBar;
    //GEN-END:variables
}
