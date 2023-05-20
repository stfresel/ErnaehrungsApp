package com.example.fitnessapp;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
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
      * Gibt die Statistik des Benutzers an
      */
     private Statistik statistik = new Statistik();
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
      * Die Methode ladet die UI-Komponenten des Homescreens
      * Daz##
      * Zudem wird festgelegt, dass beim schliessen des Fensters, <code>home</code> automatisch gespeichert wird.
      */
     public void startHome(){
          Main.gespeicherteZutaten = gespeicherteZutaten;

          //Main.benutzer.datenSpeichern();
          Controller.datenSpeichern();
          Main.stage.setOnCloseRequest(new EventHandler<>(){
               @Override
               public void handle(WindowEvent windowEvent) {
                    System.out.println("closing");
                    Platform.exit();
                    //Main.benutzer.datenSpeichern();
                    Controller.datenSpeichern();

               }
          });

          addTage2Tagebuch();

          BorderPane borderPane = new BorderPane();
          borderPane.setPrefSize(Main.stage.getScene().getWidth(), Main.stage.getScene().getHeight());
          //borderPane.setStyle("-fx-background-color: transparent;");

          //-------------------UI--------------------------------------------------------------------------------
          //Pane
          Pane pane = new Pane();

          UIstart uiBackground = new UIstart();
          uiBackground.display(pane);

          //Bild
          ImageView statImg = loadImg("src/main/resources/com/example/fitnessapp/statsIcon.png");
          ImageView profileImg = loadImg("src/main/resources/com/example/fitnessapp/profilIcon.png");
          ImageView tagebuchImg = loadImg("src/main/resources/com/example/fitnessapp/tagebuchIcon.png");
          ImageView settingsImg = loadImg("src/main/resources/com/example/fitnessapp/settingsIcon.png");
          double iconsize = 50;
          profileImg.setFitHeight(iconsize);
          profileImg.setFitWidth(iconsize);
          tagebuchImg.setFitHeight(iconsize);
          tagebuchImg.setFitWidth(iconsize);
          statImg.setFitHeight(iconsize);
          statImg.setFitWidth(iconsize);
          settingsImg.setFitHeight(iconsize);
          settingsImg.setFitWidth(iconsize);
          borderPane.getStyleClass().add("button-login");

          VBox iconHolder = new VBox();
          HBox uispacer = new HBox();

          iconHolder.getChildren().add(profileImg);
          iconHolder.getChildren().add(tagebuchImg);
          iconHolder.getChildren().add(statImg);

          uispacer.getChildren().add(iconHolder);
          iconHolder.setAlignment(Pos.CENTER);


          uispacer.getChildren().add(new Rectangle(100, 0));
          borderPane.setLeft(uispacer);


          ToolBar toolBar = new ToolBar();
          Button tagebuchButton = new Button("Tagebuch");
          tagebuchImg.setOnMouseClicked(new EventHandler<>() {
               @Override
               public void handle(MouseEvent mouseEvent) {
                    borderPane.setCenter(tagebuch.loadTagebuch());
               }
          });
          Button kontoButton = new Button("Konto");
          profileImg.setOnMouseClicked(new EventHandler<>() {
               @Override
               public void handle(MouseEvent mouseEvent) {
                    borderPane.setCenter(konto.loadKonto());
               }
          });



          //Button für Statistiken
          Button statButton = new Button("Statistik");
          statImg.setOnMouseClicked(new EventHandler<>() {
               @Override
               public void handle(MouseEvent mouseEvent) {
                    borderPane.setCenter(statistik.loadStat());
               }
          });
          toolBar.getItems().addAll(tagebuchButton, kontoButton, statButton);
          toolBar.setLayoutY(Main.stage.getHeight());
          //borderPane.setTop(toolBar);
          borderPane.setBottom(new Rectangle(0, randobenunten));
          borderPane.setTop(new Rectangle(0 , randobenunten));
          borderPane.getLeft().setStyle("-fx-row-valignment: center;");
          borderPane.setCenter(tagebuch.loadTagebuch());
          //Main.stage.setScene(new Scene(borderPane));

          uiBackground.setsize(Main.stage.getScene().getWidth()+200, Main.stage.getScene().getHeight()-randobenunten*2+100);
          uiBackground.setpos(70, randobenunten-50);
          //Main.stage.getScene().heightProperty().addListener((obs, oldVal, newVal) -> uiBackground.setsize(Main.stage.getScene().getWidth() - 50, Main.stage.getScene().getHeight()-randobenunten*2+100));


          pane.setStyle(" -fx-background-color: #B6CC95;");
          pane.getChildren().add(borderPane);

          Main.switchScene(new Scene(pane));
     }

     /**
      * Fügt einen Tag zum Tagebuch hinzu
      * Die Methode überprüft, ob der heutige Tag bereits abgespeichert wurde. Falls nicht, dann wird er hinzugefügt.
      *
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

     public ImageView loadImg(String src){
          //laden Hintergrund
          InputStream stream;
          try {
               stream = new FileInputStream(src);
          } catch (FileNotFoundException e) {
               throw new RuntimeException(e);
          }
          Image image = new Image(stream);
          ImageView imageView = new ImageView();
          imageView.setImage(image);
          return imageView;
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
