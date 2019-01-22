package com.example.joey11.listviewwithimages;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    List<DataItem> lstData;
    String detail = "Lorem ipsum dolor sit amet, est et prima impetus, facer labore persius sit in. An quo solet verear gubergren, periculis abhorreant ne sea. Vivendo necessitatibus vel et, sea no nemore minimum definitionem. Sea primis platonem ex, duo ridens appellantur at, an purto utinam ius. Est ex ubique meliore ceteros. Ut impedit dolorem adipiscing usu.";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstData = new ArrayList<>();
        lstData.add(new DataItem(R.drawable.contact,"Profile",detail,0));
        lstData.add(new DataItem(R.drawable.icecream,"Ice Cream",detail,1));
        lstData.add(new DataItem(R.drawable.location,"Location",detail,1));
        lstData.add(new DataItem(R.drawable.telephone,"Telephone",detail,0));
        ListView listView = (ListView)findViewById(R.id.listView);
        CustomAdapter adapter = new CustomAdapter(MainActivity.this,R.layout.itemrow,lstData);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(detail);
                builder.setCancelable(true);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
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
        });
    }
}
