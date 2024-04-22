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
    private final iMyAPI weatherAPI = new WeatherData(); 
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
     * This function displays the current weather data just to 
     * demonstrate that the weather data has been successfully extracted from OpenWeather.
     * The weather data is displayed in the top panel
     */
    private void display_current_Weather() {
        // Main thing is to ask user to specify the location
        Object weather_location_object = weatherAPI.lookUpLocation("London","",""); // Wrong country code fail the program
        double[] weather_location = (double[]) weather_location_object;
    
        // If the returned map has more than 1 element, must specify the exact wanted location.
        // Otherwise, just extract latitude and longitude
        double latitude = weather_location[0];
        double longitude = weather_location[1];
        
        String[] weatherData = weatherAPI.getCurrentWeather(latitude,longitude );
        StringBuilder sb = new StringBuilder();
        for (String str : weatherData) {
            str += " ";
            sb.append(str);
            System.out.println(str);         
        }
        String string_data = sb.toString();
        String display = "Weather" + ": \n" + string_data;

        // Update label in the top panel with weather data
        Label topLabel = (Label) ((HBox) ((VBox) ((BorderPane) ((Scene) Stage
                .getWindows().stream().findFirst().orElse(null).getScene()).getRoot())
                .getCenter()).getChildren().get(0)).getChildren().get(0);

        topLabel.setText(display);
    } 
    
     /**
     * This function displays today and the next 3 days weather forecast data 
     * just to demonstrate that the weather data has been successfully extracted 
     * from OpenWeather.
     * The weather data is displayed in the below panel
     */
    private void display_forecast(){
        // Main thing is to ask user to specify the location
        Object weather_location_object = weatherAPI.lookUpLocation("Ha Noi","","VN"); 
        double[] weather_location = (double[]) weather_location_object;
    
        // If the returned map has more than 1 element, must specify the exact wanted location.
        // Otherwise, just extract latitude and longitude
        double latitude = weather_location[0];
        double longitude = weather_location[1];
        
        String forecast_data[][] = weatherAPI.getForecast(latitude,longitude );
        

        for (String[] day : forecast_data) {
            for (String info : day) {
                System.out.print(info + " ");
            }
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