package com.example.fitnessapp;

import java.io.Serializable;


public class Zutat implements Serializable {
    private String name;
    private int menge;
    private Naehrwerte naehrwerte;

    public Zutat(String name, int menge, Naehrwerte naehrwerte) {
        this.name = name;
        this.menge = menge;
        this.naehrwerte = naehrwerte;
    }

    public Zutat(){

    }



    //______________getter und setter______________


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMenge() {
        return menge;
    }

    public void setMenge(int menge) {
        this.menge = menge;
    }

    public Naehrwerte getNaehrwerte() {
        return naehrwerte;
    }

    public void setNaehrwerte(Naehrwerte naehrwerte) {
        this.naehrwerte = naehrwerte;
    }

    @Override
    public String toString() {
        return "Zutat{" +
                "name='" + name + '\'' +
                ", menge=" + menge +
                ", naehrwerte=" + naehrwerte +
                '}';
    }
}