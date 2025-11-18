package gimnasiouq.model;

import java.util.Objects;

//atributos
    public class ReservaClase {
        private String clase;
        private String horario;
        private String entrenador;
        private String fecha;
        private String identificacionUsuario;
        private int cupoMaximo;

//constructor
    public ReservaClase(String clase, String horario, String entrenador, String fecha) {
        this.clase = clase;
        this.horario = horario;
        this.entrenador = entrenador;
        this.fecha = fecha;
        this.cupoMaximo = 5; // Cupo máximo predeterminado
    }

//get y set
    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(String entrenador) {
        this.entrenador = entrenador;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIdentificacion() {
        return identificacionUsuario;
    }

    public void setIdentificacion(String identificacionUsuario) {
        this.identificacionUsuario = identificacionUsuario;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

//to string
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservaClase that)) return false;
        return cupoMaximo == that.cupoMaximo &&
                Objects.equals(clase, that.clase) &&
                Objects.equals(horario, that.horario) &&
                Objects.equals(fecha, that.fecha) &&
                Objects.equals(identificacionUsuario, that.identificacionUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clase, horario, fecha, identificacionUsuario, cupoMaximo);
    }

    @Override
    public String toString() {
        return clase + " - " + horario + " (" + fecha + ")" + " - " + identificacionUsuario + " - Cupo: " + cupoMaximo;
    }
}
