package gimnasiouq.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UsuarioTest {

    @Test
    void testRegistrarUsuarioCorrecto() {
        Usuario u = new Usuario("101", "Carlos López", 25, "3124567890", "Estudiante");

        assertEquals("101", u.getId());
        assertEquals("Carlos López", u.getNombre());
    }

    @Test
    void testRegistrarUsuarioSinNombre() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new Usuario("102", "", 22, "3112223333", "Externo"));

        assertEquals("El nombre no puede estar vacío", ex.getMessage());
    }

    @Test
    void testRegistrarUsuarioIDDuplicado() throws Exception {
        Gimnasio s = new Gimnasio();

        Usuario u1 = new Usuario("101", "Juan", 20, "3103334444", "Externo");
        Usuario u2 = new Usuario("101", "Pedro", 30, "3115556666", "Estudiante");

        s.registrarUsuario(u1);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> s.registrarUsuario(u2));

        assertEquals("El usuario con ese ID ya existe", ex.getMessage());
    }
}

