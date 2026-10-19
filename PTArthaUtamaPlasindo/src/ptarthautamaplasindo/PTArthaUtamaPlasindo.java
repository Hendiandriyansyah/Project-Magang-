
package ptarthautamaplasindo;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import Login.login;

public class PTArthaUtamaPlasindo {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                JFrame frame = new JFrame(
                    "PT ARTHA UTAMA PLASINDO"
                );

                // Memasukkan form login ke dalam JFrame
                frame.setContentPane(new login());

                // Pengaturan jendela
                frame.setDefaultCloseOperation(
                    JFrame.EXIT_ON_CLOSE
                );

                frame.setSize(950, 600);
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);

                // Menampilkan form login
                frame.setVisible(true);
            }
        });
    }
}
