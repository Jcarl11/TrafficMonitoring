package com.joey.cnseats;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyReservationsActivity extends AppCompatActivity
{
    TextView USERNAME;
    String userName;
    ArrayList<String>tickets;
    List<DataItem> dataItemList;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myreservations);
        tickets = new ArrayList<>();
        dataItemList = new ArrayList<>();
        USERNAME = (TextView)findViewById(R.id.TEXTVIEW_DATE2);
        SharedPreferences sharedPreferences = getSharedPreferences("userid", Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("username","No Username");
        USERNAME.setText(userName);
        retriveReservations();
    }

    public void retriveReservations()
    {
        try
        {
            final ListView listView = (ListView)findViewById(R.id.listView_Upcoming);
            final ArrayList<String> reservations = new ArrayList<>();
            String Url = "http://192.168.137.1:8080/SampleWebService/webresources/generic/getAllreservation";
            RequestQueue requestQueue = Volley.newRequestQueue(MyReservationsActivity.this);
            JSONObject params = new JSONObject();
            params.put("username", userName);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Url, params ,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response)
                {
                    try
                    {
                        Iterator<String> keyIterator = response.keys();
                        while(keyIterator.hasNext())
                        {
                            String key = keyIterator.next();
                            JSONObject child = response.getJSONObject(key);

                               Iterator<String>childIterator = child.keys();
                                while(childIterator.hasNext())
                                {
                                    String childKey = childIterator.next();
                                    dataItemList.add(new DataItem(key,childKey,(String) child.get(childKey)));
                                }

                        }
                        final Adapter adapter = new Adapter(MyReservationsActivity.this,R.layout.itemrow,dataItemList);
                        listView.setAdapter(adapter);
                        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
                            {
                                final String movieTitle = ((TextView)view.findViewById(R.id.TEXTVIEW_MOVIETITLE)).getText().toString();
                                final String movieTime = ((TextView)view.findViewById(R.id.TEXTVIEW_TIME)).getText().toString();
                                AlertDialog.Builder builder = new AlertDialog.Builder(MyReservationsActivity.this);
                                builder.setTitle(movieTitle);
                                builder.setMessage("Delete this reservation?");
                                builder.setCancelable(true);
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        deleteReservation(movieTitle, movieTime);
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                                return true;
                            }
                        });

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                            {
                                String movieTitle = ((TextView)view.findViewById(R.id.TEXTVIEW_MOVIETITLE)).getText().toString();
                                String movieTime = ((TextView)view.findViewById(R.id.TEXTVIEW_TIME)).getText().toString();
                                String id = ((TextView)view.findViewById(R.id.TEXTVIEW_MOVIETID)).getText().toString();
                                AlertDialog.Builder builder = new AlertDialog.Builder(MyReservationsActivity.this);
                                LayoutInflater factory = LayoutInflater.from(MyReservationsActivity.this);
                                final View view1 = factory.inflate(R.layout.qrscan, null);
                                builder.setView(view1);
                                builder.setTitle("Ticket");
                                StringBuilder sb = new StringBuilder();
                                sb.append("ID: " + id + "\n");
                                sb.append("TITLE: " + movieTitle + "\n");
                                sb.append("TIME: " + movieTime + "\n");
                                builder.setMessage(sb.toString());
                                builder.setCancelable(true);
                                AlertDialog alert = builder.create();
                                alert.show();
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
                    Toast.makeText(MyReservationsActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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

    public void deleteReservation(String movieN, String movieT)
    {
        try
        {
            RequestQueue requestQueue = Volley.newRequestQueue(MyReservationsActivity.this);
            String Url = "http://192.168.137.1:8080/SampleWebService/webresources/generic/deleteReservation";
            JSONObject params = new JSONObject();
            params.put("username", userName);
            params.put("moviename", movieN);
            params.put("time", movieT);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response)
                {
                    try
                    {
                        if(response.get("output").equals("Record Deleted"))
                        {
                            Toast.makeText(MyReservationsActivity.this, "Record Deleted", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(getIntent());
                        }
                        else if(response.get("output").equals("Something went wrong"))
                        {
                            Toast.makeText(MyReservationsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(MyReservationsActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
}
