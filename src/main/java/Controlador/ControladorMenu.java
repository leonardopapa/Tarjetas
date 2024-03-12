package Controlador;

import Modelo.Estado;
import Modelo.EstadoDAO;
import Modelo.Tarjeta;
import Modelo.TarjetaDAO;
import Modelo.Ubicacion;
import Modelo.UbicacionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControladorMenu extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("Petición inválida");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        String destino = "";
        switch (accion) {
            case "iniciar":
                System.out.println("Menu Iniciar");
                request = inicializar(request);                
                destino = "index2.jsp";
                break;
            case "cambiar":
                destino = "cambiar.jsp";
                accion = "lanzar";
                break;
            default:
                System.out.println("Acción inválida");
                destino = "index.jsp";
                break;
        }
        System.out.println("Acción:" + accion + ".");
        System.out.println("Destino:" + destino + ".");
        request.setAttribute("accion", accion);
        response.setStatus(200);
        request.getRequestDispatcher(destino).forward(request, response);
    }
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    private HttpServletRequest inicializar(HttpServletRequest request) {
        System.out.println("Inicializando");

        // Generar lista de estados
        EstadoDAO edao = new EstadoDAO();
        List<Estado> listae = edao.listar();
        request.setAttribute("elista", listae);

        // Generar lista de ubicaciones
        UbicacionDAO udao = new UbicacionDAO();
        List<Ubicacion> listau = udao.listar();
        request.setAttribute("ulista", listau);

        // Generar lista de tarjetas
        TarjetaDAO tdao = new TarjetaDAO();
        List<Tarjeta> listat = tdao.listar();
        request.setAttribute("tlista", listat);

        return request;
    }

}
