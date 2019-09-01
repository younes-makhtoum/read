package com.yaym.read.ui;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.yaym.read.R;
import com.yaym.read.core.tools.SpacesItemDecoration;
import com.yaym.read.data.models.Book;
import com.yaym.read.databinding.FragmentBookListBinding;
import com.yaym.read.viewmodels.BooksListViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class BookListFragment extends DaggerFragment {

    private FragmentBookListBinding binding;

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

    private static final String LOG_TAG = BookListFragment.class.getName();

    private BooksListViewModel booksListViewModel;
    private List<Book> booksList = new ArrayList<>();
    private QueryAdapter queryAdapter;
    private SearchView.OnQueryTextListener queryTextListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    // View initialization logic
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_list, container, false);
        return binding.getRoot();
    }

    // Post view initialization logic
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Connect adapters -- Create a new adapter that takes an empty list of books as input
        queryAdapter = new QueryAdapter(getContext());
        binding.recyclerMain.setAdapter(queryAdapter);
        // Configure the recyclerView and the viewModel
        configureRecyclerView();
        // Initialize ViewModel and other dependencies
        booksListViewModel = new ViewModelProvider(this, viewModelFactory).get(BooksListViewModel.class);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar_menu, menu);
        // Associate searchable configuration with the SearchView
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        // Implement the onQueryText listeners
        queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                doSearch(query.replace(' ','+'));
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // This method is used to launch the network connection to get the data from the Google Books API
    private void doSearch(String inputQuery) {
        if (isConnected) {
            Log.v(LOG_TAG, "LOG// doSearch with inputQuery = " + inputQuery);
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
}
