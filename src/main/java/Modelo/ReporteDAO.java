package Modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReporteDAO {

    Conexion cn = new Conexion();
    Connection con;
    CallableStatement stmt;
    ResultSet rs;

    public List<ArrayList<Object>> generarReporte(String sql) {

        con = cn.Conexion();
        List<ArrayList<Object>> reporte = new ArrayList<>();
        List<String> nombresCampos = new ArrayList<>();
        List<Object> nombresCamposObjects = new ArrayList<>();
        List<ArrayList<Object>> valoresCampos = new ArrayList<>();
        System.out.println("SQL:" + sql);
        try {
            stmt = con.prepareCall(sql);
            rs = stmt.executeQuery();

            // Obtener los nombres de los campos
            ResultSetMetaData metaData = rs.getMetaData();
            nombresCampos = obtenerNombresCampos(metaData);

            // Obtener los valores de los campos
            valoresCampos = obtenerValoresCampos(rs);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        for (String nombreCampo : nombresCampos) {
            nombresCamposObjects.add((Object) nombreCampo);
        }
        reporte.add((ArrayList<Object>) nombresCamposObjects);
        reporte.addAll(valoresCampos);

        // Imprimir el reporte
        /*
        System.out.println("Reporte:");
        for (ArrayList<Object> fila : reporte) {
            for (Object valor : fila) {
                System.out.print(valor + "\t");
            }
            System.out.println();
        }
         */
        
        return reporte;
    }

    private List<String> obtenerNombresCampos(ResultSetMetaData metaData) {
        ArrayList<String> nombresCampos = new ArrayList<>();
        try {
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                nombresCampos.add(metaData.getColumnName(i));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return nombresCampos;
    }

    private List<ArrayList<Object>> obtenerValoresCampos(ResultSet rs) {
        ArrayList<ArrayList<Object>> valoresCampos = new ArrayList<>();
        ResultSetMetaData metaData;
        try {
            metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                ArrayList<Object> fila = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    fila.add(rs.getObject(i));
                }
                valoresCampos.add(fila);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return valoresCampos;
    }
}
