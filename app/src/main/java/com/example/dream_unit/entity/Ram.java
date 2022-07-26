package com.example.dream_unit.entity;

import com.google.gson.annotations.SerializedName;

public class Ram extends Part {
    @SerializedName("capacity")
    private int capacity;
    @SerializedName("speed")
    private int speed;
    @SerializedName("timing")
    private String timimg;
    @SerializedName("cas")
    private int cas;
    @SerializedName("ram_type")
    private Object ramType;

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getTimimg() {
        return timimg;
    }

    public void setTimimg(String timimg) {
        this.timimg = timimg;
    }

    public int getCas() {
        return cas;
    }

    public void setCas(int cas) {
        this.cas = cas;
    }

    public void setRamType(Object ramType) {
        this.ramType = ramType;
    }

    public Object getRamType() {return ramType;}
}
