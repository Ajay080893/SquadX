package com.example.user.squadx.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 1/5/2018.
 */

public class Graphdata {
    @SerializedName("status")
    private String status;
    @SerializedName("name")
    private String name;
    @SerializedName("unit")
    private String unit;
    @SerializedName("period")
    private String period;
    @SerializedName("description")
    private String description;
   public List<Graphpoint> values;
    public Graphdata(String status, String name, String unit, String period, String description) {
        this.status = status;
        this.name = name;
        this.unit = unit;
        this.period = period;
        this.description = description;

    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public List<Graphpoint> getValues() {
        return values;
    }

    public void setValues(List<Graphpoint> values) {
        this.values = values;
    }


}
