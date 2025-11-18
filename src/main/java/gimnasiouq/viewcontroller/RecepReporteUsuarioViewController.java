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
import java.util.List;
import java.util.ResourceBundle;

public class RecepReporteUsuarioViewController implements Initializable {

    private Gimnasio gimnasio;
    private ObservableList<Usuario> listaUsuarios; // Declarar aquí para usar la referencia directa

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
        this.gimnasio = ModelFactory.getInstance().getGimnasio();
        this.listaUsuarios = gimnasio.getListaUsuarios(); // Obtener la ObservableList directamente del modelo
        initDataBinding();
        tableUsuarios.setItems(listaUsuarios); // Establecer la lista directamente en la tabla
        actualizarReporte();
    }

    private void actualizarReporte() {
        // No es necesario crear una nueva ObservableList aquí, ya estamos usando la del modelo
        // tableUsuarios.setItems(listaUsuarios); // Ya se hizo en initialize
        initIndicadores(listaUsuarios); // Pasar la lista directamente
        tableUsuarios.refresh(); // Refrescar la tabla para asegurar que los datos se muestren
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

    private void initIndicadores(ObservableList<Usuario> usuarios) { // Cambiar el tipo a ObservableList
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
