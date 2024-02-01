package Controlador;

import Modelo.TarjetaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControladorBuscar2 extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Buscar si una tarjeta se encuenta en estado "En Distribución" para un determinado "correo"
        // Devuelve "encontrado" o "no encontrado"
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String cuenta = request.getParameter("cuenta");        
        String correo = request.getParameter("correo");  
        TarjetaDAO tdao = new TarjetaDAO();
        String resultado = tdao.buscar(cuenta,"2", correo);     
        if (resultado.isEmpty()) out.println("no encontrado");
        else out.println(resultado);        
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