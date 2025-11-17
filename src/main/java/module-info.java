module gimnasiouq {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.desktop;
    requires gimnasiouq;

    opens gimnasiouq to javafx.fxml;
    exports gimnasiouq;

    exports gimnasiouq.viewcontroller;
    opens gimnasiouq.viewcontroller to javafx.fxml;

    exports gimnasiouq.model;
    opens gimnasiouq.model to javafx.fxml;
}
