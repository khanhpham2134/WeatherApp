package fi.tuni.prog3.weatherapp;

import javafx.scene.control.TextField;

/**
 *
 * @author crhiho
 */
public class DisplayHandler {

    public static String[] getCityName(TextField textField) {
        String[] cityData = textField.getText().split(",", 3);
        return cityData;
    }
}
