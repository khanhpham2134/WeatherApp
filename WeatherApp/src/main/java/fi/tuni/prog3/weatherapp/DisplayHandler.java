package fi.tuni.prog3.weatherapp;

/**
 *
 * @author crhiho
 */
public class DisplayHandler {

    private final iAPI weatherAPIMetric = new WeatherData("metric");
    private final iAPI weatherAPIImperial = new WeatherData("imperial");

    /**
     * Checks if the input data for the city is valid and retrieves the city location using the weather API.
     *
     * @param cityData an array of strings containing the city data
     * @return true if the input data is valid, false otherwise
     */
    public boolean ifInputValid(String[] cityData) {
        if (cityData.length == 1) {
            weatherAPIMetric.lookUpLocation(cityData[0], "", "");
            
        } else if (cityData.length == 2) {
            weatherAPIMetric.lookUpLocation(cityData[0], "", cityData[1]);
            
        } else if (cityData.length == 3) {
            weatherAPIMetric.lookUpLocation(cityData[0], cityData[1], cityData[2]);            
        } else { // invalid amount of parameters
            return true;
        }
        return weatherAPIMetric.get_error_flag();
    }
    
    /**
     * Retrieves the city information based on the provided city data.
     *
     * @param cityData an array of strings containing the city data. The array should contain the city name, and optionally the state and country.
     * @return an array of strings representing the city location information.
     */
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

    /**
     * Retrieves the current weather data in metric units for a given city.
     *
     * @param cityData an array containing the latitude and longitude of the city
     * @return an array of weather data in metric units
     */
    public String[] getCurrentWeatherDataMetric(String[] cityData) {
        String[] weatherData = {};
        weatherData = weatherAPIMetric.getCurrentWeather(Double.valueOf(cityData[0]), Double.valueOf(cityData[1]));
        
        return weatherData;
    }
    
    /**
     * Retrieves the daily forecast data in metric units for a given city.
     *
     * @param cityData an array containing the latitude and longitude of the city
     * @return a 2D array of weather data containing the forecast for each day
     */
    public String[][] getDailyForecastMetric(String[] cityData) {
        String[][] weatherData = {};
        weatherData = weatherAPIMetric.getForecast(Double.valueOf(cityData[0]), Double.valueOf(cityData[1]));
        
        return weatherData;
    }
    
    /**
     * Retrieves the hourly forecast data for a given city in metric units.
     *
     * @param cityData an array containing the latitude and longitude of the city
     * @return a 2D array of strings representing the hourly weather forecast data
     */
    public String[][] getHourlyForecastMetric(String[] cityData) {
        Object weatherDataObject;
        weatherDataObject = weatherAPIMetric.getHourlyForecast(Double.valueOf(cityData[0]), Double.valueOf(cityData[1]));
        String[][] weatherData = (String[][]) weatherDataObject;

        return weatherData;
    }

    /**
     * Retrieves the current weather data in imperial units for a given city.
     *
     * @param cityData an array containing the latitude and longitude of the city
     * @return an array of weather data in imperial units
     */
    public String[] getCurrentWeatherDataImperial(String[] cityData) {
        String[] weatherData = {};
        weatherData = weatherAPIImperial.getCurrentWeather(Double.valueOf(cityData[0]), Double.valueOf(cityData[1]));

        return weatherData;
    }
    
    /**
     * Retrieves the daily weather forecast in imperial units for a given city.
     *
     * @param cityData an array containing the latitude and longitude of the city
     * @return a 2D array containing the weather data for each day
     */
    public String[][] getDailyForecastImperial(String[] cityData) {
        String[][] weatherData = {};
        weatherData = weatherAPIImperial.getForecast(Double.valueOf(cityData[0]), Double.valueOf(cityData[1]));
        
        return weatherData;
    }
    
    /**
     * Retrieves the hourly forecast data in imperial units for a given city.
     *
     * @param cityData an array containing the latitude and longitude of the city
     * @return a two-dimensional array of strings representing the weather data
     */
    public String[][] getHourlyForecastImperial(String[] cityData) {
        Object weatherDataObject;
        weatherDataObject = weatherAPIImperial.getHourlyForecast(Double.valueOf(cityData[0]), Double.valueOf(cityData[1]));
        String[][] weatherData = (String[][]) weatherDataObject;

        return weatherData;
    }
}
