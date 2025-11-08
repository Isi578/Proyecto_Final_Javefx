package gimnasiouq.viewcontroller;

import gimnasiouq.GimnasioApp;
import gimnasiouq.util.DataUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class RecepViewController {

    @FXML
    private TabPane tabPane;

    @FXML
    void cerrarSesion(ActionEvent event) {GimnasioApp.mainStage.setScene(GimnasioApp.sceneLogin);}

    @FXML
    void initialize() {}

    public static boolean validarCredenciales(String user, String pass) {
        return user.equals(DataUtil.RECEPCIONISTA) && pass.equals(DataUtil.RECEP_CONTRASENA);
    }
}