/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fi.tuni.prog3.weatherapp;

/**
 *
 * @author bcmivu
 */

// Extend the given iAPI interface to enhance the adaptability of each method
public interface iMyAPI extends iAPI {
    
     /**
     * Returns coordinates for a location.
     * 
     * @param loc_name Name of the location for which coordinates should be fetched.
     * @param state_name Name of the state, an empty string if this is a non US location.
     * @param country_code a two character code for the country of the location
     * @return Object coordinates. Null if cannot read data
     * return Object helps return more complex data structures or custom objects
     */
    public Object lookUpLocation(String loc_name, String state_name, String country_code);
    
    /**
     * Returns the current weather for the given coordinates.
     * @param lat The latitude of the location.
     * @param lon The longitude of the location.
     * @param units unit of the temperature ("standard", "metric" or "imperial")
     * standard (SI) = Kelvin, metric = Celsius, imperial = Fahrenheit
     * @return Object current weather data. Null if data could not be read
     */
    public Object getCurrentWeather(double lat, double lon, String units);    
    
     /**
     * Returns the next 2 days weather forecast for the given coordinates.
     * @param lat The latitude of the location.
     * @param lon The longitude of the location.
     * @param units unit of the temperature ("standard", "metric" or "imperial")
     * standard (SI) = Kelvin, metric = Celsius, imperial = Fahrenheit
     * @return Object forecast weather data. Null if data could not be read
     */
    public Object getForecast(double lat, double lon, String units);
    
    
}
