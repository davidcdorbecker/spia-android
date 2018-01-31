package com.fiuady.android.fluidproject.tables;

public class lastHumidity {

    public lastHumidity(int plant1, int plant2) {
        this.plant1 = plant1;
        this.plant2 = plant2;
    }

    private int plant1;
    private int plant2;

    public int getPlant1() {
        return plant1;
    }

    public void setPlant1(int plant1) {
        this.plant1 = plant1;
    }

    public int getPlant2() {
        return plant2;
    }

    public void setPlant2(int plant2) {
        this.plant2 = plant2;
    }
}
