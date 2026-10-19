package Dashboard;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import data.DataStore;
import data.DataStore.Ranking;
import service.PerhitunganNilai;
import java.io.IOException;
import java.util.List;
import Login.login;

/**
 * Dashboard visual yang dapat diedit di NetBeans 8.2 GUI Builder.
 * Simpan bersama dashboard.form dalam package Dashboard.
 */
public class dashboard extends javax.swing.JPanel {
    private DataStore store;
    private JPanel activePage;
    private String currentPage = "Beranda";


    public dashboard() {
        initComponents();
        configureDashboard();
    }

    private void configureDashboard() {
        setPreferredSize(new Dimension(1200, 750));
        setupIcons();
        menuPanel.removeAll();
        menuPanel.setLayout(new javax.swing.BoxLayout(menuPanel, javax.swing.BoxLayout.Y_AXIS));
        JButton[] nav={btnBeranda,btnKaryawan,btnPenilaian,btnRanking,btnLaporan};
        for(JButton b:nav){
            b.setAlignmentX(Component.LEFT_ALIGNMENT);
            b.setMaximumSize(new Dimension(Integer.MAX_VALUE,48));
            b.setPreferredSize(new Dimension(192,48));
            menuPanel.add(b);
            menuPanel.add(Box.createVerticalStrut(9));
        }
        barPanel.removeAll();
        barPanel.setLayout(new GridBagLayout());
        JLabel[] barLabels={barName1,barName2,barName3,barName4,barName5};
        JProgressBar[] barValues={bar1,bar2,bar3,bar4,bar5};
        for(int i=0;i<5;i++){
            GridBagConstraints n=new GridBagConstraints();
            n.gridx=0;n.gridy=i;n.weightx=0.36;n.anchor=GridBagConstraints.WEST;
            n.fill=GridBagConstraints.HORIZONTAL;n.insets=new Insets(4,0,18,18);
            barPanel.add(barLabels[i],n);
            n.gridx=1;n.weightx=0.64;n.insets=new Insets(4,0,18,0);
            barValues[i].setPreferredSize(new Dimension(160,25));
            barPanel.add(barValues[i],n);
        }
        GridBagConstraints spacer=new GridBagConstraints();
        spacer.gridx=0;spacer.gridy=5;spacer.weighty=1;spacer.fill=GridBagConstraints.VERTICAL;
        barPanel.add(Box.createVerticalGlue(),spacer);
        sidebarPanel.setPreferredSize(new Dimension(225, 650));
        brandPanel.setPreferredSize(new Dimension(225, 145));
        headerPanel.setPreferredSize(new Dimension(800, 85));
        statsPanel.setPreferredSize(new Dimension(800, 165));
        for (javax.swing.JPanel p : new javax.swing.JPanel[] {cardKaryawan,cardDinilai,cardTerbaik,cardPeriode,chartPanel,rankingPanel}) {
            p.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new Color(216, 227, 241)),
                javax.swing.BorderFactory.createEmptyBorder(12, 14, 12, 14)));
        }
        bodyPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 20, 20));
        headerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(18, 20, 12, 20));
        menuPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(22, 12, 22, 12));
        brandPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 20, 8, 10));
        statsPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 14, 0));
        lowerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        barPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(28, 0, 8, 0));
        java.awt.Color[] colors = {new Color(49,101,224),new Color(66,143,229),new Color(67,166,232),new Color(57,183,187),new Color(92,199,184)};
        javax.swing.JProgressBar[] progress = {bar1,bar2,bar3,bar4,bar5};
        int[] values = {93,89,85,82,79};
        for (int i = 0; i < progress.length; i++) {
            progress[i].setMinimum(0);
            progress[i].setMaximum(100);
            progress[i].setValue(values[i]);
            progress[i].setStringPainted(true);
            progress[i].setString(String.format(java.util.Locale.US, "%.3f", values[i]/100.0));
            progress[i].setForeground(colors[i]);
        }
        rankingTable.setModel(new DefaultTableModel(
            new Object[][] {
                {1, "Andi Pratama", "0.925", "Terbaik"},
                {2, "Siti Rahma", "0.887", "Sangat Baik"},
                {3, "Budi Santoso", "0.851", "Baik"},
                {4, "Dewi Lestari", "0.824", "Baik"}
            },
            new String[] {"Rank", "Nama Karyawan", "Nilai Akhir", "Status"}
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        });
        rankingTable.setRowHeight(38);
        rankingTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        btnBeranda.addActionListener(e -> showPage("Beranda"));
        btnKaryawan.addActionListener(e -> showPage("Data Karyawan"));
        btnPenilaian.addActionListener(e -> showPage("Data Penilaian"));
        btnRanking.addActionListener(e -> showPage("Data Ranking"));
        btnLaporan.addActionListener(e -> showPage("Data Laporan"));
        btnLogout.addActionListener(e -> logout());
        try {
            store = new DataStore(DataStore.defaultPath());
        } catch (IOException | RuntimeException ex) {
            JOptionPane.showMessageDialog(this,"Berkas data tidak dapat dibuka dan tidak akan ditimpa:\n"+
                    DataStore.defaultPath()+"\n"+ex.getMessage(),"Gagal membaca data",JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Data lokal bermasalah, periksa file data.xml",ex);
        }
        dateLabel.setText("Periode " + java.time.Year.now().getValue());
        setPreferredSize(new Dimension(1150,730));
        refreshSummary();
    }

    private void refreshSummary() {
        if (store == null) return;
        int year = java.time.Year.now().getValue();
        List<Ranking> list = store.rankings(year);
        lblKaryawanValue.setText(String.valueOf(store.employees().size()));
        lblDinilaiValue.setText(String.valueOf(list.size()));
        lblPeriodeValue.setText(String.valueOf(year));
        lblKaryawanNote.setText("Terdaftar di aplikasi");
        lblDinilaiNote.setText("Telah dinilai tahun ini");
        lblPeriodeNote.setText("Periode berjalan");
        lblTerbaikValue.setFont(new Font("Tahoma",Font.BOLD, list.isEmpty()?16:17));
        lblTerbaikValue.setText(list.isEmpty()?"Belum ada":list.get(0).karyawan.nama);
        lblTerbaikNote.setText(list.isEmpty()?"Input penilaian dahulu":String.format(java.util.Locale.US,"Nilai akhir %.3f",list.get(0).skor));
        DefaultTableModel m = new DefaultTableModel(new Object[]{"Rank", "Nama Karyawan", "Nilai Akhir", "Status"},0) {
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        for (int i=0;i<Math.min(8,list.size());i++) {
            Ranking r=list.get(i);
            m.addRow(new Object[]{i+1,r.karyawan.nama,String.format(java.util.Locale.US,"%.3f",r.skor),PerhitunganNilai.predikat(r.skor)});
        }
        rankingTable.setModel(m);
        JLabel[] names={barName1,barName2,barName3,barName4,barName5};
        JProgressBar[] bars={bar1,bar2,bar3,bar4,bar5};
        for(int i=0;i<5;i++) {
            if(i<list.size()) {
                Ranking r=list.get(i);
                names[i].setText(r.karyawan.nama);
                bars[i].setValue((int)Math.round(r.skor*100));
                bars[i].setString(String.format(java.util.Locale.US,"%.3f",r.skor));
            } else {
                names[i].setText("- Belum ada nilai -");bars[i].setValue(0);bars[i].setString("-");
            }
        }
    }

    /** Load from classpath so icons work in both NetBeans and packaged JAR. */
    private ImageIcon loadIcon(String name, int size) {
        java.net.URL url = getClass().getResource("/Image/" + name + ".png");
        if (url == null) return null;
        Image image = new ImageIcon(url).getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    private void setupIcons() {
        String[] iconKeys = {"home", "employees", "assessment", "ranking", "report", "logout"};
        JButton[] buttons = {btnBeranda, btnKaryawan, btnPenilaian, btnRanking, btnLaporan, btnLogout};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setIcon(loadIcon(iconKeys[i], 20));
            buttons[i].setHorizontalAlignment(SwingConstants.LEFT);
            buttons[i].setIconTextGap(12);
            buttons[i].setBorder(BorderFactory.createEmptyBorder(9, 13, 9, 7));
            buttons[i].setFocusPainted(false);
            buttons[i].setOpaque(true);
        }
        btnBeranda.setBackground(new Color(38, 93, 215));
        btnLogout.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(7, 12, 12, 12),
            BorderFactory.createEmptyBorder(9, 13, 9, 7)));
        String[] cardIcons = {"total", "checked", "best", "calendar"};
        JLabel[] labels = {lblKaryawanTitle, lblDinilaiTitle, lblTerbaikTitle, lblPeriodeTitle};
        for (int i = 0; i < labels.length; i++) {
            labels[i].setIcon(loadIcon(cardIcons[i], 25));
            labels[i].setIconTextGap(8);
        }
        chartTitle.setIcon(loadIcon("report", 20));
        chartTitle.setIconTextGap(7);
        rankingTitle.setIcon(loadIcon("ranking", 20));
        rankingTitle.setIconTextGap(7);
    }

    private void showPage(String page) {
        if (store==null)return;
        if("Beranda".equals(page)) {
            bodyPanel.removeAll();
            bodyPanel.add(statsPanel,BorderLayout.NORTH);
            bodyPanel.add(lowerPanel,BorderLayout.CENTER);
            bodyPanel.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
            refreshSummary();
            activePage=null;
        } else {
            bodyPanel.removeAll();
            bodyPanel.setBorder(BorderFactory.createEmptyBorder());
            if("Data Karyawan".equals(page)){
                PanelKaryawan p=new PanelKaryawan();p.bind(store,()->refreshSummary());activePage=p;
            }else if("Data Penilaian".equals(page)){
                PanelPenilaian p=new PanelPenilaian();p.bind(store,()->refreshSummary());activePage=p;
            }else if("Data Ranking".equals(page)){
                PanelRanking p=new PanelRanking();p.bind(store,()->refreshSummary());activePage=p;
            }else if("Data Laporan".equals(page)){
                PanelLaporan p=new PanelLaporan();p.bind(store,()->refreshSummary());activePage=p;
            }else{return;}
            bodyPanel.add(activePage,BorderLayout.CENTER);
        }
        currentPage=page;
        titleLabel.setText("Beranda".equals(page)?"Dashboard":page);
        JButton[] buttons={btnBeranda,btnKaryawan,btnPenilaian,btnRanking,btnLaporan};
        String[] pages={"Beranda","Data Karyawan","Data Penilaian","Data Ranking","Data Laporan"};
        for(int i=0;i<buttons.length;i++){
            buttons[i].setBackground(page.equals(pages[i])?new Color(38,93,215):new Color(27,43,67));
        }
        bodyPanel.revalidate();bodyPanel.repaint();
    }

    private void logout() {
        int option=JOptionPane.showConfirmDialog(this,"Keluar dari sesi aplikasi?","Konfirmasi Logout",JOptionPane.YES_NO_OPTION);
        if(option!=JOptionPane.YES_OPTION)return;
        JFrame frame=new JFrame("Login - PT Artha Utama Plasindo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new login());frame.setSize(950,600);
        frame.setLocationRelativeTo(null);frame.setVisible(true);
        Window window=SwingUtilities.getWindowAncestor(this);
        if(window!=null)window.dispose();
    }

    //GEN-BEGIN:initComponents
    private void initComponents() {

        sidebarPanel = new javax.swing.JPanel();
        brandPanel = new javax.swing.JPanel();
        logoLabel = new javax.swing.JLabel();
        brandLabel = new javax.swing.JLabel();
        subBrandLabel = new javax.swing.JLabel();
        menuPanel = new javax.swing.JPanel();
        btnBeranda = new javax.swing.JButton();
        btnKaryawan = new javax.swing.JButton();
        btnPenilaian = new javax.swing.JButton();
        btnRanking = new javax.swing.JButton();
        btnLaporan = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        headerPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        bodyPanel = new javax.swing.JPanel();
        statsPanel = new javax.swing.JPanel();
        cardKaryawan = new javax.swing.JPanel();
        lblKaryawanTitle = new javax.swing.JLabel();
        lblKaryawanValue = new javax.swing.JLabel();
        lblKaryawanNote = new javax.swing.JLabel();
        cardDinilai = new javax.swing.JPanel();
        lblDinilaiTitle = new javax.swing.JLabel();
        lblDinilaiValue = new javax.swing.JLabel();
        lblDinilaiNote = new javax.swing.JLabel();
        cardTerbaik = new javax.swing.JPanel();
        lblTerbaikTitle = new javax.swing.JLabel();
        lblTerbaikValue = new javax.swing.JLabel();
        lblTerbaikNote = new javax.swing.JLabel();
        cardPeriode = new javax.swing.JPanel();
        lblPeriodeTitle = new javax.swing.JLabel();
        lblPeriodeValue = new javax.swing.JLabel();
        lblPeriodeNote = new javax.swing.JLabel();
        lowerPanel = new javax.swing.JPanel();
        chartPanel = new javax.swing.JPanel();
        chartTitle = new javax.swing.JLabel();
        barPanel = new javax.swing.JPanel();
        barName1 = new javax.swing.JLabel();
        bar1 = new javax.swing.JProgressBar();
        barName2 = new javax.swing.JLabel();
        bar2 = new javax.swing.JProgressBar();
        barName3 = new javax.swing.JLabel();
        bar3 = new javax.swing.JProgressBar();
        barName4 = new javax.swing.JLabel();
        bar4 = new javax.swing.JProgressBar();
        barName5 = new javax.swing.JLabel();
        bar5 = new javax.swing.JProgressBar();
        rankingPanel = new javax.swing.JPanel();
        rankingTitle = new javax.swing.JLabel();
        tableScroll = new javax.swing.JScrollPane();
        rankingTable = new javax.swing.JTable();

        setBackground(new java.awt.Color(241, 245, 250));
        setLayout(new java.awt.BorderLayout());

        sidebarPanel.setBackground(new java.awt.Color(27, 43, 67));
        sidebarPanel.setLayout(new java.awt.BorderLayout());

        brandPanel.setBackground(new java.awt.Color(27, 43, 67));
        brandPanel.setLayout(new java.awt.GridLayout(3, 1));

        logoLabel.setFont(new java.awt.Font("Tahoma", 1, 26)); // NOI18N
        logoLabel.setForeground(new java.awt.Color(255, 255, 255));
        logoLabel.setText("AU");
        brandPanel.add(logoLabel);

        brandLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        brandLabel.setForeground(new java.awt.Color(255, 255, 255));
        brandLabel.setText("ARTHA UTAMA");
        brandPanel.add(brandLabel);

        subBrandLabel.setForeground(new java.awt.Color(188, 203, 224));
        subBrandLabel.setText("PLASINDO");
        brandPanel.add(subBrandLabel);

        sidebarPanel.add(brandPanel, java.awt.BorderLayout.NORTH);

        menuPanel.setBackground(new java.awt.Color(27, 43, 67));
        menuPanel.setLayout(new java.awt.GridLayout(5, 1, 0, 8));

        btnBeranda.setBackground(new java.awt.Color(27, 43, 67));
        btnBeranda.setForeground(new java.awt.Color(255, 255, 255));
        btnBeranda.setText("Beranda");
        btnBeranda.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        menuPanel.add(btnBeranda);

        btnKaryawan.setBackground(new java.awt.Color(27, 43, 67));
        btnKaryawan.setForeground(new java.awt.Color(255, 255, 255));
        btnKaryawan.setText("Data Karyawan");
        btnKaryawan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        menuPanel.add(btnKaryawan);

        btnPenilaian.setBackground(new java.awt.Color(27, 43, 67));
        btnPenilaian.setForeground(new java.awt.Color(255, 255, 255));
        btnPenilaian.setText("Data Penilaian");
        btnPenilaian.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        menuPanel.add(btnPenilaian);

        btnRanking.setBackground(new java.awt.Color(27, 43, 67));
        btnRanking.setForeground(new java.awt.Color(255, 255, 255));
        btnRanking.setText("Data Ranking");
        btnRanking.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        menuPanel.add(btnRanking);

        btnLaporan.setBackground(new java.awt.Color(27, 43, 67));
        btnLaporan.setForeground(new java.awt.Color(255, 255, 255));
        btnLaporan.setText("Data Laporan");
        btnLaporan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        menuPanel.add(btnLaporan);

        sidebarPanel.add(menuPanel, java.awt.BorderLayout.CENTER);

        btnLogout.setBackground(new java.awt.Color(27, 43, 67));
        btnLogout.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("Logout");
        sidebarPanel.add(btnLogout, java.awt.BorderLayout.SOUTH);

        add(sidebarPanel, java.awt.BorderLayout.WEST);

        mainPanel.setBackground(new java.awt.Color(241, 245, 250));
        mainPanel.setLayout(new java.awt.BorderLayout());

        headerPanel.setBackground(new java.awt.Color(241, 245, 250));
        headerPanel.setLayout(new java.awt.BorderLayout());

        titleLabel.setForeground(new java.awt.Color(24, 51, 91));
        titleLabel.setText("Dashboard");
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        headerPanel.add(titleLabel, java.awt.BorderLayout.WEST);

        dateLabel.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        dateLabel.setForeground(new java.awt.Color(89, 113, 148));
        dateLabel.setText("Periode Penilaian 2026");
        headerPanel.add(dateLabel, java.awt.BorderLayout.EAST);

        mainPanel.add(headerPanel, java.awt.BorderLayout.NORTH);

        bodyPanel.setBackground(new java.awt.Color(241, 245, 250));
        bodyPanel.setLayout(new java.awt.BorderLayout());

        statsPanel.setBackground(new java.awt.Color(241, 245, 250));
        statsPanel.setLayout(new java.awt.GridLayout(1, 4, 10, 0));

        cardKaryawan.setBackground(new java.awt.Color(255, 255, 255));
        cardKaryawan.setLayout(new java.awt.GridLayout(3, 1));

        lblKaryawanTitle.setForeground(new java.awt.Color(89, 113, 148));
        lblKaryawanTitle.setText("Total Karyawan");
        lblKaryawanTitle.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        cardKaryawan.add(lblKaryawanTitle);

        lblKaryawanValue.setForeground(new java.awt.Color(24, 51, 91));
        lblKaryawanValue.setText("0");
        lblKaryawanValue.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cardKaryawan.add(lblKaryawanValue);

        lblKaryawanNote.setForeground(new java.awt.Color(89, 113, 148));
        lblKaryawanNote.setText("Jumlah karyawan");
        lblKaryawanNote.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        cardKaryawan.add(lblKaryawanNote);

        statsPanel.add(cardKaryawan);

        cardDinilai.setBackground(new java.awt.Color(255, 255, 255));
        cardDinilai.setLayout(new java.awt.GridLayout(3, 1));

        lblDinilaiTitle.setForeground(new java.awt.Color(89, 113, 148));
        lblDinilaiTitle.setText("Sudah Dinilai");
        lblDinilaiTitle.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        cardDinilai.add(lblDinilaiTitle);

        lblDinilaiValue.setForeground(new java.awt.Color(24, 51, 91));
        lblDinilaiValue.setText("0");
        lblDinilaiValue.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cardDinilai.add(lblDinilaiValue);

        lblDinilaiNote.setForeground(new java.awt.Color(89, 113, 148));
        lblDinilaiNote.setText("Karyawan telah dinilai");
        lblDinilaiNote.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        cardDinilai.add(lblDinilaiNote);

        statsPanel.add(cardDinilai);

        cardTerbaik.setBackground(new java.awt.Color(255, 255, 255));
        cardTerbaik.setLayout(new java.awt.GridLayout(3, 1));

        lblTerbaikTitle.setForeground(new java.awt.Color(89, 113, 148));
        lblTerbaikTitle.setText("Karyawan Terbaik");
        lblTerbaikTitle.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        cardTerbaik.add(lblTerbaikTitle);

        lblTerbaikValue.setForeground(new java.awt.Color(24, 51, 91));
        lblTerbaikValue.setText("Belum ada");
        lblTerbaikValue.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cardTerbaik.add(lblTerbaikValue);

        lblTerbaikNote.setForeground(new java.awt.Color(89, 113, 148));
        lblTerbaikNote.setText("Input penilaian dahulu");
        lblTerbaikNote.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        cardTerbaik.add(lblTerbaikNote);

        statsPanel.add(cardTerbaik);

        cardPeriode.setBackground(new java.awt.Color(255, 255, 255));
        cardPeriode.setLayout(new java.awt.GridLayout(3, 1));

        lblPeriodeTitle.setForeground(new java.awt.Color(89, 113, 148));
        lblPeriodeTitle.setText("Periode Penilaian");
        lblPeriodeTitle.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        cardPeriode.add(lblPeriodeTitle);

        lblPeriodeValue.setForeground(new java.awt.Color(24, 51, 91));
        lblPeriodeValue.setText("2026");
        lblPeriodeValue.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cardPeriode.add(lblPeriodeValue);

        lblPeriodeNote.setForeground(new java.awt.Color(89, 113, 148));
        lblPeriodeNote.setText("Januari - Desember");
        lblPeriodeNote.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        cardPeriode.add(lblPeriodeNote);

        statsPanel.add(cardPeriode);

        bodyPanel.add(statsPanel, java.awt.BorderLayout.NORTH);

        lowerPanel.setBackground(new java.awt.Color(241, 245, 250));
        lowerPanel.setLayout(new java.awt.GridLayout(1, 2, 12, 0));

        chartPanel.setBackground(new java.awt.Color(255, 255, 255));
        chartPanel.setLayout(new java.awt.BorderLayout());

        chartTitle.setForeground(new java.awt.Color(24, 51, 91));
        chartTitle.setText("Distribusi Nilai Karyawan");
        chartTitle.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        chartPanel.add(chartTitle, java.awt.BorderLayout.NORTH);

        barPanel.setBackground(new java.awt.Color(255, 255, 255));
        barPanel.setLayout(new java.awt.GridLayout(5, 2));

        barName1.setForeground(new java.awt.Color(24, 51, 91));
        barName1.setText("- Belum ada nilai -");
        barPanel.add(barName1);
        barPanel.add(bar1);

        barName2.setForeground(new java.awt.Color(24, 51, 91));
        barName2.setText("- Belum ada nilai -");
        barPanel.add(barName2);
        barPanel.add(bar2);

        barName3.setForeground(new java.awt.Color(24, 51, 91));
        barName3.setText("- Belum ada nilai -");
        barPanel.add(barName3);
        barPanel.add(bar3);

        barName4.setForeground(new java.awt.Color(24, 51, 91));
        barName4.setText("- Belum ada nilai -");
        barPanel.add(barName4);
        barPanel.add(bar4);

        barName5.setForeground(new java.awt.Color(24, 51, 91));
        barName5.setText("- Belum ada nilai -");
        barPanel.add(barName5);
        barPanel.add(bar5);

        chartPanel.add(barPanel, java.awt.BorderLayout.CENTER);

        lowerPanel.add(chartPanel);

        rankingPanel.setBackground(new java.awt.Color(255, 255, 255));
        rankingPanel.setLayout(new java.awt.BorderLayout());

        rankingTitle.setForeground(new java.awt.Color(24, 51, 91));
        rankingTitle.setText("Peringkat Karyawan Terbaik");
        rankingTitle.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        rankingPanel.add(rankingTitle, java.awt.BorderLayout.NORTH);

        tableScroll.setViewportView(rankingTable);

        rankingPanel.add(tableScroll, java.awt.BorderLayout.CENTER);

        lowerPanel.add(rankingPanel);

        bodyPanel.add(lowerPanel, java.awt.BorderLayout.CENTER);

        mainPanel.add(bodyPanel, java.awt.BorderLayout.CENTER);

        add(mainPanel, java.awt.BorderLayout.CENTER);
    }
    //GEN-END:initComponents

    //GEN-BEGIN:variables
    private javax.swing.JProgressBar bar1;
    private javax.swing.JProgressBar bar2;
    private javax.swing.JProgressBar bar3;
    private javax.swing.JProgressBar bar4;
    private javax.swing.JProgressBar bar5;
    private javax.swing.JLabel barName1;
    private javax.swing.JLabel barName2;
    private javax.swing.JLabel barName3;
    private javax.swing.JLabel barName4;
    private javax.swing.JLabel barName5;
    private javax.swing.JPanel barPanel;
    private javax.swing.JPanel bodyPanel;
    private javax.swing.JLabel brandLabel;
    private javax.swing.JPanel brandPanel;
    private javax.swing.JButton btnBeranda;
    private javax.swing.JButton btnKaryawan;
    private javax.swing.JButton btnLaporan;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnPenilaian;
    private javax.swing.JButton btnRanking;
    private javax.swing.JPanel cardDinilai;
    private javax.swing.JPanel cardKaryawan;
    private javax.swing.JPanel cardPeriode;
    private javax.swing.JPanel cardTerbaik;
    private javax.swing.JPanel chartPanel;
    private javax.swing.JLabel chartTitle;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel lblDinilaiNote;
    private javax.swing.JLabel lblDinilaiTitle;
    private javax.swing.JLabel lblDinilaiValue;
    private javax.swing.JLabel lblKaryawanNote;
    private javax.swing.JLabel lblKaryawanTitle;
    private javax.swing.JLabel lblKaryawanValue;
    private javax.swing.JLabel lblPeriodeNote;
    private javax.swing.JLabel lblPeriodeTitle;
    private javax.swing.JLabel lblPeriodeValue;
    private javax.swing.JLabel lblTerbaikNote;
    private javax.swing.JLabel lblTerbaikTitle;
    private javax.swing.JLabel lblTerbaikValue;
    private javax.swing.JLabel logoLabel;
    private javax.swing.JPanel lowerPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JPanel rankingPanel;
    private javax.swing.JTable rankingTable;
    private javax.swing.JLabel rankingTitle;
    private javax.swing.JPanel sidebarPanel;
    private javax.swing.JPanel statsPanel;
    private javax.swing.JLabel subBrandLabel;
    private javax.swing.JScrollPane tableScroll;
    private javax.swing.JLabel titleLabel;
    //GEN-END:variables

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("PT Artha Utama Plasindo - Dashboard");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new dashboard());
            frame.setSize(1350, 820);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
