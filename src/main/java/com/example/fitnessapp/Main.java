package com.example.fitnessapp;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Main extends Application {
    static ArrayList<Zutat> gespeicherteZutaten = new ArrayList<>();

    static Pane pane = new Pane();
    static Stage stage;
    @Override
    public void start(Stage stage1) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        stage = stage1;
        pane.setPrefHeight(500);
        pane.setPrefWidth(500);
        stage.setTitle("FitnessApp");
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        Benutzer b1=new Benutzer();
        b1.initialize();




    }

    public static void main(String[] args) {
        launch();
    }
}