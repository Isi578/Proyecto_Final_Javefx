package gimnasiouq.viewcontroller;

import gimnasiouq.factory.ModelFactory;
import gimnasiouq.model.Estudiante;
import gimnasiouq.model.Trabajador;
import gimnasiouq.model.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AdminMembresiaViewController {

    ObservableList<Usuario> listaUsuarios;
    Usuario usuarioSeleccionado;

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
    void onActualizar(ActionEvent event) {actualizarMembresia();}

    private void actualizarMembresia() {

    }

    @FXML
    void onAsignar(ActionEvent event) {asignarMembresia();}

    private void asignarMembresia() {

    }

    @FXML
    void onEliminar(ActionEvent event) {eliminarMembresia();}

    private void eliminarMembresia() {

    }

    @FXML
    void onNuevo(ActionEvent event) {nuevaMembresia();}

    private void nuevaMembresia() {

    }

    @FXML
    void initialize() {
        initView();
        comboBoxPlanMembresia.getItems().addAll("Mensual", "Trimestral", "Anual");
    }

    private void initView() {
        initDataBinding();
        listaUsuarios = ModelFactory.getInstance().obtenerUsuariosObservable();
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
                });
    }

    private void mostrarInformacionUsuario(Usuario usuarioSeleccionado) {
    }

}
