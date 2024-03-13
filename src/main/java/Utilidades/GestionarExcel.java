package Utilidades;

import Modelo.Tarjeta;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class GestionarExcel {

    public byte[] exportar(List<Tarjeta> objectList, String exportType) {
        Workbook workbook = null;
        switch (exportType) {
            case "XLS":
                workbook = new HSSFWorkbook(); // HSSFWorkbook for XLS
                break;
            case "XLSX":
                workbook = new XSSFWorkbook(); // XSSFWorkbook for XLSX
                break;
        }
        try {
            Sheet sheet = workbook.createSheet("tarjetas");

            // Iterar sobre la lista de objetos y sus propiedades
            int rowNum = 0;
            for (Tarjeta object : objectList) {
                Row row = sheet.createRow(rowNum++);
                int cellNum = 0;
                for (String property : getObjectProperties(object)) {
                    Cell cell = row.createCell(cellNum++);
                    cell.setCellValue(property);
                }
            }

            // Convertir el libro de trabajo a un array de bytes
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();
            return outputStream.toByteArray();
        } catch (IOException e) {
            System.out.println("Error en la creación del archivo Excel.");
            System.out.println(e.toString());
            return null;
        }
    }

    // Método para obtener las propiedades de un objeto
    private List<String> getObjectProperties(Tarjeta tarjeta) {
        List<String> properties = new ArrayList<>();
        // Obtener todas las propiedades de la clase Tarjeta
        Field[] fields = Tarjeta.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                // Obtener el valor de cada propiedad
                Object value = field.get(tarjeta);
                // Convertir el valor a String y agregarlo a la lista de propiedades
                properties.add(value != null ? value.toString() : "");
            } catch (IllegalAccessException e) {
                System.out.println("Error en la obtención de propiedades del objeto.");
                System.out.println(e.toString());
            }
        }
        return properties;
    }

}
