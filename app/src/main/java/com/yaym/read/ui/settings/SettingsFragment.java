package com.yaym.read.ui.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.yaym.read.R;
import com.yaym.read.ui.MainActivity;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    // Tag for log messages
    private static final String LOG_TAG = SettingsFragment.class.getName();

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    SharedPreferences sharedPrefs;

    private SettingsViewModel settingsViewModel;
    private String keyForLangFilter;
    private String keyForSelectedLangPref;
    private String selectedLanguage;
    private ListPreference langSelectPref;

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
        // Setup fields
        keyForLangFilter = getResources().getString(R.string.key_filter_lang);
        keyForSelectedLangPref = getResources().getString(R.string.key_set_lang);
        selectedLanguage = sharedPrefs.getString(keyForSelectedLangPref, null);
        langSelectPref = findPreference(keyForSelectedLangPref);
        // Populate UI
        populateUI();
        // Invalidate the menu so that it's re-prepared with the relevant items
        setHasOptionsMenu(true);
        Objects.requireNonNull(getActivity()).invalidateOptionsMenu();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Hide the bottom nav bar in the current fragment
        ((MainActivity)getActivity()).setNavigationVisibility(false);
    }

    @Override
    public void onStop() {
        super.onStop();
        // Re-show the bottom nav bar when leaving the current fragment
        ((MainActivity)getActivity()).setNavigationVisibility(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Register the preferenceChange listener
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the preferenceChange listener
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
        selectedLanguage = sharedPreferences.getString(keyForSelectedLangPref, null);
        if (key.equals(keyForLangFilter)) {
            langFilterSetup();
        } else if (key.equals(keyForSelectedLangPref) && langSelectPref != null) {
            langSelectedSetup();
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

    private void populateUI() {
        langFilterSetup();
    }

    private void langFilterSetup(){
        if (!sharedPrefs.getBoolean(keyForLangFilter, false)) {
            langSelectPref.setValue("");
            langSelectPref.setVisible(false);
        } else {
            langSelectPref.setVisible(true);
            langSelectedSetup();
        }
    }

    private void langSelectedSetup(){
        if (selectedLanguage != null) {
            if (!selectedLanguage.isEmpty()) {
                langSelectPref.setVisible(true);
                langSelectPref.setTitle(getResources().getString(R.string.selected_language));
                langSelectPref.setSummary(selectedLanguage);
            } else {
                langSelectPref.setTitle(getResources().getString(R.string.select_language));
                langSelectPref.setSummary("");
            }
        }
    }
}