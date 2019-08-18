package com.yaym.read.core.di;

import android.app.SearchManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yaym.read.services.BooksWebServices;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.yaym.read.core.tools.Constants.GOOGLE_BOOKS_API_BASE_URL;

/**
 * Modules are responsible for creating/satisfying dependencies
 * and signal to Dagger to search within the available methods for
 * possible instance providers
 */
@Module
public class ServicesModule {

    // Provide a reference to the ConnectivityManager to check state of network connectivity
    @Provides
    @Singleton
    ConnectivityManager provideConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    // Provide details on the currently active default data network
    @Provides
    @Singleton
    Boolean isConnected(ConnectivityManager connectivityManager) {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    // Provide SearchManager
    @Provides
    @Singleton
    SearchManager provideSearchManager(Context context) {
        return (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
    }

    // --- NETWORK INJECTION ---

    @Provides
    HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    OkHttpClient provideClient(HttpLoggingInterceptor interceptor) {
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    @Provides
    Gson provideGson() { return new GsonBuilder().create(); }

    @Provides
    Retrofit provideRetrofit(OkHttpClient client, Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(GOOGLE_BOOKS_API_BASE_URL)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    BooksWebServices provideBooksWebServices(Retrofit restAdapter) {
        return restAdapter.create(BooksWebServices.class);
    }
}