package gimnasiouq.viewcontroller;

import gimnasiouq.factory.ModelFactory;
import gimnasiouq.model.Gimnasio;
import gimnasiouq.model.ReservaClase;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class RecepReporteClaseViewController implements Initializable {

    private ModelFactory modelFactory;
    private Gimnasio gimnasio;

    @FXML
    private Label lblClaseMasReservada;

    @FXML
    private Label lblTotalClasesReservadas;

    @FXML
    private TableView<ReservaClase> tableClases;

    @FXML
    private TableColumn<ReservaClase, String> tcClase;

    @FXML
    private TableColumn<ReservaClase, String> tcCupoMaximo;

    @FXML
    private TableColumn<ReservaClase, String> tcEntrenador;

    @FXML
    private TableColumn<ReservaClase, String> tcFecha;

    @FXML
    private TableColumn<ReservaClase, String> tcHorario;

    @FXML
    private TableColumn<ReservaClase, String> tcIdUsuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modelFactory = ModelFactory.getInstance();
        gimnasio = modelFactory.getGimnasio();
        initDataBinding();
        initIndicadores();
        tableClases.setItems(FXCollections.observableArrayList(gimnasio.getListaReservasClases()));
    }

    private void initDataBinding() {
        tcClase.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClase()));
        tcHorario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHorario()));
        tcFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecha()));
        tcEntrenador.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEntrenador()));
        tcIdUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentificacion()));
        tcCupoMaximo.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCupoMaximo())));
    }

    private void initIndicadores() {
        lblClaseMasReservada.setText(gimnasio.contarClaseMasReservada());
        lblTotalClasesReservadas.setText(String.valueOf(gimnasio.contarTotalClasesReservadas()));
    }
}
