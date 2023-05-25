package com.example.fitnessapp;

import javafx.application.Platform;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.WindowEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Ermöglicht den Zugriff auf alle Funktionalitäten des Programmes.
 */
public class Home implements Serializable {

     /**
      * Gibt das Konto des Benutzers an.
      */
     private Konto konto = new Konto();

     /**
      * Gibt das Tagebuch des Benutzers an.
      */
     private Tagebuch tagebuch = new Tagebuch();
// wichtig, da ansonst die Zuatenen nicht serialisiert werden
     /**
      * In der ArrayList werden die bereits verwendeten Zutaten gespeichert.
      */
     private ArrayList<Zutat> gespeicherteZutaten = new ArrayList<>();
     private int randobenunten = 100;

     /**
      * Die Methode ladet die UI-Komponenten des Homescreens.
      * Zudem wird festgelegt, dass beim schliessen des Fensters, <code>home</code> automatisch gespeichert wird.
      */
     public void startHome(){
          Main.gespeicherteZutaten = gespeicherteZutaten;

          Main.controller.datenSpeichern();
          Main.stage.setOnCloseRequest(new EventHandler<>(){
               @Override
               public void handle(WindowEvent windowEvent) {
                    System.out.println("closing");
                    Main.controller.datenSpeichern();
                    Platform.exit();
               }
          });

          addTage2Tagebuch();

          BorderPane borderPane = new BorderPane();
          borderPane.setPrefSize(Main.stage.getScene().getWidth(), Main.stage.getScene().getHeight());


          UIstart uiBackground = new UIstart();
          uiBackground.display(borderPane);

          //Bild
          ImageView statImg = loadImg("statsIcon.png");
          ImageView profileImg = loadImg("profilIcon.png");
          ImageView tagebuchImg = loadImg("tagebuchIcon.png");
          ImageView settingsImg = loadImg("settingsIcon.png");
          ImageView iconImg = loadImg("logo.png");


          double iconsize = 50;
          profileImg.setFitHeight(iconsize);
          profileImg.setFitWidth(iconsize);
          tagebuchImg.setFitHeight(iconsize);
          tagebuchImg.setFitWidth(iconsize);
          statImg.setFitHeight(iconsize);
          statImg.setFitWidth(iconsize);
          settingsImg.setFitHeight(iconsize);
          settingsImg.setFitWidth(iconsize);
          iconImg.setFitHeight(iconsize*1.7);
          iconImg.setFitWidth(iconsize*1.7);
          borderPane.getStyleClass().add("button-login");

          VBox iconHolder = new VBox();
          HBox uispacer = new HBox();
          HBox topUIspacer = new HBox();

          topUIspacer.getChildren().add(iconImg);
          topUIspacer.getChildren().add(new Rectangle(0 , randobenunten));

          iconHolder.setSpacing(20);
          iconHolder.getChildren().add(profileImg);
          iconHolder.getChildren().add(tagebuchImg);
          iconHolder.getChildren().add(statImg);

          uispacer.getChildren().add(iconHolder);
          iconHolder.setAlignment(Pos.CENTER);


          uispacer.getChildren().add(new Rectangle(100, 0));
          borderPane.setLeft(uispacer);


          //ToolBar toolBar = new ToolBar();
          //Button tagebuchButton = new Button("Tagebuch");
          tagebuchImg.setOnMouseClicked(new EventHandler<>() {
               @Override
               public void handle(MouseEvent mouseEvent) {
                    borderPane.setCenter(tagebuch.loadTagebuch());
               }
          });
          //Button kontoButton = new Button("Konto");
          profileImg.setOnMouseClicked(new EventHandler<>() {
               @Override
               public void handle(MouseEvent mouseEvent) {
                    borderPane.setCenter(konto.loadKonto());
               }
          });



          //Button für Statistiken

          //Button statButton = new Button("Statistik");
          statImg.setOnMouseClicked(new EventHandler<>() {
               @Override
               public void handle(MouseEvent mouseEvent) {
                    borderPane.setCenter(loadStat());
               }
          });


          borderPane.setBottom(new Rectangle(0, randobenunten));
          borderPane.setTop(topUIspacer);
          borderPane.getLeft().setStyle("-fx-row-valignment: center;");
          borderPane.setCenter(tagebuch.loadTagebuch());

          uiBackground.setsize(Main.stage.getScene().getWidth()+200, Main.stage.getScene().getHeight()-randobenunten*2+100);
          uiBackground.setpos(70, randobenunten-50);

          borderPane.setStyle(" -fx-background-color: #B6CC95;");

          Main.switchScene(new Scene(borderPane));
     }

     /**
      * Fügt einen Tag zum Tagebuch hinzu.
      * Die Methode überprüft, ob der heutige Tag bereits abgespeichert wurde. Falls nicht, dann wird er hinzugefügt.
      *
      */
     private void addTage2Tagebuch(){
          if (tagebuch.getAnzahlTage() < 1 || !Objects.equals(tagebuch.getLastDay(), LocalDate.now())){
               Tag t1 = new Tag(LocalDate.now());
               tagebuch.addTag(t1);
          }
     }

     /**
      * Ladet den Hintergrund der Szene.
      * @param src Speicherort des Bildes
      * @return Gibt das Bild als ImageView zurück
      */
     public ImageView loadImg(String src){
          //laden Hintergrund
          InputStream stream;
          try {
               stream = new FileInputStream(Objects.requireNonNull(this.getClass().getResource(src)).getPath());
          } catch (FileNotFoundException e) {
               throw new RuntimeException(e);
          }
          Image image = new Image(stream);
          ImageView imageView = new ImageView();
          imageView.setImage(image);
          return imageView;
     }

     /**
      * Erstellt ein Pane und fügt die UI-Komponenten dazu.
      * @return Gibt das Pane mit der Statistik zurück
      */
     public Pane loadStat() {
          Pane pane = new Pane();
          final NumberAxis xAxis = new NumberAxis();
          final NumberAxis yAxis = new NumberAxis();

          XYChart.Series<Number, Number> seriesKcal = new XYChart.Series<>();
          XYChart.Series<Number, Number> seriesKohlenhydrate = new XYChart.Series<>();
          XYChart.Series<Number, Number> seriesProteine = new XYChart.Series<>();
          XYChart.Series<Number, Number> seriesFette = new XYChart.Series<>();

          xAxis.setTickLabelsVisible(false);
          yAxis.setLabel("Menge");
          LineChart<Number,Number> lineChart = new LineChart<>(xAxis, yAxis);

          lineChart.setTitle("Übersicht der aufgenommenen Nährwerte");
          lineChart.setScaleX(0.85);
          lineChart.setScaleY(0.85);

          Tagebuch tagebuch = Controller.benutzer.getHome().getTagebuch();

          seriesKcal.setName("\tKcal\t");
          seriesKohlenhydrate.setName("\tKohlenhydrate\t");
          seriesProteine.setName("\tProteine\t");
          seriesFette.setName("\tFette\t");
          for (int i = 0; i < tagebuch.getAnzahlTage(); i++) {
               seriesKcal.getData().add(new XYChart.Data<>(tagebuch.getTag(i).getDate().toEpochDay(), tagebuch.getTag(i).getInsgesamteNaehrwerte().getKcal()));
               seriesKohlenhydrate.getData().add(new XYChart.Data<>(tagebuch.getTag(i).getDate().toEpochDay(), tagebuch.getTag(i).getInsgesamteNaehrwerte().getKohlenhydrate()));
               seriesProteine.getData().add(new XYChart.Data<>(tagebuch.getTag(i).getDate().toEpochDay(), tagebuch.getTag(i).getInsgesamteNaehrwerte().getProtein()));
               seriesFette.getData().add(new XYChart.Data<>(tagebuch.getTag(i).getDate().toEpochDay(), tagebuch.getTag(i).getInsgesamteNaehrwerte().getFette()));

          }

          lineChart.getData().add(seriesKcal);
          lineChart.getData().add(seriesKohlenhydrate);
          lineChart.getData().add(seriesProteine);
          lineChart.getData().add(seriesFette);

          lineChart.setLayoutY(-50);
          pane.getChildren().add(lineChart);
          return pane;
     }




     //---------------getter und setter--------------------------

     /**
      * Gibt das Konto zurück.
      * @return Gibt eine Instanz von Konto zurück
      */
     public Konto getKonto() {
          return konto;
     }


     /**
      * Gibt das Tagebuch zurück.
      * @return Gibt eine Instanz von Tagebuch zurück
      */
     public Tagebuch getTagebuch() {
          return tagebuch;
     }

}
