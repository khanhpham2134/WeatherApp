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
     * Returns the hourly forecast for a location
     * 
     * @param lat: latitude of the location
     * @param lon: longitude of the location
     * @return Object hourly forecast data
     */
    public Object getHourlyForecast(double lat, double lon);
}
