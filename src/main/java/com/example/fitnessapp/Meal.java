package com.example.fitnessapp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Erlaubt es mehrere Zutaten zu einem Gericht zusammen zu führen.
 */
public class Meal implements Serializable{

    /**
     * Gibt den Namen der Mahlzeit an.
     */
    private String name;

    /**
     * Gibt die Zutaten der Mahlzeit an.
     */
    private final ArrayList<Zutat> zutaten = new ArrayList<>();

    /**
     * Gibt die Nährwerte der gesamten Mahlzeit an.
     */
    private Naehrwerte naehrwerteMeal = new Naehrwerte(0,0,0,0);


    private transient VBox zutatenPane;

    private final transient VBox bereitsHinzugefuegteZutaten = new VBox();
    private transient Text fehlermeldung = new Text();

    /**
     * Die Methode fügt die UI-Komponenten in ein Pane, welches anschließend angezeigt wird.
     */
    public void loadMealScene(Tag tag) {
        GridPane gridPane = new GridPane();

        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPrefSize(Main.stage.getScene().getWidth(), Main.stage.getScene().getHeight());

        //background
        InputStream stream;
        try {
            stream = new FileInputStream("src/main/resources/com/example/fitnessapp/backgroundGreen.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image background = new Image(stream);

        gridPane.setBackground(new Background(new BackgroundImage(background, null, null, null, new BackgroundSize(100, 100, true, true, false, true))));

        Scene scene = new Scene(gridPane);
        TextField nameTextField = new TextField();
        Label text = new Label("Name des Gerichtes: ");
        text.setId("strong");
        nameTextField.setId("textfield-konto");



        gridPane.addRow(1, text, nameTextField);
        Button addZutatBtn = new Button("weiter");
        addZutatBtn.setId("textfield-konto");
        addZutatBtn.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //---tempName = nameTextField.getText();
                name = nameTextField.getText();

                // evt abfrage??? mit fehlertext

                System.out.println("neue Zutat");
                loadZutatenScene(tag);
            }
        });

        // sie sollten immer die letzten elemente im gridPane sein
        gridPane.addRow(1, addZutatBtn);
        //gridPane.addRow(2, mealFertig);
        gridPane.setId("label-konto");
        Main.switchScene(scene);
        //Main.stage.setScene(scene);
    }

    /**
     * Fügt die UI-Komponenten zur <code>zutatenScene</code>, welche anschließend angezeigt wird.
     */
    public void loadZutatenScene(Tag tag){
        zutatenPane = new VBox();
        zutatenPane.setAlignment(Pos.CENTER);
        zutatenPane.setPrefSize(Main.stage.getScene().getWidth(), Main.stage.getScene().getHeight());
        GridPane zutatErstellen = new GridPane();
        GridPane loadZutate = new GridPane();
        zutatErstellen.setAlignment(Pos.CENTER);
        loadZutate.setAlignment(Pos.CENTER);

        CheckBox checkbox = new CheckBox("neue Zutat erstellen");
        checkbox.setSelected(true);
        //zutatenPane.setId("label-konto");

        //background
        InputStream stream;
        try {
            stream = new FileInputStream("src/main/resources/com/example/fitnessapp/backgroundGreen.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image background = new Image(stream);

        zutatenPane.setBackground(new Background(new BackgroundImage(background, null, null, null, new BackgroundSize(100, 100, true, true, false, true))));


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

        mengeGespeicherteZutat.setId("textfield-konto");
        comboBox.setId("textfield-konto");

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

        Label kalorienLabel = new Label("Kalorien: ");
        Label kolenLabel = new Label("Kolenhydrate: ");
        Label proteinLabel = new Label("Proteine: ");
        Label fettLabel = new Label("Fette: ");

        //design Css
        nameDerZutat.setId("strong");
        mengeGegessen.setId("strong");
        kalorienLabel.setId("strong");
        kolenLabel.setId("strong");
        proteinLabel.setId("strong");
        fettLabel.setId("strong");
        textFieldName.setId("textfield-konto");
        textFieldGegessen.setId("textfield-konto");
        kcalTextField.setId("textfield-konto");
        kolenhydrateTextField.setId("textfield-konto");
        proteineTextField.setId("textfield-konto");
        fetteTextField.setId("textfield-konto");
        checkbox.setId("strong");


        zutatErstellen.addRow(2, kalorienLabel, kcalTextField);
        zutatErstellen.addRow(3, kolenLabel, kolenhydrateTextField);
        zutatErstellen.addRow(4, proteinLabel, proteineTextField);
        zutatErstellen.addRow(5, fettLabel,fetteTextField);
        zutatErstellen.addRow(6, fehlermeldung);

        checkbox.selectedProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                System.out.println("change");
                zutaten.clear();
                if (checkbox.isSelected()){ // neue Zutat erstellen
                    //zutatenPane.getChildren().remove(loadZutate);
                    int x = zutatenPane.getChildren().indexOf(loadZutate);
                    zutatenPane.getChildren().set(x, zutatErstellen);

                } else {                    // Zutat aus Speicher holen
                    int x = zutatenPane.getChildren().indexOf(zutatErstellen);
                    zutatenPane.getChildren().set(x, loadZutate);
                }
            }
        });

        Button fertigBtn = new Button("Zutat Hinzufügen");
        fertigBtn.setPrefSize(180,30);
        fertigBtn.setId("textfield-konto");
        fertigBtn.setLayoutY(zutatenPane.getHeight()-100);

        // inuputs noch in Var speichern!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        fertigBtn.setOnMouseClicked(new EventHandler<>() {
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
                        comboBox.getItems().add(z.getName());
                        textFieldName.setText("");
                        textFieldGegessen.setText("");
                        kcalTextField.setText("");
                        fetteTextField.setText("");
                        proteineTextField.setText("");
                        kolenhydrateTextField.setText("");
                        addZutate2Meal(z);
                    }

                }
                //---------------------------------------------
            }
        });

        Button alle_zutaten_wurden_eingegeben = new Button("fertig");
        alle_zutaten_wurden_eingegeben.setId("textfield-konto");
        alle_zutaten_wurden_eingegeben.setPrefSize(180,30);

        alle_zutaten_wurden_eingegeben.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //123456789
                System.out.println("fertigggg");
                tag.addMeal(Meal.this);
                //name = nameTextField.getText();
                Controller.benutzer.getHome().startHome();
            }
        });
        zutatenPane.getChildren().add(0, bereitsHinzugefuegteZutaten);
        zutatenPane.getChildren().add(zutatenPane.getChildren().size(),fertigBtn);
        zutatenPane.getChildren().add(zutatenPane.getChildren().size(),alle_zutaten_wurden_eingegeben);



        Scene zutatenScene = new Scene(zutatenPane);
        //Main.stage.setScene(zutatenScene);
        Main.switchScene(zutatenScene);
    }

    /**
     * Diese Methode passt die Menge der Nährwerte an der neuen Menge an.
     *
     * @param alteZutat Dieser Parameter gibt die Zutat an, bei welcher man die Menge anpassen möchte.
     * @param neueMenge Dieser Parameter gibt die neue Menge an, an die die Nährwerte angepasst werden sollen.
     * @return Es wird eine neue Zutat zurückgegeben, mit den angepassten Nährwerten.
     */
    private Zutat calcNaehrwerteZutat(Zutat alteZutat, int neueMenge){
        double help = (double) neueMenge/ alteZutat.getMenge();
        int kcalNeu = (int) (alteZutat.getNaehrwerte().getKcal() * help);
        int carbsNeu = (int) (alteZutat.getNaehrwerte().getKohlenhydrate() * help);
        int proteinNeu = (int) (alteZutat.getNaehrwerte().getProtein() * help);
        int fettNeu = (int) (alteZutat.getNaehrwerte().getFette() * help);

        Naehrwerte neueNaehrwerte = new Naehrwerte(kcalNeu,fettNeu, carbsNeu, proteinNeu);

        return new Zutat(alteZutat.getName(), neueMenge, neueNaehrwerte);
    }


    /**
     * Die Methode fügt eine Zutat zur Mahlzeit hinzu. Dabei werden auch die Nährwerte der Mahlzeit angepasst.
     * <p>
     *
     * @param zutat Der Parameter gibt die Zutat an, welche zur Mahlzeit hinzugefügt werden soll.
     */
    private void addZutate2Meal(Zutat zutat) {
        zutaten.add(zutat);
        naehrwerteMeal.setKcal(naehrwerteMeal.getKcal() + zutat.getNaehrwerte().getKcal());
        naehrwerteMeal.setKohlenhydrate(naehrwerteMeal.getKohlenhydrate() + zutat.getNaehrwerte().getKohlenhydrate());
        naehrwerteMeal.setFette(naehrwerteMeal.getFette() + zutat.getNaehrwerte().getFette());
        naehrwerteMeal.setProtein(naehrwerteMeal.getProtein() + zutat.getNaehrwerte().getProtein());
        System.out.println("insgesamt NW" + naehrwerteMeal);
        HBox hBox = new HBox();
        Button delZutatBtn = new Button("-");
        delZutatBtn.setId("textfield-login");
        delZutatBtn.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("delete Zutat");
                System.out.println(zutaten);
                delZutat(zutat);
                bereitsHinzugefuegteZutaten.getChildren().remove(hBox);
                //hBox.getChildren().removeAll();     // achtung beim löschen--> evt alle zutaten in ein VBox tun
                System.out.println("a-" + zutaten);

            }
        });
        hBox.getChildren().add(delZutatBtn);
        Label zutatlabel = new Label(zutat.toString());
        zutatlabel.setId("strong");
        zutatlabel.setAlignment(Pos.CENTER);

        hBox.getChildren().add(zutatlabel);
        bereitsHinzugefuegteZutaten.getChildren().add(hBox);
    }

    /**
     * Die Methode löscht eine Zutat aus einer Mahlzeit.
     * Die Nährwerte der Mahlzeit werden auch angepasst.
     *
     * @param zutat Der Parameter gibt die Zutat an, welche gelöscht werden soll.
     */
    public void delZutat(Zutat zutat) {
        naehrwerteMeal.setKcal(naehrwerteMeal.getKcal()- zutat.getNaehrwerte().getKcal());
        naehrwerteMeal.setKohlenhydrate(naehrwerteMeal.getKohlenhydrate()- zutat.getNaehrwerte().getKohlenhydrate());
        naehrwerteMeal.setProtein(naehrwerteMeal.getProtein()- zutat.getNaehrwerte().getProtein());
        naehrwerteMeal.setFette(naehrwerteMeal.getFette()- zutat.getNaehrwerte().getFette());
        zutaten.remove(zutat);
    }

    //_________________________getter und setter ________________________________________


    public String getName() {
        return name;
    }

    public String getMealString() {
        return name + "\n\t" + zutaten;
    }

    /**
     * Gibt die ArrayList, in der die Zutaten der Mahlzeit stehen, zurück.
     * @return ArrayList der Zutaten
     */
    public ArrayList<Zutat> getZutaten() {
        return zutaten;
    }

    @Override
    public String toString() {
        return name +
                ": " + zutaten + "\n";
    }
}
