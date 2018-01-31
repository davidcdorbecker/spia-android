package com.fiuady.android.fluidproject.tables;


public class lastPumpOn {
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;


    public lastPumpOn(int day, int month, int year, int hour, int minute) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getStringDay (){
        if (day < 10) {
            return "0" + String.valueOf(day);
        }
        else {
            return String.valueOf(day);
        }
    }

    public String getStringMonth (){
        if (month < 10) {
            return "0" + String.valueOf(month);
        }
        else {
            return String.valueOf(month);
        }
    }

    public String getStringYear (){
        return String.valueOf(year);
    }

    public String getStringHour (){
        if (hour < 10) {
            return "0" + String.valueOf(hour);
        }
        else {
            return String.valueOf(hour);
        }
    }

    public String getStringMinute (){
        if (minute < 10) {
            return "0" + String.valueOf(minute);
        }
        else {
            return String.valueOf(minute);
        }
    }
}
