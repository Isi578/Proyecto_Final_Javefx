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
import java.util.Objects;
import java.util.ResourceBundle;

public class RecepReporteMembresiaViewController implements Initializable {

    private ObservableList<Usuario> listaUsuarios;

    @FXML
    private Label lblIngresosTotales;

    @FXML
    private Label lblmembresiasConValor;

    @FXML
    private Label lblmembresiasSinValor;

    @FXML
    private Label lblmembresiasTotales;

    @FXML
    private TableView<Usuario> tableMembresias;

    @FXML
    private TableColumn<Usuario, String> tcCosto;

    @FXML
    private TableColumn<Usuario, String> tcFechaInicio;

    @FXML
    private TableColumn<Usuario, String> tcFechaVencimiento;

    @FXML
    private TableColumn<Usuario, String> tcPlanMembresia;

    @FXML
    private TableColumn<Usuario, String> tcTipoMembresia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Gimnasio gimnasio = ModelFactory.getInstance().getGimnasio();
        this.listaUsuarios = FXCollections.observableArrayList(gimnasio.getListaUsuarios());
        initDataBinding();
        tableMembresias.setItems(listaUsuarios);
        initIndicadores();
    }

    private void initDataBinding() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        tcFechaInicio.setCellValueFactory(cellData -> {
            Usuario usuario = cellData.getValue();
            return new SimpleStringProperty(usuario.getFechaInicioMembresia() != null ? usuario.getFechaInicioMembresia().format(formatter) : "N/A");
        });
        tcFechaVencimiento.setCellValueFactory(cellData -> {
            Usuario usuario = cellData.getValue();
            return new SimpleStringProperty(usuario.getFechaFinMembresia() != null ? usuario.getFechaFinMembresia().format(formatter) : "N/A");
        });
        tcPlanMembresia.setCellValueFactory(cellData -> {
            Usuario usuario = cellData.getValue();
            return new SimpleStringProperty(usuario.getPlanMembresia());
        });
        tcTipoMembresia.setCellValueFactory(cellData -> {
            Usuario usuario = cellData.getValue();
            return new SimpleStringProperty(usuario.getTipoMembresia() != null ? usuario.getTipoMembresia() : "N/A");
        });
        tcCosto.setCellValueFactory(cellData -> {
            Usuario usuario = cellData.getValue();
            return new SimpleStringProperty(usuario.getMembresiaObj() != null ? String.format("$%.0f", usuario.getMembresiaObj().getCosto()) : "$0");
        });
    }

    private void initIndicadores() {
        long totalMembresias = listaUsuarios.stream().filter(u -> u.getMembresiaObj() != null).count();
        long membresiasActivas = listaUsuarios.stream().filter(Usuario::tieneMembresiaActiva).count();
        long membresiasInactivas = totalMembresias - membresiasActivas;
        double ingresosTotales = listaUsuarios.stream()
                .map(Usuario::getMembresiaObj)
                .filter(Objects::nonNull)
                .mapToDouble(Membresia::getCosto)
                .sum();

        lblmembresiasTotales.setText(String.valueOf(totalMembresias));
        lblmembresiasConValor.setText(String.valueOf(membresiasActivas));
        lblmembresiasSinValor.setText(String.valueOf(membresiasInactivas));
        lblIngresosTotales.setText(String.format("$%.0f", ingresosTotales));
    }
}
