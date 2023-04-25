package com.example.fitnessapp;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Tag {
    private ArrayList<Meal> meals = new ArrayList<>();
    private Date date;
    private Naehrwerte insgesamteNaehrwerte = new Naehrwerte(0,0,0,0);

    public Tag(Date date) {
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

    public void loadDayScene() {
        System.out.println(getDateString());
        TabPane tagTabPane = new TabPane();
        Scene tagScene = new Scene(tagTabPane);
        tagTabPane.setPrefWidth(Main.pane.getPrefWidth());
        tagTabPane.setPrefHeight(Main.pane.getPrefHeight());

        // Tab Gerichte
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vbox);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        vbox.getChildren().add(new Label("Mahlzeiten vom " + getDateString()));
        Group group = new Group();
        for (int i = 0; i < meals.size(); i++) {
            group.getChildren().addAll(new Label(meals.get(i).getName()));
        }
        vbox.getChildren().add(group);

        Tab gerichte = new Tab("Gerichte", scrollPane);
        Tab naehrwerte = new Tab("Nährwerte");

        gerichte.setClosable(false);
        naehrwerte.setClosable(false);

        tagTabPane.getTabs().addAll(gerichte, naehrwerte);
        Main.stage.setScene(tagScene);
    }


    //------------------------------ getter und setter ____________________________________


    public Date getDate() {
        return date;
    }

    public String getDateString() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }

    public String getMealsString() {
        return meals.toString();
    }
}
