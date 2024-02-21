package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MotivoDAO {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public String buscar(String id) {
        // Devuelve el nombre de un motivo a partir de su Id
        String sql = "SELECT nombre FROM motivos where id="+id;
        String nombre = "";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {                
                nombre = rs.getString("nombre");                
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        cn.Desconectar();
        return nombre;
    }
    
}
