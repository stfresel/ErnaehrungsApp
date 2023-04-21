package com.example.fitnessapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    static ArrayList<Zutat> gespeicherteZutaten = new ArrayList<>();
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Miau!");
        stage.setScene(scene);
        stage.show();
        Controller controller = new Controller();
        Meal m = new Meal();

        //m.neueZutatErstellen("Honig", 20, 100, new Naehrwerte(200, 200, 200, 200));
        //m.neueZutatErstellen("Milch", 20, 100, new Naehrwerte(100, 100, 100, 100));
        //m.neueZutatErstellen("Schoko", 100, 200, new Naehrwerte(300,300,300,300));



        m.addZutat("Milch");
        //m.addZutat("Honig");
        m.addZutat("Schoko");

        System.out.println(m);
    }

    public static void main(String[] args) {
        launch();
    }
}