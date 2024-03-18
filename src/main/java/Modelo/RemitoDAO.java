package Modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RemitoDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean guardar(Remito remito) {
        boolean resultado = false;
        String sql = "INSERT INTO documentos (documento) VALUES (?)";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setBlob(1, new FileInputStream(remito.getArchivoPDF()));
            ps.executeUpdate();
            resultado = true;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        cn.Desconectar();
        return resultado;
    }

    public Remito buscar(String id) {

        Remito remito = new Remito();
        String sql = "SELECT documento FROM documentos WHERE id =" + id;
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                remito.setId(Integer.parseInt(rs.getString("id")));
                InputStream inputStream = rs.getBinaryStream("documento");
                File archivoTemp = File.createTempFile("temp", ".pdf");
                FileOutputStream fos = new FileOutputStream(archivoTemp); 
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
                remito.setArchivoPDF(archivoTemp);
                inputStream.close();
                rs.close();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        cn.Desconectar();
        return remito;
    }
    
    public String buscarNumero() {
        String maxRemito = "";
        String sql = "SELECT MAX(documento) AS max_remito FROM movimientos WHERE estado=2;";
        try {
            con = cn.Conexion();
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                maxRemito = rs.getString("max_remito");
                System.out.println("Maximo remito obtenido:" + maxRemito);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        cn.Desconectar();
        String numero = maxRemito.substring(1,9).replaceFirst("^0+", "");
        System.out.println("Número extraído:" + numero);
        int proximoRemito = Integer.parseInt(numero) + 1;
        return String.valueOf(proximoRemito);        
    }

    public String buscarNumero2() {
        String numero = "";
        String sql = "SHOW TABLE STATUS LIKE 'documentos';";
        try {
            con = cn.Conexion();
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                numero = rs.getString("Auto_increment");
                System.out.println("Autoincrement obtenido:" + numero);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        cn.Desconectar();
        return numero;
    }

    public boolean existe(String nroRendicion) {
        // Verifica si una determinada rendición existe en la BD
        boolean resultado = false;        
        String sql = "SELECT COUNT(*) AS CANTIDAD FROM movimientos WHERE documento=?;";
        try {
            con = cn.Conexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nroRendicion+".pdf");
            rs = ps.executeQuery();
            if (rs.next()) {
                int cantidad = rs.getInt("CANTIDAD");
                resultado = cantidad > 0;
            }            
        } catch (Exception e) {
            System.out.println(e.toString());
        } 
        cn.Desconectar();
        return resultado;                
    }

}
