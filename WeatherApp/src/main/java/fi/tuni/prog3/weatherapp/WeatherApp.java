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
    private List<String> favourites = new ArrayList<String>();
    private ComboBox<String> favouritesBox = favouritesDropBox();
    private String selectedFavourite;

    private final ImageHandler imageHandler = new ImageHandler();
    private boolean isMetric = true;
    private String[] currentCityData = {};
    private String[] cityInfo = {};


    @Override
    public void start(Stage stage) {        
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));
        

        // Daily Forecast

        VBox fewDaysForecast = new VBox(25);
        fewDaysForecast.setPrefSize(290, 275);
        fewDaysForecast.setPadding(new Insets(10, 10 , 10, 10));
        fewDaysForecast.setStyle("-fx-background-color: #b8e2f2;");
        
        HBox day1 = new HBox(15);
        day1.setAlignment(Pos.CENTER);
        Text date1 = new Text("TODAY");
        date1.setStyle("-fx-font: 28 arial; -fx-font-weight: bold;");
        Text minTemp1 = new Text("0*C");
        minTemp1.setStyle("-fx-font: 28 arial;");
        Text maxTemp1 = new Text("0*C");
        maxTemp1.setStyle("-fx-font: 28 arial;");
        Image description1 = new Image(getClass().getResourceAsStream("/icons/day-clear.png"));
        ImageView descriptionView1 = new ImageView(description1);
        descriptionView1.setFitHeight(50);
        descriptionView1.setFitWidth(50);
        Region r = new Region();
        HBox.setHgrow(r, Priority.ALWAYS);
        day1.getChildren().addAll(date1, r, minTemp1, maxTemp1, descriptionView1);
        
        HBox day2 = new HBox(15);
        day2.setAlignment(Pos.CENTER);
        Text date2 = new Text("TODAY");
        date2.setStyle("-fx-font: 28 arial; -fx-font-weight: bold;");
        Text minTemp2 = new Text("0*C");
        minTemp2.setStyle("-fx-font: 28 arial;");
        Text maxTemp2 = new Text("0*C");
        maxTemp2.setStyle("-fx-font: 28 arial;");
        Image description2 = new Image(getClass().getResourceAsStream("/icons/day-clear.png"));
        ImageView descriptionView2 = new ImageView(description2);
        descriptionView2.setFitHeight(50);
        descriptionView2.setFitWidth(50);
        Region r2 = new Region();
        HBox.setHgrow(r2, Priority.ALWAYS);
        day2.getChildren().addAll(date2, r2, minTemp2, maxTemp2, descriptionView2);
        
        HBox day3 = new HBox(15);
        day3.setAlignment(Pos.CENTER);
        Text date3 = new Text("TODAY");
        date3.setStyle("-fx-font: 28 arial; -fx-font-weight: bold;");
        Text minTemp3 = new Text("0*C");
        minTemp3.setStyle("-fx-font: 28 arial;");
        Text maxTemp3 = new Text("0*C");
        maxTemp3.setStyle("-fx-font: 28 arial;");
        Image description3 = new Image(getClass().getResourceAsStream("/icons/day-clear.png"));
        ImageView descriptionView3 = new ImageView(description3);
        descriptionView3.setFitHeight(50);
        descriptionView3.setFitWidth(50);
        Region r3 = new Region();
        HBox.setHgrow(r3, Priority.ALWAYS);
        day3.getChildren().addAll(date3, r3, minTemp3, maxTemp3, descriptionView3);
        
        HBox day4 = new HBox(15);
        day4.setAlignment(Pos.CENTER);
        Text date4 = new Text("TODAY");
        date4.setStyle("-fx-font: 28 arial; -fx-font-weight: bold;");
        Text minTemp4 = new Text("0*C");
        minTemp4.setStyle("-fx-font: 28 arial;");
        Text maxTemp4 = new Text("0*C");
        maxTemp4.setStyle("-fx-font: 28 arial;");
        Image description4 = new Image(getClass().getResourceAsStream("/icons/day-clear.png"));
        ImageView descriptionView4 = new ImageView(description4);
        descriptionView4.setFitHeight(50);
        descriptionView4.setFitWidth(50);
        Region r4 = new Region();
        HBox.setHgrow(r4, Priority.ALWAYS);
        day4.getChildren().addAll(date4, r4, minTemp4, maxTemp4, descriptionView4);
        
        fewDaysForecast.getChildren().addAll(day1, day2, day3, day4);
         
        // Hourly Forecast
        GridPane hourlyForecast = new GridPane();
        hourlyForecast.setHgap(10);
        hourlyForecast.setVgap(10);
        Text[][] textList = new Text[24][5];
        ImageView[] imageViews = new ImageView[24];
        for (int hour = 0; hour < 24; hour++) {  
            Text hourText = new Text();
            hourText.setStyle("-fx-font: 20 arial; -fx-font-weight: bold;");
            hourlyForecast.add(hourText, hour, 0);
            Text degree = new Text();
            degree.setStyle("-fx-font: 20 arial;");
            hourlyForecast.add(degree, hour, 1);
            Text lowestTempHourly = new Text();
            lowestTempHourly.setStyle("-fx-font: 20 arial;");
            hourlyForecast.add(lowestTempHourly, hour, 2);
            Text highestTempHourly = new Text();
            highestTempHourly.setStyle("-fx-font: 20 arial;");
            hourlyForecast.add(highestTempHourly, hour, 3);
            Image descriptionHourly = new Image(getClass().getResourceAsStream("/icons/day-clear.png"));
            ImageView descriptionHourView = new ImageView(descriptionHourly);
            descriptionHourView.setFitHeight(30);
            descriptionHourView.setFitWidth(30);
            hourlyForecast.add(descriptionHourView, hour, 4);
            Text humidity = new Text();
            humidity.setStyle("-fx-font: 20 arial;");
            hourlyForecast.add(humidity, hour, 5);
            textList[hour][0] = hourText;
            textList[hour][1] = degree;
            textList[hour][2] = lowestTempHourly;
            textList[hour][3] = highestTempHourly;
            imageViews[hour] = descriptionHourView;
            textList[hour][4] = humidity;
        }
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(650, 220);
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
                favourites.add(capitalizedPhrase(cityName));
            } else {
                // If the button is not selected, remove the city from favorites
                favourites.remove(capitalizedPhrase(cityName));      
            } 
            updateFavBox();
            favouritesBox.setValue(null);
            updatesaveFavButtonState(); // Update the button state after toggling favorite status
        });
 
        
       
        saveFavButton.setPrefWidth(100);
        Button changeUnit = new Button("Change Unit");
        changeUnit.setPrefWidth(100);
        changeUnit.setOnAction((ActionEvent event) -> {

            if (!isMetric) {

                // Change daily forecast
                String[][] dailyForecast = displayHandler.getDailyForecastMetric(currentCityData);
                date1.setText(dailyForecast[0][0]);
                minTemp1.setText(dailyForecast[0][2]);
                maxTemp1.setText(dailyForecast[0][3]);
                date2.setText(dailyForecast[1][0]);
                minTemp2.setText(dailyForecast[1][2]);
                maxTemp2.setText(dailyForecast[1][3]);
                date3.setText(dailyForecast[2][0]);
                minTemp3.setText(dailyForecast[2][2]);
                maxTemp3.setText(dailyForecast[2][3]);
                date4.setText(dailyForecast[3][0]);
                minTemp4.setText(dailyForecast[3][2]);
                maxTemp4.setText(dailyForecast[3][3]);


                // Change current weather section
                String[] currentWeatherData = displayHandler.getCurrentWeatherDataMetric(currentCityData);
                temp.setText(currentWeatherData[0]);
                feelsLike.setText("FEELS LIKE: " + currentWeatherData[1]);
                lowestTemp.setText("L: " + dailyForecast[0][2]);
                highestTemp.setText("H: " + dailyForecast[0][3]);
                humid.setText("HUMIDITY: " + currentWeatherData[4]);
                wind.setText("WIND SPEED: " + currentWeatherData[7]);
                
                // Change hourly forecast
                String[][] hourlyForecastData = displayHandler.getHourlyForecastMetric(currentCityData);
                for (int hour = 0; hour < 24; hour++) {
                    textList[hour][0].setText(hourlyForecastData[hour][0].substring(11, 16));
                    textList[hour][1].setText(hourlyForecastData[hour][1]);
                    textList[hour][2].setText(hourlyForecastData[hour][2]);
                    textList[hour][3].setText(hourlyForecastData[hour][3]);
                    textList[hour][4].setText(hourlyForecastData[hour][5]);
                }

                isMetric = true;
            } else {
                // Change daily forecast
                String[][] dailyForecast = displayHandler.getDailyForecastImperial(currentCityData);
                date1.setText(dailyForecast[0][0]);
                minTemp1.setText(dailyForecast[0][2]);
                maxTemp1.setText(dailyForecast[0][3]);
                date2.setText(dailyForecast[1][0]);
                minTemp2.setText(dailyForecast[1][2]);
                maxTemp2.setText(dailyForecast[1][3]);
                date3.setText(dailyForecast[2][0]);
                minTemp3.setText(dailyForecast[2][2]);
                maxTemp3.setText(dailyForecast[2][3]);
                date4.setText(dailyForecast[3][0]);
                minTemp4.setText(dailyForecast[3][2]);
                maxTemp4.setText(dailyForecast[3][3]);

                // Change current weather section
                String[] currentWeatherData = displayHandler.getCurrentWeatherDataImperial(currentCityData);
                temp.setText(currentWeatherData[0]);
                feelsLike.setText("FEELS LIKE: " + currentWeatherData[1]);
                lowestTemp.setText("L: " + dailyForecast[0][2]);
                highestTemp.setText("H: " + dailyForecast[0][3]);
                humid.setText("HUMIDITY: " + currentWeatherData[4]);
                wind.setText("WIND SPEED: " + currentWeatherData[7]);
                
                
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
        HBox searchBarSection = new HBox(15);
        searchBarSection.setAlignment(Pos.CENTER);
        
        TextField searchBar = new TextField();
        searchBar.setPrefWidth(330);

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
                    updateFavBox();
                    updatesaveFavButtonState();
                    
                    
                    cityInfo = displayHandler.getCityInformation(searchBar);
                    currentCityData = cityInfo;
                    
                    if (cityInfo[2] == "") {
                        city.setText(cityName.toUpperCase() + ", " + cityInfo[3]);
                    } else {
                        city.setText(cityName.toUpperCase() + ", " + cityInfo[2] + ", " + cityInfo[3]);
                    }
                    
                    // Change daily forecast
                    String[][] dailyForecast = displayHandler.getDailyForecastMetric(cityInfo);
                    date1.setText(dailyForecast[0][0]);
                    minTemp1.setText(dailyForecast[0][2]);
                    maxTemp1.setText(dailyForecast[0][3]);
                    descriptionView1.setImage(new Image(getClass().getResourceAsStream(imageHandler.imageHandler(dailyForecast[0][4]))));
                    date2.setText(dailyForecast[1][0]);
                    minTemp2.setText(dailyForecast[1][2]);
                    maxTemp2.setText(dailyForecast[1][3]);
                    descriptionView2.setImage(new Image(getClass().getResourceAsStream(imageHandler.imageHandler(dailyForecast[1][4]))));
                    date3.setText(dailyForecast[2][0]);
                    minTemp3.setText(dailyForecast[2][2]);
                    maxTemp3.setText(dailyForecast[2][3]);
                    descriptionView3.setImage(new Image(getClass().getResourceAsStream(imageHandler.imageHandler(dailyForecast[2][4]))));
                    date4.setText(dailyForecast[3][0]);
                    minTemp4.setText(dailyForecast[3][2]);
                    maxTemp4.setText(dailyForecast[3][3]);
                    descriptionView4.setImage(new Image(getClass().getResourceAsStream(imageHandler.imageHandler(dailyForecast[3][4]))));

                    // Change current weather section
                    String[] currentWeatherData = displayHandler.getCurrentWeatherDataMetric(cityInfo);
                    temp.setText(currentWeatherData[0]);
                    feelsLike.setText("FEELS LIKE: " + currentWeatherData[1]);
                    lowestTemp.setText("L: " + dailyForecast[0][2]);
                    highestTemp.setText("H: " + dailyForecast[0][3]);
                    humid.setText("HUMIDITY: " + currentWeatherData[4]);
                    descriptionView.setImage(new Image(getClass().getResourceAsStream(imageHandler.imageHandler(currentWeatherData[6]))));
                    wind.setText("WIND SPEED: " + currentWeatherData[7]);
                    
                    // Change hourly forecast
                    String[][] hourlyForecastData = displayHandler.getHourlyForecastMetric(cityInfo);
                    for (int hour = 0; hour < 24; hour++) {
                        textList[hour][0].setText(hourlyForecastData[hour][0].substring(11, 16));
                        textList[hour][1].setText(hourlyForecastData[hour][1]);
                        textList[hour][2].setText(hourlyForecastData[hour][2]);
                        textList[hour][3].setText(hourlyForecastData[hour][3]);
                        imageViews[hour].setImage(new Image(getClass().getResourceAsStream(imageHandler.imageHandler(hourlyForecastData[hour][4]))));
                        textList[hour][4].setText(hourlyForecastData[hour][5]);
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
                    
                    // Change daily forecast
                    String[][] dailyForecast = displayHandler.getDailyForecastImperial(cityInfo);
                    date1.setText(dailyForecast[0][0]);
                    minTemp1.setText(dailyForecast[0][2]);
                    maxTemp1.setText(dailyForecast[0][3]);
                    descriptionView1.setImage(new Image(getClass().getResourceAsStream(imageHandler.imageHandler(dailyForecast[0][4]))));
                    date2.setText(dailyForecast[1][0]);
                    minTemp2.setText(dailyForecast[1][2]);
                    maxTemp2.setText(dailyForecast[1][3]);
                    descriptionView2.setImage(new Image(getClass().getResourceAsStream(imageHandler.imageHandler(dailyForecast[1][4]))));
                    date3.setText(dailyForecast[2][0]);
                    minTemp3.setText(dailyForecast[2][2]);
                    maxTemp3.setText(dailyForecast[2][3]);
                    descriptionView3.setImage(new Image(getClass().getResourceAsStream(imageHandler.imageHandler(dailyForecast[2][4]))));
                    date4.setText(dailyForecast[3][0]);
                    minTemp4.setText(dailyForecast[3][2]);
                    maxTemp4.setText(dailyForecast[3][3]);
                    descriptionView4.setImage(new Image(getClass().getResourceAsStream(imageHandler.imageHandler(dailyForecast[3][4]))));

                    // Change current weather section
                    String[] currentWeatherData = displayHandler.getCurrentWeatherDataImperial(cityInfo);
                    temp.setText(currentWeatherData[0]);
                    feelsLike.setText("FEELS LIKE: " + currentWeatherData[1]);
                    lowestTemp.setText("L: " + dailyForecast[0][2]);
                    highestTemp.setText("H: " + dailyForecast[0][3]);
                    humid.setText("HUMIDITY: " + currentWeatherData[4]);
                    descriptionView.setImage(new Image(getClass().getResourceAsStream(imageHandler.imageHandler(currentWeatherData[6]))));
                    wind.setText("WIND SPEED: " + currentWeatherData[7]);
                    
                    // Change hourly forecast
                    String[][] hourlyForecastData = displayHandler.getHourlyForecastImperial(cityInfo);
                    for (int hour = 0; hour < 24; hour++) {
                        textList[hour][0].setText(hourlyForecastData[hour][0].substring(11, 16));
                        textList[hour][1].setText(hourlyForecastData[hour][1]);
                        textList[hour][2].setText(hourlyForecastData[hour][2]);
                        textList[hour][3].setText(hourlyForecastData[hour][3]);
                        imageViews[hour].setImage(new Image(getClass().getResourceAsStream(imageHandler.imageHandler(hourlyForecastData[hour][4]))));
                        textList[hour][4].setText(hourlyForecastData[hour][5]);
                    }
                } 

            }
        });

        var quitButton = getQuitButton();
        var favBox = favouritesDropBox();
     
        Button historyButton = new Button("History");


        Label favLabel = new Label("Favorite:");
        Label historyLabel = new Label("History:");

        searchBarSection.getChildren().addAll(searchBar, searchButton, historyLabel, historyButton, favLabel, favBox, quitButton);



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
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("current_location.txt"))) {
            writer.write(cityName);
            writer.close();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
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
        
        try (BufferedReader reader = new BufferedReader(new FileReader("current_location.txt"))) {
            cityName = reader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
    private ComboBox<String> favouritesDropBox() {
        try {
            // Initialize favouritesBox only if it's not already initialized
            if (favouritesBox == null) {
                favouritesBox = new ComboBox<>();
            }
            favouritesBox.setPromptText("Favourites");
            
            // Add selected favourite to search box
            favouritesBox.setOnAction(event -> {
                selectedFavourite = favouritesBox.getValue();
                if (selectedFavourite != null) {
                    searchBar.setText(selectedFavourite);
                    searchButton.fire();
                } if (selectedFavourite == null) {
                    favouritesBox.setPromptText("Favourites");
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
        if (favourites.contains(capitalizedPhrase(cityName))) {
            saveFavButton.setSelected(true);
            saveFavButton.setText("Unsave");
        } else {
            saveFavButton.setSelected(false);
            saveFavButton.setText("Save as favourite");
        }
    }
  
    private String capitalizedPhrase(String phrase) {
        // Check if the phrase is null or empty
        if (phrase == null || phrase.isEmpty()) {
            return "";
        }

        // Check if the phrase contains only one word
        if (!phrase.contains(" ")) {
            // If the phrase contains only one word, capitalize its first letter and return
            return phrase.substring(0, 1).toUpperCase() + phrase.substring(1);
        }

        // Splitting up words using split function
        String[] words = phrase.split(" ");

        for (int i = 0; i < words.length; i++) {
            // Taking letter individually from each word
            String firstLetter = words[i].substring(0, 1);
            String restOfWord = words[i].substring(1);

            // Making first letter uppercase using toUpperCase function
            firstLetter = firstLetter.toUpperCase();
            words[i] = firstLetter + restOfWord;
        }

        // Joining the words together to make a sentence
        String capitalizedPhrase = String.join(" ", words);
        return capitalizedPhrase;
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

