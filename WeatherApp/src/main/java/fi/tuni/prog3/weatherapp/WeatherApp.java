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


/**
 * JavaFX Weather Application.
 */
public class WeatherApp extends Application { 
    // By default the unit of the program is metric
    private final DisplayHandler displayHandler = new DisplayHandler();
    private final ImageHandler imageHandler = new ImageHandler();
    private boolean isMetric = true;
    private String[] currentCityData = {};
    // Daily forecast Nodes
    private Text[][] dailyForecastTexts = new Text[4][3];
    private ImageView[] dailyForecastImages = new ImageView[4];
    // Hourly forecast Nodes
    private Text[][] hourlyForecastTexts = new Text[24][4];
    private ImageView[] hourlyForecastImages = new ImageView[24];
    // Current forecast Nodes
    private Text[] currentWeatherTexts = new Text[7];
    private ImageView currentWeatherView; 

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
        Button setFav = new Button("Favorite");
        setFav.setPrefWidth(100);
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
        buttons.getChildren().addAll(setFav, changeUnit);
        additionalDataBox.setRight(buttons);
        currentWeather.setBottom(additionalDataBox);

        // Search Bar
        HBox searchBarSection = new HBox(15);
        searchBarSection.setAlignment(Pos.CENTER);
        
        TextField searchBar = new TextField();
        searchBar.setPrefWidth(230);
        searchBar.setPromptText("City, (State,) Country Code");
        
        Button searchButton = new Button("Search");
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
                    String cityName = searchBar.getText().split(",", 3)[0];
                    String[] cityInfo = displayHandler.getCityInformation(searchBar);
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
                        hourlyForecastTexts[hour][0].setText(hourlyForecastData[hour][0] + ":00");
                        hourlyForecastTexts[hour][1].setText(hourlyForecastData[hour][1]);
                        hourlyForecastTexts[hour][2].setText(hourlyForecastData[hour][2]);
                        hourlyForecastImages[hour].setImage(new Image(getClass().getResourceAsStream(imageHandler.imageHandler(hourlyForecastData[hour][3]))));
                        hourlyForecastTexts[hour][3].setText(hourlyForecastData[hour][4]);
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
                    String cityName = searchBar.getText().split(",", 3)[0];
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
                        hourlyForecastTexts[hour][0].setText(hourlyForecastData[hour][0] + ":00");
                        hourlyForecastTexts[hour][1].setText(hourlyForecastData[hour][1]);
                        hourlyForecastTexts[hour][2].setText(hourlyForecastData[hour][2]);
                        hourlyForecastImages[hour].setImage(new Image(getClass().getResourceAsStream(imageHandler.imageHandler(hourlyForecastData[hour][3]))));
                        hourlyForecastTexts[hour][3].setText(hourlyForecastData[hour][4]);
                    }
                } 

            }
        });
        Label favLabel = new Label("Favorite:");
        Label historyLabel = new Label("History:");
        Button favButton = new Button("Favorite");
        Button quitButton = new Button("Quit");
        Button historyButton = new Button("History");
        Button clearHistoryButton = new Button("Clear History");
        searchBarSection.getChildren().addAll(searchBar, searchButton, historyLabel, historyButton, clearHistoryButton, favLabel, favButton, quitButton);


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
} 

