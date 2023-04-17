module com.example.fitnessapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.fitnessapp to javafx.fxml;
    exports com.example.fitnessapp;
}