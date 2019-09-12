package com.yaym.read.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.yaym.read.R;
import com.yaym.read.core.tools.SpacesItemDecoration;
import com.yaym.read.data.models.Book;
import com.yaym.read.databinding.FragmentHomeBinding;
import com.yaym.read.ui.explore.ExploreFragment;
import com.yaym.read.ui.BooksListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.android.support.DaggerFragment;

import static com.yaym.read.core.tools.Utils.configureRecyclerView;

public class HomeFragment extends DaggerFragment {

    // Tag for log messages
    private static final String LOG_TAG = HomeFragment.class.getName();

    private FragmentHomeBinding binding;

    /* Dependency injection */
    @Inject
    Provider<GridLayoutManager> gridLayoutManagerProvider;
    @Inject
    SpacesItemDecoration decoration;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        // Configure the recyclerView and the viewModel
        configureRecyclerView(binding.recyclerHome, gridLayoutManagerProvider.get(), decoration);
        return binding.getRoot();
    }

    // Post view initialization logic
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Connect adapters -- Create a new adapter that takes an empty list of books as input
        booksListAdapter = new BooksListAdapter(getContext());
        binding.recyclerHome.setAdapter(booksListAdapter);
        // Initialize ViewModel and other dependencies
        HomeViewModel homeViewModel = new ViewModelProvider(this, viewModelFactory).get(HomeViewModel.class);
        // Start observing data from the viewModel
        homeViewModel.getAllBooks().observe(this, this::updateUI);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // Hide the search item from the app bar's menu
        MenuItem searchItem = menu.findItem(R.id.action_search);
        if (searchItem != null) {
            searchItem.setVisible(false);
        }
    }

    private void updateUI(List<Book> books) {
        booksListAdapter.setBookInfoList(books);
        //  Show the EmptyView if the no books have been found in the remote API
        if (books == null || books.isEmpty()) {
            binding.recyclerHome.setVisibility(View.GONE);
            binding.emptyView.setVisibility(View.VISIBLE);
        } else {
            binding.emptyView.setVisibility(View.GONE);
            // Clear the adapter of previous book data
            booksListAdapter.setBookInfoList(null);
            // Load it with the last fetched data
            booksListAdapter.setBookInfoList(books);
            booksListAdapter.notifyDataSetChanged();
            // Clear the list of books for the next query
            booksList = new ArrayList<>(books);
        }
    }
}