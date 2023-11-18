package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
// import java.sql.SQLException;

public class Conexion {
    Connection con;
    String bd="tarjetas";
    String url="jdbc:mysql://localhost:1433/+" + bd +"?serverTimeZone=UTC";
    String user="root";
    String pass="mindys10";
          
    public Connection Conexion() {
        // Método constructor de la clase, devuelve una conexión
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,user, pass);
            // if (con!=null) System.out.println("Conexión exitosa");
        } catch (Exception e) {
            System.out.println(e.toString());
            con = null;
        }
        return con;
    }
   
    public void Desconectar() {
        // Cierra la conexión
        try {
            if(con!=null) con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
