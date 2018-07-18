package com.example.rkluwer.cleeviofilesearchexercise;

import android.app.AlertDialog;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import java.io.File;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preference_activiy);

        EditTextPreference pathPref = (EditTextPreference) getPreferenceScreen().findPreference(ConstantValues.PREF_KEY_SET_DEFAULT_FOLDER);
        pathPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                File newPath = new File(String.valueOf(newValue));
                if (!newPath.isDirectory()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Invalid input");
                    builder.setMessage("This is not a valid directory");
                    builder.setPositiveButton("OK", null);
                    builder.show();
                    return false;
                } else {
                    return true;
                }
            }
        });
    }




}
