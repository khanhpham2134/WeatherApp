package fi.tuni.prog3.weatherapp;

import javafx.scene.control.TextField;
/**
 *
 * @author crhiho
 */
public class DisplayHandler {

    private final iAPI weatherAPIMetric = new WeatherData("metric");
    private final iAPI weatherAPIImperial = new WeatherData("imperial");

    public boolean ifInputValid(String[] cityData) {
        if (cityData.length == 1) {
            String[] cityLocation = weatherAPIMetric.lookUpLocation(cityData[0], "", "");
            
        } else if (cityData.length == 2) {
            String[] cityLocation = weatherAPIMetric.lookUpLocation(cityData[0], "", cityData[1]);
            
        } else if (cityData.length == 3) {
            String[] cityLocation = weatherAPIMetric.lookUpLocation(cityData[0], cityData[1], cityData[2]);            
        } else { // invalid amount of parameters
            return true;
        }
        return weatherAPIMetric.get_error_flag();
    }
    
    public String[] getCityInformation(String[] cityData) {
        String[] cityLocation = {};
        if (cityData.length == 1) {
            cityLocation = weatherAPIMetric.lookUpLocation(cityData[0], "", "");
        } else if (cityData.length == 2) {
            cityLocation = weatherAPIMetric.lookUpLocation(cityData[0], "", cityData[1]);
        } else {
            cityLocation = weatherAPIMetric.lookUpLocation(cityData[0], cityData[1], cityData[2]);
        }
        
        return cityLocation;
    }

    public String[] getCurrentWeatherDataMetric(String[] cityData) {
        String[] weatherData = {};
        weatherData = weatherAPIMetric.getCurrentWeather(Double.valueOf(cityData[0]), Double.valueOf(cityData[1]));
        
        return weatherData;
    }
    
    public String[][] getDailyForecastMetric(String[] cityData) {
        String[][] weatherData = {};
        weatherData = weatherAPIMetric.getForecast(Double.valueOf(cityData[0]), Double.valueOf(cityData[1]));
        
        return weatherData;
    }
    
    public String[][] getHourlyForecastMetric(String[] cityData) {
        Object weatherDataObject;
        weatherDataObject = weatherAPIMetric.getHourlyForecast(Double.valueOf(cityData[0]), Double.valueOf(cityData[1]));
        String[][] weatherData = (String[][]) weatherDataObject;

        return weatherData;
    }

    public String[] getCurrentWeatherDataImperial(String[] cityData) {
        String[] weatherData = {};
        weatherData = weatherAPIImperial.getCurrentWeather(Double.valueOf(cityData[0]), Double.valueOf(cityData[1]));

        return weatherData;
    }
    
    public String[][] getDailyForecastImperial(String[] cityData) {
        String[][] weatherData = {};
        weatherData = weatherAPIImperial.getForecast(Double.valueOf(cityData[0]), Double.valueOf(cityData[1]));
        
        return weatherData;
    }
    
    public String[][] getHourlyForecastImperial(String[] cityData) {
        Object weatherDataObject;
        weatherDataObject = weatherAPIImperial.getHourlyForecast(Double.valueOf(cityData[0]), Double.valueOf(cityData[1]));
        String[][] weatherData = (String[][]) weatherDataObject;

        return weatherData;
    }
}
