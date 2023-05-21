package com.example.fitnessapp;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

/**
 * Ist für das Design des Hintergrundes zuständig.
 */
public class UIstart {

    /**
     * Ist das Hauptrechteck, welches die Form ausmacht.
     */
    Rectangle mainRec;

    /**
     * Ist das Rechteck, das zum Ergänzen der Kreise genutzt wird
     * und dafür sorgt, dass die Form wie ein gerundetes Rechteck
     * (nur auf zwei seiten gerundet) wirken lässt
     */
    Rectangle sideRec;

    /**
     * Ist für die obere Rundung auf der linken Seite zuständig.
     */
    Circle upperCircle;
    /**
     * Ist für die untere Rundung auf der linken Seite zuständig.
     */
    Circle lowerCircle;

    /**
     * Gibt den Radius der Kreise an.
     */
    double angleRatio;

    /**
     * Im Konstruktor werden die Positionen das erste Mal angepasst.
     * Dabei sind keine Übergabeparameter vonnöten, da man später mit den beiden Methoden
     * größe sowohl als auch die Position leicht anpassen kann
     */
    public UIstart() {
        /*
         * Setzen der Farbe
         */
        Paint color = Paint.valueOf("#f5f5f5");

        /*
         * Initialisieren der benötigten Formen
         */
        this.mainRec = new Rectangle(0, 0, color);
        this.sideRec = new Rectangle(0, 0, color);
        this.upperCircle = new Circle(0, color);
        this.lowerCircle = new Circle(0, color);

        this.mainRec.setLayoutX(0);
        this.mainRec.setLayoutY(0);

        this.sideRec.setLayoutX(0);
        this.sideRec.setLayoutY(0);

        this.upperCircle.setLayoutX(0);
        this.upperCircle.setLayoutY(0);

        this.lowerCircle.setLayoutX(0);
        this.lowerCircle.setLayoutY(0);
        this.angleRatio = 0.5;
    }

    /**
     * Die Methode setsize verändert die größe der Form beliebig und passt die position der Formen an
     * @param x ist der Parameter, der die Breite der Form angibt
     * @param y ist der Parameter, der die Höhe der Form angibt
     */
    public void setsize(double x, double y){
        double oldposx = upperCircle.getLayoutX();
        double oldposy = upperCircle.getLayoutY();

        upperCircle.setRadius((y*angleRatio)/2);

        lowerCircle.setRadius((y*angleRatio)/2);

        mainRec.setWidth(x-upperCircle.getRadius());
        mainRec.setHeight(y);

        sideRec.setWidth(upperCircle.getRadius()*2);
        sideRec.setHeight(mainRec.getHeight()- upperCircle.getRadius()*2);

        /*
         * Die Position muss erneut angepasst werden, da die verschiedenen Formen erst nur die neue größe angenommen haben,
         * sprich nun eine falsche Anordnung bilden
         */
        setpos(oldposx, oldposy);
    }

    /**
     * Die Methode setpos ändert die position der Form
     * @param x Gibt die x-Koordinate an
     * @param y Gibt die y-Koordinate an
     */
    public void setpos(double x, double y){
        upperCircle.setLayoutX(x+upperCircle.getRadius());
        upperCircle.setLayoutY(y+upperCircle.getRadius());
        mainRec.setLayoutX(x+upperCircle.getRadius());
        mainRec.setLayoutY(y);

        sideRec.setLayoutX(x);
        sideRec.setLayoutY(y+upperCircle.getRadius());

        lowerCircle.setLayoutX(x + upperCircle.getRadius());
        lowerCircle.setLayoutY(mainRec.getLayoutY()+mainRec.getHeight() - lowerCircle.getRadius());
    }

    /**
     * Diese Methode dient zum einfachen Hinzufügen der Form auf ein übergebenes Pane.
     * @param pane Pane, auf welches die Formen hinzugefügt werden
     */
    public void display(Pane pane){
        pane.getChildren().add(upperCircle);
        pane.getChildren().add(lowerCircle);

        pane.getChildren().add(mainRec);
        pane.getChildren().add(sideRec);
    }
}
