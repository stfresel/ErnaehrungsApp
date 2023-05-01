package com.example.fitnessapp;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Home implements Serializable {
     private Konto konto;
     private Statistik statistik;
     private Tagebuch tagebuch;

     public Home(){
          tagebuch = new Tagebuch();
          konto = new Konto();
          statistik = new Statistik();
     }
     public Home(Home h) {
          konto = h.konto;
          statistik = h.statistik;
          tagebuch = h.tagebuch;
     }

     //----------------funktions----------------------------------
     public void startHome(){
          if (tagebuch.getAnzahlTage() < 1){
               Tag t1 = new Tag(LocalDate.now());
               tagebuch.addTag(t1);
          } else if (!Objects.equals(tagebuch.getLastDay(), LocalDate.now())){
               Tag t1 = new Tag(LocalDate.now());
               tagebuch.addTag(t1);
          }

          TabPane tabPane = new TabPane();
          Tab tagebuchTab = new Tab("Tagebuch", tagebuch.loadTagebuchScene());
          Tab kontoTab = new Tab("Konto", konto.loadKontoScene());
          Tab statTab = new Tab("Statistik", statistik.loadStatScene());
          tabPane.getTabs().addAll(tagebuchTab, kontoTab, statTab);
          Main.stage.setScene(new Scene(tabPane));
     }




     //---------------getter und setter--------------------------
     public Konto getKonto() {
          return konto;
     }

     public void setKonto(Konto konto) {
          this.konto = konto;
     }

     public Statistik getStatistik() {
          return statistik;
     }

     public void setStatistik(Statistik statistik) {
          this.statistik = statistik;
     }

     public Tagebuch getTagebuch() {
          return tagebuch;
     }

     public void setTagebuch(Tagebuch tagebuch) {
          this.tagebuch = tagebuch;
     }
}
