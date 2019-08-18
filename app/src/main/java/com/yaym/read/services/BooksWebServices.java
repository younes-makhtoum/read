package com.yaym.read.services;

import com.yaym.read.data.models.GoogleBooksResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BooksWebServices {
    @GET("v1/volumes?")
    Call<GoogleBooksResponse> getBooks(
            @Query("q") String inputQuery,
            @Query("maxResults") String maxResults);
}