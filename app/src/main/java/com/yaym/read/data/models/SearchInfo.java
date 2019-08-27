package com.yaym.read.data.models;

import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class SearchInfo {

    @SerializedName("textSnippet")
    @Expose
    String textSnippet;

    /**
     * No args constructor for use in serialization
     */
    @Ignore
    public SearchInfo() {
    }

    /**
     * @param textSnippet
     */
    public SearchInfo(String textSnippet) {
        super();
        this.textSnippet = textSnippet;
    }

    public String getTextSnippet() {
        return textSnippet;
    }

    public void setTextSnippet(String textSnippet) {
        this.textSnippet = textSnippet;
    }
}
