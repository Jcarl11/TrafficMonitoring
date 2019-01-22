package com.example.trafficmonitoring;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GlobalVariables
{
    private GlobalVariables(){}
    private static GlobalVariables instance = null;
    public static GlobalVariables getInstance()
    {
        if(instance == null)
            instance = new GlobalVariables();
        return instance;
    }
    public String convertDateToISO(Date date)
    {
        DateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        return newFormat.format(date);
    }
    public int convertDay(String day)
    {
        int num = -1;
        if(day.equalsIgnoreCase("Monday"))
            num = 0;
        else if(day.equalsIgnoreCase("Tuesday"))
            num = 1;
        else if(day.equalsIgnoreCase("Wednesday"))
            num = 1;
        else if(day.equalsIgnoreCase("Thursday"))
            num = 1;
        else if(day.equalsIgnoreCase("Friday"))
            num = 1;
        else if(day.equalsIgnoreCase("Saturday"))
            num = 1;
        else if(day.equalsIgnoreCase("Sunday"))
            num = 1;
        return num;
    }
}
