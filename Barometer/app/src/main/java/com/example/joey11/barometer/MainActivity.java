package com.example.joey11.barometer;

import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener
{
    private SensorManager sensorManager;
    private Sensor sensor;
    private TextView valueTxt;
    private Button startBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        valueTxt = (TextView)findViewById(R.id.valTxt);
        startBTN = (Button)findViewById(R.id.btnStart);
        sensorManager = (SensorManager)getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
    }

    public void startTracking(View view)
    {
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_UI);
    }

    public void stoptracking(View view)
    {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_PRESSURE)
        {
            valueTxt.setText(String.valueOf(sensorEvent.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
