package ui;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public final class Styles {
    public static final Color NAVY=new Color(25,41,68);
    public static final Color BLUE=new Color(36,91,211);
    public static final Color BG=new Color(241,245,251);
    public static final Color INK=new Color(28,49,80);
    public static final Color MUTED=new Color(104,122,147);
    private Styles() { }
    public static void button(JButton b, boolean primary) {
        b.setFont(new Font("SansSerif",Font.BOLD,12));
        b.setForeground(primary?Color.WHITE:INK);
        b.setBackground(primary?BLUE:new Color(233,239,248));
        b.setBorder(BorderFactory.createEmptyBorder(11,15,11,15));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
    public static void table(JTable t) {
        t.setFont(new Font("SansSerif",Font.PLAIN,13));
        t.setForeground(INK);
        t.setSelectionBackground(new Color(220,232,252));
        t.setSelectionForeground(INK);
        t.setRowHeight(38);
        t.setShowGrid(false);
        t.setFillsViewportHeight(true);
        t.getTableHeader().setReorderingAllowed(false);
        t.getTableHeader().setPreferredSize(new Dimension(10,40));
        t.getTableHeader().setFont(new Font("SansSerif",Font.BOLD,12));
    }
    public static void heading(JLabel title, JLabel desc) {
        title.setFont(new Font("SansSerif",Font.BOLD,23));title.setForeground(INK);
        desc.setFont(new Font("SansSerif",Font.PLAIN,12));desc.setForeground(MUTED);
    }
    public static void error(Component parent,Exception e) {
        JOptionPane.showMessageDialog(parent,e.getMessage(),"Operasi gagal",JOptionPane.ERROR_MESSAGE);
    }
}
