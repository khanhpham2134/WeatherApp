package fi.tuni.prog3.weatherapp;

/**
 * Interface for extracting data from the OpenWeatherMap API.
 */
public interface iAPI {

    /**
     * Returns coordinates for a location.
     * @param loc_name Name of the location for which coordinates should be fetched.
     * @param state_name Name of the state only for US location
     * @param country_code Country code to specify the location
     * @return String array.
     */
    public String[] lookUpLocation(String loc_name, String state_name, String country_code);

    /**
     * Returns the current weather for the given coordinates.
     * @param lat The latitude of the location.
     * @param lon The longitude of the location.
     * @return String array.
     */
    public String[] getCurrentWeather(double lat, double lon);

    /**
     * Returns a forecast for the given coordinates.
     * @param lat The latitude of the location.
     * @param lon The longitude of the location.
     * @return 2D String array.
     */
    public String[][] getForecast(double lat, double lon);
    
    /**
     * Returns the hourly forecast for a location
     * 
     * @param lat: latitude of the location
     * @param lon: longitude of the location
     * @return Object hourly forecast data
     */
    public String[][] getHourlyForecast(double lat, double lon);
}
