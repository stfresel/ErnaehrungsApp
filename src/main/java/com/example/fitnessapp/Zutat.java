package com.example.fitnessapp;

import java.io.Serializable;

public class Zutat implements Serializable {
    private String name;
    private int mengeGegessen;
    private int mengeDerNaehrwertangaben; // in gramm

    private Naehrwerte naehrwerteProXGramm;
    private Naehrwerte naehrwerteEffektivGegessen;

    public Zutat(String name, int mengeGegessen, int mengeDerNaehrwertangaben, Naehrwerte naehrwerteProXGramm) {
        this.name = name;
        this.mengeGegessen = mengeGegessen;
        this.mengeDerNaehrwertangaben = mengeDerNaehrwertangaben;
        this.naehrwerteProXGramm = naehrwerteProXGramm;
        berechenGegesseneNaehrwerte();
    }

    //___________methoden_______________

    /**
     * Berechnet die effektive Menge der Nährwerte, welche der Benutzer zu sich nimmt.
     */
    public void berechenGegesseneNaehrwerte(){
        int f = naehrwerteProXGramm.getFett() * (mengeGegessen/mengeDerNaehrwertangaben);
        int kcal = naehrwerteProXGramm.getKcal() * (mengeGegessen/mengeDerNaehrwertangaben);
        int p = naehrwerteProXGramm.getProtein() * (mengeGegessen/mengeDerNaehrwertangaben);
        int k =  naehrwerteProXGramm.getKohlenhydrate() * (mengeGegessen/mengeDerNaehrwertangaben);
        naehrwerteEffektivGegessen = new Naehrwerte(kcal, f, k, p);
    }



    //______________getter und setter______________


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMengeGegessen() {
        return mengeGegessen;
    }

    public void setMengeGegessen(int mengeGegessen) {
        this.mengeGegessen = mengeGegessen;
    }

    public int getMengeDerNaehrwertangaben() {
        return mengeDerNaehrwertangaben;
    }

    public void setMengeDerNaehrwertangaben(int mengeDerNaehrwertangaben) {
        this.mengeDerNaehrwertangaben = mengeDerNaehrwertangaben;
    }

    public Naehrwerte getNaehrwerteProXGramm() {
        return naehrwerteProXGramm;
    }

    public void setNaehrwerteProXGramm(Naehrwerte naehrwerteProXGramm) {
        this.naehrwerteProXGramm = naehrwerteProXGramm;
    }

    public Naehrwerte getNaehrwerteEffektivGegessen() {
        return naehrwerteEffektivGegessen;
    }

    public void setNaehrwerteEffektivGegessen(Naehrwerte naehrwerteEffektivGegessen) {
        this.naehrwerteEffektivGegessen = naehrwerteEffektivGegessen;
    }

    @Override
    public String toString() {
        return "Zutat{" +
                "name='" + name + '\'' +
                ", mengeGegessen=" + mengeGegessen +
                ", mengeDerNaehrwertangaben=" + mengeDerNaehrwertangaben +
                ", naehrwerteProXGramm=" + naehrwerteProXGramm +
                ", naehrwerteEffektivGegessen=" + naehrwerteEffektivGegessen +
                '}';
    }
}