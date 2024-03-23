package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UbicacionDAO {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List listar() {
        // Devuelve la lista de ubicaciones en el arraylist list
        String sql = "SELECT * FROM ubicaciones";
        List<Ubicacion> lista = new ArrayList<>();
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Ubicacion e = new Ubicacion();
                e.setId(rs.getInt("id"));
                e.setNombre(rs.getString("nombre"));
                lista.add(e);
            }

        } catch (Exception e) {
            System.out.println("Error en la lista de ubicaciones");
            System.out.println(e.toString());
        }

        cn.Desconectar();
        return lista;
    }
    
    public List listarCorreos() {
        // Devuelve la lista de ubicaciones en el arraylist list
        String sql = "SELECT * FROM ubicaciones where tipo=2";
        List<Ubicacion> lista = new ArrayList<>();
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Ubicacion e = new Ubicacion();
                e.setId(rs.getInt("id"));
                e.setNombre(rs.getString("nombre"));
                lista.add(e);
            }

        } catch (Exception e) {
            System.out.println("Error en la lista de ubicaciones");
            System.out.println(e.toString());
        }

        cn.Desconectar();
        return lista;
    }
    
    public String buscar(String id) {
         // Devuelve el nombre de una ubicación a partir de su Id
        String sql = "SELECT nombre FROM ubicaciones where id=?";
        String nombre = "";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {                
                nombre = rs.getString("nombre");                
            }
        } catch (Exception e) {
            System.out.println("Error en la búsqueda del nombre de la ubicación");
            System.out.println(e.toString());
        }

        cn.Desconectar();
        return nombre;
    }

    public String obtenerEmail(String id) {
        // Devuelve el nombre de una ubicación a partir de su Id
        String sql = "SELECT email FROM ubicaciones where id="+id;
        String email = null;
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {                
                email = rs.getString("email");                
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        cn.Desconectar();
        return email;
    }
    
}
