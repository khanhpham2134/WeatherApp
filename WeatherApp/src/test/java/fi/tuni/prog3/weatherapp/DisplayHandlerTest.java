package fi.tuni.prog3.weatherapp;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DisplayHandlerTest {

    @Test
    public void testIfInputValid_validInput() {
        DisplayHandler instance = new DisplayHandler();
        TextField textField = new TextField("London");
        boolean result = instance.ifInputValid(textField);
        assertFalse(result, "Expected valid input, but got invalid.");
    }

    @Test
    public void testIfInputValid_invalidInput() {
        DisplayHandler instance = new DisplayHandler();
        TextField textField = new TextField("Invalid City Name,invalid param");
        boolean result = instance.ifInputValid(textField);
        assertTrue(result, "Expected invalid input, but got valid.");
    }

    @Test
    public void testGetCityInformation() {
        DisplayHandler instance = new DisplayHandler();
        String[] cityData = {"London"};
        String[] result = instance.getCityInformation(cityData);
        assertNotNull(result, "City information is null.");
        assertEquals(4, result.length, "Expected city information array length 4.");
    }

    @Test
    public void testGetCurrentWeatherDataMetric() {
        DisplayHandler instance = new DisplayHandler();
        String[] cityData = {"51.51", "-0.13"}; // Coordinates of London
        String[] result = instance.getCurrentWeatherDataMetric(cityData);
        assertNotNull(result, "Current weather data in metric is null.");
        assertEquals(6, result.length, "Expected current weather data array length 6.");
    }

    @Test
    public void testGetDailyForecastMetric() {
        DisplayHandler instance = new DisplayHandler();
        String[] cityData = {"51.51", "-0.13"}; // Coordinates of London
        String[][] result = instance.getDailyForecastMetric(cityData);
        assertNotNull(result, "Daily forecast data in metric is null.");
        assertTrue(result.length > 0, "Daily forecast data array is empty.");
    }

    @Test
    public void testGetHourlyForecastMetric() {
        DisplayHandler instance = new DisplayHandler();
        String[] cityData = {"51.51", "-0.13"}; // Coordinates of London
        String[][] result = instance.getHourlyForecastMetric(cityData);
        assertNotNull(result, "Hourly forecast data in metric is null.");
        assertTrue(result.length > 0, "Hourly forecast data array is empty.");
    }

    @Test
    public void testGetCurrentWeatherDataImperial() {
        DisplayHandler instance = new DisplayHandler();
        String[] cityData = {"51.51", "-0.13"}; // Coordinates of London
        String[] result = instance.getCurrentWeatherDataImperial(cityData);
        assertNotNull(result, "Current weather data in Imperial units is null.");
        assertEquals(6, result.length, "Expected current weather data array length 6.");
    }

    @Test
    public void testGetDailyForecastImperial() {
        DisplayHandler instance = new DisplayHandler();
        String[] cityData = {"51.51", "-0.13"}; // Coordinates of London
        String[][] result = instance.getDailyForecastImperial(cityData);
        assertNotNull(result, "Daily forecast data in Imperial units is null.");
        assertTrue(result.length > 0, "Daily forecast data array in Imperial units is empty.");
    }

    @Test
    public void testGetHourlyForecastImperial() {
        DisplayHandler instance = new DisplayHandler();
        String[] cityData = {"51.51", "-0.13"}; // Coordinates of London
        String[][] result = instance.getHourlyForecastImperial(cityData);
        assertNotNull(result, "Hourly forecast data in Imperial units is null.");
        assertTrue(result.length > 0, "Hourly forecast data array in Imperial units is empty.");
    }

}

