package com.yaym.read.services;

import com.yaym.read.data.models.GoogleBooksResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import static com.yaym.read.core.tools.Constants.GOOGLE_BOOKS_API_VOLUMES;

public interface BooksWebServices {
    @GET(GOOGLE_BOOKS_API_VOLUMES)
    Call<GoogleBooksResponse> getBooks(@QueryMap Map<String, String> params);
}