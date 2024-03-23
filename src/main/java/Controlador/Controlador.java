package Controlador;

import Modelo.Estado;
import Modelo.Ubicacion;
import Modelo.EstadoDAO;
import Modelo.Tarjeta;
import Modelo.TarjetaDAO;
import Modelo.UbicacionDAO;
import Utilidades.GestionarExcel;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controlador extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
            Gestiones de los botones de navegación, exportación y filtrado de la pantalla principal
        */
        
        String accion = request.getParameter("accion");
        System.out.println("Accion:" + accion);        
        boolean acc1 = accion.equalsIgnoreCase("filtrar");
        boolean acc2 = accion.equalsIgnoreCase("primero");
        boolean acc3 = accion.equalsIgnoreCase("anterior");
        boolean acc4 = accion.equalsIgnoreCase("siguiente");
        boolean acc5 = accion.equalsIgnoreCase("ultimo");
        boolean acc6 = accion.equalsIgnoreCase("exportar");
        if (acc1 || acc2 || acc3 || acc4 || acc5 || acc6) {
            // Obtener parámetros de filtrado                
            String cuenta = request.getParameter("cuenta");
            String estado = request.getParameter("selEstado");
            String ubicacion = request.getParameter("selUbicacion");
            String desde = request.getParameter("desde");
            String hasta = request.getParameter("hasta");
            String inicioListaS = request.getParameter("inicioLista");
            String tamanoSubListaS = request.getParameter("tamanoSubLista");
            /*
            System.out.println("Inicio Lista: " + inicioListaS);
            System.out.println("Tamaño Sub Lista: " + tamanoSubListaS);
            */

            // Generar lista de tarjetas filtrada                                
            TarjetaDAO tdao = new TarjetaDAO();
            List<Tarjeta> listat = tdao.filtrar(cuenta, estado, ubicacion, desde, hasta);
            int tamanoLista = listat.size();

            // Generar sublista
            int maxSize = 10;
            int inicioLista = Integer.parseInt(inicioListaS);
            int tamanoSubLista = Integer.parseInt(tamanoSubListaS);
            if (accion.equalsIgnoreCase("filtrar") || acc2) {
                inicioLista = 0;
                tamanoSubLista = (listat.size() < maxSize ? listat.size() : maxSize);

            }
            if (accion.equalsIgnoreCase("siguiente")) {
                if (inicioLista + maxSize < tamanoLista) {
                    inicioLista = inicioLista + maxSize;
                    tamanoSubLista = inicioLista + maxSize > tamanoLista ? tamanoLista - inicioLista : maxSize;
                }
            }

            if (accion.equalsIgnoreCase("ultimo")) {
                inicioLista = tamanoLista > maxSize ? tamanoLista - maxSize : 0;
                tamanoSubLista = inicioLista + maxSize > tamanoLista ? tamanoLista - inicioLista : maxSize;
            }

            if (accion.equalsIgnoreCase("anterior")) {
                if (inicioLista - maxSize > 0) {
                    inicioLista = inicioLista - maxSize;
                    tamanoSubLista = inicioLista + maxSize > tamanoLista ? tamanoLista - inicioLista : maxSize;
                } else {
                    tamanoSubLista = inicioLista;
                    inicioLista = 0;
                }
            }

            if (accion.equalsIgnoreCase("exportar")) {
                GestionarExcel gestionarExcel = new Utilidades.GestionarExcel();
                byte[] excelFile = gestionarExcel.exportar(listat, "XLS");

                // Configurar la respuesta HTTP
                response.setContentType("application/vnd.ms-excel");                                
                response.setHeader("Content-Disposition", "attachment; filename=tarjetas-export.xls");

                // Enviar el archivo Excel como respuesta                
                response.getOutputStream().write(excelFile);
            }
            
            /*
            System.out.println("Luego del filtrado: ");
            System.out.println("Inicio Lista: " + inicioLista);
            System.out.println("Max Size: " + maxSize);
            System.out.println("Tamaño Lista: " + tamanoLista);
            System.out.println("Tamaño Sub Lista: " + tamanoSubLista);
            */

            List<Tarjeta> lista = listat.subList(inicioLista, inicioLista + tamanoSubLista);

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
            request.setAttribute("tlista", lista);
            request.setAttribute("tamanoLista", String.valueOf(tamanoLista));
            request.setAttribute("maxSize", String.valueOf(maxSize));
            request.setAttribute("inicioLista", String.valueOf(inicioLista));
            request.setAttribute("tamanoSubLista", String.valueOf(tamanoSubLista));
            request.setAttribute("cuenta", cuenta);
            request.setAttribute("estado", estado);
            request.setAttribute("ubicacion", ubicacion);
            request.setAttribute("desde", desde);
            request.setAttribute("hasta", hasta);
            request.setAttribute("unombre", uNombre);
            request.setAttribute("enombre", eNombre);
            request.getRequestDispatcher("index2.jsp").forward(request, response);                        
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
