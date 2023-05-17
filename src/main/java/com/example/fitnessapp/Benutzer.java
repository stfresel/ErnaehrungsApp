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
    private transient Button buttonReg = new Button();
    private final transient PasswordField[] passwordField = new PasswordField[2];
    private final transient Text textfehler = new Text();
    private final transient Text txt = new Text();
    private final transient TextField textfieldLBenutzer = new TextField();
    private final transient PasswordField textfieldLPasswort = new PasswordField();
    private final transient Button buttonLLogin =new Button();
    private ImageView imageView;
    private ImageView iconView;
    private final transient Rectangle backgroundrec = new Rectangle();


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
        InputStream stream;
        try {
            stream = new FileInputStream("src/main/resources/com/example/fitnessapp/login.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image background = new Image(stream);
        imageView = new ImageView();
        imageView.setImage(background);
        imageView.setX(0);
        imageView.setY(0);
        
        //Maximieren der Groesse des Hintergrunds 
        adjustBackgroundSize();
        imageView.setPreserveRatio(true);

        //Laden des Icons
        InputStream iconstream;
        try {
            iconstream = new FileInputStream("src/main/resources/com/example/fitnessapp/logo.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image icon = new Image(iconstream);
        iconView = new ImageView();
        iconView.setImage(icon);
        Main.switchScene(new Scene(pane));
        iconView.setX(Main.stage.getWidth()/2 - Main.stage.getWidth()/12);
        iconView.setY(Main.stage.getHeight()/6.2);
        iconView.setFitHeight(88);
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
        textfieldLBenutzer.setPromptText("Benutzer");
        textfieldLBenutzer.setId("textfield-login");

        //Passwortfeld
        textfieldLPasswort.setPrefHeight(40);       // Size Textfeld Passwort Loginfenster
        textfieldLPasswort.setPrefWidth(200);
        textfieldLPasswort.setPromptText("Passwort");
        textfieldLPasswort.setId("textfield-login");

        //Button
        buttonLLogin.setText("Login");
        buttonLLogin.setPrefHeight(40);     // Size Button "Login" Loginfenster
        buttonLLogin.setPrefWidth(200);
        //login.getStyleClass().set(0, "logreg");
        buttonLLogin.setId("button-login");

        //Rechteck Hintergrund
        backgroundrec.setFill(Paint.valueOf("#B6CC95"));
        backgroundrec.setWidth(300);        // Size Hintergrund Rechteck
        backgroundrec.setHeight(400);
        backgroundrec.setOpacity(0.8);


        for (int i = 0; i < 2; i++) {

            passwordField[i] = new PasswordField();
            passwordField[i].setId("textfield-login");
            passwordField[i].setPrefHeight(40);         // Size 2 Passwortfelder Registrierungsfenster
            passwordField[i].setPrefWidth(200);
            if(i==0){
                passwordField[i].setPromptText("Passwort");
            }else{
                passwordField[i].setPromptText("Passwort wiederholen");
            }

            pane.getChildren().add(passwordField[i]);
            passwordField[i].setVisible(false);

            //setzen der positionen der UI-Bausteine
        }
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


        //Text registieren (clickable text im Login Fenster)
        txt.setLayoutY((pane.getHeight()/2) + pane.getHeight()/4);
        txt.setLayoutX(pane.getWidth()/2 - 30);
        txt.setText("Registrieren");

        txt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                updateUI();
                neuesKonto();
            }
        });

        Main.stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            updateUI();
        });

        Main.stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            updateUI();
        });

        updateUI();
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
        txt.setVisible(true);
        passwordField[0].setVisible(true);
        passwordField[1].setVisible(true);

        //registrierungsbutton
        buttonReg = new Button();
        buttonReg.setText("Registrieren");
        System.out.println();
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
        textfieldRBenutzername.setPromptText("Benutzername");

        updateUI();

        pane.getChildren().add(textfieldRBenutzername);

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
                updateUI();
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
        updateUI();
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
     * Methode zum Anpassen der Positionen der einzelnen UI-Bauteile
     * @author  René Weissteiner
     * @date    16.05.2023
     */
    public void updateUI(){
        double midx = Main.stage.getWidth();
        double midy = Main.stage.getHeight();
        System.out.println(midx);
        System.out.println(midy);

        //Loginfenster
        textfieldLBenutzer.setLayoutX(midx/2 - textfieldLBenutzer.getWidth()/2);
        textfieldLBenutzer.setLayoutY(midy/2 - 50);

        textfieldLPasswort.setLayoutX(midx/2 - textfieldLPasswort.getWidth()/2);
        textfieldLPasswort.setLayoutY(midy/2 + 10);

        buttonLLogin.setLayoutX(midx/2 - buttonLLogin.getWidth()/2);
        buttonLLogin.setLayoutY(midy/2 + 75);

        //Registrierungsfenster
        textfieldRBenutzername.setLayoutX(midx/2 - textfieldRBenutzername.getWidth()/2);
        textfieldRBenutzername.setLayoutY(midy/2 - 70);

        passwordField[0].setLayoutX(midx/2 - passwordField[0].getWidth()/2);
        passwordField[0].setLayoutY(midy/2 - 20);

        passwordField[1].setLayoutX(midx/2 - passwordField[1].getWidth()/2);
        passwordField[1].setLayoutY(midy/2 + 30);

        buttonReg.setLayoutX(midx/2 - buttonReg.getWidth()/2);
        buttonReg.setLayoutY(midy/2 + 80);

        //beide Anzeigen
        backgroundrec.setX(midx/2 - backgroundrec.getWidth()/2);
        backgroundrec.setY(midy/2 - backgroundrec.getHeight()/2);

        iconView.setX(Main.stage.getWidth()/2 - 40);
        iconView.setY(backgroundrec.getY() + 10);

        // Unterscheidung zwischen Loginfenster und Registrierungsfenster beim Login/Registrierungs Text (clickable) und Fehlertext
       if(buttonLLogin.isVisible()){
            txt.setLayoutX(midx/2 - 30);
            txt.setLayoutY(midy/2 + 140);
            textfehler.setLayoutY(pane.getHeight()/2 - 55);
            textfehler.setLayoutX(pane.getWidth() / 2 - 149.0/ 2);
       }else{
            txt.setLayoutX(midx/2 - 25);
            txt.setLayoutY(midy/2 + 140);
           textfehler.setLayoutY(pane.getHeight() / 2 - 70);
            if(Objects.equals(textfehler.getText(), "Kein Benutzername")) {
                textfehler.setLayoutX(pane.getWidth() / 2 - 60);
            }else{
                textfehler.setLayoutX(pane.getWidth() / 2 - 120);
            }
       }

        adjustBackgroundSize();
    }

    /**
     *
     * Methode zum Anpassen der Groesse des Hintergrund an die Fenstergroesse
     * @author  René Weissteiner
     * @date    16.05.2023
     */
    public void adjustBackgroundSize(){
        // Binden Sie die Breite und Höhe der ImageView an die Breite und Höhe der Pane
        imageView.fitWidthProperty().bind(Main.stage.getScene().getWindow().widthProperty());
        imageView.fitHeightProperty().bind(Main.stage.getScene().getWindow().heightProperty());
        // Setzen Sie den PreserveRatio-Parameter auf true, um das Seitenverhältnis des Bildes zu erhalten
        imageView.setPreserveRatio(false);
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
                    System.out.println("Passwort und Benutzer stimmen überein");

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