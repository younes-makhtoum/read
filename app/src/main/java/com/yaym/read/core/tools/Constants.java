package com.yaym.read.core.tools;

public class Constants {

    /**
     * URL element variables for book data from the Google Books API
     */
    public static final String GOOGLE_BOOKS_REQUEST_URL_START = "https://www.googleapis.com/books/v1/volumes?q=";
    public static final String GOOGLE_BOOKS_REQUEST_URL_END = "&maxResults=10";
    public static final int BOOK_LOADER_ID = 1;

    // Constants referring to the name of the keys,
    // in the JSON objects pulled from the Google Books API

    public static final String ID = "id";
    public static final String ITEMS = "items";
    public static final String VOLUME_INFO = "volumeInfo";
    public static final String TITLE = "title";
    public static final String PUBLISHED_DATE = "publishedDate";
    public static final String INFO_LINK = "infoLink";
    public static final String AUTHORS = "authors";
    public static final String SEARCH_INFO = "searchInfo";
    public static final String TEXT_SNIPPET = "textSnippet";
    public static final String IMAGE_LINKS = "imageLinks";
    public static final String THUMBNAIL = "thumbnail";

}
