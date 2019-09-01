package com.yaym.read.ui;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.yaym.read.R;
import com.yaym.read.core.tools.Utils;
import com.yaym.read.viewmodels.SettingsViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class SettingsActivity extends FragmentActivity {

    public static final String LOG_TAG = SettingsActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Inject
        ViewModelProvider.Factory viewModelFactory;

        SettingsViewModel settingsViewModel;

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
}