package com.example.fitnessapp;

import javafx.event.EventHandler;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Benutzer implements Serializable{
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

    private Tagebuch meinTagebuch;

    private Path path = null;
    //------------------------------


    public Benutzer() {
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
        meinTagebuch = new Tagebuch();
        //meinTagebuch.start();
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
        boolean loggedIn = false;
        // Speichern im File, wo alle Benutzernamen und Passwörter stehen
        FileWriter fr = new FileWriter("Benutzer.txt");
        BufferedWriter br = new BufferedWriter(fr);
        textfehler.setFill(Color.RED);
        textfehler.setLayoutY(350);
        textfehler.setLayoutX(table.getWidth() / 2 - 149 / 2);
        if (passwordField[0].getText().equals(passwordField[1].getText()) && txt.getText().length() != 0) {
            loggedIn = true;
            System.out.println("Passwort richtig");
            System.out.println(txt.getText());
            System.out.println(passwordField[1].getText());
            benutzername = txt.getText();
            passwort = passwordField[1].getText();
            br.write(benutzername);
            br.write(" ");
            br.write(passwort);
            br.newLine();

        } else {
            if (txt.getText().length() == 0 && !Objects.equals(passwordField[0].getText(), passwordField[1].getText())) {
                textfehler.setText("Kein Benutzername und Passwort falsch");
            } else {
                textfehler.setText("Passwörter stimmen nicht überein");
            }
        }
        br.close();
        if (loggedIn){
            tagebuchSpeichern();
        }


        tagebuchStarten();
    }

    private void tagebuchSpeichern(){
        // Erstellen des Paths zum .ser File
        path = Paths.get(benutzername + passwort + ".ser");
        // Erstellen eines .ser Files wo das Tagebuch gespeichert wird
        try (ObjectOutputStream whereToWrite = new ObjectOutputStream(Files.newOutputStream(path , StandardOpenOption.CREATE))) {
            whereToWrite.writeObject(meinTagebuch);
            System.out.println("Saved Tagebuch");
        } catch (IOException e) {
            System.out.println("Can't serialize " + path.getFileName() + ": " + e.getMessage());
        }
    }

    public void einloggen() throws IOException {
        group.requestFocus();
        FileReader fr = new FileReader("Benutzer.txt");
        BufferedReader br = new BufferedReader(fr);


        benutzername = textfieldbenutzer.getText();
        passwort = passwortfieldbenutzer.getText();
        //System.out.println(textfieldbenutzer.getText());
        //System.out.println(passwortfieldbenutzer.getText());
        boolean loggedIn = false;
        zeile = br.readLine();
        do {

            benutzer = zeile.split(" ");
            if (Objects.equals(textfieldbenutzer.getText(), benutzer[0]) && Objects.equals(passwortfieldbenutzer.getText(), benutzer[1])) {
                textfehler.setText("");
                System.out.println("Passwort und Benutzer stimmern überein");

                benutzername = textfieldbenutzer.getText();
                passwort = passwortfieldbenutzer.getText();
                loggedIn = true;

            } else {
                group.requestFocus();

                textfieldbenutzer.setText("");
                passwortfieldbenutzer.setText("");
                textfehler.setFill(Color.RED);
                textfehler.setText("Passwort oder Benutzername falsch!");

            }
            zeile = br.readLine();

        } while (zeile != null);
        br.close();

        if (loggedIn){
            // path wieder erstellen evt funktion?
            path = Paths.get(benutzername + passwort + ".ser");
            try (ObjectInputStream whereToReadFrom = new ObjectInputStream(Files.newInputStream(path))) {
                meinTagebuch = (Tagebuch) whereToReadFrom.readObject();
                System.out.println("auslesen vom file");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Fehler beim Auslesen aus dem Tagebuch" + e.getMessage());
            }
            tagebuchStarten();
        }
    }

    private void tagebuchStarten() {
        if (meinTagebuch.getAnzahlTage() < 1){
            Tag t1 = new Tag(LocalDate.now());
            meinTagebuch.addTag(t1);
        } else if (!Objects.equals(meinTagebuch.getLastDay(), LocalDate.now())){
            Tag t1 = new Tag(LocalDate.now());
            meinTagebuch.addTag(t1);
        }
        meinTagebuch.loadTagebuchScene();
        EventHandler<KeyEvent> keyEventEventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.S && !(keyEvent.getTarget() instanceof TextInputControl)) {
                    tagebuchSpeichern();
                    System.out.println("gespeichert");
                }
            }
        };
        Main.stage.addEventFilter(KeyEvent.ANY, keyEventEventHandler);
    }

}