package com.joey.barometerv4;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity implements SensorEventListener
{
    int i = 0;
    private SensorManager sensorManager;
    private Sensor sensor;
    private float currentDegree = 0f;
    private TextView valueTxt;
    private Button btnSave;
    private Button startBTN;
    private ImageView image;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        valueTxt = (TextView)findViewById(R.id.valTxt);
        startBTN = (Button)findViewById(R.id.btnStart);
        sensorManager = (SensorManager)getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        image = (ImageView) findViewById(R.id.imageHand);




    }

    public void startTracking(View view)
    {
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void click(View view)
    {

    }
    public void stoptracking(View view)
    {
        sensorManager.unregisterListener(this);
        valueTxt.setText("0000.00");
        image.clearAnimation();
        image.setRotation(-28);
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {


        if(sensorEvent.sensor.getType() == Sensor.TYPE_PRESSURE)
        {
            valueTxt.setText(String.format("%.2f", (sensorEvent.values[0])));
            RequestQueue sample = Volley.newRequestQueue(MainActivity.this);
            String URL = "http://192.168.1.6:8080/WebServiceProject/webresources/test/insertdata/" + String.valueOf(sensorEvent.values[0]);
            float degree = (sensorEvent.values[0]);
            if(valueTxt.getText() != "0000.00")
            {
                image.setRotation(degree + 90);
            }

            StringRequest stringRequest = new StringRequest(Request.Method.GET,URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            /*Math.round(Integer.parseInt(valueTxt.getText().toString()) / 77)*/
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   // Toast.makeText(MainActivity.this, error.getMessage()+"fail", Toast.LENGTH_LONG).show();
                }
            });
            sample.add(stringRequest);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public static void verifyStoragePermissions(Activity activity){
        int write = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(write != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}
