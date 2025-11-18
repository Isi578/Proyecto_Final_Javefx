package gimnasiouq.viewcontroller;

import gimnasiouq.GimnasioApp;
import gimnasiouq.factory.ModelFactory;
import gimnasiouq.model.Gimnasio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {

    private Gimnasio gimnasio;

    @FXML
    private Button loginButton;
    @FXML
    private Label txtAdvertencia;
    @FXML
    private PasswordField txtPasswordLogin;
    @FXML
    private TextField txtUserLogin; // Asumiendo que el FXML tiene un TextField para el usuario

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.gimnasio = ModelFactory.getInstance().getGimnasio();
        txtAdvertencia.setText("");
    }

    @FXML
    void login(ActionEvent event) {
        String user = txtUserLogin.getText();
        String pass = txtPasswordLogin.getText();

        if (user == null || user.trim().isEmpty() || pass == null || pass.trim().isEmpty()) {
            mostrarAlerta("Error", "Los campos de usuario y contraseña no pueden estar vacíos.", Alert.AlertType.WARNING);
            return;
        }

        String rol = gimnasio.autenticarEmpleado(user, pass);

        if (rol != null) {
            if (rol.equals("ADMINISTRADOR")) {
                GimnasioApp.goToAdministrador();
            } else if (rol.equals("RECEPCIONISTA")) {
                GimnasioApp.goToRecepcionista();
            }
        } else {
            mostrarAlerta("Error de Autenticación", "Usuario o contraseña incorrectos.", Alert.AlertType.ERROR);
            txtAdvertencia.setText("Credenciales incorrectas");
        }
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
