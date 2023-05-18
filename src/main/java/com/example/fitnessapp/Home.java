package com.example.fitnessapp;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.WindowEvent;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Home implements Serializable {

     private Konto konto;
     private Statistik statistik;
     private Tagebuch tagebuch;
// wichtig, da ansonst die Zuatenen nicht serialisiert werden
     private ArrayList<Zutat> gespeicherteZutaten = new ArrayList<>();


     public Home(){
          tagebuch = new Tagebuch();
          konto = new Konto();
          statistik = new Statistik();
     }

     /**
      * Die Methode ladet die Toolbar des HomeScreens.
      * Es wird festgelegt, dass beim schliessen des Fensters, die Daten automatisch gespeichert werden:
      */
     public void startHome(){

          Main.gespeicherteZutaten = gespeicherteZutaten;

          Main.benutzer.datenSpeichern();
          Main.stage.setOnCloseRequest(new EventHandler<WindowEvent>(){
               @Override
               public void handle(WindowEvent windowEvent) {
                    System.out.println("closing");
                    Platform.exit();
                    Main.benutzer.datenSpeichern();
               }
          });

          addTage2Tagebuch();

          BorderPane borderPane = new BorderPane();
          borderPane.setPrefSize(Main.stage.getScene().getWidth(), Main.stage.getScene().getHeight());
          ToolBar toolBar = new ToolBar();
          Button tagebuchButton = new Button("Tagebuch");
          tagebuchButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
               @Override
               public void handle(MouseEvent mouseEvent) {
                    borderPane.setCenter(tagebuch.loadTagebuch());
               }
          });
          Button kontoButton = new Button("Konto");
          kontoButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
               @Override
               public void handle(MouseEvent mouseEvent) {
                    borderPane.setCenter(konto.loadKonto());
               }
          });

          Button statButton = new Button("Statistik");
          statButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
               @Override
               public void handle(MouseEvent mouseEvent) {
                    borderPane.setCenter(statistik.loadStat());
               }
          });
          toolBar.getItems().addAll(tagebuchButton, kontoButton, statButton);
          toolBar.setLayoutY(Main.stage.getHeight());
          borderPane.setTop(toolBar);
          borderPane.setCenter(tagebuch.loadTagebuch());
          //Main.stage.setScene(new Scene(borderPane));
          Main.switchScene(new Scene(borderPane));
     }

     /**
      * Die Methode fügt den heutigen Tag mit Datum zum Tagebuch hinzu, wenn er noch nicht erstellt wurde.
      */
     private void addTage2Tagebuch(){
          if (tagebuch.getAnzahlTage() < 1){
               Tag t1 = new Tag(LocalDate.now());
               //tagebuch.addTag(t1);        wieder einfügen

               //-----------
               tagebuch.addTag(new Tag(LocalDate.of(2023,5,3)));
               tagebuch.addTag(new Tag(LocalDate.of(2023,5,4)));
               tagebuch.addTag(t1);
               //-------
          } else if (!Objects.equals(tagebuch.getLastDay(), LocalDate.now())){
               Tag t1 = new Tag(LocalDate.now());
               tagebuch.addTag(t1);
          }
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
