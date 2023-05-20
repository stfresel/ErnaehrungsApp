package com.example.fitnessapp;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * <h2>Klasse: Tag</h2>
 * Die Klasse beinhaltet das Datum, die Mahlzeiten und die gesamten Nährwerte eines Tages.
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
     * <h3>Mahlzeit hinzufügen</h3>
     * Die Methode fügt die Mahlzeit zum Tag hinzu. Dabei werden die Nährwerte der einzelnen Zutaten zu den täglichen
     * Nährwerten hinzugerechnet.
     */
    public void addMeal(Meal meal){
        meals.add(meal);

        System.out.println("adddddddddddddddddd meal");
        // Nährwerte zum Tag hinzufügen
        for (int i = 0; i < meal.getZutaten().size(); i++) {//123456789
            Naehrwerte temp = meal.getZutaten().get(i).getNaehrwerte();
            insgesamteNaehrwerte.setKcal(insgesamteNaehrwerte.getKcal() + temp.getKcal());
            insgesamteNaehrwerte.setKohlenhydrate(insgesamteNaehrwerte.getKohlenhydrate() + temp.getKohlenhydrate());
            insgesamteNaehrwerte.setProtein(insgesamteNaehrwerte.getProtein() + temp.getProtein());
            insgesamteNaehrwerte.setFette(insgesamteNaehrwerte.getFette() + temp.getFette());
        }
    }

    public void removeMeal(String mealName){
        for (int i = 0; i < meals.size(); i++) {
            if (Objects.equals(mealName, meals.get(i).getName())) {
                meals.remove(i);
                break;
            }
        }
    }

    /**
     * Ladet alle die Mahlzeiten, die an einem Tag eingetragen wurden, in ein Pane.
     *<p>
     *
     * @return Gibt ein ScrollPane mit allen gegessenen Mahlzeiten.
     */
    public VBox ladeDetailansichtTag() {
        System.out.println("detailansichhhhhhhht----------");
        System.out.println(date);
        VBox vBox = new VBox();
        //vBox.setPrefSize(Main.stage.getScene().getWidth(), Main.stage.getScene().getHeight());

        vBox.getChildren().add(new Label("Nährwerte: "));
        vBox.getChildren().add(new Label("Kalorien: " + insgesamteNaehrwerte.getKcal() + " / " + Controller.getNaehrwerte().getKcal()));
        vBox.getChildren().add(new Label("Kohlenhydrate: " + insgesamteNaehrwerte.getKohlenhydrate() + " / " + Controller.getNaehrwerte().getKcal()));
        vBox.getChildren().add(new Label("Proteine: " + insgesamteNaehrwerte.getProtein() + " / " + Controller.getNaehrwerte().getProtein()));
        vBox.getChildren().add(new Label("Fette: " + insgesamteNaehrwerte.getFette() + " / " + Controller.getNaehrwerte().getFette()));

        if (date.equals(LocalDate.now())){
            vBox.getChildren().add(new Label("Mahlzeiten von heute: " + date));
        }else{
            vBox.getChildren().add(new Label("Mahlzeiten vom " + date));
        }

        for (Meal meal : meals) {
            vBox.getChildren().addAll(new Label(meal.getName()));
        }

        return vBox;
    }


    //------------------------------ getter und setter ____________________________________


    /*
    public String getDateString() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }

     */

    public LocalDate getDate() {
        return date;
    }

    public String getMealsString() {
        return meals.toString();
    }

    public Naehrwerte getInsgesamteNaehrwerte() {
        return insgesamteNaehrwerte;
    }
}
