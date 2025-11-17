package gimnasiouq.viewcontroller;

import gimnasiouq.factory.ModelFactory;
import gimnasiouq.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class RecepControlAccesoViewController {
    private ModelFactory modelFactory;
    private Usuario usuarioActual;
    private ObservableList<ControlAcceso> listaRegistros;

    @FXML
    private Button btnBuscarUsuario;

    @FXML
    private Button btnEliminar;

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

    @FXML
    void initialize() {
        modelFactory = ModelFactory.getInstance();
        btnValidarIngreso.setDisable(true);

        initDataBinding();
        listaRegistros = modelFactory.obtenerRegistrosObservable();
        tableRegistros.setItems(listaRegistros);

        limpiarInformacionUsuario();
    }

    private void initDataBinding() {
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tcIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentificacion()));
        tcTipoMembresia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoMembresia()));
        tcFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        tcHora.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHora().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
        tcEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isEstado() ? "ACTIVA" : "INACTIVA"));

        tcEstado.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if ("ACTIVA".equals(item)) {
                        setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    }
                }
            }
        });

        // El tipo de usuario no está en ControlAcceso, así que lo buscamos.
        // Esto es aceptable si el tipo de usuario no cambia.
        tcUsuario.setCellValueFactory(cellData -> {
            String id = cellData.getValue().getIdentificacion();
            if (id == null) return new SimpleStringProperty("N/A");

            Optional<Usuario> usuario = modelFactory.buscarUsuario(id);
            if (usuario.isEmpty()) return new SimpleStringProperty("Desconocido");

            if (usuario.get() instanceof Estudiante) {
                return new SimpleStringProperty("Estudiante");
            } else if (usuario.get() instanceof Trabajador) {
                return new SimpleStringProperty("Trabajador UQ");
            } else {
                return new SimpleStringProperty("Externo");
            }
        });
    }

    @FXML
    void onBuscarUsuario(ActionEvent event) {
        String identificacion = txtIdentificacion.getText();

        if (identificacion == null || identificacion.trim().isEmpty()) {
            mostrarAlerta("Error", "Ingrese una identificación", Alert.AlertType.WARNING);
            return;
        }

        Optional<Usuario> usuario = modelFactory.buscarUsuario(identificacion.trim());

        if (usuario.isPresent()) {
            usuarioActual = usuario.get();
            actualizarInformacionUsuario(usuarioActual);
            btnValidarIngreso.setDisable(!usuarioActual.tieneMembresiaActiva());
        } else {
            limpiarInformacionUsuario();
            mostrarAlerta("Usuario no encontrado",
                    "No existe un usuario con la identificación: " + identificacion,
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onValidarIngreso(ActionEvent event) {
        if (usuarioActual == null) {
            mostrarAlerta("Error", "Primero debe buscar un usuario", Alert.AlertType.ERROR);
            return;
        }

        if (!modelFactory.validarIngresoUsuario(usuarioActual.getIdentificacion())) {
            mostrarAlerta("Membresía Inactiva",
                    "El usuario no puede ingresar. Membresía NO ACTIVA.",
                    Alert.AlertType.ERROR);
            return;
        }

        boolean ok = modelFactory.registrarIngresoUsuario(usuarioActual.getIdentificacion());
        if (ok) {
            mostrarAlerta("Ingreso Validado",
                    "Acceso registrado exitosamente para " + usuarioActual.getNombre(),
                    Alert.AlertType.INFORMATION);
            limpiarFormulario();
        } else {
            mostrarAlerta("Error", "No se pudo registrar el ingreso", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onEliminar(ActionEvent event) {
        ControlAcceso registroSeleccionado = tableRegistros.getSelectionModel().getSelectedItem();

        if (registroSeleccionado != null) {
            boolean ok = modelFactory.eliminarRegistro(registroSeleccionado);
            if (ok) {
                mostrarAlerta("Registro eliminado", "El registro ha sido eliminado", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se pudo eliminar el registro", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Seleccione un registro para eliminar", Alert.AlertType.WARNING);
        }
    }

    private void actualizarInformacionUsuario(Usuario usuario) {
        lbNombreEncontrado.setText(usuario.getNombre());
        if (usuario.getMembresiaObj() != null) {
            lbMembresiaEncontrada.setText(usuario.getMembresiaObj().getTipoMembresia());
            lbFechaVencimientoEncontrado.setText(usuario.getMembresiaObj().getFin().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            String estado = usuario.getMembresiaObj().isActiva() ? "ACTIVA" : "INACTIVA";
            lbMembresiaActivaNoActiva.setText(estado);
            if ("ACTIVA".equals(estado)) {
                lbMembresiaActivaNoActiva.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            } else {
                lbMembresiaActivaNoActiva.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            }
        } else {
            lbMembresiaEncontrada.setText("N/A");
            lbFechaVencimientoEncontrado.setText("N/A");
            lbMembresiaActivaNoActiva.setText("SIN MEMBRESIA");
            lbMembresiaActivaNoActiva.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }
    }

    private void limpiarInformacionUsuario() {
        lbNombreEncontrado.setText("Valide la identificación");
        lbMembresiaEncontrada.setText("Valide la identificación");
        lbFechaVencimientoEncontrado.setText("Valide la identificación");
        lbMembresiaActivaNoActiva.setText("Valide la identificación");
        lbMembresiaActivaNoActiva.setStyle("");
        btnValidarIngreso.setDisable(true);
    }



    private void limpiarFormulario() {
        txtIdentificacion.clear();
        limpiarInformacionUsuario();
        usuarioActual = null;
    }

    private void mostrarAlerta(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
