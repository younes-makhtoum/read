package com.yaym.read.ui;

import android.os.Bundle;

import dagger.android.support.DaggerFragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaym.read.R;
import com.yaym.read.core.tools.Utils;
import com.yaym.read.data.models.Book;
import com.yaym.read.databinding.FragmentBookIdCardBinding;
import com.yaym.read.viewmodels.BookSummaryViewModel;

import javax.inject.Inject;

import static com.yaym.read.core.tools.Utils.convertIndustryIdentifiersArrayListToString;

public class BookIdCardFragment extends DaggerFragment {

    // Tag for log messages
    private static final String LOG_TAG = BookIdCardFragment.class.getName();
    // Store the binding
    private FragmentBookIdCardBinding binding;
    // Book object instance declaration to handle the received parcelable
    private Book selectedBook;
    // Tells whether the selected Book is among the favorites
    private boolean isFavorite;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private BookSummaryViewModel bookSummaryViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBookIdCardBinding.bind(inflater.inflate(R.layout.fragment_book_id_card, container, false));
        View rootView = binding.getRoot();

        // Initiate the ViewModel
        bookSummaryViewModel = new ViewModelProvider(this, viewModelFactory).get(BookSummaryViewModel.class);

        // Get the selected Book from the parent activity
        selectedBook = ((DetailActivity)this.getActivity()).getSelectedBook();

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
            binding.bookTitle.setText(selectedBook.getVolumeInfo().getTitle());
        }
        else {
            binding.bookTitle.setVisibility(View.GONE);
        }

        if(selectedBook.getVolumeInfo().getSubtitle() != null) {
            binding.bookSubtitle.setText(selectedBook.getVolumeInfo().getSubtitle());
        }
        else {
            binding.bookSubtitle.setVisibility(View.GONE);
        }

        if(selectedBook.getVolumeInfo().getAuthors() != null) {
            binding.bookAuthors.setText(Utils.convertStringsArrayListToString(selectedBook.getVolumeInfo().getAuthors(), getResources().getString(R.string.new_line_separator), getContext()));
        }
        else {
            binding.bookAuthors.setVisibility(View.GONE);
        }

        if(selectedBook.getVolumeInfo().getPublishedDate() != null) {
            binding.bookPublishedDate.setText(selectedBook.getVolumeInfo().getPublishedDate().substring(0,4));
        }
        else {
            binding.bookPublishedDate.setVisibility(View.GONE);
        }

        if(selectedBook.getVolumeInfo().getPublisher() != null) {
            binding.bookPublisher.setText(selectedBook.getVolumeInfo().getPublisher());
        }
        else {
            binding.bookPublisher.setVisibility(View.GONE);
        }

        if(selectedBook.getVolumeInfo().getLanguage() != null) {
            binding.bookLanguage.setText(selectedBook.getVolumeInfo().getLanguage());
        }
        else {
            binding.bookLanguage.setVisibility(View.GONE);
        }

        if(selectedBook.getVolumeInfo().getCategories() != null) {
            binding.bookCategories
                    .setText(Utils.convertStringsArrayListToString(selectedBook.getVolumeInfo().getCategories(), getResources().getString(R.string.comma_separator), getContext()));
        }
        else {
            binding.bookCategories.setVisibility(View.GONE);
        }

        if(selectedBook.getVolumeInfo().getPageCount() != null) {
            binding.bookPages.setText(selectedBook.getVolumeInfo().getPageCount().toString());
        }
        else {
            binding.bookPages.setVisibility(View.GONE);
        }

        if(selectedBook.getVolumeInfo().getIndustryIdentifiers() != null) {
            binding.bookISBN.setText(convertIndustryIdentifiersArrayListToString(selectedBook.getVolumeInfo().getIndustryIdentifiers()));
        }
        else {
            binding.bookISBN.setVisibility(View.GONE);
        }
    }
}