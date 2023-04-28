package com.example.fitnessapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
        Tagebuch tagebuch = new Tagebuch();


        Tag t1 = new Tag(new Date(2023, Calendar.APRIL, 23));
        t1.addMeal(new Meal());
        tagebuch.addTag(t1);
        Tag t2 = new Tag(new Date(2023, Calendar.APRIL, 24));
        tagebuch.addTag(t2);
        Tag t3 = new Tag(new Date(2023, Calendar.APRIL, 25));
        tagebuch.addTag(t3);

        //tagebuch.loadTagebuchScene();


        Controller controller = new Controller(stage, scene, pane);
        //controller.addZutaten2Meal();



    }

    public static void main(String[] args) {
        launch();
    }
}