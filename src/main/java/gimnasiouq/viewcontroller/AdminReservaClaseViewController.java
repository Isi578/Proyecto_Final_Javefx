package gimnasiouq.viewcontroller;

import gimnasiouq.factory.ModelFactory;
import gimnasiouq.model.Entrenador;
import gimnasiouq.model.Gimnasio;
import gimnasiouq.model.ReservaClase;
import gimnasiouq.model.Usuario;
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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AdminReservaClaseViewController implements Initializable {

    private Gimnasio gimnasio;
    private ObservableList<ReservaClase> listaReservas;
    private ReservaClase reservaSeleccionada;

    @FXML
    private ComboBox<String> comboBoxClase;
    @FXML
    private ComboBox<Entrenador> comboBoxEntrenador;
    @FXML
    private ComboBox<String> comboBoxHorario;
    @FXML
    private ComboBox<Usuario> comboBoxUsuario;
    @FXML
    private DatePicker datePickerFecha;
    @FXML
    private TableView<ReservaClase> tableReservas;
    @FXML
    private TableColumn<ReservaClase, String> tcClase;
    @FXML
    private TableColumn<ReservaClase, String> tcEntrenador;
    @FXML
    private TableColumn<ReservaClase, String> tcEstado;
    @FXML
    private TableColumn<ReservaClase, String> tcFecha;
    @FXML
    private TableColumn<ReservaClase, String> tcHorario;
    @FXML
    private TableColumn<ReservaClase, String> tcIdentificacion;
    @FXML
    private TableColumn<ReservaClase, String> tcNombre;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.gimnasio = ModelFactory.getInstance().getGimnasio();
        this.listaReservas = FXCollections.observableArrayList(gimnasio.getListaReservasClases());

        initDataBinding();
        tableReservas.setItems(listaReservas);
        listenerSelection();

        // Poblar ComboBoxes
        comboBoxClase.getItems().addAll("Yoga", "Spinning", "Boxeo", "Funcional");
        comboBoxHorario.getItems().addAll("06:00", "08:00", "16:00", "18:00");
        comboBoxEntrenador.setItems(FXCollections.observableArrayList(gimnasio.getListaEntrenador()));
        comboBoxUsuario.setItems(FXCollections.observableArrayList(gimnasio.getListaUsuarios()));
    }

    private void initDataBinding() {
        tcClase.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClase()));
        tcEntrenador.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEntrenador().getNombre()));
        tcEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstado()));
        tcFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        tcHorario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHorario()));
        tcIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsuario().getIdentificacion()));
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsuario().getNombre()));
    }

    private void listenerSelection() {
        tableReservas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            reservaSeleccionada = newSelection;
            mostrarInformacionReserva(reservaSeleccionada);
        });
    }

    @FXML
    void onNuevo(ActionEvent event) {
        limpiarCampos();
    }

    @FXML
    void onConfirmar(ActionEvent event) {
        try {
            ReservaClase reserva = buildReservaFromFields();
            if (reserva == null) return;

            // Lógica de negocio (ej. verificar si el usuario puede reservar)
            if (!reserva.getUsuario().tieneMembresiaActiva()) {
                mostrarAlerta("Error", "El usuario no tiene una membresía activa para reservar.", Alert.AlertType.ERROR);
                return;
            }

            gimnasio.getListaReservasClases().add(reserva);
            listaReservas.add(reserva);

            limpiarCampos();
            mostrarAlerta("Éxito", "Reserva creada exitosamente.", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo crear la reserva: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onActualizar(ActionEvent event) {
        if (reservaSeleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una reserva de la tabla.", Alert.AlertType.ERROR);
            return;
        }

        try {
            ReservaClase datosNuevos = buildReservaFromFields();
            if (datosNuevos == null) return;

            // Actualizar el objeto original
            reservaSeleccionada.setClase(datosNuevos.getClase());
            reservaSeleccionada.setEntrenador(datosNuevos.getEntrenador());
            reservaSeleccionada.setFecha(datosNuevos.getFecha());
            reservaSeleccionada.setHorario(datosNuevos.getHorario());
            reservaSeleccionada.setUsuario(datosNuevos.getUsuario());
            // El estado se podría actualizar aquí también si fuera necesario
            
            tableReservas.refresh();
            limpiarCampos();
            mostrarAlerta("Éxito", "Reserva actualizada exitosamente.", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo actualizar la reserva: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onEliminar(ActionEvent event) {
        if (reservaSeleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una reserva de la tabla.", Alert.AlertType.ERROR);
            return;
        }

        Optional<ButtonType> result = mostrarAlertaConfirmacion("Confirmar Eliminación",
                "¿Está seguro de que desea eliminar esta reserva?");

        if (result.isPresent() && result.get() == ButtonType.OK) {
            gimnasio.getListaReservasClases().remove(reservaSeleccionada);
            listaReservas.remove(reservaSeleccionada);
            limpiarCampos();
            mostrarAlerta("Éxito", "Reserva eliminada exitosamente.", Alert.AlertType.INFORMATION);
        }
    }

    private ReservaClase buildReservaFromFields() {
        Usuario usuario = comboBoxUsuario.getValue();
        String clase = comboBoxClase.getValue();
        Entrenador entrenador = comboBoxEntrenador.getValue();
        LocalDate fecha = datePickerFecha.getValue();
        String horario = comboBoxHorario.getValue();

        if (usuario == null || clase == null || entrenador == null || fecha == null || horario == null) {
            mostrarAlerta("Error", "Complete todos los campos para la reserva.", Alert.AlertType.ERROR);
            return null;
        }

        // El estado inicial podría ser "Confirmada"
        return new ReservaClase(clase, fecha, horario, "Confirmada", usuario, entrenador);
    }

    private void mostrarInformacionReserva(ReservaClase reserva) {
        if (reserva != null) {
            comboBoxUsuario.setValue(reserva.getUsuario());
            comboBoxClase.setValue(reserva.getClase());
            comboBoxEntrenador.setValue(reserva.getEntrenador());
            datePickerFecha.setValue(reserva.getFecha());
            comboBoxHorario.setValue(reserva.getHorario());
        } else {
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        tableReservas.getSelectionModel().clearSelection();
        reservaSeleccionada = null;
        comboBoxUsuario.setValue(null);
        comboBoxClase.setValue(null);
        comboBoxEntrenador.setValue(null);
        datePickerFecha.setValue(null);
        comboBoxHorario.setValue(null);
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
}
