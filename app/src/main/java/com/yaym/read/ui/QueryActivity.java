package com.yaym.read.ui;

import android.os.Bundle;
import dagger.android.support.DaggerAppCompatActivity;
import com.yaym.read.R;

public class QueryActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the content view
        setContentView(R.layout.book_list_host);
        // As FragmentManager automatically saves and restores fragments over configuration changes,
        // we only need to add the fragment if the savedInstanceState is null.
        if (savedInstanceState == null) {
            BookListFragment fragment = new BookListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.query_content, fragment)
                    .commit();
        }
    }
}