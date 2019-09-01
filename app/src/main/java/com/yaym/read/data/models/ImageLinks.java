package com.yaym.read.data.models;

import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class ImageLinks {

    @SerializedName("smallThumbnail")
    @Expose
    String smallThumbnail;
    @SerializedName("thumbnail")
    @Expose
    String thumbnail;

    /**
     * No args constructor for use in serialization
     */
    @Ignore
    public ImageLinks() {
    }

    /**
     * 
     * @param thumbnail
     * @param smallThumbnail
     */
    public ImageLinks(String smallThumbnail, String thumbnail) {
        super();
        this.smallThumbnail = smallThumbnail;
        this.thumbnail = thumbnail;
    }

    public String getSmallThumbnail() {
        if (smallThumbnail != null) {
            return smallThumbnail;
        } else {
            return "";
        }
    }

    public void setSmallThumbnail(String smallThumbnail) {
        this.smallThumbnail = smallThumbnail;
    }

    public String getThumbnail() {
        if (thumbnail != null) {
            return thumbnail;
        } else {
            return "";
        }
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
