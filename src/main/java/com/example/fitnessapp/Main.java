package com.example.fitnessapp;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;
/*
 * Die Applikation soll es ermöglichen seine Ernährung zu tracken und somit auch gesünder und bewusster zu essen.
 * Die Menge der Nährwertangaben wird individuell für jede Person berechnet.
 */

/**
 * Erstellt die Stage und bindet das css-File ein.
 */
public class Main extends Application {
    static ArrayList<Zutat> gespeicherteZutaten = new ArrayList<>();
    static Stage stage;
    @Override
    public void start(Stage stage1) {
        stage = stage1;
        stage.setMinWidth(500);
        stage.setMinHeight(500);
        stage.setMaxWidth(750);
        stage.setMaxHeight(500);
        Pane pane = new Pane();
        pane.setPrefSize(500, 500);
        stage.setTitle("FitnessApp");
        Scene scene = new Scene(pane);
        String css = Objects.requireNonNull(this.getClass().getResource("styles.css")).toExternalForm();
        scene.getStylesheets().add(css);
        System.out.println(scene.getStylesheets());
        stage.setScene(scene);
        stage.show();
        Controller c = new Controller();
        c.initialize();
    }

    /**
     * Die Methode lädt eine neue Szene und fügt das Stylesheet hinzu.
     * @param s Gibt die Szene an, welche angezeigt werden soll.
     */
    public static void switchScene(Scene s){
        try {
            String cssStyleFile = Objects.requireNonNull( Main.class.getResource("styles.css").toExternalForm() );
            s.getStylesheets().add(cssStyleFile);
            Main.stage.setScene(s);
        } catch(Exception e) {
            System.out.println("Exception @ switchScene()");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}