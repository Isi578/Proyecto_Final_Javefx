package gimnasiouq.factory;

import gimnasiouq.model.*;
import gimnasiouq.util.DataUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Optional;

public class ModelFactory {
    private Gimnasio gimnasio;

    private static ModelFactory instance;

    private ModelFactory() {
        this.gimnasio = DataUtil.inicializarDatos();
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

     public ObservableList<Usuario> obtenerUsuariosObservable() {
         return FXCollections.observableArrayList(gimnasio.getListaUsuarios());
     }

    public ObservableList<Entrenador> obtenerEntrenadorObservable() {
        return FXCollections.observableArrayList(gimnasio.getListaEntrenador());
     }

    public List<Entrenador> obtenerEntrenadores() {
        return gimnasio.getListaEntrenador();
    }

    public ObservableList<ControlAcceso> obtenerRegistrosObservable() {
        return FXCollections.observableArrayList(gimnasio.getListaRegistrosAcceso());
    }

    public boolean eliminarRegistro(ControlAcceso registro) {
        return gimnasio.getListaRegistrosAcceso().remove(registro);
    }

    public Optional<Usuario> buscarUsuario(String id) {
        return gimnasio.buscarUsuario(id);
    }

    public boolean validarIngresoUsuario(String identificacion) {
        return gimnasio.validarIngresoUsuario(identificacion);
    }

    public boolean registrarIngresoUsuario(String identificacion) {
        return gimnasio.registrarIngresoUsuario(identificacion);
    }

    public void actualizarReportes() {
    }
}
