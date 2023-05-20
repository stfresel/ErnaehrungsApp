package com.example.fitnessapp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * Ermöglicht es nur double Werte einzugeben.
 */
public class NumericTextField extends TextField {
    public NumericTextField() {
        this.textProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!newValue.matches("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?")) {
                    System.out.println("f");
                    NumericTextField.super.setText(oldValue);
                    //NumericTextField.super.setText(newValue.replaceAll("[^-?(([1-9][0-9]*)|0)?(\\\\.[0-9]*)?]", ""));
                }
            }
        });
    }

    public double getDouble(){
        return Double.parseDouble(getText());
    }

}
