package Modelo;

import java.sql.Date;

public class Movimiento {
    
    private int id, cliente, pieza;
    private Date Fecha;
    private Estado movimiento;
    private Ubicacion ubicacion;
    private Motivo motivo;
    private Operador operador;
    private String documento;

    public Movimiento() {
    }

    public Movimiento(int id, int cliente, Date Fecha, Estado movimiento, Ubicacion ubicacion, Motivo motivo, Operador operador, String documento) {
        this.id = id;
        this.cliente = cliente;
        this.Fecha = Fecha;
        this.movimiento = movimiento;
        this.ubicacion = ubicacion;
        this.motivo = motivo;
        this.operador = operador;
        this.documento = documento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public Estado getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(Estado movimiento) {
        this.movimiento = movimiento;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Motivo getMotivo() {
        return motivo;
    }

    public void setMotivo(Motivo motivo) {
        this.motivo = motivo;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public int getPieza() {
        return pieza;
    }

    public void setPieza(int pieza) {
        this.pieza = pieza;
    }

    @Override
    public String toString() {
        return "Movimiento{" + "id=" + id + ", cliente=" + cliente + ", pieza=" + pieza + ", Fecha=" + Fecha + ", movimiento=" + movimiento + ", ubicacion=" + ubicacion + ", motivo=" + motivo + ", operador=" + operador + ", documento=" + documento + '}';
    }
    
    
    
}
