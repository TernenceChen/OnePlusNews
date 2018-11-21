package com.example.oneplus.opnew;

import android.content.SharedPreferences;

public class SharedPreferencesManager {

    public boolean addTable(SharedPreferences sharedPreferences, String key){
        return sharedPreferences.getBoolean(key,false);
    }

}
