package com.example.fitnessapp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    private String tempName = null;
    private Naehrwerte naehrwerte = new Naehrwerte(0,0,0,0);
    //private final Path path = Paths.get("ZutatenFile.ser");
    private VBox zutatenPane;

    private final VBox bereitsHinzugefuegteZutaten = new VBox();
    //private HBox hBox;

    public void loadMealScene() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPrefWidth(Main.pane.getPrefWidth());
        gridPane.setPrefHeight(Main.pane.getPrefHeight());
        Scene scene = new Scene(gridPane);

        // wenn bereits eine Zutat zum meal hinzugefügt wurde
        if (zutaten.size() > 0){
            gridPane.getChildren().add(bereitsHinzugefuegteZutaten);
        }
        TextField nameTextField;
        // wenn man den namen des gerichtes eingegeben hat und man dann zutaten hinzufügt, damit namen neu geladen wird
        if (tempName != null) {
            nameTextField = new TextField(tempName);
        }else{
            nameTextField = new TextField();
        }
        //gridPane.addRow(0, bereitsHinzugefuegteZutaten);
        gridPane.addRow(1, new Label("Name des Gerichtes: "), nameTextField);
        Button addZutatBtn = new Button("Zutat hinzufügen");
        addZutatBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // zutaten fenster laden
                tempName = nameTextField.getText();
                System.out.println("neue Zutat");
                loadZutatenScene();
            }
        });

        Button mealFertig = new Button("fertig");
        mealFertig.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });
        // sie sollten immer die letzten elemente im gridPane sein
        gridPane.addRow(1, addZutatBtn);
        gridPane.addRow(2, mealFertig);
        Main.stage.setScene(scene);
    }

    /**
     * Ladet die Zutaten Scene, bei welcher man Zutaten zum Gericht hinzufügen kann.
     */
    public void loadZutatenScene(){
        zutatenPane = new VBox();

        zutatenPane.setPrefWidth(Main.pane.getWidth());
        zutatenPane.setPrefHeight(Main.pane.getHeight());

        //GridPane zutatSuchen = new GridPane();
        GridPane zutatErstellen = new GridPane();
        GridPane loadZutate = new GridPane();


        CheckBox checkbox = new CheckBox("neue Zutat erstellen");

        // gespeicherte zutat verwenden
        String[] zutatenNames = new String[Main.gespeicherteZutaten.size()];
        for (int i = 0; i < Main.gespeicherteZutaten.size(); i++) {
            zutatenNames[i] = Main.gespeicherteZutaten.get(i).getName();
        }
        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(zutatenNames));
        NumericTextField mengeGespeicherteZutat = new NumericTextField();

        // no in eventhandler mochn
        loadZutate.addRow(0, comboBox, mengeGespeicherteZutat);



        // zutat neu erstellen ----------------------------------------------------------------
        Label nameDerZutat = new Label("Name: ");
        TextField textFieldName = new TextField();

        Label mengeGegessen = new Label("Gegessene Menge (in g): ");
        NumericTextField textFieldGegessen = new NumericTextField();


        zutatenPane.getChildren().add(0, checkbox);
        zutatenPane.getChildren().add(loadZutate);
        zutatErstellen.addRow(0, nameDerZutat, textFieldName);
        zutatErstellen.addRow(1, mengeGegessen, textFieldGegessen);


        NumericTextField grammTextField = new NumericTextField();
        NumericTextField proteineTextField = new NumericTextField();
        NumericTextField fetteTextField = new NumericTextField();
        NumericTextField kolenhydrateTextField = new NumericTextField();
        NumericTextField kcalTextField = new NumericTextField();

        zutatErstellen.addRow(2, new Label("Menge der Nährwertangabe (in g): "), grammTextField);
        zutatErstellen.addRow(3, new Label("Kalorien: "), kcalTextField);
        zutatErstellen.addRow(4, new Label("Kolenhydrate: "), kolenhydrateTextField);
        zutatErstellen.addRow(5, new Label("Proteine: "), proteineTextField);
        zutatErstellen.addRow(6, new Label("Fette: "),fetteTextField);

        checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                System.out.println("change");
                if (checkbox.isSelected()){ // neue Zutat erstellen
                    zutatenPane.getChildren().remove(loadZutate);
                    zutatenPane.getChildren().add(zutatErstellen);

                } else {                    // Zutat aus Speicher holen
                    zutatenPane.getChildren().remove(zutatErstellen);
                    zutatenPane.getChildren().add(loadZutate);
                }
            }
        });

        //zutatenPane.getChildren().add(zutatSuchen);
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
                    for (int i = 0; i < Main.gespeicherteZutaten.size(); i++) {
                        if (Objects.equals(Main.gespeicherteZutaten.get(i).getName(), nameDerZutat.getText())){
                            // trefeerrrrrrr
                            //Autofilll();
                            System.out.println("Autofilllllll-----wichtig");
                        }
                    }

                    System.out.println("feritggg");
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
                }
                //---------------------------------------------
            }
        });

        Button alle_zutaten_wurden_eingegeben = new Button("das waren alle zutaten");
        alle_zutaten_wurden_eingegeben.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("fertigggg");
                loadMealScene();
            }
        });
        zutatenPane.getChildren().add(0, bereitsHinzugefuegteZutaten);
        zutatenPane.getChildren().add(zutatenPane.getChildren().size(),fertigBtn);
        zutatenPane.getChildren().add(zutatenPane.getChildren().size(),alle_zutaten_wurden_eingegeben);


        Scene zutatenScene = new Scene(zutatenPane);
        Main.stage.setScene(zutatenScene);

    }


    private void addZutate2Meal(Zutat z) {
        zutaten.add(z);
        naehrwerte.setKcal(naehrwerte.getKcal() + z.getNaehrwerteEffektivGegessen().getKcal());
        naehrwerte.setKohlenhydrate(naehrwerte.getKohlenhydrate() + z.getNaehrwerteEffektivGegessen().getKohlenhydrate());
        naehrwerte.setFett(naehrwerte.getFett() + z.getNaehrwerteEffektivGegessen().getFett());
        naehrwerte.setProtein(naehrwerte.getProtein() + z.getNaehrwerteEffektivGegessen().getProtein());
        System.out.println("insgesamt NW" + naehrwerte);
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
                loadZutatenScene();

            }
        });
        hBox.getChildren().add(delZutatBtn);
        hBox.getChildren().add(new Label(z.toString()));
        bereitsHinzugefuegteZutaten.getChildren().add(hBox);
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
