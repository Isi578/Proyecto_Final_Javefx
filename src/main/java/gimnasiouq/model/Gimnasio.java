package gimnasiouq.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

// Clase principal del modelo que gestiona todas las listas de datos.
public class Gimnasio {
    private final ObservableList<Usuario> listaUsuarios; // Cambiado a ObservableList
    private Recepcion recepcionista;
    private Administrador administrador;
    private final List<Entrenador> listaEntrenador;
    private final ObservableList<ControlAcceso> listaRegistrosAcceso; // Cambiado a ObservableList
    private final ObservableList<ReservaClase> listaReservasClases; // Cambiado a ObservableList

//constructor
    public Gimnasio() {
        this.listaUsuarios = FXCollections.observableArrayList(); // Inicializado como ObservableList
        this.recepcionista = null;
        this.administrador = null;
        this.listaEntrenador = new ArrayList<>();
        this.listaRegistrosAcceso = FXCollections.observableArrayList(); // Inicializado como ObservableList
        this.listaReservasClases = FXCollections.observableArrayList(); // Inicializado como ObservableList
    }

//get y set para los nuevos atributos
    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public Recepcion getRecepcionista() {
        return recepcionista;
    }

    public void setRecepcionista(Recepcion recepcionista) {
        this.recepcionista = recepcionista;
    }

//get y set existentes
    public ObservableList<Usuario> getListaUsuarios() { // Retorna ObservableList
        return listaUsuarios;
    }

    public List<Entrenador> getListaEntrenador() {
        return listaEntrenador;
    }

    public ObservableList<ControlAcceso> getListaRegistrosAcceso() { // Retorna ObservableList
        return listaRegistrosAcceso;
    }

    public ObservableList<ReservaClase> getListaReservasClases() { // Retorna ObservableList
        return listaReservasClases;
    }

//crud del usuario

// metodo para verificar exixstencia de usuario
    public boolean verificarUsuario(String id) {
        return listaUsuarios.stream().anyMatch(u -> u.getIdentificacion().equals(id));
    }

//metodo para el registro de usuarios
    public void registrarUsuario(Usuario usuario) throws Exception {
        if (verificarUsuario(usuario.getIdentificacion())) {
            throw new Exception("El usuario con ID: " + usuario.getIdentificacion() + " ya se encuentra registrado.");
        }
        this.listaUsuarios.add(usuario);
    }

//metodo para buscar usuario con id
    public Optional<Usuario> buscarUsuario(String id) {
        return listaUsuarios.stream().filter(u -> u.getIdentificacion().equals(id)).findFirst();
    }

    public Usuario buscarUsuarioPorIdentificacion(String identificacion) {
        return buscarUsuario(identificacion).orElse(null);
    }

//metodo para actualizar el ususario con el id
    public void actualizarUsuario(String id, Usuario usuarioInfo) throws Exception {
        Usuario usuario = buscarUsuario(id)
                .orElseThrow(() -> new Exception("Usuario no encontrado con el ID: " + id));

        usuario.setNombre(usuarioInfo.getNombre());
        usuario.setEdad(usuarioInfo.getEdad());
        usuario.setCelular(usuarioInfo.getCelular());
        usuario.setTipoMembresia(usuarioInfo.getTipoMembresia()); // Actualizar el tipo de membresía
        // No se actualiza el ID, ya que es el identificador único.
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
    public void asignarMembresiaUsuario(String usuarioId, Membresia membresia) throws Exception {
        Usuario usuario = buscarUsuario(usuarioId)
                .orElseThrow(() -> new Exception("Usuario no encontrado con el ID: " + usuarioId));
        usuario.setMembresiaObj(membresia);
    }

//metodo para eliminar una membresia
    public void eliminarMembresiaUsuario(String usuarioId) throws Exception {
        Usuario usuario = buscarUsuario(usuarioId)
                .orElseThrow(() -> new Exception("Usuario no encontrado con el ID: " + usuarioId));
        usuario.setMembresiaObj(null);
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
            return new MembresiaBasica(costo, fechaInicio, fechaFin);
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

    public void agregarEntrenador(Entrenador entrenador) throws Exception {
        if (verificarEntrenador(entrenador.getIdentificacion())) {
            throw new Exception("El entrenador con ID: " + entrenador.getIdentificacion() + " ya se encuentra registrado.");
        }
        listaEntrenador.add(entrenador);
    }

//metodo para buscar un entrenador
    public Optional<Entrenador> buscarEntrenador(String identificacion) {
        return listaEntrenador.stream().filter(e -> e.getIdentificacion().equals(identificacion)).findFirst();
    }

//metodo para actualizar un entrenador
    public void actualizarEntrenador(String id, Entrenador entrenadorInfo) throws Exception {
        Entrenador entrenador = buscarEntrenador(id)
                .orElseThrow(() -> new Exception("Entrenador no encontrado con el ID: " + id));

        entrenador.setNombre(entrenadorInfo.getNombre());
        entrenador.setEdad(entrenadorInfo.getEdad());
        entrenador.setCorreo(entrenadorInfo.getCorreo());
        entrenador.setCargo(entrenadorInfo.getCargo());
    }

//metodo para eliminar un entrenador
    public void eliminarEntrenador(String identificacion) throws Exception {
        boolean removed = this.listaEntrenador.removeIf(entrenador -> entrenador.getIdentificacion().equals(identificacion));
        if (!removed) {
            throw new Exception("No se pudo eliminar. No existe un entrenador con el ID: " + identificacion);
        }
    }

//CRUD de control de acceso

    public void agregarRegistroAcceso(ControlAcceso registro) {
        listaRegistrosAcceso.add(registro);
    }

//metodo para registrar el ingreso del usuario
    public void registrarIngresoUsuario(String identificacion) {
        Usuario u = buscarUsuarioPorIdentificacion(identificacion);
        if (u != null && u.tieneMembresiaActiva()) {
            Membresia membresia = u.getMembresiaObj();
            ControlAcceso registro = new ControlAcceso(
                    LocalDate.now(),
                    LocalTime.now(),
                    u.getNombre(),
                    u.getIdentificacion(),
                    membresia.getTipo(),
                    true
            );
            agregarRegistroAcceso(registro);
        }
    }

//CRUD de reportes

//metodo para contar la clase mas reservada
    public String contarClaseMasReservada() {
        if (listaReservasClases.isEmpty()) {
            return "Ninguna";
        }
        Map<String, Integer> conteoClases = new HashMap<>();
        for (ReservaClase reserva : listaReservasClases) {
            String clase = reserva.getClase();
            conteoClases.put(clase, conteoClases.getOrDefault(clase, 0) + 1);
        }

        String claseMasReservada = null;
        int maxReservas = 0;
        for (Map.Entry<String, Integer> entry : conteoClases.entrySet()) {
            if (entry.getValue() > maxReservas) {
                maxReservas = entry.getValue();
                claseMasReservada = entry.getKey();
            }
        }
        return claseMasReservada;
    }

//metodo para contar el total de clases reservadas
    public int contarTotalClasesReservadas() {
        return listaReservasClases.size();
    }

    public String autenticarEmpleado(String usuario, String contrasena) {
        // Verificar si el administrador existe y las credenciales coinciden
        if (administrador != null && administrador.getNombreUsuario().equals(usuario) && administrador.getContrasena().equals(contrasena)) {
            return "ADMINISTRADOR";
        }

        // Verificar si el recepcionista existe y las credenciales coinciden
        if (recepcionista != null && recepcionista.getNombreUsuario().equals(usuario) && recepcionista.getContrasena().equals(contrasena)) {
            return "RECEPCIONISTA";
        }

        return null;
    }
}
