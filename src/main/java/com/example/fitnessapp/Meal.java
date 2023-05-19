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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Meal implements Serializable{

    private String name;
    private final ArrayList<Zutat> zutaten = new ArrayList<>();

    private String tempName = null;
    private Naehrwerte naehrwerteMeal = new Naehrwerte(0,0,0,0);
    private transient VBox zutatenPane;

    private final transient VBox bereitsHinzugefuegteZutaten = new VBox();
    private transient Text fehlermeldung = new Text();

    public void loadMealScene() {
        GridPane gridPane = new GridPane();

        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPrefSize(Main.stage.getScene().getWidth(), Main.stage.getScene().getHeight());

        Scene scene = new Scene(gridPane);

        // wenn bereits Zutaten hinzugefügt wurden
        if (zutaten.size() > 0){
            gridPane.getChildren().add(bereitsHinzugefuegteZutaten);
        }
        TextField nameTextField;
        // wenn man den namen des gerichtes eingegeben hat und man dann zutaten hinzufügt, damit namen neu geladen wird
        nameTextField = new TextField();
        if (tempName != null) {
            nameTextField.setText(tempName);
        }

        gridPane.addRow(1, new Label("Name des Gerichtes: "), nameTextField);
        Button addZutatBtn = new Button("Zutat hinzufügen");
        addZutatBtn.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tempName = nameTextField.getText();
                System.out.println("neue Zutat");
                loadZutatenScene();
            }
        });

        Button mealFertig = new Button("fertig");
        mealFertig.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                name = nameTextField.getText();
                Main.benutzer.getHome().startHome();
            }
        });
        // sie sollten immer die letzten elemente im gridPane sein
        gridPane.addRow(1, addZutatBtn);
        gridPane.addRow(2, mealFertig);
        Main.switchScene(scene);
        //Main.stage.setScene(scene);
    }

    /**
     * Ladet die Zutaten Scene, bei welcher man Zutaten zum Gericht hinzufügen kann.
     */
    public void loadZutatenScene(){
        zutatenPane = new VBox();
        zutatenPane.setPrefSize(Main.stage.getScene().getWidth(), Main.stage.getScene().getHeight());
        GridPane zutatErstellen = new GridPane();
        GridPane loadZutate = new GridPane();
        CheckBox checkbox = new CheckBox("neue Zutat erstellen");
        checkbox.setSelected(true);

        // Fehlermeldung setzten
        fehlermeldung.setText("Bitte gib die fehlenden Werte ein");
        fehlermeldung.setVisible(false);
        fehlermeldung.setFill(Color.RED);

        // gespeicherte zutat verwenden -------------------------------
        String[] zutatenNames = new String[Main.gespeicherteZutaten.size()];
        for (int i = 0; i < Main.gespeicherteZutaten.size(); i++) {
            zutatenNames[i] = Main.gespeicherteZutaten.get(i).getName();
        }
        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(zutatenNames));
        NumericTextField mengeGespeicherteZutat = new NumericTextField();


        // no in eventhandler mochn
        loadZutate.addRow(0, comboBox, mengeGespeicherteZutat);
        loadZutate.addRow(1, fehlermeldung);


        // zutat neu erstellen ----------------------------------------------------------------
        Label nameDerZutat = new Label("Name: ");
        TextField textFieldName = new TextField();

        Label mengeGegessen = new Label("Gegessene Menge (in g): ");
        NumericTextField textFieldGegessen = new NumericTextField();

        zutatenPane.getChildren().add(0, checkbox);
        zutatenPane.getChildren().add(zutatErstellen);

        zutatErstellen.addRow(0, nameDerZutat, textFieldName);
        zutatErstellen.addRow(1, mengeGegessen, textFieldGegessen);



        NumericTextField proteineTextField = new NumericTextField();
        NumericTextField fetteTextField = new NumericTextField();
        NumericTextField kolenhydrateTextField = new NumericTextField();
        NumericTextField kcalTextField = new NumericTextField();
        //fehlermeldung.setVisible(false);

        zutatErstellen.addRow(2, new Label("Kalorien: "), kcalTextField);
        zutatErstellen.addRow(3, new Label("Kolenhydrate: "), kolenhydrateTextField);
        zutatErstellen.addRow(4, new Label("Proteine: "), proteineTextField);
        zutatErstellen.addRow(5, new Label("Fette: "),fetteTextField);
        zutatErstellen.addRow(6, fehlermeldung);

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

        Button fertigBtn = new Button("Zutat Hinzufügen");
        fertigBtn.setPrefSize(60,30);
        fertigBtn.setLayoutY(zutatenPane.getHeight()-100);

        // inuputs noch in Var speichern!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        fertigBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Zutat aus Speicher holen
                if (!checkbox.isSelected()){
                    if (comboBox.getValue().isEmpty()){
                        fehlermeldung.setVisible(true);
                    }else{
                        for (int i = 0; i < Main.gespeicherteZutaten.size(); i++) {
                            if (Objects.equals(Main.gespeicherteZutaten.get(i).getName(), comboBox.getValue())){
                                if (mengeGespeicherteZutat.getText().isEmpty()){
                                    fehlermeldung.setVisible(true);
                                }else {
                                    fehlermeldung.setVisible(false);
                                    System.out.println("aus array holen");
                                    Zutat arrZutat = Main.gespeicherteZutaten.get(i);
                                    int menge = (int) mengeGespeicherteZutat.getDouble();    // Menge der Zutat die gerade gegessen wird
                                    Zutat z = calcNaehrwerteZutat(arrZutat, menge);

                                    addZutate2Meal(z);
                                    break;
                                }

                            }
                        }
                    }


                    System.out.println("fertiggg");
                } else {
                    // Zutat neu Hinzufügen
                    System.out.println("neue Zutat erstellt");
                    if (textFieldName.getText().isEmpty() || textFieldGegessen.getText().isEmpty() ||
                            kcalTextField.getText().isEmpty() || fetteTextField.getText().isEmpty() ||
                            kolenhydrateTextField.getText().isEmpty() || proteineTextField.getText().isEmpty()){
                        // Falls nicht alle Werte eigegeben wurden
                        fehlermeldung.setVisible(true);
                    }else {
                        fehlermeldung.setVisible(false);
                        Zutat z = new Zutat(textFieldName.getText(), (int) Math.round(textFieldGegessen.getDouble()),
                                new Naehrwerte((int) Math.round(kcalTextField.getDouble()), (int) Math.round(fetteTextField.getDouble()),
                                        (int) Math.round(kolenhydrateTextField.getDouble()), (int) Math.round(proteineTextField.getDouble())));
                        System.out.println(z);
                        Main.gespeicherteZutaten.add(z);
                        // alle Eingabefelder zurücksetzen
                        textFieldName.setText("");
                        textFieldGegessen.setText("");
                        kcalTextField.setText("");
                        fetteTextField.setText("");
                        kolenhydrateTextField.setText("");
                        proteineTextField.setText("");
                        addZutate2Meal(z);
                    }

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
        //Main.stage.setScene(zutatenScene);
        Main.switchScene(zutatenScene);
    }


    private Zutat calcNaehrwerteZutat(Zutat alteZutat, int neueMenge){
        double help = (double) neueMenge/ alteZutat.getMenge();
        int kcalNeu = (int) (alteZutat.getNaehrwerte().getKcal() * help);
        int carbsNeu = (int) (alteZutat.getNaehrwerte().getKohlenhydrate() * help);
        int proteinNeu = (int) (alteZutat.getNaehrwerte().getProtein() * help);
        int fettNeu = (int) (alteZutat.getNaehrwerte().getFett() * help);

        Naehrwerte neueNaehrwerte = new Naehrwerte(kcalNeu,fettNeu, carbsNeu, proteinNeu);

        return new Zutat(alteZutat.getName(), neueMenge, neueNaehrwerte);
    }


    private void addZutate2Meal(Zutat z) {
        zutaten.add(z);
        naehrwerteMeal.setKcal(naehrwerteMeal.getKcal() + z.getNaehrwerte().getKcal());
        naehrwerteMeal.setKohlenhydrate(naehrwerteMeal.getKohlenhydrate() + z.getNaehrwerte().getKohlenhydrate());
        naehrwerteMeal.setFett(naehrwerteMeal.getFett() + z.getNaehrwerte().getFett());
        naehrwerteMeal.setProtein(naehrwerteMeal.getProtein() + z.getNaehrwerte().getProtein());
        System.out.println("insgesamt NW" + naehrwerteMeal);
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

            }
        });
        hBox.getChildren().add(delZutatBtn);
        hBox.getChildren().add(new Label(z.toString()));
        bereitsHinzugefuegteZutaten.getChildren().add(hBox);
    }

    public void delZutat(Zutat z) {
        naehrwerteMeal.setKcal(naehrwerteMeal.getKcal()-z.getNaehrwerte().getKcal());
        naehrwerteMeal.setKohlenhydrate(naehrwerteMeal.getKohlenhydrate()-z.getNaehrwerte().getKohlenhydrate());
        naehrwerteMeal.setProtein(naehrwerteMeal.getProtein()-z.getNaehrwerte().getProtein());
        naehrwerteMeal.setFett(naehrwerteMeal.getFett()-z.getNaehrwerte().getFett());
        zutaten.remove(z);
    }

    //_________________________getter und setter ________________________________________


    public String getName() {
        return name;
    }

    public String getMealString() {
        return name + "\n\t" + zutaten;
    }

    public ArrayList<Zutat> getZutaten() {
        return zutaten;
    }

    @Override
    public String toString() {
        return name +
                ": " + zutaten + "\n";
    }
}
