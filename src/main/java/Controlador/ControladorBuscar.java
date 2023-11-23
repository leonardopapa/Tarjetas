package Controlador;

import Modelo.TarjetaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Leandro
 */
public class ControladorBuscar extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String cuenta = request.getParameter("cuenta");
        // out.println("Buscando cuenta:" + cuenta);
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
