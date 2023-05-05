package com.example.fitnessapp;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;

import java.io.Serializable;

public class Statistik implements Serializable {

    public Pane loadStat() {
        Pane pane = new Pane();
        pane.getChildren().add(diagrammNaehrwerte());
        return pane;
    }

    private LineChart diagrammNaehrwerte() {

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        LineChart<Number, Number> diagramm = new LineChart<>(xAxis, yAxis);

        // idee --> mann konn die anzahl der tage verändern mit an comboBox

        xAxis.setLabel("Datum");
        yAxis.setLabel("Aufgenommen");
        LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis, yAxis);

        lineChart.setTitle("Übersicht Nährwerte");

        Tagebuch tagebuch = Main.benutzer.getHome().getTagebuch();

        XYChart.Series<Number, Number> seriesKcal = new XYChart.Series<Number, Number>();
        seriesKcal.setName("Kalorien");
        for (int i = 0; i < tagebuch.getAnzahlTage(); i++) {
            seriesKcal.getData().add(new XYChart.Data<>(tagebuch.getTag(i).getDate().toEpochDay(), tagebuch.getTag(i).getInsgesamteNaehrwerte().getKcal()));
        }

        XYChart.Series<Number, Number> seriesKohlenhydrate = new XYChart.Series<Number, Number>();
        seriesKcal.setName("Kohlenhydrate");
        for (int i = 0; i < tagebuch.getAnzahlTage(); i++) {
            seriesKohlenhydrate.getData().add(new XYChart.Data<>(tagebuch.getTag(i).getDate().toEpochDay(), tagebuch.getTag(i).getInsgesamteNaehrwerte().getKohlenhydrate()));
        }

        XYChart.Series<Number, Number> seriesProteine = new XYChart.Series<Number, Number>();
        seriesKcal.setName("Proteine");
        for (int i = 0; i < tagebuch.getAnzahlTage(); i++) {
            seriesProteine.getData().add(new XYChart.Data<>(tagebuch.getTag(i).getDate().toEpochDay(), tagebuch.getTag(i).getInsgesamteNaehrwerte().getProtein()));
        }

        XYChart.Series<Number, Number> seriesFette = new XYChart.Series<Number, Number>();
        seriesKcal.setName("Fette");
        for (int i = 0; i < tagebuch.getAnzahlTage(); i++) {
            seriesFette.getData().add(new XYChart.Data<>(tagebuch.getTag(i).getDate().toEpochDay(), tagebuch.getTag(i).getInsgesamteNaehrwerte().getFett()));
        }

        return diagramm;
    }
}
