package gimnasiouq.viewcontroller;

import gimnasiouq.factory.ModelFactory;
import gimnasiouq.model.Gimnasio;
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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AdminReporteMembresiaViewController implements Initializable {

    private Gimnasio gimnasio;

    @FXML
    private Label lblIngresosTotales;
    @FXML
    private Label lblmembresiasConValor; // Activas
    @FXML
    private Label lblmembresiasSinValor; // Inactivas
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
    private TableColumn<Membresia, String> tcTipoMembresia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Obtener la instancia de Gimnasio
        this.gimnasio = ModelFactory.getInstance().getGimnasio();

        // Cargar y procesar los datos
        actualizarReporte();
    }

    private void actualizarReporte() {
        // Obtener todas las membresías de todos los usuarios
        List<Membresia> todasLasMembresias = gimnasio.getListaUsuarios().stream()
                .map(Usuario::getMembresiaObj)
                .filter(Objects::nonNull) // Filtra usuarios sin membresía
                .collect(Collectors.toList());

        // Poblar la tabla
        ObservableList<Membresia> membresiasObservables = FXCollections.observableArrayList(todasLasMembresias);
        tableMembresias.setItems(membresiasObservables);
        initDataBinding();

        // Calcular y mostrar los indicadores
        initIndicadores(todasLasMembresias);
    }

    private void initDataBinding() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        tcFechaInicio.setCellValueFactory(cellData -> {
            Membresia m = cellData.getValue();
            return new SimpleStringProperty(m.getInicio() != null ? m.getInicio().format(formatter) : "N/A");
        });
        tcFechaVencimiento.setCellValueFactory(cellData -> {
            Membresia m = cellData.getValue();
            return new SimpleStringProperty(m.getFin() != null ? m.getFin().format(formatter) : "N/A");
        });
        tcTipoMembresia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo()));
        tcCosto.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("$%.0f", cellData.getValue().getCosto())));
    }

    private void initIndicadores(List<Membresia> membresias) {
        // Calcular total de membresías
        long total = membresias.size();

        // Calcular membresías activas
        long activas = membresias.stream().filter(Membresia::estaVigente).count();

        // Calcular membresías inactivas
        long inactivas = total - activas;

        // Calcular ingresos totales
        double ingresos = membresias.stream().mapToDouble(Membresia::getCosto).sum();

        // Asignar los valores a los Labels
        lblmembresiasTotales.setText(String.valueOf(total));
        lblmembresiasConValor.setText(String.valueOf(activas));
        lblmembresiasSinValor.setText(String.valueOf(inactivas));
        lblIngresosTotales.setText(String.format("$%.0f", ingresos));
    }
}
