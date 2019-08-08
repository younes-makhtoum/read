package com.yaym.read;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

/**
 * {@link Book} represents a book dealing with the keyword searched by the user.
 * The details fetched from the Google Books API and displayed to the user are :
 * Title / Author(s) / Publication Year / TextSnippet (short description)
 */
public class Book {

    /** Title of the book */
    private final String mId;

    /** Title of the book */
    private final String mTitle;

    /** Author of the book */
    private final ArrayList<String> mAuthors;

    /** Publication year of the book */
    private final String mYear;

    /** Description of the book */
    private final String mTextSnippet;

    /** Information link for the book */
    private final String mInfoLink;

    /** Thumbnail image of the book */
    private final String mThumbnail;

    /**
     * Create a new book object with the following parameters
     *
     * @param id is the id of the book
     * @param title is the title of the book
     * @param authors is the list of authors of the book
     * @param year is the year in which the book has been published
     * @param textSnippet is a short description about the book
     * @param infoLink is the link to an information web page about the book
     * @param thumbnail is the link to the thumbnail image of the book
     */
    public Book(String id, String title, ArrayList<String> authors, String year, String textSnippet, String infoLink, String thumbnail) {
        mId = id;
        mTitle = title;
        mAuthors = authors;
        mYear = year;
        mTextSnippet = textSnippet;
        mInfoLink = infoLink;
        mThumbnail = thumbnail;
    }

    @BindingAdapter("bookThumbnailUrl")
    public static void loadBookThumbnail(ImageView view, String thumbnailUrl) {
        Glide.with(view.getContext())
                .load(thumbnailUrl)
                .apply(new RequestOptions().override(200, 600))
                .into(view);
    }

    /**
     * Get the id of the book
     */
    public String getId() {
        return mId;
    }

    /**
     * Get the title of the book
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Get the list of authors of the book
     */
    public ArrayList<String> getAuthors() {
        return mAuthors;
    }

    /**
     * Get the published year of the book
     */
    public String getYear() {
        return mYear;
    }

    /**
     * Get the short description about the book
     */
    public String getTextSnippet() { return mTextSnippet; }

    /**
     * Get the URL of the book's information web page
     */
    public String getInfoLink() {
        return mInfoLink;
    }

    /**
     * Get the URL of the book's thumbnail image
     */
    public String getThumbnail() {
        return mThumbnail;
    }
}