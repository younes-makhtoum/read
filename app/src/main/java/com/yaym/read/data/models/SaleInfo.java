package com.yaym.read.data.models;

import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class SaleInfo {

    @SerializedName("country")
    @Expose
    String saleCountry;
    @SerializedName("saleability")
    @Expose
    String saleability;
    @SerializedName("isEbook")
    @Expose
    Boolean isEbook;

    /**
     * No args constructor for use in serialization
     */
    @Ignore
    public SaleInfo() {
    }

    /**
     * 
     * @param saleability
     * @param isEbook
     * @param saleCountry
     */
    public SaleInfo(String saleCountry, String saleability, Boolean isEbook) {
        super();
        this.saleCountry = saleCountry;
        this.saleability = saleability;
        this.isEbook = isEbook;
    }

    public String getSaleCountry() {
        return saleCountry;
    }

    public void setSaleCountry(String country) {
        this.saleCountry = country;
    }

    public String getSaleability() {
        return saleability;
    }

    public void setSaleability(String saleability) {
        this.saleability = saleability;
    }

    public Boolean getIsEbook() {
        return isEbook;
    }

    public void setIsEbook(Boolean isEbook) {
        this.isEbook = isEbook;
    }
}
