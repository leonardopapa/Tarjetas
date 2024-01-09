package Utilidades;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class GestionarEmail {

    public boolean enviarEmail(String destinatario, String subject, String texto, String rutaAdjunto, String nombreAdjunto) {

        boolean resultado;

        // Configuración del servidor de correo
        String host = "smtp.gmail.com";
        String usuario = "leonardopapa2000@gmail.com";
        final String contraseña = "ccpizuqhloncizll";

        // Destinatario y remitente
        // String destinatario = "leonardo_papa@hotmail.com";
        final String remitente = "leonardopapa2000@gmail.com";

        // Configuración de propiedades
        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.host", host);
        propiedades.put("mail.smtp.port", "587");
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");
        propiedades.put("mail.smtp.ssl.trust", host);
        propiedades.setProperty("mail.smtp.user", usuario);
        propiedades.setProperty("mail.smtp.password", contraseña);

        // Obtener la sesión de correo               
        Session sesion = Session.getDefaultInstance(propiedades, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, contraseña);
            }
        });

        try {
            // Crear mensaje de correo
            MimeMessage mensaje = new MimeMessage(sesion);
            mensaje.setFrom(new InternetAddress(remitente, "CREDIMAS - Leonardo Papa"));
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            mensaje.setSubject(subject);

            // Crear cuerpo del mensaje
            BodyPart cuerpoMensaje = new MimeBodyPart();
            cuerpoMensaje.setText(texto);

            // Crear parte del archivo adjunto
            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource(rutaAdjunto)));
            adjunto.setFileName(nombreAdjunto);

            // Combinar partes del mensaje
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(cuerpoMensaje);
            multipart.addBodyPart(adjunto);

            // Establecer el contenido del mensaje
            mensaje.setContent(multipart);

            // Enviar mensaje
            Transport.send(mensaje);
            System.out.println("El mensaje ha sido enviado con éxito.");
            resultado = true;

        } catch (Exception e) {
            e.getMessage();
            resultado = false;
        }
        return resultado;
    }
}
