package com.yaym.read.data.models;

import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GoogleBooksResponse {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("totalItems")
    @Expose
    private Integer totalItems;
    @SerializedName("items")
    @Expose
    private List<Book> books = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    @Ignore
    public GoogleBooksResponse() {
    }

    /**
     * 
     * @param books
     * @param totalItems
     * @param kind
     */
    public GoogleBooksResponse(String kind, Integer totalItems, List<Book> books) {
        super();
        this.kind = kind;
        this.totalItems = totalItems;
        this.books = books;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

}
