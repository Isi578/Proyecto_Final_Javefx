package gimnasiouq.viewcontroller;

import gimnasiouq.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class RecepReservarClaseViewController {

    @FXML
    private ComboBox<String> ComboBoxClase;

    @FXML
    private ComboBox<String> ComboBoxEntrenador;

    @FXML
    private ComboBox<String> ComboBoxHorario;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnConfirmar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNuevo;

    @FXML
    private TableColumn<Usuario, String> tcClase;

    @FXML
    private TableColumn<Usuario, String> tcEntrenador;

    @FXML
    private TableColumn<Usuario, String> tcEstado;

    @FXML
    private TableColumn<Usuario, String> tcFecha;

    @FXML
    private TableColumn<Usuario, String> tcHorario;

    @FXML
    private TableColumn<Usuario, String> tcIdentificacion;

    @FXML
    private TableColumn<Usuario, String> tcNombre;

    @FXML
    private TableColumn<Usuario, String> tcTipoMembresia;

    @FXML
    private TableColumn<Usuario, String> tcUsuario;

    @FXML
    private TextField txtBeneficios;

    @FXML
    private TextField txtFecha;

    @FXML
    void onActualizar(ActionEvent event) {actualizarReservaClase ();}
        private void actualizarReservaClase (){
        }

    @FXML
    void onCorfirmar(ActionEvent event) {confirmarReservaClase ();}
        private void confirmarReservaClase (){
        }

    @FXML
    void onEliminar(ActionEvent event) {eliminarReservaClase ();}
        private void eliminarReservaClase (){
        }

    @FXML
    void onNuevo(ActionEvent event) {nuevoReservaClase ();}
        private void nuevoReservaClase () {
        }
}
