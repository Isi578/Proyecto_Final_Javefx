package gimnasiouq.model;

import java.time.LocalDate;

//atrubutos
    public class MembresiaBasica extends Membresia {


//cosntructor
    public MembresiaBasica(double costo, LocalDate fechaInicio, LocalDate fechaFin, boolean estado, boolean accesoGeneral) {
        super("Basica", costo, fechaInicio, fechaFin, true, true);
    }

//metodo para obtener los beneficios de la membresia
    public String obtenerBeneficios() {
        return "• Acceso general al gimnasio";
    }
}
