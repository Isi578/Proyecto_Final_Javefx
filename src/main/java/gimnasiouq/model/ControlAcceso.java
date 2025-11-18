package gimnasiouq.model;

import java.time.LocalDate;
import java.time.LocalTime;

//atributos
public class ControlAcceso {
    private LocalDate fecha;
    private LocalTime hora;
    private String nombre;
    private String identificacion;
    private String tipoMembresia;
    private boolean estado;

//constructor
    public ControlAcceso(LocalDate fecha, LocalTime hora, String nombre, String identificacion, String tipoMembresia, boolean estado) {
        this.fecha = fecha;
        this.hora = hora;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.tipoMembresia = tipoMembresia;
        this.estado = estado;
    }

//get y set
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTipoMembresia() {
        return tipoMembresia;
    }

    public void setTipoMembresia(String tipoMembresia) {
        this.tipoMembresia = tipoMembresia;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

//to string
    @Override
    public String toString() {
        return "ControlAcceso{" +
                "fecha=" + fecha +
                ", hora=" + hora +
                ", nombre='" + nombre + '\'' +
                ", identificacion='" + identificacion + '\'' +
                ", tipoMembresia='" + tipoMembresia + '\'' +
                ", estado=" + estado +
                '}';
    }
}
