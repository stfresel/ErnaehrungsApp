package com.example.fitnessapp;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.Serializable;

public class Konto implements Serializable {
    private Koerperdaten meineKoerperdaten;

    public GridPane loadKonto() {

        //Pane pane = new Pane();
        //pane.getChildren().add(new Label("keine Kontoansicht vorhanden"));
        //return pane;
        return datenAnsicht();
    }

    public GridPane datenAnsicht() {
        Button speichernBtn = new Button("speichern");
        NumericTextField alterTextField = new NumericTextField();
        NumericTextField groesseTextField = new NumericTextField();
        NumericTextField gewichtTextField = new NumericTextField();
        ComboBox<String> geschlechtCombobox = new ComboBox<>();
        geschlechtCombobox.getItems().addAll("weiblich", "männlich");

        // wenns s erste mol aufgerufn werd
        if (meineKoerperdaten == null){
            meineKoerperdaten = new Koerperdaten();
            // nur speichern
            speichernBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    meineKoerperdaten.setGroesse(groesseTextField.getDouble());
                    meineKoerperdaten.setGewicht(gewichtTextField.getDouble());
                    meineKoerperdaten.setAlter(alterTextField.getInt());
                    System.out.println("Combobox Geschlecht id: " + geschlechtCombobox.getValue());
                    meineKoerperdaten.setGeschlecht(geschlechtCombobox.getValue());

                    Main.benutzer.getHome().startHome();
                }
            });
        } else {
            groesseTextField.setText(String.valueOf(meineKoerperdaten.getGroesse()));
            gewichtTextField.setText(String.valueOf(meineKoerperdaten.getGewicht()));
            alterTextField.setText(String.valueOf(meineKoerperdaten.getAlter()));
            geschlechtCombobox.setValue(meineKoerperdaten.getGeschlecht());
            // --------------------------------------------------------combo box auf einstellung setzten

            //wenn man epas ändert
            speichernBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    meineKoerperdaten.setGroesse(groesseTextField.getDouble());
                    meineKoerperdaten.setGewicht(gewichtTextField.getDouble());
                    meineKoerperdaten.setAlter(alterTextField.getInt());
                    meineKoerperdaten.setGeschlecht(geschlechtCombobox.getValue());
                }
            });

        }

        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(Main.stage.getWidth(), Main.stage.getHeight());

        gridPane.addRow(0, new Label("Informationen zum Profil"));
        gridPane.addRow(1, new Label("Benutzername: " + Main.benutzer.getBenutzername()));
        gridPane.addRow(2, new Label("Passwort: " + Main.benutzer.getPasswort()));

        gridPane.addRow(3, new Label("Körperdaten"));
        gridPane.addRow(4, new Label("Größe (in m): "), groesseTextField);
        gridPane.addRow(5, new Label("Gewicht (in kg): "), gewichtTextField);
        gridPane.addRow(6, new Label("Alter (in Jahren): "), alterTextField);
        gridPane.addRow(7, new Label("Geschlecht: "), geschlechtCombobox);




        gridPane.addRow(8, speichernBtn);

        return  gridPane;
    }


}
