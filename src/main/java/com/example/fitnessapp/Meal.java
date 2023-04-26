package com.example.fitnessapp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Objects;

public class Meal implements Serializable{

    private String name;
    private final ArrayList<Zutat> zutaten = new ArrayList<>();

    private Naehrwerte naehrwerte = new Naehrwerte(0,0,0,0);
    private Path path = Paths.get("ZutatenFile.ser");
    private VBox zutatenPane = new VBox();

    private final VBox bereitsHinzugefuegteZutaten = new VBox();
    //private HBox hBox;

    public void loadMealScene() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPrefWidth(Main.pane.getPrefWidth());
        gridPane.setPrefHeight(Main.pane.getPrefHeight());
        Scene scene = new Scene(gridPane);
        TextField nameTextField = new TextField();
        //gridPane.addRow(0, bereitsHinzugefuegteZutaten);
        gridPane.addRow(1, new Label("Name des Gerichtes: "), nameTextField);
        Button addZutatBtn = new Button("Zutat hinzufügen");
        addZutatBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // zutaten fenster laden
                System.out.println("neue Zutat");
                loadZutatenScene();
            }
        });
        gridPane.addRow(1, addZutatBtn);
        Main.stage.setScene(scene);
    }

    /**
     * Ladet die Zutaten Scene, bei welcher man Zutaten zum Gericht hinzufügen kann.
     */
    public void loadZutatenScene(){
        zutatenPane.setPrefWidth(Main.pane.getWidth());
        zutatenPane.setPrefHeight(Main.pane.getHeight());

        GridPane zutatSuchen = new GridPane();
        GridPane zutatErstellen = new GridPane();

        // zutat suchen
        CheckBox checkbox = new CheckBox("neue Zutat erstellen");

        Label nameDerZutat = new Label("Name: ");
        TextField textFieldName = new TextField();

        Label mengeGegessen = new Label("Gegessene Menge (in g): ");
        NumericTextField textFieldGegessen = new NumericTextField();
        //zutatenPane.getChildren().addAll(nameDerZutat, textFieldName, mengeGegessen, textFieldGegessen);

        zutatSuchen.addRow(0, checkbox);
        zutatSuchen.addRow(1, nameDerZutat, textFieldName);
        zutatSuchen.addRow(2, mengeGegessen, textFieldGegessen);

        // zutat erstellen
        Label naehrwerteaufXgramm = new Label("Menge der Nährwertangabe (in g): ");
        NumericTextField grammTextField = new NumericTextField();

        Label proteine = new Label("Proteine: ");
        NumericTextField proteineTextField = new NumericTextField();

        Label fette = new Label("Fette: ");
        NumericTextField fetteTextField = new NumericTextField();

        Label kolenhydrate = new Label("Kolenhydrate: ");
        NumericTextField kolenhydrateTextField = new NumericTextField();

        Label kcal = new Label("Kalorien: ");
        NumericTextField kcalTextField = new NumericTextField();

        zutatErstellen.addRow(0, naehrwerteaufXgramm, grammTextField);
        zutatErstellen.addRow(1, kcal, kcalTextField);
        zutatErstellen.addRow(2, kolenhydrate, kolenhydrateTextField);
        zutatErstellen.addRow(3, proteine, proteineTextField);
        zutatErstellen.addRow(4, fette,fetteTextField);

        checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                System.out.println("change");
                if (checkbox.isSelected()){ // neue Zutat erstellen
                    zutatenPane.getChildren().add(zutatErstellen);
                } else {                    // Zutat aus Speicher holen
                    zutatenPane.getChildren().remove(zutatErstellen);
                }
            }
        });

        zutatenPane.getChildren().add(zutatSuchen);
        // erstellen des Submit-Buttons
        Button fertigBtn = new Button("Zutat Hinzufügen");
        fertigBtn.setPrefWidth(30*2);
        fertigBtn.setPrefHeight(30);
        fertigBtn.setLayoutY(zutatenPane.getHeight()-100);

        // inuputs noch in Var speichern!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        fertigBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Zutat aus Speicher holen
                if (!checkbox.isSelected()){
                    // Achtung die nutritions miasn no an die menge des gegessenen ungepasst werdn
                    Zutat ztemp = getZutatausSpeicher(nameDerZutat.getText());
                    System.out.println(ztemp);
                    Zutat z;
                    if (ztemp != null) {
                        z = new Zutat(nameDerZutat.getText(),Integer.parseInt(textFieldGegessen.getText()), ztemp.getMengeDerNaehrwertangaben(),
                                ztemp.getNaehrwerteProXGramm());
                        addZutate2Meal(z);      // achtung olle zutaten.add methoden ersetzen

                    }else{
                        System.out.println("gesuchte Zutat ist null");
                    }
                    System.out.println("Zutat aus Speicher geholen");
                    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                } else {
                    // Zutat neu Hinzufügen
                    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                    System.out.println("neue Zutat erstellt");
                    Zutat z = new Zutat(textFieldName.getText(), Integer.parseInt(textFieldGegessen.getText()),
                            Integer.parseInt(grammTextField.getText()),
                            new Naehrwerte(Integer.parseInt(kcalTextField.getText()), Integer.parseInt(fetteTextField.getText()),
                                    Integer.parseInt(kolenhydrateTextField.getText()), Integer.parseInt(proteineTextField.getText())));
                    System.out.println(z);
                    Main.gespeicherteZutaten.add(z);
                    addZutate2Meal(z);
                    saveZutaten();
                }
                //---------------------------------------------
            }
        });

        Button alle_zutaten_wurden_eingegeben = new Button("das waren alle zutaten");
        alle_zutaten_wurden_eingegeben.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("fetrigggg");
                loadMealScene();
            }
        });
        zutatenPane.getChildren().add(0, bereitsHinzugefuegteZutaten);
        zutatenPane.getChildren().add(alle_zutaten_wurden_eingegeben);

        zutatenPane.getChildren().add(fertigBtn);
        Scene zutatenScene = new Scene(zutatenPane);
        Main.stage.setScene(zutatenScene);
    }

    /**
     *
     * @param name Name der Zutat, welche man zum Gericht hinzufügen möchte und sie bereits benutzt hat.
     * @return
     */
    private Zutat getZutatausSpeicher(String name){
        System.out.println(name);
        System.out.println("weiter mochn----------------------------------");


        try (ObjectInputStream whereToReadFrom = new ObjectInputStream(Files.newInputStream(path))) {
            Main.gespeicherteZutaten = (ArrayList<Zutat>) whereToReadFrom.readObject();
            System.out.println("auslesen vom file");
            //System.out.println(Main.gespeicherteZutaten);
            for (int i = 0; i < Main.gespeicherteZutaten.size(); i++) {
                if (Objects.equals(Main.gespeicherteZutaten.get(i).getName(), name)) {
                    return Main.gespeicherteZutaten.get(i);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("File: Meal --> Fehler bei addZutat");
            //throw new RuntimeException();
        }
        return null;
        // evt mit a combo box
    }

    /**
     * Speichert die neuen Zutaten ab.
     */
    private void saveZutaten (){
        try (ObjectOutputStream whereToWrite = new ObjectOutputStream(Files.newOutputStream(path , StandardOpenOption.CREATE))) {
            whereToWrite.reset();
            whereToWrite.writeObject(Main.gespeicherteZutaten);
            System.out.println("Saved Zutaten");
        } catch (IOException e) {
            System.out.println("Can't serialize file: " + e.getMessage());
        }
    }

    private void addZutate2Meal(Zutat z) {
        zutaten.add(z);
        naehrwerte.setKcal(naehrwerte.getKcal() + z.getNaehrwerteEffektivGegessen().getKcal());
        naehrwerte.setKohlenhydrate(naehrwerte.getKohlenhydrate() + z.getNaehrwerteEffektivGegessen().getKohlenhydrate());
        naehrwerte.setFett(naehrwerte.getFett() + z.getNaehrwerteEffektivGegessen().getFett());
        naehrwerte.setProtein(naehrwerte.getProtein() + z.getNaehrwerteEffektivGegessen().getProtein());
        HBox hBox = new HBox();
        Button delZutatBtn = new Button("-");
        delZutatBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("delete Zutat");
                System.out.println(zutaten);
                delZutat(z);
                bereitsHinzugefuegteZutaten.getChildren().remove(hBox);
                //hBox.getChildren().removeAll();     // achtung beim löschen--> evt alle zutaten in ein VBox tun
                System.out.println("a-" + zutaten);
                //reloadZutatenListe();

            }
        });
        hBox.getChildren().add(delZutatBtn);
        hBox.getChildren().add(new Label(z.toString()));
        bereitsHinzugefuegteZutaten.getChildren().add(hBox);
    }

    private void reloadZutatenListe() {

    }

    public void delZutat(Zutat z) {
        zutaten.remove(z);
    }

    //_________________________getter und setter ________________________________________


    public String getName() {
        return name;
    }

    public String getMealString() {
        String string = name + "\n\t" + zutaten;
        return string;
    }

    @Override
    public String toString() {
        return name +
                " zutaten=" + zutaten +
                '}';
    }
}


/*
BufferedReader reader;      //------------
        try {
            reader = new BufferedReader(new FileReader(zutatenFile));

            String buf = reader.readLine();
            while (buf != null){
                //-------------------------------------------------------------------------
                System.out.println(buf);

                buf = reader.readLine();
            }
        } catch (FileNotFoundException e){
            System.out.println("Zutatenfile konnte nicht gefunden werden");
        }
 */
