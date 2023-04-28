package com.example.fitnessapp;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.Serializable;
import java.util.ArrayList;


public class Tagebuch implements Serializable {
    private final ArrayList<Tag> tage = new ArrayList<>();
    private VBox tagebuchVbox;



    public void addTag(Tag t) {
        tage.add(t);
    }

    /**
     * Ladet die Start-Scene des Tagebuches.
     * Im Tagebuch gibt es zwei Tabs (Heute und Vergangenheit) wo man die jeweiligen Einträge sehen kann.
     */
    public void loadTagebuchScene() {
        System.out.println("LTTTTT");
        TabPane tabPane = new TabPane();
        Tab heute = new Tab("Heute", loadHeute());
        Tab vergangeneTage = new Tab("Vergangenheit", loadVergangeneTage());
        System.out.println("mm");
        heute.setClosable(false);
        vergangeneTage.setClosable(false);
        tabPane.getTabs().addAll(heute, vergangeneTage);
        System.out.println("..");
        Main.s = new Scene(tabPane, Main.pane.getPrefWidth(), Main.pane.getPrefHeight());
        System.out.println("x");

    }

    /**
     * Die Funktion ladet den Tab heute, welcher in der Scene des Tagesbuches ist.
     * @return Gibt das ScrollPane zurück, welches als Node beim Tab heute verwendet wird.
     */
    private ScrollPane loadHeute() {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPrefHeight(Main.pane.getPrefHeight());
        vBox.setPrefWidth(Main.pane.getPrefWidth());
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vBox);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        vBox.getChildren().add(new Label("Heute " + tage.get(tage.size()-1).getDate()));
        vBox.getChildren().add(new Label(tage.get(tage.size()-1).getMealsString()));

        Button addMealBtn = new Button("neue Mahlzeit");
        addMealBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("add meal");
                Meal meal = new Meal();
                meal.loadMealScene();
                tage.get(tage.size()-1).addMeal(meal);

                //-------------------------------------------------------
                // meal scene laden
            }
        });
        vBox.getChildren().add(addMealBtn);
        return scrollPane;
    }

    /**
     * Die Funktion git ein ScrollPane zurück, auf welchem alle Tage (außer der heutige) zur Übersicht dargestellt werden.
     * @return Gibt ebenfalls ein ScrollPane zurück, welches man bei dem Tab Vergangenheit als Node verwenden.
     */
    public ScrollPane loadVergangeneTage(){
        tagebuchVbox = new VBox();
        tagebuchVbox.setSpacing(10);
        for (int i = tage.size()-2; i >= 0; i--) {      // weil tag.size -1 ist immer der heutige Tag
            showTag(i);
        }
        tagebuchVbox.setPrefHeight(Main.pane.getPrefHeight());
        tagebuchVbox.setPrefWidth(Main.pane.getPrefWidth());
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(tagebuchVbox);

        // Always show vertical scroll bar
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        // Horizontal scroll bar is only displayed when needed
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        return scrollPane;
        //Main.stage.setScene(tagebuchScene);
    }

    /**
     * Die Funktion erstellt für einen Tag i einen EventHandler, welcher beim Klicken auf die detailansicht des jeweiligen Tages springt.
     * @param i Der Parameter i steht für den Index. Die Funktion wird in einer Schleife aufgerufen.
     */
    private void showTag(int i) {
        Group group = new Group();
        group.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tage.get(i).ladeDetailansichtTag();
            }
        });
        group.getChildren().add(new Label(tage.get(i).getDate().toString()));
        tagebuchVbox.getChildren().add(group);
    }


}
