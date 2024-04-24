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
import com.google.gson.JsonSyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import java.util.NoSuchElementException;

public class WeatherData implements iMyAPI {

    private final String API_KEY;
    private String UNIT;
    private boolean ERROR_LOCATION;
    /**
     * Constructor that initializes the API key and the unit for temperature
     * @param unit 
     */
    public WeatherData(String unit){
        API_KEY = "7e2aaa9d361a771034770163dde8b02c";  
        UNIT = unit;
        ERROR_LOCATION = false;
    }
    
    public boolean get_error_flag(){
        return ERROR_LOCATION;
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
     * Retrieves hourly weather forecast data for the specified latitude and longitude coordinates.
     *
     * @param lat The latitude of the location for which to retrieve the forecast data.
     * @param lon The longitude of the location for which to retrieve the forecast data.
     * @return A 2D String array containing hourly forecast data for the next 24 hours. Each row represents a forecast hour,
     *         and each column contains specific information in the following order: timestamp, temperature, minimum temperature,
     *         maximum temperature, weather description, and humidity.
     */
    @Override
    public String[][] getHourlyForecast(double lat, double lon) {
        String[][] hourlyForecast = new String[24][5]; // 24 is 24 timestamps
        // 5 is for time, temperature, wind speed, sky, humidity 
        
        try {
            String urlString = "https://pro.openweathermap.org/data/2.5/forecast/hourly?lat=" + lat + "&lon=" + lon + "&appid=" + API_KEY+"&cnt=24"+ "&units=" + UNIT;
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
           
            // Retrieve timezone from JSON response
            int timezoneOffset = jsonResponse.get("city").getAsJsonObject().get("timezone").getAsInt();
            ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(timezoneOffset);
            ZoneId zoneId = ZoneId.ofOffset("UTC", zoneOffset);

            int index = 0; 
            for (JsonElement element : hourlyData) { 
                JsonObject dataPoint = element.getAsJsonObject();                
                long timestampSeconds = dataPoint.get("dt").getAsLong(); // Timestamp in seconds
                // Convert timestamp to LocalDateTime in the time zone of the location               
                LocalDateTime localDateTime = LocalDateTime.ofInstant(java.time.Instant.ofEpochSecond(timestampSeconds), zoneId);
                String localDateTimeString = localDateTime.format(java.time.format.DateTimeFormatter.ofPattern("HH"));
                
                // String timestamp = dataPoint.get("dt_txt").getAsString();
                double temp = dataPoint.getAsJsonObject("main").get("temp").getAsDouble();
                long temp_rounded = Math.round(temp);
                
                String unitSuffix = "metric".equals(UNIT) ? "°C" : "°F";
                String temperature = String.valueOf(temp_rounded) + unitSuffix;
                String weatherMain = dataPoint.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
                String humidity = dataPoint.getAsJsonObject("main").get("humidity").getAsString();
                double windSpeed = dataPoint.getAsJsonObject("wind").get("speed").getAsDouble();
                String windSpeedFormatted = String.valueOf(windSpeed) + (UNIT.equals("metric") ? "m/s" : "mph");
                // Store data in array
                hourlyForecast[index][0] = localDateTimeString;
                hourlyForecast[index][1] = temperature;
                hourlyForecast[index][2] = windSpeedFormatted;
                hourlyForecast[index][3] = weatherMain;
                hourlyForecast[index][4] = humidity + "%";

                index++;                
                
            }                           
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return hourlyForecast;
    
    }
    
    // implement iAPI
    /**
     * Return the coordinate, the state (if it is a US location), and the country code 
     * of the wanted location. If the user only provides two parameters, the function 
     * automatically uses them as loc_name and country_code. If the user wants to specify 
     * the state, they must provide all three parameters separated by commas.
     *  
     * @param loc_name: name of the location
     * @param state_name: name of the state if it's an US location
     * @param country_code: country code
     * @return a String array that contains latitude, longitude, state or empty string,
     * and country code.
     */
    @Override
    public String[] lookUpLocation(String loc_name, String state_name, String country_code) {
        // changing the return type to map <country_code, double[]>
        final String Open_weather_geocoding = "http://api.openweathermap.org/geo/1.0/direct";
        ERROR_LOCATION = false;
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
            JsonElement json_element = json_array.get(0);  // json_array actually only has 1 element
            String latitude = json_element.getAsJsonObject().get("lat").getAsString();
            String longitude = json_element.getAsJsonObject().get("lon").getAsString();
            String Country = json_element.getAsJsonObject().get("country").getAsString();
            String state = ""; // Default value is an empty string
            JsonElement stateElement = json_element.getAsJsonObject().get("state");
            if (stateElement != null) {
                state = stateElement.getAsString(); // Update state if it's present in the JSON
            }
            return new String[] {latitude, longitude,state,Country};
        }
        
        catch(IOException e){
            e.printStackTrace();
            ERROR_LOCATION = true;
            System.out.println("Catch block IOException is executed");
            return null;          
    }   
        catch (JsonSyntaxException | NoSuchElementException | IndexOutOfBoundsException e) {
        ERROR_LOCATION = true;
        System.out.println("Error API query due to mismatched loc_name and country_code");
        return null;       
    }
        
        finally{ 
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

     * @return       An array of string containing the current temperature, 
     * feels like, min and max temperature (in metric, Celsius), humidity, description,
     * sky, and wind speed (in metric, m/s), and visibility IN THIS ORDER.
     * 
     * Returns an array of string with all elements are "Error" if error occurs during 
     * the retrieval process.
     */    
    @Override
    public String[] getCurrentWeather(double lat, double lon) {
        final String Open_weather = "http://api.openweathermap.org/data/2.5/weather";
        
        // query 
        String query = "lat=" + lat + "&lon=" + lon + "&appid=" + API_KEY + "&units=" + UNIT;
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
            double visibility = jsonResponse.get("visibility").getAsDouble();
            visibility = visibility / 1000;
            String Visibility = String.valueOf(visibility) + "km";
            
            JsonObject wind = jsonResponse.getAsJsonObject("wind");
            String wind_speed = wind.get("speed").getAsString();
            JsonObject main = jsonResponse.getAsJsonObject("main");          
            double temperature_double = main.get("temp").getAsDouble();
            double feels_like_double = main.get("feels_like").getAsDouble();
            double min_temp_double = main.get("temp_min").getAsDouble();
            double max_temp_double = main.get("temp_max").getAsDouble();
             // Round the double values to the nearest integer
            long roundedTemperature = Math.round(temperature_double);
            long roundedFeelsLike = Math.round(feels_like_double);
            long roundedMinTemp = Math.round(min_temp_double);
            long roundedMaxTemp = Math.round(max_temp_double);
            // Convert to String
            String temperature = String.valueOf(roundedTemperature);
            String feels_like = String.valueOf(roundedFeelsLike);
            String min_temp = String.valueOf(roundedMinTemp);
            String max_temp = String.valueOf(roundedMaxTemp);  
            
            // Add unit
            if ("metric".equals(UNIT)){
              temperature += "°C";
              feels_like += "°C";
              min_temp += "°C";
              max_temp += "°C";  
              wind_speed += "m/s";
            } else if ("imperial".equals(UNIT)){
              temperature += "°F";
              feels_like += "°F";
              min_temp += "°F";
              max_temp += "°F";  
              wind_speed +="mph";
            }
            
            String humidity = main.get("humidity").getAsString();
            humidity += "%";
            JsonObject weather = jsonResponse.getAsJsonArray("weather").get(0).getAsJsonObject();
            String description = weather.get("description").getAsString();
            String main_sky = weather.get("main").getAsString();
            
            String[] weatherInfo = {temperature, feels_like, min_temp, max_temp,
                                humidity, description, main_sky, wind_speed,Visibility}; 

            return weatherInfo;            
        } catch (IOException e) {
            e.printStackTrace();
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
                + "&appid=" + API_KEY + "&units=" + UNIT;

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
                
                String[][] weather_forecast = new String[4][5]; // 4 days, each with 5 pieces of information

            for (int i = 0; i < forecastList.size(); i++) {
                JsonObject day = forecastList.get(i).getAsJsonObject();

                long dateTime = day.get("dt").getAsLong();
                String date = new java.text.SimpleDateFormat("dd/MM").format(new java.util.Date(dateTime * 1000));

                double tempDouble = day.getAsJsonObject("temp").get("day").getAsDouble();
                long tempRounded = Math.round(tempDouble);

                double minTempDouble = day.getAsJsonObject("temp").get("min").getAsDouble();
                long minTempRounded = Math.round(minTempDouble);

                double maxTempDouble = day.getAsJsonObject("temp").get("max").getAsDouble();
                long maxTempRounded = Math.round(maxTempDouble);

                String sky = day.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();

                String unitSuffix = "metric".equals(UNIT) ? "°C" : "°F";

                weather_forecast[i][0] = date;
                weather_forecast[i][1] = String.valueOf(tempRounded) + unitSuffix;
                weather_forecast[i][2] = String.valueOf(minTempRounded)+ unitSuffix;
                weather_forecast[i][3] = String.valueOf(maxTempRounded)+ unitSuffix;
                weather_forecast[i][4] = sky;
            }

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
    


