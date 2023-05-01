package com.example.fitnessapp;



public class Controller {
    Benutzer benutzer;
    public Controller(Benutzer b) {
        benutzer = b;
    }

    public void start() {
        benutzer.initialize();
    }

}