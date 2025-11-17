package gimnasiouq.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class AccessoTest {

    @Test
    void testAccesoPermitido() throws Exception {
        Gimnasio s = new Gimnasio();

        Usuario u = new Usuario("101", "Camilo", 21, "312334455", "Estudiante");
        u.asignarMembresia(new Membresia("Mensual", 50000, LocalDate.now()) {
            @Override
            public String obtenerBeneficios() {
                return "";
            }
        });

        s.registrarUsuario(u);

        assertTrue(s.validarAcceso("101"));
    }

    @Test
    void testAccesoDenegadoPorUsuarioInactivo() throws Exception {
        Gimnasio s = new Gimnasio();

        Usuario u = new Usuario("101", "Roberto", 31, "311221133", "Externo");
        s.registrarUsuario(u);

        assertFalse(s.validarAcceso("101"));
    }

}
