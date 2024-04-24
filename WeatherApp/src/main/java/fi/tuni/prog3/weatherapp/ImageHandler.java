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

    public String imageHandler(String weatherType) {
        switch (weatherType) {
            case "Thunderstorm":
                return "/icons/day-cloudy-thunder.png";
        
            case "Rain":
                return "/icons/day-cloudy-rain.png";
            
            case "Clear":
                return "/icons/day-clear.png";

            case "Snow":
                return "/icons/day-cloudy-showy.png";

            case "Clouds":
                return "/icons/day-cloudy.png";
        }
        return "/icons/day-clear.png";
    }
}
