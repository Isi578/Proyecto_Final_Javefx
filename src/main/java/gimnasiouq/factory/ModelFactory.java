package gimnasiouq.factory;

import gimnasiouq.model.Entrenador;
import gimnasiouq.model.Gimnasio;
import gimnasiouq.model.Usuario;
import gimnasiouq.util.DataUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Optional;

public class ModelFactory {
    private static ModelFactory modelFactory;
    private final Gimnasio gimnasioUQ;

    private final ObservableList<Usuario> listaUsuariosObservable;
    private final ObservableList<Entrenador> listaEntrenadoresObservable;

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

    //<editor-fold desc="CRUD Usuario">
    public Usuario registrarUsuario(Usuario usuario) throws Exception {
        Usuario nuevoUsuario = gimnasioUQ.registrarUsuario(usuario);
        obtenerUsuariosObservable(); // Actualiza la lista observable
        return nuevoUsuario;
    }

    public Usuario actualizarUsuario(String id, Usuario usuarioInfo) throws Exception {
        Usuario usuarioActualizado = gimnasioUQ.actualizarUsuario(id, usuarioInfo);
        obtenerUsuariosObservable(); // Actualiza la lista observable
        return usuarioActualizado;
    }

    public void eliminarUsuario(String id) throws Exception {
        gimnasioUQ.eliminarUsuario(id);
        obtenerUsuariosObservable(); // Actualiza la lista observable
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
}
