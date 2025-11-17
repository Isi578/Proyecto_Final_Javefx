package gimnasiouq.model;

import java.util.Objects;

public class Entrenador {

// Atributos
    private String nombre;
    private String identificacion;
    private String edad;
    private String correo;
    private String cargo;

// Constructores
    public Entrenador(String nombre, String identificacion, String edad, String correo, String cargo) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.edad = edad;
        this.correo = correo;
        this.cargo = cargo;
    }

    public Entrenador(String e1, String daniel, String fuerza) {
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }
    public String getEdad() { return edad; }
    public void setEdad(String edad) { this.edad = edad; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

//to string
    @Override
    public String toString() {
        return "Entrenador{" +
                "nombre='" + nombre + '\'' +
                ", identificacion='" + identificacion + '\'' +
                ", edad=" + edad + '\'' +
                ", correo='" + correo + '\'' +
                ", cargo='" + cargo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entrenador that = (Entrenador) o;
        return Objects.equals(identificacion, that.identificacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificacion);
    }
}
