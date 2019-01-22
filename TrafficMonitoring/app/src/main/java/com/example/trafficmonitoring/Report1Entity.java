package com.example.trafficmonitoring;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

class Report1Entity
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

    public String toJSON()
    {
        JSONObject json = new JSONObject();
        try {
            json.put("timeStamp", GlobalVariables.getInstance().convertDateToISO(getTimeStamp()));
            json.put("average", getAverage());
            json.put("day", getDay());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }
    private Date timeStamp;
    private String average;
    private String day;
}
