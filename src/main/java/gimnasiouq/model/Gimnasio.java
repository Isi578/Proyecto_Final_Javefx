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

    public boolean asignarMembresiaUsuario(String identificacionUsuario, Membresia membresia) {
        if (identificacionUsuario == null || identificacionUsuario.isEmpty() || !validarMembresia(membresia)) return false;
        Usuario usuario = buscarUsuarioPorIdentificacion(identificacionUsuario);
        if (usuario == null) return false;
        usuario.setMembresiaActiva(membresia);
        return true;
    }

    public boolean actualizarMembresiaUsuario(String identificacionUsuario, Membresia membresia) {
        return asignarMembresiaUsuario(identificacionUsuario, membresia);
    }

    public boolean eliminarMembresiaUsuario(String identificacionUsuario) {
        if (identificacionUsuario == null || identificacionUsuario.isEmpty()) return false;
        Usuario usuario = buscarUsuarioPorIdentificacion(identificacionUsuario);
        if (usuario == null) return false;
        if (usuario.getMembresiaActiva() == null) return false;
        usuario.setMembresiaActiva(null);
        return true;
    }

    public Membresia obtenerMembresiaUsuario(String identificacionUsuario) {
        Usuario usuario = buscarUsuarioPorIdentificacion(identificacionUsuario);
        return usuario != null ? usuario.getMembresiaActiva() : null;
    }

    public Membresia calcularMembresiaPorPlan(String tipoPlan, String tipoMembresia, Usuario usuario) {
        if (tipoPlan == null || tipoPlan.isEmpty() || usuario == null) return null;

        java.time.LocalDate fechaInicio = java.time.LocalDate.now();
        java.time.LocalDate fechaFin;
        double costo = 0;

        String plan = tipoPlan.trim().toLowerCase();
        String tier = (tipoMembresia == null || tipoMembresia.isBlank()) ? "basica" : tipoMembresia.trim().toLowerCase();

        switch (plan) {
            case "mensual" -> fechaFin = fechaInicio.plusMonths(1);
            case "trimestral" -> fechaFin = fechaInicio.plusMonths(3);
            case "anual" -> fechaFin = fechaInicio.plusYears(1);
            default -> {
                return null;
            }
        }

        if (tier.equals("basica") || tier.equalsIgnoreCase("básica")) {
            switch (plan) {
                case "mensual" -> costo = 10000;
                case "trimestral" -> costo = 30000;
                case "anual" -> costo = 100000;
            }
        } else if (tier.equals("premium")) {
            switch (plan) {
                case "mensual" -> costo = 15000;
                case "trimestral" -> costo = 40000;
                case "anual" -> costo = 150000;
            }
        } else if (tier.equals("vip")) {
            switch (plan) {
                case "mensual" -> costo = 20000;
                case "trimestral" -> costo = 50000;
                case "anual" -> costo = 200000;
            }
        }

        if (usuario instanceof Estudiante) {
            costo *= 0.90; // 10% de descuento
        } else if (usuario instanceof TrabajadorUQ) {
            costo *= 0.80; // 20% de descuento
        }

        if (tier.equals("basica") || tier.equalsIgnoreCase("básica")) {
            return new MembresiaBasica(costo, fechaInicio, fechaFin);
        } else if (tier.equals("premium")) {
            return new MembresiaPremium(costo, fechaInicio, fechaFin);
        } else if (tier.equals("vip")) {
            return new MembresiaVIP(costo, fechaInicio, fechaFin);
        }

        return new MembresiaBasica(costo, fechaInicio, fechaFin);
    }
}
