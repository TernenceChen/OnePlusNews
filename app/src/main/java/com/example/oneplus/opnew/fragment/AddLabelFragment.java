package com.example.oneplus.opnew.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.oneplus.opnew.R;


public class AddLabelFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener,Preference.OnPreferenceChangeListener{

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.checkbox);

    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }
}
