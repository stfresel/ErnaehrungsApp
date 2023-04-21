package com.example.fitnessapp;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Objects;

public class Meal {
    private ArrayList<Zutat> zutaten = new ArrayList<>();
    Path path = Paths.get("ZutatenFile.ser");

    private File zutatenFile = new File("ZutatenFile.ser");

    /**
     * Holt die Zutat aus dem Zutaten File(dort wo alle Zutaten gespeichert sind).
     * @param name Name der Zutat, nachdem in ZutatenFile.ser gesucht wird
     */
    public void addZutat(String name) {
        try (ObjectInputStream whereToReadFrom = new ObjectInputStream(Files.newInputStream(path))) {
            Main.gespeicherteZutaten = (ArrayList<Zutat>) whereToReadFrom.readObject();
            System.out.println("auslesen vom file");
            System.out.println(Main.gespeicherteZutaten);
            for (int i = 0; i < Main.gespeicherteZutaten.size(); i++) {
                if (Objects.equals(Main.gespeicherteZutaten.get(i).getName(), name)) {
                    zutaten.add(Main.gespeicherteZutaten.get(i));
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("File: Meal --> Fehler bei addZutat");
            throw new RuntimeException();
        }
        System.out.println(zutaten);
    }

    public void neueZutatErstellen(String name, int mengeGegessen, int mengeDerAngaben, Naehrwerte naehrwerte) {
        Zutat z = new Zutat(name, mengeGegessen, mengeDerAngaben, naehrwerte);
        zutaten.add(z);     // zum Gericht hinzufügen
        Main.gespeicherteZutaten.add(z);    // zum Speicher, wo alle Zutaten sind, hinzufügen
        saveZutaten();
    }

    private void saveZutaten (){
        try (ObjectOutputStream whereToWrite = new ObjectOutputStream(Files.newOutputStream(path , StandardOpenOption.CREATE))) {
            whereToWrite.reset();
            whereToWrite.writeObject(Main.gespeicherteZutaten);
            System.out.println("Saved Zutaten");
        } catch (IOException e) {
            System.out.println("Can't serialize file: " + e.getMessage());
        }
    }

    public void delZutat(String name) {
        for (int i = 0; i < zutaten.size(); i++) {
            if (Objects.equals(zutaten.get(i).getName(), name)){
                zutaten.remove(i);
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Meal{" +
                "zutaten=" + zutaten +
                '}';
    }
}


/*
BufferedReader reader;      //------------
        try {
            reader = new BufferedReader(new FileReader(zutatenFile));

            String buf = reader.readLine();
            while (buf != null){
                //-------------------------------------------------------------------------
                System.out.println(buf);

                buf = reader.readLine();
            }
        } catch (FileNotFoundException e){
            System.out.println("Zutatenfile konnte nicht gefunden werden");
        }
 */
