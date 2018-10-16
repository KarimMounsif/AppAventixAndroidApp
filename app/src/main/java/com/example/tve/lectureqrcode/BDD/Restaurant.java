package com.example.tve.lectureqrcode.BDD;

/**
 * Created by TVE on 02/02/2018.
 */

public class Restaurant {
    private String adrRestaurant;
    private String nomRestaurant;
    private String telRestaurant;
    private String positionRestaurant;

    public Restaurant(String adrRestaurant, String nomRestaurant, String telRestaurant, String positionRestaurant) {
        this.adrRestaurant = adrRestaurant;
        this.nomRestaurant = nomRestaurant;
        this.telRestaurant = telRestaurant;
        this.positionRestaurant = positionRestaurant;
    }

    public Restaurant() {
    }

    public String getAdrRestaurant() {
        return adrRestaurant;
    }

    public void setAdrRestaurant(String adrRestaurant) {
        this.adrRestaurant = adrRestaurant;
    }

    public String getNomRestaurant() {
        return nomRestaurant;
    }

    public void setNomRestaurant(String nomRestaurant) {
        this.nomRestaurant = nomRestaurant;
    }

    public String getTelRestaurant() {
        return telRestaurant;
    }

    public void setTelRestaurant(String telRestaurant) {
        this.telRestaurant = telRestaurant;
    }

    public String getPositionRestaurant() {
        return positionRestaurant;
    }

    public void setPositionRestaurant(String positionRestaurant) {
        this.positionRestaurant = positionRestaurant;
    }
}