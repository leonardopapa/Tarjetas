package Utilidades;

import Modelo.Tarjeta;
import Modelo.TarjetaDAO;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportController2 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener parámetros de filtrado                
        String cuenta = request.getParameter("cuenta");
        String estado = request.getParameter("selEstado");
        String ubicacion = request.getParameter("selUbicacion");
        String desde = request.getParameter("desde");
        String hasta = request.getParameter("hasta");              

        // Generar lista de tarjetas filtrada                                
        TarjetaDAO tdao = new TarjetaDAO();
        List<Tarjeta> listat = tdao.filtrar(cuenta, estado, ubicacion, desde, hasta);

        // Generar archivo Excel
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
        response.setHeader("Content-Disposition", "attachment; filename=piezas." + exportType.toLowerCase());

        Sheet sheet = workbook.createSheet("Hoja1");
        
        // Crear las celdas de título
            String [] titulos = {"Pieza", "Cuenta", "Fecha de Emisión", "Estado", "Fecha de Ultimo Estado", "Motivo", "Ubicacion"};
            int rowNum = 0;
            Row row = sheet.createRow(rowNum++);
            Cell cell = null;
            int cellNum = 0;            
            for (String encabezados: titulos) {
                cell = row.createCell(cellNum++);
                cell.setCellValue(encabezados);
            }
            
            // Iterar sobre la lista de tarjetas y construir el Excel
            for (Tarjeta pieza : listat) {
                row = sheet.createRow(rowNum++);
                cellNum = 0;                
                cell = row.createCell(cellNum++);
                cell.setCellValue(pieza.getPieza());
                cell = row.createCell(cellNum++);
                cell.setCellValue(pieza.getCliente());
                cell = row.createCell(cellNum++);
                cell.setCellValue(pieza.getFechaEmision());
                cell = row.createCell(cellNum++);
                cell.setCellValue(pieza.getEstado().getNombre());
                cell = row.createCell(cellNum++);
                cell.setCellValue(pieza.getFechaEstado());
                cell = row.createCell(cellNum++);
                cell.setCellValue(pieza.getMotivo().getNombre());
                cell = row.createCell(cellNum++);
                cell.setCellValue(pieza.getUbicacion().getNombre());
                }
            
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        workbook.close();
    }
    
}
