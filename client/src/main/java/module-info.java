module com.projet_rip {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.projet_rip to javafx.fxml;
    exports com.projet_rip;
}
