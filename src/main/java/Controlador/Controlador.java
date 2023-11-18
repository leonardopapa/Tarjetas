package Controlador;

import Modelo.Conexion;
import Modelo.Estado;
import Modelo.Ubicacion;
import Modelo.EstadoDAO;
import Modelo.Tarjeta;
import Modelo.TarjetaDAO;
import Modelo.UbicacionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controlador extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        PrintWriter out = response.getWriter();
        switch (accion) {

            case "testconeccion":

                Conexion cn = new Conexion();
                if (cn == null) {
                    out.println(
                            "Error en la conexion");
                } else {
                    out.println("Conexion exitosa");
                    
                    // Generar lista de estados
                    EstadoDAO edao = new EstadoDAO();
                    List<Estado> listae = edao.listar();
                    request.setAttribute("elista", listae);
                    
                    // Mostrar lista de estados
                    for (Estado valor : listae) {
                        out.println("Id: " + valor.getId());
                        out.println("Nombre: " + valor.getNombre());
                    }

                    // Generar lista de ubicaciones
                    UbicacionDAO udao = new UbicacionDAO();
                    List<Ubicacion> listau = udao.listar();
                    request.setAttribute("ulista", listau);
                    
                    // Mostrar lista de ubicaciones
                    for (Ubicacion valor : listau) {
                        out.println("Id: " + valor.getId());
                        out.println("Nombre: " + valor.getNombre());
                    }
                                        
                    // Generar lista de tarjetas
                    TarjetaDAO tdao = new TarjetaDAO();
                    List<Tarjeta> listat = tdao.listar();
                    request.setAttribute("tlista", listat);
                    
                    // Mostrar lista de tarjetas
                    for (Tarjeta valort : listat) {
                        out.println("Cuenta: " + valort.getCliente());
                        out.println("Fecha de Emisión: " +valort.getFechaEmision());
                    }
                }
                // request.getRequestDispatcher("index.jsp").forward(request, response);
                break;
                
            case "prueba":
                // Generar lista de estados
                    EstadoDAO edao = new EstadoDAO();
                    List<Estado> listae = edao.listar();
                    request.setAttribute("elista", listae);
                    
                    // Mostrar lista de estados
                    for (Estado valor : listae) {
                        out.println("Id: " + valor.getId());
                        out.println("Nombre: " + valor.getNombre());
                    }

                    // Generar lista de ubicaciones
                    UbicacionDAO udao = new UbicacionDAO();
                    List<Ubicacion> listau = udao.listar();
                    request.setAttribute("ulista", listau);
                    
                    // Mostrar lista de ubicaciones
                    for (Ubicacion valor : listau) {
                        out.println("Id: " + valor.getId());
                        out.println("Nombre: " + valor.getNombre());
                    }
                                        
                    // Generar lista de tarjetas
                    TarjetaDAO tdao = new TarjetaDAO();
                    List<Tarjeta> listat = tdao.listar();
                    request.setAttribute("tlista", listat);
                    
                    // Mostrar lista de tarjetas
                    for (Tarjeta valort : listat) {
                        out.println("Cuenta: " + valort.getCliente());
                        out.println("Fecha de Emisión: " +valort.getFechaEmision());
                    }
                break;
                            
            case "iniciar":

                out.println("Variables cargadas");

                request.setAttribute("respuesta", 200);


            request.getRequestDispatcher("index.jsp").forward(request, response);
            break;

            case "inicializar":

                /*

                //Obtener la página de origen
                // String origen = (String) request.getAttribute("origen");
                // Generar lista de estados
                EstadoDAO edao = new EstadoDAO();
                List<Estado> listae = edao.listar();

                // request.setAttribute("elista", listae);
                // Generar lista de ubicaciones
                UbicacionDAO udao = new UbicacionDAO();
                List<Ubicacion> listau = udao.listar();

                // request.setAttribute("ulista", listau);
                ListaDeRespuestas listaRespuesta = new ListaDeRespuestas(listae, listau);

                ObjectMapper objectMapper = new ObjectMapper();
                String jsonString = objectMapper.writeValueAsString(listaRespuesta);

                // Configurar la respuesta HTTP
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonString);

                out.println(jsonString);

                //String respuesta = origen+".jsp";
                //out.println(respuesta);
                 */
                request.getRequestDispatcher("index.jsp").forward(request, response);

                break;

            case "capturar":
                request.getRequestDispatcher(
                        "capturar.jsp").forward(request, response);
                break;

            case "consultar":
                request.getRequestDispatcher(
                        "consultar.jsp").forward(request, response);
                break;

            case "dashboard":
                request.getRequestDispatcher(
                        "dashboard.jsp").forward(request, response);
                break;

            case "enviar":
                request.getRequestDispatcher(
                        "enviar.jsp").forward(request, response);
                break;

            case "recibir":
                request.getRequestDispatcher(
                        "cambiar.jsp").forward(request, response);
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
                out.println(
                        "No se especificó una acción válida");

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
        processRequest(request, response);
    }

}
