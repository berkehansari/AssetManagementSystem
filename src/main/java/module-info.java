module com.example.prototest2 {
    requires javafx.controls;
    requires javafx.fxml;
            
            requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.prototest2 to javafx.fxml;
    exports com.example.prototest2;
}