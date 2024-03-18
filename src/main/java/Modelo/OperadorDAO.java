package Modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OperadorDAO {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public String obtenerCertificado(int id) {
                
        // Devuelve el nombre el archivo de certificado de firma digital para el usuario 
        // con el id ingresado como parámetro

        String resultado = "";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement("SELECT certificado FROM operadores WHERE id=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                resultado = rs.getString("certificado");
            }
        } catch (Exception e) {
            System.out.println("Error en la búsqueda del certificado");
            System.out.println(e.toString());
            resultado = "error";
        }
        cn.Desconectar();
        return resultado;
    }
    
}

  

