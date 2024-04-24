/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package fi.tuni.prog3.weatherapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author bcmivu
 */
public class WeatherDataTest {
    
    public WeatherDataTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of get_error_flag method, of class WeatherData.
     */
    @Test
    public void testGet_error_flag() {
         // Initialize WeatherData instance
        WeatherData instance = new WeatherData("metric");
        
        // Verify initial error flag is false
        assertFalse(instance.get_error_flag(), "Initial error flag should be false.");
    }

    /**
     * Test of getHourlyForecast method, of class WeatherData.
     */
    @Test
    public void testGetHourlyForecast() {
        // Initialize WeatherData instance
        WeatherData instance = new WeatherData("metric");

        // Define latitude and longitude for testing (replace with actual values)
        double lat = 40.7128; // New York City latitude
        double lon = -74.0060; // New York City longitude

        // Call the method
        String[][] result = instance.getHourlyForecast(lat, lon);

        // Verify that the result is not null
        assertNotNull(result, "Hourly forecast should not be null.");

        // Verify that the result has 24 rows (one for each hour)
        assertEquals(24, result.length, "Hourly forecast should contain 24 rows.");

        // Verify that each row has 5 columns
        for (String[] row : result) {
            assertEquals(5, row.length, "Each row in the hourly forecast should contain 5 columns.");
        }
    }

    /**
     * Test of lookUpLocation method, of class WeatherData.
     */
    @Test
    public void testLookUpLocation_ValidLocation() {
        WeatherData weatherData = new WeatherData("metric");
        String loc_name = "New York";
        String state_name = "New York";
        String country_code = "US";
        
        String[] result = weatherData.lookUpLocation(loc_name, state_name, country_code);
        
        assertNotNull(result);
        assertEquals(4, result.length);
        assertFalse(weatherData.get_error_flag());
    }
    
    @Test
    public void testLookUpLocation_InvalidLocation() {
        WeatherData weatherData = new WeatherData("metric");
        String loc_name = "Invalid Location";
        String state_name = "";
        String country_code = "";
        
        String[] result = weatherData.lookUpLocation(loc_name, state_name, country_code);
        
        assertNull(result);
        assertTrue(weatherData.get_error_flag());
    }
    
    @Test
    public void testGetCurrentWeather_ValidCoordinates() {
        WeatherData weatherData = new WeatherData("metric");
        double lat = 40.7128; // Latitude of New York City
        double lon = -74.0060; // Longitude of New York City
        
        String[] result = weatherData.getCurrentWeather(lat, lon);
        
        assertNotNull(result);
        assertEquals(9, result.length);
        assertFalse(result[0].equals("ERROR")); // Checking if temperature is not an error
    }
    
    @Test
    public void testGetCurrentWeather_InvalidCoordinates() {
        WeatherData weatherData = new WeatherData("metric");
        double lat = 1000; // Invalid latitude
        double lon = 2000; // Invalid longitude
        
        String[] result = weatherData.getCurrentWeather(lat, lon);
        
        assertNotNull(result);
        assertEquals(8, result.length); // Excluding visibility
        assertEquals("ERROR", result[0]); // Checking if temperature is an error
    }

    /**
     * Test of getForecast method, of class WeatherData.
     */
    @Test
    public void testGetForecast() {
        System.out.println("getForecast");
        double lat = 40.7128; // New York latitude
        double lon = -74.0060; // New York longitude
        WeatherData instance = new WeatherData("metric"); // Using metric unit for testing

        // Call the method to get the forecast
        String[][] result = instance.getForecast(lat, lon);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result array has 4 days forecast data
        assertEquals(4, result.length);

        // Assert that each day's forecast has 5 pieces of information
        assertEquals(5, result[0].length);

        // Assert that each piece of information is not null or empty
        for (String[] dayForecast : result) {
            assertNotNull(dayForecast[0]); // Date
            assertNotNull(dayForecast[1]); // Main temperature
            assertNotNull(dayForecast[2]); // Min temperature
            assertNotNull(dayForecast[3]); // Max temperature
            assertNotNull(dayForecast[4]); // Sky condition
            assertFalse(dayForecast[0].isEmpty()); // Date
            assertFalse(dayForecast[1].isEmpty()); // Main temperature
            assertFalse(dayForecast[2].isEmpty()); // Min temperature
            assertFalse(dayForecast[3].isEmpty()); // Max temperature
            assertFalse(dayForecast[4].isEmpty()); // Sky condition
        }
    }
    
}
