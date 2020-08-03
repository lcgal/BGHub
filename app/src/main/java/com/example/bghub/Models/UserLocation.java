package com.example.bghub.Models;



public class UserLocation {

    private String UserId;

    private double Latitude;

    private double Longitude;

    public UserLocation (String userId,double latitude, double longitude) {
        UserId  = userId;
        Latitude = latitude;
        Longitude = longitude;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }


}
