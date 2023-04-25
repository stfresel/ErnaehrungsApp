package com.example.fitnessapp;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Date;

public class Tagebuch {
    private ArrayList<Tag> tage = new ArrayList<>();
    private VBox tagebuchVbox;

    private TabPane tabPane;



    public void addTag(Tag t) {
        tage.add(t);
    }
    public void loadTagebuchScene() {
        tabPane = new TabPane();
        Tab heute = new Tab("Heute", loadHeute());
        Tab vergangeneTage = new Tab("Vergangenheit", loadVergangeneTage());
        heute.setClosable(false);
        vergangeneTage.setClosable(false);
        tabPane.getTabs().addAll(heute, vergangeneTage);
        Main.stage.setScene(new Scene(tabPane, Main.pane.getPrefWidth(), Main.pane.getPrefHeight()));
    }

    private ScrollPane loadHeute() {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPrefHeight(Main.pane.getPrefHeight());
        vBox.setPrefWidth(Main.pane.getPrefWidth());
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vBox);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        vBox.getChildren().add(new Label("Heute " + tage.get(tage.size()-1).getDateString()));
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

    private void showTag(int i) {
        Group group = new Group();
        group.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tage.get(i).loadDayScene();
            }
        });
        group.getChildren().add(new Label(tage.get(i).getDateString()));
        tagebuchVbox.getChildren().add(group);
    }


}
