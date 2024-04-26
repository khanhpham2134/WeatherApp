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
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;


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
        fewDaysForecast.setPrefWidth(350);
        fewDaysForecast.setPadding(new Insets(10, 10 , 10, 10));
        fewDaysForecast.setStyle("-fx-background-color: #b8e2f2;");
        
        for (int i = 0; i < 4; i++) {
            HBox day = new HBox(15);
            day.setAlignment(Pos.CENTER);

            Text date = new Text("Day " + (i + 1)); // Example: You can replace this with the actual date
            date.setStyle("-fx-font: 28 arial; -fx-font-weight: bold;");

            Text minTemp = new Text("0°C");
            minTemp.setStyle("-fx-font: 28 arial;");

            Text maxTemp = new Text("0°C");
            maxTemp.setStyle("-fx-font: 28 arial;");

            Image description = new Image(getClass().getResourceAsStream("/icons/day-clear.png"));
            ImageView descriptionView = new ImageView(description);
            descriptionView.setFitHeight(50);
            descriptionView.setFitWidth(50);

            Region r = new Region();
            HBox.setHgrow(r, Priority.ALWAYS);

            day.getChildren().addAll(date, r, minTemp, maxTemp, descriptionView);
            dailyForecastTexts[i][0] = date;
            dailyForecastTexts[i][1] = minTemp;
            dailyForecastTexts[i][2] = maxTemp;
            dailyForecastImages[i] = descriptionView;

            fewDaysForecast.getChildren().add(day);
        }
        
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
        currentWeather.setPrefWidth(420);
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
        Text feelsLike = new Text("Feels like: ");
        feelsLike.setStyle("-fx-font: 23 arial;");
        currentWeatherTexts[4] = feelsLike;
        Text humid = new Text("Humidity: "); 
        humid.setStyle("-fx-font: 23 arial;");
        currentWeatherTexts[5] = humid;
        Text wind = new Text("Wind Speed: ");
        wind.setStyle("-fx-font: 23 arial;");
        data.getChildren().addAll(feelsLike, humid, wind);
        currentWeatherTexts[6] = wind;
        additionalDataBox.setLeft(data);
        VBox buttons = new VBox(15);

        // Load previously saved favourites
        readToFile();
        updateFavBox();
        updateHisBox();

        saveFavButton = new ToggleButton("Save as favourite");
        saveFavButton.setMaxWidth(210);
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
        changeUnit.setMaxWidth(180);
        changeUnit.setOnAction((ActionEvent event) -> {
            if (!isMetric) {
                // Change daily forecast
                dailyForecast = displayHandler.getDailyForecastMetric(currentCityData);
                updateDailyForecast(dailyForecastTexts, dailyForecastImages, dailyForecast);

                // Change current weather section
                currentWeatherData = displayHandler.getCurrentWeatherDataMetric(currentCityData);
                updateCurrentWeather(currentWeatherTexts, currentWeatherView, currentWeatherData, dailyForecast);
                
                // Change hourly forecast
                hourlyForecastData = displayHandler.getHourlyForecastMetric(currentCityData);
                updateHourlyForecast(hourlyForecastTexts, hourlyForecastImages, hourlyForecastData);
                
                isMetric = true;

            } else {
                // Change daily forecast
                dailyForecast = displayHandler.getDailyForecastImperial(currentCityData);
                updateDailyForecast(dailyForecastTexts, dailyForecastImages, dailyForecast);

                // Change current weather section
                currentWeatherData = displayHandler.getCurrentWeatherDataImperial(currentCityData);
                updateCurrentWeather(currentWeatherTexts, currentWeatherView, currentWeatherData, dailyForecast);
                
                // Change hourly forecast
                hourlyForecastData = displayHandler.getHourlyForecastImperial(currentCityData);
                updateHourlyForecast(hourlyForecastTexts, hourlyForecastImages, hourlyForecastData);

                isMetric = false;
            }
        });
        buttons.getChildren().addAll(saveFavButton, changeUnit);
        additionalDataBox.setRight(buttons);
        currentWeather.setBottom(additionalDataBox);

        // Search Bar
        HBox searchBarSection = new HBox(15);
        searchBarSection.setAlignment(Pos.CENTER);
        
        searchBar = new TextField();
        searchBar.setPrefWidth(230);
        searchBar.setPromptText("City, (State,) Country Code");
        
        searchButton = new Button("Search");
        searchButton.setOnAction((ActionEvent event) -> {
            if (isMetric) {
                String input = searchBar.getText();
                String[] inputParams = input.split(",", 10);
                boolean inputIsInvalid = displayHandler.ifInputValid(inputParams);
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

                    cityInfo = displayHandler.getCityInformation(inputParams);
                    

                    currentCityData = cityInfo;
                    if (cityInfo[2] == "" || !cityInfo[3].contains("US")) {
                        cityLoc = cityName.toUpperCase() + ", " + cityInfo[3];
                        city.setText(cityLoc);
                    } else {
                        cityLoc = cityName.toUpperCase() + ", " + cityInfo[2] + ", " + cityInfo[3];
                        city.setText(cityLoc);
                    }
                    if (!history.contains(cityLoc)) {
                        history.add(cityLoc);}
                    
                    updatesaveFavButtonState();
                    updateFavBox();
                    updateHisBox();
                    
                    // Change daily forecast
                    dailyForecast = displayHandler.getDailyForecastMetric(cityInfo);
                    updateDailyForecast(dailyForecastTexts, dailyForecastImages, dailyForecast);

                    // Change current weather section
                    currentWeatherData = displayHandler.getCurrentWeatherDataMetric(cityInfo);
                    updateCurrentWeather(currentWeatherTexts, currentWeatherView, currentWeatherData, dailyForecast);
                    
                    // Change hourly forecast
                    hourlyForecastData = displayHandler.getHourlyForecastMetric(cityInfo);
                    updateHourlyForecast(hourlyForecastTexts, hourlyForecastImages, hourlyForecastData);
                } 
            } else {
                String input = searchBar.getText();
                String[] inputParams = input.split(",", 10);
                boolean inputIsInvalid = displayHandler.ifInputValid(inputParams);
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
                    cityInfo = displayHandler.getCityInformation(inputParams);
                    currentCityData = cityInfo;
                    if (cityInfo[2] == "" || !cityInfo[3].contains("US")) {
                        cityLoc = cityName.toUpperCase() + ", " + cityInfo[3];
                        city.setText(cityLoc);
                    } else {
                        cityLoc = cityName.toUpperCase() + ", " + cityInfo[2] + ", " + cityInfo[3];
                        city.setText(cityLoc);
                    }
                    if (!history.contains(cityLoc)) {
                        history.add(cityLoc);}
                    
                    updatesaveFavButtonState();
                    updateFavBox();
                    updateHisBox();
                    
                    // Change daily forecast
                    dailyForecast = displayHandler.getDailyForecastImperial(cityInfo);
                    updateDailyForecast(dailyForecastTexts, dailyForecastImages, dailyForecast);

                    // Change current weather section
                    currentWeatherData = displayHandler.getCurrentWeatherDataImperial(cityInfo);
                    updateCurrentWeather(currentWeatherTexts, currentWeatherView, currentWeatherData, dailyForecast);
                    
                    // Change hourly forecast
                    hourlyForecastData = displayHandler.getHourlyForecastImperial(cityInfo);
                    updateHourlyForecast(hourlyForecastTexts, hourlyForecastImages, hourlyForecastData);
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

    /**
        * Creates and returns a quit button.
        *
        * @return the quit button
        */
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
    
    /**
     * Writes the data of the WeatherApp object to three separate files: "favourites.txt", "current_location.txt", and "history.txt".
     * The favourites and history lists are written line by line to their respective files.
     * The current location and isMetric values are written to "current_location.txt".
     * If an IOException occurs during the writing process, the exception is printed to the standard error stream.
     */
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
    
    /**
     * Reads data from files and updates the weather information in the application.
     * This method reads data from three files: "favourites.txt", "current_location.txt", and "history.txt".
     * It populates the 'favourites' list with the contents of "favourites.txt".
     * It reads the current location and weather unit information from "current_location.txt" and updates the UI accordingly.
     * It populates the 'history' list with the contents of "history.txt".
     * 
     * @throws IOException if an I/O error occurs while reading the files.
     */
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
            cityInfo = displayHandler.getCityInformation(inputParams);
            currentCityData = cityInfo;
            if (cityInfo[2] == "" || !cityInfo[3].contains("US")) {
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
            updateDailyForecast(dailyForecastTexts, dailyForecastImages, dailyForecast);
            updateCurrentWeather(currentWeatherTexts, currentWeatherView, currentWeatherData, dailyForecast);
            updateHourlyForecast(hourlyForecastTexts, hourlyForecastImages, hourlyForecastData);

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
     
    /**
     * Returns a ComboBox that displays the user's favorite items.
     * 
     * @return The ComboBox that displays the user's favorite items.
     */
    private ComboBox<String> favouritesDropBox() {
        try {
            // Initialize favouritesBox only if it's not already initialized
            if (favouritesBox == null) {
                favouritesBox = new ComboBox<>();
                favouritesBox.setMaxWidth(10);
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
    
    /**
     * Creates and returns a ComboBox with a list of historical search items.
     * If the ComboBox is not already initialized, it initializes it and sets its maximum width to 10.
     * The ComboBox is set with a prompt text "Choose History".
     * When a historical search item is selected from the ComboBox, it sets the selected item as the text in the searchBar and triggers the searchButton.
     * If the ComboBox is empty and the history list is not null or empty, it sets the items of the ComboBox to the history list.
     *
     * @return The ComboBox with historical search items.
     */
    private ComboBox<String> historyDropBox() {
        try {
            // Initialize favouritesBox only if it's not already initialized
            if (historyBox == null) {
                historyBox = new ComboBox<>();
                historyBox.setMaxWidth(10);
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
      
    /**
     * Updates the favorites drop box with the current list of favorites.
     * Clears the existing items in the drop box and sets the items to the current list of favorites.
     */
    private void updateFavBox() {
        favouritesDropBox().getItems().clear();
        favouritesDropBox().getItems().setAll(favourites);
    }

    /**
     * Updates the history drop box with the items from the history list.
     */
    private void updateHisBox() {
        historyDropBox().getItems().clear();
        historyDropBox().getItems().setAll(history);
    }
    
    /**
     * Updates the state of the save favorite button based on whether the current city location is in the favorites list.
     * If the current city location is in the favorites list, the button will be selected and display "Unsave".
     * If the current city location is not in the favorites list, the button will be unselected and display "Save as favourite".
     */
    private void updatesaveFavButtonState() {
        if (favourites.contains(cityLoc)) {
            saveFavButton.setSelected(true);
            saveFavButton.setText("Unsave");
        } else {
            saveFavButton.setSelected(false);
            saveFavButton.setText("Save as favourite");
        }
    }

    /**
     * Updates the daily forecast in the WeatherApp.
     *
     * @param dailyForecastTexts   a 2D array of Text objects representing the daily forecast text elements
     * @param dailyForecastImages  an array of ImageView objects representing the daily forecast image elements
     * @param dailyForecast        a 2D array of Strings representing the daily forecast data
     */
    private void updateDailyForecast(Text[][] dailyForecastTexts, ImageView[] dailyForecastImages, String[][] dailyForecast) {
        for (int i = 0; i < 4; i++) {
            dailyForecastTexts[i][0].setText(dailyForecast[i][0]);
            dailyForecastTexts[i][1].setText(dailyForecast[i][2]);
            dailyForecastTexts[i][2].setText(dailyForecast[i][3]);
            dailyForecastImages[i].setImage(new Image(getClass().getResourceAsStream(imageHandler.forecastImageHandler(dailyForecast[i][4]))));
        }
    }
    
    /**
     * Updates the current weather information in the UI.
     *
     * @param currentWeatherTexts An array of Text objects representing the UI elements for displaying current weather information.
     * @param currentWeatherView  An ImageView object representing the UI element for displaying the current weather icon.
     * @param currentWeatherData  An array of strings containing the current weather data.
     * @param dailyForecast       A 2D array of strings containing the daily forecast data.
     */
    private void updateCurrentWeather(Text[] currentWeatherTexts, ImageView currentWeatherView, String[] currentWeatherData, String[][] dailyForecast) {
        currentWeatherTexts[3].setText(currentWeatherData[0]);
        currentWeatherTexts[4].setText("Feels like: " + currentWeatherData[1]);
        currentWeatherTexts[0].setText("L: " + dailyForecast[0][2]);
        currentWeatherTexts[1].setText("H: " + dailyForecast[0][3]);
        currentWeatherTexts[5].setText("Humidity: " + currentWeatherData[4]);
        currentWeatherView.setImage(new Image(getClass().getResourceAsStream(imageHandler.currentImageHandler(currentWeatherData))));
        currentWeatherTexts[6].setText("Wind Speed: " + currentWeatherData[7]);
    }

    /**
     * Updates the hourly forecast display with the provided data.
     *
     * @param hourlyForecastTexts   The 2D array of Text objects representing the hourly forecast text elements.
     * @param hourlyForecastImages  The array of ImageView objects representing the hourly forecast image elements.
     * @param hourlyForecastData    The 2D array of Strings containing the hourly forecast data.
     */
    private void updateHourlyForecast(Text[][] hourlyForecastTexts, ImageView[] hourlyForecastImages, String[][] hourlyForecastData) {
        String sunrise = currentWeatherData[9];
        String sunset = currentWeatherData[10];
        for (int hour = 0; hour < 24; hour++) {
            hourlyForecastTexts[hour][0].setText(hourlyForecastData[hour][0] + ":00");
            hourlyForecastTexts[hour][1].setText(hourlyForecastData[hour][1]);
            hourlyForecastTexts[hour][2].setText(hourlyForecastData[hour][2]);
            hourlyForecastImages[hour].setImage(new Image(getClass().getResourceAsStream(imageHandler.hourlyImageHandler(hourlyForecastData[hour], sunrise, sunset))));
            hourlyForecastTexts[hour][3].setText(hourlyForecastData[hour][4]);
        }
    }
    
}