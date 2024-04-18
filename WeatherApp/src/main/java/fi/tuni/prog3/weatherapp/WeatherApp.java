package fi.tuni.prog3.weatherapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * JavaFX Weather Application.
 */
public class WeatherApp extends Application {

    @Override
    public void start(Stage stage) {        
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));

        root.setTop(searchBar());
        root.setLeft(currentWeather());
        root.setRight(fewDaysForecast());
        root.setBottom(hourlyForecast());
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("WeatherApp");
        stage.show();
    }
    
    public static void main(String[] args) {
        launch();
    }
    
    /**
     * The `currentWeather()` function creates a BorderPane layout displaying current weather
     * information with temperature, city, air quality, rain, wind, and buttons for favorite and unit
     * change.
     * 
     * @return The `currentWeather()` method returns a `BorderPane` object that contains various
     * elements such as temperature information, city name, current weather data, additional weather
     * data like air quality, rain, and wind, as well as buttons for setting favorite and changing
     * units.
     */
    private BorderPane currentWeather() {
        BorderPane pane = new BorderPane();
        pane.setPrefSize(390, 275);
        pane.setPadding(new Insets(10, 10 , 10, 10));
        pane.setStyle("-fx-background-color: #ffffc2");
        
        VBox lowestTempBox = new VBox();
        lowestTempBox.setAlignment(Pos.CENTER);
        Text lowestTemp = new Text("L: -3*C");
        lowestTemp.setStyle("-fx-font: 20 arial;");
        lowestTempBox.getChildren().add(lowestTemp);
        pane.setLeft(lowestTempBox);

        VBox highestTempBox = new VBox();
        highestTempBox.setAlignment(Pos.CENTER);
        Text highestTemp = new Text("H: 10*C");
        highestTemp.setStyle("-fx-font: 20 arial;");
        highestTempBox.getChildren().add(highestTemp);
        pane.setRight(highestTempBox);

        VBox currentWeatherTextBox = new VBox();
        currentWeatherTextBox.setAlignment(Pos.CENTER);
        Label currentWeather = new Label("Current Weather");
        currentWeather.setStyle("-fx-font: 24 arial;");
        Text city = new Text("city");
        city.setStyle("-fx-font: 20 arial;");
        currentWeatherTextBox.getChildren().addAll(currentWeather, city);
        pane.setTop(currentWeatherTextBox);

        VBox currentWeatherDataBox = new VBox();
        currentWeatherDataBox.setAlignment(Pos.CENTER);
        Text temp = new Text("12*C");
        temp.setStyle("-fx-font: 45 arial;");
        Text logo = new Text("logo");
        currentWeatherDataBox.getChildren().addAll(temp, logo);
        pane.setCenter(currentWeatherDataBox);

        BorderPane additionalDataBox = new BorderPane();
        VBox data = new VBox();
        Text airQuality = new Text("Air Quality: ");
        airQuality.setStyle("-fx-font: 23 arial;");
        Text rain = new Text("Rain: ");
        rain.setStyle("-fx-font: 23 arial;");
        Text wind = new Text("Wind: ");
        wind.setStyle("-fx-font: 23 arial;");
        data.getChildren().addAll(airQuality, rain, wind);
        additionalDataBox.setLeft(data);
        VBox buttons = new VBox(15);
        Button setFav = new Button("Favorite");
        Button changeUnit = new Button("Imperial");
        buttons.getChildren().addAll(setFav, changeUnit);
        additionalDataBox.setRight(buttons);
        pane.setBottom(additionalDataBox);

        return pane;
    }

    private VBox fewDaysForecast() {
        VBox box = new VBox(15);
        box.setPrefSize(290, 275);
        box.setPadding(new Insets(10, 10 , 10, 10));
        box.setStyle("-fx-background-color: b8e2f2;");

        HBox day1 = new HBox(15);
        Text date1 = new Text("TODAY");
        date1.setStyle("-fx-font: 40 arial;");
        Text temp1 = new Text("0*C");
        temp1.setStyle("-fx-font: 28 arial;");
        Text logo1 = new Text("logo");
        Region r = new Region();
        HBox.setHgrow(r, Priority.ALWAYS);
        day1.getChildren().addAll(date1, r, temp1, logo1);

        HBox day2 = new HBox(15);
        Text date2 = new Text("TODAY");
        date2.setStyle("-fx-font: 40 arial;");
        Text temp2 = new Text("0*C");
        temp2.setStyle("-fx-font: 28 arial;");
        Text logo2 = new Text("logo");
        Region r2 = new Region();
        HBox.setHgrow(r2, Priority.ALWAYS);
        day2.getChildren().addAll(date2, r2, temp2, logo2);

        HBox day3 = new HBox(15);
        Text date3 = new Text("TODAY");
        date3.setStyle("-fx-font: 40 arial;");
        Text temp3 = new Text("0*C");
        temp3.setStyle("-fx-font: 28 arial;");
        Text logo3 = new Text("logo");
        Region r3 = new Region();
        HBox.setHgrow(r3, Priority.ALWAYS);
        day3.getChildren().addAll(date3, r3, temp3, logo3);

        HBox day4 = new HBox(15);
        Text date4 = new Text("TODAY");
        date4.setStyle("-fx-font: 40 arial;");
        Text temp4 = new Text("0*C");
        temp4.setStyle("-fx-font: 28 arial;");
        Text logo4 = new Text("logo");
        Region r4 = new Region();
        HBox.setHgrow(r4, Priority.ALWAYS);
        day4.getChildren().addAll(date4, r4, temp4, logo4);

        HBox day5 = new HBox(15);
        Text date5 = new Text("TODAY");
        date5.setStyle("-fx-font: 40 arial;");
        Text temp5 = new Text("0*C");
        temp5.setStyle("-fx-font: 28 arial;");
        Text logo5 = new Text("logo");
        Region r5 = new Region();
        HBox.setHgrow(r5, Priority.ALWAYS);
        day5.getChildren().addAll(date5, r5, temp5, logo5);

        box.getChildren().addAll(day1, day2, day3, day4, day5);
        return box;
    }

    private ScrollPane hourlyForecast() {
        GridPane grid = new GridPane();
        for (int i = 0; i < 24; i++) {
            addColumn(i, grid);
        }

        ScrollPane scroll = new ScrollPane();
        scroll.setPrefSize(650, 200);
        scroll.setContent(grid);

        return scroll;
    }

    private void addColumn(int hour, GridPane grid) {
        Text hourText = new Text(Integer.toString(hour));
        hourText.setStyle("-fx-font: 20 arial;");
        grid.add(hourText, hour, 0);
        Text degree = new Text("0*C");
        degree.setStyle("-fx-font: 20 arial;");
        grid.add(degree, hour, 1);
        Text logo = new Text("logo");
        logo.setStyle("-fx-font: 20 arial");
        grid.add(logo, hour, 2);
    }

    private BorderPane searchBar() {
        BorderPane pane = new BorderPane();

        Button searchButton = new Button("Search");
        pane.setLeft(searchButton);

        Button historyButton = new Button("History");
        pane.setRight(historyButton);

        TextField searchBar = new TextField();
        pane.setCenter(searchBar);

        return pane;
    }
 } 