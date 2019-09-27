package com.yaym.read.ui.detail;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import dagger.android.support.DaggerFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.yaym.read.R;
import com.yaym.read.core.tools.Utils;
import com.yaym.read.data.models.Book;
import com.yaym.read.databinding.FragmentBookDetailsBinding;

import org.parceler.Parcels;

import java.util.Objects;

import javax.inject.Inject;

import static com.yaym.read.core.tools.Utils.convertIndustryIdentifiersArrayListToString;

public class BookDetailsFragment extends DaggerFragment {

    // Tag for log messages
    private static final String LOG_TAG = BookDetailsFragment.class.getName();
    // Store the bindings
    private FragmentBookDetailsBinding bookDetailsBinding;

    // Book object instance declaration to handle the received parcelable
    private Book selectedBook;
    // Tells whether the selected Book is among the favorites
    private boolean isFavorite;

    @Inject
    Context context;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private BookDetailsViewModel bookDetailsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Assign the bindings
        bookDetailsBinding = FragmentBookDetailsBinding.bind(inflater.inflate(R.layout.fragment_book_details, container, false));
        // Set the rootView
        View rootView = bookDetailsBinding.getRoot();
        // Initiate the ViewModel
        bookDetailsViewModel = new ViewModelProvider(this, viewModelFactory).get(BookDetailsViewModel.class);
        // Get the selected Book from the parent activity
        if (getArguments() != null) {
            selectedBook = Parcels.unwrap(getArguments().getParcelable("book"));
        }
        // Query the Book favorites database to check if the selected Book is present,
        // and display an appropriate appearance of the star ImageButton.
        bookDetailsViewModel.checkBookInFavorites(selectedBook.getId()).observe(this, bookEntry -> {
            //  Show the EmptyView if the the project_table is empty
            if (bookEntry == null) {
                isFavorite = false;
                bookDetailsBinding.favoriteBook.setSelected(false);
            } else {
                isFavorite = true;
                bookDetailsBinding.favoriteBook.setSelected(true);
            }
        });
        
        bookDetailsBinding.favoriteBook.setOnClickListener(v -> {
            if (!isFavorite) {
                Log.v(LOG_TAG, "LOG// Favorite book");
                bookDetailsViewModel.saveBookAsFavorite(selectedBook);
                bookDetailsBinding.favoriteBook.setSelected(true);
                isFavorite = true;
            } else {
                Log.v(LOG_TAG, "LOG// Unfavorite book");
                bookDetailsViewModel.removeBookFromFavorites(selectedBook);
                bookDetailsBinding.favoriteBook.setSelected(false);
                isFavorite = false;
            }
        });

        bookDetailsBinding.bookCoverFull.setOnClickListener(v -> {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(selectedBook.getVolumeInfo().getInfoLink()));
            try {
                getContext().startActivity(webIntent);
            } catch (ActivityNotFoundException ex) {
                getContext().startActivity(webIntent);
            }
        });

        populateUI();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Set the collapsing toolbar with no title
        NavigationUI.setupWithNavController(bookDetailsBinding.collapsingToolbar, bookDetailsBinding.toolbar, Navigation.findNavController(getActivity(), R.id.nav_host_fragment));
        bookDetailsBinding.collapsingToolbar.setTitleEnabled(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Hide the activity-level toolbar in the current fragment
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        // Re-show the activity-level toolbar when leaving the current fragment
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).show();
    }

    // Helper method to populate the UI with detailed data about the selected Book.
    private void populateUI() {

        if(selectedBook.getVolumeInfo().getTitle() != null) {
            bookDetailsBinding.bookDetailsScrolling.bookTitle.setText(selectedBook.getVolumeInfo().getTitle());
        }
        else {
            bookDetailsBinding.bookDetailsScrolling.bookTitle.setVisibility(View.GONE);
        }

        if(selectedBook.getVolumeInfo().getSubtitle() != null) {
            bookDetailsBinding.bookDetailsScrolling.bookSubtitle.setText(selectedBook.getVolumeInfo().getSubtitle());
        }
        else {
            bookDetailsBinding.bookDetailsScrolling.bookSubtitle.setVisibility(View.GONE);
        }

        if (selectedBook.getVolumeInfo().getAuthors() != null) {
            bookDetailsBinding.bookDetailsScrolling.bookAuthors.setText(Utils.convertStringsArrayListToString(selectedBook.getVolumeInfo().getAuthors(), getResources().getString(R.string.new_line_separator), getContext()));
        } else {
            bookDetailsBinding.bookDetailsScrolling.bookAuthors.setVisibility(View.GONE);
        }

        if (selectedBook.getVolumeInfo().getPublishedDate() != null) {
            bookDetailsBinding.bookDetailsScrolling.bookPublishedDate.setText(selectedBook.getVolumeInfo().getPublishedDate().substring(0, 4));
        } else {
            bookDetailsBinding.bookDetailsScrolling.bookPublishedDate.setVisibility(View.GONE);
        }

        if (selectedBook.getVolumeInfo().getPublisher() != null) {
            bookDetailsBinding.bookDetailsScrolling.bookPublisher.setText(selectedBook.getVolumeInfo().getPublisher());
        } else {
            bookDetailsBinding.bookDetailsScrolling.bookPublisher.setVisibility(View.GONE);
        }

        if (selectedBook.getVolumeInfo().getLanguage() != null) {
            bookDetailsBinding.bookDetailsScrolling.bookLanguage.setText(selectedBook.getVolumeInfo().getLanguage());
        } else {
            bookDetailsBinding.bookDetailsScrolling.bookLanguage.setVisibility(View.GONE);
        }

        if (selectedBook.getVolumeInfo().getCategories() != null) {
            bookDetailsBinding.bookDetailsScrolling.bookCategories
                    .setText(Utils.convertStringsArrayListToString(selectedBook.getVolumeInfo().getCategories(), getResources().getString(R.string.comma_separator), getContext()));
        } else {
            bookDetailsBinding.bookDetailsScrolling.bookCategories.setVisibility(View.GONE);
        }

        if (selectedBook.getVolumeInfo().getPageCount() != null) {
            bookDetailsBinding.bookDetailsScrolling.bookPages.setText(selectedBook.getVolumeInfo().getPageCount().toString());
        } else {
            bookDetailsBinding.bookDetailsScrolling.bookPages.setVisibility(View.GONE);
        }

        if (selectedBook.getVolumeInfo().getIndustryIdentifiers() != null) {
            bookDetailsBinding.bookDetailsScrolling.bookISBN.setText(convertIndustryIdentifiersArrayListToString(selectedBook.getVolumeInfo().getIndustryIdentifiers()));
        } else {
            bookDetailsBinding.bookDetailsScrolling.bookISBN.setVisibility(View.GONE);
        }

        Glide.with(context)
                .load(Utils.changeBookCoverThumbnail(selectedBook.getVolumeInfo().getImageLinks().getThumbnail()))
                .placeholder(R.drawable.ic_book_placeholder)
                .error(R.drawable.ic_book_placeholder)
                .fitCenter()
                .into(bookDetailsBinding.bookCoverFull);
        if (selectedBook.getVolumeInfo().getDescription() != null) {
            bookDetailsBinding.bookDetailsScrolling.bookDescription.setText(selectedBook.getVolumeInfo().getDescription());
        } else {
            bookDetailsBinding.bookDetailsScrolling.bookDescription.setVisibility(View.GONE);
        }
    }
}