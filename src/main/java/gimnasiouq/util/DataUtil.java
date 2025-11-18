package gimnasiouq.util;

import gimnasiouq.model.*;

public class DataUtil {

    public static final String ADMINISTRADOR = "Administrador";
    public static final String ADMIN_CONTRASENA = "123";
    public static final String RECEPCIONISTA = "Recepcionista";
    public static final String RECEP_CONTRASENA = "123";

    public static Gimnasio inicializarDatos() {
        Gimnasio gimnasioUQ = new Gimnasio();

        Usuario usuario1 = new Externo("Jose", "1094887139", 45, "3248054175", "Basica");
        Usuario usuario2 = new Estudiante("Martin", "1094887140", 28, "3110000000", "Premium", 0.1);
        Usuario usuario3 = new Trabajador("Paola", "1094887141", 32, "3001111111", "VIP", "Seguro de salud");

        gimnasioUQ.getListaUsuarios().add(usuario1);
        gimnasioUQ.getListaUsuarios().add(usuario2);
        gimnasioUQ.getListaUsuarios().add(usuario3);

        return gimnasioUQ;
    }
}
