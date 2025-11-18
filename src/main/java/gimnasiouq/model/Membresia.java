package gimnasiouq.model;

import java.time.LocalDate;
import java.time.LocalTime;

//atributos
public abstract class Membresia {
    private String tipo;
    private double costo;
    private LocalDate inicio;
    private LocalDate fin;
    private boolean activa;
    private boolean accesoGym;
    private final LocalDate fechaPago;
    private final LocalTime horaPago;

//constructor
    public Membresia(String tipo, double costo, LocalDate inicio, LocalDate fin, boolean activa) {
        this.tipo = tipo;
        this.costo = costo;
        this.inicio = inicio;
        this.fin = fin;
        this.activa = activa;
        this.fechaPago = LocalDate.now();
        this.horaPago = LocalTime.now();
    }

    public Membresia() {
        this.fechaPago = LocalDate.now();
        this.horaPago = LocalTime.now();
    }

// Getters y Setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public boolean isAccesoGym() {
        return accesoGym;
    }

    public void setAccesoGym(boolean accesoGym) {
        this.accesoGym = accesoGym;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public LocalTime getHoraPago() {
        return horaPago;
    }

// metodo membresia vigente
    public boolean estaVigente() {
        LocalDate hoy = LocalDate.now();
        return activa && fin != null && (fin.isAfter(hoy) || fin.isEqual(hoy));
    }
//metodo dias restantes de la membresia
    public long diasRestantes() {
        LocalDate hoy = LocalDate.now();
        if (fin == null || hoy.isAfter(fin)) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.DAYS.between(hoy, fin);
    }

//to string
    @Override
    public String toString() {
        return tipo + " - $" + costo + " (Vigente hasta: " + fin + ")";
    }

    public abstract String obtenerBeneficios();

}
