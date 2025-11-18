package gimnasiouq.model;

import java.time.LocalDate;

    public class MembresiaVIP extends Membresia {

//constructor
    public MembresiaVIP(double costo, LocalDate inicio, LocalDate fin) {
        super("VIP", costo, inicio, fin, true);
        setAccesoGym(true);
    }

//to string
    @Override
    public String obtenerBeneficios() {
        return "• Acceso ilimitado a todas las instalaciones.\n" +
               "• Acceso a todas las clases grupales.\n" +
               "• Entrenamiento personalizado incluido.\n" +
               "• Acceso a la zona de spa y bienestar.";
    }
}
