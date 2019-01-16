package com.example.oneplus.opnew;

import android.content.SharedPreferences;

public class SharedPreferencesManager {

    public boolean isLabelExists(SharedPreferences sharedPreferences, String key){
        return sharedPreferences.getBoolean(key,false);
    }

}
