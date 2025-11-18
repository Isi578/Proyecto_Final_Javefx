package gimnasiouq.viewcontroller;

import gimnasiouq.factory.ModelFactory;
import gimnasiouq.model.Gimnasio;
import gimnasiouq.model.ReservaClase;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class RecepReporteClaseViewController implements Initializable {

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
    private TableColumn<ReservaClase, String> tcEntrenador;
    @FXML
    private TableColumn<ReservaClase, String> tcFecha;
    @FXML
    private TableColumn<ReservaClase, String> tcHorario;
    @FXML
    private TableColumn<ReservaClase, String> tcIdUsuario;
    
    @FXML
    private TableColumn<ReservaClase, String> tcCupoMaximo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.gimnasio = ModelFactory.getInstance().getGimnasio();
        actualizarReporte();
    }

    private void actualizarReporte() {
        List<ReservaClase> reservas = gimnasio.getListaReservasClases();
        ObservableList<ReservaClase> reservasObservables = FXCollections.observableArrayList(reservas);

        tableClases.setItems(reservasObservables);
        initDataBinding();
        initIndicadores();
    }

    private void initDataBinding() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        tcClase.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClase()));
        tcHorario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHorario()));
        tcFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecha().format(formatter)));
        tcEntrenador.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEntrenador().getNombre()));
        tcIdUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsuario().getIdentificacion()));
        
        tcCupoMaximo.setCellValueFactory(cellData -> new SimpleStringProperty("20"));
    }

    private void initIndicadores() {
        lblClaseMasReservada.setText(gimnasio.contarClaseMasReservada());
        lblTotalClasesReservadas.setText(String.valueOf(gimnasio.contarTotalClasesReservadas()));
    }
}
