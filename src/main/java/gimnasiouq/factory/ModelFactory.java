package gimnasiouq.factory;

import gimnasiouq.model.ControlAcceso;
import gimnasiouq.model.Entrenador;
import gimnasiouq.model.Gimnasio;
import gimnasiouq.model.Usuario;
import gimnasiouq.util.DataUtil;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Optional;

public class ModelFactory {
    private static ModelFactory modelFactory;
    private final Gimnasio gimnasioUQ;

    private final ObservableList<Usuario> listaUsuariosObservable;
    private final ObservableList<Entrenador> listaEntrenadoresObservable;
    private final ObservableList<ControlAcceso> listaRegistrosObservable;

    // Propiedades para los reportes
    private final IntegerProperty totalUsuariosProperty;
    private final IntegerProperty membresiasActivasProperty;
    private final IntegerProperty membresiasInactivasProperty;
    private final IntegerProperty totalMembresiasProperty;
    private final DoubleProperty ingresosTotalesProperty;
    private final StringProperty claseMasReservadaProperty;
    private final IntegerProperty totalClasesReservadasProperty;


    public static ModelFactory getInstance(){
        if (modelFactory == null){
            modelFactory = new ModelFactory();
        }
        return modelFactory;
    }

    private ModelFactory() {
        gimnasioUQ = DataUtil.inicializarDatos();
        listaUsuariosObservable = FXCollections.observableArrayList(gimnasioUQ.getListaUsuarios());
        listaEntrenadoresObservable = FXCollections.observableArrayList(gimnasioUQ.getListaEntrenador());
        listaRegistrosObservable = FXCollections.observableArrayList(gimnasioUQ.getListaRegistrosAcceso());

        // Inicializar propiedades
        totalUsuariosProperty = new SimpleIntegerProperty();
        membresiasActivasProperty = new SimpleIntegerProperty();
        membresiasInactivasProperty = new SimpleIntegerProperty();
        totalMembresiasProperty = new SimpleIntegerProperty();
        ingresosTotalesProperty = new SimpleDoubleProperty();
        claseMasReservadaProperty = new SimpleStringProperty();
        totalClasesReservadasProperty = new SimpleIntegerProperty();

        actualizarReportes();
    }

    public void actualizarReportes() {
        totalUsuariosProperty.set(gimnasioUQ.contarTotalUsuarios());
        membresiasActivasProperty.set(gimnasioUQ.contarMembresiasUsuariosActivas());
        membresiasInactivasProperty.set(gimnasioUQ.contarMembresiasUsuariosInactivas());
        totalMembresiasProperty.set(gimnasioUQ.contarTotalMembresias());
        ingresosTotalesProperty.set(gimnasioUQ.calcularIngresosTotales());
        claseMasReservadaProperty.set(gimnasioUQ.contarClaseMasReservada());
        totalClasesReservadasProperty.set(gimnasioUQ.contarTotalClasesReservadas());
    }


    public Gimnasio getGimnasio() {
        return gimnasioUQ;
    }

    public ObservableList<Usuario> obtenerUsuariosObservable() {
        listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios());
        return listaUsuariosObservable;
    }

    public ObservableList<Entrenador> obtenerEntrenadorObservable() {
        listaEntrenadoresObservable.setAll(gimnasioUQ.getListaEntrenador());
        return listaEntrenadoresObservable;
    }

    public ObservableList<ControlAcceso> obtenerRegistrosObservable() {
        listaRegistrosObservable.setAll(gimnasioUQ.getListaRegistrosAcceso());
        return listaRegistrosObservable;
    }

    //<editor-fold desc="Getters para Propiedades de Reportes">
    public IntegerProperty totalUsuariosProperty() {
        return totalUsuariosProperty;
    }

    public IntegerProperty membresiasActivasProperty() {
        return membresiasActivasProperty;
    }

    public IntegerProperty membresiasInactivasProperty() {
        return membresiasInactivasProperty;
    }

    public IntegerProperty totalMembresiasProperty() {
        return totalMembresiasProperty;
    }

    public DoubleProperty ingresosTotalesProperty() {
        return ingresosTotalesProperty;
    }

    public StringProperty claseMasReservadaProperty() {
        return claseMasReservadaProperty;
    }

    public IntegerProperty totalClasesReservadasProperty() {
        return totalClasesReservadasProperty;
    }
    //</editor-fold>

    //<editor-fold desc="CRUD Usuario">
    public Usuario registrarUsuario(Usuario usuario) throws Exception {
        Usuario nuevoUsuario = gimnasioUQ.registrarUsuario(usuario);
        obtenerUsuariosObservable(); // Actualiza la lista observable
        actualizarReportes();
        return nuevoUsuario;
    }

    public Usuario actualizarUsuario(String id, Usuario usuarioInfo) throws Exception {
        Usuario usuarioActualizado = gimnasioUQ.actualizarUsuario(id, usuarioInfo);
        obtenerUsuariosObservable(); // Actualiza la lista observable
        actualizarReportes();
        return usuarioActualizado;
    }

    public void eliminarUsuario(String id) throws Exception {
        gimnasioUQ.eliminarUsuario(id);
        obtenerUsuariosObservable(); // Actualiza la lista observable
        actualizarReportes();
    }

    public Optional<Usuario> buscarUsuario(String id) {
        return gimnasioUQ.buscarUsuario(id);
    }
    //</editor-fold>

    //<editor-fold desc="CRUD Entrenador">
    public boolean agregarEntrenador(Entrenador entrenador) {
        boolean agregado = gimnasioUQ.agregarEntrenador(entrenador);
        if (agregado) {
            obtenerEntrenadorObservable();
        }
        return agregado;
    }

    public Entrenador actualizarEntrenador(String identificacion, Entrenador entrenador) throws Exception {
        Entrenador actualizado = gimnasioUQ.actualizarEntrenador(identificacion, entrenador);
        if (actualizado != null) {
            obtenerEntrenadorObservable();
        }
        return actualizado;
    }

    public boolean eliminarEntrenador(String identificacion) throws Exception {
        boolean eliminado = gimnasioUQ.eliminarEntrenador(identificacion);
        if (eliminado) {
            obtenerEntrenadorObservable();
        }
        return eliminado;
    }
    //</editor-fold>

    //<editor-fold desc="Control Acceso">
    public boolean validarIngresoUsuario(String identificacion) {
        return gimnasioUQ.validarIngresoUsuario(identificacion);
    }

    public boolean registrarIngresoUsuario(String identificacion) {
        boolean registrado = gimnasioUQ.registrarIngresoUsuario(identificacion);
        if (registrado) {
            obtenerRegistrosObservable();
            actualizarReportes();
        }
        return registrado;
    }

    public boolean eliminarRegistro(ControlAcceso registro) {
        boolean eliminado = gimnasioUQ.getListaRegistrosAcceso().remove(registro);
        if (eliminado) {
            obtenerRegistrosObservable();
        }
        return eliminado;
    }
    //</editor-fold>
}
