package com.example.dream_unit.entity;

import com.google.gson.annotations.SerializedName;

public class Mobo extends Part {
    @SerializedName("socket")
    private String socket;
    @SerializedName("chipset")
    private String chipset;
    @SerializedName("memoryCap")
    private int memoryCap;
    @SerializedName("memoryStandard")
    private Object memoryStandard;
    @SerializedName("usbPorts")
    private int usbPorts;
    @SerializedName("PCI")
    private int pci;
    @SerializedName("type")
    private Object type;

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public String getChipset() {
        return chipset;
    }

    public void setChipset(String chipset) {
        this.chipset = chipset;
    }

    public int getMemoryCap() {
        return memoryCap;
    }

    public void setMemoryCap(int memoryCap) {
        this.memoryCap = memoryCap;
    }

    public Object getMemoryStandard() {
        return memoryStandard;
    }

    public void setMemoryStandard(Object memoryStandard) {
        this.memoryStandard = memoryStandard;
    }

    public int getUsbPorts() {
        return usbPorts;
    }

    public void setUsbPorts(int usbPorts) {
        this.usbPorts = usbPorts;
    }

    public int getPci() {
        return pci;
    }

    public void setPci(int pci) {
        this.pci = pci;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }
}
