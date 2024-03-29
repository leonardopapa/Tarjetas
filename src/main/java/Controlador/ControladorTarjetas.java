package Controlador;

import Modelo.Conexion;
import Modelo.Estado;
import Modelo.EstadoDAO;
import Modelo.Movimiento;
import Modelo.MovimientoDAO;
import Modelo.Operador;
import Modelo.Reporte;
import Modelo.ReporteDAO;
import Modelo.TarjetaDAO;
import Modelo.Ubicacion;
import Modelo.UbicacionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControladorTarjetas extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("Llamada a servlet no valida");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = "";

        // Obtener los parámetros del formulario
        Enumeration<String> parametros = request.getParameterNames();
        List<String> piezas = new ArrayList();
        List<String> cuentas = new ArrayList();
        List<String> fechas = new ArrayList();
        List<String> estados = new ArrayList();

        // Procesar los parámetros
        while (parametros.hasMoreElements()) {
            String parametro = parametros.nextElement();
            if (parametro.startsWith("pieza")) {
                piezas.add(request.getParameter(parametro));
            } else if (parametro.startsWith("cuenta")) {
                cuentas.add(request.getParameter(parametro));
            } else if (parametro.startsWith("fecha")) {
                String fechaFormateada;
                try {
                    fechaFormateada = convertirFecha(request.getParameter(parametro));
                    fechas.add(fechaFormateada);
                } catch (Exception e) {
                    System.out.println("Error en la conversión de fechas");
                    System.out.println(e.getMessage());
                }
            } else if (parametro.startsWith("accion")) {
                accion = request.getParameter("accion");
            } else if (parametro.startsWith("resultado")) {
                estados.add(request.getParameter(parametro));
            }
        }

        //Mostrar la cantidad de parámetros recibidos
        /*
        System.out.println("Piezas:" + piezas.size());
        System.out.println("Cuentas:" + cuentas.size());
        System.out.println("Fechas:" + fechas.size());
         */
        //Mostrar los parámetros recibidos
        /*
        
        System.out.println("Datos recibidos:");        
        for (int i = 0; i < piezas.size(); i++) {
            System.out.println( i + " - Cuenta: " + cuentas.get(i) + " // Pieza: " + piezas.get(i)+ " // Fecha: " + fechas.get(i) + " // Estado: " + estados.get(i) + " // Motivo:" + motivos.get(i) );
        }
        System.out.println("Cantidad=" + piezas.size());
         */
        switch (accion) {
            case "capturar":

                int exitos = 0;
                int fracasos = 0;

                // Verificar que no haya piezas duplicadas
                TarjetaDAO td = new TarjetaDAO();
                String duplicados = td.buscar(piezas);
                String result = "";
                if (duplicados.equalsIgnoreCase("ok")) {

                    // Iterar sobre los datos y guardarlos en la base de datos
                    List<Movimiento> lista = new ArrayList<Movimiento>();
                    for (int i = 0; i < piezas.size(); i++) {
                        Movimiento movimiento = new Movimiento();
                        Estado estado = new Estado();
                        estado.setId(1); // Impresa
                        movimiento.setMovimiento(estado);
                        Operador operador = new Operador();
                        operador.setId(11); // Operador ficticio
                        MovimientoDAO mdao = new MovimientoDAO();
                        movimiento.setCliente(Integer.parseInt(cuentas.get(i)));
                        movimiento.setPieza(Integer.parseInt(piezas.get(i)));
                        movimiento.setFecha(Date.valueOf(fechas.get(i)));
                        movimiento.setOperador(operador);
                        lista.add(movimiento);
                        boolean resultado = mdao.agregarNuevo(movimiento);
                        if (resultado) {
                            exitos++;
                        } else {
                            fracasos++;
                        }
                    }
                    request.setAttribute("lista", lista);

                    System.out.println("Resultados de la captura de tarjetas:");
                    System.out.println("Exitos=" + exitos);
                    System.out.println("Fracasos=" + fracasos);

                    result = fracasos == 0 ? "OK. " + exitos + " piezas grabada/as con éxito" : "Se grabaron con éxito " + exitos + "piezas y fallaron " + fracasos;

                } else {
                    result = "La pieza " + result + " se encuentra duplicada. No se grabaron los cambios";
                }

                System.out.println("Resultado:" + result);
                request.setAttribute("resultado", result);
                request.getRequestDispatcher("capturar.jsp").forward(request, response);
                break;

            case "consultar":
                // Generar lista de movimientos.                
                String cuenta = request.getParameter("bcuenta");
                MovimientoDAO mdao = new MovimientoDAO();
                List<Movimiento> lista = mdao.listar(cuenta);
                request.setAttribute("mlista", lista);
                request.setAttribute("cuentab", cuenta);
                request.getRequestDispatcher("consultar.jsp").forward(request, response);
                break;

            case "reportes":
                String reporte = request.getParameter("reporte");
                String desde = request.getParameter("desde");
                String hasta = request.getParameter("hasta");
                String correo4 = request.getParameter("correo");

                System.out.println("Reporte:" + reporte);

                String sql = "";
                switch (reporte) {
                    case "1":
                        sql = "CALL tarjetas_x_estado_impos('" + desde + "', '" + hasta + "')";
                        break;
                    case "2":
                        sql = "0".equalsIgnoreCase(correo4) ? "CALL motivos2('" + desde + "', '" + hasta + "')" : "CALL motivos('" + desde + "', '" + hasta + "', " + correo4 + ")";
                        break;
                    case "3":
                        sql = "0".equalsIgnoreCase(correo4) ? "CALL rapidez('" + desde + "', '" + hasta + "')" : "CALL rapidez_correo('" + desde + "', '" + hasta + "', " + correo4 + ")";
                        break;
                    case "4":
                        sql = "0".equalsIgnoreCase(correo4) ? "CALL efectiv('" + desde + "', '" + hasta + "')" : "CALL efectiv_correo('" + desde + "', '" + hasta + "', " + correo4 + ")";
                        break;
                }

                ReporteDAO rdao = new ReporteDAO();
                List<ArrayList<Object>> report = rdao.generarReporte(sql);

                // Generar lista de reportes
                List<Reporte> reportes = new ArrayList();
                reportes.add(new Reporte("0", "Seleccione un reporte"));
                reportes.add(new Reporte("1", "Tarjetas Impuestas por Estado"));
                reportes.add(new Reporte("2", "Motivos de Rechazo"));
                reportes.add(new Reporte("3", "Rapidez en la Entrega"));
                reportes.add(new Reporte("4", "Valocidad en la Entrega"));

                String reporten = null;
                for (int i = 0; i < reportes.size(); i++) {
                    if (reportes.get(i).getId().equalsIgnoreCase(reporte)) {
                        reporten = reportes.get(i).getNombre();
                        break;
                    }
                }

                UbicacionDAO correosDao = new UbicacionDAO();
                String nombreCorreo = correosDao.buscar(correo4);

                System.out.println("Nombre correo: " + nombreCorreo);
                System.out.println("Nombre reporte: " + reporten);

                // Devolver valores a la capa de presentación
                request.setAttribute("nombrec", nombreCorreo);
                request.setAttribute("reporten", reporten);
                request.setAttribute("reporte", report);
                request.setAttribute("correo", correo4);
                request.setAttribute("desde", desde);
                request.setAttribute("hasta", hasta);
                request.setAttribute("nreporte", reporte);
                request.getRequestDispatcher("reportes.jsp").forward(request, response);
                break;

            case "dashboard":
                String desde2 = request.getParameter("desde");
                String hasta2 = request.getParameter("hasta");
                String correo3 = request.getParameter("correo");
                System.out.println("Desde:" + desde2);
                System.out.println("Hasta:" + hasta2);
                System.out.print("Correo:");
                System.out.println(correo3.isEmpty() ? "vacío" : correo3);
                Conexion cn2 = new Conexion();
                Connection con2 = cn2.Conexion();
                PreparedStatement ps2;
                ResultSet rs2;

                // Obtener valores de efectividad
                String sql2 = correo3.isEmpty() ? "CALL efectiv (?, ?)" : "CALL efectiv_correo (?, ?, ?)";
                List<String> meses = new ArrayList<>();
                List<String> efectividad = new ArrayList<>();
                try {
                    ps2 = con2.prepareStatement(sql2);
                    ps2.setDate(1, Date.valueOf(desde2));
                    ps2.setDate(2, Date.valueOf(hasta2));
                    if (!(correo3.isEmpty())) {
                        ps2.setInt(3, Integer.valueOf(correo3));
                    }
                    rs2 = ps2.executeQuery();
                    while (rs2.next()) {
                        meses.add(rs2.getString("mes"));
                        efectividad.add(rs2.getString("porcentaje_entregadas"));
                    }
                } catch (Exception e) {
                    System.out.println("Error en la consulta SQL de efectividad");
                    System.out.println(e.toString());
                }

                // Obtener valores de rapidez
                sql2 = correo3.isEmpty() ? "CALL rapidez (?, ?)" : "CALL rapidez_correo (?, ?, ?)";
                List<String> meses2 = new ArrayList<>();
                List<String> rapidez = new ArrayList<>();
                try {
                    ps2 = con2.prepareStatement(sql2);
                    ps2.setDate(1, Date.valueOf(desde2));
                    ps2.setDate(2, Date.valueOf(hasta2));
                    if (!(correo3.isEmpty())) {
                        ps2.setInt(3, Integer.valueOf(correo3));
                    }
                    rs2 = ps2.executeQuery();
                    while (rs2.next()) {
                        meses2.add(rs2.getString("mes"));
                        rapidez.add(rs2.getString("rapidez"));
                    }
                } catch (Exception e) {
                    System.out.println("Error en la consulta SQL de rapidez");
                    System.out.println(e.toString());
                }

                // Convertir los arreglos a JSON 
                String json1 = listToJSON(meses);
                String json2 = listToJSON(efectividad);
                String json3 = listToJSON(meses2);
                String json4 = listToJSON(rapidez);

                // Combininar los arreglos JSON en un único objeto JSON
                String jsonResponse = String.format("{\"meses\":%s,\"efectividad\":%s,\"meses2\":%s,\"rapidez\":%s}",
                        json1, json2, json3, json4);

                // Set content type and write the JSON response
                response.setContentType("application/json");
                response.getWriter().write(jsonResponse);
                break;

            case "buscarCambiar":
                String pieza = request.getParameter("pieza");
                String resultado3;
                TarjetaDAO tdao = new TarjetaDAO();
                String fechaEmision = tdao.buscar(pieza, "fecha_emision");
                String estadoId = tdao.buscar(pieza, "estado");
                String cuenta1 = tdao.buscar(pieza, "cliente");
                EstadoDAO estDAO = new EstadoDAO();
                String estado = estDAO.buscar(estadoId);
                if (cuenta1.isEmpty()) {
                    resultado3 = "no encontrado";
                } else {
                    resultado3 = "encontrado";
                }
                // Combinar las respuestas en un único objeto JSON
                String jsonResponse2 = String.format("{\"resultado\":\"%s\",\"cuenta\":\"%s\",\"fechaEmision\":\"%s\",\"estado\":\"%s\",\"estadoid\":\"%s\"}",
                        resultado3, cuenta1, fechaEmision, estado, estadoId);

                // Devolver la respuesta en formato JSON al cliente
                response.setContentType("application/json");
                response.getWriter().write(jsonResponse2);
                break;

            case "buscarEnviar":
                // Llamado desde enviar.jsp
                // Busca una pieza y devuelve la fecha de emisión y el número de cuenta.
                // En caso de no encontrarse, devuelve "no encontrado" en ambos casos

                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                String pieza2 = piezas.get(0);
                TarjetaDAO tdao2 = new TarjetaDAO();
                String resultado2 = tdao2.buscar(pieza2);
                if (resultado2.isEmpty()) {
                    out.println("no encontrado, no encontrado");
                } else {
                    out.println(resultado2);
                }

                break;

            case "verificar":
                // Verificar si una pieza se encuenta en estado "En Distribución" para un determinado "correo"
                // Si se encuentra devuelve el número de cuenta. Caso contrario devuelve "no encontrado"
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out2 = response.getWriter();
                String pieza3 = piezas.get(0);
                String correo = request.getParameter("correo");
                TarjetaDAO tdao3 = new TarjetaDAO();
                String resultado4 = tdao3.buscar(pieza3, "2", correo);
                if (resultado4.isEmpty()) {
                    out2.println("no encontrado");
                } else {
                    out2.println(tdao3.buscarCuenta(pieza3));
                }
                break;

            case "cambiar":

                // Validar que todos los estados sean iguales
                boolean cambioValido = true;
                String estadoActual = estados.get(0);
                System.out.println("Estado actual: " + estadoActual);
                for (int i = 1; i < piezas.size(); i++) {
                    if (!estados.get(i).equals(estadoActual)) {
                        request.setAttribute("accion", "error");
                        request.setAttribute("error", "Los estados no son iguales");
                        System.out.println("Estados no son iguales");
                        cambioValido = false;
                    }
                }

                // Validar que el nuevo estado sea válido, dado el estado actual
                if (cambioValido) {
                    cambioValido = false;
                    String nuevoEstado = request.getParameter("selEstado");
                    System.out.println("Nuevo estado: " + nuevoEstado);

                    if ((nuevoEstado.equals("7") || nuevoEstado.equals("8") || nuevoEstado.equals("5") || nuevoEstado.equals("6")) && estadoActual.equals("4")) {
                        cambioValido = true;
                        // "En Sucursal" a "Entregada", "Reenvío" o "Destruida" o "Enviada a Sucursal"
                    }
                    if (nuevoEstado.equals("6") && (estadoActual.equals("1") || estadoActual.equals("3"))) {
                        cambioValido = true;
                        // "Devuelta" o "Impresa" a "Enviada a Sucursal"
                    }
                    if (nuevoEstado.equals("4") && estadoActual.equals("6")) {
                        cambioValido = true;
                        // "Enviada a Sucursal" a "En Sucursal"
                    }
                    if (!(cambioValido)) {
                        request.setAttribute("accion", "error");
                        request.setAttribute("error", "Fallo en la precedencia de los estados");
                        System.out.println("Fallo en la precedencia de los estados");

                    } else {

                        // Registrar el cambio
                        System.out.println("El cambio es válido");
                        System.out.println("Cambio válido:" + cambioValido);
                        String nuevaUbicacion = request.getParameter("selUbicacion");
                        String fecha = request.getParameter("fcambio");
                        int exitos2 = 0;
                        int fracasos2 = 0;
                        for (int i = 0; i < piezas.size(); i++) {
                            Movimiento m = new Movimiento();
                            m.setPieza(Integer.parseInt(piezas.get(i)));
                            m.setCliente(Integer.parseInt(cuentas.get(i)));
                            m.setFecha(Date.valueOf(fecha));
                            Estado estado2 = new Estado();
                            estado2.setId(Integer.parseInt(nuevoEstado));
                            m.setMovimiento(estado2);
                            Ubicacion ubicacion2 = new Ubicacion();
                            ubicacion2.setId(Integer.parseInt(nuevaUbicacion));
                            m.setUbicacion(ubicacion2);
                            Operador operador = new Operador();
                            operador.setId(11); // Operador ficticio                    
                            m.setOperador(operador);
                            MovimientoDAO mdao2 = new MovimientoDAO();
                            boolean resultado = mdao2.agregarCambiar(m);
                            if (resultado) {
                                exitos2++;
                            } else {
                                fracasos2++;
                            }
                        }
                        System.out.println("Exitos: " + exitos2);
                        System.out.println("Fracasos: " + fracasos2);

                        request.setAttribute("accion", "ok");
                    }
                }
                request.getRequestDispatcher("cambiar.jsp").forward(request, response);

            default:
                System.out.println("No se especificó una acción válida");
                break;
        }
    }

    private <T> String listToJSON(List<T> list) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            json.append("\"").append(list.get(i)).append("\"");
            if (i < list.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    private String convertirFecha(String fechaOrigen) throws ParseException {
        SimpleDateFormat formatoOrigen = new SimpleDateFormat("dd/MM/yyyy");
        // Convertir la fecha de origen de String a Date
        java.util.Date fechaOrigenDate = formatoOrigen.parse(fechaOrigen);
        // Crear un objeto SimpleDateFormat con el patrón de la fecha de destino
        SimpleDateFormat formatoDestino = new SimpleDateFormat("yyy-MM-dd");
        // Convertir la fecha de Date a String en el formato de destino
        return formatoDestino.format(fechaOrigenDate);
    }

    private String convertirFecha2(String fechaOrigen) throws ParseException {
        SimpleDateFormat formatoOrigen = new SimpleDateFormat("yyy-MM-dd");
        // Convertir la fecha de origen de String a Date
        java.util.Date fechaOrigenDate = formatoOrigen.parse(fechaOrigen);
        // Crear un objeto SimpleDateFormat con el patrón de la fecha de destino
        SimpleDateFormat formatoDestino = new SimpleDateFormat("dd/MM/yyyy");
        // Convertir la fecha de Date a String en el formato de destino
        return formatoDestino.format(fechaOrigenDate);
    }
}
