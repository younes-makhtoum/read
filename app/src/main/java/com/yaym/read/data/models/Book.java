package com.yaym.read.data.models;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yaym.read.R;

import org.parceler.Parcel;

import com.yaym.read.core.tools.Utils;

@Parcel
@Entity(tableName = "book_table")
public class Book {

    @SerializedName("kind")
    @Expose
    String kind;
    @PrimaryKey
    @SerializedName("id")
    @Expose
    @NonNull
    String id;
    @SerializedName("etag")
    @Expose
    String etag;
    @SerializedName("selfLink")
    @Expose
    String selfLink;
    @SerializedName("volumeInfo")
    @Embedded
    @Expose
    VolumeInfo volumeInfo;
    @SerializedName("saleInfo")
    @Embedded
    @Expose
    SaleInfo saleInfo;
    @SerializedName("accessInfo")
    @Embedded
    @Expose
    AccessInfo accessInfo;
    @SerializedName("searchInfo")
    @Embedded
    @Expose
    SearchInfo searchInfo;

    /**
     * No args constructor for use in serialization
     * 
     */
    @Ignore
    public Book() {
    }

    /**
     * 
     * @param saleInfo
     * @param id
     * @param searchInfo
     * @param etag
     * @param volumeInfo
     * @param selfLink
     * @param accessInfo
     * @param kind
     */
    public Book(String kind, String id, String etag, String selfLink, VolumeInfo volumeInfo, SaleInfo saleInfo, AccessInfo accessInfo, SearchInfo searchInfo) {
        super();
        this.kind = kind;
        this.id = id;
        this.etag = etag;
        this.selfLink = selfLink;
        this.volumeInfo = volumeInfo;
        this.saleInfo = saleInfo;
        this.accessInfo = accessInfo;
        this.searchInfo = searchInfo;
    }

/*    @BindingAdapter("bookThumbnailUrl")
    public static void loadBookThumbnail(ImageView view, String thumbnailUrl) {
        // If a thumbnail URL is available for a given book, modify it to get a higher resolution version
        if (thumbnailUrl != null) {

            RequestOptions options = new RequestOptions()
                    .override(200, 600)
                    .placeholder(R.drawable.ic_book_placeholder)
                    .error(R.drawable.ic_book_placeholder);

            Glide.with(view.getContext())
                    .load(Utils.changeBookCoverThumbnail(thumbnailUrl))
                    .apply(options)
                    .into(view);
        }
    }*/

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    public SaleInfo getSaleInfo() {
        return saleInfo;
    }

    public void setSaleInfo(SaleInfo saleInfo) {
        this.saleInfo = saleInfo;
    }

    public AccessInfo getAccessInfo() {
        return accessInfo;
    }

    public void setAccessInfo(AccessInfo accessInfo) {
        this.accessInfo = accessInfo;
    }

    public SearchInfo getSearchInfo() {
        return searchInfo;
    }

    public void setSearchInfo(SearchInfo searchInfo) {
        this.searchInfo = searchInfo;
    }
}
