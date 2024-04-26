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
public class ImageHandlerTest {
    
    public ImageHandlerTest() {
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
     * Test of currentImageHandler method, of class ImageHandler.
     */
    @Test
    public void testCurrentImageHandler() {
        System.out.println("Testing currentImageHandler");
        String[] weatherData = {"", "", "", "", "", "", "Clear", "", "12", "5", "18"};
        ImageHandler instance = new ImageHandler();
        String expResult = "/icons/day-clear.png";
        String result = instance.currentImageHandler(weatherData);
        assertEquals(expResult, result);
    }

    /**
     * Test of hourlyImageHandler method, of class ImageHandler.
     */
    @Test
    public void testHourlyImageHandler() {
        System.out.println("Testing hourlyImageHandler");
        String[] weatherData = {"0", "", "", "Clear", "", "", ""};
        String sunrise = "5";
        String sunset = "18";
        ImageHandler instance = new ImageHandler();
        String expResult = "/icons/night-clear.png";
        String result = instance.hourlyImageHandler(weatherData, sunrise, sunset);
        assertEquals(expResult, result);
    }

    /**
     * Test of forecastImageHandler method, of class ImageHandler.
     */
    @Test
    public void testForecastImageHandler() {
        System.out.println("Testing forecastImageHandler");
        String weatherType = "Clear";
        ImageHandler instance = new ImageHandler();
        String expResult = "/icons/day-clear.png";
        String result = instance.forecastImageHandler(weatherType);
        assertEquals(expResult, result);
    }

    /**
     * Test of imageReturn method, of class ImageHandler.
     */
    @Test
    public void testImageReturn() {
        System.out.println("Testing imageReturn");
        int comparedTime = 12;
        int sunrise = 5;
        int sunset = 18;
        String weatherType = "Clear";
        ImageHandler instance = new ImageHandler();
        String expResult = "/icons/day-clear.png";
        String result = instance.imageReturn(comparedTime, sunrise, sunset, weatherType);
        assertEquals(expResult, result);
    }
    
}
