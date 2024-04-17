/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.weatherapp;

/**
 *
 * @author bcmivu
 */
// import java.util.logging.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.util.Scanner;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;


public class WeatherData implements iMyAPI {

    private final String API_KEY;
    private String Unit; // temperature unit
    
    /**
     * Constructor that initializes the API key and the unit for temperature
     * @param unit 
     */
    public WeatherData(String unit){
        API_KEY = "7e2aaa9d361a771034770163dde8b02c";  // replace this with your API key
        Unit = unit;
    }
    
    /** Don't pay attention to this yet
    private String read_API_key(String file_name){
        try (Scanner scanner = new Scanner(new File(file_name))){
            return scanner.nextLine();
        }
        catch (Exception e){
            // logException("Can't read API key", e);
            e.printStackTrace();
            return null;
        }
    }
    
    
    //private void logException(String msg, Exception e) {
      //  Logger.getLogger(WeatherData.class.getName()).log(Level.SEVERE, msg,e);
    //}
    */

    // implement iMyAPI
    /**
     * Return the coordinate of the wanted location. If country_code is provided,
     * then only 1 specific coordinate of the 1 specific location is returned. If 
     * the country_code isn't provided (which should be avoided), the query will extract
     * at maximum 5 coordinates of possible 5 location with the same name, but the
     * function only returns the first coordinate for the first location.
     * The function works by reading data from an URL with the Scanner class and 
     * parsing of the resulting JSON string with the Gson library. 
     *  
     * @param loc_name: name of the location
     * @param state_name: name of the state if it's an US location
     * @param country_code: country code
     * @return a double array that contains latitude and longitude
     */
    @Override
    public double[] lookUpLocation(String loc_name, String state_name, String country_code) {
        // changing the return type to map <country_code, double[]>
        final String Open_weather_geocoding = "http://api.openweathermap.org/geo/1.0/direct";
        
        // query
        String query = "q=" + loc_name + "," + state_name + "," + country_code + "&limit=5" +"&appid=" + API_KEY;
        // limit is not provided, by default only 1 location returned
    
        // URL
        String address = Open_weather_geocoding + "?" + query;
    
        // Utilizing scanner
        Scanner scanner = null;
        try{
            URL url = new URL(address); // URL created
            
            scanner = new Scanner(url.openStream(), "UTF-8"); // Attach scanner to a stream opened from URL
        
            // Read coordinate data as a JSON string, use StringBuilder
            StringBuilder json_string = new StringBuilder();
            while (scanner.hasNextLine()){
                json_string.append(scanner.nextLine());
            }
            
            // Parse a JSON array of location 
            Gson gson = new Gson();
            JsonArray json_array = JsonParser.parseString(json_string.toString()).getAsJsonArray();
            
            
            System.out.println("Number of elements in json_array: " + json_array.size()); // behaviour control
            
            JsonElement json_element = json_array.get(0);  // json_array actually only has 1 element
            double latitude = json_element.getAsJsonObject().get("lat").getAsDouble();
            double longitude = json_element.getAsJsonObject().get("lon").getAsDouble();
        
            return new double[] {latitude, longitude};
        }
        
        catch(IOException e){
            e.printStackTrace();
            return null;
        }
        
        finally{ // What is this for :)??
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    
    /**
     * Retrieves current weather information based on latitude and longitude coordinates.
     *
     * @param lat    The latitude coordinate of the location.
     * @param lon    The longitude coordinate of the location.
     * @param units  The unit of measurement for temperature. Valid values are "metric" for Celsius or "imperial" for Fahrenheit.
     * @return       A string containing the current weather information, including temperature, 
     * humidity, and description, formatted based on the specified units.
     *               Returns null if an error occurs during the retrieval process.
     */    
    @Override
    public String getCurrentWeather(double lat, double lon, String units) {
        final String Open_weather = "http://api.openweathermap.org/data/2.5/weather";
        
        // query 
        String query = "lat=" + lat + "&lon=" + lon + "&appid=" + API_KEY + "&units=" + units;
        
        // url address
        String address = Open_weather + "?" + query;
        
        // URL created
        URL url = null;
        try{
            url = new URL(address);
        }
        catch (MalformedURLException e){
            e.printStackTrace();
            return null;
        }
        
        try {
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
            // String cityName = jsonResponse.get("name").getAsString();
            JsonObject main = jsonResponse.getAsJsonObject("main");
            double temperature = main.get("temp").getAsDouble();
            double humidity = main.get("humidity").getAsDouble();
            JsonObject weather = jsonResponse.getAsJsonArray("weather").get(0).getAsJsonObject();
            String description = weather.get("description").getAsString();

            // Construct readable weather information string
            String weatherInfo =  "";//"Weather in " + cityName + ", " + "" +":\n";
            
            if("metric".equals(units)){  // assume that unit could only either be metric or imperial
            weatherInfo += "Temperature: " + temperature + "°C\n";
            } else if ("imperial".equals(units)){
            weatherInfo += "Temperature: " + temperature + "°F\n";
            }
            
            weatherInfo += "Humidity: " + humidity + "%\n";
            weatherInfo += "Description: " + description;

            return weatherInfo;            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } 
    }

    /**
     * Retrieves a next 2 days weather forecast for the specified location coordinates.
     *
     * @param lat The latitude of the location.
     * @param lon The longitude of the location.
     * @param units The units to use for temperature measurement. Can be "metric" for Celsius or "imperial" for Fahrenheit.
     * @return A String containing the weather forecast for the next two days, including temperature, humidity, and description.
     *         Returns null if an error occurs during the retrieval process.
     * @throws IOException If an I/O exception occurs while connecting to the weather API.
     */    
    @Override
    public String getForecast(double lat, double lon, String units) {
        // Build the URL for the API call
        String url_String = "http://api.openweathermap.org/data/2.5/forecast/daily"
                + "?lat=" + lat
                + "&lon=" + lon
                + "&cnt=3" // 3 days including today
                + "&appid=" + API_KEY + "&units=" + units;

        try {
            // Create a URL object
            URL url = new URL(url_String);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();

            // Check if the response code indicates success
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse the JSON response
                JsonObject jsonObject = new Gson().fromJson(response.toString(), JsonObject.class);

                // Extract the forecast data for the next two days
                JsonArray forecastList = jsonObject.getAsJsonArray("list");

                JsonObject day1 = forecastList.get(1).getAsJsonObject();
                JsonObject day2 = forecastList.get(2).getAsJsonObject();

                // Extract relevant information
                double day1Temp = day1.getAsJsonObject("temp").get("day").getAsDouble();
                double day2Temp = day2.getAsJsonObject("temp").get("day").getAsDouble();
                double day1Humidity = day1.get("humidity").getAsDouble();
                double day2Humidity = day2.get("humidity").getAsDouble();
                String day1Description = day1.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
                String day2Description = day2.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
                
                String displayed_unit ="";
                if("metric".equals(units)){  // assume that unit could only either be metric or imperial
                displayed_unit =  "°C";
                } else if ("imperial".equals(units)){
                displayed_unit =  "°F";
                }                 
                
                // Display the forecast for the next two days
                String weather_forecast = "One day after: " + ":\n"  
                        + "Temperature: " + day1Temp + " " + displayed_unit + ", Humidity: " + day1Humidity + "%, Description: " + day1Description +"\n" 
                        + "One day after: " + "\n"
                        + "Temperature: " + day2Temp + " " + displayed_unit + ", Humidity: " + day2Humidity + "%, Description: " + day2Description ;             

                return weather_forecast;
            } else {
                System.out.println("Error: " + responseCode);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // implement iAPI
    @Override
    public String lookUpLocation(String loc){
        double[] coordinates = lookUpLocation(loc,"","");  // utilize the above defined method
        if (coordinates != null){
            return coordinates[0] + " " + coordinates[1]; // lat and long
        }
        else {
        return null; 
        }
    }
    
    @Override
    public String getCurrentWeather(double lat, double lon) { // utilize the above defined method
    String weather_info = getCurrentWeather(lat,lon,Unit);
    return weather_info;
    }
    
    @Override
    public String getForecast(double lat, double lon) {  // utilize the above defined method
    String forecast = getForecast(lat,lon,Unit);
    return forecast;    
    }
}

