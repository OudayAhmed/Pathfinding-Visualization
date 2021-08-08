module com.ouday {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;

    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome;
    requires org.kordamp.ikonli.materialdesign2;
    requires org.kordamp.ikonli.evaicons;

    opens com.ouday.controller to javafx.fxml;
    exports com.ouday;
}