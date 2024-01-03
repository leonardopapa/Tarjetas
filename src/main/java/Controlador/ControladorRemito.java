package Controlador;

import Modelo.RemitoDAO;
import Modelo.UbicacionDAO;
import Utilidades.GestionarPDF;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

        PrintWriter out = response.getWriter();

        // Obtener los parámetros del formulario
        Enumeration<String> parametros = request.getParameterNames();

        // Arrays para almacenar cuentas y fechas
        String[] cuentas = new String[10]; // Tamaño arbitrario, puedes ajustarlo según tus necesidades        
        int indiceCuentas = 0;

        // Procesar los parámetros
        String accion = "";
        while (parametros.hasMoreElements()) {
            String parametro = parametros.nextElement();

            // Verificar si el parámetro es de tipo "cuenta o "accion"
            if (parametro.startsWith("cuenta")) {
                cuentas[indiceCuentas++] = request.getParameter(parametro);
            } else if (parametro.startsWith("accion")) {
                accion = request.getParameter("accion");
            }
        }

        // Obtener ruta de desgliegue
        ServletContext servletContext = getServletContext();
        String rutaDespliegue = servletContext.getRealPath("/");

        // Instanciar la clase GestionarPDF        
        GestionarPDF remito = new GestionarPDF();

        // Construir la ruta completa hacia la carpeta "pdf"
        String rutaPdf = rutaDespliegue + "pdf";

        switch (accion) {
            case "remito":
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
                if (numeroRemito == null) {
                    numeroRemito = "1";
                }
                System.out.println("Remito Nº: " + numeroRemito);

                // Generar remito                
                File documentoPDF = remito.crearRemito(fechaEnvio3, correo3, cuentas, indiceCuentas, numeroRemito, rutaDespliegue);

                // Verificar si la carpeta "pdf" existe, si no, crearla
                File carpetaPdf = new File(rutaPdf);
                if (!carpetaPdf.exists()) {
                    carpetaPdf.mkdirs(); // Crear la carpeta y sus directorios padres si no existen
                }

                // Generar el nombre del archivo
                String nombreArchivo = "R" + String.format("%8s", numeroRemito).replace(' ', '0') + ".pdf";

                // Guardar el archivo PDF en la carpeta "pdf"
                grabarArchivo(rutaPdf, nombreArchivo, documentoPDF);

                // Direccionar la salida a la capa de presentación
                request.setAttribute("nombreArchivo", nombreArchivo);
                request.getRequestDispatcher("enviar2.jsp").forward(request, response);
                break;

            case "firmar":
                // Obtener los parámetros                
                String nombreArchivo2 = request.getParameter("archivo");
                String alias = request.getParameter("alias");
                String firma = request.getParameter("password");
                System.out.println("Archivo:" + nombreArchivo2);
                System.out.println("Alias:" + alias);
                System.out.println("Password:" + firma);

                // Obtener la ruta del archivo PDF a firmar                                                
                String rutaPdfOrigen = rutaPdf + File.separator + nombreArchivo2;
                System.out.println("Ruta PDF origen = " + rutaPdfOrigen);

                // Obtener el almacen de claves
                // File keyStore = new File(rutaDespliegue + File.separator + "keystore" + File.separator + "keystore.p12");
                String rutaKeystore = rutaDespliegue + "keystore" + File.separator + "keystore.p12";
                System.out.println("Ruta keyStore = " + rutaKeystore);

                // Firmar el archivo PDF
                File pdfFirmado = remito.firmarPDF(nombreArchivo2, rutaPdfOrigen, alias, firma, rutaKeystore);
                System.out.println("PDF firmado obtenido");

                // Grabar el archivo PDF en la carpeta pdf del directorio de contexto
                if (pdfFirmado.length() > 0) {
                    grabarArchivo(rutaPdf, nombreArchivo2, pdfFirmado);
                    System.out.println("PDF firmado grabado");
                } else {
                    System.out.println("Error en el proceso de firma, no se puede firmar");
                }
                break;

            default:
                out.println("No se especificó una acción válida");
                break;
        }
    }

    private void grabarArchivo(String path, String nombreArchivo, File origen) {
        File destino = new File(path, nombreArchivo);
        try {
            FileOutputStream fos = new FileOutputStream(destino);
            InputStream inputStream = new FileInputStream(origen);
            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
