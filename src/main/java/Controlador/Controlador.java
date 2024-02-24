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
                        out.println("Fecha de Emisión: " + valort.getFechaEmision());
                    }
                }
                // request.getRequestDispatcher("index.jsp").forward(request, response);
                break;

            case "filtrar":
                // Obtener parámetros de filtrado
                System.out.println("Filtrar");
                String cuenta = request.getParameter("cuenta");
                String estado = request.getParameter("selEstado");
                String ubicacion = request.getParameter("selUbicacion");
                String desde = request.getParameter("desde");
                String hasta = request.getParameter("hasta");

                // Mostrar parámetros de filtrado
                System.out.println("Cuenta:" + cuenta);
                System.out.println("Estado:" + estado);
                System.out.println("Ubicacion:" + ubicacion);
                System.out.println("Desde:" + desde);
                System.out.println("Hasta:" + hasta);

                // Generar lista de tarjetas filtrada
                TarjetaDAO tdao = new TarjetaDAO();
                List<Tarjeta> listat = tdao.filtrar(cuenta, estado, ubicacion, desde, hasta);

                // Generar lista de estados
                EstadoDAO edao = new EstadoDAO();
                List<Estado> listae = edao.listar();
                request.setAttribute("elista", listae);
                String eNombre = edao.buscar(estado);

                // Generar lista de ubicaciones
                UbicacionDAO udao = new UbicacionDAO();
                List<Ubicacion> listau = udao.listar();
                request.setAttribute("ulista", listau);
                String uNombre = udao.buscar(ubicacion);

                // Devolver valores a la capa de presentación
                request.setAttribute("tlista", listat);
                request.setAttribute("cuenta", cuenta);
                request.setAttribute("estado", estado);
                request.setAttribute("ubicacion", ubicacion);
                request.setAttribute("desde", desde);
                request.setAttribute("hasta", hasta);
                request.setAttribute("unombre", uNombre);
                request.setAttribute("enombre", eNombre);
                request.getRequestDispatcher("index2.jsp").forward(request, response);
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
        processRequest(request, response);
    }

}
