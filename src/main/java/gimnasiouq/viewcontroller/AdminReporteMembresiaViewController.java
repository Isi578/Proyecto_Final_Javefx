package gimnasiouq.viewcontroller;

import gimnasiouq.factory.ModelFactory;
import gimnasiouq.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminReporteMembresiaViewController {

    @FXML
    private Label lblIngresosTotales;

    @FXML
    private Label lblmembresiasConValor;

    @FXML
    private Label lblmembresiasSinValor;

    @FXML
    private Label lblmembresiasTotales;

    @FXML
    private TableColumn<ReservaClase, String> tcCosto;

    @FXML
    private TableColumn<ReservaClase, String> tcFechaInicio;

    @FXML
    private TableColumn<ReservaClase, String> tcFechaVencimiento;

    @FXML
    private TableColumn<ReservaClase, String> tcPlanMembresia;

    @FXML
    private TableColumn<ReservaClase, String> tcTipoMembresia;


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
        if (tcFechaInicio != null) {
            tcFechaInicio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaInicioFormateada()));
        }
        if (tcFechaVencimiento != null) {
            tcFechaVencimiento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaFinFormateada()));
        }
        if (tcPlanMembresias != null) {
            tcPlanMembresias.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlanMembresia()));
        }
        if (tcTipoMembresias != null) {
            tcTipoMembresias.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoMembresia()));
        }
        if (tcCosto != null) {
            tcCosto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCostoMembresiaFormateado()));
        }
    }

    private void cargarIndicadores() {
        if (lblMembresiasTotales != null)
            lblMembresiasTotales.textProperty().bind(reportesMembresiasController.membresiasTotalesProperty().asString());
        if (lblMembresiasConValor != null)
            lblMembresiasConValor.textProperty().bind(reportesMembresiasController.membresiasConValorProperty().asString());
        if (lblMembresiasSinValor != null)
            lblMembresiasSinValor.textProperty().bind(reportesMembresiasController.membresiasSinValorProperty().asString());
        if (lblIngresosTotales != null)
            lblIngresosTotales.textProperty().bind(reportesMembresiasController.ingresosTotalesProperty().asString("$%.0f"));
    }

}
