package gimnasiouq.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Gimnasio {
    private List<Usuario> listaUsuarios;
    private List<Recepcion> listaRecepcionista;
    private List<Administrador> listaAdministrador;
    private List<Entrenador> listaEntrenador;
    private List<ControlAcceso> listaRegistrosAcceso;

    public Gimnasio() {
        this.listaUsuarios = new ArrayList<>();
        this.listaRecepcionista = new ArrayList<>();
        this.listaAdministrador = new ArrayList<>();
        this.listaEntrenador = new ArrayList<>();
        this.listaRegistrosAcceso = new ArrayList<>();
    }

    //<editor-fold desc="CRUD Usuario">
    public boolean verificarUsuario(String id) {
        return listaUsuarios.stream().anyMatch(u -> u.getIdentificacion().equals(id));
    }

    public Usuario registrarUsuario(Usuario usuario) throws Exception {
        if (verificarUsuario(usuario.getIdentificacion())) {
            throw new Exception("El usuario con ID: " + usuario.getIdentificacion() + " ya se encuentra registrado.");
        }
        this.listaUsuarios.add(usuario);
        return usuario;
    }

    public Optional<Usuario> buscarUsuario(String id) {
        return listaUsuarios.stream().filter(u -> u.getIdentificacion().equals(id)).findFirst();
    }

    public Usuario actualizarUsuario(String id, Usuario usuarioInfo) throws Exception {
        Usuario usuario = buscarUsuario(id)
                .orElseThrow(() -> new Exception("Usuario no encontrado con el ID: " + id));

        usuario.setNombre(usuarioInfo.getNombre());
        usuario.setEdad(usuarioInfo.getEdad());
        usuario.setCelular(usuarioInfo.getCelular());
        // No se actualiza el ID, ya que es el identificador único.

        return usuario;
    }

    public void eliminarUsuario(String id) throws Exception {
        boolean removed = this.listaUsuarios.removeIf(usuario -> usuario.getIdentificacion().equals(id));
        if (!removed) {
            throw new Exception("No se pudo eliminar. No existe un usuario con el ID: " + id);
        }
    }
    //</editor-fold>

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<Recepcion> getListaRecepcionista() {
        return listaRecepcionista;
    }

    public void setListaRecepcionista(List<Recepcion> listaRecepcionista) {
        this.listaRecepcionista = listaRecepcionista;
    }

    public List<Administrador> getListaAdministrador() {
        return listaAdministrador;
    }

    public void setListaAdministrador(List<Administrador> listaAdministrador) {
        this.listaAdministrador = listaAdministrador;
    }

    public List<Entrenador> getListaEntrenador() {
        return listaEntrenador;
    }

    public void setListaEntrenador(List<Entrenador> listaEntrenador) {
        this.listaEntrenador = listaEntrenador;
    }

    public List<ControlAcceso> getListaRegistrosAcceso() {
        return listaRegistrosAcceso;
    }

    public void setListaRegistrosAcceso(List<ControlAcceso> listaRegistrosAcceso) {
        this.listaRegistrosAcceso = listaRegistrosAcceso;
    }
}
