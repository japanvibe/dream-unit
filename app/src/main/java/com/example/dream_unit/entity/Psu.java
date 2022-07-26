package com.example.dream_unit.entity;

import com.google.gson.annotations.SerializedName;

public class Psu extends Part {
    @SerializedName("maxPower")
    private int maxPower;
    @SerializedName("fans")
    private int fans;
    @SerializedName("energyEfficiency")
    private Object energyEfficiency;
    @SerializedName("weight")
    private float weight;
    @SerializedName("power_from")
    private int powerFrom;
    @SerializedName("power_to")
    private int powerTo;

    public int getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(int maxPower) {
        this.maxPower = maxPower;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public Object getEnergyEfficiency() {
        return energyEfficiency;
    }

    public void setEnergyEfficiency(Object energyEfficiency) {
        this.energyEfficiency = energyEfficiency;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setPowerFrom(int powerFrom) {
        this.powerFrom = powerFrom;
    }

    public void setPowerTo(int powerTo) {
        this.powerTo = powerTo;
    }
}
