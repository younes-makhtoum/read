package com.yaym.read.ui;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaym.read.R;
import com.yaym.read.databinding.FragmentWebReaderBinding;

import dagger.android.support.DaggerFragment;

public class WebReaderFragment extends DaggerFragment {

    // Tag for log messages
    public static final String LOG_TAG = WebReaderFragment.class.getName();
    // Store the binding
    private FragmentWebReaderBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentWebReaderBinding.bind(inflater.inflate(R.layout.fragment_web_reader, container, false));
        View rootView = binding.getRoot();

        return rootView;
    }
}