# WeatherApp

WeatherApp is a JavaFX-based application that provides weather information for locations around the world. It offers current weather data, daily forecasts, and hourly forecasts using the OpenWeatherMap API.

## Features

- Search for weather information by city name, state (for US locations), and country code
- Display current weather conditions including temperature, feels-like temperature, humidity, wind speed, and more
- Show a 4-day forecast including daily high and low temperatures
- Provide hourly forecasts for the next 24 hours
- Toggle between metric and imperial units
- Save favorite locations for quick access
- View search history
- Automatically save and load favorite locations and current settings

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 17 or later
- Maven

### Installation

1. Clone the repository:
   ```
   git clone https://your-repository-url.git
   ```

2. Navigate to the project directory:
   ```
   cd WeatherApp/WeatherApp
   ```

3. Build the project:
   ```
   mvn clean package
   ```

### Running the Application

To run the application, use the following command: 
```
mvn javafx:run
```

## Usage

1. Enter a city name in the search bar (optionally include state and country code for more precise results)
2. Click the "Search" button or press Enter to fetch weather data
3. Use the "Change Unit" button to switch between metric and imperial units
4. Click "Save as favourite" to add a location to your favorites
5. Use the "Favorites" and "History" dropdowns to quickly access previously searched locations
6. The "Clear History" button allows you to remove all items from the search history
7. The "Quit" button ends the application

## File Structure

The main components of the project are:

WeatherApp/WeatherApp/src/main/java/fi/tuni/prog3/weatherapp/

├── WeatherData.java      # Handles API requests and data processing

├── DisplayHandler.java   # Manages data display formatting

├── iAPI.java             # Interface for API operations

└── iReadAndWriteToFile.java  # Interface for file I/O operations

![Screenshot 2024-09-23 214117](https://github.com/user-attachments/assets/20eefaf3-ce17-4501-b90b-0ccd39b9aaa8)
![Screenshot 2024-09-23 214035](https://github.com/user-attachments/assets/a2668c57-a467-4605-b3ed-eb957c9be706)
![Screenshot 2024-09-23 213930](https://github.com/user-attachments/assets/3de0e48d-bcec-4b17-8987-3db96ebe3fe7)
