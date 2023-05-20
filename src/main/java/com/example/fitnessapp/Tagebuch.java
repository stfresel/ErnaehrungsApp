package com.example.fitnessapp;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * <h2>Tagebuch</h2>
 * In der Klasse werden alle vergangenen Tage abgespeichert.
 * Mit der Klasse Tagebuch hat man einen Überblick über alle vergangenen Tage, an denen einen Eintrag erstellt wurde.
 * Zudem können auch neue Einträge erstellen werden.
 * <p>
 *
 */
public class Tagebuch implements Serializable {
    /**
     * Beinhaltet zusätzlich zum heutigen Tag auch alle vergangenen Tage.
     */
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
     * Die Methode ladet den Tab heute, welcher in der Scene des Tagesbuches ist.
     * @return Gibt das ScrollPane zurück, welches beim <code>tabPane</code> als Tab <code>heute</code> verwendet wird.
     */
    private ScrollPane loadHeute() {
        /*
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(Main.stage.getScene().getWidth(), Main.stage.getScene().getHeight());
        scrollPane.setContent(vBox);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        vBox.getChildren().add(new Label("Heute " + tage.get(tage.size()-1).getDate()));

        vBox.getChildren().add(new Label("Nährwerte:"));
        vBox.getChildren().add(new Label("Kalorien: " + Controller.getNaehrwerte().getKcal()))

        vBox.getChildren().add(new Label(tage.get(tage.size()-1).getMealsString()));

         */
        VBox vBox = new VBox(tage.get(tage.size()-1).ladeDetailansichtTag());
        Button addMealBtn = new Button("neue Mahlzeit");
        addMealBtn.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("add meal");
                Meal meal = new Meal();
                meal.loadMealScene(tage.get(tage.size()-1));
                //tage.get(tage.size()-1).addMeal(meal); //123456789
            }
        });
        vBox.getChildren().add(addMealBtn);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vBox);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        return scrollPane;
    }

    /**
     * Die Methode git ein ScrollPane zurück, auf welchem alle Tage (außer der heutige) zur Übersicht dargestellt werden.
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
     * Die Methode erstellt für einen Tag index einen EventHandler, welcher beim Klicken auf die detailansicht des jeweiligen Tages springt.
     * @param index <code>index</code> entspricht der Stelle in der ArrayList <code>tage</code>. Die Methode wird in einer Schleife aufgerufen.
     */
    private void showTag(int index) {
        Group group = new Group();
        group.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                boolean containsTab = false;
                VBox vBox = tage.get(index).ladeDetailansichtTag();
                ScrollPane scrollPane = new ScrollPane();
                scrollPane.setContent(vBox);
                scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

                Tab tab = new Tab(tage.get(index).getDate().toString(), scrollPane);
                SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                for (int i = 0; i < tabPane.getTabs().size(); i++) {
                    if (Objects.equals(tabPane.getTabs().get(i).getText(), tage.get(index).getDate().toString())){
                        selectionModel.select(i);
                        containsTab = true;
                        break;
                    }
                }

                if (!containsTab){
                    tabPane.getTabs().add(tab);
                    selectionModel.select(tab);
                }

            }
        });
        group.getChildren().add(new Label(tage.get(index).getDate().toString()));
        tagebuchVbox.getChildren().add(group);
    }

    //----------getter---------------

    /**
     * Die Methode gibt das Datum, der letzte Stelle der ArrayList <code>tage</code> zurück.
     * @return Gibt das Datum des letzten Tages im Tagebuch zurück.
     */
    public LocalDate getLastDay(){
        return tage.get(tage.size()-1).getDate();
    }

    /**
     * Die Methode gibt die Länge der ArrayList <code>tage</code> zurück.
     * @return Gibt die Anzahl der Tage im Tagebuch zurück.
     */
    public int getAnzahlTage(){
        return tage.size();
    }

    public Tag getTag(int index) {
        return tage.get(index);
    }

}
