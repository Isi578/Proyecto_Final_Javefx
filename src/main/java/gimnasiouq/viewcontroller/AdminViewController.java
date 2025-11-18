package gimnasiouq.viewcontroller;

import gimnasiouq.GimnasioApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class AdminViewController {

    @FXML
    private TabPane tabPane;

    @FXML
    void cerrarSesion(ActionEvent event) {
        // Asumiendo que GimnasioApp maneja las escenas.
        if (GimnasioApp.mainStage != null && GimnasioApp.sceneLogin != null) {
            GimnasioApp.mainStage.setScene(GimnasioApp.sceneLogin);
        }
    }

    @FXML
    void initialize() {
    }
}
