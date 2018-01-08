package com.example.user.squadx.Activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.user.squadx.Adapter.SQLiteListAdapter;
import com.example.user.squadx.Model.DBHelper;
import com.example.user.squadx.Model.SQLiteHelper;
import com.example.user.squadx.R;

import java.util.ArrayList;

public class HistoryListActivity extends AppCompatActivity {
    DBHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    SQLiteListAdapter ListAdapter ;

    ArrayList<String> ID_ArrayList = new ArrayList<String>();
    ArrayList<String> DATE_ArrayList = new ArrayList<String>();
    ArrayList<String> TIME_ArrayList = new ArrayList<String>();
    ArrayList<String> QUANTITY_ArrayList = new ArrayList<String>();
    ArrayList<String> INVESTTYPE_ArrayList = new ArrayList<String>();
    ArrayList<String> TOTALVALUE_ArrayList = new ArrayList<String>();
    ListView LISTVIEW;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);

        LISTVIEW = (ListView) findViewById(R.id.listView1);

        SQLITEHELPER = new DBHelper(this);

    }

    @Override
    protected void onResume() {

        ShowSQLiteDBdata() ;

        super.onResume();
    }

    private void ShowSQLiteDBdata() {

      SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();

        cursor = SQLITEDATABASE.rawQuery("SELECT * FROM demoTable", null);

        ID_ArrayList.clear();
        DATE_ArrayList.clear();
        TIME_ArrayList.clear();
        QUANTITY_ArrayList.clear();
        INVESTTYPE_ArrayList.clear();
        TOTALVALUE_ArrayList.clear();

        if (cursor.moveToFirst()) {
            do {
                ID_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_ID)));

                  DATE_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_DATE)));

                TIME_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_TIME)));

                QUANTITY_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_QUANTITY)));
                INVESTTYPE_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_INVESTTYPE)));
                TOTALVALUE_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_TOTALVALUE)));

            } while (cursor.moveToNext());
        }

        ListAdapter = new SQLiteListAdapter(HistoryListActivity.this,

                ID_ArrayList,
                DATE_ArrayList,
                TIME_ArrayList,
                QUANTITY_ArrayList,
                INVESTTYPE_ArrayList,
                TOTALVALUE_ArrayList


        );

        LISTVIEW.setAdapter(ListAdapter);

        cursor.close();
    }
}