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
import java.util.List;
import java.util.ResourceBundle;

public class AdminReporteClaseViewController implements Initializable {

    private Gimnasio gimnasio;
    private ObservableList<ReservaClase> listaReservas; // Declarar aquí para usar la referencia directa

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
        this.listaReservas = gimnasio.getListaReservasClases(); // Obtener la ObservableList directamente del modelo
        initDataBinding();
        tableClases.setItems(listaReservas); // Establecer la lista directamente en la tabla
        actualizarReporte();
    }

    private void actualizarReporte() {
        // No es necesario crear una nueva ObservableList aquí, ya estamos usando la del modelo
        // tableClases.setItems(listaReservas); // Ya se hizo en initialize
        initIndicadores();
        tableClases.refresh(); // Refrescar la tabla para asegurar que los datos se muestren
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
