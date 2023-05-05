package com.example.fitnessapp;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Tag implements Serializable {
    private final ArrayList<Meal> meals = new ArrayList<>();
    private final LocalDate date;
    private Naehrwerte insgesamteNaehrwerte = new Naehrwerte(0,0,0,0);

    public Tag(LocalDate date) {
        this.date = date;
    }

    public void addMeal(Meal meal){
        meals.add(meal);

        // Nährwerte zum Tag hinzufügen
        for (int i = 0; i < meal.getZutaten().size(); i++) {
            Naehrwerte temp = meal.getZutaten().get(i).getNaehrwerte();
            insgesamteNaehrwerte.setKcal(insgesamteNaehrwerte.getKcal() + temp.getKcal());
            insgesamteNaehrwerte.setKohlenhydrate(insgesamteNaehrwerte.getKohlenhydrate() + temp.getKohlenhydrate());
            insgesamteNaehrwerte.setProtein(insgesamteNaehrwerte.getProtein() + temp.getProtein());
            insgesamteNaehrwerte.setFett(insgesamteNaehrwerte.getFett() + temp.getFett());
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
     * Ladet sie detailansicht eines Tages.
     * Dort gibt es zwei Tabs: Gerichte und Nährwerte.
     */
    public void ladeDetailansichtTag() {
        System.out.println(date);
        ScrollPane scrollPane = new ScrollPane();

        VBox vBox = new VBox();
        vBox.setSpacing(10);

        Scene tabScene = new Scene(scrollPane);
        vBox.setPrefSize(Main.stage.getScene().getWidth(), Main.stage.getScene().getHeight());

        scrollPane.setContent(vBox);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        vBox.getChildren().add(new Label("Mahlzeiten vom " + date));

        Group group = new Group();
        for (Meal meal : meals) {
            group.getChildren().addAll(new Label(meal.getName()));
        }
        Button zurueckBtn = new Button("zurück");
        zurueckBtn.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Main.benutzer.getHome().startHome();
            }
        });

        vBox.getChildren().add(group);
        vBox.getChildren().add(zurueckBtn);

        Main.stage.setScene(tabScene);
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
