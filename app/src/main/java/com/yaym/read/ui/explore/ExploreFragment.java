package com.yaym.read.ui.explore;

import android.app.SearchManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yaym.read.R;
import com.yaym.read.core.tools.SpacesItemDecoration;
import com.yaym.read.core.tools.Utils;
import com.yaym.read.data.models.Book;

import com.yaym.read.databinding.FragmentExploreBinding;
import com.yaym.read.ui.BooksListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.android.support.DaggerFragment;

public class ExploreFragment extends DaggerFragment {

    // Tag for log messages
    private static final String LOG_TAG = ExploreFragment.class.getName();

    private FragmentExploreBinding binding;
    private String inputQuery;

    /* Dependency injection */
    @Inject
    Provider<GridLayoutManager> gridLayoutManagerProvider;
    @Inject
    SpacesItemDecoration decoration;
    @Inject
    Boolean isConnected;
    @Inject
    SearchManager searchManager;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ExploreViewModel booksListViewModel;
    private List<Book> booksList = new ArrayList<>();
    private BooksListAdapter booksListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // Invalidate the menu so that it's re-prepared with the relevant items
        Objects.requireNonNull(getActivity()).invalidateOptionsMenu();
    }

    // View initialization logic
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_explore, container, false);
        // Configure the recyclerView and the viewModel
        Utils.configureRecyclerView(binding.recyclerExplore, gridLayoutManagerProvider.get(), decoration);
        return binding.getRoot();
    }

    // Post view initialization logic
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Connect adapters -- Create a new adapter that takes an empty list of books as input
        booksListAdapter = new BooksListAdapter(getContext());
        binding.recyclerExplore.setAdapter(booksListAdapter);
        // Initialize ViewModel and other dependencies
        booksListViewModel = new ViewModelProvider(this, viewModelFactory).get(ExploreViewModel.class);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        // Associate searchable configuration with the SearchView
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        // Make the search menu item expanded by default (does not work for the moment)
        searchView.setIconifiedByDefault(false);
        // Implement the onQueryText listeners
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                inputQuery = query.replace(' ', '+');
                searchView.clearFocus();
                doSearch();
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
    }

    // This method is used to launch the network connection to get the data from the Google Books API
    private void doSearch() {
        if (isConnected) {
            Log.v(LOG_TAG, "LOG// doSearch with inputQuery = " + inputQuery);
            binding.dummyBooks.setVisibility(View.GONE);
            binding.loadingSpinner.setVisibility(View.VISIBLE);
            booksListViewModel.fetchBooks(inputQuery);
            // Start observing data from the viewModel
            booksListViewModel.getBooks().observe(this, this::updateUI);
        } else {
            // Otherwise, display a network issue message
            // First, hide welcome image and loading indicator so error message will be visible
            binding.dummyBooks.setVisibility(View.GONE);
            binding.loadingSpinner.setVisibility(View.GONE);
            // Update empty state with no connection error message
            binding.errorMessage.setText(R.string.no_internet_connection);
        }
    }

    private void updateUI(List<Book> books) {
        Log.v(LOG_TAG, "LOG// updateUI with these books " + books);
        //  Show the EmptyView if the no books have been found in the remote API
        if (books == null || books.isEmpty()){
            binding.loadingSpinner.setVisibility(View.GONE);
            binding.errorMessage.setVisibility(View.VISIBLE);
            // Set empty state text to display "No books found."
            binding.errorMessage.setText(R.string.no_books);
        } else {
            binding.loadingSpinner.setVisibility(View.GONE);
            // Clear the adapter of previous book data
            booksListAdapter.setBookInfoList(null);
            // Load it with the last fetched data
            booksListAdapter.setBookInfoList(books);
            booksListAdapter.notifyDataSetChanged();
            // Clear the list of books for the next query
            booksList = new ArrayList<>(books);
            // Fix for the display of recycler view items at network task completion
            Objects.requireNonNull(binding.recyclerExplore.getLayoutManager())
                    .smoothScrollToPosition(binding.recyclerExplore, new RecyclerView.State(),
                            0);
        }
    }
}
