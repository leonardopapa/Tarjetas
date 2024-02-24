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

public class Controlador2 extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
        
        request.getRequestDispatcher("index2.jsp").forward(request, response);

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
