package fi.tuni.prog3.weatherapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.text.SimpleDateFormat;
import java.util.Map;


/**
 * JavaFX Weather Application.
 */
public class WeatherApp extends Application {
    private final iAPI weatherAPI = new WeatherData("metric"); 
    // By default the unit of the program is metric

    @Override
    public void start(Stage stage) {

        //Creating a new BorderPane.
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));

        //Adding HBox to the center of the BorderPane.
        root.setCenter(getCenterVBox());

        //Adding button to the BorderPane and aligning it to the right.
        var quitButton = getQuitButton();
        BorderPane.setMargin(quitButton, new Insets(10, 10, 0, 10));
        root.setBottom(quitButton);
        BorderPane.setAlignment(quitButton, Pos.TOP_RIGHT);

        Scene scene = new Scene(root, 500, 700);
        stage.setScene(scene);
        stage.setTitle("WeatherApp");
        stage.show();
        
        display_current_Weather(); // Testing the extracted data from OpenWeather
        display_forecast(); // Testing the extracted data from OpenWeather
    }

    public static void main(String[] args) {
        launch();
    }
    
    /**
     * Testing lookUpLocation and getCurrentWeather
     */
    private void display_current_Weather() {
        String[] weather_location = weatherAPI.lookUpLocation("New York","",""); 
             
        if(weather_location != null){
        double latitude = Double.parseDouble(weather_location[0]);
        double longitude = Double.parseDouble(weather_location[1]);
        System.out.println(weather_location[2]+" "+ weather_location[3]);
        // Testing output of the function
        String[] weatherData = weatherAPI.getCurrentWeather(latitude,longitude );

        for (String str : weatherData) {
            System.out.println(str);         
        }
        }
       
    } 
    
     /**
     * Testing lookUpLocation, getForecast, and getHourlyForecast
     */
    private void display_forecast(){
        // Main thing is to ask user to specify the location
        String[] weather_location = weatherAPI.lookUpLocation("Tampere","","FI"); 
      
        double latitude = Double.parseDouble(weather_location[0]);
        double longitude = Double.parseDouble(weather_location[1]);
        
        // Testing output of the two functions
        String[][] forecast_data = weatherAPI.getForecast(latitude,longitude );
        String[][] hourly_forecast = weatherAPI.getHourlyForecast(latitude, longitude);
        
        
        
        for (String[] day : forecast_data) { // priting forecast data
            for (String info : day) {
                System.out.print(info + " ");
            }
            System.out.println();
        }
        
                // Loop through each row
        for (int i = 0; i < hourly_forecast.length; i++) { // printing hourly forecast data
            // Loop through each column in the current row
            for (int j = 0; j < hourly_forecast[i].length; j++) {
                // Print the current element
                System.out.print(hourly_forecast[i][j] + " ");
            }
            // Move to the next line after printing all elements in the row
            System.out.println();
        }
    }
    

    private VBox getCenterVBox() {
        //Creating an HBox.
        VBox centerHBox = new VBox(10);

        //Adding two VBox to the HBox.
        centerHBox.getChildren().addAll(getTopHBox(), getBottomHBox());

        return centerHBox;
    }

    private HBox getTopHBox() {
        //Creating a VBox for the left side.
        HBox leftHBox = new HBox();
        leftHBox.setPrefHeight(330);
        leftHBox.setStyle("-fx-background-color: #8fc6fd;");

        leftHBox.getChildren().add(new Label("Top Panel"));

        return leftHBox;
    }

    private HBox getBottomHBox() {
        //Creating a VBox for the right side.
        HBox rightHBox = new HBox();
        rightHBox.setPrefHeight(330);
        rightHBox.setStyle("-fx-background-color: #b1c2d4;");

        rightHBox.getChildren().add(new Label("Bottom Panel"));

        return rightHBox;
    }

    private Button getQuitButton() {
        //Creating a button.
        Button button = new Button("Quit");

        //Adding an event to the button to terminate the application.
        button.setOnAction((ActionEvent event) -> {
            Platform.exit();
        });

        return button;
    }
}