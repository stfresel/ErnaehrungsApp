package com.example.fitnessapp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controller {

    private final double abstandZuRand = 40;

    private final double prefSize = 30;
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
        addZutat2MealBtn.setPrefHeight(prefSize);
        addZutat2MealBtn.setPrefWidth(prefSize);
        System.out.println("H" + addZutat2MealBtn.getHeight());
        addZutat2MealBtn.setLayoutY(pane.getHeight()-addZutat2MealBtn.getHeight()-abstandZuRand);   //height und width ist immer 0.0 ???
        addZutat2MealBtn.setLayoutX(pane.getWidth()-addZutat2MealBtn.getWidth()-abstandZuRand);
        addZutat2MealBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("+");
                loadZutatenScene();
            }
        });
        pane.getChildren().removeAll();
        pane.getChildren().add(addZutat2MealBtn);
    }

    private void loadZutatenScene(){
        VBox zutatenPane = new VBox();
        zutatenPane.setPrefWidth(pane.getWidth());
        zutatenPane.setPrefHeight(pane.getHeight());

        GridPane zutatSuchen = new GridPane();
        GridPane zutatErstellen = new GridPane();
    // zutat suchen
        CheckBox checkbox = new CheckBox("neue Zutat erstellen");

        Label nameDerZutat = new Label("Name: ");
        TextField textFieldName = new TextField();

        Label mengeGegessen = new Label("Gegessene Menge (in g): ");
        TextField textFieldGegessen = new TextField();
        //zutatenPane.getChildren().addAll(nameDerZutat, textFieldName, mengeGegessen, textFieldGegessen);

        zutatSuchen.addRow(0, checkbox);
        zutatSuchen.addRow(1, nameDerZutat, textFieldName);
        zutatSuchen.addRow(2, mengeGegessen, textFieldGegessen);

    // zutat erstellen
        Label naehrwerteaufXgramm = new Label("Menge der Nährwertangabe (in g): ");
        TextField grammTextField = new TextField();

        Label proteine = new Label("Proteine: ");
        TextField proteineTextField = new TextField();

        Label fette = new Label("Fette: ");
        TextField fetteTextField = new TextField();

        Label kolenhydrate = new Label("Kolenhydrate: ");
        TextField kolenhydrateTextField = new TextField();

        Label kcal = new Label("Kalorien: ");
        TextField kcalTextField = new TextField();

        zutatErstellen.addRow(0, naehrwerteaufXgramm, grammTextField);
        zutatErstellen.addRow(1, kcal, kcalTextField);
        zutatErstellen.addRow(2, kolenhydrate, kolenhydrateTextField);
        zutatErstellen.addRow(3, proteine, proteineTextField);
        zutatErstellen.addRow(4, fette,fetteTextField);

        checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                System.out.println("change");
                if (checkbox.isSelected()){ // neue Zutat erstellen
                    zutatenPane.getChildren().add(zutatErstellen);
                } else {                    // Zutat aus Speicher holen
                    if (zutatenPane.getChildren().contains(zutatErstellen)){
                        zutatenPane.getChildren().remove(zutatErstellen);
                    }
                }
            }
        });

        zutatenPane.getChildren().add(zutatSuchen);
        // erstellen des Submit-Buttons
        Button fertigBtn = new Button("Fertig");
        fertigBtn.setPrefWidth(prefSize*2);
        fertigBtn.setPrefHeight(prefSize);
        fertigBtn.setLayoutY(zutatenPane.getHeight()-prefSize-abstandZuRand);

        // inuputs noch in Var speichern!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        fertigBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Zutat aus Speicher holen

                // Zutat neu Hinzufügen
                //---------------------------------------------
            }
        });
        zutatenPane.getChildren().add(fertigBtn);
        Scene zutatenScene = new Scene(zutatenPane);
        stage.setScene(zutatenScene);

    }
}