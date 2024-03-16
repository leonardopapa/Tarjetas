package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EstadoDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List listar() {
        // Devuelve la lista de estados en el arraylist list
        String sql = "SELECT * FROM estados";
        List<Estado> lista = new ArrayList<>();
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Estado e = new Estado();
                e.setId(rs.getInt("id"));
                e.setNombre(rs.getString("nombre"));
                lista.add(e);
            }

        } catch (Exception e) {
            System.out.println("Error en la lista de estados");
            System.out.println(e.toString());
        }

        cn.Desconectar();
        return lista;
    }
    
    public String buscar(String id) {
        // Devuelve el nombre de un estado a partir de su Id
        String sql = "SELECT nombre FROM estados where id=?";
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
            System.out.println("Error en la búsqueda del nombre del estado");
            System.out.println(e.toString());
        }

        cn.Desconectar();
        return nombre;
    }
    
}
