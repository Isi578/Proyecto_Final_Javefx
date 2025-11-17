package gimnasiouq.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Clase principal del modelo que gestiona todas las listas de datos.
public class Gimnasio {
    private List<Usuario> listaUsuarios;
    private List<Recepcion> listaRecepcionista;
    private List<Administrador> listaAdministrador;
    private List<Entrenador> listaEntrenador;
    private List<ControlAcceso> listaRegistrosAcceso;

//constructor
    public Gimnasio() {
        this.listaUsuarios = new ArrayList<>();
        this.listaRecepcionista = new ArrayList<>();
        this.listaAdministrador = new ArrayList<>();
        this.listaEntrenador = new ArrayList<>();
        this.listaRegistrosAcceso = new ArrayList<>();
    }

//get y set
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
    
//crud del usuario

// metodo para verificar exixstencia de usuario
    public boolean verificarUsuario(String id) {
        return listaUsuarios.stream().anyMatch(u -> u.getIdentificacion().equals(id));
    }

//metodo para el registro de usuarios
    public Usuario registrarUsuario(Usuario usuario) throws Exception {
        if (verificarUsuario(usuario.getIdentificacion())) {
            throw new Exception("El usuario con ID: " + usuario.getIdentificacion() + " ya se encuentra registrado.");
        }
        this.listaUsuarios.add(usuario);
        return usuario;
    }

//metodo para buscar usuario con id
    public Optional<Usuario> buscarUsuario(String id) {
        return listaUsuarios.stream().filter(u -> u.getIdentificacion().equals(id)).findFirst();
    }

//metodo para actualizar el ususario con el id
    public Usuario actualizarUsuario(String id, Usuario usuarioInfo) throws Exception {
        Usuario usuario = buscarUsuario(id)
                .orElseThrow(() -> new Exception("Usuario no encontrado con el ID: " + id));

        usuario.setNombre(usuarioInfo.getNombre());
        usuario.setEdad(usuarioInfo.getEdad());
        usuario.setCelular(usuarioInfo.getCelular());
        // No se actualiza el ID, ya que es el identificador único.
        return usuario;
    }

//metodo para eliminar un usuario con el id
    public void eliminarUsuario(String id) throws Exception {
        boolean removed = this.listaUsuarios.removeIf(usuario -> usuario.getIdentificacion().equals(id));
        if (!removed) {
            throw new Exception("No se pudo eliminar. No existe un usuario con el ID: " + id);
        }
    }

//crud de la membresia

//metodo para asignar membresia a un usuario   
    public Usuario asignarMembresiaUsuario(String usuarioId, Membresia membresia) throws Exception {
        Usuario usuario = buscarUsuario(usuarioId)
                .orElseThrow(() -> new Exception("Usuario no encontrado con el ID: " + usuarioId));
        usuario.setMembresiaObj(membresia);
        return usuario;
    }

//metodo para actualizar una membresia
    public Usuario actualizarMembresiaUsuario(String usuarioId, Membresia membresia) throws Exception {
        return asignarMembresiaUsuario(usuarioId, membresia);
    }
    
//metodo para eliminar una membresia    
    public Usuario eliminarMembresiaUsuario(String usuarioId) throws Exception {
        Usuario usuario = buscarUsuario(usuarioId)
                .orElseThrow(() -> new Exception("Usuario no encontrado con el ID: " + usuarioId));
        usuario.setMembresiaObj(null);
        return usuario;
    }
    
//metodo para obtener la membresia de un usuario
    public Optional<Membresia> obtenerMembresiaUsuario(String usuarioId) throws Exception {
        Usuario usuario = buscarUsuario(usuarioId)
                .orElseThrow(() -> new Exception("Usuario no encontrado con el ID: " + usuarioId));
        return Optional.ofNullable(usuario.getMembresiaObj());
    }

//metodo para calcular la membresia por plan de un usuario
    public Membresia calcularMembresiaPorPlan(String tipoPlan, String tipoMembresia, Usuario usuario) {
        if (tipoPlan == null || tipoPlan.isEmpty() || usuario == null) return null;

        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin;
        double costo = 0;
        boolean costoAsignado = false;

        String plan = tipoPlan.trim().toLowerCase();
        String tier = (tipoMembresia == null || tipoMembresia.isBlank()) ? "basica" : tipoMembresia.trim().toLowerCase();

        switch (plan) {
            case "mensual" -> fechaFin = fechaInicio.plusMonths(1);
            case "trimestral" -> fechaFin = fechaInicio.plusMonths(3);
            case "anual" -> fechaFin = fechaInicio.plusYears(1);
            default -> {
                return null; // Si el plan no es válido, no se puede calcular.
            }
        }

        if (tier.equals("basica") || tier.equalsIgnoreCase("básica")) {
            switch (plan) {
                case "mensual" -> costo = 10000;
                case "trimestral" -> costo = 30000;
                case "anual" -> costo = 100000;
            }
            costoAsignado = true;
        } else if (tier.equals("premium")) {
            switch (plan) {
                case "mensual" -> costo = 15000;
                case "trimestral" -> costo = 40000;
                case "anual" -> costo = 150000;
            }
            costoAsignado = true;
        } else if (tier.equals("vip")) {
            switch (plan) {
                case "mensual" -> costo = 20000;
                case "trimestral" -> costo = 50000;
                case "anual" -> costo = 200000;
            }
            costoAsignado = true;
        }

        // Si el tipo de membresía no coincide con ninguno, no se puede calcular.
        if (!costoAsignado) {
            return null;
        }

        if (usuario instanceof Estudiante) {
            costo *= 0.90; // 10% de descuento
        } else if (usuario instanceof Trabajador) {
            costo *= 0.80; // 20% de descuento
        }

        if (tier.equals("basica") || tier.equalsIgnoreCase("básica")) {
            return new MembresiaBasica(costo, fechaInicio, fechaFin, true, true);
        } else if (tier.equals("premium")) {
            return new MembresiaPremium(costo, fechaInicio, fechaFin);
        } else if (tier.equals("vip")) {
            return new MembresiaVIP(costo, fechaInicio, fechaFin);
        }

        // Se retorna null para evitar crear una membresía incorrecta.
        return null;
    }

// CRUD del Entrenador

// metodo para verificar existencia de entrenador
    public boolean verificarEntrenador(String identificacion) {
        return listaEntrenador.stream().anyMatch(e -> e.getIdentificacion().equals(identificacion));
    }

    public boolean agregarEntrenador(Entrenador entrenador) {
        if (verificarEntrenador(entrenador.getIdentificacion())) {
            return false;
        }
        return listaEntrenador.add(entrenador);
    }

//metodo para buscar un entrenador
    public Optional<Entrenador> buscarEntrenador(String identificacion) {
        return listaEntrenador.stream().filter(e -> e.getIdentificacion().equals(identificacion)).findFirst();
    }

//metodo para actualizar un entrenador
    public Entrenador actualizarEntrenador(String id, Entrenador entrenadorInfo) throws Exception {
        Entrenador entrenador = buscarEntrenador(id)
                .orElseThrow(() -> new Exception("Entrenador no encontrado con el ID: " + id));

        entrenador.setNombre(entrenadorInfo.getNombre());
        entrenador.setEdad(entrenadorInfo.getEdad());
        entrenador.setCorreo(entrenadorInfo.getCorreo());
        entrenador.setCargo(entrenadorInfo.getCargo());
        return entrenador;
    }

//metodo para eliminar un entrenador
    public boolean eliminarEntrenador(String identificacion) throws Exception {
        boolean removed = this.listaEntrenador.removeIf(entrenador -> entrenador.getIdentificacion().equals(identificacion));
        if (!removed) {
            throw new Exception("No se pudo eliminar. No existe un entrenador con el ID: " + identificacion);
        }
        return removed;
    }
}
