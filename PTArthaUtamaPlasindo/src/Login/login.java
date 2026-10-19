package Login;

import Dashboard.dashboard;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.URL;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

/**
 * Panel login PT Artha Utama Plasindo.
 * File login.form adalah pasangan GUI Builder untuk kelas ini.
 * Komponen dan layout dapat diedit melalui tab Design di NetBeans 8.2.
 */
public class login extends javax.swing.JPanel {

    private final char defaultEchoChar;
    private Border normalFieldBorder;

    public login() {
        initComponents();
        defaultEchoChar = txtPassword.getEchoChar();
        configureExperience();
    }

    /** Pengaturan interaksi dan detail nonstruktural di luar guarded code. */
    private void configureExperience() {
        normalFieldBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(213, 223, 238), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12));
        final Border focusedFieldBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(49, 94, 234), 2),
                BorderFactory.createEmptyBorder(7, 11, 7, 11));
        for (javax.swing.text.JTextComponent field : new javax.swing.text.JTextComponent[]{txtUsername, txtPassword}) {
            field.setBorder(normalFieldBorder);
            field.addFocusListener(new FocusAdapter() {
                @Override public void focusGained(FocusEvent e) { field.setBorder(focusedFieldBorder); }
                @Override public void focusLost(FocusEvent e) { field.setBorder(normalFieldBorder); }
            });
        }
        loginCard.setBorder(BorderFactory.createLineBorder(new Color(224, 232, 244)));
        btnLogin.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnLogin.setOpaque(true);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogin.setBackground(new Color(49, 94, 234));
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                if (btnLogin.isEnabled()) btnLogin.setBackground(new Color(39, 79, 209));
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                btnLogin.setBackground(new Color(49, 94, 234));
            }
        });
        checkShowPassword.setFocusPainted(false);
        checkShowPassword.setOpaque(false);
        checkShowPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Pertahankan gambar logo asli yang sudah tersedia di folder src/Image.
        // Jika folder Image tidak ada, jangan menyebabkan form crash.
        URL logoResource = getClass().getResource("/Image/logo aup.png");
        if (logoResource != null) {
            ImageIcon raw = new ImageIcon(logoResource);
            java.awt.image.BufferedImage scaled = new java.awt.image.BufferedImage(
                    99, 60, java.awt.image.BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = scaled.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            graphics.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            graphics.drawImage(raw.getImage(), 0, 0, 99, 60, null);
            graphics.dispose();
            labelLogo.setIcon(new ImageIcon(scaled));
        }

        txtUsername.addActionListener(e -> txtPassword.requestFocusInWindow());
        txtPassword.addActionListener(e -> processLogin());
    }

    private void showInlineError(String message) {
        labelMessage.setText(message);
    }

    private void processLogin() {
        String username = txtUsername.getText().trim();
        char[] password = txtPassword.getPassword();
        try {
            if (username.isEmpty()) {
                showInlineError("Username wajib diisi.");
                txtUsername.requestFocusInWindow();
                return;
            }
            if (password.length == 0) {
                showInlineError("Password wajib diisi.");
                txtPassword.requestFocusInWindow();
                return;
            }
            // Kredensial demo sesuai proyek awal. Ganti dengan autentikasi
            // ke database/server sebelum dipakai untuk data produksi.
            if (!"admin".equals(username) || !Arrays.equals(password, "admin123".toCharArray())) {
                showInlineError("Username atau password tidak sesuai.");
                txtPassword.setText("");
                txtPassword.requestFocusInWindow();
                return;
            }
            showInlineError(" ");
            JFrame frame = new JFrame("PT Artha Utama Plasindo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new dashboard());
            frame.setSize(1360, 780);
            frame.setMinimumSize(new Dimension(1030, 680));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            Window loginWindow = SwingUtilities.getWindowAncestor(this);
            if (loginWindow != null) loginWindow.dispose();
        } finally {
            Arrays.fill(password, '\0');
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loginCard = new javax.swing.JPanel();
        labelLogo = new javax.swing.JLabel();
        labelCompany = new javax.swing.JLabel();
        labelTagline = new javax.swing.JLabel();
        labelWelcome = new javax.swing.JLabel();
        labelCaption = new javax.swing.JLabel();
        labelUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        labelPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        checkShowPassword = new javax.swing.JCheckBox();
        btnLogin = new javax.swing.JButton();
        labelMessage = new javax.swing.JLabel();
        labelFooter = new javax.swing.JLabel();

        setBackground(new java.awt.Color(20, 36, 63));

        loginCard.setBackground(new java.awt.Color(255, 255, 255));

        labelLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/logo aup.png"))); // NOI18N

        labelCompany.setFont(new java.awt.Font("SansSerif", 1, 19)); // NOI18N
        labelCompany.setForeground(new java.awt.Color(23, 40, 68));
        labelCompany.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCompany.setText("PT ARTHA UTAMA PLASINDO");

        labelTagline.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        labelTagline.setForeground(new java.awt.Color(102, 117, 140));
        labelTagline.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTagline.setText("PORTAL MANAJEMEN PERUSAHAAN");

        labelWelcome.setFont(new java.awt.Font("SansSerif", 1, 22)); // NOI18N
        labelWelcome.setForeground(new java.awt.Color(23, 40, 68));
        labelWelcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelWelcome.setText("Selamat datang kembali");

        labelCaption.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        labelCaption.setForeground(new java.awt.Color(102, 117, 140));
        labelCaption.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCaption.setText("Silakan masuk untuk melanjutkan aktivitas.");

        labelUsername.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        labelUsername.setForeground(new java.awt.Color(23, 40, 68));
        labelUsername.setText("USERNAME");

        txtUsername.setBackground(new java.awt.Color(246, 249, 253));
        txtUsername.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtUsername.setForeground(new java.awt.Color(23, 40, 68));
        txtUsername.setToolTipText("Masukkan username akun Anda");

        labelPassword.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        labelPassword.setForeground(new java.awt.Color(23, 40, 68));
        labelPassword.setText("PASSWORD");

        txtPassword.setBackground(new java.awt.Color(246, 249, 253));
        txtPassword.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(23, 40, 68));
        txtPassword.setToolTipText("Masukkan password akun Anda");

        checkShowPassword.setBackground(new java.awt.Color(255, 255, 255));
        checkShowPassword.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        checkShowPassword.setForeground(new java.awt.Color(102, 117, 140));
        checkShowPassword.setText("Tampilkan password");
        checkShowPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkShowPasswordActionPerformed(evt);
            }
        });

        btnLogin.setBackground(new java.awt.Color(49, 94, 234));
        btnLogin.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("MASUK KE DASHBOARD  →");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        labelMessage.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        labelMessage.setForeground(new java.awt.Color(197, 48, 72));
        labelMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelMessage.setText(" ");

        labelFooter.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        labelFooter.setForeground(new java.awt.Color(102, 117, 140));
        labelFooter.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelFooter.setText("UNIVERSITAS INDRAPRASTA");

        javax.swing.GroupLayout loginCardLayout = new javax.swing.GroupLayout(loginCard);
        loginCard.setLayout(loginCardLayout);
        loginCardLayout.setHorizontalGroup(
            loginCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(loginCardLayout.createSequentialGroup()
                .addGroup(loginCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(loginCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(loginCardLayout.createSequentialGroup()
                            .addGap(47, 47, 47)
                            .addGroup(loginCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(labelUsername)
                                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelPassword)
                                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(checkShowPassword)
                                .addComponent(labelMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(loginCardLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(loginCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(labelWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelTagline, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(loginCardLayout.createSequentialGroup()
                            .addGap(25, 25, 25)
                            .addGroup(loginCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(labelFooter, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelCaption, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, loginCardLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labelCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginCardLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(labelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(166, 166, 166))
        );
        loginCardLayout.setVerticalGroup(
            loginCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginCardLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(labelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelTagline, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelCaption, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(labelPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(checkShowPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(labelMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelFooter, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(295, Short.MAX_VALUE)
                .addComponent(loginCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(265, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(loginCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        processLogin();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void checkShowPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkShowPasswordActionPerformed
        txtPassword.setEchoChar(checkShowPassword.isSelected() ? (char) 0 : defaultEchoChar);
    }//GEN-LAST:event_checkShowPasswordActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JCheckBox checkShowPassword;
    private javax.swing.JLabel labelCaption;
    private javax.swing.JLabel labelCompany;
    private javax.swing.JLabel labelFooter;
    private javax.swing.JLabel labelLogo;
    private javax.swing.JLabel labelMessage;
    private javax.swing.JLabel labelPassword;
    private javax.swing.JLabel labelTagline;
    private javax.swing.JLabel labelUsername;
    private javax.swing.JLabel labelWelcome;
    private javax.swing.JPanel loginCard;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
