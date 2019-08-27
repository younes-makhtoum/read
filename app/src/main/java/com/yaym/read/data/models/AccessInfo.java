package com.yaym.read.data.models;

import androidx.room.Ignore;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class AccessInfo {

    @SerializedName("country")
    @Expose
    String accessCountry;
    @SerializedName("viewability")
    @Expose
    String viewability;
    @SerializedName("webReaderLink")
    @Expose
    String webReaderLink;

    /**
     * No args constructor for use in serialization
     */
    @Ignore
    public AccessInfo() {
    }

    /**
     * @param webReaderLink
     * @param viewability
     * @param accessCountry
     */
    public AccessInfo(String accessCountry, String viewability, String webReaderLink) {
        super();
        this.accessCountry = accessCountry;
        this.viewability = viewability;
        this.webReaderLink = webReaderLink;
    }

    public String getAccessCountry() {
        return accessCountry;
    }

    public void setAccessCountry(String accessCountry) {
        this.accessCountry = accessCountry;
    }

    public String getViewability() {
        return viewability;
    }

    public void setViewability(String viewability) {
        this.viewability = viewability;
    }

    public String getWebReaderLink() {
        return webReaderLink;
    }

    public void setWebReaderLink(String webReaderLink) {
        this.webReaderLink = webReaderLink;
    }
}
