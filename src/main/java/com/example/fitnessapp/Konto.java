package com.example.fitnessapp;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.Serializable;

public class Konto implements Serializable {

    public Pane loadKontoScene() {
        Pane pane = new Pane();
        pane.getChildren().add(new Label("keine Kontoansicht vorhanden"));
        return pane;
    }
}
