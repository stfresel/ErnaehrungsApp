package com.example.fitnessapp;

import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Objects;

public class Benutzer implements Serializable{

    //--------------------------------------------
    //Attribute für Benutzer und Passwort
    private String benutzername;
    private String passwort;
    private Home home = new Home();
    //------------------------------


    //------------------getter und setter--------------------------

    public Home getHome() {
        return home;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setBenutzername(String name) {
        benutzername = name;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public void setHome(Home home) {
        this.home = home;
    }
}