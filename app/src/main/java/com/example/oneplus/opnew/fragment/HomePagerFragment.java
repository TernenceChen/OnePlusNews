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
import com.example.oneplus.opnew.bean.Label;

import java.util.ArrayList;
import java.util.List;


public class HomePagerFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private TabLayout tabLayout;
    private MyTabAdapter adapter;
    private ViewPager viewPager;
    private List<String> listStr = new ArrayList<>();
    private View view;
    private List<Fragment> fragments;
    private Toolbar toolbar;
    private SharedPreferences sps;
    boolean isDelete;
    boolean isAdd;
    Label label;

    SharedPreferences.OnSharedPreferenceChangeListener mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Log.i("HomePagerFragment", "onSharedPreferenceChanged key = " + key);

            String keyText = "";
            boolean keyValue = false;

            switch (key) {
                case "key1":
                    keyText = "军事";
                    keyValue = sps.getBoolean("key1",false);
                    break;
                case "key2":
                    keyText = "科技";
                    keyValue = sps.getBoolean("key2",false);
                    break;
                case "key3":
                    keyText = "财经";
                    keyValue = sps.getBoolean("key3",false);
                    break;
                case "key4":
                    keyText = "时尚";
                    keyValue = sps.getBoolean("key4",false);
                    break;
            }
            if (keyValue) {
                listStr.add(keyText);
                fragments.add(BaseFragment.newInstance(keyText));
                adapter.updateData(listStr);
                isAdd = true;
            } else {
                listStr.remove(keyText);
                fragments.remove(BaseFragment.newInstance(keyText));
                adapter.updateData(listStr);
                isDelete = true;
            }
            Log.i("HomePagerFragment log", "listStr size = " + listStr.size());
            Log.i("HomePagerFragment log","position = " + listStr);
            if (listStr.size() <= 6){
                tabLayout.setTabMode(TabLayout.MODE_FIXED);
            }else {
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            }
            adapter.notifyDataSetChanged();
            viewPager.setAdapter(adapter);

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.firstfrag_layout,container,false);
        tabLayout = view.findViewById(R.id.tl_tab);
        toolbar = view.findViewById(R.id.toolbar_news);
        viewPager = view.findViewById(R.id.view_pager);
        sps = getActivity().getSharedPreferences("com.example.oneplus.opnew_preferences",Context.MODE_PRIVATE);
        label = new Label();
        listStr = label.getListStr();
        String title = "首页";
        initToolbar(toolbar,title);
        initViews();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        sps.registerOnSharedPreferenceChangeListener(mListener);
    }

    public void onResume() {
        super.onResume();
        sps.registerOnSharedPreferenceChangeListener(mListener);
    }

    public void onPause(){
        sps.unregisterOnSharedPreferenceChangeListener(mListener);
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
        boolean key1 = sps.getBoolean("key1",false);
        boolean key2 = sps.getBoolean("key2",false);
        boolean key3 = sps.getBoolean("key3",false);
        boolean key4 = sps.getBoolean("key4",false);
        if (key1 == true){
            listStr.add("军事");
        }
        if (key2 == true){
            listStr.add("科技");
        }
        if (key3 == true){
            listStr.add("财经");
        }
        if (key4 == true){
            listStr.add("时尚");
        }
        fragments = new ArrayList<>();
        for(int i = 0; i < listStr.size(); i++){
            fragments.add(BaseFragment.newInstance(listStr.get(i)));
        }
        adapter = new MyTabAdapter(getChildFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        if (fragments.size() <= 6){
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }else {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
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