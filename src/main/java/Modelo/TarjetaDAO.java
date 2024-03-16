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

        int cuenta = tarjeta.getCliente();
        Date fecha = tarjeta.getFechaEmision();
        int estado = tarjeta.getEstado().getId();
        //String sql2 = "INSERT INTO tarjetas (CLIENTE, FECHA_EMISION, ESTADO, FECHA_ESTADO) VALUES("+cuenta+",'"+fecha+"',"+estado+",'"+fecha+"');";
        //System.out.println(sql2);

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cuenta);
            ps.setDate(2, fecha);
            ps.setInt(3, estado);
            ps.setDate(4, fecha);
            resultado = ps.execute();
            System.out.println("Resultado:" + resultado);
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
            ps.setInt(1, tarjeta.getCliente());
            ps.setDate(2, tarjeta.getFechaEmision());
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
                if (motivo.getNombre() == null) {
                    motivo.setNombre("");
                }
                tarjeta.setMotivo(motivo);
                ubicacion.setNombre(rs.getString("ubicacion"));
                if (ubicacion.getNombre() == null) {
                    ubicacion.setNombre("");
                }
                tarjeta.setUbicacion(ubicacion);
                lista.add(tarjeta);
            }

        } catch (Exception e) {
            System.out.println("Error en el método listar tarjetas");
            System.out.println(e.toString());
        }

        cn.Desconectar();
        return lista;
    }

    public String buscar(List<String>piezas) {
        
        // Buscar si la pieza que se desea ingresar, existe ya en la base de datos
        // Devuelve "ok" si no se encuentra.
        // Devuelve el número de le pieza, si se encuentra

        String resultado = "ok";
        con = cn.Conexion();
        try {
            String sql = "SELECT pieza FROM tarjetas WHERE pieza IN (";
            for (int i = 0; i < piezas.size(); i++) {
                if (i > 0) {
                    sql += ",";
                }
                sql += "?";
            }
            sql += ")";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                resultado = rs.getString("pieza");
                System.out.println("Valor coincidente encontrado: " + resultado);
                cn.Desconectar();
                return resultado;
            }
        } catch (Exception e) {
            System.out.println("Error en la búsqueda de pieza");
            System.out.println(e.toString());
            resultado = "error";
        }
        cn.Desconectar();
        return resultado;
    }

    public String buscar(String cuenta) {

        String resultado = "";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement("SELECT * FROM tarjetas WHERE cliente=? and (estado=1 or estado=5)");
            ps.setInt(1, Integer.parseInt(cuenta));
            rs = ps.executeQuery();

            if (rs.next()) {
                resultado = rs.getString("fecha_emision");
            }

        } catch (Exception e) {
            System.out.println(e.toString());
            resultado = "error";
        }
        cn.Desconectar();
        return resultado;
    }

    public String buscar2(String cuenta, String estado, String atributo) {
        String resultado = "";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement("SELECT * FROM tarjetas WHERE cliente=? and estado=" + estado);
            ps.setInt(1, Integer.parseInt(cuenta));
            rs = ps.executeQuery();

            if (rs.next()) {
                resultado = rs.getString(atributo);
                System.out.println("Resultado obtenido: " + resultado);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
            resultado = "error";
        }
        cn.Desconectar();
        return resultado;
    }

    public String buscar(String cuenta, String estado, String correo) {
        String resultado = "";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement("SELECT * FROM tarjetas WHERE cliente=? and estado=? and ubicacion=?");
            ps.setInt(1, Integer.parseInt(cuenta));
            ps.setInt(2, Integer.parseInt(estado));
            ps.setInt(3, Integer.parseInt(correo));
            rs = ps.executeQuery();

            if (rs.next()) {
                resultado = "encontrado";
                System.out.println("Se encontró la tarjeta " + cuenta + " en distribución para el correo " + correo);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
            resultado = "error";
        }
        cn.Desconectar();
        return resultado;
    }

    public String buscar3(String cuenta, String atributo) {
        String resultado = "";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement("SELECT * FROM tarjetas WHERE cliente=?");
            ps.setInt(1, Integer.parseInt(cuenta));
            rs = ps.executeQuery();

            if (rs.next()) {
                resultado = rs.getString(atributo);
                // System.out.println("Resultado obtenido: "+resultado);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
            resultado = "error";
        }
        cn.Desconectar();
        return resultado;
    }

    public List<Tarjeta> filtrar(String cuenta, String estado, String ubicacion, String desde, String hasta) {

        // Devuelve la lista de tarjetas filtrada en el arraylist list
        String select = "SELECT * FROM vista_tarjetas WHERE ";
        String buscarCuenta = (cuenta.isEmpty()) ? "" : "cliente = " + cuenta + " AND ";
        String buscarEstado = (estado.isEmpty()) ? "" : "estado_id = " + estado + " AND ";
        String buscarUbicacion = (ubicacion.isEmpty()) ? "" : "ubicacion_id = " + ubicacion + " AND ";
        String buscarFecha = "";
        if (desde == null) {
            if (!(hasta == null)) {
                buscarFecha = "fecha_estado < '" + hasta + "'";
            }
        } else {
            if (hasta == null) {
                buscarFecha = "fecha_estado > '" + desde + "'";
            } else {
                buscarFecha = "fecha_estado BETWEEN '" + desde + "' AND '" + hasta + "'";
            }
        }
        String sql = select + buscarCuenta + buscarEstado + buscarUbicacion + buscarFecha;
        if (sql.endsWith("AND ")) {
            sql = sql.substring(0, sql.length() - 4);
        }
        System.out.println(sql);
        List<Tarjeta> lista = new ArrayList<>();
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            int regcount = 0;
            while (rs.next()) {
                regcount++;
                Tarjeta tarjeta = new Tarjeta();
                Estado estado2 = new Estado();
                Motivo motivo = new Motivo();
                Ubicacion ubicacion2 = new Ubicacion();
                tarjeta.setCliente(rs.getInt("cliente"));
                tarjeta.setFechaEmision(rs.getDate("fecha_emision"));
                estado2.setNombre(rs.getString("estado"));
                tarjeta.setEstado(estado2);
                tarjeta.setFechaEstado(rs.getDate("fecha_estado"));
                tarjeta.setPieza(rs.getInt("pieza"));
                motivo.setNombre(rs.getString("motivo"));
                if (motivo.getNombre() == null) {
                    motivo.setNombre("");
                }
                tarjeta.setMotivo(motivo);
                ubicacion2.setNombre(rs.getString("ubicacion"));
                if (ubicacion2.getNombre() == null) {
                    ubicacion2.setNombre("");
                }
                tarjeta.setUbicacion(ubicacion2);
                lista.add(tarjeta);
            }
            System.out.println("Registros encontrados: " + regcount);

        } catch (Exception e) {
            System.out.println("Error en la consulta SQL de filtrado");
            System.out.println(e.toString());
        }

        cn.Desconectar();
        return lista;
    }

}
