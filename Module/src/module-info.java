module Module {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    opens codes.starter;
    opens codes.phases;
    opens codes.result;
}