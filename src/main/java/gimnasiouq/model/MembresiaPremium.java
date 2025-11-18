package gimnasiouq.model;

import java.time.LocalDate;

    public class MembresiaPremium extends Membresia {

//constructor
    public MembresiaPremium(double costo, LocalDate inicio, LocalDate fin) {
        super("Premium", costo, inicio, fin, true);
        setAccesoGym(true);
    }

//to string
    @Override
    public String obtenerBeneficios() {
        return "• Acceso general a las instalaciones.\n" +
               "• Acceso a todas las clases grupales.";
    }
}
