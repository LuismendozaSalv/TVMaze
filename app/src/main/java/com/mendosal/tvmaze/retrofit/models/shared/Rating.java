package com.mendosal.tvmaze.retrofit.models.shared;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("average")
    @Expose
    private float average;

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

}
