module com.example.javafx_calculadoracompleja {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javafx_calculadoracompleja to javafx.fxml;
    exports com.example.javafx_calculadoracompleja;

}