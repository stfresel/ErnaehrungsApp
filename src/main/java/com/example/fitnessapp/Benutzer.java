package com.example.fitnessapp;

import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
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
    private transient TextField textfieldRBenutzername = new TextField();
    private transient PasswordField[] passwordField = new PasswordField[2];
    private transient Text[] text = new Text[3];
    private transient Text textfehler = new Text();
    private transient Text txt = new Text();
    private transient TextField textfieldLBenutzer = new TextField();
    private transient PasswordField textfieldLPasswort = new PasswordField();
    private transient Button buttonLLogin =new Button();
    InputStream stream;
    InputStream iconstream;
    private transient Image background;
    private transient Image icon;
    private transient Rectangle backgroundrec = new Rectangle();


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

    public void initialize() {
        pane.setPrefSize(Main.stage.getScene().getWidth(), Main.stage.getScene().getHeight());
        
        //laden Hintergrund
        try {
            stream = new FileInputStream("src/main/resources/com/example/fitnessapp/login.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        background = new Image(stream);
        
        ImageView imageView = new ImageView();
        imageView.setImage(background);
        imageView.setX(0);
        imageView.setY(0);
        
        //Maximieren der Groesse des Hintergrunds 
        if(Main.stage.getWidth() >= Main.stage.getHeight()){
            imageView.setFitWidth(Main.stage.getWidth());
        }else
            imageView.setFitHeight(Main.stage.getHeight());
        imageView.setPreserveRatio(true);

        //Laden des Icons
        try {
            iconstream = new FileInputStream("src/main/resources/com/example/fitnessapp/logo.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        icon = new Image(iconstream);

        ImageView iconView = new ImageView();
        //Setting image to the image view
        iconView.setImage(icon);
        //Setting the image view parameters
        Main.switchScene(new Scene(pane));
        System.out.println(iconView.getImage().getWidth());
        iconView.setX(Main.stage.getWidth()/2 - Main.stage.getHeight()/12);
        iconView.setY(Main.stage.getHeight()/6.2);
        iconView.setFitHeight(Main.stage.getHeight()/6);
        iconView.setPreserveRatio(true);

        //Hinzufügen
        pane.getChildren().add(imageView);
        pane.getChildren().add(backgroundrec);
        pane.getChildren().add(iconView);
        pane.getChildren().add(textfieldLBenutzer);
        pane.getChildren().add(textfieldLPasswort);
        pane.getChildren().add(buttonLLogin);
        pane.getChildren().add(textfehler);
        pane.getChildren().add(txt);

        loginfun();
    }
    public void loginfun(){

        textfehler.setVisible(false);
        pane.requestFocus();
        textfieldLBenutzer.setVisible(true);
        textfieldLPasswort.setVisible(true);
        buttonLLogin.setVisible(true);
        txt.setVisible(true);

        //Textfeld
        textfieldLBenutzer.setPrefHeight(40);       // Size Textfeld Benutzername Loginfenster
        textfieldLBenutzer.setPrefWidth(200);
        textfieldLBenutzer.setLayoutY(179);
        textfieldLBenutzer.setLayoutX(159);
        textfieldLBenutzer.setPromptText("Benutzer");
        textfieldLBenutzer.setId("textfield-login");

        //Passwortfeld
        textfieldLPasswort.setPrefHeight(40);       // Size Textfeld Passwort Loginfenster
        textfieldLPasswort.setPrefWidth(200);
        textfieldLPasswort.setLayoutY(239);
        textfieldLPasswort.setLayoutX(159);
        textfieldLPasswort.setPromptText("Passwort");
        textfieldLPasswort.setId("textfield-login");

        //Button
        buttonLLogin.setText("Login");
        buttonLLogin.setPrefHeight(40);     // Size Button "Login" Loginfenster
        buttonLLogin.setPrefWidth(200);
        buttonLLogin.setLayoutX(Main.stage.getScene().getWidth() / 2 - buttonLLogin.getPrefWidth()/2);
        buttonLLogin.setLayoutY(300);
        //login.getStyleClass().set(0, "logreg");
        buttonLLogin.setId("button-login");

        //Rechteck Hintergrund
        backgroundrec.setFill(Paint.valueOf("#B6CC95"));
        backgroundrec.setWidth(pane.getWidth()/1.8);        // Size Hintergrund Rechteck
        backgroundrec.setHeight(pane.getHeight()/1.4);
        backgroundrec.setX(pane.getWidth()/2 - backgroundrec.getWidth()/2);
        backgroundrec.setY(pane.getHeight()/2 - backgroundrec.getHeight()/2);
        backgroundrec.setOpacity(0.8);

//--------------------------------------------------------------------------------------------------------\\
        buttonLLogin.setOnMouseClicked(new EventHandler<MouseEvent>() {
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

        //Text registieren (clickable text im Login Fenster)
        txt.setLayoutY((pane.getHeight()/2) + pane.getHeight()/4);
        txt.setLayoutX(pane.getWidth()/2 - 30);
        txt.setText("Registrieren");

        txt.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
        textfieldLBenutzer.setVisible(false);
        textfieldLPasswort.setVisible(false);
        buttonLLogin.setVisible(false);
        txt.setVisible(false);
        txt.setText("Login");
        txt.setLayoutX(295);
        txt.setLayoutY(375);
        txt.setVisible(true);

        //registrierungsbutton
        Button buttonReg = new Button();
        buttonReg.setText("Registrieren");
        System.out.println();
        buttonReg.setLayoutX(pane.getWidth() / 2 - 149.0/ 2);
        buttonReg.setLayoutY(400);
        buttonReg.setPrefHeight(40);        // Size Button "registrieren" Registrierungsfenster
        buttonReg.setPrefWidth(200);
        buttonReg.setId("button-login");



        pane.getChildren().add(buttonReg);

        //Textfeld für den Benutzername
        textfieldRBenutzername = new TextField();
        textfieldRBenutzername.setMinHeight(25);
        textfieldRBenutzername.setMinWidth(149);
        textfieldRBenutzername.setPrefHeight(40);       // Size Textfeld Benutzername Registrierungsfenster
        textfieldRBenutzername.setPrefWidth(200);
        textfieldRBenutzername.setId("textfield-login");
        double x = pane.getWidth() / 2 - textfieldRBenutzername.getMinWidth() / 2;
        double y = pane.getHeight() - counter;
        textfieldRBenutzername.setLayoutX(x);
        textfieldRBenutzername.setLayoutY(y);
        textfieldRBenutzername.setPromptText("Benutzername");

        //Textfeld für das Passwort
        counter -= 100;
        pane.getChildren().add(textfieldRBenutzername);
        for (int i = 0; i < 2; i++) {
            y = pane.getHeight() - counter;
            passwordField[i] = new PasswordField();
            passwordField[i].setLayoutX(x);
            passwordField[i].setId("textfield-login");
            passwordField[i].setPrefHeight(40);         // Size 2 Passwortfelder Registrierungsfenster
            passwordField[i].setPrefWidth(200);
            passwordField[i].setLayoutY(y);
            counter -= 100;
            if(i==0){
                passwordField[i].setPromptText("Passwort");
            }else{
                passwordField[i].setPromptText("Passwort wiederholen");
            }

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
            //text[0].setText("Benutzer");
            //text[1].setText("Neues Passwort");
            //text[2].setText("Wiederholen des Passwortes");
            pane.getChildren().add(text[i]);
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
        txt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                textfehler.setVisible(false);
                passwordField[0].setVisible(false);
                passwordField[1].setVisible(false);
                textfieldRBenutzername.setVisible(false);
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
     * @throws IOException Die Exception wird geworfen, falls es einen Fehler beim Lesen/Schreiben des Benutzer.txt files gibt.
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
        if (passwordField[0].getText().equals(passwordField[1].getText()) && textfieldRBenutzername.getText().length() != 0 && passwordField[0].getText().length()!=0 &&passwordField[1].getText().length()!=0) {
            loggedIn = true;
            System.out.println("Passwort richtig");
            System.out.println(textfieldRBenutzername.getText());
            System.out.println(passwordField[1].getText());
            benutzername = textfieldRBenutzername.getText();
            passwort = passwordField[1].getText();
            br.write(benutzername);
            br.write(" ");
            br.write(passwort);
            br.newLine();

        } else {
            textfehler.setVisible(true);
            if (textfieldRBenutzername.getText().length() == 0 && !Objects.equals(passwordField[0].getText(), passwordField[1].getText()) && (passwordField[0].getText().length()==0 || passwordField[1].getText().length()==0)) {
                textfehler.setText("Kein Benutzername und Passwort falsch");
            } else if (Objects.equals(passwordField[0].getText(), passwordField[1].getText()) && textfieldRBenutzername.getText().length()==0){
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
     * @throws IOException Die Exception wird geworfen, falls es einen Fehler beim Lesen/Schreiben des Benutzer.txt files gibt.
     */
    public void einloggen() throws IOException {
        pane.requestFocus();
        FileReader fr = new FileReader("Benutzer.txt");
        BufferedReader br = new BufferedReader(fr);
        textfehler.setVisible(false);
        benutzername = textfieldLBenutzer.getText();
        passwort = textfieldLPasswort.getText();
        boolean loggedIn = false;
        String zeile = br.readLine();
        do {
            if(zeile==null){
                textfehler.setVisible(true);
                textfehler.setFill(Color.RED);
                textfehler.setText("Kein Benutzer regristriert");
            }else{
                String[] benutzer = zeile.split(" ");
                if (Objects.equals(textfieldLBenutzer.getText(), benutzer[0]) && Objects.equals(textfieldLPasswort.getText(), benutzer[1])) {
                    textfehler.setText("");
                    System.out.println("Passwort und Benutzer stimmern überein");

                    benutzername = textfieldLBenutzer.getText();
                    passwort = textfieldLPasswort.getText();
                    loggedIn = true;

                } else {
                    pane.requestFocus();

                    textfieldLBenutzer.setText("");
                    textfieldLPasswort.setText("");
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