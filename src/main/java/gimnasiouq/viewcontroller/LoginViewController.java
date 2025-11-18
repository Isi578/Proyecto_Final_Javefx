package gimnasiouq.viewcontroller;

import gimnasiouq.GimnasioApp;
import gimnasiouq.factory.ModelFactory;
import gimnasiouq.model.Gimnasio;
import gimnasiouq.util.DataUtil; // Importar DataUtil
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
    private ComboBox<String> comboBoxUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.gimnasio = ModelFactory.getInstance().getGimnasio();
        comboBoxUser.getItems().addAll(DataUtil.ADMINISTRADOR, DataUtil.RECEPCIONISTA); // Usar constantes de DataUtil
        txtAdvertencia.setText("");
    }

    @FXML
    void login(ActionEvent event) {
        String user = comboBoxUser.getSelectionModel().getSelectedItem();
        String pass = txtPasswordLogin.getText();

        if (user == null || user.trim().isEmpty() || pass == null || pass.trim().isEmpty()) {
            mostrarAlerta("Error", "Los campos de usuario y contraseña no pueden estar vacíos.", Alert.AlertType.WARNING);
            return;
        }

        // Autenticación usando las constantes de DataUtil
        if (user.equals(DataUtil.ADMINISTRADOR) && pass.equals(DataUtil.ADMIN_CONTRASENA)) {
            GimnasioApp.goToAdministrador();
        } else if (user.equals(DataUtil.RECEPCIONISTA) && pass.equals(DataUtil.RECEP_CONTRASENA)) {
            GimnasioApp.goToRecepcionista();
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
