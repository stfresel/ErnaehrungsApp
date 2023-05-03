package com.example.fitnessapp;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;

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
          Main.benutzer.datenSpeichern();
          Main.stage.setOnCloseRequest(new EventHandler<WindowEvent>(){
               @Override
               public void handle(WindowEvent windowEvent) {
                    System.out.println("closing");
                    Platform.exit();
                    Main.benutzer.datenSpeichern();
               }
          });

          if (tagebuch.getAnzahlTage() < 1){
               Tag t1 = new Tag(LocalDate.now());
               tagebuch.addTag(t1);
          } else if (!Objects.equals(tagebuch.getLastDay(), LocalDate.now())){
               Tag t1 = new Tag(LocalDate.now());
               tagebuch.addTag(t1);
          }
          //______________________________________
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
          //TabPane tabPane = new TabPane();
          //Tab tagebuchTab = new Tab("Tagebuch", tagebuch.loadTagebuchScene());
          //Tab kontoTab = new Tab("Konto", konto.loadKontoScene());
          //Tab statTab = new Tab("Statistik", statistik.loadStatScene());
          //tabPane.getTabs().addAll(tagebuchTab, kontoTab, statTab);
          Main.stage.setScene(new Scene(borderPane));
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
