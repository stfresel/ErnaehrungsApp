package com.example.fitnessapp;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Ermöglicht es die Körperdaten anzuzeigen und zu verändern.
 */
public class Konto implements Serializable {
    /**
     * Beinhaltet alle Körperdaten einer Person.
     */
    private Koerperdaten koerperdaten;
    private transient GridPane gridPaneCalcPart;

    private transient Rectangle backrec = new Rectangle();

    /**
     * Laden die UI-Komponenten mit den Log-In-Daten, BMI, täglichen Nährwerte und den Körperdaten in die HBox.
     * @return Gibt die HBox mit den UI-Komponenten zurück
     */
    public Pane loadKonto() {
        HBox hBox = new HBox();
        Pane pane = new Pane(hBox);
        hBox.setMaxSize(Main.stage.getScene().getWidth()*0.8, Main.stage.getScene().getHeight());
        gridPaneCalcPart = new GridPane();
        calcPart(gridPaneCalcPart);
        hBox.getChildren().add(0,datenAnsicht());
        hBox.getChildren().add(1, gridPaneCalcPart);

        return pane;
    }

    /**
     * Die Methode passt den BMI und die täglichen Nährwerte laufen an die Änderungen der Körperdaten an.
     * @param gridPane Auf dieses Pane werden die Änderungen gespeichert.
     */
    public void calcPart(GridPane gridPane) {
        koerperdaten.tagesUmsatzBerechnen();
        gridPane.setPrefSize(Main.stage.getWidth()/2, Main.stage.getHeight()/2);
        gridPane.getChildren().clear();
        //gridPane.getChildren().removeAll();
        Label bmiLabel = new Label("\t BMI: " + koerperdaten.getBMI());
        Label tagbedarfLabel = new Label("       Täglicher Bedarf:");
        Label kalorienLabel = new Label("\t Kalorien       : " + koerperdaten.getTagesUmsatz().getKcal());
        Label kohlenhydrateLabel = new Label("\t Kohlenhydrate  : " + koerperdaten.getTagesUmsatz().getKohlenhydrate());
        Label proteinLabel = new Label("\t Proteine       : " + koerperdaten.getTagesUmsatz().getProtein());
        Label fetteLabel = new Label("\t Fette          : " + koerperdaten.getTagesUmsatz().getFette());

        bmiLabel.setId("text");
        tagbedarfLabel.setId("strong");
        kalorienLabel.setId("text");
        kohlenhydrateLabel.setId("text");
        proteinLabel.setId("text");
        fetteLabel.setId("text");

        gridPane.setAlignment(Pos.TOP_CENTER);

        gridPane.addRow(0, bmiLabel);
        gridPane.addRow(1,tagbedarfLabel);
        gridPane.addRow(2,kalorienLabel);
        gridPane.addRow(3,kohlenhydrateLabel);
        gridPane.addRow(4,proteinLabel);
        gridPane.addRow(5,fetteLabel);
    }

    /**
     * Ladet die Körperdaten und die Inputs in ein GridPane.
     * @param ifbackground optionaler Übergabeparameter, der die Farbe des Hintergrundes bestimmt
     * @return Gibt das GridPane mit den UI-Komponenten zurück
     */
    public GridPane datenAnsicht(boolean... ifbackground) {
        GridPane gridPane = new GridPane();
        Button speichernBtn = new Button("speichern");
        gridPane.setPrefSize(Main.stage.getScene().getWidth()/2, Main.stage.getScene().getHeight());
        //NumericTextField alterTextField = new NumericTextField();
        DatePicker alterDatePicker = new DatePicker();
        NumericTextField groesseTextField = new NumericTextField();
        NumericTextField gewichtTextField = new NumericTextField();
        ComboBox<String> geschlechtCombobox = new ComboBox<>();
        Text textfehler = new Text("Bitte gib die Daten vollständig und korrekt an");
        textfehler.setVisible(false);
        geschlechtCombobox.getItems().addAll("weiblich", "männlich");


        // Größe vom Konto, beim Öffnen vom Konto Tab
        gridPane.setPrefSize(Main.stage.getScene().getWidth()/1.5, Main.stage.getScene().getHeight()/2);

        if (ifbackground.length > 0) {
            Image background = new Image(Objects.requireNonNull(getClass().getResourceAsStream("backgroundGreen.png")));

            gridPane.setBackground(new Background(new BackgroundImage(background, null, null, null, new BackgroundSize(100, 100, true, true, false, true))));
            // Größe vom Konto gleich nach dem registrieren
            gridPane.setPrefSize(Main.stage.getScene().getWidth(), Main.stage.getScene().getHeight());
        }

        // Wenn es direkt nach dem Registrieren geöffnet wird
        if (koerperdaten == null){
            speichernBtn.setVisible(true);
            koerperdaten = new Koerperdaten();
            // nur speichern
            speichernBtn.setOnMouseClicked(new EventHandler<>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (geschlechtCombobox.getSelectionModel().getSelectedItem()==null ||
                            alterDatePicker.getValue() == null ||
                            alterDatePicker.getValue().isAfter(LocalDate.now())
                            || groesseTextField.getText().length() < 1 || gewichtTextField.getText().length() < 1){
                        setFehlermeldung(textfehler);

                    }else {
                        koerperdaten.setKoerperdaten(groesseTextField.getDouble(), gewichtTextField.getDouble(),
                                alterDatePicker.getValue(), geschlechtCombobox.getValue());
                        //Main.benutzer.getHome().startHome();
                        koerperdaten.tagesUmsatzBerechnen();
                        Controller.benutzer.getHome().startHome();
                        //############

                    }

                }
            });
        } else {
            groesseTextField.setText(String.valueOf(koerperdaten.getGroesse()));
            gewichtTextField.setText(String.valueOf(koerperdaten.getGewicht()));
            //alterTextField.setText(String.valueOf(koerperdaten.getAlter()));
            alterDatePicker.setValue(koerperdaten.getBirthday());
            geschlechtCombobox.setValue(koerperdaten.getGeschlecht());
            //wenn man etwas verändert will
            speichernBtn.setOnMouseClicked(new EventHandler<>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (geschlechtCombobox.getSelectionModel().getSelectedItem() == null ||
                            alterDatePicker.getValue() == null ||
                            alterDatePicker.getValue().isAfter(LocalDate.now())
                            || groesseTextField.getText().length() < 1 || gewichtTextField.getText().length() < 1){
                        setFehlermeldung(textfehler);
                    }
                    koerperdaten.setKoerperdaten(groesseTextField.getDouble(), gewichtTextField.getDouble(),
                            alterDatePicker.getValue(), geschlechtCombobox.getValue());
                    calcPart(gridPaneCalcPart);
                    koerperdaten.tagesUmsatzBerechnen();
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
        labellist.add(new Label("Geburtsdatum: "));
        labellist.add(new Label("Geschlecht: "));

        labellist.add(new Label(Controller.benutzer.getBenutzername()));
        //############2
        labellist.add(new Label(Controller.benutzer.getPasswort()));



        //Setzen Style Labels
        for (int i = 0; i < labellist.size(); i++) {
            if(i == 0 || i == 3){
                labellist.get(i).setId("strong");
            }else
                labellist.get(i).setId("text");
        }

        //Setzen Style Textfelder
        groesseTextField.setId("textfield-konto");
        gewichtTextField.setId("textfield-konto");
        //alterTextField.setId("textfield-konto");
        alterDatePicker.setId("textfield-konto");
        geschlechtCombobox.setId("textfield-konto");
        speichernBtn.setId("textfield-konto");
        gridPane.setVgap(3);

        //Konfigurieren Gridpane
        //gridPane.addRow(0, labellist.get(0));
        GridPane.setColumnSpan(labellist.get(0), 2);
        GridPane.setColumnSpan(labellist.get(3), 2);
        gridPane.addRow(0,labellist.get(0));
        gridPane.addRow(1, labellist.get(1), labellist.get(8));
        gridPane.addRow(2, labellist.get(2), labellist.get(9));
        gridPane.addRow(3, new Label(""));

        gridPane.addRow(4, labellist.get(3));
        gridPane.addRow(5, labellist.get(4), groesseTextField);
        gridPane.addRow(6, labellist.get(5), gewichtTextField);
        gridPane.addRow(7, labellist.get(6), alterDatePicker);
        gridPane.addRow(8, labellist.get(7), geschlechtCombobox);
        gridPane.addRow(9, speichernBtn);
        gridPane.addRow(10, textfehler);

        //gridPane.setPrefSize(Main.stage.getScene().getWidth()/1.5, Main.stage.getScene().getHeight()/2);

        return gridPane;
    }

    /**
     * Setzt eine Fehlermeldung
     * @param text Gibt die UI-Komponente an, welche verändert werden soll.
     */
    private void setFehlermeldung(Text text){
        text.setFill(Color.RED);
        text.setVisible(true);
    }

    /**
     * Gibt den Tagesumsatz der Person zurück
     * @return Tagesumsatz der Nährwerte
     */
    public Naehrwerte getNaehrwerteTagesUmsatz() {
        return koerperdaten.getTagesUmsatz();
    }
}