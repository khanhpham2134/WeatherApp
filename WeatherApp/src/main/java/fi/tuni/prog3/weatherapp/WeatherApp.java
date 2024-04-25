package fi.tuni.prog3.weatherapp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;



/**
 * JavaFX Weather Application.
 */
public class WeatherApp extends Application { 
    // By default the unit of the program is metric
    private String cityName;
    private TextField searchBar;
    private Button searchButton;
    private ToggleButton saveFavButton;
    private List<String> favourites = new ArrayList<String>();
    private ComboBox<String> favouritesBox = favouritesDropBox();
    private List<String> history = new ArrayList<String>();
    private ComboBox<String> historyBox = historyDropBox();
    private String selectedFavourite;

    private boolean isMetric = true;
    private String[] currentCityData = {};
    private String[] cityInfo = {};
    private String[] cityData = {};
    private String cityLoc;
    private String[] inputParams = {};

    private final DisplayHandler displayHandler = new DisplayHandler();
    private final ImageHandler imageHandler = new ImageHandler();

    // Daily forecast Nodes
    private Text[][] dailyForecastTexts = new Text[4][3];
    private ImageView[] dailyForecastImages = new ImageView[4];
    // Hourly forecast Nodes
    private Text[][] hourlyForecastTexts = new Text[24][4];
    private ImageView[] hourlyForecastImages = new ImageView[24];
    // Current forecast Nodes
    private Text[] currentWeatherTexts = new Text[7];
    private ImageView currentWeatherView; 
    
    private String[][] dailyForecast;
    private String[] currentWeatherData;
    private String[][] hourlyForecastData;
    @Override
    public void start(Stage stage) {        
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));
        
        // Few Days Forecast     
        VBox fewDaysForecast = new VBox(25);
        fewDaysForecast.setPadding(new Insets(10, 10 , 10, 10));
        fewDaysForecast.setStyle("-fx-background-color: #b8e2f2;");
        fewDaysForecast.setPrefWidth(350);
        
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
        dailyForecastTexts[0][0] = date1;
        dailyForecastTexts[0][1] = minTemp1;
        dailyForecastTexts[0][2] = maxTemp1;
        dailyForecastImages[0] = descriptionView1;
        
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
        dailyForecastTexts[1][0] = date2;
        dailyForecastTexts[1][1] = minTemp2;
        dailyForecastTexts[1][2] = maxTemp2;
        dailyForecastImages[1] = descriptionView2;
        
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
        dailyForecastTexts[2][0] = date3;
        dailyForecastTexts[2][1] = minTemp3;
        dailyForecastTexts[2][2] = maxTemp3;
        dailyForecastImages[2] = descriptionView3;
        
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
        dailyForecastTexts[3][0] = date4;
        dailyForecastTexts[3][1] = minTemp4;
        dailyForecastTexts[3][2] = maxTemp4;
        dailyForecastImages[3] = descriptionView4;
        
        fewDaysForecast.getChildren().addAll(day1, day2, day3, day4);
        
        // Hourly Forecast
        GridPane hourlyForecast = new GridPane();
        hourlyForecast.setHgap(10);
        hourlyForecast.setVgap(10);
        for (int hour = 0; hour < 24; hour++) {  
            Text hourText = new Text();
            hourText.setStyle("-fx-font: 20 arial; -fx-font-weight: bold;");
            hourlyForecast.add(hourText, hour, 0);
            Text degree = new Text();
            degree.setStyle("-fx-font: 20 arial;");
            hourlyForecast.add(degree, hour, 1);
            Text windSpeed = new Text();
            windSpeed.setStyle("-fx-font: 20 arial;");
            hourlyForecast.add(windSpeed, hour, 2);
            Image descriptionHourly = new Image(getClass().getResourceAsStream("/icons/day-clear.png"));
            ImageView descriptionHourView = new ImageView(descriptionHourly);
            descriptionHourView.setFitHeight(30);
            descriptionHourView.setFitWidth(30);
            hourlyForecast.add(descriptionHourView, hour, 3);
            Text humidity = new Text();
            humidity.setStyle("-fx-font: 20 arial;");
            hourlyForecast.add(humidity, hour, 4);
            hourlyForecastTexts[hour][0] = hourText;
            hourlyForecastTexts[hour][1] = degree;
            hourlyForecastTexts[hour][2] = windSpeed;
            hourlyForecastImages[hour] = descriptionHourView;
            hourlyForecastTexts[hour][3] = humidity;
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
        currentWeatherTexts[0] = lowestTemp;
    
        VBox highestTempBox = new VBox();
        highestTempBox.setAlignment(Pos.CENTER);
        Text highestTemp = new Text("H: ");
        highestTemp.setStyle("-fx-font: 20 arial;");
        highestTempBox.getChildren().add(highestTemp);
        currentWeather.setRight(highestTempBox);
        currentWeatherTexts[1] = highestTemp;
    
        VBox currentWeatherTextBox = new VBox(10);
        currentWeatherTextBox.setAlignment(Pos.CENTER);
        Label currentWeatherLabel = new Label("CURRENT WEATHER");
        currentWeatherLabel.setStyle("-fx-font: 24 arial; -fx-font-weight: bold;");
        Text city = new Text();
        city.setStyle("-fx-font: 20 arial;");
        currentWeatherTextBox.getChildren().addAll(currentWeatherLabel, city);
        currentWeather.setTop(currentWeatherTextBox);
        currentWeatherTexts[2] = city;
    
        VBox currentWeatherDataBox = new VBox();
        currentWeatherDataBox.setAlignment(Pos.CENTER);

        Text temp = new Text("12*c");
        temp.setStyle("-fx-font: 45 arial;");
        currentWeatherTexts[3] = temp;       
        Image description = new Image(getClass().getResourceAsStream("/icons/day-clear.png"));
        ImageView descriptionView = new ImageView(description);
        descriptionView.setFitHeight(90);
        descriptionView.setFitWidth(90);
        currentWeatherView = descriptionView;
        currentWeatherDataBox.getChildren().addAll(temp, descriptionView);
        currentWeather.setCenter(currentWeatherDataBox);
        BorderPane additionalDataBox = new BorderPane();
        VBox data = new VBox();
        Text feelsLike = new Text("FEELS LIKE: ");
        feelsLike.setStyle("-fx-font: 23 arial;");
        currentWeatherTexts[4] = feelsLike;
        Text humid = new Text("HUMIDITY: "); 
        humid.setStyle("-fx-font: 23 arial;");
        currentWeatherTexts[5] = humid;
        Text wind = new Text("WIND SPEED: ");
        wind.setStyle("-fx-font: 23 arial;");
        data.getChildren().addAll(feelsLike, humid, wind);
        currentWeatherTexts[6] = wind;
        additionalDataBox.setLeft(data);
        VBox buttons = new VBox(15);

        // Load previously saved favourites
        readToFile();
        updateFavBox();
        updateHisBox();
        //Button setFav = new Button("Favorite");
        saveFavButton = new ToggleButton("Save as favourite");
        // Update the button state based on the favorite status
        updatesaveFavButtonState();

        // Add event handler to toggle the favorite status of the city when the button is clicked
        saveFavButton.setOnAction(event -> {
            if (saveFavButton.isSelected()) {
                // If the button is selected, add the city to favorites
                favourites.add(cityLoc);
            } else {
                // If the button is not selected, remove the city from favorites
                favourites.remove(cityLoc);      
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
                    hourlyForecastTexts[hour][0].setText(hourlyForecastData[hour][0] + ":00");
                    hourlyForecastTexts[hour][1].setText(hourlyForecastData[hour][1]);
                    hourlyForecastTexts[hour][2].setText(hourlyForecastData[hour][2]);
                    hourlyForecastTexts[hour][3].setText(hourlyForecastData[hour][4]);
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
                    hourlyForecastTexts[hour][0].setText(hourlyForecastData[hour][0] + ":00");
                    hourlyForecastTexts[hour][1].setText(hourlyForecastData[hour][1]);
                    hourlyForecastTexts[hour][2].setText(hourlyForecastData[hour][2]);
                    hourlyForecastTexts[hour][3].setText(hourlyForecastData[hour][4]);
                }

                isMetric = false;
            }
        });
        buttons.getChildren().addAll(saveFavButton, changeUnit);
        additionalDataBox.setRight(buttons);
        currentWeather.setBottom(additionalDataBox);

        // Search Bar
        HBox searchBarSection = new HBox(10);
        searchBarSection.setAlignment(Pos.CENTER);
        
        searchBar = new TextField();
        searchBar.setPrefWidth(160);
        searchBar.setPromptText("City, (State,) Country Code");
        
        searchButton = new Button("Search");
        searchButton.setOnAction((ActionEvent event) -> {
            if (isMetric) {
                boolean inputIsInvalid = displayHandler.ifInputValid(searchBar);
                String input = searchBar.getText();
                String[] inputParams = input.split(",", 10);
                if (inputParams[0].strip() == "") {
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
                    cityName = inputParams[0];

                    updatesaveFavButtonState();
                    updateFavBox();
                    updateHisBox();

                    String[] cityInfo = displayHandler.getCityInformation(inputParams);
                    currentCityData = cityInfo;
                    if (cityInfo[2] == "") {
                        cityLoc = cityName.toUpperCase() + ", " + cityInfo[3];
                        city.setText(cityLoc);
                    } else {
                        cityLoc = cityName.toUpperCase() + ", " + cityInfo[2] + ", " + cityInfo[3];
                        city.setText(cityLoc);
                    }
                    if (!history.contains(cityLoc)) {
                        history.add(cityLoc);}
                    
                    // Change daily forecast
                    String[][] dailyForecast = displayHandler.getDailyForecastMetric(cityInfo);
                    date1.setText(dailyForecast[0][0]);
                    minTemp1.setText(dailyForecast[0][2]);
                    maxTemp1.setText(dailyForecast[0][3]);
                    descriptionView1.setImage(new Image(getClass().getResourceAsStream(imageHandler.forecastImageHandler(dailyForecast[0][4]))));
                    date2.setText(dailyForecast[1][0]);
                    minTemp2.setText(dailyForecast[1][2]);
                    maxTemp2.setText(dailyForecast[1][3]);
                    descriptionView2.setImage(new Image(getClass().getResourceAsStream(imageHandler.forecastImageHandler(dailyForecast[1][4]))));
                    date3.setText(dailyForecast[2][0]);
                    minTemp3.setText(dailyForecast[2][2]);
                    maxTemp3.setText(dailyForecast[2][3]);
                    descriptionView3.setImage(new Image(getClass().getResourceAsStream(imageHandler.forecastImageHandler(dailyForecast[2][4]))));
                    date4.setText(dailyForecast[3][0]);
                    minTemp4.setText(dailyForecast[3][2]);
                    maxTemp4.setText(dailyForecast[3][3]);
                    descriptionView4.setImage(new Image(getClass().getResourceAsStream(imageHandler.forecastImageHandler(dailyForecast[3][4]))));

                    // Change current weather section
                    String[] currentWeatherData = displayHandler.getCurrentWeatherDataMetric(cityInfo);
                    String daySunrise = currentWeatherData[9];
                    String daySunset = currentWeatherData[10];
                    System.out.println(daySunrise);
                    System.out.println(daySunset);
                    System.out.println(currentWeatherData[8]);
                    temp.setText(currentWeatherData[0]);
                    feelsLike.setText("FEELS LIKE: " + currentWeatherData[1]);
                    lowestTemp.setText("L: " + dailyForecast[0][2]);
                    highestTemp.setText("H: " + dailyForecast[0][3]);
                    humid.setText("HUMIDITY: " + currentWeatherData[4]);
                    descriptionView.setImage(new Image(getClass().getResourceAsStream(imageHandler.currentImageHandler(currentWeatherData))));
                    wind.setText("WIND SPEED: " + currentWeatherData[7]);
                    
                    // Change hourly forecast
                    String[][] hourlyForecastData = displayHandler.getHourlyForecastMetric(cityInfo);
                    for (int hour = 0; hour < 24; hour++) {
                        hourlyForecastTexts[hour][0].setText(hourlyForecastData[hour][0] + ":00");
                        hourlyForecastTexts[hour][1].setText(hourlyForecastData[hour][1]);
                        hourlyForecastTexts[hour][2].setText(hourlyForecastData[hour][2]);
                        hourlyForecastImages[hour].setImage(new Image(getClass().getResourceAsStream(imageHandler.hourlyImageHandler(hourlyForecastData[hour], daySunrise, daySunset))));
                        hourlyForecastTexts[hour][3].setText(hourlyForecastData[hour][4]);
                    }
                } 
            } else {
                boolean inputIsInvalid = displayHandler.ifInputValid(searchBar);
                String input = searchBar.getText();
                String[] inputParams = input.split(",", 10);
                if (inputParams[0].strip() == "") {
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
                    String cityName = inputParams[0];
                    String[] cityInfo = displayHandler.getCityInformation(inputParams);
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
                    descriptionView1.setImage(new Image(getClass().getResourceAsStream(imageHandler.forecastImageHandler(dailyForecast[0][4]))));
                    date2.setText(dailyForecast[1][0]);
                    minTemp2.setText(dailyForecast[1][2]);
                    maxTemp2.setText(dailyForecast[1][3]);
                    descriptionView2.setImage(new Image(getClass().getResourceAsStream(imageHandler.forecastImageHandler(dailyForecast[1][4]))));
                    date3.setText(dailyForecast[2][0]);
                    minTemp3.setText(dailyForecast[2][2]);
                    maxTemp3.setText(dailyForecast[2][3]);
                    descriptionView3.setImage(new Image(getClass().getResourceAsStream(imageHandler.forecastImageHandler(dailyForecast[2][4]))));
                    date4.setText(dailyForecast[3][0]);
                    minTemp4.setText(dailyForecast[3][2]);
                    maxTemp4.setText(dailyForecast[3][3]);
                    descriptionView4.setImage(new Image(getClass().getResourceAsStream(imageHandler.forecastImageHandler(dailyForecast[3][4]))));

                    // Change current weather section
                    String[] currentWeatherData = displayHandler.getCurrentWeatherDataImperial(cityInfo);
                    String daySunrise = currentWeatherData[9];
                    String daySunset = currentWeatherData[10];
                    temp.setText(currentWeatherData[0]);
                    feelsLike.setText("FEELS LIKE: " + currentWeatherData[1]);
                    lowestTemp.setText("L: " + dailyForecast[0][2]);
                    highestTemp.setText("H: " + dailyForecast[0][3]);
                    humid.setText("HUMIDITY: " + currentWeatherData[4]);
                    descriptionView.setImage(new Image(getClass().getResourceAsStream(imageHandler.currentImageHandler(currentWeatherData))));
                    wind.setText("WIND SPEED: " + currentWeatherData[7]);
                    
                    // Change hourly forecast
                    String[][] hourlyForecastData = displayHandler.getHourlyForecastImperial(cityInfo);
                    for (int hour = 0; hour < 24; hour++) {
                        hourlyForecastTexts[hour][0].setText(hourlyForecastData[hour][0] + ":00");
                        hourlyForecastTexts[hour][1].setText(hourlyForecastData[hour][1]);
                        hourlyForecastTexts[hour][2].setText(hourlyForecastData[hour][2]);
                        hourlyForecastImages[hour].setImage(new Image(getClass().getResourceAsStream(imageHandler.hourlyImageHandler(hourlyForecastData[hour], daySunrise, daySunset))));
                        hourlyForecastTexts[hour][3].setText(hourlyForecastData[hour][4]);
                    }
                } 

            }
        });
        Label favLabel = new Label("Favorite:");
        Label historyLabel = new Label("History:");
        var quitButton = getQuitButton();
        var favBox = favouritesDropBox();
        var hisBox = historyDropBox();
;
        Button clearHistoryButton = new Button("Clear History");
        clearHistoryButton.setOnAction(event -> {
            history.clear();
            updateHisBox();
        });
        searchBarSection.getChildren().addAll(searchBar, searchButton, historyLabel, hisBox, clearHistoryButton, favLabel, favBox, quitButton);


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
            writer.write(cityLoc);
            writer.newLine(); // Move to the next line
            writer.write(String.valueOf(isMetric));
            writer.close();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("history.txt"))) {
            for (String location : history) {
                writer.write(location);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
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
            cityLoc = reader.readLine();
            String secondLine = reader.readLine(); // Read the second line
            if (secondLine == null) {
                isMetric = true;
            }
            else { // Check if the second line exists
                isMetric = Boolean.parseBoolean(secondLine);}
            Text city = currentWeatherTexts[2];
            inputParams = cityLoc.split(",", 10);
            String[] cityInfo = displayHandler.getCityInformation(inputParams);
            currentCityData = cityInfo;
            if (cityInfo[2] == "") {
                city.setText(cityLoc);
            } else {
                city.setText(cityLoc);
            }
            if (!isMetric) {
                dailyForecast = displayHandler.getDailyForecastImperial(cityInfo);
                currentWeatherData = displayHandler.getCurrentWeatherDataImperial(cityInfo);
                hourlyForecastData = displayHandler.getHourlyForecastImperial(cityInfo);}
            else {
                dailyForecast = displayHandler.getDailyForecastMetric(cityInfo);
                currentWeatherData = displayHandler.getCurrentWeatherDataMetric(cityInfo);
                hourlyForecastData = displayHandler.getHourlyForecastMetric(cityInfo);
            }
            String daySunrise = currentWeatherData[9];
            String daySunset = currentWeatherData[10];

            Text date1 = dailyForecastTexts[0][0];
            Text minTemp1 = dailyForecastTexts[0][1];
            Text maxTemp1 = dailyForecastTexts[0][2];
            ImageView descriptionView1 = dailyForecastImages[0];

            Text date2 = dailyForecastTexts[1][0];
            Text minTemp2 = dailyForecastTexts[1][1];
            Text maxTemp2 = dailyForecastTexts[1][2];
            ImageView descriptionView2 = dailyForecastImages[1];

            Text date3 = dailyForecastTexts[2][0];
            Text minTemp3 = dailyForecastTexts[2][1];
            Text maxTemp3 = dailyForecastTexts[2][2];
            ImageView descriptionView3 = dailyForecastImages[2];

            Text date4 = dailyForecastTexts[3][0];
            Text minTemp4 = dailyForecastTexts[3][1];
            Text maxTemp4 = dailyForecastTexts[3][2];
            ImageView descriptionView4 = dailyForecastImages[3];

            // Change daily forecast
            date1.setText(dailyForecast[0][0]);
            minTemp1.setText(dailyForecast[0][2]);
            maxTemp1.setText(dailyForecast[0][3]);
            descriptionView1.setImage(new Image(getClass().getResourceAsStream(imageHandler.forecastImageHandler(dailyForecast[0][4]))));
            date2.setText(dailyForecast[1][0]);
            minTemp2.setText(dailyForecast[1][2]);
            maxTemp2.setText(dailyForecast[1][3]);
            descriptionView2.setImage(new Image(getClass().getResourceAsStream(imageHandler.forecastImageHandler(dailyForecast[1][4]))));
            date3.setText(dailyForecast[2][0]);
            minTemp3.setText(dailyForecast[2][2]);
            maxTemp3.setText(dailyForecast[2][3]);
            descriptionView3.setImage(new Image(getClass().getResourceAsStream(imageHandler.forecastImageHandler(dailyForecast[2][4]))));
            date4.setText(dailyForecast[3][0]);
            minTemp4.setText(dailyForecast[3][2]);
            maxTemp4.setText(dailyForecast[3][3]);
            descriptionView4.setImage(new Image(getClass().getResourceAsStream(imageHandler.forecastImageHandler(dailyForecast[3][4]))));

            // Change current weather section
            Text lowestTemp = currentWeatherTexts[0];
            Text highestTemp = currentWeatherTexts[1];
            Text temp = currentWeatherTexts[3];
            Text feelsLike = currentWeatherTexts[4];
            Text humid = currentWeatherTexts[5];
            Text wind = currentWeatherTexts[6];
            ImageView descriptionView = currentWeatherView;

            
            temp.setText(currentWeatherData[0]);
            feelsLike.setText("FEELS LIKE: " + currentWeatherData[1]);
            lowestTemp.setText("L: " + dailyForecast[0][2]);
            highestTemp.setText("H: " + dailyForecast[0][3]);
            humid.setText("HUMIDITY: " + currentWeatherData[4]);
            descriptionView.setImage(new Image(getClass().getResourceAsStream(imageHandler.currentImageHandler(currentWeatherData))));
            wind.setText("WIND SPEED: " + currentWeatherData[7]);
            
            // Change hourly forecast
            
            for (int hour = 0; hour < 24; hour++) {
                Text hourText = hourlyForecastTexts[hour][0];
                Text degree = hourlyForecastTexts[hour][1];
                Text windSpeed = hourlyForecastTexts[hour][2];
                ImageView descriptionHourView = hourlyForecastImages[hour];
                Text humidity = hourlyForecastTexts[hour][3];

                hourlyForecastTexts[hour][0].setText(hourlyForecastData[hour][0] + ":00");
                hourlyForecastTexts[hour][1].setText(hourlyForecastData[hour][1]);
                hourlyForecastTexts[hour][2].setText(hourlyForecastData[hour][2]);
                hourlyForecastImages[hour].setImage(new Image(getClass().getResourceAsStream(imageHandler.hourlyImageHandler(hourlyForecastData[hour], daySunrise, daySunset))));
                hourlyForecastTexts[hour][3].setText(hourlyForecastData[hour][4]);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader("history.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                history.add(line);
            }
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
    
    private ComboBox<String> historyDropBox() {
        try {
            // Initialize favouritesBox only if it's not already initialized
            if (historyBox == null) {
                historyBox = new ComboBox<>();
                historyBox.setPrefWidth(130);
            }
            historyBox.setPromptText("Choose History");
            
            // Add selected favourite to search box
            historyBox.setOnAction(event -> {
                String selected = historyBox.getValue();
                if (selected != null) {
                    searchBar.setText(selected);
                    searchButton.fire();
                }
            });
            // Check if favourites is null or empty before setting items
            if (historyBox.getItems().isEmpty() && history != null && !history.isEmpty()) {
                historyBox.getItems().setAll(history);
            }
    
            return historyBox;
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return historyBox;
    }
      
      // This method updates the items in the ComboBox
    private void updateFavBox() {
        favouritesDropBox().getItems().clear();
        favouritesDropBox().getItems().setAll(favourites);
    }
    
    private void updateHisBox() {
        historyDropBox().getItems().clear();
        historyDropBox().getItems().setAll(history);
    }
    
    private void updatesaveFavButtonState() {
        if (favourites.contains(cityLoc)) {
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

        // Trim extra spaces and split the phrase by comma
        String[] parts = phrase.trim().split(",");

        StringBuilder result = new StringBuilder();

        // Capitalize and append each part
        for (int i = 0; i < parts.length; i++) {
            // Trim extra spaces
            String part = parts[i].trim();

            // Capitalize the first letter of the part
            String capitalizedPart = part.substring(0, 1).toUpperCase() + part.substring(1);

            // Capitalize the country code if it's present (index 1)
            if (i == 1 && parts.length > 1) {
                capitalizedPart = capitalizedPart.toUpperCase();
            }

            result.append(capitalizedPart);

            // Append comma if it's not the last part
            if (i < parts.length - 1) {
                result.append(", ");
            }
        }

        return result.toString();
    }
    



}
    


/*
    // Empty seacrch history button
    Button clearFavButton = new Button("Clear favourites");

    clearFavs.setOnAction(event -> {
        favourites.clear();
        updateFavBox();
    });

}*/



