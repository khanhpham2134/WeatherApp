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
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class WeatherData implements iMyAPI {

    private final String API_KEY;
   
    /**
     * Constructor that initializes the API key and the unit for temperature
     * @param unit 
     */
    public WeatherData(){
        API_KEY = "7e2aaa9d361a771034770163dde8b02c";  
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
        String query = "q=" + loc_name + "," + state_name + "," + country_code +"&appid=" + API_KEY;
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
     * Retrieves hourly weather forecast data for the specified latitude and longitude coordinates.
     *
     * @param lat The latitude coordinate.
     * @param lon The longitude coordinate.
     * @return A map containing hourly weather forecast data for the next four days, with each day represented
     *         by a key ("Day 0" for the first 24 hours, "Day 1" for the next 24 hours, and so on) mapping to a 2D array. Each row
     *         in the array represents an hourly forecast with columns containing hour, temperature, minimum
     *         temperature, maximum temperature, and weather condition.
     */
    @Override
    public Map<String, String[][]> getHourlyForecast(double lat, double lon) {
        Map<String, String[][]> hourlyForecast = new HashMap<>();
        
        try {
            String urlString = "https://pro.openweathermap.org/data/2.5/forecast/hourly?lat=" + lat + "&lon=" + lon + "&appid=" + API_KEY+ "&units=metric";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
            JsonArray hourlyData = jsonResponse.getAsJsonArray("list");
            
            hourlyForecast.put("Day 0", new String[24][5]);
            for (int i = 0; i < 24; i++) { // iterate through the first 24 timestamps
                JsonElement element = hourlyData.get(i);
                JsonObject dataPoint = element.getAsJsonObject();
                String timestamp = dataPoint.get("dt_txt").getAsString();
                String temperature = dataPoint.getAsJsonObject("main").get("temp").getAsString();
                String tempMin = dataPoint.getAsJsonObject("main").get("temp_min").getAsString();
                String tempMax = dataPoint.getAsJsonObject("main").get("temp_max").getAsString();
                String weatherMain = dataPoint.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
                int hour = Integer.parseInt(timestamp.split(" ")[1].split(":")[0]);
                String Hour =  String.valueOf(hour);
                // Extract day from timestamp
                              
                // Store data in array
                
                hourlyForecast.get("Day 0")[i] = new String[] {Hour, temperature, tempMin, tempMax, weatherMain};
            }
            
            hourlyForecast.put("Day 1", new String[24][5]);
            for (int i = 24; i < 48; i++) { // iterate through the next 24 timestamps
                JsonElement element = hourlyData.get(i);
                JsonObject dataPoint = element.getAsJsonObject();
                String timestamp = dataPoint.get("dt_txt").getAsString();
                String temperature = dataPoint.getAsJsonObject("main").get("temp").getAsString();
                String tempMin = dataPoint.getAsJsonObject("main").get("temp_min").getAsString();
                String tempMax = dataPoint.getAsJsonObject("main").get("temp_max").getAsString();
                String weatherMain = dataPoint.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
                int hour = Integer.parseInt(timestamp.split(" ")[1].split(":")[0]);
                String Hour =  String.valueOf(hour);
                // Extract day from timestamp
                              
                // Store data in array               
                hourlyForecast.get("Day 1")[i-24] = new String[] {Hour, temperature, tempMin, tempMax, weatherMain};
            }

            hourlyForecast.put("Day 2", new String[24][5]);
            for (int i = 48; i < 72; i++) { // iterate through the next 24 timestamps
                JsonElement element = hourlyData.get(i);
                JsonObject dataPoint = element.getAsJsonObject();
                String timestamp = dataPoint.get("dt_txt").getAsString();
                String temperature = dataPoint.getAsJsonObject("main").get("temp").getAsString();
                String tempMin = dataPoint.getAsJsonObject("main").get("temp_min").getAsString();
                String tempMax = dataPoint.getAsJsonObject("main").get("temp_max").getAsString();
                String weatherMain = dataPoint.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
                int hour = Integer.parseInt(timestamp.split(" ")[1].split(":")[0]);
                String Hour =  String.valueOf(hour);
                // Extract day from timestamp
                              
                // Store data in array
                
                hourlyForecast.get("Day 2")[i-48] = new String[] {Hour, temperature, tempMin, tempMax, weatherMain};
            }

            hourlyForecast.put("Day 3", new String[24][5]);
            for (int i = 72; i < 96; i++) { // iterate through the last 24 timestamps
                JsonElement element = hourlyData.get(i);
                JsonObject dataPoint = element.getAsJsonObject();
                String timestamp = dataPoint.get("dt_txt").getAsString();
                String temperature = dataPoint.getAsJsonObject("main").get("temp").getAsString();
                String tempMin = dataPoint.getAsJsonObject("main").get("temp_min").getAsString();
                String tempMax = dataPoint.getAsJsonObject("main").get("temp_max").getAsString();
                String weatherMain = dataPoint.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
                int hour = Integer.parseInt(timestamp.split(" ")[1].split(":")[0]);
                String Hour =  String.valueOf(hour);
                // Extract day from timestamp
                              
                // Store data in array
                
                hourlyForecast.get("Day 3")[i-72] = new String[] {Hour, temperature, tempMin, tempMax, weatherMain};
            }            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return hourlyForecast;
    
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
    
    /**
     * Retrieves current weather information based on latitude and longitude coordinates.
     *
     * @param lat    The latitude coordinate of the location.
     * @param lon    The longitude coordinate of the location.

     * @return       An array of string containing the current temperature, 
     * feels like, min and max temperature (in metric, Celsius), humidity, description,
     * sky, and wind speed (in metric, m/s) IN THIS ORDER.
     * 
     * Returns an array of string with all elements are "Error" if error occurs during 
     * the retrieval process.
     */    
    @Override
    public String[] getCurrentWeather(double lat, double lon) {
        final String Open_weather = "http://api.openweathermap.org/data/2.5/weather";
        
        // query 
        String query = "lat=" + lat + "&lon=" + lon + "&appid=" + API_KEY + "&units=metric";
        // By default return measurement in metric
        
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
            JsonObject main = jsonResponse.getAsJsonObject("main");
            String temperature = main.get("temp").getAsString();
            String feels_like = main.get("feels_like").getAsString();
            String min_temp = main.get("temp_min").getAsString();
            String max_temp = main.get("temp_max").getAsString();         
            String humidity = main.get("humidity").getAsString();
            JsonObject weather = jsonResponse.getAsJsonArray("weather").get(0).getAsJsonObject();
            String description = weather.get("description").getAsString();
            String main_sky = weather.get("main").getAsString();
            JsonObject wind = jsonResponse.getAsJsonObject("wind");
            String wind_speed = wind.get("speed").getAsString();

            // Construct array of weather info in string
            String[] weatherInfo = {temperature, feels_like, min_temp, max_temp
            ,humidity,description,main_sky,wind_speed}; 
            // These are string, so if needed they must be converted to rounded 
            // integer (except for wind speed can be double) to be displayed on GUI

            return weatherInfo;            
        } catch (IOException e) {
            String [] error = {"ERROR","ERROR","ERROR","ERROR","ERROR","ERROR","ERROR"
            ,"ERROR"};
            return error;
        } catch (IndexOutOfBoundsException e) {
            String [] error = {"ERROR","ERROR","ERROR","ERROR","ERROR","ERROR","ERROR"
            ,"ERROR"};
            return error;
        }
    }
    
    /**
     * Retrieves today + next 3 days weather forecast for the same location coordinates
     * with the getCurrentWeather function.
     *
     * @param lat The latitude of the location.
     * @param lon The longitude of the location.
     * 
     * @return A String array of string arrays in which each child array represent 
     * the weather of a day (starting from today). Each child String array contains the date,
     * main temperature, min, max (in Celsius) and sky.
     * @throws IOException If an I/O exception occurs while connecting to the weather API.
     */    
    @Override
    public String[][] getForecast(double lat, double lon) {
        // Build the URL for the API call
        String url_String = "http://api.openweathermap.org/data/2.5/forecast/daily"
                + "?lat=" + lat
                + "&lon=" + lon
                + "&cnt=4" // 4 days including today
                + "&appid=" + API_KEY + "&units=metric";

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

                // Extract the forecast data for the upcoming 4 days including today
                JsonArray forecastList = jsonObject.getAsJsonArray("list");
                
                JsonObject day0 = forecastList.get(0).getAsJsonObject();
                JsonObject day1 = forecastList.get(1).getAsJsonObject();
                JsonObject day2 = forecastList.get(2).getAsJsonObject();
                JsonObject day3 = forecastList.get(3).getAsJsonObject();
                
                // Extract relevant information
                // date and time 
                long  day0_date_time = day0.get("dt").getAsLong();
                String day0Date = new java.text.SimpleDateFormat("dd/MM").format(new java.util.Date (day0_date_time * 1000));
                long  day1_date_time = day1.get("dt").getAsLong();
                String day1Date = new java.text.SimpleDateFormat("dd/MM").format(new java.util.Date (day1_date_time * 1000));
                long  day2_date_time = day2.get("dt").getAsLong();
                String day2Date = new java.text.SimpleDateFormat("dd/MM").format(new java.util.Date (day2_date_time * 1000));
                long  day3_date_time = day3.get("dt").getAsLong();
                String day3Date = new java.text.SimpleDateFormat("dd/MM").format(new java.util.Date (day3_date_time * 1000));
                
                // main temperature
                String day0Temp = day0.getAsJsonObject("temp").get("day").getAsString();
                String day1Temp = day1.getAsJsonObject("temp").get("day").getAsString();
                String day2Temp = day2.getAsJsonObject("temp").get("day").getAsString();
                String day3Temp = day3.getAsJsonObject("temp").get("day").getAsString();
                
                // min temperature
                String day0min = day0.getAsJsonObject("temp").get("min").getAsString();
                String day1min = day1.getAsJsonObject("temp").get("min").getAsString();
                String day2min = day2.getAsJsonObject("temp").get("min").getAsString();
                String day3min = day3.getAsJsonObject("temp").get("min").getAsString();                
                
                // max temperature
                String day0max = day0.getAsJsonObject("temp").get("max").getAsString();
                String day1max = day1.getAsJsonObject("temp").get("max").getAsString();
                String day2max = day2.getAsJsonObject("temp").get("max").getAsString();
                String day3max = day3.getAsJsonObject("temp").get("max").getAsString(); 
                
                // sky
                String day0sky = day0.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
                String day1sky = day1.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
                String day2sky = day2.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
                String day3sky = day3.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
                                
                // Display the forecast for the next two days
                String[][] weather_forecast = {
                    {day0Date, day0Temp, day0min, day0max, day0sky}, // Today's data
                    {day1Date, day1Temp, day1min, day1max, day1sky}, // One day after's data
                    {day2Date, day2Temp, day2min, day2max, day2sky}, // Two days after's data
                    {day3Date, day3Temp, day3min, day3max, day3sky} // Three days after's data
                    // These are string, so the temperature value should be 
                    // converted to rounded integers to be displayed on the GUI
                };
                return weather_forecast;
            } else {
                System.out.println("Error: " + responseCode);
                String [][] error_here = {
                    {"ERROR", "ERROR", "ERROR", "ERROR"}, 
                    {"ERROR", "ERROR", "ERROR", "ERROR"}, 
                    {"ERROR", "ERROR", "ERROR", "ERROR"}, 
                    {"ERROR", "ERROR", "ERROR", "ERROR"} 
                };
                return error_here;
            }
        } catch (IOException e) {
            e.printStackTrace();
                String [][] error_here = {
                    {"ERROR", "ERROR", "ERROR", "ERROR"}, 
                    {"ERROR", "ERROR", "ERROR", "ERROR"}, 
                    {"ERROR", "ERROR", "ERROR", "ERROR"}, 
                    {"ERROR", "ERROR", "ERROR", "ERROR"} 
                };
                return error_here;
        }
    }
}

