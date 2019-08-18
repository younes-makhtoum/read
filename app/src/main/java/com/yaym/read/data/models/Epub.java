package com.yaym.read.data.models;

import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Epub {

    @SerializedName("isAvailable")
    @Expose
    private Boolean ePubIsAvailable;

    /**
     * No args constructor for use in serialization
     * 
     */
    @Ignore
    public Epub() {
    }

    /**
     * 
     * @param ePubIsAvailable
     */
    public Epub(Boolean ePubIsAvailable) {
        super();
        this.ePubIsAvailable = ePubIsAvailable;
    }

    public Boolean getEPubIsAvailable() {
        return ePubIsAvailable;
    }

    public void setEPubIsAvailable(Boolean ePubIsAvailable) {
        this.ePubIsAvailable = ePubIsAvailable;
    }

}