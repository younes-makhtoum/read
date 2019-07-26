package com.yaym.read;

import java.util.ArrayList;

/**
 * {@link Book} represents a book dealing with the keyword searched by the user.
 * The details fetched from the Google Books API and displayed to the user are :
 * Title / Author(s) / Publication Year / TextSnippet (short description)
 */
public class Book {

    /** Title of the book */
    private String mTitle;

    /** Author of the book */
    private ArrayList<String> mAuthors;

    /** Publication year of the book */
    private String mYear;

    /** Description of the book */
    private String mTextSnippet;

    /** Information link for the book */
    private String mInfoLink;

    /** Thumbnail image of the book */
    private String mThumbnail;

    /**
     * Create a new book object with the following parameters
     *
     * @param Title is the title of the book
     * @param Authors is the list of authors of the book
     * @param Year is the year in which the book has been published
     * @param TextSnippet is a short description about the book
     * @param InfoLink is the link to an information web page about the book
     * @param Thumbnail is the link to the thumbnail image of the book
     */
    public Book(String Title, ArrayList<String> Authors, String Year, String TextSnippet, String InfoLink, String Thumbnail) {
        mTitle = Title;
        mAuthors = Authors;
        mYear = Year;
        mTextSnippet = TextSnippet;
        mInfoLink = InfoLink;
        mThumbnail = Thumbnail;
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


