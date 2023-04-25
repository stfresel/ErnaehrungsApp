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

}