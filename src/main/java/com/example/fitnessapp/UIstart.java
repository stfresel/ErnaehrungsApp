package com.example.fitnessapp;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class UIstart {
    Rectangle mainRec;
    Rectangle sideRec;
    Circle upperCircle;
    Circle lowerCircle;
    double angleRatio;


    public UIstart() {
        Paint color = Paint.valueOf("#f5f5f5");

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

    public void setsize(double x, double y){
        double oldposx = upperCircle.getLayoutX();
        double oldposy = upperCircle.getLayoutY();

        upperCircle.setRadius((y*angleRatio)/2);

        lowerCircle.setRadius((y*angleRatio)/2);

        //mainRec.setWidth(x-x/angleRatio);
        //mainRec.setHeight(y-y/angleRatio);
        mainRec.setWidth(x-upperCircle.getRadius());
        mainRec.setHeight(y);

        sideRec.setWidth(upperCircle.getRadius()*2);
        sideRec.setHeight(mainRec.getHeight()- upperCircle.getRadius()*2);

        setpos(oldposx, oldposy);
    }

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

    public void display(Pane pane){
        pane.getChildren().add(upperCircle);
        pane.getChildren().add(lowerCircle);

        pane.getChildren().add(mainRec);
        pane.getChildren().add(sideRec);
    }
}
