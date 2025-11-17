package gimnasiouq.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class MembresiaTest {

    @Test
    void testAsignarMembresia() {
        Usuario u = new Usuario("101", "Carlos", 22, "310112233", "Externo");
        Membresia m = new Membresia("Mensual", 50000, LocalDate.now()) {
            @Override
            public String obtenerBeneficios() {
                return "";
            }
        };

        u.asignarMembresia(m);

        assertTrue(u.tieneMembresiaActiva());
    }

    @Test
    void testAsignarMembresiaUsuarioInexistente() {
        Gimnasio s = new Gimnasio();

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            s.asignarMembresia("999", new Membresia("Mensual", 50000, LocalDate.now()) {
                @Override
                public String obtenerBeneficios() {
                    return "";
                }
            });
        });

        assertEquals("Usuario no encontrado", ex.getMessage());
    }

    @Test
    void testFechasMembresiaMensual() {
        LocalDate inicio = LocalDate.of(2024, 1, 1);
        Membresia m = new Membresia("Mensual", 60000, inicio) {
            @Override
            public String obtenerBeneficios() {
                return "";
            }
        };

        assertEquals(LocalDate.of(2024, 2, 1), m.getFechaFin());
    }

}
