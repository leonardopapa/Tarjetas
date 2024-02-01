package Controlador;

import Modelo.RemitoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControladorBuscar3 extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Comprobar si la recepción ya existe en la base de datos
        response.setContentType("text/html;charset=UTF-8");
        String nroRendicion = request.getParameter("nrend");
        PrintWriter out = response.getWriter();        
        RemitoDAO recepcion = new RemitoDAO();
        String resultado4 = null;        
        if (!recepcion.existe(nroRendicion)) resultado4 = "no encontrado";
        else resultado4 = "encontado";        
        out.println(resultado4);
        request.setAttribute("resultado", resultado4);
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
