/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.weatherapp;

/**
 *
 * @author bcmivu
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;

public class WeatherData implements iAPI {

    private static final String API_KEY = "7e2aaa9d361a771034770163dde8b02c";
    private static final String API_URL = "https://api.openweathermap.org/data/2.5";

    @Override
    public String lookUpLocation(String loc) {
        // Implementation to look up location coordinates using OpenWeatherMap API
        final String API_KEY = "7e2aaa9d361a771034770163dde8b02c";
        final String GEOCODING_URL = "https://api.openweathermap.org/geo/1.0/direct?q=";
        try {
            // Construct URL for the geocoding API request
            String apiUrl = GEOCODING_URL + loc + "&limit=1&appid=" + API_KEY;

            // Open a connection to the API
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read response from the API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse the JSON response using Gson
            JsonParser parser = new JsonParser();
            JsonObject jsonResponse = parser.parse(response.toString()).getAsJsonObject();

            // Extract coordinates from the JSON object
            JsonArray coordinates = jsonResponse.getAsJsonArray("coordinates");
            JsonObject firstCoordinate = coordinates.get(0).getAsJsonObject();
            double lat = firstCoordinate.get("lat").getAsDouble();
            double lon = firstCoordinate.get("lon").getAsDouble();

            // Return the coordinates as a string
            return "Latitude: " + lat + ", Longitude: " + lon;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
    }

    @Override
    public String getCurrentWeather(double lat, double lon) {
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            connection.disconnect();
            
            // Parse JSON response
            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();

            // Extract relevant weather information
            String cityName = jsonResponse.get("name").getAsString();
            JsonObject main = jsonResponse.getAsJsonObject("main");
            double temperature = main.get("temp").getAsDouble();
            double humidity = main.get("humidity").getAsDouble();
            JsonObject weather = jsonResponse.getAsJsonArray("weather").get(0).getAsJsonObject();
            String description = weather.get("description").getAsString();

            // Construct readable weather information string
            String weatherInfo = "Weather in " + cityName + ":\n";
            weatherInfo += "Temperature: " + temperature + "°C\n";
            weatherInfo += "Humidity: " + humidity + "%\n";
            weatherInfo += "Description: " + description;

            return weatherInfo;            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getForecast(double lat, double lon) {
        try {
            URL url = new URL(API_URL + "/forecast?lat=" + lat + "&lon=" + lon + "&appid=" + API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

