package gimnasiouq.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {

    private String nombre;
    private String identificacion;
    private int edad;
    private String celular;
    private String tipoMembresia;
    private Membresia membresiaObj;
    private final List<ReservaClase> reservas = new ArrayList<>();

    public Usuario(String nombre, String identificacion, int edad, String celular, String membresia) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.edad = edad;
        this.celular = celular;
        this.tipoMembresia = membresia;
        this.membresiaObj = null;
    }

    public Usuario() {

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


    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getMembresia() {
        return tipoMembresia;
    }

    public void setMembresia(String membresia) {
        this.tipoMembresia = membresia;
    }

    public List<ReservaClase> getReservas() {
        return reservas;
    }

    public String getTipoMembresia() {
        return tipoMembresia;
    }

    public void setTipoMembresia(String tipoMembresia) {
        this.tipoMembresia = tipoMembresia;
    }

    public Membresia getMembresiaObj() {
        return membresiaObj;
    }

    public void setMembresiaObj(Membresia membresiaObj) {
        this.membresiaObj = membresiaObj;
        if (membresiaObj != null) {
            this.tipoMembresia = membresiaObj.getTipo();
        }
    }

    public Membresia getMembresiaActiva() {
        return membresiaObj;
    }

    public void setMembresiaActiva(Membresia membresia) {
        setMembresiaObj(membresia);
    }

    public LocalDate getFechaInicioMembresia() {
        return membresiaObj != null ? membresiaObj.getInicio() : null;
    }

    public LocalDate getFechaFinMembresia() {
        return membresiaObj != null ? membresiaObj.getFin() : null;
    }

    public double getCostoMembresia() {
        return membresiaObj != null ? membresiaObj.getCosto() : 0.0;
    }

    public String getCostoMembresiaFormateado() {
        return membresiaObj != null ? String.format("$%.0f", membresiaObj.getCosto()) : "$0";
    }

    public String getPlanMembresia() {
        if (membresiaObj == null) return "Sin plan";

        LocalDate inicio = membresiaObj.getInicio();
        LocalDate fin = membresiaObj.getFin();

        if (inicio == null || fin == null) return "Sin plan";

        long meses = ChronoUnit.MONTHS.between(inicio, fin);

        if (meses <= 1) return "Mensual";
        if (meses <= 3) return "Trimestral";
        if (meses <= 12) return "Anual";
        return "Personalizado";
    }

    public boolean tieneMembresiaActiva() {
        return membresiaObj != null && membresiaObj.estaVigente();
    }

    public boolean membresiaVencida() {
        return membresiaObj != null && !membresiaObj.estaVigente();
    }

    public String getEstadoMembresia() {
        if (membresiaObj == null) {
            return "NO ACTIVA";
        }
        return membresiaObj.estaVigente() ? "ACTIVA" : "NO ACTIVA";
    }

    public String getFechaInicioFormateada() {
        if (membresiaObj == null || membresiaObj.getInicio() == null) {
            return "Sin fecha";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return membresiaObj.getInicio().format(formatter);
    }

    public String getFechaFinFormateada() {
        if (membresiaObj == null || membresiaObj.getFin() == null) {
            return "Sin fecha";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return membresiaObj.getFin().format(formatter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(identificacion, usuario.identificacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificacion);
    }
}
