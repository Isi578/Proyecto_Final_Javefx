package gimnasiouq;

import gimnasiouq.factory.ModelFactory; // Importar ModelFactory
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GimnasioApp extends Application {

    public static Stage mainStage;
    public static Scene sceneLogin;
    public static Scene sceneRecepcionista;
    public static Scene sceneAdministrador;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;

        // Inicializar el ModelFactory para cargar los datos iniciales
        ModelFactory.getInstance(); 

        var loginUrl = GimnasioApp.class.getResource("login.fxml");
        var recepUrl = GimnasioApp.class.getResource("recep.fxml");
        var adminUrl = GimnasioApp.class.getResource("admin.fxml");


        sceneLogin = new Scene(FXMLLoader.load(loginUrl));
        sceneRecepcionista = new Scene(FXMLLoader.load(recepUrl));
        sceneAdministrador = new Scene(FXMLLoader.load(adminUrl));

        goToLogin();
        mainStage.show();
    }

    public static void goToLogin() {
        mainStage.setScene(sceneLogin);
        mainStage.setTitle("Iniciar sesión");
    }

    public static void goToRecepcionista() {
        mainStage.setScene(sceneRecepcionista);
        mainStage.setTitle("Panel de Recepcionista");
    }

    public static void goToAdministrador() {
        mainStage.setScene(sceneAdministrador);
        mainStage.setTitle("Panel de Administrador");
    }
}