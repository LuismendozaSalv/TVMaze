package com.mendosal.tvmaze.retrofit.models.shared;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mendosal.tvmaze.retrofit.models.show.Previousepisode;

public class Links {

    @SerializedName("self")
    @Expose
    private Self self;
    @SerializedName("previousepisode")
    @Expose
    private Previousepisode previousepisode;

    public Self getSelf() {
        return self;
    }

    public void setSelf(Self self) {
        this.self = self;
    }

    public Previousepisode getPreviousepisode() {
        return previousepisode;
    }

    public void setPreviousepisode(Previousepisode previousepisode) {
        this.previousepisode = previousepisode;
    }

}
