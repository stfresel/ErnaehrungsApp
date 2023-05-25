package com.example.fitnessapp;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Ladet die Anmeldungs- und Registrierungsszene.
 * Zudem ist er für das Speichern der Daten verantwortlich.
 */
public class Controller {

    /**
     * Gibt den Benutzer an.
     */
    static Benutzer benutzer;

    File ordner;
    File file_benutzer;
    Path benutzerTxt;
    public Pane pane = new Pane();
    private TextField textfieldRBenutzername = new TextField();
    private Button buttonReg = new Button();
    private final PasswordField[] passwordField = new PasswordField[2];
    private final Text textfehler = new Text();
    private final Text txt = new Text();
    private final TextField textfieldLBenutzer = new TextField();
    private final PasswordField textfieldLPasswort = new PasswordField();
    private final Button buttonLLogin =new Button();
    private ImageView imageView;
    private ImageView iconView;
    private final Rectangle backgroundrec = new Rectangle();
    private final double sizeOfObjectsX = 200;
    private final double sizeOfObjectsY = 40;


    /**

     * In der Methode werden die UI-Komponenten initialisiert.
     * Der Hintergrund wird geladen und es werden alle Komponenten hinzugefügt.
     */
    public void initialize() {
        benutzerTxt = Paths.get("C:\\ErnaehrungsApp\\Benutzer.txt");
        ordner = new File("C:\\ErnaehrungsApp");
        file_benutzer = new File("C:\\ErnaehrungsApp\\Benutzer.txt");
        try{
            if (!ordner.exists()){
                System.out.println("Ordner gibt es nicht");
                ordner.mkdir();
                file_benutzer.createNewFile();

            } else if (!file_benutzer.exists()) {
                file_benutzer.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        benutzer = new Benutzer();
        pane.setPrefSize(Main.stage.getScene().getWidth(), Main.stage.getScene().getHeight());

        //laden Hintergrund
        InputStream stream;
        try {
            stream = new FileInputStream(Objects.requireNonNull(this.getClass().getResource("backgroundLogin.png")).getPath());
            //stream = new FileInputStream("src/main/resources/com/example/fitnessapp/backgroundLogin.png");
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
            //iconstream = new FileInputStream("src/main/resources/com/example/fitnessapp/logo.png");
            iconstream = new FileInputStream(Objects.requireNonNull(this.getClass().getResource("logo.png")).getPath());
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

    /**
     * Die Methode ladet die UI-Komponenten, welche für die Anmeldung benötigt werden, in das <code>pane</code>.
     * Zu den UI-Komponenten gehört ein Textfeld für den Benutzernamen (<code>textfieldLBenutzer</code>) und ein Textfeld
     * für das Passwort (<code>textfieldLPasswort</code>).
     */
    public void loginfun(){
        System.out.println("loginfunnnnnnn");
        textfehler.setVisible(false);
        pane.requestFocus();
        textfieldLBenutzer.setVisible(true);
        textfieldLPasswort.setVisible(true);
        buttonLLogin.setVisible(true);
        txt.setVisible(true);

        //Textfeld
        textfieldLBenutzer.setPrefHeight(sizeOfObjectsY);       // Size Textfeld Benutzername Loginfenster
        textfieldLBenutzer.setPrefWidth(sizeOfObjectsX);
        textfieldLBenutzer.setPromptText("Benutzer");
        textfieldLBenutzer.setId("textfield-login");

        //Passwortfeld
        textfieldLPasswort.setPrefHeight(sizeOfObjectsY);       // Size Textfeld Passwort Loginfenster
        textfieldLPasswort.setPrefWidth(sizeOfObjectsX);
        textfieldLPasswort.setPromptText("Passwort");
        textfieldLPasswort.setId("textfield-login");

        //Button
        buttonLLogin.setText("Login");
        buttonLLogin.setPrefHeight(sizeOfObjectsY);     // Size Button "Login" Loginfenster
        buttonLLogin.setPrefWidth(sizeOfObjectsX);
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
            passwordField[i].setPrefHeight(sizeOfObjectsY);         // Size 2 Passwortfelder Registrierungsfenster
            passwordField[i].setPrefWidth(sizeOfObjectsX);

            pane.getChildren().add(passwordField[i]);
            passwordField[i].setVisible(false);

            //setzen der positionen der UI-Bausteine
        }
        passwordField[0].setPromptText("Passwort");
        passwordField[1].setPromptText("Passwort wiederholen");
//--------------------------------------------------------------------------------------------------------\\
        buttonLLogin.setOnMouseClicked(mouseEvent -> {
            try {
                einloggen();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        //Text registieren (clickable text im Login Fenster)
        txt.setLayoutY((pane.getHeight()/2) + pane.getHeight()/4);
        txt.setLayoutX(pane.getWidth()/2 - 30);
        txt.setText("Registrieren");

        txt.setOnMouseClicked(mouseEvent -> {
            updateUI();
            neuesKonto();
        });

        // achtung!!!!!!!!!!!!! scene net stage!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        Main.stage.getScene().widthProperty().addListener((obs, oldVal, newVal) -> updateUI());

        Main.stage.getScene().heightProperty().addListener((obs, oldVal, newVal) -> updateUI());

        updateUI();
    }


    /**
     * Die Methode ladet die UI-Komponenten, welche für die Registrierung benötigt werden, in das <code>pane</code>.
     * Zu den UI-Komponenten gehört ein Textfeld für den Benutzernamen (<code>textfieldRBenutzername</code>) und zwei Textfelder
     * für das Passwort und das Wiederholen des Passwortes (<code>passwordField[2]</code>). Es wurde eingebaut um
     * eventuelle Tippfehler zu vermeiden.
     */
    public void neuesKonto() {
        //benutzer.setHome(new Home());
        textfehler.setVisible(false);
        //int counter = 400;
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
        buttonReg.setPrefHeight(sizeOfObjectsY);        // Size Button "registrieren" Registrierungsfenster
        buttonReg.setPrefWidth(sizeOfObjectsX);
        buttonReg.setId("button-login");



        pane.getChildren().add(buttonReg);

        //Textfeld für den Benutzernamen
        textfieldRBenutzername = new TextField();
        textfieldRBenutzername.setMinHeight(25);
        textfieldRBenutzername.setMinWidth(149);
        textfieldRBenutzername.setPrefHeight(sizeOfObjectsY);       // Size Textfeld Benutzername Registrierungsfenster
        textfieldRBenutzername.setPrefWidth(sizeOfObjectsX);
        textfieldRBenutzername.setId("textfield-login");
        //double x = pane.getWidth() / 2 - textfieldRBenutzername.getMinWidth() / 2;
        //double y = pane.getHeight() - counter;
        textfieldRBenutzername.setPromptText("Benutzername");

        updateUI();

        pane.getChildren().add(textfieldRBenutzername);

        buttonReg.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    if (isBenutzernameFrei(textfieldRBenutzername.getText())){
                        speichern();
                    }else{
                        //########Textfehler stezten!!!!!!!!
                        System.out.println("Benutzername bereits vergeben");
                        textfehler.setText("Benutzername bereits vergeben");
                        textfehler.setVisible(true);
                        textfehler.setFill(Color.RED);
                        textfehler.setLayoutY(pane.getHeight()/2 - 80);
                        updateUI();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        txt.setOnMouseClicked(new EventHandler<>() {
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
     * Überprüft, ob der Benutzername nicht schon vergeben ist
     * @param benutzername Benutzername, nach welchem gesucht werden soll
     * @return Gibt true zurück, wenn er noch frei ist
     */

    private boolean isBenutzernameFrei(String benutzername){
        try {
            FileReader fr = new FileReader(file_benutzer);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();

            while(line != null){
                System.out.println(line);
                String[] userdata = line.split(" ");
                if (Objects.equals(userdata[0], benutzername)){
                    return false;
                }
                line = br.readLine();
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * Die Methode speichert den <code>benutzername</code> und das <code>passwort</code> im <code>Benutzer.txt</code> File.
     *
     * @throws IOException Die Exception wird geworfen, falls es einen Fehler beim Lesen/Schreiben des Benutzer.txt files gibt.
     */
    private void speichern() throws IOException {
        boolean loggedIn = false;
        // Speichern im File, wo alle Benutzernamen und Passwörter stehen

        //URL url = this.getClass().getResource("Benutzer.txt");


        //###
        //BufferedWriter br = new BufferedWriter(fr);
        textfehler.setFill(Color.RED);
        if (passwordField[0].getText().equals(passwordField[1].getText()) && textfieldRBenutzername.getText().length() != 0 && passwordField[0].getText().length()!=0 &&passwordField[1].getText().length()!=0) {
            loggedIn = true;
            BufferedWriter fr = new BufferedWriter(new FileWriter(file_benutzer, true));
            System.out.println("Passwort richtig");

            benutzer.setBenutzername(textfieldRBenutzername.getText());
            benutzer.setPasswort(passwordField[1].getText());
            String temp = benutzer.getBenutzername() + " " + benutzer.getPasswort();
            fr.append(temp);
            fr.newLine();
            fr.close();

        } else {
            textfehler.setVisible(true);
            if (textfieldRBenutzername.getText().isEmpty() || passwordField[0].getText().isEmpty() || passwordField[1].getText().isEmpty()){
                textfehler.setText("Bitte gib alle Daten ein");
            } else if (!passwordField[0].getText().equals(passwordField[1].getText())){
                textfehler.setText("Passwörter stimmen nicht überein");
            }else {
                textfehler.setText("Benutzername und/oder Passwort sind falsch");
            }
        }
        updateUI();
        if (loggedIn){
            // Erstellen des Paths zum .ser File
            datenSpeichern();

            //Main.stage.setScene(new Scene(home.getKonto().datenAnsicht()));
            Main.switchScene(new Scene(benutzer.getHome().getKonto().datenAnsicht(true)));
            //home.startHome();
        }
    }

    /**
     * Die Methode passt die Position der UI-Bauteile an die Größe des Fensters an.
     */
    public void updateUI(){
        double midx = Main.stage.getScene().getWidth();
        double midy = Main.stage.getScene().getHeight();
        System.out.println(midx);
        System.out.println(midy);

        //Loginfenster
        textfieldLBenutzer.setLayoutX(midx/2 - sizeOfObjectsX/2);
        textfieldLBenutzer.setLayoutY(midy/2 - 50);

        textfieldLPasswort.setLayoutX(midx/2 - sizeOfObjectsX/2);
        textfieldLPasswort.setLayoutY(midy/2 + 10);

        buttonLLogin.setLayoutX(midx/2 - sizeOfObjectsX/2);
        buttonLLogin.setLayoutY(midy/2 + 75);

        //Registrierungsfenster
        textfieldRBenutzername.setLayoutX(midx/2 - sizeOfObjectsX/2);
        textfieldRBenutzername.setLayoutY(midy/2 - 70);

        passwordField[0].setLayoutX(midx/2 - sizeOfObjectsX/2);
        passwordField[0].setLayoutY(midy/2 - 20);

        passwordField[1].setLayoutX(midx/2 - sizeOfObjectsX/2);
        passwordField[1].setLayoutY(midy/2 + 30);

        System.out.println(buttonReg.getWidth());
        buttonReg.setLayoutX(midx/2 - sizeOfObjectsX/2);
        buttonReg.setLayoutY(midy/2 + 80);

        //beide Anzeigen
        backgroundrec.setX(midx/2 - backgroundrec.getWidth()/2);
        backgroundrec.setY(midy/2 - backgroundrec.getHeight()/2);

        iconView.setX(midx/2 - sizeOfObjectsY - 5);
        iconView.setY(backgroundrec.getY() + 10);

        // Unterscheidung zwischen Loginfenster und Registrierungsfenster beim Login/Registrierungs-Text (clickable) und Fehlertext
        if(buttonLLogin.isVisible()){
            txt.setLayoutX(midx/2 - 32);
            txt.setLayoutY(midy/2 + 140);
            textfehler.setLayoutY(pane.getHeight()/2 - 55);
            textfehler.setLayoutX(pane.getWidth() / 2 - 149.0/ 2);

        }else {
            txt.setLayoutX(midx / 2 - 18);
            txt.setLayoutY(midy / 2 + 140);
            textfehler.setLayoutY(pane.getHeight() / 2 - 80);
        }
        if (Objects.equals(textfehler.getText(), "Bitte gib alle Daten ein")) {
            textfehler.setLayoutX(pane.getWidth() / 2 - 60);
        } else if (Objects.equals(textfehler.getText(), "Passwörter stimmen nicht überein")) {
            textfehler.setLayoutX(pane.getWidth() / 2 - 105);
        }else if(Objects.equals(textfehler.getText(), "Benutzername und/oder Passwort sind falsch")) {
            textfehler.setLayoutX(pane.getWidth() / 2 - 137);
        }else if(Objects.equals(textfehler.getText(), "Benutzername bereits vergeben")){
            textfehler.setLayoutX(pane.getWidth() / 2 - 99);
        }
        adjustBackgroundSize();
    }

    /**
     * Die Methode passt die Größe vom Hintergrund an die Fenstergröße an.
     */
    public void adjustBackgroundSize(){
        // Binden Sie die Breite und Höhe der ImageView an die Breite und Höhe der Pane
        /*
        if (imageView.getFitHeight() > imageView.getFitWidth()){
            imageView.fitHeightProperty().bind(Main.stage.getScene().getWindow().heightProperty());
        }else {
            imageView.fitWidthProperty().bind(Main.stage.getScene().getWindow().widthProperty());
        }

         */
        imageView.fitHeightProperty().bind(Main.stage.getScene().getWindow().heightProperty());

        // Setzen Sie den PreserveRatio-Parameter auf true, um das Seitenverhältnis des Bildes zu erhalten
        imageView.setPreserveRatio(true);

    }

    /**
     * Die Daten werden Serialisiert.
     * Die Methode speichert die Daten von <code>Home</code> im <code>(benutzername)_(passwort).ser</code> File.
     *
     * @serialData Die gesamte Klasse <code>Home</code>, samt den Attribute, werden serialisiert und in ein File geschrieben.
     */
    public void datenSpeichern(){
        File serFile = new File(ordner.getPath() + "\\" + benutzer.getBenutzername() + "_" + benutzer.getPasswort() + ".ser");
        System.out.println(serFile);
        if (!serFile.exists()){
            try {
                serFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Path path = serFile.toPath();

        try (ObjectOutputStream whereToWrite = new ObjectOutputStream(Files.newOutputStream(path, StandardOpenOption.CREATE))) {
            whereToWrite.writeObject(benutzer.getHome());
            System.out.println("Saved Home");
        } catch (IOException e) {
            System.out.println("Can't serialize " + path.getFileName() + ": " + e.getMessage());
        }
    }


    /**
     * Die Methode ist für das login zuständig.
     * Dabei überprüft sie, ob im File <code>Benutzer.txt</code> der Benutzername und
     * das dazugehörige Passwort abgespeichert sind.
     *
     * @throws IOException Die Exception wird geworfen, falls es einen Fehler beim Lesen/Schreiben des Benutzer.txt files gibt.
     */
    public void einloggen() throws IOException {
        pane.requestFocus();

        BufferedReader br = new BufferedReader(new FileReader(benutzerTxt.toFile()));
        textfehler.setVisible(false);
        benutzer.setBenutzername(textfieldLBenutzer.getText());
        benutzer.setPasswort(textfieldLPasswort.getText());
        boolean loggedIn = false;
        String zeile = br.readLine();
        while(zeile != null){
            String[] benutzerDaten = zeile.split(" ");
            if (Objects.equals(textfieldLBenutzer.getText(), benutzerDaten[0]) && Objects.equals(textfieldLPasswort.getText(), benutzerDaten[1])) {
                //textfehler.setText("");
                textfehler.setVisible(false);
                System.out.println("Passwort und Benutzer stimmen überein");

                benutzer.setBenutzername(textfieldLBenutzer.getText());
                benutzer.setPasswort(textfieldLPasswort.getText());
                loggedIn = true;
                break;
            }
            zeile = br.readLine();
        }
        br.close();

        if (loggedIn){
            auslesenSer();
        }else{
            pane.requestFocus();
            textfieldLBenutzer.setText("");
            textfieldLPasswort.setText("");
            textfehler.setVisible(true);
            textfehler.setFill(Color.RED);
            textfehler.setText("Benutzername und/oder Passwort sind falsch");
        }
        updateUI();
    }

    /**
     * Die Methode ist für die Deserialization der Daten zuständig.
     * Der ausgelesene Stream wird in <code>Home</code> gecastet und im <code>Benutzer</code> unter <code>home</code>
     * gespeichert.
     */
    private void auslesenSer(){
        File serFile = new File(ordner.getPath() + "\\" + benutzer.getBenutzername() + "_" + benutzer.getPasswort() + ".ser");
        System.out.println(serFile);
        Path path = serFile.toPath();

        try {
            try (ObjectInputStream whereToReadFrom = new ObjectInputStream(Files.newInputStream(path))) {
                benutzer.setHome((Home) whereToReadFrom.readObject());
                System.out.println("auslesen vom file");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Fehler beim Auslesen aus dem Tagebuch" + e.getMessage());
        }

        benutzer.getHome().startHome();
    }

    /**
     * Gibt den Tagesumsatz einer Person zurück
     * @return Tagesumsatz an Nährwerten
     */
    static Naehrwerte getNaehrwerte(){
        return benutzer.getHome().getKonto().getNaehrwerteTagesUmsatz();
    }

}