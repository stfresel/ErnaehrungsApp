package com.example.fitnessapp;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Controller {

    private final double abstandZuRand = 40;
    private Pane pane;
    private Scene scene;
    private Stage stage;

    public Controller(Stage stage, Scene scene,Pane pane) {
        this.pane = pane;
        this.scene = scene;
        this.stage = stage;
    }

    /**
     * Ruft die Funktion auf, die eine neue oder bereitsbestehende Zutat zum Meal hinzufügt, auf.
     * Sie wird aufgerufen, sobald man sich im Layout Tagebucheintrag befindet.
     */
    public void addZutaten2Meal(){
        Button addZutat2MealBtn = new Button("+");
        addZutat2MealBtn.setPrefHeight(30);
        addZutat2MealBtn.setPrefWidth(30);
        System.out.println("H" + addZutat2MealBtn.getHeight());
        addZutat2MealBtn.setLayoutY(pane.getHeight()-addZutat2MealBtn.getHeight()-abstandZuRand);   //height und width ist immer 0.0 ???
        addZutat2MealBtn.setLayoutX(pane.getWidth()-addZutat2MealBtn.getWidth()-abstandZuRand);
        addZutat2MealBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("+");

            }
        });
        pane.getChildren().removeAll();
        pane.getChildren().add(addZutat2MealBtn);
    }

    private void loadZutatenScene(){
        Pane zutatenPane = new Pane();
        zutatenPane.setPrefWidth(pane.getWidth());
        zutatenPane.setPrefHeight(pane.getHeight());
        // zutaten pane layout

        Scene zutatenScene = new Scene(zutatenPane);
        stage.setScene(zutatenScene);
    }
}