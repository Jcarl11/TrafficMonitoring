package com.joey.cnseats;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class PastMoviesActivity extends AppCompatActivity
{
    TextView dateView;
    ArrayList<PastMoviesDataItem> dataItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pastfilms);
        dataItemList = new ArrayList<>();
        dateView = (TextView)findViewById(R.id.TEXTVIEW_DATE2);
        Date date = new Date();
        dateView.setText(date.toGMTString());
        getPastMovies();
    }

    public void getPastMovies()
    {
        try
        {
            final ListView listView = (ListView)findViewById(R.id.listView_PastMovies);
            String Url = "http://192.168.137.1:8080/SampleWebService/webresources/generic/getPastFilms";
            RequestQueue requestQueue = Volley.newRequestQueue(PastMoviesActivity.this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response)
                {
                    try {
                        Iterator<String> keyIterator = response.keys();
                        while (keyIterator.hasNext())
                        {
                            String key = keyIterator.next();
                            JSONObject child = response.getJSONObject(key);
                            String title = (String)child.get("title");
                            String rating = (String)child.get("rating");
                            String time = (String)child.get("playdate");
                            String desc = (String)child.get("description");
                            dataItemList.add(new PastMoviesDataItem(title,desc,rating,time));
                        }

                        PastMoviesAdapter adapter = new PastMoviesAdapter(PastMoviesActivity.this,R.layout.itemrow3,dataItemList);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                            {
                                String description = ((TextView)findViewById(R.id.TEXTVIEW_DESCRIPTION)).getText().toString();
                                AlertDialog.Builder builder = new AlertDialog.Builder(PastMoviesActivity.this);
                                builder.setTitle("Full Description");
                                builder.setMessage(description);
                                builder.setCancelable(true);
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        });
                    }catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(PastMoviesActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            int socketTimeout = 30000;
            RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjectRequest.setRetryPolicy(retryPolicy);
            requestQueue.add(jsonObjectRequest);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public Bitmap decodeImage(String encodedImage)
    {
        byte[] decodedString1 = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedString = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.length);
        return decodedString;
    }
}
