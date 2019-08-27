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

    @BindingAdapter("bookThumbnailUrl")
    public static void loadBookThumbnail(ImageView view, String thumbnailUrl) {
        // If a thumbnail URL is available for a given book, modify it to get a higher resolution version
        if (thumbnailUrl != null) {
            thumbnailUrl = thumbnailUrl.replace("zoom=1", "zoom=2");
        }
        Glide.with(view.getContext())
                .load(thumbnailUrl)
                .apply(new RequestOptions().override(200, 600))
                .placeholder(R.drawable.ic_book_placeholder)
                .into(view);
    }

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

    /*// Write object values to parcel for storage
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(kind);
        dest.writeString(id);
        dest.writeString(etag);
        dest.writeString(selfLink);
        dest.writeParcelable(volumeInfo, flags);
        dest.writeParcelable(saleInfo, flags);
        dest.writeParcelable(accessInfo, flags);
        dest.writeParcelable(searchInfo, flags);
    }
    // Constructor used for parcel
    protected Book(Parcel parcel){
        kind = parcel.readString();
        id = Objects.requireNonNull(parcel.readString());
        etag = parcel.readString();
        selfLink = parcel.readString();
        volumeInfo = parcel.readParcelable(VolumeInfo.class.getClassLoader());
        saleInfo = parcel.readParcelable(SaleInfo.class.getClassLoader());
        accessInfo = parcel.readParcelable(AccessInfo.class.getClassLoader());
        searchInfo = parcel.readParcelable(SearchInfo.class.getClassLoader());
    }
    // Creator - used when un-parceling our parcel (creating a Book object)
    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>(){
        @Override
        public Book createFromParcel(Parcel parcel) {
            return new Book(parcel);
        }
        @Override
        public Book[] newArray(int size) {
            return new Book[0];
        }
    };
    //return hashcode of object
    public int describeContents() {
        return hashCode();
    }*/
}
