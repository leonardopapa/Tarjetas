package Controlador;

import Modelo.Conexion;
import Modelo.Estado;
import Modelo.Movimiento;
import Modelo.MovimientoDAO;
import Modelo.Operador;
import Modelo.RemitoDAO;
import Modelo.Ubicacion;
import Modelo.UbicacionDAO;
import Utilidades.GestionarEmail;
import Utilidades.GestionarPDF;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
                request.setAttribute("correoId", idCorreo);
                request.setAttribute("fechaRemito", fechaEnvio2);
                request.setAttribute("ccuentas", concatenarCuentas(cuentas, indiceCuentas));

                request.getRequestDispatcher("enviar2.jsp").forward(request, response);
                break;

            case "firmar":
                // Obtener los parámetros                
                String nombreArchivo2 = request.getParameter("archivo");
                String alias = request.getParameter("alias");
                String firma = request.getParameter("password");
                String idCorreo3 = request.getParameter("correo");
                String fechaEnvio6 = request.getParameter("fenvio");
                String ccuentas2 = request.getParameter("ccuentas");

                // Mostrar por consola los parámetros recibidos
                System.out.println("Archivo:" + nombreArchivo2);
                System.out.println("Alias:" + alias);
                System.out.println("Password:" + firma);

                // Obtener la ruta del archivo PDF a firmar                                                
                String rutaPdfOrigen = rutaPdf + File.separator + nombreArchivo2;
                // System.out.println("Ruta PDF origen = " + rutaPdfOrigen);

                // Obtener el almacen de claves                
                String rutaKeystore = rutaDespliegue + "keystore" + File.separator + "keystore.p12";

                // Firmar el archivo PDF
                File pdfFirmado = remito.firmarPDF(nombreArchivo2, rutaPdfOrigen, alias, firma, rutaKeystore);
                
                // Grabar el archivo PDF en la carpeta pdf del directorio de contexto                                                
                String resultado = null;                
                if (pdfFirmado.length() > 0) {
                    grabarArchivo(rutaPdf, nombreArchivo2, pdfFirmado);
                    System.out.println("PDF firmado grabado");
                    resultado ="ok";
                } else {
                    System.out.println("No se puedo grabar el archivo PDF firmado");
                    resultado ="error";
                }
                
                // Devolver el control a la capa de presentación
                request.setAttribute("resultado", resultado);
                request.setAttribute("archivo", nombreArchivo2);
                request.setAttribute("correo", idCorreo3);
                request.setAttribute("fenvio", fechaEnvio6);
                request.setAttribute("ccuentas", ccuentas2);
                request.getRequestDispatcher("enviar3.jsp").forward(request, response);
                break;

            case "grabarRemito":
                // Obtener los parámetros                
                String nombreArchivo3 = request.getParameter("archivo");
                String ccuentas = request.getParameter("ccuentas");
                String idCorreo2 = request.getParameter("correo");
                String fechaEnvio4 = request.getParameter("fenvio");
                String fechaEnvio5 = "";
                try {
                    fechaEnvio5 = convertirFecha2(fechaEnvio4);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                
                // Grabar movimiento "enviar" en BD
                boolean resultadoGrabacion = grabarMovimientoEnviar(idCorreo2, fechaEnvio5, ccuentas, nombreArchivo3);
                String resultado2 = (resultadoGrabacion ? "exito" : "error");

                // email Remito a correos por email
                UbicacionDAO correoDao = new UbicacionDAO();
                String emailCorreo = correoDao.obtenerEmail(idCorreo2);
                GestionarEmail mailer = new GestionarEmail();
                String subject = "Envío de remito "+nombreArchivo3;
                String texto = "Se adjunta remito " + nombreArchivo3;
                boolean resultadoEmail = mailer.enviarEmail(emailCorreo, subject, texto, rutaPdf + File.separator + nombreArchivo3, nombreArchivo3);
                System.out.println("Resultado email:" + (resultadoEmail ? "OK" : "Fracaso"));

                // Devolver el control a la capa de presentación
                request.setAttribute("resultado", resultado2);
                request.setAttribute("archivo", nombreArchivo3);
                request.getRequestDispatcher("enviar3.jsp").forward(request, response);
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

    private String concatenarCuentas(String[] cuentas, int cntCuentas) {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < cntCuentas; i++) {
            resultado.append(cuentas[i]);

            // Agregar coma solo si no es el último elemento.
            if (i < cntCuentas - 1) {
                resultado.append(",");
            }
        }
        return resultado.toString();
    }

    private boolean grabarMovimientoEnviar(String idCorreo, String fechaEnvio, String ccuentas, String remito) {

        // Iterar sobre los datos y guardarlos en la base de datos
        boolean resultado;
        String[] cuentas = ccuentas.split(",");
        String fecha = null;
        try {
            fecha = convertirFecha(fechaEnvio);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        int exitos = 0;
        int fracasos = 0;
        for (String cuenta : cuentas) {
            Movimiento movimiento = new Movimiento();
            Estado estado = new Estado();
            estado.setId(2); // En Distribución
            movimiento.setMovimiento(estado);
            Operador operador = new Operador();
            operador.setId(11); // Operador ficticio
            Ubicacion correo = new Ubicacion();
            correo.setId(Integer.parseInt(idCorreo));
            MovimientoDAO mdao = new MovimientoDAO();
            movimiento.setCliente(Integer.parseInt(cuenta));            
            movimiento.setFecha(Date.valueOf(fecha));            
            movimiento.setOperador(operador);
            movimiento.setUbicacion(correo);
            System.out.println("Documento:<" + remito+">");
            movimiento.setDocumento(remito);            
            System.out.println("Llegué hasta acá");
            resultado = mdao.agregarEnviar(movimiento);
            if (resultado) {
                exitos++;
            } else {
                fracasos++;
            }
        }

        resultado = exitos > 0;

        System.out.println("Resultados de la grabación del movimiento de envío:");
        System.out.println("Exitos:" + exitos);
        System.out.println("Fracasos:" + fracasos);
        System.out.println("Resultado general:" + resultado);

        return resultado;
    }

}
