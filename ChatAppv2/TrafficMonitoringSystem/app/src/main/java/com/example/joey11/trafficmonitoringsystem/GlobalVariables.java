package com.example.joey11.trafficmonitoringsystem;

import java.util.ArrayList;

public class GlobalVariables
{
    private GlobalVariables() {}
    private static GlobalVariables instance = null;
    public static GlobalVariables getInstance()
    {
        if(instance == null)
            instance = new GlobalVariables();
        return instance;
    }

    public static ArrayList<Report1Entity> report1Entities = null;
    public int dayConvert(String day)
    {
        int num = 0;
        if(day.equalsIgnoreCase("Monday"))
            num = 1;
        else if(day.equalsIgnoreCase("Tuesday"))
            num = 2;
        else if(day.equalsIgnoreCase("Wednesday"))
            num = 3;
        else if(day.equalsIgnoreCase("Thursday"))
            num = 4;
        else if(day.equalsIgnoreCase("Friday"))
            num = 5;
        else if(day.equalsIgnoreCase("Saturday"))
            num = 6;
        else if(day.equalsIgnoreCase("Sunday"))
            num = 7;
        return num;
    }
}
