module org.example.proyecto_final_javefx {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.proyecto_final_javefx to javafx.fxml;
    exports org.example.proyecto_final_javefx;

    exports org.example.proyecto_final_javefx.viewcontroller;
    opens org.example.proyecto_final_javefx.viewcontroller to javafx.fxml;

    exports org.example.proyecto_final_javefx.model;
    opens org.example.proyecto_final_javefx.model to javafx.fxml;
}
