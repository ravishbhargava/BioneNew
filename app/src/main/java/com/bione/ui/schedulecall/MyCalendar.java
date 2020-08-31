package com.bione.ui.schedulecall;


import com.bione.utils.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyCalendar {
    // storing day - like Sun, Wed etc..,
    // date like 1, 2 etc..
    //Month 1..12
    // year .. 2019.. beyond

    private String day;
    private String date, month, year;
    private int monthNumber = 0;
    private String monthName = "";
    private int pos;

    public MyCalendar() {
    }

    public MyCalendar(String day, String date, String month, String year, int i) {
        this.day = day;
        this.date = date;
        this.month = getMonthStr(month);
        this.year = year;
        this.pos = i;
        this.monthNumber = Integer.parseInt(month) + 1;

    }

    private String getMonthStr(String month) {

        Calendar cal = Calendar.getInstance();

        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        int monthnum = Integer.parseInt(month);
        cal.set(Calendar.MONTH, monthnum);
        String month_name = month_date.format(cal.getTime());
        return month_name;

    }


    public int getPos() {
        return pos;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String date) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMonth() {
        return month;
    }


    public String getYear() {
        return year;
    }


    public void setYear(String year) {
        this.year = year;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(int monthNumber) {
        this.monthNumber = monthNumber;
    }

    public String getMonthName() throws ParseException {
        String monthpassed = String.valueOf(monthNumber);
        return formatMonth(monthpassed);
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String formatMonth(String month) throws ParseException {
        SimpleDateFormat monthParse = new SimpleDateFormat("MM");
        SimpleDateFormat monthDisplay = new SimpleDateFormat("MMM");
        return monthDisplay.format(monthParse.parse(month));
    }
}