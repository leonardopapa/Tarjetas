package Utilidades;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportController extends HttpServlet {
    
    /* Convierte una tabla de datos en un archivo Excel
        Recibe como parámetros:
        a) el tipo de archivo de salida (exportTpye="XLS" o "XLSX")
        b) el título de las columnas, separadas por comas
        c) los datos, separados por comas        
    */ 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Workbook workbook = null;
        String exportType = request.getParameter("exportType");
        switch (exportType) {
            case "XLS":
                response.setContentType("application/vnd.ms-excel");
                workbook = new HSSFWorkbook(); // HSSFWorkbook for XLS
                break;
            case "XLSX":
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                workbook = new XSSFWorkbook(); // XSSFWorkbook for XLSX
                break;
        }
        response.setHeader("Content-Disposition", "attachment; filename=informe." + exportType.toLowerCase());
        
        Sheet sheet = workbook.createSheet("Hoja1");

        // Obtener los parámetros del formulario
        String[] columnas = request.getParameter("columna").split(",");
        String[] datos = request.getParameter("dato").split(",");

        // Crear la fila de encabezado
        int cols = columnas.length;        
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < cols; i++) {
            headerRow.createCell(i).setCellValue(columnas[i]);
        }

        // Crear las filas de datos
        int cntDatos = datos.length;
        int filas = (int) Math.ceil (cntDatos / cols);
        System.out.println("columnas:" + cols);
        System.out.println("filas:" + filas);
        System.out.println("datos:" + cntDatos);
        System.out.println("");
        int contador = 0;
        for (int i = 1; i < filas + 1; i++) {
            Row dataRow = sheet.createRow(i);
            for (int j = 0; j < cols; j++) {                
                System.out.println("fila:" + i);
                System.out.println("columna:" + j);
                System.out.println("contador:" + contador);
                System.out.println("Dato:" + datos[contador]);
                System.out.println("");                
                dataRow.createCell(j).setCellValue(datos[contador++]);
            }
        }

        OutputStream out = response.getOutputStream();
        workbook.write(out);
        workbook.close();
    }

}