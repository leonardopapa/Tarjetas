package Controlador;

import Modelo.TarjetaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControladorBuscar extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Llamado desde enviar.jsp
        // Busca una pieza y devuelve la fecha de emisión y el número de cuenta.
        // En caso de no encontrarse, devuelve "no encontrado" en ambos casos
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String pieza = request.getParameter("pieza");                
        TarjetaDAO tdao = new TarjetaDAO();
        String resultado = tdao.buscar(pieza);        
        if (resultado.isEmpty()) {            
            out.println("no encontrado, no encontrado");
        } else {            
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
