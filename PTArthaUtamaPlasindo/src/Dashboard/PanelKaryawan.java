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
public class PanelKaryawan extends javax.swing.JPanel {
    private DataStore store;
    private Runnable onChange;
    private int year = java.time.Year.now().getValue();

    public PanelKaryawan() {
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
        searchField.setToolTipText("Pencarian otomatis");
        searchField.setBorder(BorderFactory.createTitledBorder("Cari Karyawan"));
        attachEvents();
    }

    private void attachEvents() {
        btnTambah.addActionListener(e -> {
            if (store == null) return;
            Karyawan k=Dialogs.employee(this,null);
            if(k!=null) try {store.putEmployee(k,null);changed();}
            catch(Exception ex){error(ex);}
        });
        btnEdit.addActionListener(e -> {
            Karyawan previous=selected(); if(previous==null)return;
            Karyawan next=Dialogs.employee(this,previous);
            if(next!=null)try{store.putEmployee(next,previous.id);changed();}
            catch(Exception ex){error(ex);}
        });
        btnHapus.addActionListener(e -> {
            Karyawan previous=selected();if(previous==null)return;
            if(JOptionPane.showConfirmDialog(this,"Hapus "+previous.nama+" beserta seluruh penilaiannya?","Konfirmasi Hapus",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
                try{store.removeEmployee(previous.id);changed();}catch(Exception ex){error(ex);}
        });
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e){refreshData();}
            public void removeUpdate(javax.swing.event.DocumentEvent e){refreshData();}
            public void changedUpdate(javax.swing.event.DocumentEvent e){refreshData();}
        });
    }
    private Karyawan selected() {
        if(store==null)return null;
        int row=dataTable.getSelectedRow();
        if(row<0){JOptionPane.showMessageDialog(this,"Pilih satu baris karyawan terlebih dahulu.");return null;}
        return store.employee(String.valueOf(dataTable.getValueAt(row,0)));
    }
    public void refreshData() {
        if(store==null)return;
        String q=searchField.getText().trim().toLowerCase(java.util.Locale.ROOT);
        DefaultTableModel m=new DefaultTableModel(new Object[]{"ID","Nama Karyawan","Bagian / Divisi","Jabatan"},0){
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        for(Karyawan k:store.employees()) {
            if((k.id+" "+k.nama+" "+k.bagian+" "+k.jabatan).toLowerCase(java.util.Locale.ROOT).contains(q))
                m.addRow(new Object[]{k.id,k.nama,k.bagian,k.jabatan});
        }
        dataTable.setModel(m);
        footerLabel.setText(m.getRowCount()+" dari "+store.employees().size()+" karyawan • Data tersimpan otomatis di komputer ini");
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
        searchField = new javax.swing.JTextField();
        headerArea = new javax.swing.JPanel();
        contentArea = new javax.swing.JPanel();
        toolBar = new javax.swing.JPanel();
        tableScroll = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();

        setBackground(new java.awt.Color(241,245,251));
        setLayout(new java.awt.BorderLayout());
        headerArea.setBackground(new java.awt.Color(241,245,251));
        headerArea.setLayout(new java.awt.GridLayout(2,1));
        headingLabel.setText("Data Karyawan");
        headingLabel.setFont(new java.awt.Font("SansSerif",1,23));
        subtitleLabel.setText("Kelola identitas, bagian, dan jabatan karyawan.");
        subtitleLabel.setFont(new java.awt.Font("SansSerif",0,12));
        headerArea.add(headingLabel);
        headerArea.add(subtitleLabel);
        add(headerArea, java.awt.BorderLayout.NORTH);
        contentArea.setBackground(new java.awt.Color(255,255,255));
        contentArea.setLayout(new java.awt.BorderLayout());
        toolBar.setBackground(new java.awt.Color(255,255,255));
        toolBar.setLayout(new java.awt.GridLayout(1,4,8,0));
        btnTambah.setText("+ Tambah Karyawan");
        toolBar.add(btnTambah);
        btnEdit.setText("Edit Terpilih");
        toolBar.add(btnEdit);
        btnHapus.setText("Hapus Terpilih");
        toolBar.add(btnHapus);
        searchField.setText("");
        toolBar.add(searchField);
        contentArea.add(toolBar, java.awt.BorderLayout.NORTH);
        tableScroll.setViewportView(dataTable);
        contentArea.add(tableScroll,java.awt.BorderLayout.CENTER);
        add(contentArea,java.awt.BorderLayout.CENTER);
        footerLabel.setText("Pilih satu baris untuk mengedit atau menghapus. Data tersimpan otomatis.");
        footerLabel.setFont(new java.awt.Font("SansSerif",0,12));
        add(footerLabel,java.awt.BorderLayout.SOUTH);
    }
    //GEN-END:initComponents

    //GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnTambah;
    private javax.swing.JPanel contentArea;
    private javax.swing.JTable dataTable;
    private javax.swing.JLabel footerLabel;
    private javax.swing.JPanel headerArea;
    private javax.swing.JLabel headingLabel;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JScrollPane tableScroll;
    private javax.swing.JPanel toolBar;
    //GEN-END:variables
}
