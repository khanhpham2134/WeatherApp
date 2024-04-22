package fi.tuni.prog3.weatherapp;

import javafx.scene.control.TextField;
import java.util.Map;

/**
 *
 * @author crhiho
 */
public class DisplayHandler {

    private final iMyAPI weatherAPI = new WeatherData();

    public String[] getCurrentWeatherData(TextField textField) {
        String[] cityData = textField.getText().split(",", 3);
        Object cityLocationObject = weatherAPI.lookUpLocation(cityData[0], "", cityData[1]);
        double[] cityLocation = (double[]) cityLocationObject;
        String[] weatherData = weatherAPI.getCurrentWeather(cityLocation[0], cityLocation[1]);
        
        return weatherData;
    }
    
    public String[][] getDailyForecast(TextField textField) {
        String[] cityData = textField.getText().split(",", 3);
        Object cityLocationObject = weatherAPI.lookUpLocation(cityData[0], "", cityData[1]);
        double[] cityLocation = (double[]) cityLocationObject;
        String[][] weatherData = weatherAPI.getForecast(cityLocation[0], cityLocation[1]);
        
        return weatherData;
    }
    
    public Map<String, String[][]> getHourlyForecast(TextField textField) {
        String[] cityData = textField.getText().split(",", 3);
        Object cityLocationObject = weatherAPI.lookUpLocation(cityData[0], "", cityData[1]);
        double[] cityLocation = (double[]) cityLocationObject;
        Object weatherDataObject = weatherAPI.getHourlyForecast(cityLocation[0], cityLocation[1]);
        Map<String, String[][]> weatherData = (Map<String, String[][]>) weatherDataObject;
        
        return weatherData;
    }

    public String[] changeCurrentWeatherUnit(String[] weatherData) {
        // String[] weatherInfo = {temperature, feels_like, min_temp, max_temp,humidity,description,main_sky,wind_speed};
        Double newTemp = Double.valueOf(weatherData[0]) * 1.8 + 32;
        Double newFeelsLike = Double.valueOf(weatherData[1]) * 1.8 + 32;
        Double newMinTemp = Double.valueOf(weatherData[2]) * 1.8 + 32;
        Double newMaxTemp = Double.valueOf(weatherData[3]) * 1.8 + 32;
        Double newWindSpeed = Double.valueOf(weatherData[7]);

        
        String[] result = {};
        return result;
    }
}
