package gimnasiouq.model;

import java.time.LocalDate;

public class MembresiaBasica extends Membresia {

//constructor
    public MembresiaBasica(double costo, LocalDate inicio, LocalDate fin) {
        super("Basica", costo, inicio, fin, true);
        setAccesoGym(true);
    }
//to string
    @Override
    public String obtenerBeneficios() {
        return "• Acceso general a las instalaciones del gimnasio.";
    }
}
