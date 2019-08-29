package com.yaym.read.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;

import com.bumptech.glide.Glide;
import com.yaym.read.R;
import com.yaym.read.core.tools.Utils;
import com.yaym.read.data.models.Book;
import com.yaym.read.databinding.FragmentBookSummaryBinding;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class BookSummaryFragment extends DaggerFragment {

    // Tag for log messages
    public static final String LOG_TAG = BookSummaryFragment.class.getName();
    // Store the binding
    private FragmentBookSummaryBinding binding;
    // Book object instance declaration to handle the received parcelable
    private Book selectedBook;
    @Inject
    WebViewClient webViewClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBookSummaryBinding.bind(inflater.inflate(R.layout.fragment_book_summary, container, false));
        View rootView = binding.getRoot();

        // Get the selected Book from the parent activity
        selectedBook = ((DetailActivity)this.getActivity()).getSelectedBook();

        Log.v(LOG_TAG,"LOG// WebReaderURL is : " + selectedBook.getAccessInfo().getWebReaderLink());
        Log.v(LOG_TAG,"LOG// InfoLinkURL is : " + selectedBook.getVolumeInfo().getInfoLink());

        binding.bookCoverFull.setOnClickListener(v -> {
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

    private void populateUI() {

        Glide.with(getContext())
                .load(Utils.changeBookCoverThumbnail(selectedBook.getVolumeInfo().getImageLinks().getThumbnail()))
                .placeholder(R.drawable.ic_book_placeholder)
                .fitCenter()
                .into(binding.bookCoverFull);

        if(selectedBook.getVolumeInfo().getDescription() != null) {
            binding.bookDescription.setText(selectedBook.getVolumeInfo().getDescription());
        }
        else {
            binding.bookDescription.setVisibility(View.GONE);
        }
    }
}