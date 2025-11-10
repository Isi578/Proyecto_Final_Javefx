package gimnasiouq.model;
import java.time.LocalDate;

    public class MembresiaVIP extends Membresia {

//constructor
    public MembresiaVIP(double costo, LocalDate inicio, LocalDate fin) {
        super("VIP", costo, inicio, fin, true, true);
    }

//metodo para obtener los beneficios de la membresia
        public String obtenerBeneficios() {
            return "• Acceso general al gimnasio, clases grupales y entrenador personalizado";
        }
}
