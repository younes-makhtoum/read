package com.yaym.read.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import dagger.android.support.DaggerFragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.yaym.read.R;
import com.yaym.read.data.models.Book;
import com.yaym.read.databinding.FragmentSummaryBinding;
import com.yaym.read.viewmodels.BookSummaryViewModel;

import javax.inject.Inject;

public class SummaryFragment extends DaggerFragment {

    // Tag for log messages
    private static final String LOG_TAG = SummaryFragment.class.getName();
    // Store the binding
    private FragmentSummaryBinding binding;
    // Book object instance declaration to handle the received parcelable
    private Book selectedBook;
    // Tells whether the selected Book is among the favorites
    private boolean isFavorite;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private BookSummaryViewModel bookSummaryViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSummaryBinding.bind(inflater.inflate(R.layout.fragment_summary, container, false));
        View rootView = binding.getRoot();

        // Initiate the ViewModel
        bookSummaryViewModel = new ViewModelProvider(this, viewModelFactory).get(BookSummaryViewModel.class);

        // Get the selected Book from the parent activity
        selectedBook = ((DetailActivity)this.getActivity()).getSelectedBook();

        Glide.with(getContext())
                .load(selectedBook.getVolumeInfo().getImageLinks().getThumbnail())
                .placeholder(R.drawable.ic_book_placeholder)
                .fitCenter()
                .into(binding.bookCover);

        // Query the Book favorites database to check if the selected Book is present,
        // and display an appropriate appearance of the star ImageButton.
        bookSummaryViewModel.checkBookInFavorites(selectedBook.getId()).observe(this, bookEntry -> {
            //  Show the EmptyView if the the project_table is empty
            if (bookEntry == null) {
                isFavorite = false;
                binding.starButton.setSelected(false);
                Log.v(LOG_TAG, "LOG// Book is not favorite");
            } else {
                isFavorite = true;
                binding.starButton.setSelected(true);
            }
        });

        binding.bookCover.setOnClickListener(v -> {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(selectedBook.getVolumeInfo().getInfoLink()));
            try {
                getContext().startActivity(webIntent);
            } catch (ActivityNotFoundException ex) {
                getContext().startActivity(webIntent);
            }
        });

        binding.starButton.setOnClickListener(v -> {
            if (!isFavorite) {
                Log.v(LOG_TAG, "LOG// Favorite book");
                bookSummaryViewModel.saveBookAsFavorite(selectedBook);
                binding.starButton.setSelected(true);
                isFavorite = true;
            } else {
                Log.v(LOG_TAG, "LOG// Unfavorite book");
                bookSummaryViewModel.removeBookFromFavorites(selectedBook);
                binding.starButton.setSelected(false);
                isFavorite = false;
            }
        });

        populateUI();

        return rootView;
    }

    // Helper method to populate the summary screen with detailed data about the selected Book.
    private void populateUI() {

        if(selectedBook.getVolumeInfo().getTitle() != null) {
            binding.title.setText(selectedBook.getVolumeInfo().getTitle());
        }
        else {
            binding.title.setVisibility(View.GONE);
        }

        if(selectedBook.getVolumeInfo().getPublishedDate() != null) {
            binding.publishingDate.setText(selectedBook.getVolumeInfo().getPublishedDate().substring(0,4));
        }
        else {
            binding.publishingDate.setVisibility(View.GONE);
        }

        if(selectedBook.getVolumeInfo().getAverageRating() != null) {
            binding.averageRating.setText(String.valueOf(selectedBook.getVolumeInfo().getAverageRating()));
        }
        else {
            binding.averageRating.setVisibility(View.GONE);
        }

        if(selectedBook.getVolumeInfo().getDescription() != null) {
            binding.bookDescription.setText(selectedBook.getVolumeInfo().getDescription());
        }
        else {
            binding.bookDescription.setVisibility(View.GONE);
        }
    }
}