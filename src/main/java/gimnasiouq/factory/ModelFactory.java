package gimnasiouq.factory;

import gimnasiouq.model.*;
import gimnasiouq.util.DataUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

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

    // Métodos eliminados:
    // public ObservableList<Usuario> obtenerUsuariosObservable() {
    //     return gimnasio.getListaUsuarios();
    // }
    //
    // public ObservableList<Entrenador> obtenerEntrenadorObservable() {
    //     return FXCollections.observableArrayList(gimnasio.getListaEntrenador());
    // }

    public List<Entrenador> obtenerEntrenadores() {
        return gimnasio.getListaEntrenador();
    }

    public void actualizarReportes() {
        // Lógica para actualizar reportes si es necesario
    }
}
