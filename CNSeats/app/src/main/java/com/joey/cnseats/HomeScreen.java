package com.joey.cnseats;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<String>buttons;
    ImageButton button;
    String id,userN,selectedMovie, selectedMovieTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        buttons = new ArrayList<>();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences sharedPreferences = getSharedPreferences("userid", Context.MODE_PRIVATE);
        Resources res = getResources();
        id = String.format(res.getString(R.string.users_id),sharedPreferences.getString("id",""));
        userN = sharedPreferences.getString("username","");
        Bundle bundle = getIntent().getExtras();
        selectedMovie = bundle.getString("selectedmovie");
        selectedMovieTime = bundle.getString("playtime");
        try
        {
            RequestQueue requestQueue = Volley.newRequestQueue(HomeScreen.this);
            String myURL = "http://192.168.137.1:8080/SampleWebService/webresources/generic/getSeats";
            JSONObject data = new JSONObject();
            data.put("movieName", selectedMovie);
            data.put("playTime", selectedMovieTime);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(myURL, data, new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response)
                {
                    try
                    {
                        StringBuilder sb = new StringBuilder();
                        JSONArray ja = response.getJSONArray("seatnumber");

                        for(int i = 0 ; i < ja.length(); i++)
                        {
                            sb.append(ja.getString(i) + " ");
                            int id = getResources().getIdentifier(ja.getString(i) , "id", getPackageName());
                            ImageButton but = (ImageButton) findViewById(id);
                            but.setBackgroundResource(R.drawable.seats_reserved);
                        }
                    }catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(HomeScreen.this, error.toString(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.choice_nowshowing)
        {
            startActivity(new Intent(HomeScreen.this,PastMoviesActivity.class));
        } else if (id == R.id.choice_upcoming)
        {
            startActivity(new Intent(HomeScreen.this,MyReservationsActivity.class));
        }else if(id == R.id.choice_myTickets)
        {
            startActivity(new Intent(HomeScreen.this,AvailableMoviesActivity.class));
        }else if (id == R.id.choice_logout)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreen.this);
            builder.setTitle("Confirm");
            builder.setMessage("Are you sure want to logout?");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(HomeScreen.this, LoginActivity.class));
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void reserve(View view)
    {
        if(view.getBackground().getConstantState().equals(HomeScreen.this.getResources().getDrawable(R.drawable.seats_available).getConstantState()))
        {
            buttons.add(getResources().getResourceEntryName(view.getId()));
            view.setBackgroundResource(R.drawable.seats_occupied);
        }
        else if(view.getBackground().getConstantState().equals(HomeScreen.this.getResources().getDrawable(R.drawable.seats_reserved).getConstantState()))
        {
            Toast.makeText(HomeScreen.this, "This seat is already occupied", Toast.LENGTH_SHORT).show();
        }
        else if(view.getBackground().getConstantState().equals(HomeScreen.this.getResources().getDrawable(R.drawable.seats_occupied).getConstantState()))
        {
            buttons.remove(getResources().getResourceEntryName(view.getId()));
            view.setBackgroundResource(R.drawable.seats_available);
        }

    }

    public void reserveButtonClicked(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreen.this);
        builder.setTitle("Confirm");
        builder.setMessage("The selected seats will be reserved");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                reserveConfirmed();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void reserveConfirmed()
    {
        try
        {

            RequestQueue myRequestQueue = Volley.newRequestQueue(HomeScreen.this);
            String Url = "http://192.168.137.1:8080/SampleWebService/webresources/generic/reservation";
            JSONArray arr = new JSONArray(buttons);
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("uname", userN);
            jsonObj.put("seats",arr);
            jsonObj.put("moviename", selectedMovie);
            jsonObj.put("playtime", selectedMovieTime);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Url, jsonObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response)
                {
                    try
                    {
                        if(response.get("output").equals("Reserved"))
                        {
                            Toast.makeText(HomeScreen.this, "Success", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(getIntent());

                        }
                        else
                        {
                            Toast.makeText(HomeScreen.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(HomeScreen.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            int socketTimeout = 30000;
            RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjectRequest.setRetryPolicy(retryPolicy);
            myRequestQueue.add(jsonObjectRequest);


        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
