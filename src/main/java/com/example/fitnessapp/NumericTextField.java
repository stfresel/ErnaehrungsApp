package com.example.fitnessapp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class NumericTextField extends TextField {
    public NumericTextField() {
        this.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!newValue.matches("(\\d+| \\.)")) {
                    NumericTextField.super.setText(newValue.replaceAll("[^(\\d | \\.)]", ""));
                }
            }
        });
    }

    public double getDouble(){
        return Double.parseDouble(getText());
    }

    public int getInt(){
        return Integer.parseInt(getText());
    }

}
