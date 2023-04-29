package com.example.fitnessapp;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

import static javafx.scene.text.FontSmoothingType.LCD;

public class Benutzer {
    public Group group=new Group();
    //JavaF
    public Rectangle table=new Rectangle(Main.pane.getPrefWidth(),Main.pane.getPrefHeight());
    Button buttonReg=new Button();
    TextField txt = new TextField();
    PasswordField[] passwordField = new PasswordField[2];
    Text[] text = new Text[3];
    Text textfehler=new Text();
    Text textregri=new Text();
    TextField textfieldbenutzer=new TextField();
    PasswordField passwortfieldbenutzer=new PasswordField();
    Button login=new Button();

    //--------------------------------------------
    //Atribute für Benutzer und Passwort
    private String benutzername;
    private String passwort;
    private String[] benutzer = new String[2];
    private String zeile;
    //------------------------------


    public Benutzer() throws FileNotFoundException {
    }
    public void initialize(){
        Main.stage.setScene(new Scene(group,Main.pane.getPrefWidth(),Main.pane.getPrefHeight()));

        //Textfeld
        textfieldbenutzer.setLayoutY(109);
        textfieldbenutzer.setLayoutX(159);
        textfieldbenutzer.setPromptText("Benutzer");
        //Passwortfeld
        passwortfieldbenutzer.setLayoutY(169);
        passwortfieldbenutzer.setLayoutX(159);
        passwortfieldbenutzer.setPromptText("Passwort");

        //Button
        login.setText("Login");
        login.setLayoutY(237);
        login.setLayoutX(159);
        login.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    einloggen();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        //Text log in
        textfehler.setLayoutX(159);
        textfehler.setLayoutY(225);
        //Text regristieren
        textregri.setLayoutY(210);
        textregri.setLayoutX(250);
        textregri.setText("Regristrieren");
        textregri.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                neuesKonto();
            }
        });
        //Hinzufügen
        group.getChildren().add(textfieldbenutzer);
        group.getChildren().add(passwortfieldbenutzer);
        group.getChildren().add(login);
        group.getChildren().add(textfehler);
        group.getChildren().add(textregri);
    }

    public void neuesKonto() {
        textfehler.setVisible(false);
        int counter = 400;
        group.requestFocus();
        textfieldbenutzer.setVisible(false);
        passwortfieldbenutzer.setVisible(false);
        login.setVisible(false);
        textregri.setVisible(false);
        buttonReg = new Button();
        buttonReg.setText("Regristrieren");
        System.out.println();
        buttonReg.setLayoutX(table.getWidth() / 2 - 149 / 2);
        buttonReg.setLayoutY(400);
        group.getChildren().add(buttonReg);

        txt = new TextField();
        txt.setMinHeight(25);
        txt.setMinWidth(149);
        double x = table.getWidth() / 2 - txt.getMinWidth() / 2;
        txt.setLayoutX(x);
        double y = table.getHeight() - counter;
        txt.setLayoutY(y);
        txt.setPromptText("Neuen Benutzer");
        counter -= 100;
        group.getChildren().add(txt);
        for (int i = 0; i < 2; i++) {
            y = table.getHeight() - counter;
            passwordField[i] = new PasswordField();
            passwordField[i].setLayoutX(x);
            passwordField[i].setLayoutY(y);
            counter -= 100;
            passwordField[i].setPromptText("Neues Passwort");


            group.getChildren().add(passwordField[i]);


        }
        counter = 400;
        for (int i = 0; i < 3; i++) {
            y = table.getHeight() - counter;
            text[i] = new Text();
            text[i].setLayoutX(x);
            text[i].setLayoutY(y - 1);
            counter -= 100;
        }


        for (int i = 0; i < 3; i++) {

            text[0].setText("Benutzer");
            text[1].setText("Neues Passwort");
            text[2].setText("Wiederholen des Passwotes");
            group.getChildren().add(text[i]);
        }
        buttonReg.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    speichern();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }

    private void speichern() throws IOException {
        FileWriter fr = new FileWriter("Benutzer.txt");
        BufferedWriter br = new BufferedWriter(fr);
        textfehler.setFill(Color.RED);
        textfehler.setLayoutY(350);
        textfehler.setLayoutX(table.getWidth() / 2 - 149 / 2);
        if (passwordField[0].getText().equals(passwordField[1].getText()) && txt.getText().length() != 0) {
            System.out.println("Passwort richtig");
            System.out.println(txt.getText());
            System.out.println(passwordField[1].getText());
            br.write(txt.getText());
            br.write(" ");
            br.write(passwordField[1].getText());
            br.newLine();

        } else {
            if (txt.getText().length() == 0 && !Objects.equals(passwordField[0].getText(), passwordField[1].getText())) {
                textfehler.setText("Kein Benutzername und Passwort falsch");
            } else {
                textfehler.setText("Passwörter stimmen nicht überein");
            }
        }
        br.close();

    }

    public void einloggen() throws IOException {
        group.requestFocus();
        FileReader fr = new FileReader("Benutzer.txt");
        BufferedReader br = new BufferedReader(fr);


        benutzername = textfieldbenutzer.getText();
        passwort = passwortfieldbenutzer.getText();
        //System.out.println(textfieldbenutzer.getText());
        //System.out.println(passwortfieldbenutzer.getText());
        zeile = br.readLine();
        do {

            benutzer = zeile.split(" ");
            if (Objects.equals(textfieldbenutzer.getText(), benutzer[0]) && Objects.equals(passwortfieldbenutzer.getText(), benutzer[1])) {
                textfehler.setText("");
                System.out.println("Passwort und Benutzer stimmern überein");
                System.exit(0);

            } else {
                group.requestFocus();

                textfieldbenutzer.setText("");
                passwortfieldbenutzer.setText("");
                textfehler.setFill(Color.RED);
                textfehler.setText("Passwort oder Benutzername falsch!");
                //textField.setText("Passwort oder Benutzername falsch");

            }
            zeile = br.readLine();

        } while (zeile != null);
        br.close();


    }

}