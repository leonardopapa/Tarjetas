package Utilidades;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

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
        
        File archivoTemp = null;
        
        try {
            InputStream inputStream = new FileInputStream("remito.pdf");
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

    public File firmarPDF(File documentoPDF) {
        File temp = new File("temp.pdf");
        return temp;
    }

}