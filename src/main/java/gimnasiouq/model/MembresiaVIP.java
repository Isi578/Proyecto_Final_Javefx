package gimnasiouq.model;
import java.time.LocalDate;

public class MembresiaVIP extends Membresia {

    public MembresiaVIP(double costo, LocalDate inicio, LocalDate fin) {
        super("VIP", costo, inicio, fin, true);
    }
}
