package com.example.user.squadx.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 1/6/2018.
 */

public class SQLiteHelper  extends SQLiteOpenHelper {

    static String DATABASE_NAME="DemoDataBase";

    public static final String KEY_ID="id";

    public static final String TABLE_NAME="demoTable";

    public static final String KEY_DATE="date";

    public static final String KEY_TIME="time";

    public static final String KEY_QUANTITY="quantity";
    public static final String KEY_INVESTTYPE="investtype";
    public static final String KEY_TOTALVALUE="totalvalue";

    public SQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY, "+KEY_TIME+" VARCHAR, "+KEY_QUANTITY+" VARCHAR, "+KEY_INVESTTYPE+" VARCHAR, "+KEY_TOTALVALUE+" VARCHAR)";
        database.execSQL(CREATE_TABLE);
       /* String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY, "+KEY_Name+" VARCHAR, "+KEY_PhoneNumber+" VARCHAR, "+KEY_Subject+" VARCHAR)";
        database.execSQL(CREATE_TABLE);*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

}
