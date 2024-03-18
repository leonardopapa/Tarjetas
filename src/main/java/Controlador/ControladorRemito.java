package Controlador;

import Modelo.Estado;
import Modelo.EstadoDAO;
import Modelo.Motivo;
import Modelo.MotivoDAO;
import Modelo.Movimiento;
import Modelo.MovimientoDAO;
import Modelo.Operador;
import Modelo.OperadorDAO;
import Modelo.RemitoDAO;
import Modelo.TarjetaDAO;
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
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
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
        List<String> piezas = new ArrayList();
        List<String> cuentas = new ArrayList();        
        List<String> motivos = new ArrayList();
        List<String> estados = new ArrayList();

        // Procesar los parámetros
        String accion = "";
        EstadoDAO estadoDao = new EstadoDAO();
        MotivoDAO motivoDao = new MotivoDAO();
        while (parametros.hasMoreElements()) {
            String parametro = parametros.nextElement();
            if (parametro.startsWith("pieza")) {
                piezas.add(request.getParameter(parametro));
            } else if (parametro.startsWith("cuenta")) {
                cuentas.add(request.getParameter(parametro));
            } else if (parametro.startsWith("accion")) {
                accion = request.getParameter("accion");
                System.out.println("Acción:" + accion);
            } else if (parametro.startsWith("motivo")) {
                String motivo = request.getParameter(parametro);
                if ("0".equals(motivo)) {
                    motivos.add(" ");
                } else {
                    motivos.add(motivoDao.buscar(motivo));
                }
            } else if (parametro.startsWith("resultado")) {
                String estado = request.getParameter(parametro);
                estados.add(estadoDao.buscar(estado));
            }
        }
        
        System.out.println("Piezas recibidas: " + piezas.size() );
        System.out.println("Cuentas recibidas: " + cuentas.size() );

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
                System.out.println("Fecha de envío antes de convertir:" + fechaEnvio2);
                try {
                    fechaEnvio3 = convertirFecha2(fechaEnvio2);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                System.out.println("Fecha de envío convertida:" + fechaEnvio3);

                // Obtener número de remito
                RemitoDAO remitoDAO = new RemitoDAO();
                String numeroRemito = remitoDAO.buscarNumero();
                if (numeroRemito == null) {
                    numeroRemito = "1";
                }
                System.out.println("Remito Nº: " + numeroRemito);

                // Generar remito                
                File documentoPDF = remito.crearRemito(fechaEnvio3, correo3, cuentas, piezas, numeroRemito, rutaDespliegue);

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
                request.setAttribute("resultado", "ok");
                request.setAttribute("archivo", nombreArchivo);
                request.setAttribute("correo", idCorreo);
                request.setAttribute("fenvio", fechaEnvio2);
                request.setAttribute("ccuentas", concatenarCuentas(cuentas));
                request.setAttribute("cpiezas", concatenarCuentas(piezas));
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
                String cpiezas = request.getParameter("cpiezas");

                // Mostrar por consola los parámetros recibidos
                System.out.println("Archivo:" + nombreArchivo2);
                System.out.println("Alias:" + alias);
                System.out.println("Password:" + firma);

                // Obtener la ruta del archivo PDF a firmar                                                
                String rutaPdfOrigen = rutaPdf + File.separator + nombreArchivo2;
                
                // Obtener el almacen de claves
                OperadorDAO opDao = new OperadorDAO();
                String certificado = opDao.obtenerCertificado(11); // Obtener certificado para el operador 11
                String rutaKeystore = rutaDespliegue + "keystore" + File.separator + certificado;

                // Verificar alias y password
                String resultado = null;
                String destino = null;
                if (remito.verificarFirma(alias, firma, rutaKeystore)) {
                    // Firmar el archivo PDF
                    File pdfFirmado = remito.firmarPDF(nombreArchivo2, rutaPdfOrigen, alias, firma, rutaKeystore);
                    // Grabar el archivo PDF en la carpeta pdf del directorio de contexto
                    grabarArchivo(rutaPdf, nombreArchivo2, pdfFirmado);
                    System.out.println("PDF firmado grabado");
                    resultado = "ok";
                    destino = "enviar3.jsp";
                } else {
                    System.out.println("No se grabó el archivo PDF firmado");
                    resultado = "error";
                    destino = "enviar2.jsp";
                }

                // Devolver el control a la capa de presentación
                request.setAttribute("resultado", resultado);
                request.setAttribute("archivo", nombreArchivo2);
                request.setAttribute("correo", idCorreo3);
                request.setAttribute("fenvio", fechaEnvio6);
                request.setAttribute("ccuentas", ccuentas2);
                request.setAttribute("cpiezas", cpiezas);
                request.getRequestDispatcher(destino).forward(request, response);
                break;

            case "grabarRemito":
                // Obtener los parámetros                
                String nombreArchivo3 = request.getParameter("archivo");
                String ccuentas = request.getParameter("ccuentas");
                String ccpiezas = request.getParameter("cpiezas");
                String idCorreo2 = request.getParameter("correo");
                String fechaEnvio4 = request.getParameter("fenvio");
                String fechaEnvio5 = "";                
                try {
                    fechaEnvio5 = convertirFecha2(fechaEnvio4);
                } catch (Exception e) {                
                }                
                
                // Grabar movimiento "enviar" en BD
                boolean resultadoGrabacion = grabarMovimientoEnviar(idCorreo2, fechaEnvio5, ccpiezas, nombreArchivo3);
                String resultado2 = (resultadoGrabacion ? "exito" : "error");
                
                if (resultadoGrabacion) {
                    // Enviar Remito a correo por email
                    UbicacionDAO correoDao = new UbicacionDAO();
                    String emailCorreo = correoDao.obtenerEmail(idCorreo2);
                    GestionarEmail mailer = new GestionarEmail();
                    String subject = "Envío de remito " + nombreArchivo3;
                    String texto = "Se adjunta remito " + nombreArchivo3;
                    boolean resultadoEmail = mailer.enviarEmail(emailCorreo, subject, texto, rutaPdf + File.separator + nombreArchivo3, nombreArchivo3);
                    System.out.println("Resultado email:" + (resultadoEmail ? "OK" : "Fracaso"));
                }

                // Devolver el control a la capa de presentación
                request.setAttribute("resultado", resultado2);
                request.setAttribute("archivo", nombreArchivo3);
                request.getRequestDispatcher("enviar3.jsp").forward(request, response);
                break;

            case "recibir":
                // Obtener correo
                String idCorreo4 = request.getParameter("correo");
                UbicacionDAO correoDAO2 = new UbicacionDAO();
                String correo2 = correoDAO2.buscar(idCorreo4);
                
                // Obtener fecha de rendición
                String fechaRend = request.getParameter("frend");
                String fechaRendicion = "";
                try {
                    fechaRendicion = convertirFecha2(fechaRend);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                
                // Obtener número de rendición
                String nroRendicion = request.getParameter("nrend");
                
                System.out.println("Correo: " + correo2);
                System.out.println("Fecha de Rendición: " + fechaRendicion);
                System.out.println("Numero de Rendicion: " + nroRendicion);

                // Comprobar si la recepción ya existe en la base de datos
                RemitoDAO recepcion = new RemitoDAO();
                String resultado4 = null;
                String nombreArchivo4 = null;
                if (recepcion.existe(nroRendicion)) {
                    resultado4 = "error";
                } else {
                    // Generar remito de recepción
                    File documentoPDF2 = remito.crearRecepcion(fechaRendicion, correo2, cuentas, piezas, estados, motivos, nroRendicion, rutaDespliegue);

                    // Verificar si la carpeta "pdf" existe, si no, crearla
                    File carpetaPdf2 = new File(rutaPdf);
                    if (!carpetaPdf2.exists()) {
                        carpetaPdf2.mkdirs(); // Crear la carpeta y sus directorios padres si no existen
                    }

                    // Generar el nombre del archivo
                    nombreArchivo4 = nroRendicion + ".pdf";

                    // Guardar el archivo PDF en la carpeta "pdf"
                    grabarArchivo(rutaPdf, nombreArchivo4, documentoPDF2);
                    resultado4 = "ok";
                }
                
                // Direccionar la salida a la capa de presentación
                request.setAttribute("resultado", resultado4);
                request.setAttribute("archivo", nombreArchivo4);
                request.setAttribute("correo", idCorreo4);
                request.setAttribute("frend", fechaRendicion);
                request.setAttribute("ccuentas", concatenarCuentas(cuentas));
                request.setAttribute("cpiezas", concatenarCuentas(piezas));
                request.setAttribute("cresultados", concatenarCuentas(estados));
                request.setAttribute("cmotivos", concatenarCuentas(motivos));
                request.getRequestDispatcher("recibir2.jsp").forward(request, response);
                break;

            case "firmarRecepcion":
                // Obtener los parámetros                
                String nombreArchivo5 = request.getParameter("archivo");
                String alias2 = request.getParameter("alias");
                String firma2 = request.getParameter("password");
                String idCorreo5 = request.getParameter("correo");
                String fechaRendicion2 = request.getParameter("frend");
                String cpiezas2 = request.getParameter("ccuentas");
                String ccuentas3 = request.getParameter("ccuentas");
                String cresultados2 = request.getParameter("cresultados");
                String cmotivos2 = request.getParameter("cmotivos");

                // Mostrar por consola los parámetros recibidos
                System.out.println("Archivo:" + nombreArchivo5);
                System.out.println("Alias:" + alias2);
                System.out.println("Password:" + firma2);

                // Obtener la ruta del archivo PDF a firmar                                                
                String rutaPdfOrigen2 = rutaPdf + File.separator + nombreArchivo5;
                // System.out.println("Ruta PDF origen = " + rutaPdfOrigen);

                // Obtener el almacen de claves                
                String rutaKeystore2 = rutaDespliegue + "keystore" + File.separator + "keystore.p12";

                // Verificar alias y password
                String resultado3 = null;
                String destino2 = null;
                if (remito.verificarFirma(alias2, firma2, rutaKeystore2)) {
                    // Firmar el archivo PDF
                    File pdfFirmado = remito.firmarPDF(nombreArchivo5, rutaPdfOrigen2, alias2, firma2, rutaKeystore2);
                    // Grabar el archivo PDF en la carpeta pdf del directorio de contexto
                    grabarArchivo(rutaPdf, nombreArchivo5, pdfFirmado);
                    System.out.println("PDF firmado grabado");
                    resultado3 = "ok";
                    destino2 = "recibir3.jsp";
                } else {
                    System.out.println("No se grabó el archivo PDF firmado");
                    resultado3 = "error";
                    destino2 = "recibir2.jsp";
                }

                // Devolver el control a la capa de presentación
                request.setAttribute("resultado", resultado3);
                request.setAttribute("archivo", nombreArchivo5);
                request.setAttribute("correo", idCorreo5);
                request.setAttribute("frend", fechaRendicion2);
                request.setAttribute("ccuentas", ccuentas3);
                request.setAttribute("cpiezas", cpiezas2);
                request.setAttribute("cresultados", cresultados2);
                request.setAttribute("cmotivos", cmotivos2);
                request.getRequestDispatcher(destino2).forward(request, response);
                break;
            
            case "grabarRecepcion":
                // Obtener los parámetros                
                String nombreArchivo6 = request.getParameter("archivo");
                String ccuentas4 = request.getParameter("ccuentas");
                String cpiezas3 = request.getParameter("cpiezas");
                String cresultados3 = request.getParameter("cresultados");
                String cmotivos3 = request.getParameter("cmotivos");
                String idCorreo6 = request.getParameter("correo");
                String fechaRecep2 = request.getParameter("frend");                
                String fechaRecepcion3 = "";
                System.out.println("Fecha a convertir: " + fechaRecep2);
                try {
                    fechaRecepcion3 = convertirFecha(fechaRecep2);
                } catch (Exception e) {
                    System.out.println("Error de conversión de fecha");
                    System.out.println(e.toString());
                }
                System.out.println("Fecha convertida: " + fechaRecepcion3);

                // Grabar movimiento "recibir" en BD
                boolean resultadoGrabacion2 = grabarMovimientoRecibir(idCorreo6, fechaRecepcion3, cpiezas3, ccuentas4, nombreArchivo6, cresultados3, cmotivos3);
                String resultado5 = (resultadoGrabacion2 ? "exito" : "error");

                // email Informe de Recepcion a correos por email
                if (resultadoGrabacion2) {
                    UbicacionDAO correoDao2 = new UbicacionDAO();
                    String emailCorreo2 = correoDao2.obtenerEmail(idCorreo6);
                    GestionarEmail mailer2 = new GestionarEmail();
                    String subject2 = "Recepcion Tarjetas Remito " + nombreArchivo6;
                    String texto2 = "Se adjunta informe de recepción de remito " + nombreArchivo6;
                    boolean resultadoEmail2 = mailer2.enviarEmail(emailCorreo2, subject2, texto2, rutaPdf + File.separator + nombreArchivo6, nombreArchivo6);
                    System.out.println("Resultado email:" + (resultadoEmail2 ? "OK" : "Fracaso"));
                }

                // Devolver el control a la capa de presentación
                request.setAttribute("resultado", resultado5);
                request.setAttribute("archivo", nombreArchivo6);
                request.getRequestDispatcher("recibir3.jsp").forward(request, response);
                break;

            default:
                System.out.println("No se especificó una acción válida");
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

    private String concatenarCuentas(List<String> cuentas) {
        // Recibe una lista de Strings
        // Devuelve un String con los valores de la lista concatenados y separados por comas
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < cuentas.size(); i++) {
            resultado.append(cuentas.get(i));

            // Agregar coma solo si no es el último elemento.
            if (i < cuentas.size() - 1) {
                resultado.append(",");
            }
        }
        return resultado.toString();
    }

    private boolean grabarMovimientoEnviar(String idCorreo, String fechaEnvio, String cpiezas, String remito) {

        // Iterar sobre los datos y guardarlos en la base de datos
        boolean resultado;        
        String[] piezas = cpiezas.split(",");
        String fecha = null;
        try {
            fecha = convertirFecha(fechaEnvio);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        int exitos = 0;
        int fracasos = 0;
        
        for (String pieza : piezas) {
            Movimiento movimiento = new Movimiento();
            Estado estado = new Estado();
            estado.setId(2); // En Distribución
            movimiento.setMovimiento(estado);
            Operador operador = new Operador();
            operador.setId(11); // Operador ficticio
            Ubicacion correo = new Ubicacion();
            correo.setId(Integer.parseInt(idCorreo));
            MovimientoDAO mdao = new MovimientoDAO();
            movimiento.setPieza(Integer.parseInt(pieza));
            TarjetaDAO tdao = new TarjetaDAO();
            String cuenta = tdao.buscarCuenta(pieza);
            movimiento.setCliente(Integer.parseInt(cuenta));
            movimiento.setFecha(Date.valueOf(fecha));
            movimiento.setOperador(operador);
            movimiento.setUbicacion(correo);
            movimiento.setDocumento(remito);            
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

    private boolean grabarMovimientoRecibir(String idCorreo2, String fechaRecepcion, String cpiezas, String ccuentas, String nombreArchivo3, String cresultados, String cmotivos) {
        // Iterar sobre los datos y guardarlos en la base de datos
        boolean resultado;
        String[] cuentas = ccuentas.split(",");
        String[] piezas = cpiezas.split(",");
        String[] resultados = cresultados.split(",");
        String[] motivos = cmotivos.split(",");
        
        int exitos = 0;
        int fracasos = 0;
        for (int i=0; i < piezas.length; i++) {        
            Movimiento movimiento = new Movimiento();
            Estado estado = new Estado();
            estado.setId(Integer.parseInt(resultados[i])); 
            movimiento.setMovimiento(estado);            
            Operador operador = new Operador();
            operador.setId(11); // Operador ficticio
            Ubicacion correo = new Ubicacion();
            correo.setId(Integer.parseInt(idCorreo2));
            MovimientoDAO mdao = new MovimientoDAO();
            movimiento.setPieza(Integer.parseInt(piezas[i]));
            movimiento.setCliente(Integer.parseInt(cuentas[i]));
            movimiento.setFecha(Date.valueOf(fechaRecepcion));
            movimiento.setOperador(operador);
            movimiento.setUbicacion(correo);            
            movimiento.setDocumento(nombreArchivo3);            
            if ("0".equals(motivos[i])) {                        
                        resultado = mdao.agregarRecibir2(movimiento);                        
                    } else {                        
                        Motivo motivo = new Motivo();
                        motivo.setId(Integer.parseInt(motivos[i]));
                        movimiento.setMotivo(motivo);
                        resultado = mdao.agregarRecibir(movimiento);
                    }            
            if (resultado) {
                exitos++;
            } else {
                fracasos++;
            }
        }

        resultado = exitos > 0;

        System.out.println("Resultados de la grabación del movimiento de recepción:");
        System.out.println("Exitos:" + exitos);
        System.out.println("Fracasos:" + fracasos);
        System.out.println("Resultado general:" + resultado);

        return resultado;
    }

}
