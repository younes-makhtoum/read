package com.yaym.read.ui.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.yaym.read.R;
import com.yaym.read.core.tools.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    // Tag for log messages
    private static final String LOG_TAG = SettingsFragment.class.getName();

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private SettingsViewModel settingsViewModel;

    @Override
    public void onAttach(@NonNull Context context){
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        // Initiate the ViewModel
        settingsViewModel = new ViewModelProvider(this, viewModelFactory).get(SettingsViewModel.class);
        // Invalidate the menu so that it's re-prepared with the relevant items
        setHasOptionsMenu(true);
        Objects.requireNonNull(getActivity()).invalidateOptionsMenu();
    }

    @Override
    public void onResume() {
        super.onResume();
        //unregister the preferenceChange listener
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        //unregister the preference change listener
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        // Clear the toolbar's action menu
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(false);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        String keyForSelectedLanguagesPreference = getResources().getString(R.string.key_set_languages);
        String keyForLanguageFiltering = getResources().getString(R.string.key_filter_languages);

        MultiSelectListPreference languageSelectionPref = findPreference(keyForSelectedLanguagesPreference);

        if (key.equals(keyForSelectedLanguagesPreference) && languageSelectionPref != null) {

            Set<String> languagesSelectionSet = sharedPreferences.getStringSet(keyForSelectedLanguagesPreference, null);

            if (languagesSelectionSet != null) {
                if (!languagesSelectionSet.isEmpty()) {
                    ArrayList<String> languagesSelectionArray = new ArrayList<>(languagesSelectionSet);
                    languageSelectionPref.setSummary(Utils.convertStringsArrayListToString(languagesSelectionArray, getResources().getString(R.string.comma_separator), getContext()));
                } else {
                    languageSelectionPref.setSummary(getResources().getString(R.string.none));
                }
            }
        } else if (key.equals(keyForLanguageFiltering)) {
            if (!sharedPreferences.getBoolean(keyForLanguageFiltering, false)) {
                languageSelectionPref.setValues(new HashSet<>());
                languageSelectionPref.setSummary("");
            }
        }
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference.getKey().equals(getResources().getString(R.string.key_erase_database))) {
            new AlertDialog.Builder(getContext())
                    .setTitle(getResources().getString(R.string.clear_the_database))
                    .setMessage(getResources().getString(R.string.erase_all_message))
                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        // Continue with delete operation
                        settingsViewModel.eraseAllFavoriteBooks();
                    })
                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        return super.onPreferenceTreeClick(preference);
    }
}