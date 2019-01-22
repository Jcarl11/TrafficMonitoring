package com.example.joey11.imagestolistviewfromdb;

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
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    ArrayList<DataItem>dataItems;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataItems = new ArrayList<>();
        getMovies();
    }

    public void getMovies()
    {
        try
        {
            final ListView listView = (ListView)findViewById(R.id.listView);
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            String URL = "http://192.168.1.6:8080/StoringImageBase64/webresources/generic/getMovies";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response)
                {
                    try {
                        Iterator<String> iterator = response.keys();
                        while (iterator.hasNext())
                        {
                            String key = iterator.next();
                            JSONObject child = response.getJSONObject(key);
                            dataItems.add(new DataItem(convertBase64((String) child.get("image")), key, (String) child.get("title"), (String) child.get("description")));
                        }

                        MyAdapter adapter = new MyAdapter(MainActivity.this, R.layout.itemrow, dataItems);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setCancelable(true);
                                builder.setMessage(((TextView)view.findViewById(R.id.TEXTVIEW_DESCRIPTION)).getText());
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        });
                    }catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });

            int socketTimeout = 30000;
            RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjectRequest.setRetryPolicy(retryPolicy);
            requestQueue.add(jsonObjectRequest);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public Bitmap convertBase64(String imageString)
    {
        byte[] imageBytes = Base64.decode(imageString,Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0,imageBytes.length);
        return decodedImage;
    }
}
