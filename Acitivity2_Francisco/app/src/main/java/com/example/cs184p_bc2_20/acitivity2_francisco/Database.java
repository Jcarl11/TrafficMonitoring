package com.example.cs184p_bc2_20.acitivity2_francisco;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 7/21/2017.
 */

public class Database extends SQLiteOpenHelper{
    private static final String DB_NAME = "ACCOUNTS.db";
    private static final String TABLE_NAME = "ACCOUNTS_TABLE";
    private static final String COL_1 = "USERNAME";
    private static final String COL_2 = "PASSWORD";
    private static final String COL_3 = "GENDER";


    public Database(Context context) {
        super(context, DB_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+ TABLE_NAME +" ("+COL_1+" VARCHAR(50), "+COL_2+" VARCHAR(50), "+COL_3+" VARCHAR(8))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertInfo(String username, String password, String gender)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, username);
        contentValues.put(COL_2, password);
        contentValues.put(COL_3, gender);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
