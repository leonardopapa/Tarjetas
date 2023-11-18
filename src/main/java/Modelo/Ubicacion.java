package Modelo;

public class Ubicacion {

    private int id;
    private String nombre;
    private Tipo tipo;

    public Ubicacion() {
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

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Ubicacion{" + "id=" + id + ", nombre=" + nombre + ", tipo=" + tipo + '}';
    }
    
}
