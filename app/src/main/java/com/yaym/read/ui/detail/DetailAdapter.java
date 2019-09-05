package com.yaym.read.ui.detail;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Provides the appropriate {@link Fragment} for a view pager.
 */
public class DetailAdapter extends FragmentPagerAdapter {

    // Tag for log messages
    public static final String LOG_TAG = DetailAdapter.class.getName();

    private String[] tabTitles = new String[]{"ID Card", "Summary"};

    public DetailAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BookIdCardFragment();
            case 1:
                return new BookSummaryFragment();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return 2;
    }
}
