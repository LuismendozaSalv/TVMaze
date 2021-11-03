package com.mendosal.tvmaze.retrofit.models.show;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScoreShow {

    @SerializedName("score")
    @Expose
    private float score;
    @SerializedName("show")
    @Expose
    private ShowEntity show;

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public ShowEntity getShow() {
        return show;
    }

    public void setShow(ShowEntity show) {
        this.show = show;
    }

}