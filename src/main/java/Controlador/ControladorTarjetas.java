package Controlador;

import Modelo.Estado;
import Modelo.Motivo;
import Modelo.Movimiento;
import Modelo.MovimientoDAO;
import Modelo.Operador;
import Modelo.Tarjeta;
import Modelo.TarjetaDAO;
import Modelo.Ubicacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        switch (accion) {

            case "capturaruno":
                /*
                String cuenta3 = request.getParameter("inputCuenta");
                String fecha3 = request.getParameter("inputFecha");
                Tarjeta tarjeta3 = new Tarjeta();
                Estado estado3 = new Estado();
                estado3.setId(1); // Impresa
                TarjetaDAO tdao3 = new TarjetaDAO();
                tarjeta3.setCliente(Integer.parseInt(cuenta3));
                tarjeta3.setFechaEmision(Date.valueOf(fecha3));
                tarjeta3.setEstado(estado3);
                boolean resultado3 = tdao3.agregar(tarjeta3);
                request.setAttribute("resultado", resultado3);

                request.getRequestDispatcher("capturar.jsp").forward(request, response);
                 */
                break;

            case "capturar":
                /*
                String[] cuentas = request.getParameterValues("cuentas[]");
                String[] fechas = request.getParameterValues("fechas[]");
                Tarjeta tarjeta = new Tarjeta();
                Estado estado = new Estado();
                estado.setId(1); // Impresa
                tarjeta.setEstado(estado);
                TarjetaDAO tdao = new TarjetaDAO();
                boolean resultado = false;
                int exitos = 0;
                int fracasos = 0;

                // Iterar sobre los datos y guardarlos en la base de datos
                for (int i = 0; i < cuentas.length; i++) {
                    tarjeta.setCliente(Integer.parseInt(cuentas[i]));
                    tarjeta.setFechaEmision(Date.valueOf(fechas[i]));
                    resultado = tdao.agregar(tarjeta);
                    if (resultado) {
                        exitos++;
                    } else {
                        fracasos++;
                    }
                }
                request.setAttribute("exitos", exitos);
                request.setAttribute("fracasos", fracasos);
                request.getRequestDispatcher("capturar.jsp").forward(request, response);
                 */
                break;

            case "borrar":
                String cuenta2 = request.getParameter("inputCuenta");
                String fecha2 = request.getParameter("inputFecha");
                Tarjeta tarjeta2 = new Tarjeta();
                TarjetaDAO tdao2 = new TarjetaDAO();
                tarjeta2.setCliente(Integer.parseInt(cuenta2));
                tarjeta2.setFechaEmision(Date.valueOf(fecha2));
                boolean resultado2 = tdao2.borrar(tarjeta2);
                request.setAttribute("resultado", resultado2);

                request.getRequestDispatcher("capturar.jsp").forward(request, response);
                break;

            case "cambiar":
                request.getRequestDispatcher("cambiar.jsp").forward(request, response);
                break;

            case "consultar":
                request.getRequestDispatcher("consultar.jsp").forward(request, response);
                break;

            case "dashboard":
                request.getRequestDispatcher("dashboard.jsp").forward(request, response);
                break;

            case "enviar":
                request.getRequestDispatcher("enviar.jsp").forward(request, response);
                break;

            case "recibir":
                request.getRequestDispatcher("cambiar.jsp").forward(request, response);
                break;

            case "reportes":
                /*
                String desde = request.getParameter("fecha-desde");
                String hasta = request.getParameter("fecha-hasta");
                String reporte = request.getParameter("sel-reporte");
                String respuesta = "";
                switch (reporte) {
                    case "0":
                        request.setAttribute("tipoError", "Debe seleccionar un reporte de la lista");
                        respuesta = "reportes/error-reporte.jsp";
                        break;
                    case "1":
                        if (desde.isEmpty() || hasta.isEmpty()) {
                            request.setAttribute("tipoError", "Debe indicar fechas de inicio y fin");
                            respuesta = "reportes/error-reporte.jsp";
                        } else {
                            ReporteProspectoPorEstadoDAO reporteDAO = new ReporteProspectoPorEstadoDAO();
                            List<ReporteProspectoPorEstado> reporteFinal = reporteDAO.listar(Date.valueOf(desde), Date.valueOf(hasta));
                            request.setAttribute("reporte", reporteFinal);
                            respuesta = "reportes/reporte_prospectos_por_estado.jsp";
                        }
                        break;
                }
                
                request.getRequestDispatcher(respuesta).forward(request, response);
                 */
                break;

            default:
                out.println("No se especificó una acción válida");
                break;

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        String accion ="";

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
                motivos[indiceCuentas-1] = request.getParameter(parametro);
            } else if (parametro.startsWith("resultado")) {
                estados[indiceCuentas-1] = request.getParameter(parametro);
            }
        }

        //Mostrar los resultados
        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Resultado</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Cuentas:</h2>");
        out.println("<ul>");

        for (int i = 0; i < indiceCuentas; i++) {
            out.println("<li> " + i + " - Cuenta: " + cuentas[i] + " // Fecha: " + fechas[i] + " // Estado: " +estados[i] +" // Motivo:"+ motivos[i] +"</li>");
        }

        out.println("</ul>");

        out.println("Cantidad=" + indiceCuentas);
        
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

                out.println("<script>alert('Datos grabados');</script>");

                request.getRequestDispatcher("Controlador2").forward(request, response);
                
            case "enviar":
                out.println("Enviar");
                String correo = request.getParameter("correo");
                String fechaEnvio = request.getParameter("fenvio");
                out.println("Correo: "+correo);
                out.println("fechaEnvio: "+fechaEnvio);
                
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
                out.println("Correo: "+correo2);
                out.println("Fecha de Rendición: "+fechaRendicion);
                out.println("Numero de Rendicion: "+ nroRendicion);
                                
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
                /*
                out.println("<br> Exitos=" + exitos);
                out.println("<br> Fracasos=" + fracasos);
                out.println("</body>");
                out.println("</html>");
                 */

                out.println("<script>alert('Datos grabados');</script>");

                request.getRequestDispatcher("Controlador2").forward(request, response);
                break;

            default:
                out.println("No se especificó una acción válida");
                break;

        }

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
