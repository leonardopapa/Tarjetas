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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String cuenta = request.getParameter("cuenta");        
        TarjetaDAO tdao = new TarjetaDAO();
        String resultado = tdao.buscar2(cuenta,"2","fecha_imposicion");     
        if (resultado.isEmpty()) {
            // request.setAttribute("resultado", "no encontrado");
            out.println("no encontrado");
        } else {
            request.setAttribute("resultado", resultado);
            out.println(resultado);
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