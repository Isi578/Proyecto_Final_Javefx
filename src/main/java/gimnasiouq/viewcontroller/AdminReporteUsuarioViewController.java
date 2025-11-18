package gimnasiouq.viewcontroller;

import gimnasiouq.factory.ModelFactory;
import gimnasiouq.model.Estudiante;
import gimnasiouq.model.Externo;
import gimnasiouq.model.Gimnasio;
import gimnasiouq.model.Trabajador;
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

public class AdminReporteUsuarioViewController implements Initializable {

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
        Gimnasio gimnasio = ModelFactory.getInstance().getGimnasio();
        ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList(gimnasio.getListaUsuarios());
        initDataBinding();
        tableUsuarios.setItems(listaUsuarios);
        initIndicadores(listaUsuarios); // Llamar a initIndicadores y pasar la lista de usuarios
        actualizarReporte();
    }

    private void actualizarReporte() {
        tableUsuarios.refresh();
    }

    private void initDataBinding() {
        tcEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstadoMembresia()));
        tcIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentificacion()));
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tcUsuario.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Estudiante) return new SimpleStringProperty("Estudiante");
            if (cellData.getValue() instanceof Trabajador) return new SimpleStringProperty("Trabajador");
            if (cellData.getValue() instanceof Externo) return new SimpleStringProperty("Externo");
            return new SimpleStringProperty("N/A");
        });
    }

    private void initIndicadores(ObservableList<Usuario> usuarios) {
        // Calcular total de usuarios
        long total = usuarios.size();

        // Calcular membresías activas
        long activas = usuarios.stream().filter(Usuario::tieneMembresiaActiva).count();

        // Calcular membresías inactivas
        long inactivas = total - activas;

        // Asignar los valores a los Labels
        lblTotalUsuarios.setText(String.valueOf(total));
        lblMembresiasActivas.setText(String.valueOf(activas));
        lblMembresiasInactivas.setText(String.valueOf(inactivas));
    }
}
