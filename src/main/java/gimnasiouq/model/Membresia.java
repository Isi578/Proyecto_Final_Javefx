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
    private  boolean accesoGym;
    private LocalDate fechaPago;
    private LocalTime horaPago;

//constructor
    public Membresia(String tipo, double costo, LocalDate inicio, LocalDate fin, boolean activa, boolean accesoGym) {
        this.tipo = tipo;
        this.costo = costo;
        this.inicio = inicio;
        this.fin = fin;
        this.activa = activa;
        this.accesoGym = accesoGym;
        this.fechaPago = LocalDate.now();
        this.horaPago = LocalTime.now();
    }

    public Membresia(String mensual, int i, LocalDate now) {
        this.fechaPago = LocalDate.now();
        this.horaPago = LocalTime.now();
    }

//get y set
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
    public void setAccesoGym(boolean accesoGeneral) {
        this.accesoGym = accesoGym;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }
    public LocalTime getHoraPago() {
        return horaPago;
    }

//metodo para definir vigencia de la memebresia
    public boolean estaVigente() {
        LocalDate hoy = LocalDate.now();
        return activa && (fin.isAfter(hoy) || fin.isEqual(hoy));
    }
//metodo para saber los dias que quedan de a membresia
    public long diasRestantes() {
        LocalDate hoy = LocalDate.now();
        if (hoy.isAfter(fin)) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.DAYS.between(hoy, fin);
    }

//to string
    @Override
    public String toString() {
        return "Membresia{" +
                "tipo='" + tipo + '\'' +
                ", costo=" + costo +
                ", inicio=" + inicio +
                ", fin=" + fin +
                ", activa=" + activa +
                ", accesoGym=" + accesoGym +
                ", fechaPago=" + fechaPago +
                ", horaPago=" + horaPago +
                '}';
    }
//metodo para obtener los beneficios de la membresia
    public abstract String obtenerBeneficios();

    public String getTipoMembresia() {
        return tipo;
    }

}
