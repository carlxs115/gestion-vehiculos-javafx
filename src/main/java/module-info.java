module com.gestionvehiculos {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires javafx.graphics;

    opens com.gestionvehiculos to javafx.fxml;
    exports com.gestionvehiculos;
    exports com.gestionvehiculos.model;
    opens com.gestionvehiculos.model to javafx.fxml;
    exports com.gestionvehiculos.controller;
    opens com.gestionvehiculos.controller to javafx.fxml;
}