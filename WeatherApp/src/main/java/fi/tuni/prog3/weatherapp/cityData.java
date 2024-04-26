package fi.tuni.prog3.weatherapp;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class cityData {
    @Expose
    private String cityName;
    @Expose
    private String latitude;
    @Expose
    private String longitude;
    @Expose
    private String state;
    @Expose
    private String country;
    @Expose
    private boolean isMetric;

    @Override
    public String toString() {
        return cityName; // This will be used by the ComboBox to display the city name
    }

    // Getters and setters (necessary for Gson)
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    public boolean isMetric() {
        return isMetric;
    }

    public void setMetric(boolean metric) {
        isMetric = metric;
    }
}
