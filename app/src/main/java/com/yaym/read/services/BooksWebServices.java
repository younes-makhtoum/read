package com.yaym.read.services;

import com.yaym.read.data.models.GoogleBooksResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.yaym.read.core.tools.Constants.GOOGLE_BOOKS_API_PARAM_LANG_FILTER;
import static com.yaym.read.core.tools.Constants.GOOGLE_BOOKS_API_PARAM_MAX_RESULTS;
import static com.yaym.read.core.tools.Constants.GOOGLE_BOOKS_API_PARAM_QUERY;
import static com.yaym.read.core.tools.Constants.GOOGLE_BOOKS_API_VOLUMES;

public interface BooksWebServices {
    @GET(GOOGLE_BOOKS_API_VOLUMES)
    Call<GoogleBooksResponse> getBooks(
            @Query(GOOGLE_BOOKS_API_PARAM_QUERY) String inputQuery,
            @Query(GOOGLE_BOOKS_API_PARAM_MAX_RESULTS) String maxResults,
            @Query(GOOGLE_BOOKS_API_PARAM_LANG_FILTER) String langFilter);
}