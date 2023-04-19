package com.example.fitnessapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Miau!");
        stage.setScene(scene);
        stage.show();
        Controller controller = new Controller();

        Meal m = new Meal();
        m.neueZutatErstellen("Milch", 20, 100, new Naehrwerte(100, 100, 100, 100));
        m.neueZutatErstellen("Honig", 20, 100, new Naehrwerte(200, 200, 200, 200));


        m.addZutat("Milch");
    }

    public static void main(String[] args) {
        launch();
    }
}