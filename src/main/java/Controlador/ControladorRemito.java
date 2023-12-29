package Controlador;

import Modelo.RemitoDAO;
import Modelo.UbicacionDAO;
import Utilidades.GestionarPDF;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControladorRemito extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControladorRemito</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControladorRemito at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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

        // Obtener los parámetros del formulario
        Enumeration<String> parametros = request.getParameterNames();

        // Arrays para almacenar cuentas y fechas
        String[] cuentas = new String[10]; // Tamaño arbitrario, puedes ajustarlo según tus necesidades
        String[] fechas = new String[10];  // Tamaño arbitrario, puedes ajustarlo según tus necesidades
        String[] motivos = new String[10];  // Tamaño arbitrario, puedes ajustarlo según tus necesidades
        String[] estados = new String[10];  // Tamaño arbitrario, puedes ajustarlo según tus necesidades

        int indiceCuentas = 0;
        int indiceFechas = 0;

        // Procesar los parámetros
        String accion = "";
        while (parametros.hasMoreElements()) {
            String parametro = parametros.nextElement();

            // Verificar si el parámetro es de tipo "cuenta", "fecha", "accion, "motivo" o "resultado"
            if (parametro.startsWith("cuenta")) {
                cuentas[indiceCuentas++] = request.getParameter(parametro);
            } else if (parametro.startsWith("fecha")) {
                String fechaFormateada;
                try {
                    fechaFormateada = convertirFecha(request.getParameter(parametro));
                    fechas[indiceFechas++] = fechaFormateada;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else if (parametro.startsWith("accion")) {
                accion = request.getParameter("accion");
            } else if (parametro.startsWith("motivo")) {
                motivos[indiceCuentas - 1] = request.getParameter(parametro);
            } else if (parametro.startsWith("resultado")) {
                estados[indiceCuentas - 1] = request.getParameter(parametro);
            }
        }

        // Obtener correo
        String idCorreo = request.getParameter("correo");
        UbicacionDAO correoDAO = new UbicacionDAO();
        String correo3 = correoDAO.buscar(idCorreo);

        // Obtener fecha de envío
        String fechaEnvio2 = request.getParameter("fenvio");
        String fechaEnvio3 = "";
        try {
            fechaEnvio3 = convertirFecha2(fechaEnvio2);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        // Obtener número de remito
        RemitoDAO remitoDAO = new RemitoDAO();
        String numeroRemito = remitoDAO.buscarNumero();
        System.out.println("Remito Nº: " + numeroRemito);

        // Obtener ruta de desgliegue
        ServletContext servletContext = getServletContext();
        String rutaDespliegue = servletContext.getRealPath("/");

        // Generar remito
        GestionarPDF remito = new GestionarPDF();
        File documentoPDF = remito.crearRemito(fechaEnvio3, correo3, cuentas, indiceCuentas, numeroRemito, rutaDespliegue);

        // Construir la ruta completa hacia la carpeta "pdf"
        String rutaPdf = rutaDespliegue + File.separator + "pdf";
        
        System.out.println("rutaPdf:" + rutaPdf);

        // Verificar si la carpeta "pdf" existe, si no, crearla
        File carpetaPdf = new File(rutaPdf);
        if (!carpetaPdf.exists()) {
            carpetaPdf.mkdirs(); // Crear la carpeta y sus directorios padres si no existen
        }

        // Generar el nombre del archivo
        String nombreArchivo = "R" + String.format("%8s", numeroRemito).replace(' ', '0') + ".pdf";

        // Crear el archivo pdf en la ruta de contexto, carpeta pdf
        File archivoPDF = new File(rutaPdf, nombreArchivo);

        // Configurar el tipo de contenido de la respuesta
        // response.setContentType("application/pdf");
        // Configurar el encabezado de la respuesta para indicar la descarga del archivo
        // response.setHeader("Content-Disposition", "inline; filename=" + nombreArchivo);

        // Guardar el archivo PDF en la carpeta "pdf"
        FileOutputStream fos = new FileOutputStream(archivoPDF);
        InputStream fis = new FileInputStream(documentoPDF);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            fos.write(buffer, 0, bytesRead);
        }

        // Cerrar el flujo de saluda
        // out2.close();

        // Direccionar la salida a la capa de presentación
        request.setAttribute("nombreArchivo", nombreArchivo);        
        request.getRequestDispatcher("enviar2.jsp").forward(request, response);
    }

    private String convertirFecha(String fechaOrigen) throws ParseException {
        SimpleDateFormat formatoOrigen = new SimpleDateFormat("dd/MM/yyyy");
        // Convertir la fecha de origen de String a Date
        java.util.Date fechaOrigenDate = formatoOrigen.parse(fechaOrigen);
        // Crear un objeto SimpleDateFormat con el patrón de la fecha de destino
        SimpleDateFormat formatoDestino = new SimpleDateFormat("yyy-MM-dd");
        // Convertir la fecha de Date a String en el formato de destino
        return formatoDestino.format(fechaOrigenDate);
    }

    private String convertirFecha2(String fechaOrigen) throws ParseException {
        SimpleDateFormat formatoOrigen = new SimpleDateFormat("yyy-MM-dd");
        // Convertir la fecha de origen de String a Date
        java.util.Date fechaOrigenDate = formatoOrigen.parse(fechaOrigen);
        // Crear un objeto SimpleDateFormat con el patrón de la fecha de destino
        SimpleDateFormat formatoDestino = new SimpleDateFormat("dd/MM/yyyy");
        // Convertir la fecha de Date a String en el formato de destino
        return formatoDestino.format(fechaOrigenDate);
    }

}
