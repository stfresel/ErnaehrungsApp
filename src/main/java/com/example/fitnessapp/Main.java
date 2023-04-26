package com.example.fitnessapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Main extends Application {
    static ArrayList<Zutat> gespeicherteZutaten = new ArrayList<>();

    static Pane pane = new Pane();
    static Stage stage;
    private Path path = Paths.get("TagebuchFile.ser");

    @Override
    public void start(Stage stage1) {
        stage = stage1;
        pane.setPrefHeight(500);
        pane.setPrefWidth(500);
        Scene scene = new Scene(pane);
        stage.setTitle("FitnessApp");
        stage.setScene(scene);
        stage.show();
        Tagebuch tagebuch = new Tagebuch();
        Tag t1 = new Tag(LocalDate.of(2023, 5, 23));
        tagebuch.addTag(t1);
        Tag t2 = new Tag(LocalDate.of(2023, 5, 24));
        tagebuch.addTag(t2);
        Tag t3 = new Tag(LocalDate.of(2023, 5, 25));
        t3.addMeal(new Meal());
        tagebuch.addTag(t3);
        tagebuch.loadTagebuchScene();


    }

    public static void main(String[] args) {
        launch();
    }

    // im Benutzer schreiben + in Path vom .ser file
    // evt konn man a es .ser file wegtien wo die zutaten gespeichert sein
    public void saveTagebuch(Tagebuch t) {
        try (ObjectOutputStream whereToWrite = new ObjectOutputStream(Files.newOutputStream(
                path, StandardOpenOption.CREATE))) {

            whereToWrite.writeObject(t);
        } catch (IOException ioe) {
            System.out.println("Can't serialize file: " + ioe.getMessage());
        }
    }

    public Tagebuch loadTagebuch(Tagebuch t) {// des t net übergeben! ->in der Main bzw Benutzer uebergeben
        try (ObjectInputStream whereToReadFrom = new ObjectInputStream(Files.newInputStream(path))){

            t = (Tagebuch) whereToReadFrom.readObject();

        } catch (ClassNotFoundException cnfe) {
            System.out.println("Can't find the declaration of Tagebuch: " + cnfe.getMessage());
        }  catch (IOException ioe) {
            System.out.println("Can't deserialize file: " + ioe.getMessage());
        }
        return t;
    }
}