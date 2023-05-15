package com.example.fitnessapp;

import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Objects;

public class Benutzer implements Serializable{
    public transient Pane pane=new Pane();
    private transient TextField txt = new TextField();
    private transient PasswordField[] passwordField = new PasswordField[2];
    private transient Text[] text = new Text[3];
    private transient Text textfehler=new Text();
    private transient Text textregilogi=new Text();
    private transient TextField textfieldbenutzer=new TextField();
    private transient PasswordField passwortfieldbenutzer=new PasswordField();
    private transient Button login=new Button();

    //--------------------------------------------
    //Atribute für Benutzer und Passwort
    private String benutzername;
    private String passwort;
    private Home home;
    private Path path = null;
    //------------------------------
    
    /**
     * Die Methode laden die Anmeldungs-Scene, mit den ganzen Komponenten.
     */

    public void initialize(){
        pane.setPrefSize(Main.stage.getScene().getWidth(), Main.stage.getScene().getHeight());
        //Main.stage.setScene(new Scene(pane));
        Main.switchScene(new Scene(pane));
        //Hinzufügen
        pane.getChildren().add(textfieldbenutzer);
        pane.getChildren().add(passwortfieldbenutzer);
        pane.getChildren().add(login);
        pane.getChildren().add(textfehler);
        pane.getChildren().add(textregilogi);
        loginfun();
    }
    public void loginfun(){

        textfehler.setVisible(false);
        pane.requestFocus();
        textfieldbenutzer.setVisible(true);
        passwortfieldbenutzer.setVisible(true);
        login.setVisible(true);
        textregilogi.setVisible(true);

        //Textfeld
        textfieldbenutzer.setLayoutY(109);
        textfieldbenutzer.setLayoutX(159);
        textfieldbenutzer.setPromptText("Benutzer");
        textfieldbenutzer.setId("textfield-login");

        //Passwortfeld
        passwortfieldbenutzer.setLayoutY(169);
        passwortfieldbenutzer.setLayoutX(159);
        passwortfieldbenutzer.setPromptText("Passwort");
        passwortfieldbenutzer.setId("textfield-login");

        //Button
        login.setText("Login");
        login.setPrefHeight(40);
        login.setPrefWidth(200);
        login.setLayoutX(Main.stage.getScene().getWidth() / 2 - login.getPrefWidth()/2);
        login.setLayoutY(300);
        //login.getStyleClass().set(0, "logreg");
        login.setId("button-login");


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
        //Text registieren
        textregilogi.setLayoutY(210);
        textregilogi.setLayoutX(250);
        textregilogi.setText("Registrieren");
        textregilogi.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                neuesKonto();
            }
        });

    }

    /**
     * Die Methode ladet die Registrierungsszene. Sie wird immer von initialize() aufgerufen.
     */
    public void neuesKonto() {
        home = new Home();
        //meinTagebuch.start();
        textfehler.setVisible(false);
        int counter = 400;
        pane.requestFocus();
        textfieldbenutzer.setVisible(false);
        passwortfieldbenutzer.setVisible(false);
        login.setVisible(false);
        textregilogi.setVisible(false);
        textregilogi.setText("Login");
        textregilogi.setLayoutX(295);
        textregilogi.setLayoutY(375);
        textregilogi.setVisible(true);
        Button buttonReg = new Button();
        buttonReg.setText("Registrieren");
        System.out.println();
        buttonReg.setLayoutX(pane.getWidth() / 2 - 149.0/ 2);
        buttonReg.setLayoutY(400);
        pane.getChildren().add(buttonReg);

        txt = new TextField();
        txt.setMinHeight(25);
        txt.setMinWidth(149);
        double x = pane.getWidth() / 2 - txt.getMinWidth() / 2;
        txt.setLayoutX(x);
        double y = pane.getHeight() - counter;
        txt.setLayoutY(y);
        txt.setPromptText("Neuen Benutzer");
        counter -= 100;
        pane.getChildren().add(txt);
        for (int i = 0; i < 2; i++) {
            y = pane.getHeight() - counter;
            passwordField[i] = new PasswordField();
            passwordField[i].setLayoutX(x);
            passwordField[i].setLayoutY(y);
            counter -= 100;
            passwordField[i].setPromptText("Neues Passwort");

            pane.getChildren().add(passwordField[i]);

        }
        counter = 400;
        for (int i = 0; i < 3; i++) {
            y = pane.getHeight() - counter;
            text[i] = new Text();
            text[i].setLayoutX(x);
            text[i].setLayoutY(y - 1);
            counter -= 100;
        }


        for (int i = 0; i < 3; i++) {
            text[0].setText("Benutzer");
            text[1].setText("Neues Passwort");
            text[2].setText("Wiederholen des Passwortes");
            pane.getChildren().add(text[i]);
        }
        buttonReg.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    speichern();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        textregilogi.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                textfehler.setVisible(false);
                passwordField[0].setVisible(false);
                passwordField[1].setVisible(false);
                txt.setVisible(false);
                buttonReg.setVisible(false);
                for (int i = 0; i < 3; i++) {
                    text[i].setVisible(false);
                }
                loginfun();
            }
        });
    }

    /**
     * Nach dem Registrieren wird der BEnutzer sowohl im Benutzer.txt File als auch in einem neuen Seialisierunsfile gespeichert.
     * Name des Serialisierungsfile ist: Benutzername + Passwort + .ser
     * @throws IOException Die Exception wird geworfen, falls es einen Fehler beim Lesen/Schreiben vom Benutzer.txt File gibt.
     */
    private void speichern() throws IOException {
        boolean loggedIn = false;
        // Speichern im File, wo alle Benutzernamen und Passwörter stehen
        FileWriter fr = null;
        fr = new FileWriter("Benutzer.txt");

        BufferedWriter br = new BufferedWriter(fr);
        textfehler.setFill(Color.RED);
        textfehler.setLayoutY(350);
        textfehler.setLayoutX(pane.getWidth() / 2 - 149.0/ 2);
        if (passwordField[0].getText().equals(passwordField[1].getText()) && txt.getText().length() != 0 && passwordField[0].getText().length()!=0 &&passwordField[1].getText().length()!=0) {
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
            textfehler.setVisible(true);
            if (txt.getText().length() == 0 && !Objects.equals(passwordField[0].getText(), passwordField[1].getText()) && (passwordField[0].getText().length()==0 || passwordField[1].getText().length()==0)) {
                textfehler.setText("Kein Benutzername und Passwort falsch");
            } else if (Objects.equals(passwordField[0].getText(), passwordField[1].getText()) && txt.getText().length()==0){
                textfehler.setText("Kein Benutzername");
            }else {
                textfehler.setText("Passwörter stimmen nicht überein");
            }
        }
        br.close();
        if (loggedIn){
            // Erstellen des Paths zum .ser File
            datenSpeichern();
            //--------------
            home.getTagebuch().addTag(new Tag(LocalDate.of(2023,5,1)));
            home.getTagebuch().addTag(new Tag(LocalDate.of(2023, 5, 2)));

            //----------------


            //Main.stage.setScene(new Scene(home.getKonto().datenAnsicht()));
            Main.switchScene(new Scene(home.getKonto().datenAnsicht()));
            //home.startHome();
        }
    }

    /**
     * Die Methode speichert die Daten (Home) im .ser File.
     */
    public void datenSpeichern(){
        path = Paths.get(benutzername + passwort + ".ser");
        // Erstellen eines .ser Files wo das Tagebuch gespeichert wird
        try (ObjectOutputStream whereToWrite = new ObjectOutputStream(Files.newOutputStream(path , StandardOpenOption.CREATE))) {
            whereToWrite.writeObject(home);
            System.out.println("Saved Home");
        } catch (IOException e) {
            System.out.println("Can't serialize " + path.getFileName() + ": " + e.getMessage());
        }
    }


    /**
     * Die Methode wird aufgerufen, wenn von initialize() aufgerufen, wenn man sich anmeldet.
     * @throws IOException Die Exception wird geworfen, falls es einen Fehler beim Lesen/Schreiben vom Benutzer.txt File gibt.
     */
    public void einloggen() throws IOException {
        pane.requestFocus();
        FileReader fr = new FileReader("Benutzer.txt");
        BufferedReader br = new BufferedReader(fr);
        textfehler.setVisible(false);
        benutzername = textfieldbenutzer.getText();
        passwort = passwortfieldbenutzer.getText();
        boolean loggedIn = false;
        String zeile = br.readLine();
        do {
            if(zeile==null){
                textfehler.setVisible(true);
                textfehler.setFill(Color.RED);
                textfehler.setText("Kein Benutzer regristriert");
            }else{
                String[] benutzer = zeile.split(" ");
                if (Objects.equals(textfieldbenutzer.getText(), benutzer[0]) && Objects.equals(passwortfieldbenutzer.getText(), benutzer[1])) {
                    textfehler.setText("");
                    System.out.println("Passwort und Benutzer stimmern überein");

                    benutzername = textfieldbenutzer.getText();
                    passwort = passwortfieldbenutzer.getText();
                    loggedIn = true;

                } else {
                    pane.requestFocus();

                    textfieldbenutzer.setText("");
                    passwortfieldbenutzer.setText("");
                    textfehler.setFill(Color.RED);
                    textfehler.setText("Passwort oder Benutzername falsch!");

                }
                zeile = br.readLine();
            }


        } while (zeile != null);
        br.close();

        if (loggedIn){
            // path wieder erstellen evt funktion?
            path = Paths.get(benutzername + passwort + ".ser");
            try (ObjectInputStream whereToReadFrom = new ObjectInputStream(Files.newInputStream(path))) {
                home = (Home) whereToReadFrom.readObject();
                System.out.println("auslesen vom file");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Fehler beim Auslesen aus dem Tagebuch" + e.getMessage());
            }
            //homeStarten();
            home.startHome();
        }
    }
    //------------------getter und setter--------------------------

    public Home getHome() {
        return home;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public String getPasswort() {
        return passwort;
    }

}