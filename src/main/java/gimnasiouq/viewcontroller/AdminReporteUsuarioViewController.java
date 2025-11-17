package gimnasiouq.viewcontroller;

import gimnasiouq.model.ReservaClase;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminReporteUsuarioViewController {

    @FXML
    private Label lblUsuariosActivos;

    @FXML
    private Label lblUsuariosConMensualidadActiva;

    @FXML
    private Label lblUsuariosSinMensualidadActiva;

    @FXML
    private TableColumn<ReservaClase, String> tcEstado;

    @FXML
    private TableColumn<ReservaClase, String> tcIdentificacion;

    @FXML
    private TableColumn<ReservaClase, String> tcNombre;

    @FXML
    private TableColumn<ReservaClase, String> tcUsuario;

    ObservableList<Usuario> listaUsuarios;
    private final ReportesUsuariosController reportesUsuariosController = new ReportesUsuariosController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initView();
        cargarIndicadores();
    }

    private void initView() {
        initDataBinding();
        listaUsuarios = ModelFactory.getInstance().obtenerUsuariosObservable();
        listaUsuarios.addListener((ListChangeListener.Change<? extends Usuario> change) -> {
            cargarIndicadores();
            tableView.refresh();
        });
        tableView.setItems(listaUsuarios);

    }

    private void initDataBinding() {
        if (tcEstado != null) {
            tcEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstadoMembresia()));
        }
        if (tcIdentificacion != null) {
            tcIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentificacion()));
        }
        if (tcNombre != null) {
            tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        }
        if (tcUsuario != null) {
            tcUsuario.setCellValueFactory(cellData -> {
                if (cellData.getValue() instanceof Estudiante) {
                    return new SimpleStringProperty("Estudiante");
                } else if (cellData.getValue() instanceof TrabajadorUQ) {
                    return new SimpleStringProperty("Trabajador UQ");
                } else {
                    return new SimpleStringProperty("Externo");
                }
            });
        }
    }

    private void cargarIndicadores() {
        if (lblUsuariosMensualidadActiva != null)
            lblUsuariosMensualidadActiva.textProperty().bind(reportesUsuariosController.usuariosMembresiaActivasProperty().asString());
        if (lblUsuariosSinMensualidadActiva != null)
            lblUsuariosSinMensualidadActiva.textProperty().bind(reportesUsuariosController.usuariosMembresiaInactivasProperty().asString());
        if (lblUsuariosActivos != null)
            lblUsuariosActivos.textProperty().bind(reportesUsuariosController.usuariosTotalesProperty().asString());
    }
}
