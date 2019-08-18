package com.yaym.read.data.models;

import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pdf {

    @SerializedName("isAvailable")
    @Expose
    private Boolean pdfIsAvailable;

    /**
     * No args constructor for use in serialization
     * 
     */
    @Ignore
    public Pdf() {
    }

    /**
     * 
     * @param pdfIsAvailable
     */
    public Pdf(Boolean pdfIsAvailable) {
        super();
        this.pdfIsAvailable = pdfIsAvailable;
    }

    public Boolean getPdfIsAvailable() {
        return pdfIsAvailable;
    }

    public void setPdfIsAvailable(Boolean pdfIsAvailable) {
        this.pdfIsAvailable = pdfIsAvailable;
    }

}
