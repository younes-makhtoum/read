package com.yaym.read.ui;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.yaym.read.R;
import com.yaym.read.core.tools.SpacesItemDecoration;
import com.yaym.read.data.models.Book;
import com.yaym.read.databinding.ActivityMainBinding;
import com.yaym.read.viewmodels.BooksListViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class QueryActivity extends DaggerAppCompatActivity implements QueryAdapter.BookAdapterListener {

    /* Dependency injection */
    @Inject
    GridLayoutManager gridLayoutManager;
    @Inject
    SpacesItemDecoration decoration;
    @Inject
    Boolean isConnected;
    @Inject
    SearchManager searchManager;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public static final String LOG_TAG = QueryActivity.class.getName();

    private ActivityMainBinding binding;
    private String inputQuery;
    private BooksListViewModel booksListViewModel;
    private List<Book> booksList = new ArrayList<>();
    private QueryAdapter queryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the content view
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        // Configure the recyclerView and the viewModel
        configureRecyclerView();
        booksListViewModel = new ViewModelProvider(this, viewModelFactory).get(BooksListViewModel.class);
        // Create a new adapter that takes an empty list of books as input
        queryAdapter = new QueryAdapter(this);
        binding.recyclerMain.setAdapter(queryAdapter);
        // Handle intent for API queries
        handleIntent(getIntent());
    }

    private void configureRecyclerView() {
        binding.recyclerMain.setLayoutManager(gridLayoutManager);
        // Enable performance optimizations (significantly smoother scrolling),
        // by setting the following parameters on the RecyclerView
        binding.recyclerMain.setHasFixedSize(true);
        binding.recyclerMain.setItemViewCacheSize(20);
        binding.recyclerMain.setDrawingCacheEnabled(true);
        binding.recyclerMain.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        // Add space between grid items in the RecyclerView,
        binding.recyclerMain.addItemDecoration(decoration);
    }

    public void onNewIntent(Intent intent) {
         super.onNewIntent(intent);
         setIntent(intent);
         handleIntent(intent);
     }

    // This method handles the intent sent by the search interface when the user hits the search icon after entering a query in the search bar
    // and call the doSearch() method to launch the network connection to get the data from the Google Books API
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // Get the input query for the searchview intent
            inputQuery = intent.getStringExtra(SearchManager.QUERY).replace(' ','+');
            doSearch();
        }
    }

    // This method is used to launch the network connection to get the data from the Google Books API
    private void doSearch() {
        if (isConnected) {
            binding.welcomeImage.setVisibility(View.GONE);
            binding.loadingSpinner.setVisibility(View.VISIBLE);
            booksListViewModel.fetchBooks(inputQuery);
            // Start observing data from the viewModel
            booksListViewModel.getBooks().observe(this, booksList -> updateUI(booksList));
        } else {
            // Otherwise, display a network issue message
            // First, hide welcome image and loading indicator so error message will be visible
            binding.welcomeImage.setVisibility(View.GONE);
            binding.loadingSpinner.setVisibility(View.GONE);
            // Update empty state with no connection error message
            binding.emptyView.setText(R.string.no_internet_connection);
        }
    }

    private void updateUI(List<Book> books) {
        queryAdapter.setBookInfoList(books);
        //  Show the EmptyView if the no books have been found in the remote API
        if (books == null || books.isEmpty()){
            binding.loadingSpinner.setVisibility(View.GONE);
            binding.emptyView.setVisibility(View.VISIBLE);
            // Set empty state text to display "No books found."
            binding.emptyView.setText(R.string.no_books);
        } else {
            binding.emptyView.setVisibility(View.GONE);
            binding.loadingSpinner.setVisibility(View.GONE);
            // Clear the adapter of previous book data
            queryAdapter.setBookInfoList(null);
            // Load it with the last fetched data
            queryAdapter.setBookInfoList(books);
            queryAdapter.notifyDataSetChanged();
            // Clear the list of books for the next query
            booksList = new ArrayList<>(books);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        // Associate searchable configuration with the SearchView
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // The onClick implementation of the RecyclerView item click
    // The purpose is to send an intent to a web browser
    // to open a website with more information about the selected book.
    @Override
    public void onBookClicked(Book book) {
        // Convert the String URL into a URI object (to pass into the Intent constructor)
        Uri bookUri = Uri.parse(book.getVolumeInfo().getInfoLink());
        // Create a new intent to view the book URI
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);
        // Send the intent to launch a new activity
        startActivity(websiteIntent);
    }
}