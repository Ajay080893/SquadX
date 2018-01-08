package com.example.user.squadx.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.example.user.squadx.API.BitcoinRate;
import com.example.user.squadx.Model.Graphdata;
import com.example.user.squadx.Model.Graphpoint;
import com.example.user.squadx.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Graph extends AppCompatActivity implements OnChartGestureListener,
        OnChartValueSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "https://api.blockchain.info/";
    private static Retrofit retrofit = null;
    private LineChart mChart;
    int referenceTimestamp=1504742400;
    ArrayList<Entry> yVals;
    String xval;
    int x;
    ArrayList<String> xVals ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // To make full screen layout
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_graph);
        xVals = new ArrayList<String>();
        yVals = new ArrayList<Entry>();
        mChart = (LineChart) findViewById(R.id.barchart);
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);


        // add data
      //  setData();

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);

        // no description text
      /*  mChart.setDescription("Demo Line Chart");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);


        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines


        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);*/

        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);

        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

        //  dont forget to refresh the drawing
      //  mChart.invalidate();
        connectAndGetApiData();

    }
    public void connectAndGetApiData(){

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        BitcoinRate apiGraph = retrofit.create(BitcoinRate.class);

        Call<Graphdata> call = apiGraph.getgraphDetails();
        call.enqueue(new Callback<Graphdata>() {
            @Override
            public void onResponse(Call<Graphdata> call, Response<Graphdata> response) {
                List<Graphpoint> values = response.body().getValues();
                for(int i=0;i<values.size();i++){
                    x=values.get(i).getX();
                    long unixSeconds = x;


// convert seconds to milliseconds
                    Date date = new Date(unixSeconds*1000L);
// the format of your date
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
// give a timezone reference for formatting (see comment at the bottom)
                    sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
                    String formattedDate = sdf.format(date);
                    /*xval=String.valueOf(x);
                    System.out.println(formattedDate);*/
                    xVals.add(formattedDate);
                    float y=values.get(i).getY();

                    yVals.add(new Entry(y, i));

                }


                    LineDataSet set1,set2;

                    // create a dataset and give it a type
                    set1 = new LineDataSet(yVals, "DataSet 1");


                    // set1.setFillColor(Color.RED);

                    // set the line to be drawn like this "- - - - - -"
                    //   set1.enableDashedLine(10f, 5f, 0f);
                    // set1.enableDashedHighlightLine(10f, 5f, 0f);
                    set1.setColor(Color.BLACK);
                    set1.setCircleColor(Color.BLACK);
                    set1.setLineWidth(1f);
                    set1.setCircleRadius(3f);
                    set1.setDrawCircleHole(false);
                    set1.setValueTextSize(9f);
                    set1.setDrawFilled(true);

                    ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                    dataSets.add(set1); // add the datasets

                    // create a data object with the datasets
                    LineData data = new LineData(xVals,dataSets);

                    // set data
                    mChart.setData(data);
                  /*  MyMarkerView myMarkerView= new MyMarkerView(getApplicationContext(), R.layout.custom_marker_view, referenceTimestamp);
                    mChart.setMarkerView(myMarkerView);
                    AxisValueFormatter xAxisFormatter = new HourAxisValueformatter(referenceTimestamp);*/
                  /*  XAxis xAxis = mChart.getXAxis();
                    xAxis.setValueFormatter(xAxisFormatter);*/



              /*  List<Graphpoint> rate = response.body();
                List<Graphdata> rate1 = response.body();*/
               /* for (int i = 0; i < rate.size(); i++) {


                }*/





            }

            @Override
            public void onFailure(Call<Graphdata> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }
    private ArrayList<String> setXAxisValues(){
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("10");
        xVals.add("20");
        xVals.add("30");
        xVals.add("30.5");
        xVals.add("40");

        return xVals;
    }

    private ArrayList<Entry> setYAxisValues(){
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        yVals.add(new Entry(60, 0));
        yVals.add(new Entry(48, 1));
        yVals.add(new Entry(70.5f, 2));
        yVals.add(new Entry(100, 3));
        yVals.add(new Entry(180.9f, 4));

        return yVals;
    }

    private void setData() {
        ArrayList<String> xVals = setXAxisValues();

        ArrayList<Entry> yVals = setYAxisValues();

        LineDataSet set1,set2;

        // create a dataset and give it a type
        set1 = new LineDataSet(yVals, "DataSet 1");

        set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        //   set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals,dataSets);

        // set data
        mChart.setData(data);
       /* MyMarkerView myMarkerView= new MyMarkerView(getApplicationContext(), R.layout.custom_marker_view, referenceTimestamp);
        mChart.setMarkerView(myMarkerView);
        AxisValueFormatter xAxisFormatter = new HourAxisValueformatter(referenceTimestamp);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setValueFormatter(xAxisFormatter);
*/
    }


    @Override
    public void onChartGestureStart(MotionEvent me,
                                    ChartTouchListener.ChartGesture

                                            lastPerformedGesture) {

        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me,
                                  ChartTouchListener.ChartGesture
                                          lastPerformedGesture) {

        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if(lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            // or highlightTouch(null) for callback to onNothingSelected(...)
            mChart.highlightValues(null);
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2,
                             float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: "
                + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

  /*  @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + mChart.getLowestVisibleXIndex()
                + ", high: " + mChart.getHighestVisibleXIndex());

        Log.i("MIN MAX", "xmin: " + mChart.getXChartMin()
                + ", xmax: " + mChart.getXChartMax()
                + ", ymin: " + mChart.getYChartMin()
                + ", ymax: " + mChart.getYChartMax());
    }*/

   /* @Override
    public void onValueSelected(Entry e, Highlight h) {
        mChart.getXAxis().getValueFormatter().getFormattedValue(e.getX(), mChart.getXAxis());
    }
*/
    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }
}
