package Controlador;

import Modelo.Estado;
import Modelo.Tarjeta;
import Modelo.TarjetaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
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
                
            case "buscarFechaEmison":
                String cuenta = request.getParameter("cuenta");
                TarjetaDAO tdao = new TarjetaDAO();
                String resultado = tdao.buscar(cuenta);
                // out.println("resultado = "+resultado);
                if (resultado.isEmpty()) {
                    request.setAttribute("fechaEmision", "no encontrado");
                    out.println("no encontrado");
                } else {
                    request.setAttribute("fechaEmision", resultado);
                    out.println(resultado);
                }
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

        // Obtener los parámetros del formulario
        Enumeration<String> parametros = request.getParameterNames();

        // Arrays para almacenar cuentas y fechas
        String[] cuentas = new String[10]; // Tamaño arbitrario, puedes ajustarlo según tus necesidades
        String[] fechas = new String[10];  // Tamaño arbitrario, puedes ajustarlo según tus necesidades

        int indiceCuentas = 0;
        int indiceFechas = 0;

        // Procesar los parámetros
        while (parametros.hasMoreElements()) {
            String parametro = parametros.nextElement();

            // Verificar si el parámetro es de tipo "cuenta" o "fecha"
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
            }
        }

        // Mostrar los resultados
        /*
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Resultado</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Cuentas:</h2>");
        out.println("<ul>");

        for (int i = 0; i < indiceCuentas; i++) {
            out.println("<li>" + i + ": " + cuentas[i] + " // " + fechas[i] + "</li>");
        }

        out.println("</ul>");

        out.println("Cantidad=" + indiceCuentas);
        */

        int exitos = 0;
        int fracasos = 0;

        // Iterar sobre los datos y guardarlos en la base de datos
        for (int i = 0; i < indiceCuentas; i++) {
            out.println("<br> Indice:" + i);
            Tarjeta tarjeta = new Tarjeta();
            Estado estado = new Estado();
            estado.setId(1); // Impresa
            tarjeta.setEstado(estado);
            
            TarjetaDAO tdao = new TarjetaDAO();
            
            tarjeta.setCliente(Integer.parseInt(cuentas[i]));
            
            tarjeta.setFechaEmision(Date.valueOf(fechas[i]));
            
            out.println(tarjeta.toString());
            boolean resultado = tdao.agregar(tarjeta);
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
