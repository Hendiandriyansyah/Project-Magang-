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
public class PanelRanking extends javax.swing.JPanel {
    private DataStore store;
    private Runnable onChange;
    private int year = java.time.Year.now().getValue();

    public PanelRanking() {
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
        Styles.button(btnSebelum, false);
        lblTahun.setHorizontalAlignment(SwingConstants.CENTER);
        lblTahun.setFont(new Font("SansSerif", Font.BOLD, 15));
        Styles.button(btnSesudah, false);
        Styles.button(btnEkspor, true);
        attachEvents();
    }

    private void attachEvents() {
        btnSebelum.addActionListener(e->{if(year>2000){year--;refreshData();}});
        btnSesudah.addActionListener(e->{if(year<2100){year++;refreshData();}});
        btnEkspor.addActionListener(e->exportCsv());
    }
    private void exportCsv() {if(store!=null)try{
        JFileChooser chooser=new JFileChooser();chooser.setSelectedFile(new java.io.File("ranking-"+year+".csv"));
        if(chooser.showSaveDialog(this)!=JFileChooser.APPROVE_OPTION)return;
        Path file=chooser.getSelectedFile().toPath();
        if(!file.toString().toLowerCase(java.util.Locale.ROOT).endsWith(".csv"))file=java.nio.file.Paths.get(file.toString()+".csv");
        if(java.nio.file.Files.exists(file)&&JOptionPane.showConfirmDialog(this,"File sudah ada. Timpa?","Konfirmasi",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION)return;
        CsvExporter.write(store,year,file);
        JOptionPane.showMessageDialog(this,"CSV berhasil disimpan:\n"+file.toAbsolutePath());
    }catch(Exception ex){error(ex);}}
    public void refreshData() {
        if(store==null)return;
        lblTahun.setText("TAHUN "+year);
        DefaultTableModel m=new DefaultTableModel(new Object[]{"Peringkat","ID","Nama Karyawan","Bagian","Nilai Akhir","Predikat"},0){
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        int n=0;
        for(DataStore.Ranking r:store.rankings(year))m.addRow(new Object[]{++n,r.karyawan.id,r.karyawan.nama,r.karyawan.bagian,String.format(java.util.Locale.US,"%.3f",r.skor),PerhitunganNilai.predikat(r.skor)});
        dataTable.setModel(m);
        footerLabel.setText(n+" karyawan memiliki penilaian tahun "+year+" • Urutan berdasarkan nilai tertinggi");
    }

    @SuppressWarnings("unchecked")
    //GEN-BEGIN:initComponents
    private void initComponents() {
        headingLabel = new javax.swing.JLabel();
        subtitleLabel = new javax.swing.JLabel();
        footerLabel = new javax.swing.JLabel();
        btnSebelum = new javax.swing.JButton();
        lblTahun = new javax.swing.JLabel();
        btnSesudah = new javax.swing.JButton();
        btnEkspor = new javax.swing.JButton();
        headerArea = new javax.swing.JPanel();
        contentArea = new javax.swing.JPanel();
        toolBar = new javax.swing.JPanel();
        tableScroll = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();

        setBackground(new java.awt.Color(241,245,251));
        setLayout(new java.awt.BorderLayout());
        headerArea.setBackground(new java.awt.Color(241,245,251));
        headerArea.setLayout(new java.awt.GridLayout(2,1));
        headingLabel.setText("Ranking Karyawan");
        headingLabel.setFont(new java.awt.Font("SansSerif",1,23));
        subtitleLabel.setText("Peringkat dihitung otomatis berdasarkan hasil penilaian.");
        subtitleLabel.setFont(new java.awt.Font("SansSerif",0,12));
        headerArea.add(headingLabel);
        headerArea.add(subtitleLabel);
        add(headerArea, java.awt.BorderLayout.NORTH);
        contentArea.setBackground(new java.awt.Color(255,255,255));
        contentArea.setLayout(new java.awt.BorderLayout());
        toolBar.setBackground(new java.awt.Color(255,255,255));
        toolBar.setLayout(new java.awt.GridLayout(1,4,8,0));
        btnSebelum.setText("← Tahun Sebelumnya");
        toolBar.add(btnSebelum);
        lblTahun.setText("2026");
        toolBar.add(lblTahun);
        btnSesudah.setText("Tahun Berikutnya →");
        toolBar.add(btnSesudah);
        btnEkspor.setText("Ekspor CSV");
        toolBar.add(btnEkspor);
        contentArea.add(toolBar, java.awt.BorderLayout.NORTH);
        tableScroll.setViewportView(dataTable);
        contentArea.add(tableScroll,java.awt.BorderLayout.CENTER);
        add(contentArea,java.awt.BorderLayout.CENTER);
        footerLabel.setText("Nilai akhir 0,000–1,000. Jika seri, diurutkan berdasarkan nama.");
        footerLabel.setFont(new java.awt.Font("SansSerif",0,12));
        add(footerLabel,java.awt.BorderLayout.SOUTH);
    }
    //GEN-END:initComponents

    //GEN-BEGIN:variables
    private javax.swing.JButton btnEkspor;
    private javax.swing.JButton btnSebelum;
    private javax.swing.JButton btnSesudah;
    private javax.swing.JPanel contentArea;
    private javax.swing.JTable dataTable;
    private javax.swing.JLabel footerLabel;
    private javax.swing.JPanel headerArea;
    private javax.swing.JLabel headingLabel;
    private javax.swing.JLabel lblTahun;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JScrollPane tableScroll;
    private javax.swing.JPanel toolBar;
    //GEN-END:variables
}
