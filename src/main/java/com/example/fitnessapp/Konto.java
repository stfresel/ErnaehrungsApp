package com.example.fitnessapp;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.Serializable;
import java.util.Objects;

public class Konto implements Serializable {
    private Koerperdaten meineKoerperdaten;

    public HBox loadKonto() {
        HBox hBox = new HBox();
        hBox.getChildren().addAll(datenAnsicht(), calcPart());
        return hBox;
    }
    public GridPane calcPart() {
        GridPane gridPane = new GridPane();
        meineKoerperdaten.tagesUmsatzBerechnen();
        gridPane.addRow(0, new Label("BMI: " + meineKoerperdaten.getBMI()));
        gridPane.addRow(1,new Label("täglicher Bedarf"));
        gridPane.addRow(2,new Label("Kalorien       : " + meineKoerperdaten.getTagesUmsatz().getKcal()));
        gridPane.addRow(3,new Label("Kohlenhydrate  : " + meineKoerperdaten.getTagesUmsatz().getKohlenhydrate()));
        gridPane.addRow(4,new Label("Proteine       : " + meineKoerperdaten.getTagesUmsatz().getProtein()));
        gridPane.addRow(5,new Label("Fette          : " + meineKoerperdaten.getTagesUmsatz().getFett()));



        return gridPane;
    }

    public GridPane datenAnsicht() {
        Button speichernBtn = new Button("speichern");
        NumericTextField alterTextField = new NumericTextField();
        NumericTextField groesseTextField = new NumericTextField();
        NumericTextField gewichtTextField = new NumericTextField();
        ComboBox<String> geschlechtCombobox = new ComboBox<>();
        Text textfehler=new Text();
        geschlechtCombobox.getItems().addAll("weiblich", "männlich");
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(Main.stage.getScene().getWidth(), Main.stage.getScene().getHeight());
        gridPane.addRow(9,new Label(),textfehler);
        // Wenn es direkt nach dem Registrieren geöffnet wird
        if (meineKoerperdaten == null){
            meineKoerperdaten = new Koerperdaten();

            // nur speichern
            speichernBtn.setOnMouseClicked(new EventHandler<>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if ( Objects.equals(groesseTextField.getText(), "") ||Objects.equals(gewichtTextField.getText(), "") || Objects.equals(groesseTextField.getText(), "") ||alterTextField.getText() == null||gewichtTextField.getText()==null||alterTextField.getText()==null||gewichtTextField.getText()==null|| geschlechtCombobox.getValue()==null){
                        textfehler.setLayoutX(50);
                        textfehler.setLayoutY(225);
                        textfehler.setFill(Color.RED);
                        textfehler.setText("Zu wenig Daten eingegeben");
                        textfehler.setVisible(true);

                    }else {
                        meineKoerperdaten.setGroesse(groesseTextField.getDouble());
                        meineKoerperdaten.setGewicht(gewichtTextField.getDouble());
                        meineKoerperdaten.setAlter(alterTextField.getInt());
                        System.out.println("Combobox Geschlecht id: " + geschlechtCombobox.getValue());
                        meineKoerperdaten.setGeschlecht(geschlechtCombobox.getValue());
                        Main.benutzer.getHome().startHome();
                    }


                }
            });
        } else {
            groesseTextField.setText(String.valueOf(meineKoerperdaten.getGroesse()));
            gewichtTextField.setText(String.valueOf(meineKoerperdaten.getGewicht()));
            alterTextField.setText(String.valueOf(meineKoerperdaten.getAlter()));
            geschlechtCombobox.setValue(meineKoerperdaten.getGeschlecht());

            //wenn man etwas verändert wird
            speichernBtn.setOnMouseClicked(new EventHandler<>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    meineKoerperdaten.setGroesse(groesseTextField.getDouble());
                    meineKoerperdaten.setGewicht(gewichtTextField.getDouble());
                    meineKoerperdaten.setAlter(alterTextField.getInt());
                    meineKoerperdaten.setGeschlecht(geschlechtCombobox.getValue());
                }
            });

        }

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
