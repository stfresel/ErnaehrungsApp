package com.example.fitnessapp;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Beinhaltet alle Tage, an denen ein Eintrag getätigt wurde.
 * Mit der Klasse Tagebuch hat man einen Überblick über alle vergangenen Tage, an denen einen Eintrag erstellt wurde.
 * Zudem können auch neue Einträge erstellen werden
 */
public class Tagebuch implements Serializable {
    /**
     * Beinhaltet alle vergangenen Tage und den heutigen Tag, samt Einträge.
     */
    private final ArrayList<Tag> tage = new ArrayList<>();
    private transient VBox tagebuchVbox;

    private transient TabPane tabPane;

    /**
     * Fügt einen Tag zum Tagebuch hinzu.
     * @param tag
     */
    public void addTag(Tag tag) {
        tage.add(tag);
    }

    /**
     * Ladet die Hauptansicht mit den zwei Tabs.
     * Im Tagebuch gibt es zwei Tabs (Heute und Vergangenheit) wo man informationen zum jeweiligen Tag erhällt.
     *
     */
    public Pane loadTagebuch() {
        tabPane = new TabPane();
        tabPane.setPrefHeight(Main.stage.getScene().getHeight()/1.5);
        Pane pane = new Pane(tabPane);
        Tab heute = new Tab("Heute", loadHeute());
        Tab vergangeneTage = new Tab("Vergangenheit", loadVergangeneTage());

        heute.setId("textfield-konto");
        vergangeneTage.setId("textfield-konto");
        tabPane.setId("tabpane");

        heute.setClosable(false);
        vergangeneTage.setClosable(false);
        tabPane.getTabs().addAll(heute, vergangeneTage);
        return pane;
    }

    /**
     * Die Methode ladet den Tab <code>heute</code>, welcher in der Hauptansicht des Tagesbuches zu sehen ist.
     * @return Gibt das ScrollPane zurück, welches beim in der Hauptansicht als Tab <code>Heute</code> angezeigt wird.
     */
    private ScrollPane loadHeute() {
        VBox vBox = new VBox(tage.get(tage.size()-1).ladeDetailansichtTag());
        Button addMealBtn = new Button("neue Mahlzeit");
        addMealBtn.setId("textfield-konto");
        addMealBtn.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Meal meal = new Meal();
                meal.loadMealScene(tage.get(tage.size()-1));
            }
        });
        vBox.getChildren().add(addMealBtn);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vBox);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPrefSize(Main.stage.getScene().getWidth(), Main.stage.getScene().getWidth());

        return scrollPane;
    }

    /**
     * Die Methode ladet alle vergangenen Tage in eine Szene.
     * @return Gibt das ScrollPane zurück, welches beim in der Hauptansicht als Tab <code>Vergangenheit</code> angezeigt wird.
     */
    public ScrollPane loadVergangeneTage(){
        tagebuchVbox = new VBox();
        tagebuchVbox.setSpacing(10);
        Text message = new Text("Keine vergangenen Tage gespeichert.");
        if (tage.size() < 2){
            message.setVisible(true);
            tagebuchVbox.getChildren().add(message);
        }else {
            message.setVisible(false);
            for (int i = tage.size()-2; i >= 0; i--) {      // tag.size -1 ist immer der heutige Tag
                showTag(i);
            }
        }

        tagebuchVbox.setPrefSize(Main.stage.getScene().getWidth(), Main.stage.getScene().getHeight());
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setId("vergangeneTage");
        scrollPane.setContent(tagebuchVbox);


        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        // Horizontale ScrollBar wird nur angezeigt, wenn sie gebraucht wird
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        return scrollPane;
    }

    /**
     * Die Methode weisst einen Tag einen EventHandler zu, welcher beim Klicken auf das Label, in die detailansicht des jeweiligen Tages springt.
     * @param index Entspricht der Stelle in der ArrayList <code>tage</code>.
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
                tab.setId("textfield-konto");
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
     * Die Methode gibt das neuste Datum (letzte Stelle im Array) zurück.
     * @return Das aktuellste Datum im Array wird zurückgegeben
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

    /**
     * Die Methode gibt einen den Tag mit dem Index <code>index</code> zurück.
     *
     * @param index Gibt die Stelle im Array an, die zurückgegeben werden soll.
     * @return Gibt den Tag zurück.
     */
    public Tag getTag(int index) {
        return tage.get(index);
    }

}
