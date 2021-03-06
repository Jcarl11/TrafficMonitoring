package com.example.joey11.trafficmonitoringsystem;

import java.util.Date;

public class Report1Entity
{
    public Report1Entity(Date timeStamp, String average, String day)
    {
        this.timeStamp = timeStamp;
        this.average = average;
        this.day = day;
    }

    public Report1Entity() {}

    public Date getTimeStamp() {return timeStamp;}
    public void setTimeStamp(Date timeStamp) {this.timeStamp = timeStamp;}
    public String getAverage() {return average;}
    public void setAverage(String average) {this.average = average;}
    public String getDay() {return day;}
    public void setDay(String day) {this.day = day;}

    private Date timeStamp;
    private String average;
    private String day;

}
