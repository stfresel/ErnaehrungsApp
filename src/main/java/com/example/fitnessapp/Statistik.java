package com.example.fitnessapp;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.Serializable;

public class Statistik implements Serializable {

    public Pane loadStat() {
        Pane pane = new Pane();
        pane.getChildren().add(new Label("keine Statistik vorhanden"));
        return pane;
    }
}
