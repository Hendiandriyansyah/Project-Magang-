package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/** Opsional untuk integrasi MySQL; aplikasi utama memakai DataStore lokal, tanpa JDBC. */
public final class Koneksi {
    private Koneksi() {}
    public static Connection getKoneksi() throws SQLException {
        String url=System.getProperty("artha.db.url", "jdbc:mysql://localhost:3306/db_artha_utama_plasindo?useSSL=false");
        String user=System.getProperty("artha.db.user","root");
        String pass=System.getProperty("artha.db.password","");
        return DriverManager.getConnection(url,user,pass);
    }
    public static void tutupKoneksi(Connection c) throws SQLException {if(c!=null)c.close();}
}
