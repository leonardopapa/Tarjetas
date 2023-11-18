package Modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TarjetaDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean agregar(Tarjeta tarjeta) {
            con = cn.Conexion();
            boolean resultado = false;
            String sql = "INSERT INTO tarjetas (CLIENTE, FECHA_EMISION, ESTADO, FECHA_ESTADO) VALUES(?,?, ?, ?);";
            
            int  cuenta = tarjeta.getCliente();
            Date fecha = tarjeta.getFechaEmision();
            int estado = tarjeta.getEstado().getId();            
            //String sql2 = "INSERT INTO tarjetas (CLIENTE, FECHA_EMISION, ESTADO, FECHA_ESTADO) VALUES("+cuenta+",'"+fecha+"',"+estado+",'"+fecha+"');";
            //System.out.println(sql2);
            
            try {
                ps = con.prepareStatement(sql);
                ps.setInt(1,cuenta);
                ps.setDate(2,fecha);
                ps.setInt(3,estado);                
                ps.setDate(4,fecha);
                resultado = ps.execute();
            } catch (Exception e) {          
                System.out.println(e.toString());                
                System.out.println("Error al agregar la tarjeta");
                return resultado;         
            }            
            cn.Desconectar();
            return resultado;
        }     
    
    public boolean borrar(Tarjeta tarjeta) {
            boolean resultado = false;
            String sql = "DELETE FROM tarjetas WHERE CLIENTE=? AND FECHA_EMISION=?";
            try {
                ps = con.prepareStatement(sql);
                ps.setInt(1,tarjeta.getCliente());
                ps.setDate(2,tarjeta.getFechaEmision());
                resultado = ps.execute();
            } catch (Exception e) {          
                System.out.println(e.toString());
                System.out.println("Error al borrar la tarjeta");               
                return resultado;
            }
            cn.Desconectar();
            return resultado;
        }
    
    public List listar() {
        // Devuelve la lista de tarjetas en el arraylist list
        String sql = "SELECT * FROM vista_tarjetas";
        List<Tarjeta> lista = new ArrayList<>();
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Tarjeta tarjeta = new Tarjeta();
                Estado estado = new Estado();
                Motivo motivo = new Motivo();
                Ubicacion ubicacion = new Ubicacion();                
                tarjeta.setCliente(rs.getInt("cliente"));
                tarjeta.setFechaEmision(rs.getDate("fecha_emision"));
                estado.setNombre(rs.getString("estado"));
                tarjeta.setEstado(estado);
                tarjeta.setFechaEstado(rs.getDate("fecha_estado"));
                motivo.setNombre(rs.getString("motivo"));
                if (motivo.getNombre()==null) motivo.setNombre("");
                tarjeta.setMotivo(motivo);
                ubicacion.setNombre(rs.getString("ubicacion"));
                if (ubicacion.getNombre()==null) ubicacion.setNombre("");
                tarjeta.setUbicacion(ubicacion);                
                lista.add(tarjeta);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        cn.Desconectar();
        return lista;
    }
    
    public String buscar(String cuenta) {
        
        String resultado="";        
        try {
            // Registrar la solicitud
            con = cn.Conexion();
            ps = con.prepareStatement("SELECT * FROM tarjetas WHERE cuit=? and estado=1");
            ps.setInt(1, Integer.parseInt(cuenta));            
            rs = ps.executeQuery();
            
            if (rs.next()) resultado = rs.getString("fecha_emision");            
            
        } catch (Exception e) {
            System.out.println(e.toString());
            resultado="error";
        }
        cn.Desconectar();        
        return resultado;
    }
}
    
 