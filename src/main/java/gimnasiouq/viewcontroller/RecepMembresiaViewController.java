package gimnasiouq.viewcontroller;

import gimnasiouq.factory.ModelFactory;
import gimnasiouq.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class RecepMembresiaViewController {

    private Gimnasio gimnasio;
    private ObservableList<Usuario> listaUsuarios;
    private Usuario usuarioSeleccionado;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAsignar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNuevo;

    @FXML
    private ComboBox<String> comboBoxPlanMembresia;

    // @FXML // Eliminado: comboBoxTipoMembresia
    // private ComboBox<String> comboBoxTipoMembresia;

    @FXML
    private TableView<Usuario> tableView;

    @FXML
    private TableColumn<Usuario, String> tcCosto;

    @FXML
    private TableColumn<Usuario, String> tcEstado;

    @FXML
    private TableColumn<Usuario, String> tcFechaFin;

    @FXML
    private TableColumn<Usuario, String> tcFechaInicio;

    @FXML
    private TableColumn<Usuario, String> tcIdentificacion;

    @FXML
    private TableColumn<Usuario, String> tcNombre;

    @FXML
    private TableColumn<Usuario, String> tcPlan;

    @FXML
    private TableColumn<Usuario, String> tcUsuario;

    @FXML
    private TextField txtCosto;

    @FXML
    private TextField txtFechaFin;

    @FXML
    private TextField txtFechaInicio;

    @FXML
    void onActualizar(ActionEvent event) {
        actualizarMembresia();
    }

    private void actualizarMembresia() {
        if (usuarioSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un usuario para actualizar su membresía.", Alert.AlertType.WARNING);
            return;
        }

        String planSeleccionado = comboBoxPlanMembresia.getSelectionModel().getSelectedItem();
        // String tipoMembresiaSeleccionado = comboBoxTipoMembresia.getSelectionModel().getSelectedItem(); // Eliminado
        String tipoMembresiaSeleccionado = usuarioSeleccionado.getTipoMembresia(); // Obtener del usuario seleccionado

        if (planSeleccionado == null || tipoMembresiaSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un plan y el usuario debe tener un tipo de membresía definido.", Alert.AlertType.WARNING);
            return;
        }

        try {
            Membresia nuevaMembresia = gimnasio.calcularMembresiaPorPlan(planSeleccionado, tipoMembresiaSeleccionado, usuarioSeleccionado);
            if (nuevaMembresia != null) {
                gimnasio.asignarMembresiaUsuario(usuarioSeleccionado.getIdentificacion(), nuevaMembresia);
                mostrarAlerta("Éxito", "Membresía actualizada correctamente.", Alert.AlertType.INFORMATION);
                limpiarCampos();
                actualizarTabla(); // Refrescar la tabla para mostrar los cambios
            } else {
                mostrarAlerta("Error", "No se pudo calcular la membresía con los datos proporcionados.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al actualizar la membresía: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onAsignar(ActionEvent event) {
        asignarMembresia();
    }

    private void asignarMembresia() {
        if (usuarioSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un usuario para asignar una membresía.", Alert.AlertType.WARNING);
            return;
        }

        String planSeleccionado = comboBoxPlanMembresia.getSelectionModel().getSelectedItem();
        // String tipoMembresiaSeleccionado = comboBoxTipoMembresia.getSelectionModel().getSelectedItem(); // Eliminado
        String tipoMembresiaSeleccionado = usuarioSeleccionado.getTipoMembresia(); // Obtener del usuario seleccionado

        if (planSeleccionado == null || tipoMembresiaSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un plan y el usuario debe tener un tipo de membresía definido.", Alert.AlertType.WARNING);
            return;
        }

        try {
            Membresia nuevaMembresia = gimnasio.calcularMembresiaPorPlan(planSeleccionado, tipoMembresiaSeleccionado, usuarioSeleccionado);
            if (nuevaMembresia != null) {
                gimnasio.asignarMembresiaUsuario(usuarioSeleccionado.getIdentificacion(), nuevaMembresia);
                mostrarAlerta("Éxito", "Membresía asignada correctamente.", Alert.AlertType.INFORMATION);
                limpiarCampos();
                actualizarTabla(); // Refrescar la tabla para mostrar los cambios
            } else {
                mostrarAlerta("Error", "No se pudo calcular la membresía con los datos proporcionados.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al asignar la membresía: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onEliminar(ActionEvent event) {
        eliminarMembresia();
    }

    private void eliminarMembresia() {
        if (usuarioSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un usuario para eliminar su membresía.", Alert.AlertType.WARNING);
            return;
        }

        try {
            gimnasio.eliminarMembresiaUsuario(usuarioSeleccionado.getIdentificacion());
            mostrarAlerta("Éxito", "Membresía eliminada correctamente.", Alert.AlertType.INFORMATION);
            limpiarCampos();
            actualizarTabla();
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al eliminar la membresía: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onNuevo(ActionEvent event) {
        limpiarCampos();
        tableView.getSelectionModel().clearSelection();
        usuarioSeleccionado = null;
    }

    @FXML
    void initialize() {
        this.gimnasio = ModelFactory.getInstance().getGimnasio();
        this.listaUsuarios = (ObservableList<Usuario>) gimnasio.getListaUsuarios();
        initView();
        comboBoxPlanMembresia.getItems().addAll("Mensual", "Trimestral", "Anual");


        comboBoxPlanMembresia.valueProperty().addListener((obs, oldVal, newVal) -> updateMembershipDetails());
    }

    private void initView() {
        initDataBinding();
        tableView.setItems(listaUsuarios);
        listenerSelection();
    }

    private void initDataBinding() {
        if (tcNombre != null) {
            tcNombre.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getNombre()));
        }
        if (tcIdentificacion != null) {
            tcIdentificacion.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getIdentificacion()));
        }
        if (tcFechaInicio != null) {
            tcFechaInicio.setCellValueFactory(cellData -> {
                Usuario usuario = cellData.getValue();
                return new SimpleStringProperty(usuario.getFechaInicioFormateada());
            });
        }
        if (tcFechaFin != null) {
            tcFechaFin.setCellValueFactory(cellData -> {
                Usuario usuario = cellData.getValue();
                return new SimpleStringProperty(usuario.getFechaFinFormateada());
            });
        }
        if (tcPlan != null) {
            tcPlan.setCellValueFactory(cellData -> {
                Usuario usuario = cellData.getValue();
                return new SimpleStringProperty(usuario.getPlanMembresia());
            });
        }
        if (tcCosto != null) {
            tcCosto.setCellValueFactory(cellData -> {
                Usuario usuario = cellData.getValue();
                return new SimpleStringProperty(usuario.getCostoMembresiaFormateado());
            });
        }
        if (tcEstado != null) {
            tcEstado.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getEstadoMembresia()));
        }
        if (tcUsuario != null) {
            tcUsuario.setCellValueFactory(cellData -> {
                if (cellData.getValue() instanceof Estudiante) {
                    return new SimpleStringProperty("Estudiante");
                } else if (cellData.getValue() instanceof Trabajador) {
                    return new SimpleStringProperty("Trabajador UQ");
                } else {
                    return new SimpleStringProperty("Externo");
                }
            });
        }
    }

    private void listenerSelection() {
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newSelection) -> {
                    usuarioSeleccionado = newSelection;
                    mostrarInformacionUsuario(usuarioSeleccionado);
                    updateMembershipDetails();
                });
    }

    private void mostrarInformacionUsuario(Usuario usuario) {
        if (usuario != null) {
            if (usuario.getMembresiaObj() != null) {
                Membresia membresia = usuario.getMembresiaObj();
                txtFechaInicio.setText(membresia.getInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                txtFechaFin.setText(membresia.getFin().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                comboBoxPlanMembresia.getSelectionModel().select(membresia.getTipo());

                txtCosto.setText(String.valueOf(membresia.getCosto()));
            } else {
                limpiarCampos();
            }
        }
    }

    private void limpiarCampos() {
        txtFechaInicio.setText("");
        txtFechaFin.setText("");
        comboBoxPlanMembresia.getSelectionModel().clearSelection();
        txtCosto.setText("");
    }

    private void actualizarTabla() {
        tableView.refresh();
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    // Método para actualizar los detalles de la membresía
    private void updateMembershipDetails() {
        String planSeleccionado = comboBoxPlanMembresia.getSelectionModel().getSelectedItem();
        String tipoMembresiaSeleccionado = (usuarioSeleccionado != null) ? usuarioSeleccionado.getTipoMembresia() : null; // Obtener del usuario seleccionado

        if (usuarioSeleccionado != null && planSeleccionado != null && tipoMembresiaSeleccionado != null) {
            Membresia membresiaCalculada = gimnasio.calcularMembresiaPorPlan(planSeleccionado, tipoMembresiaSeleccionado, usuarioSeleccionado);
            if (membresiaCalculada != null) {
                txtFechaInicio.setText(membresiaCalculada.getInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                txtFechaFin.setText(membresiaCalculada.getFin().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                txtCosto.setText(String.format("%.0f", membresiaCalculada.getCosto()));
            } else {
                // Si no se puede calcular, limpiar los campos
                txtFechaInicio.setText("");
                txtFechaFin.setText("");
                txtCosto.setText("");
            }
        } else {
            // Si falta alguna selección, limpiar los campos
            txtFechaInicio.setText("");
            txtFechaFin.setText("");
            txtCosto.setText("");
        }
    }
}
