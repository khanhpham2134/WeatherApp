/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.weatherapp;

/**
 *
 * @author hvhie
 */
public class ImageHandler {

    public String currentImageHandler(String[] weatherData) {
        String weatherType = weatherData[6];
        Integer comparedTime = Integer.valueOf(weatherData[8]);
        Integer sunrise = Integer.valueOf(weatherData[9]);
        Integer sunset = Integer.valueOf(weatherData[10]);
        return imageReturn(comparedTime, sunrise, sunset, weatherType);
    }

    public String hourlyImageHandler(String[] weatherData, String sunrise, String sunset) {
        String weatherType = weatherData[3];
        Integer comparedTime = Integer.valueOf(weatherData[0]);
        Integer sunriseValue = Integer.valueOf(sunrise);
        Integer sunsetValue = Integer.valueOf(sunset);
        return imageReturn(comparedTime, sunriseValue, sunsetValue, weatherType);

    } 

    public String forecastImageHandler(String weatherType) {
        return imageReturn(12, 5, 18, weatherType);
    }

    public String imageReturn(int comparedTime, int sunrise, int sunset, String weatherType) {
        if (comparedTime <= sunset && comparedTime >= sunrise) {
            switch (weatherType) {
                case "Thunderstorm":
                    return "/icons/day-cloudy-thunder.png";
            
                case "Rain":
                    return "/icons/day-cloudy-rain.png";
                
                case "Clear":
                    return "/icons/day-clear.png";
    
                case "Snow":
                    return "/icons/day-cloudy-snowy.png";
    
                case "Clouds":
                    return "/icons/day-cloudy.png";
            }
            return "/icons/day-clear.png";
        } else {
            switch (weatherType) {
                case "Thunderstorm":
                    return "/icons/night-cloudy-thunder.png";
            
                case "Rain":
                    return "/icons/night-cloudy-rain.png";
                
                case "Clear":
                    return "/icons/night-clear.png";
    
                case "Snow":
                    return "/icons/night-cloudy-snowy.png";
    
                case "Clouds":
                    return "/icons/night-cloudy.png";
            }
            return "/icons/night-clear.png";

        }
    }
}
