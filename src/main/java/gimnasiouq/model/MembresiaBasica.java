package gimnasiouq.model;

import java.time.LocalDate;

public class MembresiaBasica extends Membresia {

    boolean accesoGeneral;

    public MembresiaBasica(String tipo, double costo, LocalDate fechaInicio, LocalDate fechaFin, boolean estado, boolean accesoGeneral) {
        super(tipo, costo, fechaInicio, fechaFin, estado);
        this.accesoGeneral = accesoGeneral;
    }

    public MembresiaBasica(boolean accesoGeneral) {
        this.accesoGeneral = accesoGeneral;
    }

    public MembresiaBasica(double costo, LocalDate inicio, LocalDate fin) {
        super("Basica", costo, inicio, fin, true);
        this.accesoGeneral = true;
    }

    public boolean isAccesoGeneral() {
        return accesoGeneral;
    }

    public void setAccesoGeneral(boolean accesoGeneral) {
        this.accesoGeneral = accesoGeneral;
    }

    public String obtenerBeneficios() {
        return "• Acceso general al gimnasio";
    }
}
