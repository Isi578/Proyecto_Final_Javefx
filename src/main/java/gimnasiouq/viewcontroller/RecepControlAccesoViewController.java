package gimnasiouq.viewcontroller;

import gimnasiouq.factory.ModelFactory;
import gimnasiouq.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class RecepControlAccesoViewController implements Initializable {

    private Gimnasio gimnasio;
    private Usuario usuarioActual;
    private ObservableList<ControlAcceso> listaRegistros;

    @FXML
    private Button btnValidarIngreso;
    @FXML
    private Label lbFechaVencimientoEncontrado;
    @FXML
    private Label lbMembresiaActivaNoActiva;
    @FXML
    private Label lbMembresiaEncontrada;
    @FXML
    private Label lbNombreEncontrado;
    @FXML
    private TableView<ControlAcceso> tableRegistros;
    @FXML
    private TableColumn<ControlAcceso, String> tcEstado;
    @FXML
    private TableColumn<ControlAcceso, String> tcFecha;
    @FXML
    private TableColumn<ControlAcceso, String> tcHora;
    @FXML
    private TableColumn<ControlAcceso, String> tcIdentificacion;
    @FXML
    private TableColumn<ControlAcceso, String> tcNombre;
    @FXML
    private TableColumn<ControlAcceso, String> tcTipoMembresia;
    @FXML
    private TableColumn<ControlAcceso, String> tcUsuario;
    @FXML
    private TextField txtIdentificacion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.gimnasio = ModelFactory.getInstance().getGimnasio();
        this.listaRegistros = FXCollections.observableArrayList(gimnasio.getListaRegistrosAcceso());

        initDataBinding();
        tableRegistros.setItems(listaRegistros);
        btnValidarIngreso.setDisable(true);
        limpiarInformacionUsuario();
    }

    private void initDataBinding() {
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tcIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentificacion()));
        tcTipoMembresia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoMembresia()));
        tcFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        tcHora.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHora().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
        tcEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isEstado() ? "Activo" : "Inactivo"));

        tcEstado.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if ("Activo".equalsIgnoreCase(item)) {
                        setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    }
                }
            }
        });

        tcUsuario.setCellValueFactory(cellData -> {
            Usuario usuario = gimnasio.buscarUsuarioPorIdentificacion(cellData.getValue().getIdentificacion());
            if (usuario instanceof Estudiante) return new SimpleStringProperty("Estudiante");
            if (usuario instanceof Trabajador) return new SimpleStringProperty("Trabajador");
            if (usuario instanceof Externo) return new SimpleStringProperty("Externo");
            return new SimpleStringProperty("N/A");
        });
    }

    @FXML
    void onBuscarUsuario(ActionEvent event) {
        String identificacion = txtIdentificacion.getText();
        if (identificacion == null || identificacion.trim().isEmpty()) {
            mostrarAlerta("Error", "Ingrese una identificación", Alert.AlertType.WARNING);
            return;
        }

        gimnasio.buscarUsuario(identificacion.trim()).ifPresentOrElse(
            usuario -> {
                usuarioActual = usuario;
                actualizarInformacionUsuario(usuario);
                btnValidarIngreso.setDisable(false);
            },
            () -> {
                limpiarInformacionUsuario();
                mostrarAlerta("Usuario no encontrado", "No existe un usuario con la identificación: " + identificacion, Alert.AlertType.WARNING);
            }
        );
    }

    @FXML
    void onValidarIngreso(ActionEvent event) {
        if (usuarioActual == null) {
            mostrarAlerta("Error", "Primero debe buscar un usuario", Alert.AlertType.ERROR);
            return;
        }

        if (!usuarioActual.tieneMembresiaActiva()) {
            mostrarAlerta("Membresía Inactiva", "El usuario no puede ingresar. Su membresía no está activa o ha vencido.", Alert.AlertType.ERROR);
            return;
        }

        ControlAcceso nuevoAcceso = new ControlAcceso(java.time.LocalDate.now(), LocalTime.now(), usuarioActual.getNombre(), usuarioActual.getIdentificacion(), usuarioActual.getMembresiaObj().getTipo(), true);
        gimnasio.getListaRegistrosAcceso().add(nuevoAcceso);
        listaRegistros.add(nuevoAcceso);

        mostrarAlerta("Ingreso Validado", "Acceso registrado exitosamente para " + usuarioActual.getNombre(), Alert.AlertType.INFORMATION);
        limpiarFormulario();
    }

    @FXML
    void onEliminar(ActionEvent event) {
        ControlAcceso registroSeleccionado = tableRegistros.getSelectionModel().getSelectedItem();
        if (registroSeleccionado == null) {
            mostrarAlerta("Error", "Seleccione un registro de la tabla para eliminar.", Alert.AlertType.WARNING);
            return;
        }

        Optional<ButtonType> result = mostrarAlertaConfirmacion("Confirmar Eliminación",
                "¿Está seguro de que desea eliminar el registro de acceso de " + registroSeleccionado.getNombre() + "?");

        if (result.isPresent() && result.get() == ButtonType.OK) {
            gimnasio.getListaRegistrosAcceso().remove(registroSeleccionado);
            listaRegistros.remove(registroSeleccionado);
            mostrarAlerta("Registro Eliminado", "El registro de acceso ha sido eliminado.", Alert.AlertType.INFORMATION);
        }
    }

    private void actualizarInformacionUsuario(Usuario usuario) {
        lbNombreEncontrado.setText(usuario.getNombre());
        Membresia membresia = usuario.getMembresiaObj();
        if (membresia != null) {
            lbMembresiaEncontrada.setText(membresia.getTipo());
            lbFechaVencimientoEncontrado.setText(usuario.getFechaFinFormateada());
            String estado = usuario.getEstadoMembresia();
            lbMembresiaActivaNoActiva.setText(estado);
            if ("ACTIVA".equals(estado)) {
                lbMembresiaActivaNoActiva.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            } else {
                lbMembresiaActivaNoActiva.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            }
        } else {
            lbMembresiaEncontrada.setText("Sin Membresía");
            lbFechaVencimientoEncontrado.setText("N/A");
            lbMembresiaActivaNoActiva.setText("INACTIVA");
            lbMembresiaActivaNoActiva.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }
    }

    private void limpiarInformacionUsuario() {
        lbNombreEncontrado.setText("-");
        lbMembresiaEncontrada.setText("-");
        lbFechaVencimientoEncontrado.setText("-");
        lbMembresiaActivaNoActiva.setText("-");
        lbMembresiaActivaNoActiva.setStyle("");
    }

    private void limpiarFormulario() {
        txtIdentificacion.clear();
        limpiarInformacionUsuario();
        usuarioActual = null;
        btnValidarIngreso.setDisable(true);
    }

    private void mostrarAlerta(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Optional<ButtonType> mostrarAlertaConfirmacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        return alert.showAndWait();
    }
}
