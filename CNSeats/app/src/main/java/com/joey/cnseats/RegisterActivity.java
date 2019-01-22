package com.joey.cnseats;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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

public class RegisterActivity extends AppCompatActivity
{

    ImageView imageView;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    EditText firstName,lastName,middleName,email,username,password,address;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE)
        {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        imageView = (ImageView)findViewById(R.id.IMAGEVIEW_SELECTOR);
        firstName = (EditText) findViewById(R.id.TEXTFIELD_FNAME);
        lastName = (EditText)findViewById(R.id.TEXTFIELD_LNAME);
        middleName = (EditText)findViewById(R.id.TEXTFIELD_MI);
        email = (EditText)findViewById(R.id.TEXTFIELD_EMAIL);
        username = (EditText)findViewById(R.id.TEXTFIELD_USERNAME);
        password = (EditText) findViewById(R.id.TEXTFIELD_REGISTERPASSWORD);
        address = (EditText)findViewById(R.id.TEXTFIELD_ADDRESS);

    }

    public void submitClicked(View view)
    {
        if(hasNullFields() == 0) {
            try {
                RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                String myURL = "http://192.168.137.1:8080/SampleWebService/webresources/generic/register";
                JSONObject myHashmap = new JSONObject();
                myHashmap.put("fname", firstName.getText().toString().trim());
                myHashmap.put("lname", lastName.getText().toString().trim());
                myHashmap.put("middleInitial", middleName.getText().toString().trim());
                myHashmap.put("emailAdd", email.getText().toString().trim());
                myHashmap.put("uname", username.getText().toString().trim());
                myHashmap.put("pword", password.getText().toString().trim());
                myHashmap.put("address", address.getText().toString().trim());


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(myURL, myHashmap, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(RegisterActivity.this, (String) response.get("output"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                int socketTimeout = 30000;
                RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                jsonObjectRequest.setRetryPolicy(retryPolicy);
                requestQueue.add(jsonObjectRequest);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }else
        {
            Toast.makeText(RegisterActivity.this, "Fill up empty fields", Toast.LENGTH_SHORT).show();
        }

    }


    public void chooserClicked(View view)
    {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);

    }

    public void clearBTNclicked(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to clear all the fields?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                clearallFields();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener()
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

    private void clearallFields()
    {
        ViewGroup group = (ViewGroup)findViewById(R.id.NAME_LAYOUT);
        ViewGroup group2 = (ViewGroup)findViewById(R.id.ROOT_PARENT);
        for (int i = 0, count = group.getChildCount(); i < count; ++i)
        {
            View view = group.getChildAt(i);
            if (view instanceof EditText)
            {
                ((EditText)view).getText().clear();
            }
        }

        for (int i = 0, count = group2.getChildCount(); i < count; ++i)
        {
            View view = group2.getChildAt(i);
            if (view instanceof EditText)
            {
                ((EditText)view).getText().clear();
            }
            else if(view instanceof CheckBox)
            {
                ((CheckBox)view).setChecked(false);
            }
        }
        firstName.requestFocus();
    }

    private int hasNullFields()
    {
        boolean hasNull = true;
        int numberOfNullFields=0;
        ViewGroup group = (ViewGroup)findViewById(R.id.NAME_LAYOUT);
        ViewGroup group2 = (ViewGroup)findViewById(R.id.ROOT_PARENT);
        for (int i = 0, count = group.getChildCount(); i < count; ++i)
        {
            View view = group.getChildAt(i);
            if (view instanceof EditText)
            {
                if(((EditText)view).getText().equals(""))
                {
                    numberOfNullFields++;
                }
            }
        }

        for (int i = 0, count = group2.getChildCount(); i < count; ++i)
        {
            View view = group2.getChildAt(i);
            if (view instanceof EditText)
            {
                if(((EditText)view).getText().equals(""))
                {
                    numberOfNullFields++;
                }
            }
            else if(view instanceof CheckBox)
            {
                if(((CheckBox)view).isChecked() == false)
                {
                    numberOfNullFields++;
                }
            }
        }
        return numberOfNullFields;
    }
}
