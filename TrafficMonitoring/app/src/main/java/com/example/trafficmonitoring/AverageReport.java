package com.example.trafficmonitoring;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AverageReport extends AppCompatActivity {

    LineChart lineChart;
    ArrayList<String> dataList;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_average_report);
        lineChart = (LineChart)findViewById(R.id.lineChart);
        dataList = getIntent().getStringArrayListExtra("data");
        try {
            String json = new JSONObject(dataList.get(0)).getString("timeStamp");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final ArrayList<String> xAxisLabel = new ArrayList<>();
        xAxisLabel.add("Mon");
        xAxisLabel.add("Tue");
        xAxisLabel.add("Wed");
        xAxisLabel.add("Thu");
        xAxisLabel.add("Fri");
        xAxisLabel.add("Sat");
        xAxisLabel.add("Sun");
        XAxis xAxis = lineChart.getXAxis();

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xAxisLabel.get((int)value);
            }
        });
        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
        HashMap<String, Float> values = new HashMap<>();
        ArrayList<Entry> yAxis = new ArrayList<>();
        for(String data : dataList)
        {
            try {
                JSONObject json = new JSONObject(data);
                String day = json.getString("day");
                float average = Float.parseFloat(json.getString("average"));
                values.put(day, average);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        yAxis.add(new Entry(0, values.get("Monday")));
        yAxis.add(new Entry(1, values.get("Tuesday")));
        yAxis.add(new Entry(2, values.get("Wednesday")));
        yAxis.add(new Entry(3, values.get("Thursday")));
        yAxis.add(new Entry(4, values.get("Friday")));
        yAxis.add(new Entry(5, values.get("Saturday")));
        yAxis.add(new Entry(6, values.get("Sunday")));
        LineDataSet lineDataSet1 = new LineDataSet(yAxis,"Values");
        lineDataSet1.setDrawCircles(true);
        lineDataSet1.setColor(Color.BLUE);
        lineDataSets.add(lineDataSet1);
        lineDataSet1.setDrawFilled(true);
        lineChart.getXAxis().setAxisMinimum(0f);
        lineChart.getXAxis().setAxisMaximum(6f);
        lineChart.setData(new LineData(lineDataSets));
    }
}
