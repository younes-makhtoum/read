package com.yaym.read;

import android.app.SearchManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.TextView;

import com.yaym.read.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import static com.yaym.read.Constants.BOOK_LOADER_ID;
import static com.yaym.read.Constants.GOOGLE_BOOKS_REQUEST_URL_END;
import static com.yaym.read.Constants.GOOGLE_BOOKS_REQUEST_URL_START;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>, BookAdapter.BookAdapterListener {

    public static final String LOG_TAG = BookActivity.class.getName();

    private String googleBooksRequestUrl = "";
    private List<Book> booksList = new ArrayList<>();
    private BookAdapter mAdapter;

    // Store the binding
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the content view
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Create a new adapter that takes an empty list of books as input
        mAdapter = new BookAdapter(booksList,  this);
        binding.recyclerView.setAdapter(mAdapter);
        // Handle intent for API queries
        handleIntent(getIntent());
    }

     public void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    // This method handles the intent sent by the search interface when the user hits the search icon after entering a query in the search bar
    // and call the doSearch() method to launch the network connection to get the data from the Google Books API
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String inputQuery = intent.getStringExtra(SearchManager.QUERY).replace(' ','+');
            // Building the URL query from the user's inputQuery and the static element variables of the URL
            googleBooksRequestUrl = GOOGLE_BOOKS_REQUEST_URL_START + inputQuery + GOOGLE_BOOKS_REQUEST_URL_END;
            doSearch();
        }
    }

    // This method is used to launch the network connection to get the data from the Google Books API
    private void doSearch() {
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
            binding.welcomeImage.setVisibility(View.GONE);
            binding.loadingSpinner.setVisibility(View.VISIBLE);
        } else {
            // Otherwise, display a network issue message
            // First, hide welcome image and loading indicator so error message will be visible
            binding.welcomeImage.setVisibility(View.GONE);
            binding.loadingSpinner.setVisibility(View.GONE);
            // Update empty state with no connection error message
            binding.emptyView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new BookLoader(this, googleBooksRequestUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.GONE);
        //Clear the adapter of previous book data
        mAdapter.setBookInfoList(null);
        // If there is a valid list of {@link Book}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.setBookInfoList(books);
            mAdapter.notifyDataSetChanged();
            booksList = new ArrayList<>(books);
        }
        else {
        // Set empty state text to display "No books found."
        binding.emptyView.setText(R.string.no_books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.setBookInfoList(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchview_in_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    // The onClick implementation of the RecyclerView item click
    // The purpose is to send an intent to a web browser
    // to open a website with more information about the selected book.
    @Override
    public void onBookClicked(Book book) {
        // Convert the String URL into a URI object (to pass into the Intent constructor)
        Uri bookUri = Uri.parse(book.getInfoLink());
        // Create a new intent to view the book URI
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);
        // Send the intent to launch a new activity
        startActivity(websiteIntent);
    }
}