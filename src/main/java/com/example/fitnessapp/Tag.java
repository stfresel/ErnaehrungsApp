package com.example.fitnessapp;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Ermöglicht es die Mahlzeiten Tagen zuzuweisen, dadurch erhält man einen guten Überblick über den Verlauf.
 */
public class Tag implements Serializable {

    /**
     * Gibt das Datum des Tages an.
     */
    private final LocalDate date;

    /**
     * Gibt die Mahlzeiten, die an diesem Tag eingetragen wurden an.
     */
    private final ArrayList<Meal> meals = new ArrayList<>();

    /**
     * Gibt die gesamten Nährwerte, die an diesem Tag eingetragen wurden, an.
     */
    private Naehrwerte insgesamteNaehrwerte = new Naehrwerte(0,0,0,0);

    public Tag(LocalDate date) {
        this.date = date;
    }

    /**
     * Mahlzeit zu einem Tag hinzufügen.
     * Die Methode fügt die Mahlzeit zum Tag hinzu. Dabei werden die Nährwerte der einzelnen Zutaten zu den täglichen
     * Nährwerten hinzugerechnet.
     */
    public void addMeal(Meal meal){
        meals.add(meal);

        // Nährwerte zum Tag hinzufügen
        for (int i = 0; i < meal.getZutaten().size(); i++) {//123456789
            Naehrwerte temp = meal.getZutaten().get(i).getNaehrwerte();
            insgesamteNaehrwerte.setKcal(insgesamteNaehrwerte.getKcal() + temp.getKcal());
            insgesamteNaehrwerte.setKohlenhydrate(insgesamteNaehrwerte.getKohlenhydrate() + temp.getKohlenhydrate());
            insgesamteNaehrwerte.setProtein(insgesamteNaehrwerte.getProtein() + temp.getProtein());
            insgesamteNaehrwerte.setFette(insgesamteNaehrwerte.getFette() + temp.getFette());
        }
    }

    /**
     * Ladet alle Mahlzeiten, die an einem Tag eingetragen wurden, in ein Pane.
     *
     * @return Gibt ein ScrollPane mit allen gegessenen Mahlzeiten.
     */
    public VBox ladeDetailansichtTag() {
        System.out.println(date);
        VBox vBox = new VBox();
        //vBox.setPrefSize(Main.stage.getScene().getWidth(), Main.stage.getScene().getHeight());


        Label naehrwerteLabel = new Label("Nährwerte: ");
        Label kalorienLabel = new Label("Kalorien: " + insgesamteNaehrwerte.getKcal() + " / " + Controller.getNaehrwerte().getKcal());
        Label kohlenLabel = new Label("Kohlenhydrate: " + insgesamteNaehrwerte.getKohlenhydrate() + " / " + Controller.getNaehrwerte().getKohlenhydrate());
        Label proteinLabel = new Label("Proteine: " + insgesamteNaehrwerte.getProtein() + " / " + Controller.getNaehrwerte().getProtein());
        Label fetteLabel = new Label("Fette: " + insgesamteNaehrwerte.getFette() + " / " + Controller.getNaehrwerte().getFette());

        Label mahlheute = new Label("Mahlzeiten von heute: " + date);

        naehrwerteLabel.setId("strong");
        kalorienLabel.setId("text");
        kohlenLabel.setId("text");
        proteinLabel.setId("text");
        fetteLabel.setId("text");

        mahlheute.setId("strong");

        vBox.getChildren().add(naehrwerteLabel);
        vBox.getChildren().add(kalorienLabel);
        vBox.getChildren().add(kohlenLabel);
        vBox.getChildren().add(proteinLabel);
        vBox.getChildren().add(fetteLabel);

        if (date.equals(LocalDate.now())){
            vBox.getChildren().add(mahlheute);
        }else{
            Label mahlLabel = new Label("Mahlzeiten vom " + date);
            mahlLabel.setId("strong");
            vBox.getChildren().add(mahlLabel);
        }

        for (Meal meal : meals) {
            vBox.getChildren().addAll(new Label(meal.getName()));
        }

        return vBox;
    }


    //------------------------------ getter und setter ____________________________________


    /**
     * Gibt das Datum des Tages zurück.
     * @return LocalDate vom Tag
     */
    public LocalDate getDate() {
        return date;
    }


    /**
     * Gibt die insgesamt an einem Tag gegessene Nährwerte zurück.
     * @return insgesamt gegessene Nährwerte vom Tag
     */
    public Naehrwerte getInsgesamteNaehrwerte() {
        return insgesamteNaehrwerte;
    }
}
