import Dashboard.dashboard;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
public class GuiSmokeTest {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                JFrame f = new JFrame("PT Artha Utama Plasindo - UI Smoke Test");
                dashboard d=new dashboard();
                f.setContentPane(d);
                f.setSize(1360,820);
                f.setLocationRelativeTo(null);
                f.setVisible(true);
                d.doLayout();
                int buttons=count(d,JButton.class);
                if(buttons<6) throw new AssertionError("Navigasi tidak lengkap: "+buttons);
                for (Component item: recursively(d)) {
                    if(item instanceof JButton && ((JButton)item).getText().equals("Data Karyawan")){
                        ((JButton)item).doClick(); break;
                    }
                }
                if(count(d,JTable.class)<1)throw new AssertionError("Halaman karyawan gagal dibuka");
                for (Component item: recursively(d)) {
                    if(item instanceof JButton && ((JButton)item).getText().equals("Beranda")){
                        ((JButton)item).doClick(); break;
                    }
                }
                BufferedImage im=new BufferedImage(1360,820,BufferedImage.TYPE_INT_RGB);
                d.printAll(im.createGraphics());
                ImageIO.write(im,"png",new File("preview/Pratinjau_Setelah_Perbaikan.png"));
                f.dispose();
                System.out.println("GUI SMOKE OK: sidebar, navigasi data karyawan, beranda, dan rendering Swing.");
            }catch(Exception e){throw new RuntimeException(e);}
        });
    }
    static java.util.List<Component> recursively(Container root) {
       java.util.ArrayList<Component> all=new java.util.ArrayList<>();
       for(Component x:root.getComponents()) {all.add(x);if(x instanceof Container)all.addAll(recursively((Container)x));}
       return all;
    }
    static int count(Container root,Class<?> type){int n=0;for(Component c:recursively(root))if(type.isInstance(c))n++;return n;}
}
