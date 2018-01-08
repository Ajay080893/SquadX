package com.example.user.squadx.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.squadx.API.BitcoinRate;
import com.example.user.squadx.Model.Rate;
import com.example.user.squadx.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "https://api.coinmarketcap.com/";
    private static Retrofit retrofit = null;
    EditText txtname,txtrank,txtmarketpriceusd,txtpricebtc,txtpctg1,txtpctg2,txtptcg3,txtvolume,txtmarketprice,txtasupply,txttsupply;
    ImageView btnadd,btnhistory,btngraph;
 String txtprice;
    Context context;
 ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtname=(EditText) findViewById(R.id.txtname);
        txtrank=(EditText) findViewById(R.id.edtrank);
        txtmarketpriceusd=(EditText) findViewById(R.id.txtpriceusd);
        txtpricebtc=(EditText) findViewById(R.id.txtpricebtc);
        txtpctg1=(EditText) findViewById(R.id.pctgoneh);
        txtpctg2=(EditText) findViewById(R.id.pctgsecnd);
        txtptcg3=(EditText) findViewById(R.id.pctgd);
        txtvolume=(EditText) findViewById(R.id.txtvoulume);
        txtmarketprice=(EditText) findViewById(R.id.txtmarketprice);
        txtasupply=(EditText) findViewById(R.id.txtavailsupply);
        txttsupply=(EditText) findViewById(R.id.totalsupply);
        btnadd=(ImageView) findViewById(R.id.btnadd);
        btnhistory=(ImageView) findViewById(R.id.btnhistory);
        btngraph=(ImageView) findViewById(R.id.btngraph);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Investment.class);
                Bundle bundle = new Bundle();
                //Add your data from getFactualResults method to bundle
                bundle.putString("price", txtmarketpriceusd.getText().toString());
                //Add the bundle to the intent
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btnhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryListActivity
                        .class);
                startActivity(intent);
            }
        });
        btngraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Graph.class);
                startActivity(intent);
            }
        });

        connectAndGetApiData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add) {
            Intent intent = new Intent(MainActivity.this, Investment.class);
            Bundle bundle = new Bundle();
            //Add your data from getFactualResults method to bundle
            bundle.putString("price", txtmarketpriceusd.getText().toString());
            //Add the bundle to the intent
            intent.putExtras(bundle);
            startActivity(intent);

        }

        else if(id==R.id.graph){

            Intent intent = new Intent(MainActivity.this, Graph
                    .class);
            startActivity(intent);

        }
        else{
            Intent intent = new Intent(MainActivity.this, HistoryListActivity
                    .class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    public void connectAndGetApiData(){

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        BitcoinRate bitcoinRate = retrofit.create(BitcoinRate.class);

        Call<List<Rate>> call = bitcoinRate.getrateDetails();
        call.enqueue(new Callback<List<Rate>>() {
            @Override
            public void onResponse(Call<List<Rate>> call, Response<List<Rate>> response) {
                List<Rate> rate = response.body();
                for (int i = 0; i < rate.size(); i++) {
                    txtname.setText(rate.get(i).getName());
                    txtrank.setText(rate.get(i).getRank());
                    txtvolume.setText(rate.get(i).getVoulume_usd());
                    txtasupply.setText(rate.get(i).getAvailable_supply());
                    txttsupply.setText(rate.get(i).getTotal_supply());
                    txtmarketprice.setText(rate.get(i).getMarket_cap_usd());
                    txtmarketpriceusd.setText(rate.get(i).getPrice_usd());
                    txtpctg1.setText(rate.get(i).getPercent_change_1h());
                    txtpctg2.setText(rate.get(i).getPercent_change_24h());
                    txtptcg3.setText(rate.get(i).getPercent_change_7d());
                    txtpricebtc.setText(rate.get(i).getPrice_btc());


                }





            }

            @Override
            public void onFailure(Call<List<Rate>> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }





}
