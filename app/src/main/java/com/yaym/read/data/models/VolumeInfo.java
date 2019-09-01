package com.yaym.read.data.models;

import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yaym.read.data.converters.IndustryIdListConverter;
import com.yaym.read.data.converters.StringsListConverter;

import org.parceler.Parcel;

@Parcel
public class VolumeInfo {

    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("subtitle")
    @Expose
    String subtitle;
    @SerializedName("authors")
    @TypeConverters(StringsListConverter.class)
    @Expose
    ArrayList<String> authors = null;
    @SerializedName("publisher")
    @Expose
    String publisher;
    @SerializedName("publishedDate")
    @Expose
    String publishedDate;
    @SerializedName("description")
    @Expose
    String description;
    @SerializedName("industryIdentifiers")
    @TypeConverters(IndustryIdListConverter.class)
    @Expose
    List<IndustryIdentifier> industryIdentifiers;
    @SerializedName("pageCount")
    @Expose
    Integer pageCount;
    @SerializedName("categories")
    @TypeConverters(StringsListConverter.class)
    @Expose
    ArrayList<String> categories = null;
    @SerializedName("averageRating")
    @Expose
    Double averageRating;
    @SerializedName("ratingsCount")
    @Expose
    Integer ratingsCount;
    @SerializedName("imageLinks")
    @Embedded
    @Expose
    ImageLinks imageLinks;
    @SerializedName("language")
    @Expose
    String language;
    @SerializedName("previewLink")
    @Expose
    String previewLink;
    @SerializedName("infoLink")
    @Expose
    String infoLink;
    @SerializedName("canonicalVolumeLink")
    @Expose
    String canonicalVolumeLink;

    /**
     * No args constructor for use in serialization
     */
    @Ignore
    public VolumeInfo() {
    }

    /**
     * @param pageCount
     * @param averageRating
     * @param infoLink
     * @param description
     * @param publisher
     * @param authors
     * @param canonicalVolumeLink
     * @param title
     * @param previewLink
     * @param ratingsCount
     * @param imageLinks
     * @param subtitle
     * @param categories
     * @param language
     * @param publishedDate
     * @param industryIdentifiers
     */
    public VolumeInfo(String title, String subtitle, ArrayList<String> authors, String publisher, String publishedDate, String description, List<IndustryIdentifier> industryIdentifiers, Integer pageCount, ArrayList<String> categories, Double averageRating, Integer ratingsCount, ImageLinks imageLinks, String language, String previewLink, String infoLink, String canonicalVolumeLink) {
        super();
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.industryIdentifiers = industryIdentifiers;
        this.pageCount = pageCount;
        this.categories = categories;
        this.averageRating = averageRating;
        this.ratingsCount = ratingsCount;
        this.imageLinks = imageLinks;
        this.language = language;
        this.previewLink = previewLink;
        this.infoLink = infoLink;
        this.canonicalVolumeLink = canonicalVolumeLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public List<IndustryIdentifier> getIndustryIdentifiers() {
        return industryIdentifiers;
    }

    public void setIndustryIdentifiers(List<IndustryIdentifier> industryIdentifiers) {
        this.industryIdentifiers = industryIdentifiers;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(Integer ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public ImageLinks getImageLinks() {
        if (imageLinks != null) {
            return imageLinks;
        } else {
            return new ImageLinks();
        }
    }

    public void setImageLinks(ImageLinks imageLinks) {
        this.imageLinks = imageLinks;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public String getInfoLink() {
        return infoLink;
    }

    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }

    public String getCanonicalVolumeLink() {
        return canonicalVolumeLink;
    }

    public void setCanonicalVolumeLink(String canonicalVolumeLink) {
        this.canonicalVolumeLink = canonicalVolumeLink;
    }
}
