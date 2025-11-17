package gimnasiouq.viewcontroller;

import gimnasiouq.factory.ModelFactory;
import gimnasiouq.model.Estudiante;
import gimnasiouq.model.Trabajador;
import gimnasiouq.model.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class RecepReporteUsuarioViewController implements Initializable {

    private ModelFactory modelFactory;

    @FXML
    private Label lblTotalUsuarios;

    @FXML
    private Label lblMembresiasActivas;

    @FXML
    private Label lblMembresiasInactivas;

    @FXML
    private TableView<Usuario> tableUsuarios;

    @FXML
    private TableColumn<Usuario, String> tcEstado;

    @FXML
    private TableColumn<Usuario, String> tcIdentificacion;

    @FXML
    private TableColumn<Usuario, String> tcNombre;

    @FXML
    private TableColumn<Usuario, String> tcUsuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modelFactory = ModelFactory.getInstance();
        initDataBinding();
        initIndicadores();
    }

    private void initDataBinding() {
        ObservableList<Usuario> listaUsuarios = modelFactory.obtenerUsuariosObservable();
        tableUsuarios.setItems(listaUsuarios);

        tcEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstadoMembresia()));
        tcIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentificacion()));
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
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

    private void initIndicadores() {
        modelFactory.actualizarReportes(); // Asegura que los datos estén frescos
        lblTotalUsuarios.textProperty().bind(modelFactory.totalUsuariosProperty().asString());
        lblMembresiasActivas.textProperty().bind(modelFactory.membresiasActivasProperty().asString());
        lblMembresiasInactivas.textProperty().bind(modelFactory.membresiasInactivasProperty().asString());
    }
}
