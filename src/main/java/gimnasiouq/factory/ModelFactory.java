package gimnasiouq.factory;

import gimnasiouq.model.*;

public class ModelFactory {
    private final ObservableList<ControlAcceso> listaRegistrosObservable;
    private final Gimnasio gimnasio;
    private static ModelFactory instance;

    private ModelFactory() {
        this.gimnasio = new Gimnasio();
        inicializarDatos();
    }

    public static ModelFactory getInstance() {
        if (instance == null) {
            instance = new ModelFactory();
        }
        return instance;
    }

    public Gimnasio getGimnasio() {
        return gimnasio;
    }

    private void inicializarDatos() {
        try {
            Usuario estudiante = new Estudiante("Juan Perez", "111", 20, "311111", "Estudiante", 0.10);
            Usuario trabajador = new Trabajador("Ana Gomez", "222", 35, "322222", "Trabajador", "Beneficios especiales");
            Usuario externo = new Externo("Carlos Diaz", "333", 40, "333333", "Externo");

            gimnasio.registrarUsuario(estudiante);
            gimnasio.registrarUsuario(trabajador);
            gimnasio.registrarUsuario(externo);

            Membresia membresiaJuan = gimnasio.calcularMembresiaPorPlan("Mensual", "Basica", estudiante);
            gimnasio.asignarMembresiaUsuario("111", membresiaJuan);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Administrador admin = new Administrador("admin", "admin123");
        Recepcion recepcionista = new Recepcion("recep", "recep123");

        gimnasio.getListaAdministrador().add(admin);
        gimnasio.getListaRecepcionista().add(recepcionista);
    }

    public void actualizarReportes() {
    }
}
