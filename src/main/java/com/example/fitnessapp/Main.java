package com.example.fitnessapp;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

/**
 * <h1>FitnessApp</h1>
 * #####
 */
public class Main extends Application {
    static ArrayList<Zutat> gespeicherteZutaten = new ArrayList<>();
    static Stage stage;
    @Override
    public void start(Stage stage1) {
        stage = stage1;
        stage.setMinWidth(500);
        stage.setMinHeight(500);
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