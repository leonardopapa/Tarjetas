package Controlador;

import Modelo.Conexion;
import Modelo.Estado;
import Modelo.Motivo;
import Modelo.Movimiento;
import Modelo.MovimientoDAO;
import Modelo.Operador;
import Modelo.Reporte;
import Modelo.Ubicacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControladorTarjetas extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
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

        PrintWriter out = response.getWriter();
        String accion = "";

        // Obtener los parámetros del formulario
        Enumeration<String> parametros = request.getParameterNames();

        // Arrays para almacenar cuentas y fechas
        String[] cuentas = new String[10]; // Tamaño arbitrario, puedes ajustarlo según tus necesidades
        String[] fechas = new String[10];  // Tamaño arbitrario, puedes ajustarlo según tus necesidades
        String[] motivos = new String[10];  // Tamaño arbitrario, puedes ajustarlo según tus necesidades
        String[] estados = new String[10];  // Tamaño arbitrario, puedes ajustarlo según tus necesidades

        int indiceCuentas = 0;
        int indiceFechas = 0;

        // Procesar los parámetros
        while (parametros.hasMoreElements()) {
            String parametro = parametros.nextElement();

            // Verificar si el parámetro es de tipo "cuenta", "fecha", "accion, "motivo" o "resultado"
            if (parametro.startsWith("cuenta")) {
                cuentas[indiceCuentas++] = request.getParameter(parametro);
            } else if (parametro.startsWith("fecha")) {
                String fechaFormateada;
                try {
                    fechaFormateada = convertirFecha(request.getParameter(parametro));
                    fechas[indiceFechas++] = fechaFormateada;
                } catch (ParseException ex) {
                    Logger.getLogger(ControladorTarjetas.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (parametro.startsWith("accion")) {
                accion = request.getParameter("accion");
            } else if (parametro.startsWith("motivo")) {
                motivos[indiceCuentas - 1] = request.getParameter(parametro);
            } else if (parametro.startsWith("resultado")) {
                estados[indiceCuentas - 1] = request.getParameter(parametro);
            }
        }

        //Mostrar los resultados
        /*
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Resultado</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Cuentas:</h2>");
        out.println("<ul>");
         */
        for (int i = 0; i < indiceCuentas; i++) {
            out.println("<li> " + i + " - Cuenta: " + cuentas[i] + " // Fecha: " + fechas[i] + " // Estado: " + estados[i] + " // Motivo:" + motivos[i] + "</li>");
        }

        /*
        out.println("</ul>");
        out.println("Cantidad=" + indiceCuentas);
         */
        switch (accion) {
            case "capturar":
                int exitos = 0;
                int fracasos = 0;

                // Iterar sobre los datos y guardarlos en la base de datos
                for (int i = 0; i < indiceCuentas; i++) {
                    out.println("<br> Indice:" + i);
                    Movimiento movimiento = new Movimiento();
                    Estado estado = new Estado();
                    estado.setId(1); // Impresa
                    movimiento.setMovimiento(estado);
                    Operador operador = new Operador();
                    operador.setId(11); // Operador ficticio
                    MovimientoDAO mdao = new MovimientoDAO();
                    movimiento.setCliente(Integer.parseInt(cuentas[i]));
                    movimiento.setFecha(Date.valueOf(fechas[i]));
                    movimiento.setOperador(operador);
                    boolean resultado = mdao.agregarNuevo(movimiento);
                    if (resultado) {
                        exitos++;

                    } else {
                        fracasos++;
                    }
                }
                request.setAttribute("exitos", exitos);
                request.setAttribute("fracasos", fracasos);
                /*
                out.println("<br> Exitos=" + exitos);
                out.println("<br> Fracasos=" + fracasos);
                out.println("</body>");
                out.println("</html>");
                 */

                // out.println("<script>alert('Datos grabados');</script>");
                request.getRequestDispatcher("Controlador2").forward(request, response);

            case "enviar":
                out.println("Enviar");
                String correo = request.getParameter("correo");
                String fechaEnvio = request.getParameter("fenvio");
                out.println("Correo: " + correo);
                out.println("fechaEnvio: " + fechaEnvio);

                // Iterar sobre los datos y guardarlos en la base de datos
                boolean resultado = false;
                for (int i = 0; i < indiceCuentas; i++) {
                    out.println("Indice:" + i);
                    Movimiento movimiento = new Movimiento();
                    Estado estado = new Estado();
                    estado.setId(2); // En Distribución
                    movimiento.setMovimiento(estado);
                    Operador operador = new Operador();
                    operador.setId(11); // Operador ficticio
                    Ubicacion correo2 = new Ubicacion();
                    correo2.setId(Integer.parseInt(correo));
                    out.println("Llegué hasta aqui 0");
                    MovimientoDAO mdao = new MovimientoDAO();
                    movimiento.setCliente(Integer.parseInt(cuentas[i]));
                    out.println("Llegué hasta aqui 1");
                    movimiento.setFecha(Date.valueOf(fechaEnvio));
                    out.println("Llegué hasta aqui 2");
                    movimiento.setOperador(operador);
                    out.println("Llegué hasta aqui 3");
                    movimiento.setUbicacion(correo2);
                    out.println("Llegué hasta aqui 4");
                    resultado = mdao.agregarEnviar(movimiento);
                }
                request.setAttribute("resultado", resultado);
                /*
                out.println("<br> Exitos=" + exitos);
                out.println("<br> Fracasos=" + fracasos);
                out.println("</body>");
                out.println("</html>");
                 */

                out.println("<script>alert('Datos grabados');</script>");

                request.getRequestDispatcher("Controlador2").forward(request, response);
                break;

            case "consultar":
                // Generar lista de movimientos.                
                String cuenta = request.getParameter("bcuenta");
                MovimientoDAO mdao = new MovimientoDAO();
                List<Movimiento> lista = mdao.listar(cuenta);
                request.setAttribute("mlista", lista);
                request.setAttribute("cuentab", cuenta);
                request.getRequestDispatcher("consultar2.jsp").forward(request, response);
                break;

            case "recibir":
                out.println("RECIBIR");
                String correo2 = request.getParameter("correo");
                String fechaRendicion = request.getParameter("frend");
                String nroRendicion = request.getParameter("nrend");
                out.println("Correo: " + correo2);
                out.println("Fecha de Rendición: " + fechaRendicion);
                out.println("Numero de Rendicion: " + nroRendicion);

                // Iterar sobre los datos y guardarlos en la base de datos
                boolean resultado2 = false;
                for (int i = 0; i < indiceCuentas; i++) {
                    out.println("Indice:" + i);
                    Movimiento movimiento = new Movimiento();
                    Estado estado = new Estado();
                    estado.setId(Integer.parseInt(estados[i])); // Entregada o devuelta 
                    movimiento.setMovimiento(estado);
                    Operador operador = new Operador();
                    operador.setId(11); // Operador ficticio
                    Ubicacion correo3 = new Ubicacion();
                    correo3.setId(Integer.parseInt(correo2));
                    //out.println("Llegué hasta aqui 0");
                    MovimientoDAO mdao3 = new MovimientoDAO();
                    movimiento.setCliente(Integer.parseInt(cuentas[i]));
                    //out.println("Llegué hasta aqui 1");
                    movimiento.setFecha(Date.valueOf(fechaRendicion));
                    //out.println("Llegué hasta aqui 2");
                    movimiento.setOperador(operador);
                    //out.println("Llegué hasta aqui 3");
                    movimiento.setUbicacion(correo3);
                    // out.println("Llegué hasta aqui 4");

                    if (!(motivos[i].isEmpty())) {
                        out.println("Motivo no vacío:" + i);
                        Motivo motivo = new Motivo();
                        motivo.setId(Integer.parseInt(motivos[i]));
                        movimiento.setMotivo(motivo);
                        resultado2 = mdao3.agregarRecibir(movimiento);
                    } else {
                        out.println("Motivo vacío:" + i);
                        resultado2 = mdao3.agregarRecibir2(movimiento);
                    }

                }
                request.setAttribute("resultado", resultado2);

                out.println("<script>alert('Datos grabados');</script>");

                request.getRequestDispatcher("Controlador2").forward(request, response);
                break;

            case "reportes":
                String reporte = request.getParameter("reporte");
                String desde = request.getParameter("desde");
                String hasta = request.getParameter("hasta");
                Conexion cn = new Conexion();
                Connection con;
                PreparedStatement ps;
                ResultSet rs;
                con = cn.Conexion();
                String sql = "";
                List<String> titulos = new ArrayList();
                List<Reporte> reporte3 = new ArrayList();
                String columna1 = "";
                String columna2 = "cantidad";
                if (reporte.equalsIgnoreCase("1")) //tarjetas por estado
                {
                    sql = "CALL tarjetas_x_estado_impos(?,?)";
                    titulos.add("Estado");
                    titulos.add("Cantidad");
                    columna1 = "estado";
                } else if (reporte.equalsIgnoreCase("2")) //motivos de rechazo
                {
                    sql = "CALL motivos(?,?)";
                    titulos.add("Motivo");
                    titulos.add("Cantidad");
                    columna1 = "causa";
                }
                try {
                    ps = con.prepareStatement(sql);
                    ps.setDate(1, Date.valueOf(desde));
                    ps.setDate(2, Date.valueOf(hasta));
                    rs = ps.executeQuery();

                    // Convertir el resultset a lista
                    try {
                        while (rs.next()) {
                            Reporte reporteLinea = new Reporte();
                            reporteLinea.setDescripcion(rs.getString(columna1));
                            reporteLinea.setCantidad(rs.getInt(columna2));
                            reporte3.add(reporteLinea);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace(); // Maneja la excepción de manera adecuada en tu aplicación.
                    } finally {
                        // Cierra el ResultSet si es necesario.
                        try {
                            if (rs != null) {
                                rs.close();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    System.out.println(e.toString());
                    System.out.println("Error al generar el reporte");
                    response.setStatus(500);
                    return;
                }
                cn.Desconectar();
                request.setAttribute("reporte", reporte3);
                request.setAttribute("titulos", titulos);
                response.setStatus(200);
                request.getRequestDispatcher("reportes2.jsp").forward(request, response);
                break;

            case "dashboard":
                String desde2 = request.getParameter("desde");
                String hasta2 = request.getParameter("hasta");
                //out.println("Desde:" + desde2);
                //out.println("Hasta:" + hasta2);
                Conexion cn2 = new Conexion();
                Connection con2 = cn2.Conexion();
                PreparedStatement ps2;
                ResultSet rs2;

                // Obtener valores de efectividad
                String sql2 = "select * from efectividad";
                List<String> meses = new ArrayList<>();
                List<String> efectividad = new ArrayList<>();                
                try {
                    ps2 = con2.prepareStatement(sql2);
                    rs2 = ps2.executeQuery();
                    while (rs2.next()) {
                        meses.add(rs2.getString("mes"));
                        efectividad.add(rs2.getString("porcentaje_entregadas"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                             
                // Obtener valores de rapidez
                sql2 = "SELECT * FROM tarjetas.rapidez_x_fecha_imposicion";
                List<String> meses2= new ArrayList<>();
                List<String> rapidez = new ArrayList<>();
                try {
                    ps2 = con2.prepareStatement(sql2);
                    rs2 = ps2.executeQuery();
                    while (rs2.next()) {
                        meses2.add(rs2.getString("mes"));
                        rapidez.add(rs2.getString("rapidez"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // Convertir los arreglos a JSON 
                String json1 = listToJSON(meses);
                String json2 = listToJSON(efectividad);
                String json3 = listToJSON(meses2);
                String json4 = listToJSON(rapidez);

                //out.println("json1:" + json1);
                //out.println("json2:" + json2);
                //out.println("json3:" + json3);
                //out.println("json4:" + json4);

                // Combininar los arreglos JSON en un único objeto JSON
                String jsonResponse = String.format("{\"meses\":%s,\"efectividad\":%s,\"meses2\":%s,\"rapidez\":%s}",
                        json1, json2, json3, json4);

                //out.println("jsonresponse:" + jsonResponse);

                // Set content type and write the JSON response
                response.setContentType("application/json");
                response.getWriter().write(jsonResponse);
                // request.getRequestDispatcher("dashboard.jsp").forward(request, response);
                break;

            default:
                out.println("No se especificó una acción válida");
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

    protected String convertirFecha(String fechaOrigen) throws ParseException {
        SimpleDateFormat formatoOrigen = new SimpleDateFormat("dd/MM/yyyy");
        // Convertir la fecha de origen de String a Date
        java.util.Date fechaOrigenDate = formatoOrigen.parse(fechaOrigen);
        // Crear un objeto SimpleDateFormat con el patrón de la fecha de destino
        SimpleDateFormat formatoDestino = new SimpleDateFormat("yyy-MM-dd");
        // Convertir la fecha de Date a String en el formato de destino
        return formatoDestino.format(fechaOrigenDate);
    }
}
