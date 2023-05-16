package com.example.fitnessapp;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;


public class Tagebuch implements Serializable {
    private final ArrayList<Tag> tage = new ArrayList<>();
    private transient VBox tagebuchVbox;

    private transient TabPane tabPane;



    public void addTag(Tag tag) {
        tage.add(tag);
    }

    /**
     * Ladet die Start-Scene des Tagebuches.
     * Im Tagebuch gibt es zwei Tabs (Heute und Vergangenheit) wo man die jeweiligen Einträge sehen kann.
     *
     * @return Es wird ein TabPane mit der Ansicht des Tagebuches zurückgegeben.
     */
    public TabPane loadTagebuch() {
        tabPane = new TabPane();
        Tab heute = new Tab("Heute", loadHeute());
        Tab vergangeneTage = new Tab("Vergangenheit", loadVergangeneTage());
        heute.setClosable(false);
        vergangeneTage.setClosable(false);
        tabPane.getTabs().addAll(heute, vergangeneTage);
        return tabPane;
    }

    /**
     * Die Funktion ladet den Tab heute, welcher in der Scene des Tagesbuches ist.
     * @return Gibt das ScrollPane zurück, welches als Node beim Tab heute verwendet wird.
     */
    private ScrollPane loadHeute() {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(Main.stage.getScene().getWidth(), Main.stage.getScene().getHeight());
        scrollPane.setContent(vBox);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        vBox.getChildren().add(new Label("Heute " + tage.get(tage.size()-1).getDate()));
        vBox.getChildren().add(new Label(tage.get(tage.size()-1).getMealsString()));

        Button addMealBtn = new Button("neue Mahlzeit");
        addMealBtn.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("add meal");
                Meal meal = new Meal();
                meal.loadMealScene();
                tage.get(tage.size()-1).addMeal(meal);
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
        for (int i = tage.size()-2; i >= 0; i--) {      // tag.size -1 ist immer der heutige Tag
            showTag(i);
        }
        tagebuchVbox.setPrefSize(Main.stage.getScene().getWidth(), Main.stage.getScene().getHeight());
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(tagebuchVbox);

        // Vertikale ScrollBar wird immer angezeigt
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        // Horizontale ScrollBar wird nur angezeigt, wenn sie gebraucht wird
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        return scrollPane;
    }

    /**
     * Die Funktion erstellt für einen Tag i einen EventHandler, welcher beim Klicken auf die detailansicht des jeweiligen Tages springt.
     * @param index Der Index entspricht der Stelle, in der ArrayList tage. Die Funktion wird in einer Schleife aufgerufen.
     */
    private void showTag(int index) {
        Group group = new Group();
        group.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Tab tab = new Tab(tage.get(index).getDate().toString(), tage.get(index).ladeDetailansichtTag());
                tabPane.getTabs().add(tab);
                //tage.get(index).ladeDetailansichtTag();
                SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                selectionModel.select(tab); //select by object

            }
        });
        group.getChildren().add(new Label(tage.get(index).getDate().toString()));
        tagebuchVbox.getChildren().add(group);
    }

    //----------getter---------------
    public LocalDate getLastDay(){
        return tage.get(tage.size()-1).getDate();
    }
    public int getAnzahlTage(){
        return tage.size();
    }

    public Tag getTag(int index) {
        return tage.get(index);
    }

}
