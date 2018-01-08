package com.example.user.squadx.API;

import com.example.user.squadx.Model.Graphdata;
import com.example.user.squadx.Model.Rate;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by user on 1/4/2018.
 */

public interface BitcoinRate
{
    @GET("v1/ticker/bitcoin/")
    Call<List<Rate>> getrateDetails();
    @GET("charts/market-price?timespan=4months&format=json&cors=true&includeLastPoint=true")
    Call<Graphdata> getgraphDetails();
}
