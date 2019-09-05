package com.yaym.read.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.ToxicBakery.viewpager.transforms.ScaleInOutTransformer;
import com.google.android.material.tabs.TabLayout;
import com.yaym.read.R;
import com.yaym.read.data.models.Book;
import com.yaym.read.databinding.ActivityDetailBinding;

import org.parceler.Parcels;

import dagger.android.support.DaggerAppCompatActivity;

public class DetailActivity extends DaggerAppCompatActivity {

    // Tag for log messages
    private static final String LOG_TAG = DetailActivity.class.getName();

    // Book object instance declaration to handle the received parcelable
    private Book selectedBook;

    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the content view
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        // Collect our intent and get our parcel with the selected Movie object
        Intent intent = getIntent();
        selectedBook  = Parcels.unwrap(intent.getParcelableExtra("Book"));
        setTitle("");
        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = binding.viewPager;
        viewPager.setId(R.id.view_pager);
        // Create an adapter that knows which fragment should be shown on each page
        DetailAdapter detailAdapter = new DetailAdapter(getSupportFragmentManager());
        // Set the adapter onto the view pager
        viewPager.setAdapter(detailAdapter);
        // Animate the ViewPager
        binding.viewPager.setPageTransformer(true, new ScaleInOutTransformer());
        // Give the TabLayout the ViewPager
        TabLayout tabLayout = binding.slidingTabs;
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public Book getSelectedBook(){
        return this.selectedBook;
    }
}
