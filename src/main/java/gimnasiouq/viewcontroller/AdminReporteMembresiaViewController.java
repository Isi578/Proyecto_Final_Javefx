package gimnasiouq.viewcontroller;

import gimnasiouq.factory.ModelFactory;
import gimnasiouq.model.Gimnasio;
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
import java.util.ResourceBundle;

public class AdminReporteMembresiaViewController implements Initializable {

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
        ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList(gimnasio.getListaUsuarios());
        initDataBinding(listaUsuarios);
        tableMembresias.setItems(listaUsuarios);
        initIndicadores(gimnasio);
    }

    private void initDataBinding(ObservableList<Usuario> listaUsuarios) {
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

    private void initIndicadores(Gimnasio gimnasio) {
        lblmembresiasTotales.setText(String.valueOf(gimnasio.contarTotalMembresias()));
        lblmembresiasConValor.setText(String.valueOf(gimnasio.contarMembresiasUsuariosActivas()));
        lblmembresiasSinValor.setText(String.valueOf(gimnasio.contarMembresiasUsuariosInactivas()));
        lblIngresosTotales.setText(String.format("$%.0f", gimnasio.calcularIngresosTotales()));
    }
}
