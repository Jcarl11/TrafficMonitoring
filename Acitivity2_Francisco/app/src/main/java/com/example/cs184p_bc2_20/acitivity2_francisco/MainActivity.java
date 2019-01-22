package com.example.cs184p_bc2_20.acitivity2_francisco;

import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    Database database;
    EditText USERNAME, PWORD,repass;
    Button signInButton;
    String gender;
    RadioGroup rg;
    CheckBox checkB;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new Database(this);
        rg = (RadioGroup)findViewById(R.id.rg1);
        USERNAME = (EditText)findViewById(R.id.TEXTFIELD_USERNAME);
        PWORD = (EditText)findViewById(R.id.TEXTFIELD_PASSWORD);
        repass = (EditText) findViewById(R.id.TEXTFIELD_REPASSWORD);
        signInButton = (Button) findViewById(R.id.BUTTON_SUBMIT);
        checkB = (CheckBox) findViewById(R.id.CHECKBOX_AGREEMENT);
        signInButton.setBackgroundColor(Color.parseColor("#77d5f4"));
        signInButton.setClickable(false);
        addData();
    }

    public void addData()
    {

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkB.isChecked())
                {
                    if(PWORD.getText().toString().trim().equals(repass.getText().toString().trim()))
                    {
                        gender = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                        boolean result = database.insertInfo(USERNAME.getText().toString(), PWORD.getText().toString(),gender);
                        if(result = true)
                        {
                            Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Insertion Failed",Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Password does not match!", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Fill the necessary fields", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void licenseAgreementTicked(View v)
    {
        CheckBox checkbox = (CheckBox) v;
        if(checkbox.isChecked())
        {
            signInButton.setBackgroundColor(Color.parseColor("#678bf7"));
            signInButton.setEnabled(true);
        }
        else
        {
            signInButton.setBackgroundColor(Color.parseColor("#77d5f4"));
            signInButton.setEnabled(false);
        }
    }
}
