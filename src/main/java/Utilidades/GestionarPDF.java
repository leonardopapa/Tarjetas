package Utilidades;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.itextpdf.signatures.BouncyCastleDigest;
import com.itextpdf.signatures.DigestAlgorithms;
import com.itextpdf.signatures.IExternalDigest;
import com.itextpdf.signatures.IExternalSignature;
import com.itextpdf.signatures.PdfSigner;
import com.itextpdf.signatures.PrivateKeySignature;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class GestionarPDF {

    public File crearRemito(String fechaRemito, String nombreCorreo, String[] cuentas, int cntCuentas, String numeroRemito, String rutaDespliegue) {

        PdfWriter writer = null;
        try {
            writer = new PdfWriter("remito.pdf");
        } catch (FileNotFoundException ex) {
            System.out.println("No se puede crear el archivo remito.pdf");
        }

        // Crear un documento PDF 
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
        document.setMargins(50, 50, 50, 50);

        // Obtener la imagen del logo
        Image logo = null;
        try {
            logo = new Image(ImageDataFactory.create(rutaDespliegue + "img\\tarjeta.jpg"));
        } catch (Exception ex) {
            System.out.println("No se puede encontrar la imagen tarjeta.jpg");
        }

        // Agregar una tabla para colocar allí el logo y el título del documento 
        Table table = new Table(new float[]{1, 8})
                .setBorder(null)
                .useAllAvailableWidth();
        table.addCell(new Cell()
                .setBorder(null)
                .add(new Paragraph("")
                        .add(logo)));
        table.addCell(new Cell()
                .setBorder(null)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .add(new Paragraph("REMITO DE ENTREGA DE TARJETAS AL CORREO")
                        .setBold()
                        .setTextAlignment(TextAlignment.CENTER)
                ));
        document.add(table);

        // Agregar una tabla para colocar allí el número de remito, el nombre del correo y la fecha
        table = new Table(new float[]{1, 1})
                .setBorder(Border.NO_BORDER);
        table.addCell(new Cell()
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph("Remito Nº:")
                        .setBold()));
        table.addCell(new Cell()
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph(numeroRemito)));

        table.addCell(new Cell()
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph("Correo:")
                        .setBold()));
        table.addCell(new Cell()
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph(nombreCorreo)));

        table.addCell(new Cell()
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph("Fecha:")
                        .setBold()));
        table.addCell(new Cell()
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph(fechaRemito)));

        document.add(new Paragraph(""));
        document.add(table);

        // Agregar un párrafo
        document.add(new Paragraph("En el día de la fecha, se entregan al correo las tarjetas que se indican a continuación:").setMarginTop(10));
        document.add(new Paragraph(""));

        // Agregar el listado de cuentas
        com.itextpdf.layout.element.List cuentasPDF = new com.itextpdf.layout.element.List()
                .setListSymbol("\u2022")
                .setSymbolIndent(12);

        for (int i = 0; i < cntCuentas; i++) {
            cuentasPDF.add(new ListItem(cuentas[i]));
        }
        document.add(cuentasPDF);
        document.add(new Paragraph(""));
        document.add(new Paragraph(""));

        // Agregar la sección de firma del correo
        document.add(new Paragraph("Recibí conforme:"));
        document.add(new Paragraph(""));
        document.add(new Paragraph("_____________________________")
                .setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph("Firma del correo")
                .setTextAlignment(TextAlignment.RIGHT));
        document.close();

        // Devolver el archivo PDF creado        
        return devolver("remito.pdf");
    }
    
    public File crearRecepcion(String fechaRemito, String nombreCorreo, String[] cuentas, int cntCuentas, String numeroRemito, String rutaDespliegue) {

        PdfWriter writer = null;
        try {
            writer = new PdfWriter("remito.pdf");
        } catch (FileNotFoundException ex) {
            System.out.println("No se puede crear el archivo remito.pdf");
        }

        // Crear un documento PDF 
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
        document.setMargins(50, 50, 50, 50);

        // Obtener la imagen del logo
        Image logo = null;
        try {
            logo = new Image(ImageDataFactory.create(rutaDespliegue + "img\\tarjeta.jpg"));
        } catch (Exception ex) {
            System.out.println("No se puede encontrar la imagen tarjeta.jpg");
        }

        // Agregar una tabla para colocar allí el logo y el título del documento 
        Table table = new Table(new float[]{1, 8})
                .setBorder(null)
                .useAllAvailableWidth();
        table.addCell(new Cell()
                .setBorder(null)
                .add(new Paragraph("")
                        .add(logo)));
        table.addCell(new Cell()
                .setBorder(null)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .add(new Paragraph("INFORME DE RECEPCION DE TARJETAS")
                        .setBold()
                        .setTextAlignment(TextAlignment.CENTER)
                ));
        document.add(table);

        // Agregar una tabla para colocar allí el número de remito, el nombre del correo y la fecha
        table = new Table(new float[]{1, 1})
                .setBorder(Border.NO_BORDER);
        table.addCell(new Cell()
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph("Rendición Nº:")
                        .setBold()));
        table.addCell(new Cell()
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph(numeroRemito)));
        
        table.addCell(new Cell()
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph("Fecha:")
                        .setBold()));
        table.addCell(new Cell()
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph(fechaRemito)));

        document.add(new Paragraph(""));
        document.add(table);

        // Agregar un párrafo
        document.add(new Paragraph("En el día de la fecha, se reciben del correo " + nombreCorreo + " las tarjetas que se indican a continuación:").setMarginTop(10));
        document.add(new Paragraph(""));

        // Agregar el listado de cuentas
        com.itextpdf.layout.element.List cuentasPDF = new com.itextpdf.layout.element.List()
                .setListSymbol("\u2022")
                .setSymbolIndent(12);

        for (int i = 0; i < cntCuentas; i++) {
            cuentasPDF.add(new ListItem(cuentas[i]));
        }
        document.add(cuentasPDF);        
        document.close();

        // Devolver el archivo PDF creado        
        return devolver("remito.pdf");
    }
    
    public boolean verificarFirma(String alias, String keystorePassword, String keyStore) {
    
        try {
            // Cargar el archivo de almacén de claves (keystore)
            KeyStore keystore2 = KeyStore.getInstance("PKCS12");
            keystore2.load(new FileInputStream(keyStore), keystorePassword.toCharArray());

            // Obtener la clave privada asociada con el alias
            KeyStore.ProtectionParameter protectionParam = new KeyStore.PasswordProtection(keystorePassword.toCharArray());
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keystore2.getEntry(alias, protectionParam);

            if (privateKeyEntry != null) {
                // El alias y la contraseña son correctos
                System.out.println("Alias y contraseña correctos.");
                return true;
            } else {
                // El alias o la contraseña son incorrectos
                System.out.println("Alias o contraseña incorrectos.");
                return false;
            }
        } catch (Exception e) {
            // Manejar excepciones, por ejemplo, si el archivo de keystore no es válido
            System.out.println(e.getMessage());            
            return false;
        }
    }

    public File firmarPDF(String nombreArchivo, String documentoPDF, String alias, String keystorePassword, String keyStore) {

        // Ruta del archivo PDF de destino        
        String outputFilePath = "remitoSigned.pdf";

        // Configurar Bouncy Castle como proveedor de seguridad
        BouncyCastleProvider provider = new BouncyCastleProvider();
        Security.addProvider(provider);

        // Cargar el archivo de almacén de claves (keystore) y obtener la clave privada y el certificado        
        KeyStore keystore;
        try {
            keystore = KeyStore.getInstance("PKCS12");
            keystore.load(new FileInputStream(keyStore), keystorePassword.toCharArray());
            PrivateKey privateKey = (PrivateKey) keystore.getKey(alias, keystorePassword.toCharArray());
            Certificate[] chain = keystore.getCertificateChain(alias);

            // System.out.println("Firma obtenida correctamente");

            // Crear un objeto PdfSigner para firmar el documento
            // System.out.println("Documento PDF: " + documentoPDF);
            PdfReader pdfReader = new PdfReader(documentoPDF);
            // System.out.println("Pdf reader creado correctamente");

            // PdfReader pdfReader = new PdfReader(inputFilePath);
            PdfSigner pdfSigner = new PdfSigner(pdfReader, new FileOutputStream(outputFilePath), new StampingProperties());

            // System.out.println("PdfSigner inicializado correctamente");

            // Configurar apariencia de la firma
            Rectangle rect = new Rectangle(36, 0, 200, 100);
            pdfSigner.getSignatureAppearance()                    
                    .setReuseAppearance(false)
                    .setPageRect(rect)
                    .setLayer2FontSize(8)
                    .setPageNumber(1);

            // Configurar contenedor de firma
            pdfSigner.setFieldName("Firma");
            String digestAlgorithm = DigestAlgorithms.SHA256;
            IExternalSignature pks = new PrivateKeySignature(privateKey, digestAlgorithm, provider.getName());
            IExternalDigest digest = new BouncyCastleDigest();
            pdfSigner.signDetached(digest, pks, chain, null, null, null, 0, PdfSigner.CryptoStandard.CMS);
            System.out.println("Firma electrónica aplicada correctamente.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Devolver el archivo PDF creado
        return devolver(outputFilePath);
    }

    private File devolver(String origen) {
        // Devolver el archivo PDF creado
        File archivoTemp = null;

        try {
            InputStream inputStream = new FileInputStream(origen);
            archivoTemp = File.createTempFile("temp", ".pdf");
            FileOutputStream fos = new FileOutputStream(archivoTemp);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            inputStream.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return archivoTemp;
    }
}
