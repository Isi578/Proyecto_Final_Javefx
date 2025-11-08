package gimnasiouq.viewcontroller;

import gimnasiouq.GimnasioApp;
import gimnasiouq.viewcontroller.AdminViewController;
import gimnasiouq.viewcontroller.RecepViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginViewController {

    @FXML private Button loginButton;
    @FXML private Label txtAdvertencia;
    @FXML private PasswordField txtPasswordLogin;
    @FXML private ComboBox <String> comboBoxUser;
    @FXML
    public void initialize() {
        comboBoxUser.getItems().addAll("Administrador", "Recepcionista");
    }

    @FXML
    void login(ActionEvent event) {

        String user = comboBoxUser.getValue();
        String pass = txtPasswordLogin.getText();
        String resultado = procesarLogin(user, pass);

        if ("ADMINISTRADOR".equals(resultado)) {
            GimnasioApp.goToAdministrador();
        } else if ("RECEPCIONISTA".equals(resultado)) {
            GimnasioApp.goToRecepcionista();
        } else {
            mostrarVentanaEmergente("Error de autenticación", null, "Credenciales incorrectas", Alert.AlertType.ERROR);
        }
    }

    protected String procesarLogin(String user, String pass) {
        if (user == null || user.trim().isEmpty()) {
            return "ERROR_USUARIO_VACIO";
        }

        if (pass == null || pass.trim().isEmpty()) {
            return "ERROR_PASSWORD_VACIO";
        }
        if (AdminViewController.validarCredenciales(user, pass)) {
            return "ADMINISTRADOR";
        } else if (RecepViewController.validarCredenciales(user, pass)) {
            return "RECEPCIONISTA";
        } else {
            return "ERROR_CREDENCIALES";
        }
    }

    private void mostrarVentanaEmergente(String titulo, String header, String contenido, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
