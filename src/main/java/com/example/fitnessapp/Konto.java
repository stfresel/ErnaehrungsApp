package com.example.fitnessapp;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Konto implements Serializable {
    private Koerperdaten koerperdaten;
    private transient GridPane gridPaneCalcPart;

    private transient Rectangle backrec = new Rectangle();

    public HBox loadKonto() {
        HBox hBox = new HBox();
        hBox.setPrefSize(Main.stage.getScene().getWidth(), Main.stage.getScene().getHeight());
        gridPaneCalcPart = new GridPane();
        calcPart(gridPaneCalcPart);
        hBox.getChildren().add(0,datenAnsicht());
        hBox.getChildren().add(1, gridPaneCalcPart);

        return hBox;
    }

    public void calcPart(GridPane gridPane) {
        System.out.println("-------------------------------------------");
        koerperdaten.tagesUmsatzBerechnen();
        gridPane.getChildren().clear();
        //gridPane.getChildren().removeAll();
        gridPane.addRow(0, new Label("BMI: " + koerperdaten.getBMI()));
        gridPane.addRow(1,new Label("täglicher Bedarf"));
        gridPane.addRow(2,new Label("Kalorien       : " + koerperdaten.getTagesUmsatz().getKcal()));
        gridPane.addRow(3,new Label("Kohlenhydrate  : " + koerperdaten.getTagesUmsatz().getKohlenhydrate()));
        gridPane.addRow(4,new Label("Proteine       : " + koerperdaten.getTagesUmsatz().getProtein()));
        gridPane.addRow(5,new Label("Fette          : " + koerperdaten.getTagesUmsatz().getFett()));
    }

    public GridPane datenAnsicht(boolean... ifbackground) {
        GridPane gridPane = new GridPane();
        Button speichernBtn = new Button("speichern");
        NumericTextField alterTextField = new NumericTextField();
        NumericTextField groesseTextField = new NumericTextField();
        NumericTextField gewichtTextField = new NumericTextField();
        ComboBox<String> geschlechtCombobox = new ComboBox<>();
        Text textfehler = new Text();
        geschlechtCombobox.getItems().addAll("weiblich", "männlich");

        // Größe vom Konto, beim öffnen vom Konto Tab
        gridPane.setPrefSize(Main.stage.getScene().getWidth()/3, Main.stage.getScene().getHeight()/2);

        if (ifbackground.length > 0) {
                InputStream stream;
            try {
                stream = new FileInputStream("src/main/resources/com/example/fitnessapp/backgroundGreen.png");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            Image background = new Image(stream);

            gridPane.setBackground(new Background(new BackgroundImage(background, null, null, null, new BackgroundSize(100, 100, true, true, false, true))));
            // Größe vom Konto gelich nach dem registrieren
            gridPane.setPrefSize(Main.stage.getScene().getWidth(), Main.stage.getScene().getHeight());
        }

        //gridPane.setBackground(new Background(new BackgroundFill(Paint.valueOf("#B6CC95"), null, null)));

        // Wenn es direkt nach dem Registrieren geöffnet wird
        if (koerperdaten == null){
            speichernBtn.setVisible(true);
            koerperdaten = new Koerperdaten();

            // nur speichern
            speichernBtn.setOnMouseClicked(new EventHandler<>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println(gewichtTextField.getText().length());
                    if (geschlechtCombobox.getSelectionModel().getSelectedItem()==null ||
                            alterTextField.getText().length() < 1 || groesseTextField.getText().length() < 1 ||
                            gewichtTextField.getText().length() < 1){
                        setFehlermeldung(textfehler);

                    }else {
                        System.out.println("Combobox Geschlecht id: " + geschlechtCombobox.getValue());
                        koerperdaten.setKoerperdaten(groesseTextField.getDouble(), gewichtTextField.getDouble(), alterTextField.getDouble(), geschlechtCombobox.getValue());
                        //Main.benutzer.getHome().startHome();
                        Controller.benutzer.getHome().startHome();
                        //############

                    }

                }
            });
        } else {
            groesseTextField.setText(String.valueOf(koerperdaten.getGroesse()));
            gewichtTextField.setText(String.valueOf(koerperdaten.getGewicht()));
            alterTextField.setText(String.valueOf(koerperdaten.getAlter()));
            geschlechtCombobox.setValue(koerperdaten.getGeschlecht());
            //wenn man etwas verändert will
            speichernBtn.setOnMouseClicked(new EventHandler<>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (geschlechtCombobox.getSelectionModel().getSelectedItem() == null ||
                            alterTextField.getText().length() < 1 || groesseTextField.getText().length() < 1 ||
                            gewichtTextField.getText().length() < 1){
                        setFehlermeldung(textfehler);
                    }
                    koerperdaten.setKoerperdaten(groesseTextField.getDouble(), gewichtTextField.getDouble(), alterTextField.getDouble(), geschlechtCombobox.getValue());
                    calcPart(gridPaneCalcPart);
                }
            });


        }

        //Erstellen Liste von Labels
        ArrayList<Label> labellist = new ArrayList<>();
        labellist.add(new Label("Informationen zum Profil:"));
        labellist.add(new Label("Benutzername: "));
        labellist.add(new Label("Passwort: "));
        labellist.add(new Label("Körperdaten eingeben:"));
        labellist.add(new Label("Größe (in m): "));

        labellist.add(new Label("Gewicht (in kg): "));
        labellist.add(new Label("Alter (in Jahren): "));
        labellist.add(new Label("Geschlecht: "));

        labellist.add(new Label(Controller.benutzer.getBenutzername()));
        //############2
        labellist.add(new Label(Controller.benutzer.getPasswort()));

        //Setzen Style Labels
        for (int i = 0; i < labellist.size(); i++) {
            if(i == 0 || i == 3){
                labellist.get(i).setId("strong");
            }else
                labellist.get(i).setId("label-konto");
        }

        //Setzen Style Textfelder
        groesseTextField.setId("textfield-konto");
        gewichtTextField.setId("textfield-konto");
        alterTextField.setId("textfield-konto");
        geschlechtCombobox.setId("textfield-konto");
        speichernBtn.setId("textfield-konto");
        gridPane.setVgap(3);

        //Konfigurieren Gridpane
        gridPane.addRow(0, labellist.get(0));
        gridPane.addRow(1, labellist.get(1), labellist.get(8));
        gridPane.addRow(2, labellist.get(2), labellist.get(9));
        gridPane.addRow(3, new Label());

        gridPane.addRow(4, labellist.get(3));
        gridPane.addRow(5, labellist.get(4), groesseTextField);
        gridPane.addRow(6, labellist.get(5), gewichtTextField);
        gridPane.addRow(7, labellist.get(6), alterTextField);
        gridPane.addRow(8, labellist.get(7), geschlechtCombobox);
        gridPane.addRow(9, textfehler,speichernBtn);

        return gridPane;
    }

    private void setFehlermeldung(Text textfehler){
        /*
           textfehler.setLayoutX(50);
           textfehler.setLayoutY(225);
          */
        textfehler.setFill(Color.RED);
        textfehler.setText("Bitte gib deine vollständigen Daten an");
        textfehler.setVisible(true);
    }

    public boolean alleWerteEingetragen(){
        boolean b = false;
        if (koerperdaten.getGroesse() > 0 && koerperdaten.getGewicht() > 0 &&
                koerperdaten.getAlter() > 0 && koerperdaten.getGeschlecht() != null){
            b = true;
            System.out.println("alle daten richtig eingetragen");
        }
        return b;
    }
}