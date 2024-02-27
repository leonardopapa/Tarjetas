package Controlador;

import Modelo.Estado;
import Modelo.EstadoDAO;
import Modelo.Tarjeta;
import Modelo.TarjetaDAO;
import Modelo.Ubicacion;
import Modelo.UbicacionDAO;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

        // Generar lista de ubicaciones
        UbicacionDAO udao = new UbicacionDAO();
        List<Ubicacion> listau = udao.listar();

        // Generar lista de tarjetas
        TarjetaDAO tdao = new TarjetaDAO();
        List<Tarjeta> listat = tdao.listar();
        int tamanoLista = listat.size();
        int inicioLista = 0;
        int maxSize = 10;
        int tamanoSubLista = tamanoSubLista = (tamanoLista < maxSize ? tamanoLista : maxSize);
        List<Tarjeta> lista = listat.subList(inicioLista, tamanoSubLista);

        // Generar las fechas iniciales
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaHaceTresMeses = fechaActual.minusMonths(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String hasta = fechaActual.format(formatter);
        String desde = fechaHaceTresMeses.format(formatter);

        // Enviar parámetros a la capa de presentación
        request.setAttribute("tlista", lista);
        request.setAttribute("tamanoLista", String.valueOf(tamanoLista));
        request.setAttribute("maxSize", String.valueOf(maxSize));
        request.setAttribute("inicioLista", String.valueOf(inicioLista));
        request.setAttribute("tamanoSubLista", String.valueOf(tamanoSubLista));
        request.setAttribute("ulista", listau);
        request.setAttribute("elista", listae);
        request.setAttribute("desde", desde);
        request.setAttribute("hasta", hasta);
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
