package com.example.fitnessapp;

/**
 * <h2>Benutzer Klasse</h2>
 * Die Klasse ermöglicht es verschiedene Benutzer-Accounts anzulegen. Somit werden die Daten der einzelnen Benutzer
 * getrennt voneinander gespeichert.
 * <p>
 */

public class Benutzer {

    //--------------------------------------------
    //Attribute für Benutzer und Passwort
    /**
     * Gibt den Benutzernamen des Benutzers an.
     */
    private String benutzername;

    /**
     * Gibt das Passwort des Benutzers an.
     */
    private String passwort;

    /**
     * Dort werden alle Daten serialisiert und gespeichert.
     */
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