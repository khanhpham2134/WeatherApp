package fi.tuni.prog3.weatherapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.BlurType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

import java.util.Map;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;



/**
 * JavaFX Weather Application.
 */
public class WeatherApp extends Application { 
    // By default the unit of the program is metric
    private final DisplayHandler displayHandler = new DisplayHandler();

    
    private String cityName;
    private TextField searchBar;
    private Button searchButton;
    private ToggleButton saveFavButton;

    private final ImageHandler imageHandler = new ImageHandler();
    private boolean isMetric = true;
    private String[] currentCityData = {};


    @Override
    public void start(Stage stage) {        
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));
        
        // Few Days Forecast
        VBox fewDaysForecast = new VBox(25);
        fewDaysForecast.setPrefSize(290, 275);
        fewDaysForecast.setPadding(new Insets(10, 10 , 10, 10));
        fewDaysForecast.setStyle("-fx-background-color: #b8e2f2;");
        
        HBox day1 = new HBox(15);
        day1.setAlignment(Pos.CENTER);
        Text date1 = new Text("TODAY");
        date1.setStyle("-fx-font: 40 arial;");
        Text temp1 = new Text("0*C");
        temp1.setStyle("-fx-font: 28 arial;");
        Text logo1 = new Text("logo");
        Region r = new Region();
        HBox.setHgrow(r, Priority.ALWAYS);
        day1.getChildren().addAll(date1, r, temp1, logo1);
        
        HBox day2 = new HBox(15);
        day2.setAlignment(Pos.CENTER);
        Text date2 = new Text("TODAY");
        date2.setStyle("-fx-font: 40 arial;");
        Text temp2 = new Text("0*C");
        temp2.setStyle("-fx-font: 28 arial;");
        Text logo2 = new Text("logo");
        Region r2 = new Region();
        HBox.setHgrow(r2, Priority.ALWAYS);
        day2.getChildren().addAll(date2, r2, temp2, logo2);
        
        HBox day3 = new HBox(15);
        day3.setAlignment(Pos.CENTER);
        Text date3 = new Text("TODAY");
        date3.setStyle("-fx-font: 40 arial;");
        Text temp3 = new Text("0*C");
        temp3.setStyle("-fx-font: 28 arial;");
        Text logo3 = new Text("logo");
        Region r3 = new Region();
        HBox.setHgrow(r3, Priority.ALWAYS);
        day3.getChildren().addAll(date3, r3, temp3, logo3);
        
        HBox day4 = new HBox(15);
        day4.setAlignment(Pos.CENTER);
        Text date4 = new Text("TODAY");
        date4.setStyle("-fx-font: 40 arial;");
        Text temp4 = new Text("0*C");
        temp4.setStyle("-fx-font: 28 arial;");
        Text logo4 = new Text("logo");
        Region r4 = new Region();
        HBox.setHgrow(r4, Priority.ALWAYS);
        day4.getChildren().addAll(date4, r4, temp4, logo4);
        
        fewDaysForecast.getChildren().addAll(day1, day2, day3, day4);
        
        // Hourly Forecast
        GridPane hourlyForecast = new GridPane();
        hourlyForecast.setHgap(10);
        hourlyForecast.setVgap(10);
        Text[][] textList = new Text[24][6];
        for (int hour = 0; hour < 24; hour++) {  
            Text hourText = new Text();
            hourText.setStyle("-fx-font: 20 arial; -fx-font-weight: bold;");
            hourlyForecast.add(hourText, hour, 0);
            Text degree = new Text();
            degree.setStyle("-fx-font: 20 arial;");
            hourlyForecast.add(degree, hour, 1);
            Text logo = new Text();
            logo.setStyle("-fx-font: 20 arial;");
            hourlyForecast.add(logo, hour, 2);
            Text lowestTempHourly = new Text();
            lowestTempHourly.setStyle("-fx-font: 20 arial;");
            hourlyForecast.add(lowestTempHourly, hour, 3);
            Text highestTempHourly = new Text();
            highestTempHourly.setStyle("-fx-font: 20 arial;");
            hourlyForecast.add(highestTempHourly, hour, 4);
            Text humidity = new Text();
            humidity.setStyle("-fx-font: 20 arial;");
            hourlyForecast.add(humidity, hour, 5); 
            textList[hour][0] = hourText;
            textList[hour][1] = degree;
            textList[hour][2] = logo;
            textList[hour][3] = lowestTempHourly;
            textList[hour][4] = highestTempHourly;
            textList[hour][5] = humidity;
        }
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(650, 200);
        scrollPane.setContent(hourlyForecast);
        
        // Current Weather
        BorderPane currentWeather = new BorderPane();
        currentWeather.setPrefSize(390, 275);
        currentWeather.setPadding(new Insets(10, 10 , 10, 10));
        currentWeather.setStyle("-fx-background-color: #ffffc2");
        
        VBox lowestTempBox = new VBox();
        lowestTempBox.setAlignment(Pos.CENTER);
        Text lowestTemp = new Text("L: ");
        lowestTemp.setStyle("-fx-font: 20 arial;");
        lowestTempBox.getChildren().add(lowestTemp);
        currentWeather.setLeft(lowestTempBox);
    
        VBox highestTempBox = new VBox();
        highestTempBox.setAlignment(Pos.CENTER);
        Text highestTemp = new Text("H: ");
        highestTemp.setStyle("-fx-font: 20 arial;");
        highestTempBox.getChildren().add(highestTemp);
        currentWeather.setRight(highestTempBox);
    
        VBox currentWeatherTextBox = new VBox(10);
        currentWeatherTextBox.setAlignment(Pos.CENTER);
        Label currentWeatherLabel = new Label("CURRENT WEATHER");
        currentWeatherLabel.setStyle("-fx-font: 24 arial; -fx-font-weight: bold;");
        Text city = new Text();
        city.setStyle("-fx-font: 20 arial;");
        currentWeatherTextBox.getChildren().addAll(currentWeatherLabel, city);
        currentWeather.setTop(currentWeatherTextBox);
    
        VBox currentWeatherDataBox = new VBox();
        currentWeatherDataBox.setAlignment(Pos.CENTER);
        Text temp = new Text("12*c");

        temp.setStyle("-fx-font: 45 arial;");       
        Image description = new Image(getClass().getResourceAsStream("/icons/day-clear.png"));
        ImageView descriptionView = new ImageView(description);
        descriptionView.setFitHeight(90);
        descriptionView.setFitWidth(90);
        currentWeatherDataBox.getChildren().addAll(temp, descriptionView);

        currentWeather.setCenter(currentWeatherDataBox);
        BorderPane additionalDataBox = new BorderPane();
        VBox data = new VBox();
        Text feelsLike = new Text("FEELS LIKE: ");
        feelsLike.setStyle("-fx-font: 23 arial;");
        Text humid = new Text("HUMIDITY: "); 
        humid.setStyle("-fx-font: 23 arial;");
        Text wind = new Text("WIND SPEED: ");
        wind.setStyle("-fx-font: 23 arial;");
        data.getChildren().addAll(feelsLike, humid, wind);
        additionalDataBox.setLeft(data);
        VBox buttons = new VBox(15);
        // Load previously saved favourites
        readToFile();
        updateFavBox();
        
        //Button setFav = new Button("Favorite");
        saveFavButton = new ToggleButton("Save as favourite");

        // Update the button state based on the favorite status
        updatesaveFavButtonState();

        // Add event handler to toggle the favorite status of the city when the button is clicked
        saveFavButton.setOnAction(event -> {
            if (saveFavButton.isSelected()) {
                // If the button is selected, add the city to favorites
                favourites.add(cityName);
            } else {
                // If the button is not selected, remove the city from favorites
                favourites.remove(cityName);
            } 
            updateFavBox();
            updatesaveFavButtonState(); // Update the button state after toggling favorite status
        });
 
        
       
        saveFavButton.setPrefWidth(100);
        Button changeUnit = new Button("Change Unit");
        changeUnit.setPrefWidth(100);
        changeUnit.setOnAction((ActionEvent event) -> {

            if (!isMetric) {

                // Change current weather section
                String[] currentWeatherData = displayHandler.getCurrentWeatherDataMetric(currentCityData);
                temp.setText(currentWeatherData[0]);
                feelsLike.setText("FEELS LIKE: " + currentWeatherData[1]);
                lowestTemp.setText("L: " + currentWeatherData[2]);
                highestTemp.setText("H: " + currentWeatherData[3]);
                humid.setText("HUMIDITY: " + currentWeatherData[4]);
                
                wind.setText("WIND SPEED: " + currentWeatherData[7]);
                
                // Change daily forecast
                String[][] dailyForecast = displayHandler.getDailyForecastMetric(currentCityData);
                date1.setText(dailyForecast[0][0]);
                temp1.setText(dailyForecast[0][1]);
                date2.setText(dailyForecast[1][0]);
                temp2.setText(dailyForecast[1][1]);
                date3.setText(dailyForecast[2][0]);
                temp3.setText(dailyForecast[2][1]);
                date4.setText(dailyForecast[3][0]);
                temp4.setText(dailyForecast[3][1]);
                
                // Change hourly forecast
                String[][] hourlyForecastData = displayHandler.getHourlyForecastMetric(currentCityData);
                for (int hour = 0; hour < 24; hour++) {
                    textList[hour][0].setText(hourlyForecastData[hour][0].substring(11, 16));
                    textList[hour][1].setText(hourlyForecastData[hour][1]);
                    textList[hour][2].setText(hourlyForecastData[hour][2]);
                    textList[hour][3].setText(hourlyForecastData[hour][3]);
                    textList[hour][4].setText(hourlyForecastData[hour][4]);
                    textList[hour][5].setText(hourlyForecastData[hour][5]);
                }

                isMetric = true;
            } else {
                // Change current weather section
                String[] currentWeatherData = displayHandler.getCurrentWeatherDataImperial(currentCityData);
                temp.setText(currentWeatherData[0]);
                feelsLike.setText("FEELS LIKE: " + currentWeatherData[1]);
                lowestTemp.setText("L: " + currentWeatherData[2]);
                highestTemp.setText("H: " + currentWeatherData[3]);
                humid.setText("HUMIDITY: " + currentWeatherData[4]);
                
                wind.setText("WIND SPEED: " + currentWeatherData[7]);
                
                // Change daily forecast
                String[][] dailyForecast = displayHandler.getDailyForecastImperial(currentCityData);
                date1.setText(dailyForecast[0][0]);
                temp1.setText(dailyForecast[0][1]);
                date2.setText(dailyForecast[1][0]);
                temp2.setText(dailyForecast[1][1]);
                date3.setText(dailyForecast[2][0]);
                temp3.setText(dailyForecast[2][1]);
                date4.setText(dailyForecast[3][0]);
                temp4.setText(dailyForecast[3][1]);
                
                // Change hourly forecast
                String[][] hourlyForecastData = displayHandler.getHourlyForecastImperial(currentCityData);
                for (int hour = 0; hour < 24; hour++) {
                    textList[hour][0].setText(hourlyForecastData[hour][0].substring(11, 16));
                    textList[hour][1].setText(hourlyForecastData[hour][1]);
                    textList[hour][2].setText(hourlyForecastData[hour][2]);
                    textList[hour][3].setText(hourlyForecastData[hour][3]);
                    textList[hour][4].setText(hourlyForecastData[hour][4]);
                    textList[hour][5].setText(hourlyForecastData[hour][5]);
                }

                isMetric = false;
            }
        });
        buttons.getChildren().addAll(saveFavButton, changeUnit);
        additionalDataBox.setRight(buttons);
        currentWeather.setBottom(additionalDataBox);

        // Search Bar
        HBox searchBarSection = new HBox();
        
        
        searchBar = new TextField();
        searchBar.setPrefWidth(475);
        searchBar.setPromptText("City, (State,) Country Code");
        
        searchButton = new Button("Search");
        searchButton.setOnAction((ActionEvent event) -> {
            if (isMetric) {
                boolean inputIsInvalid = displayHandler.ifInputValid(searchBar);
                String input = searchBar.getText();
                if (input.split(", ", 3)[0].strip() == "") {
                    Alert a = new Alert(AlertType.WARNING); 
                    a.setContentText("Blank Input");
                    a.show();
                    searchBar.clear();
                } else if (inputIsInvalid) {
                    Alert a = new Alert(AlertType.ERROR);
                    a.setContentText("Location Not Found. Please provide a valid city name!");
                    a.show();
                    searchBar.clear();
                } else {
                    // Change the city name
                    cityName = searchBar.getText().split(",", 3)[0];
                    updatesaveFavButtonState();
                    String[] cityInfo = displayHandler.getCityInformation(searchBar);
                    currentCityData = cityInfo;
                    
                    if (cityInfo[2] == "") {
                        city.setText(cityName.toUpperCase() + ", " + cityInfo[3]);
                    } else {
                        city.setText(cityName.toUpperCase() + ", " + cityInfo[2] + ", " + cityInfo[3]);
                    }
                    
                    // Change current weather section
                    String[] currentWeatherData = displayHandler.getCurrentWeatherDataMetric(cityInfo);
                    temp.setText(currentWeatherData[0]);
                    feelsLike.setText("FEELS LIKE: " + currentWeatherData[1]);
                    lowestTemp.setText("L: " + currentWeatherData[2]);
                    highestTemp.setText("H: " + currentWeatherData[3]);
                    humid.setText("HUMIDITY: " + currentWeatherData[4]);
                    
                    wind.setText("WIND SPEED: " + currentWeatherData[7]);
                    
                    // Change daily forecast
                    String[][] dailyForecast = displayHandler.getDailyForecastMetric(cityInfo);
                    date1.setText(dailyForecast[0][0]);
                    temp1.setText(dailyForecast[0][1]);
                    date2.setText(dailyForecast[1][0]);
                    temp2.setText(dailyForecast[1][1]);
                    date3.setText(dailyForecast[2][0]);
                    temp3.setText(dailyForecast[2][1]);
                    date4.setText(dailyForecast[3][0]);
                    temp4.setText(dailyForecast[3][1]);
                    
                    // Change hourly forecast
                    String[][] hourlyForecastData = displayHandler.getHourlyForecastMetric(cityInfo);
                    for (int hour = 0; hour < 24; hour++) {
                        textList[hour][0].setText(hourlyForecastData[hour][0].substring(11, 16));
                        textList[hour][1].setText(hourlyForecastData[hour][1]);
                        textList[hour][2].setText(hourlyForecastData[hour][2]);
                        textList[hour][3].setText(hourlyForecastData[hour][3]);
                        textList[hour][4].setText(hourlyForecastData[hour][4]);
                        textList[hour][5].setText(hourlyForecastData[hour][5]);
                    }
                } 
            } else {
                boolean inputIsInvalid = displayHandler.ifInputValid(searchBar);
                String input = searchBar.getText();
                if (input.split(", ", 3)[0].strip() == "") {
                    Alert a = new Alert(AlertType.WARNING); 
                    a.setContentText("Blank Input");
                    a.show();
                    searchBar.clear();
                } else if (inputIsInvalid) {
                    Alert a = new Alert(AlertType.ERROR);
                    a.setContentText("Location Not Found. Please provide a valid city name!");
                    a.show();
                    searchBar.clear();
                } else {
                    // Change the city name
                    cityName = searchBar.getText().split(",", 3)[0];
                    String[] cityInfo = displayHandler.getCityInformation(searchBar);
                    currentCityData = cityInfo;
                    if (cityInfo[2] == "") {
                        city.setText(cityName.toUpperCase() + ", " + cityInfo[3]);
                    } else {
                        city.setText(cityName.toUpperCase() + ", " + cityInfo[2] + ", " + cityInfo[3]);
                    }
                    
                    
                    // Change current weather section
                    String[] currentWeatherData = displayHandler.getCurrentWeatherDataImperial(cityInfo);
                    temp.setText(currentWeatherData[0]);
                    feelsLike.setText("FEELS LIKE: " + currentWeatherData[1]);
                    lowestTemp.setText("L: " + currentWeatherData[2]);
                    highestTemp.setText("H: " + currentWeatherData[3]);
                    humid.setText("HUMIDITY: " + currentWeatherData[4]);
                    
                    wind.setText("WIND SPEED: " + currentWeatherData[7]);
                    
                    // Change daily forecast
                    String[][] dailyForecast = displayHandler.getDailyForecastImperial(cityInfo);
                    date1.setText(dailyForecast[0][0]);
                    temp1.setText(dailyForecast[0][1]);
                    date2.setText(dailyForecast[1][0]);
                    temp2.setText(dailyForecast[1][1]);
                    date3.setText(dailyForecast[2][0]);
                    temp3.setText(dailyForecast[2][1]);
                    date4.setText(dailyForecast[3][0]);
                    temp4.setText(dailyForecast[3][1]);
                    
                    // Change hourly forecast
                    String[][] hourlyForecastData = displayHandler.getHourlyForecastImperial(cityInfo);
                    for (int hour = 0; hour < 24; hour++) {
                        textList[hour][0].setText(hourlyForecastData[hour][0].substring(11, 16));
                        textList[hour][1].setText(hourlyForecastData[hour][1]);
                        textList[hour][2].setText(hourlyForecastData[hour][2]);
                        textList[hour][3].setText(hourlyForecastData[hour][3]);
                        textList[hour][4].setText(hourlyForecastData[hour][4]);
                        textList[hour][5].setText(hourlyForecastData[hour][5]);
                    }
                } 

            }
        });
        
        var quitButton = getQuitButton();
        var favBox = favouritesDropBox();
     
        Button historyButton = new Button("History");
        searchBarSection.getChildren().addAll(searchBar, searchButton, historyButton, favBox, quitButton);



        // Adding Sections
        root.setTop(searchBarSection);
        root.setLeft(currentWeather);
        root.setRight(fewDaysForecast);
        root.setBottom(scrollPane);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("WeatherApp");
        stage.show(); 

    }
    
    public static void main(String[] args) {
        launch();
    }

    private Button getQuitButton() {
        //Creating a button.
        Button button = new Button("Quit");

        //Adding an event to the button to terminate the application.
        button.setOnAction((ActionEvent event) -> {
            writeToFile();
            Platform.exit();
        });

        return button;
    }
    
    // Save favourite locations
    
    private List<String> favourites = new ArrayList<String>();
    private ComboBox<String> favouritesBox = favouritesDropBox();
    
    private void writeToFile() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("favourites.txt"))) {
            for (String location : favourites) {
                writer.write(location);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        /* try (BufferedWriter writer = new BufferedWriter(new FileWriter("current_location.txt"))) {
            writer.write(city);
            writer.close();
        }

        catch (IOException e) {
            e.printStackTrace();
        }*/
    }
    
    private void readToFile() {

        try (BufferedReader reader = new BufferedReader(new FileReader("favourites.txt"))) {

            String line;

            while ((line = reader.readLine()) != null) {
                favourites.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        /*try (BufferedReader reader = new BufferedReader(new FileReader("last_location.txt"))) {
            city = reader.readLine();
            lookUpLocation(city, "", country);

        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
     
    private ComboBox<String> favouritesDropBox() {
        try {
            // Initialize favouritesBox only if it's not already initialized
            if (favouritesBox == null) {
                favouritesBox = new ComboBox<>();
            }
            if (favouritesBox.getItems().isEmpty() || favourites.isEmpty()) {
            favouritesBox.setPromptText("Favourites");
            }
            
            // Add selected favourite to search box
            favouritesBox.setOnAction(event -> {
                String selectedFavourite = favouritesBox.getValue();
                if (selectedFavourite != null) {
                    searchBar.setText(selectedFavourite);
                    searchButton.fire();
                }
            });
            // Check if favourites is null or empty before setting items
            if (favouritesBox.getItems().isEmpty() && favourites != null && !favourites.isEmpty()) {
                favouritesBox.getItems().setAll(favourites);
            }
    
            return favouritesBox;
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return favouritesBox;
    }
      
      // This method updates the items in the ComboBox
    private void updateFavBox() {
        favouritesDropBox().getItems().clear();
        favouritesDropBox().getItems().setAll(favourites);
    }
    private void updatesaveFavButtonState() {
        if (favourites.contains(cityName)) {
            saveFavButton.setSelected(true);
            saveFavButton.setText("Unsave");
        } else {
            saveFavButton.setSelected(false);
            saveFavButton.setText("Save as favourite");
        }
    }

}
    


/*
    // Empty favourites-button, add this next to the search bar
    Button clearFavButton = new Button("Clear favourites");

    clearFavs.setOnAction(event -> {
        favourites.clear();
        updateFavBox();
    });

}*/

