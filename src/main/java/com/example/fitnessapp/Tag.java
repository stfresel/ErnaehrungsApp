package com.example.fitnessapp;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
        TabPane tagTabPane = new TabPane();
        Scene tabScene = new Scene(tagTabPane);
        tagTabPane.setPrefSize(Main.stage.getScene().getWidth(), Main.stage.getScene().getHeight());

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vbox);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        vbox.getChildren().add(new Label("Mahlzeiten vom " + date));

        Group group = new Group();
        for (Meal meal : meals) {
            group.getChildren().addAll(new Label(meal.getName()));
        }
        vbox.getChildren().add(group);

        Tab gerichte = new Tab("Gerichte", scrollPane);
        Tab naehrwerte = new Tab("Nährwerte");
        // <<<<<<<<<<<<<<<<<<<<<<< no es Tab Naehrwerte Mochn

        gerichte.setClosable(false);
        naehrwerte.setClosable(false);

        tagTabPane.getTabs().addAll(gerichte, naehrwerte);
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
