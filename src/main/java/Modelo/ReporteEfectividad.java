package Modelo;

public class ReporteEfectividad {
    
    private String mes;
    private int piezas;
    private int entregadas;
    private int devueltas;
    private String porcentaje_efectividad;
    private String porcentaje_devueltas;

    public ReporteEfectividad() {
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public int getPiezas() {
        return piezas;
    }

    public void setPiezas(int piezas) {
        this.piezas = piezas;
    }

    public int getEntregadas() {
        return entregadas;
    }

    public void setEntregadas(int entregadas) {
        this.entregadas = entregadas;
    }

    public int getDevueltas() {
        return devueltas;
    }

    public void setDevueltas(int devueltas) {
        this.devueltas = devueltas;
    }

    public String getPorcentaje_efectividad() {
        return porcentaje_efectividad;
    }

    public void setPorcentaje_efectividad(String porcentaje_efectividad) {
        this.porcentaje_efectividad = porcentaje_efectividad;
    }

    public String getPorcentaje_devueltas() {
        return porcentaje_devueltas;
    }

    public void setPorcentaje_devueltas(String porcentaje_devueltas) {
        this.porcentaje_devueltas = porcentaje_devueltas;
    }
    
    
    
}
