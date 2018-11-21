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
import com.example.oneplus.opnew.bean.Label;

import java.util.ArrayList;
import java.util.List;


public class HomePagerFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private final String key1 = "key1";
    private final String key2 = "key2";
    private final String key3 = "key3";
    private final String key4 = "key4";
    private String table_military = "军事";
    private String table_technology = "科技";
    private String table_finance = "财经";
    private String table_fashion = "时尚";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> listStr = new ArrayList<>();
    private View view;
    private MyTabAdapter myTabAdapter;
    private List<Fragment> mFragmentLists;
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
                case key1:
                    keyText = table_military;
                    keyValue = mSharedPreferencesManager.addTable(mSharedPreferences,key1);
                    break;
                case key2:
                    keyText = table_technology;
                    keyValue = mSharedPreferencesManager.addTable(mSharedPreferences,key2);
                    break;
                case key3:
                    keyText = table_finance;
                    keyValue = mSharedPreferencesManager.addTable(mSharedPreferences,key3);
                    break;
                case key4:
                    keyText = table_fashion;
                    keyValue = mSharedPreferencesManager.addTable(mSharedPreferences,key4);
                    break;
            }
            if (keyValue) {
                listStr.add(keyText);
                mFragmentLists.add(BaseFragment.newInstance(keyText));
                myTabAdapter.updateData(listStr);
                isAdd = true;
            } else {
                listStr.remove(keyText);
                mFragmentLists.remove(BaseFragment.newInstance(keyText));
                myTabAdapter.updateData(listStr);
                isDelete = true;
            }
            Log.i("HomePagerFragment log", "listStr size = " + listStr.size());
            Log.i("HomePagerFragment log", "position = " + listStr);
            if (listStr.size() <= 6) {
                mTabLayout.setTabMode(TabLayout.MODE_FIXED);
            } else {
                mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            }
            myTabAdapter.notifyDataSetChanged();
            mViewPager.setAdapter(myTabAdapter);

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.firstfrag_layout,container,false);
        mTabLayout = view.findViewById(R.id.tl_tab);
        mToolbar = view.findViewById(R.id.toolbar_news);
        mViewPager = view.findViewById(R.id.view_pager);
        mSharedPreferences = getActivity().getSharedPreferences("com.example.oneplus.opnew_preferences",Context.MODE_PRIVATE);
        mLabel = new Label();
        listStr = mLabel.getListStr();
        initToolbar(mToolbar,getResources().getString(R.string.main_tab_name));
        initViews();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mSharedPreferences.registerOnSharedPreferenceChangeListener(mListener);
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

    private void initViews(){
        boolean add_key1 = mSharedPreferencesManager.addTable(mSharedPreferences,key1);
        boolean add_key2 = mSharedPreferencesManager.addTable(mSharedPreferences,key2);
        boolean add_key3 = mSharedPreferencesManager.addTable(mSharedPreferences,key3);
        boolean add_key4 = mSharedPreferencesManager.addTable(mSharedPreferences,key4);
        if (add_key1 == true){
            listStr.add(table_military);
        }
        if (add_key2 == true){
            listStr.add(table_technology);
        }
        if (add_key3 == true){
            listStr.add(table_finance);
        }
        if (add_key4 == true){
            listStr.add(table_fashion);
        }
        mFragmentLists = new ArrayList<>();
        for(int i = 0; i < listStr.size(); i++){
            mFragmentLists.add(BaseFragment.newInstance(listStr.get(i)));
        }
        myTabAdapter = new MyTabAdapter(getChildFragmentManager(),mFragmentLists);
        mViewPager.setAdapter(myTabAdapter);
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
    class MyTabAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> fragments;

        public MyTabAdapter (FragmentManager fm, List<Fragment> fragments){
            super(fm);
            this.fragments = fragments;
        }

        public void updateData(List<String> dataList){
            ArrayList<Fragment> fragments = new ArrayList<>();
            for (int i = 0, size = dataList.size(); i < size; i++) {
                Log.e("HomePagerFragment", dataList.get(i));
                fragments.add(BaseFragment.newInstance(listStr.get(i)));
            }
            setFragmentList(fragments);
        }

        private void setFragmentList(ArrayList<Fragment> fragmentList) {
            if(this.fragments != null){
                fragments.clear();
            }
            this.fragments = fragmentList;
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listStr.get(position);
        }
    }
}