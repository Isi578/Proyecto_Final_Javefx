package gimnasiouq.model;

import java.util.Objects;

public class Entrenador {

// Atributos
    private String nombre;
    private String id;
    private int edad;
    private String correo;
    private String cargo;

// Constructores
    public Entrenador(String nombre, String id, int edad, String correo, String cargo) {
        this.nombre = nombre;
        this.id = id;
        this.edad = edad;
        this.correo = correo;
        this.cargo = cargo;
    }

// Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

//to string
    @Override
    public String toString() {
        return "Entrenador{" +
                "nombre='" + nombre + '\'' +
                ", id='" + id + '\'' +
                ", edad=" + edad +
                ", correo='" + correo + '\'' +
                ", cargo='" + cargo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entrenador that = (Entrenador) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
