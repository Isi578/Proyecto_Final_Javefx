package gimnasiouq.viewcontroller;

import gimnasiouq.factory.ModelFactory;
import gimnasiouq.model.Membresia;
import gimnasiouq.model.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RecepReporteMembresiaViewController implements Initializable {

    private ModelFactory modelFactory;

    @FXML
    private Label lblIngresosTotales;

    @FXML
    private Label lblmembresiasConValor;

    @FXML
    private Label lblmembresiasSinValor;

    @FXML
    private Label lblmembresiasTotales;

    @FXML
    private TableView<Membresia> tableMembresias;

    @FXML
    private TableColumn<Membresia, String> tcCosto;

    @FXML
    private TableColumn<Membresia, String> tcFechaInicio;

    @FXML
    private TableColumn<Membresia, String> tcFechaVencimiento;

    @FXML
    private TableColumn<Membresia, String> tcPlanMembresia;

    @FXML
    private TableColumn<Membresia, String> tcTipoMembresia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modelFactory = ModelFactory.getInstance();
        initDataBinding();
        initIndicadores();
    }

    private void initDataBinding() {
        ObservableList<Usuario> listaUsuarios = modelFactory.obtenerUsuariosObservable();
        ObservableList<Membresia> listaMembresias = listaUsuarios.stream()
                .map(Usuario::getMembresiaObj)
                .filter(m -> m != null)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        tableMembresias.setItems(listaMembresias);

        tcFechaInicio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInicio().toString()));
        tcFechaVencimiento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFin().toString()));
        tcPlanMembresia.setCellValueFactory(cellData -> new SimpleStringProperty("N/A")); // El plan no está en la membresia
        tcTipoMembresia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoMembresia()));
        tcCosto.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("$%.0f", cellData.getValue().getCosto())));
    }

    private void initIndicadores() {
        modelFactory.actualizarReportes(); // Asegura que los datos estén frescos

        lblmembresiasTotales.textProperty().bind(modelFactory.totalMembresiasProperty().asString());
        lblmembresiasConValor.textProperty().bind(modelFactory.membresiasActivasProperty().asString());
        lblmembresiasSinValor.textProperty().bind(modelFactory.membresiasInactivasProperty().asString());
        lblIngresosTotales.textProperty().bind(modelFactory.ingresosTotalesProperty().asString("$%.0f"));
    }
}
