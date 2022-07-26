package com.example.dream_unit.entity;

import com.google.gson.annotations.SerializedName;

public class Cpu extends Part{
    @SerializedName("series")
    private Object series;
    @SerializedName("speed")
    private float speed;
    @SerializedName("core")
    private int core;
    @SerializedName("thread")
    private int thread;
    @SerializedName("turbo")
    private float turbo;
    @SerializedName("tdp")
    private int tdp;
    @SerializedName("socket")
    private String socket;
    @SerializedName("gen")
    private String  gen;

    public Object getSeries() {
        return series;
    }

    public void setSeries(Object series) {
        this.series = series;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getCore() {
        return core;
    }

    public void setCore(int core) {
        this.core = core;
    }

    public int getThread() {
        return thread;
    }

    public void setThread(int thread) {
        this.thread = thread;
    }

    public float getTurbo() {
        return turbo;
    }

    public void setTurbo(float turbo) {
        this.turbo = turbo;
    }

    public int getTdp() {
        return tdp;
    }

    public void setTdp(int tdp) {
        this.tdp = tdp;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }
}
