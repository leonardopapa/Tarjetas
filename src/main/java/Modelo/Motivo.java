package Modelo;

public class Motivo {
    
    private int id;
    private String nombre;

    public Motivo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Motivo{" + "id=" + id + ", nombre=" + nombre + '}';
    }
    
    
    
}
