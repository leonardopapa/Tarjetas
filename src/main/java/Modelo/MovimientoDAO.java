package Modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MovimientoDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean agregarNuevo(Movimiento movimiento) {
        // Agregar una nueva pieza en estado impresa
        con = cn.Conexion();
        boolean resultado = false;
        String sql = "INSERT INTO movimientos (FECHA, TARJETA, PIEZA, ESTADO, OPERADOR) VALUES(?,?, ?, 1, ?);";
        try {
            ps = con.prepareStatement(sql);
            ps.setDate(1, movimiento.getFecha());
            ps.setInt(2, movimiento.getCliente());
            ps.setInt(3, movimiento.getPieza());
            ps.setInt(4, movimiento.getOperador().getId());
            int filasAfectadas = ps.executeUpdate();
            resultado = filasAfectadas > 0;
            System.out.println("Resultado movimiento:" + resultado);
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error al agregar el movimiento");
            return resultado;
        }
        cn.Desconectar();
        return resultado;
    }

    public boolean agregarEnviar(Movimiento movimiento) {
        // Agregar un movimiento "en distribución"
        con = cn.Conexion();
        boolean resultado = false;
        String sql = "INSERT INTO movimientos (FECHA, TARJETA, ESTADO, UBICACION, OPERADOR, DOCUMENTO, PIEZA) VALUES(?,?, ?, ?, ?, ?, ?);";
        try {
            ps = con.prepareStatement(sql);
            ps.setDate(1, movimiento.getFecha());
            ps.setInt(2, movimiento.getCliente());
            ps.setInt(3, movimiento.getMovimiento().getId());
            ps.setInt(4, movimiento.getUbicacion().getId());
            ps.setInt(5, movimiento.getOperador().getId());
            ps.setString(6, movimiento.getDocumento());
            ps.setInt(7, movimiento.getPieza());
            int filasAfectadas = ps.executeUpdate();
            resultado = (filasAfectadas > 0);
            System.out.println("Resultado movimiento:" + resultado);
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error al agregar el movimiento");
            return resultado;
        } finally {
            cn.Desconectar();
        }
        return resultado;
    }

    public boolean agregarRecibir(Movimiento movimiento) {
        // Agrega un movimiento recibir con motivo de devolución
        con = cn.Conexion();
        boolean resultado = false;
        String sql = "INSERT INTO movimientos (FECHA, TARJETA, ESTADO, UBICACION, MOTIVO, OPERADOR,DOCUMENTO, PIEZA) VALUES(?,?, ?, ?, ?, ?, ?, ?);";

        System.out.println("Movimiento a agregar:");
        System.out.println("Fecha:" + movimiento.getFecha());
        System.out.println("Cuenta: " + movimiento.getCliente());
        System.out.println("Movimiento:" + movimiento.getMovimiento().getId());
        System.out.println("Ubicacion:" + movimiento.getUbicacion().getId());
        System.out.println("Motivo: " + movimiento.getMotivo().getId());
        System.out.println("Operador: " + movimiento.getOperador().getId());
        System.out.println("Documento: " + movimiento.getDocumento());

        try {
            ps = con.prepareStatement(sql);
            ps.setDate(1, movimiento.getFecha());
            ps.setInt(2, movimiento.getCliente());
            ps.setInt(3, movimiento.getMovimiento().getId());
            ps.setInt(4, movimiento.getUbicacion().getId());
            ps.setInt(5, movimiento.getMotivo().getId());
            ps.setInt(6, movimiento.getOperador().getId());
            ps.setString(7, movimiento.getDocumento());
            ps.setInt(8, movimiento.getPieza());
            int filasAfectadas = ps.executeUpdate();
            resultado = (filasAfectadas > 0);
            System.out.println("Resultado movimiento:" + resultado);
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error al agregar el movimiento");
            return resultado;
        } finally {
            cn.Desconectar();
        }
        return resultado;
    }

    public boolean agregarRecibir2(Movimiento movimiento) {
        // Agrega un movimiento recibir sin motivo de devolución
        con = cn.Conexion();
        boolean resultado = false;
        String sql = "INSERT INTO movimientos (FECHA, TARJETA, ESTADO, UBICACION, OPERADOR, DOCUMENTO, PIEZA) VALUES(?,?, ?, ?, ?, ?, ?);";

        System.out.println("Movimiento a agregar:");
        System.out.println(movimiento.getFecha());
        System.out.println(movimiento.getCliente());
        System.out.println(movimiento.getMovimiento().getId());
        System.out.println(movimiento.getUbicacion().getId());
        System.out.println(movimiento.getOperador().getId());
        System.out.println(movimiento.getDocumento());

        try {
            ps = con.prepareStatement(sql);
            ps.setDate(1, movimiento.getFecha());
            ps.setInt(2, movimiento.getCliente());
            ps.setInt(3, movimiento.getMovimiento().getId());
            ps.setInt(4, movimiento.getUbicacion().getId());
            ps.setInt(5, movimiento.getOperador().getId());
            ps.setString(6, movimiento.getDocumento());
            ps.setInt(7, movimiento.getPieza());
            int filasAfectadas = ps.executeUpdate();
            resultado = (filasAfectadas > 0);
            System.out.println("Resultado movimiento:" + resultado);
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error al agregar el movimiento");
            return resultado;
        } finally {
            cn.Desconectar();
        }
        return resultado;
    }

    public boolean agregarCambiar(Movimiento movimiento) {
        // Modifica el estado de una pieza
        con = cn.Conexion();
        boolean resultado = false;
        String sql = "INSERT INTO movimientos (FECHA, TARJETA, PIEZA, ESTADO, UBICACION, OPERADOR) VALUES(?,?, ?, ?, ?, ?);";

        System.out.println("Movimiento a agregar:");
        System.out.println(movimiento.getFecha());
        System.out.println(movimiento.getCliente());
        System.out.println(movimiento.getPieza());
        System.out.println(movimiento.getMovimiento().getId());
        System.out.println(movimiento.getUbicacion().getId());
        System.out.println(movimiento.getOperador().getId());

        try {
            ps = con.prepareStatement(sql);
            ps.setDate(1, movimiento.getFecha());
            ps.setInt(2, movimiento.getCliente());
            ps.setInt(3, movimiento.getPieza());
            ps.setInt(4, movimiento.getMovimiento().getId());
            ps.setInt(5, movimiento.getUbicacion().getId());
            ps.setInt(6, movimiento.getOperador().getId());
            int filasAfectadas = ps.executeUpdate();
            resultado = (filasAfectadas > 0);
            System.out.println("Resultado movimiento:" + resultado);
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error al agregar el movimiento");
            return resultado;
        } finally {
            cn.Desconectar();
        }
        return resultado;
    }

    public List listar(String cuenta) {
        // Devuelve la lista de movimeinto de una cuenta
        String sql = "SELECT * FROM vista_movimientos WHERE cuenta=" + cuenta;
        List<Movimiento> lista = new ArrayList<>();
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Movimiento movimiento = new Movimiento();
                Estado estado = new Estado();
                Motivo motivo = new Motivo();
                Ubicacion ubicacion = new Ubicacion();
                Operador operador = new Operador();
                operador.setNombre(rs.getString("operador"));
                movimiento.setOperador(operador);
                movimiento.setDocumento(rs.getString("documento"));
                if (movimiento.getDocumento()==null) movimiento.setDocumento("");
                movimiento.setCliente(rs.getInt("cuenta"));
                movimiento.setPieza(rs.getInt("pieza"));
                movimiento.setFecha(rs.getDate("fecha"));
                estado.setNombre(rs.getString("estado"));
                movimiento.setMovimiento(estado);
                motivo.setNombre(rs.getString("motivo"));
                if (motivo.getNombre() == null) motivo.setNombre("");                
                movimiento.setMotivo(motivo);
                ubicacion.setNombre(rs.getString("ubicacion"));
                if (ubicacion.getNombre() == null) ubicacion.setNombre("");
                movimiento.setUbicacion(ubicacion);                
                lista.add(movimiento);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        cn.Desconectar();
        return lista;
    }

}
