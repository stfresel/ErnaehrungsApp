package com.example.fitnessapp;

import java.io.*;
import java.nio.Buffer;
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
     * @param name Name der Zutat
     */
    public void addZutat(String name) {
        try (ObjectInputStream whereToReadFrom = new ObjectInputStream(Files.newInputStream(path))) {
            Zutat temp = (Zutat) whereToReadFrom.readObject();
            boolean flag = true;

            while (temp != null && flag){
                if (Objects.equals(temp.getName(), name)){
                    zutaten.add(temp);
                    flag = false;
                }
                temp = (Zutat) whereToReadFrom.readObject();
            }


            //_____________________________ auchtung ignorierd
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Fehler beim Meal, weil while! " + e.getMessage());
        }
        System.out.println(zutaten);
    }

    public void neueZutatErstellen(String name, int mengeGegessen, int mengeDerAngaben, Naehrwerte naehrwerte) {
        Zutat z = new Zutat(name, mengeGegessen, mengeDerAngaben, naehrwerte);
        zutaten.add(z);
        saveZutat(z);
    }

    private void saveZutat(Zutat z){
        try (ObjectOutputStream whereToWrite = new ObjectOutputStream(Files.newOutputStream(path , StandardOpenOption.CREATE))) {
            whereToWrite.writeObject(z);

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
                ", path=" + path +
                ", zutatenFile=" + zutatenFile +
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
