package gimnasiouq.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class EntrenadorTest {

    @Test
    void testRegistrarEntrenador() {
        Gimnasio s = new Gimnasio();

        Entrenador e = new Entrenador("E1", "Daniel", "Fuerza");
        s.registrarEntrenador(e);

        assertEquals(e, s.buscarEntrenador("E1"));
    }

    @Test
    void testEliminarEntrenador() throws Exception {
        Gimnasio s = new Gimnasio();

        Entrenador e = new Entrenador("E1", "Lucia", "Yoga");
        s.registrarEntrenador(e);

        s.eliminarEntrenador("E1");

        assertNull(s.buscarEntrenador("E1"));
    }

    @Test
    void testAsignarEntrenadorAClase() {
        Gimnasio s = new Gimnasio();

        Entrenador e = new Entrenador("E1", "Hernán", "Spinning");
        Clase c = new Clase("C1", "Spinning", "Grupal", "5:00 PM", 15);

        s.registrarEntrenador(e);
        s.agregarClase(c);

        s.asignarEntrenadorAClase("E1", "C1");

        assertEquals(e, c.getEntrenador());
    }
}
