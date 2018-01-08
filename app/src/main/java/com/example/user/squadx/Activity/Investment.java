package com.example.user.squadx.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.squadx.Model.DBHelper;
import com.example.user.squadx.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Investment extends AppCompatActivity {

    DBHelper SQLITEHELPER;
        EditText edtdate,edttime,edtquantity ;
        TextView txtcurrentprice,txttradprice,txttotalprice;
        Button Show,Submit;
        int intquant=0;

        SQLiteDatabase SQLITEDATABASE;
        String Date, Time, InvestType="BUY",Quantity,TotalValue ;
        Boolean CheckEditTextEmpty ;
        String SQLiteQuery,txtprice,txtquant,strtotal ;
        SimpleDateFormat dateFormat;
        RadioGroup documenttype;
        RadioButton radiobuy,radiosell;
        SharedPreferences sharedPreference;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_investment);
            sharedPreference =getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
            SQLITEHELPER = new DBHelper(this);
            edtdate = (EditText)findViewById(R.id.date);
            dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            edtdate.setText(dateFormat.format(new Date(System.currentTimeMillis())));
            edttime = (EditText)findViewById(R.id.time);
            edttime.setText(GetTime());
            edtquantity = (EditText)findViewById(R.id.edtquantity);
            documenttype = (RadioGroup)findViewById(R.id.first_group);
            radiobuy = (RadioButton) findViewById(R.id.btnbuy);
            radiosell = (RadioButton) findViewById(R.id.btnsell);
            Submit = (Button)findViewById(R.id.btnbookingsave);
            Show = (Button)findViewById(R.id.btnshow);
            txtcurrentprice=(TextView) findViewById(R.id.txtcurrent);
            txttradprice=(TextView) findViewById(R.id.txttrade);
            txttotalprice=(TextView) findViewById(R.id.txttotalvalue);

            Bundle bundle = getIntent().getExtras();

            //Extract the data…
            txtprice = bundle.getString("price");
           // txtprice=sharedPreference.getString("price","").toString().trim();
            txtcurrentprice.setText("$"+txtprice);
            txttradprice.setText(txtprice);

            edtquantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(edtquantity.getText().toString().equals("")){
                      edtquantity.setText("0");
                    }
                    txtquant=edtquantity.getText().toString();

                    intquant=Integer.parseInt(txtquant);
                    float price=Float.parseFloat(txtprice);
                    float total=intquant*price;
                    strtotal=String.valueOf(total);
                    txttotalprice.setText(strtotal);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            documenttype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {


                    if (checkedId == R.id.btnbuy) {
                        InvestType = "BUY";
                    }
                    else {
                        InvestType = "SELL";

                    }
                }
            });
            Submit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    DBCreate();

                    SubmitData2SQLiteDB();

                }
            });

            Show.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    Intent intent = new Intent(Investment.this, HistoryListActivity.class);
                    startActivity(intent);

                }
            });
        }
    private  String GetTime()
    {
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);
        int hour=cal.get(Calendar.HOUR_OF_DAY);
        int min=cal.get(Calendar.MINUTE);
        String curTimemin = String.format("%02d", min);
        return  ( hour + ":" + curTimemin);
    }
    public void DBCreate(){

        SQLITEDATABASE = openOrCreateDatabase("DemoDataBase", Context.MODE_PRIVATE, null);

        SQLITEDATABASE.execSQL("CREATE TABLE IF NOT EXISTS demoTable(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, date,time VARCHAR, quantity VARCHAR,investtype VARCHAR, totalvalue VARCHAR);");
       // SQLITEDATABASE.execSQL("CREATE TABLE IF NOT EXISTS demoTable(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR, phone_number VARCHAR, subject VARCHAR);");
    }

    public void SubmitData2SQLiteDB(){

        Date = edtdate.getText().toString();

        Time = edttime.getText().toString();

        Quantity = edtquantity.getText().toString();
        TotalValue=txttotalprice.getText().toString();

        CheckEditTextIsEmptyOrNot( Quantity);

        if(CheckEditTextEmpty == true)
        {
           // SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();
            SQLiteQuery = "INSERT INTO demoTable (date,time,quantity,investtype,totalvalue) VALUES('"+Date+"', '"+Time+"', '"+Quantity+"','"+InvestType+"','"+TotalValue+"');";
          //  SQLiteQuery = "INSERT INTO demoTable (name,phone_number,subject) VALUES('"+Name+"', '"+PhoneNumber+"', '"+Subject+"');";
            SQLITEDATABASE.execSQL(SQLiteQuery);

            Toast.makeText(Investment.this,"Data Submit Successfully", Toast.LENGTH_LONG).show();

            ClearEditTextAfterDoneTask();

        }
        else {

            Toast.makeText(Investment.this,"Please Fill All the Fields", Toast.LENGTH_LONG).show();
        }
    }

    public void CheckEditTextIsEmptyOrNot(String Quantity){

        if(TextUtils.isEmpty(Quantity) ){

            CheckEditTextEmpty = false ;

        }
        else {
            CheckEditTextEmpty = true ;
        }
    }

    public void ClearEditTextAfterDoneTask(){

        edtquantity.getText().clear();



    }

}