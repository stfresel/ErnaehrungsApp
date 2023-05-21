package com.example.fitnessapp;

/**
 * Ermöglicht es verschiedene Benutzer-Accounts anzulegen.
 * Somit werden die Daten der einzelnen Benutzer getrennt voneinander gespeichert.
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
     * Enthält alle wichtigen Daten, abgesehen vom Benutzername und Passwort.
     * Die gesamte Instanz von Home wird serialisiert, bis auf die JavaFX Komponenten.
     */
    private Home home = new Home();

    //------------------getter und setter--------------------------

    /**
     * Gibt <code>home</code> zurück.
     * @return Instanz von Home
     */
    public Home getHome() {
        return home;
    }

    /**
     * Gibt den Benutzernamen zurück.
     * @return String mit dem Benutzernamen
     */
    public String getBenutzername() {
        return benutzername;
    }

    /**
     * Gibt das Passwort zurück.
     * @return String mit dem Passwort
     */
    public String getPasswort() {
        return passwort;
    }

    /**
     * Setzt den Benutzernamen.
     * @param name neuer Benutzername
     */
    public void setBenutzername(String name) {
        benutzername = name;
    }

    /**
     * Setzt das Passwort.
     * @param passwort neues Passwort
     */
    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    /**
     * Setzt Home.
     * @param home neues Home
     */
    public void setHome(Home home) {
        this.home = home;
    }
}