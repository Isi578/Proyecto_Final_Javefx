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
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminMembresiaViewController implements Initializable {

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
    @FXML
    private ComboBox<String> comboBoxTipoMembresia;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 1. Obtener la instancia compartida del Gimnasio
        this.gimnasio = ModelFactory.getInstance().getGimnasio();

        // 2. Obtener la lista de usuarios y convertirla a ObservableList
        this.listaUsuarios = FXCollections.observableArrayList(gimnasio.getListaUsuarios());

        // 3. Configurar la vista
        initDataBinding();
        tableView.setItems(listaUsuarios);

        comboBoxPlanMembresia.getItems().addAll("Mensual", "Trimestral", "Anual");
        comboBoxTipoMembresia.getItems().addAll("Basica", "Premium", "VIP");

        listenerSelection();
    }

    private void initDataBinding() {
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tcIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentificacion()));
        tcFechaInicio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaInicioFormateada()));
        tcFechaFin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaFinFormateada()));
        tcPlan.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlanMembresia()));
        tcCosto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCostoMembresiaFormateado()));
        tcEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstadoMembresia()));
        tcUsuario.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Estudiante) return new SimpleStringProperty("Estudiante");
            if (cellData.getValue() instanceof Trabajador) return new SimpleStringProperty("Trabajador");
            if (cellData.getValue() instanceof Externo) return new SimpleStringProperty("Externo");
            return new SimpleStringProperty("N/A");
        });
    }

    private void listenerSelection() {
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            usuarioSeleccionado = newSelection;
            mostrarInformacionUsuario(usuarioSeleccionado);
        });
    }

    private void mostrarInformacionUsuario(Usuario usuario) {
        if (usuario != null) {
            txtFechaInicio.setText(usuario.getFechaInicioFormateada());
            txtFechaFin.setText(usuario.getFechaFinFormateada());
            txtCosto.setText(usuario.getCostoMembresiaFormateado());
            comboBoxPlanMembresia.setValue(usuario.getPlanMembresia());
            comboBoxTipoMembresia.setValue(usuario.getTipoMembresia());
        } else {
            limpiarCampos();
        }
    }

    @FXML
    void onAsignar(ActionEvent event) {
        if (usuarioSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un usuario de la tabla.", Alert.AlertType.ERROR);
            return;
        }

        String plan = comboBoxPlanMembresia.getValue();
        String tipo = comboBoxTipoMembresia.getValue();

        if (plan == null || tipo == null) {
            mostrarAlerta("Error", "Debe seleccionar un plan y un tipo de membresía.", Alert.AlertType.ERROR);
            return;
        }

        try {
            Membresia nuevaMembresia = gimnasio.calcularMembresiaPorPlan(plan, tipo, usuarioSeleccionado);
            if (nuevaMembresia == null) {
                mostrarAlerta("Error", "No se pudo calcular la membresía con los datos proporcionados.", Alert.AlertType.ERROR);
                return;
            }
            gimnasio.asignarMembresiaUsuario(usuarioSeleccionado.getIdentificacion(), nuevaMembresia);
            mostrarAlerta("Éxito", "Membresía asignada/actualizada correctamente.", Alert.AlertType.INFORMATION);
            refrescarTabla();
        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error al asignar la membresía: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onActualizar(ActionEvent event) {
        onAsignar(event); // La lógica es la misma que asignar una nueva
    }

    @FXML
    void onEliminar(ActionEvent event) {
        if (usuarioSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un usuario de la tabla.", Alert.AlertType.ERROR);
            return;
        }

        Optional<ButtonType> result = mostrarAlertaConfirmacion("Confirmar Eliminación",
                "¿Está seguro de que desea eliminar la membresía de " + usuarioSeleccionado.getNombre() + "?");

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                gimnasio.eliminarMembresiaUsuario(usuarioSeleccionado.getIdentificacion());
                mostrarAlerta("Éxito", "Membresía eliminada correctamente.", Alert.AlertType.INFORMATION);
                refrescarTabla();
            } catch (Exception e) {
                mostrarAlerta("Error", "Ocurrió un error al eliminar la membresía: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void onNuevo(ActionEvent event) {
        tableView.getSelectionModel().clearSelection();
        limpiarCampos();
    }

    private void limpiarCampos() {
        usuarioSeleccionado = null;
        txtFechaInicio.clear();
        txtFechaFin.clear();
        txtCosto.clear();
        comboBoxPlanMembresia.setValue(null);
        comboBoxTipoMembresia.setValue(null);
    }

    private void refrescarTabla() {
        tableView.refresh();
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            tableView.getSelectionModel().clearSelection();
            tableView.getSelectionModel().select(selectedIndex);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
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
