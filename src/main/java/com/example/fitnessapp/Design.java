package com.example.fitnessapp;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Design {
    public Rectangle createRec(double posx, double posy, double height, double width, String color){
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(Paint.valueOf(color));
        rectangle.setX(posx);
        rectangle.setY(posy);
        rectangle.setHeight(height);
        rectangle.setWidth(width);

        return rectangle;
    }
    public Circle createCircle(double posx, double posy, double radius, String color){
        Circle circle = new Circle();
        circle.setFill(Paint.valueOf(color));
        circle.setCenterX(posx);
        circle.setCenterY(posy);
        circle.setRadius(radius);
        return circle;
    }

}
