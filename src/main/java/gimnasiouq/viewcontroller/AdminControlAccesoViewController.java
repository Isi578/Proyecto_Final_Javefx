package gimnasiouq.viewcontroller;

import gimnasiouq.model.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class AdminControlAccesoViewController {
    private Usuario usuarioActual;
    private Gimnasio gimnasio = new Gimnasio();
    private ObservableList<ControlAcceso> listaRegistros;

    @FXML
    private Button btnBuscarUsuario;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnValidarIngreso;

    @FXML
    private Label lbFechaVencimientoEncontrado;

    @FXML
    private Separator lbMembresiaActivaNoActiva;

    @FXML
    private Label lbMembresiaEncontrada;

    @FXML
    private Label lbNombreEncontrado;

    @FXML
    private TableColumn<ControlAcceso, String> tcEstado;

    @FXML
    private TableColumn<ControlAcceso, String> tcFecha;

    @FXML
    private TableColumn<ControlAcceso, String> tcHora;

    @FXML
    private TableColumn<ControlAcceso, String> tcIdentificacion;

    @FXML
    private TableColumn<ControlAcceso, String> tcNombre;

    @FXML
    private TableColumn<ControlAcceso, String> tcTipoMembresia;

    @FXML
    private TableColumn<ControlAcceso, String> tcUsuario;

    @FXML
    private TextField txtIdentificacion;

    private void mostrarAlerta(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
