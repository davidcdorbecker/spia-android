package com.fiuady.android.fluidproject.tables;

/**
 * Created by david on 21/11/2017.
 */

public class lastDateConfigure {

    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;
    private int each;

    public lastDateConfigure(int day, int month, int year, int hour, int minute, int each) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
        this.each = each;
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

    public int getEach() {
        return each;
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

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
