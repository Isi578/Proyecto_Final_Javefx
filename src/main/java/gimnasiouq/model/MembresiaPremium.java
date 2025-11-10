package gimnasiouq.model;

import java.time.LocalDate;

    public class MembresiaPremium extends Membresia {

//constructor
    public MembresiaPremium(double costo, LocalDate inicio, LocalDate fin) {
        super("Premium", costo, inicio, fin, true, true);
    }

//metodo para obtener los beneficios de la membresia
    public String obtenerBeneficios() {
        return "• Acceso general al gimnasio y clases grupales";
    }
}