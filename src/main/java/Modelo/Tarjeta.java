package Modelo;

import java.sql.Date;
import java.util.List;

public class Tarjeta {
    
    private int id, cliente;
    private Date fechaEmision, fechaEstado, fechaImposicion, fechaRendicion;
    private Estado estado;
    private List<Movimiento> movimiento;
    private Motivo motivo;
    private Ubicacion ubicacion;

    public Tarjeta() {
    }

    public Tarjeta(int id, int cliente, Date fechaEmision, Date fechaEstado, Date fechaImposicion, Date fechaRendicion, Estado estado, List<Movimiento> movimiento) {
        this.id = id;
        this.cliente = cliente;
        this.fechaEmision = fechaEmision;
        this.fechaEstado = fechaEstado;
        this.fechaImposicion = fechaImposicion;
        this.fechaRendicion = fechaRendicion;
        this.estado = estado;
        this.movimiento = movimiento;
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

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaEstado() {
        return fechaEstado;
    }

    public void setFechaEstado(Date fechaEstado) {
        this.fechaEstado = fechaEstado;
    }

    public Date getFechaImposicion() {
        return fechaImposicion;
    }

    public void setFechaImposicion(Date fechaImposicion) {
        this.fechaImposicion = fechaImposicion;
    }

    public Date getFechaRendicion() {
        return fechaRendicion;
    }

    public void setFechaRendicion(Date fechaRendicion) {
        this.fechaRendicion = fechaRendicion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<Movimiento> getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(List<Movimiento> movimiento) {
        this.movimiento = movimiento;
    }

    public Motivo getMotivo() {
        return motivo;
    }

    public void setMotivo(Motivo motivo) {
        this.motivo = motivo;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return "Tarjeta{" + "id=" + id + ", cliente=" + cliente + ", fechaEmision=" + fechaEmision + ", fechaEstado=" + fechaEstado + ", fechaImposicion=" + fechaImposicion + ", fechaRendicion=" + fechaRendicion + ", estado=" + estado + ", movimiento=" + movimiento + ", motivo=" + motivo + ", ubicacion=" + ubicacion + '}';
    }
    
    
    
}

