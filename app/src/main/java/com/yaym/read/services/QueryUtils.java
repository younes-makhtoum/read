package com.yaym.read.services;

import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

import com.yaym.read.data.Book;

import org.json.JSONArray;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.yaym.read.ui.QueryActivity.LOG_TAG;
import static com.yaym.read.core.tools.Constants.AUTHORS;
import static com.yaym.read.core.tools.Constants.ID;
import static com.yaym.read.core.tools.Constants.IMAGE_LINKS;
import static com.yaym.read.core.tools.Constants.INFO_LINK;
import static com.yaym.read.core.tools.Constants.ITEMS;
import static com.yaym.read.core.tools.Constants.PUBLISHED_DATE;
import static com.yaym.read.core.tools.Constants.SEARCH_INFO;
import static com.yaym.read.core.tools.Constants.TEXT_SNIPPET;
import static com.yaym.read.core.tools.Constants.THUMBNAIL;
import static com.yaym.read.core.tools.Constants.TITLE;
import static com.yaym.read.core.tools.Constants.VOLUME_INFO;

/**
 * Helper methods related to requesting and receiving book data from the Google Books API.
 */
public final class QueryUtils {

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the Google Books dataset and return an {@link Book} object to represent a single book.
     */
    public static List<Book> fetchBookData(String requestUrl) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        List<Book> books = extractFeatureFromJson(jsonResponse);

        // Return the {@link Event}
        return books;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the book JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Book} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<Book> extractFeatureFromJson(String bookJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding books to
        List<Book> books = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(bookJSON);

            if (baseJsonResponse.has(ITEMS)) {

                // Extract the JSONArray associated with the key called "items",
                // which represents a list of items (or books).
                JSONArray bookArray = baseJsonResponse.getJSONArray(ITEMS);

                // For each book in the bookArray, create an {@link Book} object
                for (int i = 0; i < bookArray.length(); i++) {

                    // Get a single book at position i within the list of books
                    JSONObject currentBook = bookArray.getJSONObject(i);

                    String id = "";
                    String title = "";
                    String year = "";
                    String infoLink = "";
                    ArrayList<String> authors = new ArrayList<>();
                    String textSnippet = "";
                    String thumbnailLink = "";

                    id = currentBook.optString(ID);

                    if (currentBook.has(VOLUME_INFO)) {

                        // For a given book, extract the JSONObject associated with the
                        // key called "volumeInfo", which represents a list of all information about that book.
                        JSONObject volumeInfo = currentBook.getJSONObject(VOLUME_INFO);

                        // Extract the value for the key called "title"
                        title = volumeInfo.optString(TITLE);

                        // Extract the value for the key called "publishedDate" and keep only the year (first 4 characters)
                        year = volumeInfo.optString(PUBLISHED_DATE);
                        if (year.length() > 4) {
                            year = year.substring(0, 4);
                        }

                        // Extract the value for the key called "infoLink"
                        infoLink = volumeInfo.optString(INFO_LINK);

                        if (volumeInfo.has(AUTHORS)) {
                            // Extract the JSONArray associated with the key called "authors", which represents a list of authors.
                            JSONArray authorsArray = volumeInfo.getJSONArray(AUTHORS);
                            for (int j = 0; j < authorsArray.length(); j++) {
                                authors.add(authorsArray.getString(j));
                            }
                        }

                        if (volumeInfo.has(IMAGE_LINKS)) {
                            // For a given book, extract the JSONObject associated with the
                            // key called "imageLinks", which contains thumbnail information about that book.
                            JSONObject imageLinks = volumeInfo.getJSONObject(IMAGE_LINKS);
                            // Extract the JSONArray associated with the key called "imageLinks", which represents a list of image links.
                            thumbnailLink = imageLinks.getString(THUMBNAIL).replace("zoom=1","zoom=0");
                        }
                    }
                    if (currentBook.has(SEARCH_INFO)) {
                        // For a given book, extract the JSONObject associated with the
                        // key called "searchInfo", which contains text snippet information about that book.
                        JSONObject searchInfo = currentBook.getJSONObject(SEARCH_INFO);

                        // Extract the value for the key called "textSnippet"
                        textSnippet = searchInfo.getString(TEXT_SNIPPET);
                    }
                    // Create a new {@link Book} object with all its related details
                    Book book = new Book(id, title, authors, year, textSnippet, infoLink, thumbnailLink);

                    // Add the new {@link Book} to the list of books.
                    books.add(book);
                }
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the book JSON results", e);
        }
        // Return the list of books
        return books;
    }
}