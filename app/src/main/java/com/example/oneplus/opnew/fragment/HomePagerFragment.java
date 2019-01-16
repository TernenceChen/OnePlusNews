package com.example.oneplus.opnew.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.oneplus.opnew.R;
import com.example.oneplus.opnew.SharedPreferencesManager;
import com.example.oneplus.opnew.adapter.HomePagerAdapter;
import com.example.oneplus.opnew.bean.Label;

import java.util.ArrayList;
import java.util.List;


public class HomePagerFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String KEY_1 = "key1";
    private static final String KEY_2 = "key2";
    private static final String KEY_3 = "key3";
    private static final String KEY_4 = "key4";
    private String table_military;
    private String table_technology;
    private String table_finance;
    private String table_fashion;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> mStringList = new ArrayList<>();
    private View mView;
    private HomePagerAdapter mHomePagerAdapter;
    private List<Fragment> mFragmentLists;
    private List<String> mChangedList;
    private List<String> mInitiallyList;
    private Toolbar mToolbar;
    private SharedPreferences mSharedPreferences;
    private SharedPreferencesManager mSharedPreferencesManager = new SharedPreferencesManager();
    boolean isDelete;
    boolean isAdd;
    private Label mLabel;


    final SharedPreferences.OnSharedPreferenceChangeListener mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Log.i("HomePagerFragment", "onSharedPreferenceChanged key = " + key);

            String keyText = "";
            boolean keyValue = false;
            switch (key) {
                case KEY_1:
                    keyText = table_military;
                    keyValue = initExists(KEY_1);
                    break;
                case KEY_2:
                    keyText = table_technology;
                    keyValue = initExists(KEY_2);
                    break;
                case KEY_3:
                    keyText = table_finance;
                    keyValue = initExists(KEY_3);
                    break;
                case KEY_4:
                    keyText = table_fashion;
                    keyValue = initExists(KEY_4);
                    break;
            }

            Log.i("HomePagerFragment log", "1mFragmentLists size = " + mFragmentLists.size());


            if (keyValue) {
                mStringList.add(keyText);
                mFragmentLists.add(BaseFragment.newInstance(keyText));
                mHomePagerAdapter.updateData(mStringList);
                isAdd = true;
            } else {
                mStringList.remove(keyText);
                mFragmentLists.remove(BaseFragment.newInstance(keyText));
                mHomePagerAdapter.updateData(mStringList);
                isDelete = true;

            }
            Log.i("HomePagerFragment log", "listStr size = " + mStringList.size());
            Log.i("HomePagerFragment log", "position = " + mStringList);
            Log.i("HomePagerFragment log", "mFragmentLists size = " + mFragmentLists.size());
            if (mStringList.size() <= 6) {
                mTabLayout.setTabMode(TabLayout.MODE_FIXED);
            } else {
                mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            }
            mHomePagerAdapter.notifyDataSetChanged();

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.firstfrag_layout,container,false);
        if (isAdded()){
            table_military = getString(R.string.table_military);
            table_technology = getString(R.string.table_technology);
            table_finance = getString(R.string.table_finance);
            table_fashion = getString(R.string.table_fashion);
        }
        mTabLayout = mView.findViewById(R.id.tl_tab);
        mToolbar = mView.findViewById(R.id.toolbar_news);
        mViewPager = mView.findViewById(R.id.view_pager);
        mSharedPreferences = getActivity().getSharedPreferences("com.example.oneplus.opnew_preferences",Context.MODE_PRIVATE);
        if(isAdded()){
            initToolbar(mToolbar,getResources().getString(R.string.main_tab_name));
        }
        initViews();
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();
        mSharedPreferences.registerOnSharedPreferenceChangeListener(mListener);
    }

    public void onPause(){
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(mListener);
        super.onPause();
    }

    private void initToolbar (Toolbar toolbar, String title){
        AppCompatActivity appCompatActivity =  (AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(title);
        }
    }

    private boolean initExists(String key){
        return mSharedPreferencesManager.isLabelExists(mSharedPreferences, key);
    }

    private void initViews(){
        mLabel = new Label();
        mStringList = mLabel.getListStr();
        boolean add_key1 = initExists(KEY_1);
        boolean add_key2 = initExists(KEY_2);
        boolean add_key3 = initExists(KEY_3);
        boolean add_key4 = initExists(KEY_4);
        if (add_key1 == true){
            mStringList.add(table_military);
        }
        if (add_key2 == true){
            mStringList.add(table_technology);
        }
        if (add_key3 == true){
            mStringList.add(table_finance);
        }
        if (add_key4 == true){
            mStringList.add(table_fashion);
        }
        mFragmentLists = new ArrayList<>();
        for(int i = 0; i < mStringList.size(); i++){
            mFragmentLists.add(BaseFragment.newInstance(mStringList.get(i)));
        }
        mHomePagerAdapter = new HomePagerAdapter(getChildFragmentManager(),mFragmentLists,mStringList);
        mViewPager.setAdapter(mHomePagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        if (mFragmentLists.size() <= 6){
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        }else {
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
}