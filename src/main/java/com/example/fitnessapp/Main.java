package com.example.fitnessapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Main extends Application {
    static ArrayList<Zutat> gespeicherteZutaten = new ArrayList<>();

    static Benutzer benutzer = new Benutzer();
    static Pane pane = new Pane();
    static Stage stage;
    @Override
    public void start(Stage stage1) {
        stage = stage1;
        pane.setPrefHeight(500);
        pane.setPrefWidth(500);
        stage.setTitle("FitnessApp");
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        Controller c = new Controller(benutzer);
        c.start();

        gespeicherteZutaten.add(new Zutat("Zuckersirup", 100, new Naehrwerte(100, 100, 100, 100)));
        gespeicherteZutaten.add(new Zutat("Honig", 100, new Naehrwerte(100, 100, 100, 100)));
        gespeicherteZutaten.add(new Zutat("Mehl", 100, new Naehrwerte(100, 100, 100, 100)));

        /*
        stage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            @Override
            public void handle(WindowEvent windowEvent) {
                System.out.println("closing");
                Platform.exit();
                b1.tagebuchSpeichern();
                for (int i = 0; i < 20; i++) {
                    System.out.println(i);
                }
            }
        });

         */

    }

    public static void main(String[] args) {
        launch();
    }
}