package com.example.dream_unit.entity;

import com.google.gson.annotations.SerializedName;

public class VideoCard extends Part{
    @SerializedName("coreClock")
    private int coreClock;
    @SerializedName("gpu")
    private String gpu;
    @SerializedName("memory")
    private int memory;

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getCoreClock() {return coreClock;}

    public void setCoreClock(int coreClock) {
        this.coreClock = coreClock;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }
}
