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
        PrintWriter out = response.getWriter();
        // response.setContentType("application/json;charset=UTF-8");

        // Generar lista de estados
        EstadoDAO edao = new EstadoDAO();
        List<Estado> listae = edao.listar();
        request.setAttribute("elista", listae);

        // Mostrar lista de estados
        /*
                    for (Estado valor : listae) {
                        out.println("Id: " + valor.getId());
                        out.println("Nombre: " + valor.getNombre());
                    }
                    
                    // Mostrar lista de ubicaciones
                    for (Ubicacion valor : listau) {
                        out.println("Id: " + valor.getId());
                        out.println("Nombre: " + valor.getNombre());
                    }
                    
                    // Mostrar lista de tarjetas
                    for (Tarjeta valort : listat) {
                        out.println("Cuenta: " + valort.getCliente());
                        out.println("Fecha de Emisión: " +valort.getFechaEmision());
                    }
         */
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
