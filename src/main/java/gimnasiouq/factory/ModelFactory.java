package gimnasiouq.factory;

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

    public static ModelFactory getInstance(){
        if (modelFactory == null){
            modelFactory = new ModelFactory();
        }
        return modelFactory;
    }

    private ModelFactory() {
        gimnasioUQ = DataUtil.inicializarDatos();
        listaUsuariosObservable = FXCollections.observableArrayList(gimnasioUQ.getListaUsuarios());
    }

    public ObservableList<Usuario> obtenerUsuariosObservable() {
        listaUsuariosObservable.setAll(gimnasioUQ.getListaUsuarios());
        return listaUsuariosObservable;
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
}
