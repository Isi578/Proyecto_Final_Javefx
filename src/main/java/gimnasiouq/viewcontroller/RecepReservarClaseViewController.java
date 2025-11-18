package gimnasiouq.viewcontroller;

import gimnasiouq.factory.ModelFactory;
import gimnasiouq.model.*;
import gimnasiouq.util.ReservaValidationResult;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RecepReservarClaseViewController implements Initializable {

    private Gimnasio gimnasio;
    private ObservableList<ReservaClase> listaReservas; // Lista de reservas del gimnasio
    private Usuario usuarioSeleccionado;

    @FXML
    private Button btnConfirmar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnNuevo;

    @FXML
    private TextField txtFecha; // Ahora es un TextField
    @FXML
    private ComboBox<String> comboBoxClase;
    @FXML
    private ComboBox<String> comboBoxEntrenador;
    @FXML
    private ComboBox<String> comboBoxHorario;
    @FXML
    private Label lblBeneficios;

    @FXML
    private TableView<Usuario> tableUsuario; // TableView para mostrar usuarios
    @FXML
    private TableColumn<Usuario, String> tcUsuario; // Tipo de usuario (Estudiante, Externo, Trabajador)
    @FXML
    private TableColumn<Usuario, String> tcNombre;
    @FXML
    private TableColumn<Usuario, String> tcIdentificacion;
    @FXML
    private TableColumn<Usuario, String> tcTipo; // Tipo de membresía del usuario
    @FXML
    private TableColumn<Usuario, String> tcClase; // Clase reservada por el usuario
    @FXML
    private TableColumn<Usuario, String> tcHorario; // Horario de la clase reservada
    @FXML
    private TableColumn<Usuario, String> tcFecha; // Fecha de la clase reservada
    @FXML
    private TableColumn<Usuario, String> tcEntrenador; // Entrenador de la clase reservada
    @FXML
    private TableColumn<Usuario, String> tcEstado; // Estado de la membresía del usuario


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.gimnasio = ModelFactory.getInstance().getGimnasio();
        this.listaReservas = FXCollections.observableArrayList(gimnasio.getListaReservasClases()); // Obtener la ObservableList directamente del modelo

        // Poblar ComboBoxes
        comboBoxClase.getItems().addAll("Yoga", "Spinning", "Boxeo", "Funcional"); // Añadido Boxeo y Funcional para consistencia
        comboBoxHorario.getItems().addAll("06:00", "08:00", "16:00", "18:00"); // Horarios consistentes
        comboBoxEntrenador.getItems().clear();
        comboBoxEntrenador.setDisable(true);

        initDataBinding();
        tableUsuario.setItems(FXCollections.observableArrayList(gimnasio.getListaUsuarios())); // Cargar usuarios en la tabla
        listenerSelection();
        lblBeneficios.setText("Seleccione un usuario");

        cargarEntrenadoresDisponibles(); // Cargar entrenadores inicialmente
    }

    private ReservaClase getReservaForUser(String identificacion) {
        return gimnasio.getListaReservasClases().stream()
                .filter(r -> r.getIdentificacion().equals(identificacion))
                .findFirst()
                .orElse(null);
    }

    private void initDataBinding() {
        // Columnas de Usuario
        if (tcNombre != null) {
            tcNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        }
        if (tcIdentificacion != null) {
            tcIdentificacion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdentificacion()));
        }
        if (tcTipo != null) {
            tcTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTipoMembresia())); // Usar getTipoMembresia()
        }
        if (tcUsuario != null) {
            tcUsuario.setCellValueFactory(cellData -> {
                if (cellData.getValue() instanceof Estudiante) {
                    return new SimpleStringProperty("Estudiante");
                } else if (cellData.getValue() instanceof Trabajador) {
                    return new SimpleStringProperty("Trabajador");
                } else {
                    return new SimpleStringProperty("Externo");
                }
            });
        }
        if (tcEstado != null) {
            tcEstado.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstadoMembresia()));
        }

        // Columnas de Reserva (se obtienen buscando la reserva para el usuario)
        if (tcClase != null) {
            tcClase.setCellValueFactory(c -> {
                ReservaClase reserva = getReservaForUser(c.getValue().getIdentificacion());
                return new SimpleStringProperty(reserva == null ? "Sin clase" : reserva.getClase());
            });
        }
        if (tcHorario != null) {
            tcHorario.setCellValueFactory(c -> {
                ReservaClase reserva = getReservaForUser(c.getValue().getIdentificacion());
                return new SimpleStringProperty(reserva == null ? "Sin horario" : reserva.getHorario());
            });
        }
        if (tcFecha != null) {
            tcFecha.setCellValueFactory(c -> {
                ReservaClase reserva = getReservaForUser(c.getValue().getIdentificacion());
                return new SimpleStringProperty(reserva == null ? "Sin fecha" : reserva.getFecha());
            });
        }
        if (tcEntrenador != null) {
            tcEntrenador.setCellValueFactory(c -> {
                ReservaClase reserva = getReservaForUser(c.getValue().getIdentificacion());
                return new SimpleStringProperty(reserva == null ? "Sin entrenador" : reserva.getEntrenador());
            });
        }
    }

    private void listenerSelection() {
        tableUsuario.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            usuarioSeleccionado = newSel;
            if (newSel != null) {
                mostrarBeneficios(newSel);
                prepararEntrenadoresSegunMembresia(newSel);

                ReservaClase reserva = getReservaForUser(newSel.getIdentificacion());
                if (reserva != null) {
                    comboBoxClase.setValue(reserva.getClase());
                    comboBoxHorario.setValue(reserva.getHorario());
                    txtFecha.setText(reserva.getFecha());
                    comboBoxEntrenador.setValue(reserva.getEntrenador());
                } else {
                    limpiarCamposReserva();
                }
            } else {
                lblBeneficios.setText("Seleccione un usuario");
                comboBoxEntrenador.getItems().clear();
                comboBoxEntrenador.setDisable(true);
                limpiarCamposReserva();
            }
        });
    }

    private void cargarEntrenadoresDisponibles() {
        // La lista de entrenadores se carga una vez y se actualiza al seleccionar un usuario VIP
        // No se necesita un listener global aquí, la actualización se maneja en prepararEntrenadoresSegunMembresia
    }

    private void prepararEntrenadoresSegunMembresia(Usuario usuario) {
        boolean esVIP = "VIP".equalsIgnoreCase(usuario.getTipoMembresia());
        comboBoxEntrenador.getItems().clear();
        comboBoxEntrenador.setDisable(!esVIP);

        if (esVIP) {
            List<Entrenador> entrenadores = gimnasio.getListaEntrenador(); // Usar directamente la lista del gimnasio

            if (entrenadores.isEmpty()) {
                comboBoxEntrenador.setDisable(true);
                mostrarAlerta("Sin entrenadores", "No hay entrenadores disponibles. Por favor, cree un entrenador primero en la sección de Gestión de Entrenadores.", Alert.AlertType.WARNING);
            } else {
                for (Entrenador e : entrenadores) {
                    comboBoxEntrenador.getItems().add(e.getNombre());
                }
            }
        }
    }

    private void mostrarBeneficios(Usuario usuario) {
        String tipoMembresia = usuario.getTipoMembresia();
        String beneficios;
        if (tipoMembresia == null || tipoMembresia.isEmpty()) {
            beneficios = "Sin membresía asignada";
        } else {
            switch (tipoMembresia.toLowerCase()) {
                case "basica" -> beneficios = "Acceso general a máquinas";
                case "premium" -> beneficios = "Acceso general a máquinas, clases grupales";
                case "vip" ->
                        beneficios = "Acceso ilimitado a máquinas, clases grupales ilimitadas, área de spa, entrenador personal";
                default -> beneficios = "Tipo de membresía no reconocido";
            }
        }
        lblBeneficios.setText(beneficios);
    }

    @FXML
    void onNuevo(ActionEvent event) {
        limpiarCampos();
        tableUsuario.getSelectionModel().clearSelection();
        usuarioSeleccionado = null;
    }

    @FXML
    void onConfirmar(ActionEvent event) {
        if (usuarioSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un usuario de la tabla.", Alert.AlertType.ERROR);
            return;
        }

        String clase = comboBoxClase.getValue();
        String horario = comboBoxHorario.getValue();
        String fechaIngresada = txtFecha.getText();
        String entrenador = comboBoxEntrenador.getValue();

        if (clase == null || clase.isEmpty() || horario == null || horario.isEmpty() || fechaIngresada == null || fechaIngresada.isEmpty()) {
            mostrarAlerta("Error", "Debe completar todos los campos de la reserva.", Alert.AlertType.ERROR);
            return;
        }

        boolean esVIP = "VIP".equalsIgnoreCase(usuarioSeleccionado.getTipoMembresia());
        if (esVIP && (entrenador == null || entrenador.isEmpty())) {
            mostrarAlerta("Error", "Los usuarios VIP deben seleccionar un entrenador.", Alert.AlertType.ERROR);
            return;
        }

        if (!esVIP) {
            entrenador = "Sin entrenador";
        }

        ReservaClase nuevaReserva = new ReservaClase(clase, horario, entrenador, fechaIngresada);
        nuevaReserva.setIdentificacion(usuarioSeleccionado.getIdentificacion());

        ReservaValidationResult result = validarYAgregarReserva(usuarioSeleccionado.getIdentificacion(), nuevaReserva);

        if (result == ReservaValidationResult.EXITO) {
            mostrarAlerta("Éxito", "Reserva creada exitosamente.", Alert.AlertType.INFORMATION);
            limpiarCampos();
            tableUsuario.refresh(); // Refrescar la tabla para actualizar las columnas de reserva
        } else {
            mostrarMensajeErrorReserva(result);
        }
    }

    private ReservaValidationResult validarYAgregarReserva(String identificacionUsuario, ReservaClase nuevaReserva) {
        if (identificacionUsuario == null || nuevaReserva == null) {
            return ReservaValidationResult.DATOS_RESERVA_INVALIDOS;
        }

        Optional<Usuario> usuarioOpt = gimnasio.buscarUsuario(identificacionUsuario);
        if (usuarioOpt.isEmpty()) {
            return ReservaValidationResult.USUARIO_NO_ENCONTRADO;
        }
        Usuario usuario = usuarioOpt.get();

        if (!usuario.tieneMembresiaActiva()) {
            return ReservaValidationResult.MEMBRESIA_INACTIVA;
        }

        // Validar formato de fecha
        LocalDate fechaReserva;
        try {
            fechaReserva = LocalDate.parse(nuevaReserva.getFecha(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            return ReservaValidationResult.FORMATO_FECHA_INVALIDO;
        }

        // Validar fecha en el pasado
        if (fechaReserva.isBefore(LocalDate.now())) {
            return ReservaValidationResult.FECHA_EN_PASADO;
        }

        // Validar que la fecha de reserva esté dentro del período de membresía
        if (usuario.getFechaInicioMembresia() != null && usuario.getFechaFinMembresia() != null) {
            if (fechaReserva.isBefore(usuario.getFechaInicioMembresia()) || fechaReserva.isAfter(usuario.getFechaFinMembresia())) {
                return ReservaValidationResult.FECHA_FUERA_MEMBRESIA;
            }
        }

        // Validar máximo de reservas por clase (ejemplo: 3 reservas por usuario para la misma clase)
        long reservasExistentes = gimnasio.getListaReservasClases().stream()
                .filter(r -> r.getIdentificacion().equals(identificacionUsuario) &&
                        r.getClase().equals(nuevaReserva.getClase()) &&
                        r.getFecha().equals(nuevaReserva.getFecha()))
                .count();

        if (reservasExistentes >= 3) { // Límite de 3 reservas por clase por usuario
            return ReservaValidationResult.EXCEDE_MAXIMO_RESERVAS;
        }

        // Si todas las validaciones pasan, agregar la reserva
        gimnasio.getListaReservasClases().add(nuevaReserva);
        return ReservaValidationResult.EXITO;
    }


    @FXML
    void onActualizar(ActionEvent event) {
        if (usuarioSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un usuario de la tabla.", Alert.AlertType.ERROR);
            return;
        }

        ReservaClase existingReserva = getReservaForUser(usuarioSeleccionado.getIdentificacion());
        if (existingReserva == null) {
            mostrarAlerta("Error", "El usuario seleccionado no tiene reservas para actualizar.", Alert.AlertType.ERROR);
            return;
        }

        String clase = comboBoxClase.getValue();
        String horario = comboBoxHorario.getValue();
        String fechaIngresada = txtFecha.getText();
        String entrenador = comboBoxEntrenador.getValue();

        if (clase == null || clase.isEmpty() || horario == null || horario.isEmpty() || fechaIngresada == null || fechaIngresada.isEmpty()) {
            mostrarAlerta("Error", "Debe completar todos los campos de la reserva.", Alert.AlertType.ERROR);
            return;
        }

        boolean esVIP = "VIP".equalsIgnoreCase(usuarioSeleccionado.getTipoMembresia());
        if (esVIP && (entrenador == null || entrenador.isEmpty())) {
            mostrarAlerta("Error", "Los usuarios VIP deben seleccionar un entrenador.", Alert.AlertType.ERROR);
            return;
        }

        if (!esVIP) {
            entrenador = "Sin entrenador";
        }

        ReservaClase reservaActualizada = new ReservaClase(clase, horario, entrenador, fechaIngresada);
        reservaActualizada.setIdentificacion(usuarioSeleccionado.getIdentificacion());

        ReservaValidationResult result = validarYActualizarReserva(usuarioSeleccionado.getIdentificacion(), reservaActualizada);

        if (result == ReservaValidationResult.EXITO) {
            mostrarAlerta("Éxito", "Reserva actualizada exitosamente.", Alert.AlertType.INFORMATION);
            limpiarCampos();
            tableUsuario.refresh(); // Refrescar la tabla para actualizar las columnas de reserva
        } else {
            mostrarMensajeErrorReserva(result);
        }
    }

    private ReservaValidationResult validarYActualizarReserva(String identificacionUsuario, ReservaClase reservaActualizada) {
        if (identificacionUsuario == null || reservaActualizada == null) {
            return ReservaValidationResult.DATOS_RESERVA_INVALIDOS;
        }

        Optional<ReservaClase> reservaExistenteOpt = gimnasio.getListaReservasClases().stream()
                .filter(r -> r.getIdentificacion().equals(identificacionUsuario))
                .findFirst();

        if (reservaExistenteOpt.isEmpty()) {
            return ReservaValidationResult.USUARIO_NO_ENCONTRADO;
        }

        ReservaClase reservaExistente = reservaExistenteOpt.get();

        // Validar formato de fecha
        LocalDate fechaReserva;
        try {
            fechaReserva = LocalDate.parse(reservaActualizada.getFecha(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            return ReservaValidationResult.FORMATO_FECHA_INVALIDO;
        }

        // Validar fecha en el pasado
        if (fechaReserva.isBefore(LocalDate.now())) {
            return ReservaValidationResult.FECHA_EN_PASADO;
        }

        // Actualizar los campos de la reserva existente
        reservaExistente.setClase(reservaActualizada.getClase());
        reservaExistente.setHorario(reservaActualizada.getHorario());
        reservaExistente.setEntrenador(reservaActualizada.getEntrenador());
        reservaExistente.setFecha(reservaActualizada.getFecha());

        return ReservaValidationResult.EXITO;
    }

    @FXML
    void onEliminar(ActionEvent event) {
        if (usuarioSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un usuario de la tabla.", Alert.AlertType.ERROR);
            return;
        }

        ReservaClase existingReserva = getReservaForUser(usuarioSeleccionado.getIdentificacion());
        if (existingReserva == null) {
            mostrarAlerta("Error", "El usuario seleccionado no tiene reservas para eliminar.", Alert.AlertType.ERROR);
            return;
        }

        Optional<ButtonType> result = mostrarAlertaConfirmacion("Confirmar Eliminación",
                "¿Está seguro de que desea eliminar esta reserva?");

        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean exito = gimnasio.getListaReservasClases().removeIf(r -> r.getIdentificacion().equals(usuarioSeleccionado.getIdentificacion()));

            if (exito) {
                mostrarAlerta("Éxito", "Reserva eliminada exitosamente.", Alert.AlertType.INFORMATION);
                limpiarCampos();
                tableUsuario.refresh(); // Refrescar la tabla para actualizar las columnas de reserva
            } else {
                mostrarAlerta("Error", "No se pudo eliminar la reserva.", Alert.AlertType.ERROR);
            }
        }
    }

    private void limpiarCampos() {
        limpiarCamposReserva();
        lblBeneficios.setText("Seleccione un usuario");
    }

    private void limpiarCamposReserva() {
        comboBoxClase.setValue(null);
        comboBoxHorario.setValue(null);
        comboBoxEntrenador.setValue(null);
        txtFecha.clear();
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    private Optional<ButtonType> mostrarAlertaConfirmacion(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        return alert.showAndWait();
    }

    private void mostrarMensajeErrorReserva(ReservaValidationResult result) {
        String mensaje = "";
        switch (result) {
            case FORMATO_FECHA_INVALIDO:
                mensaje = "Error: La fecha ingresada no tiene el formato dd/MM/yyyy.";
                break;
            case FECHA_EN_PASADO:
                mensaje = "Error: La fecha de la reserva no puede ser anterior a la fecha actual.";
                break;
            case EXCEDE_MAXIMO_RESERVAS:
                mensaje = "Error: Se ha excedido el límite de 3 reservas para esta clase.";
                break;
            case USUARIO_NO_ENCONTRADO:
                mensaje = "Error: Usuario no encontrado.";
                break;
            case MEMBRESIA_INACTIVA:
                mensaje = "Error: El usuario no tiene una membresía activa.";
                break;
            case FECHA_FUERA_MEMBRESIA:
                mensaje = "Error: La fecha de la reserva está fuera del período de validez de la membresía del usuario.";
                break;
            case DATOS_RESERVA_INVALIDOS:
                mensaje = "Error: Datos de reserva incompletos o inválidos.";
                break;
            case ERROR_DESCONOCIDO:
            default:
                mensaje = "Error desconocido al procesar la reserva.";
                break;
        }
        mostrarAlerta("Error de Reserva", mensaje, Alert.AlertType.ERROR);
    }
}
