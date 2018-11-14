package com.example.oneplus.opnew.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.oneplus.opnew.R;

public class AddFragment extends Fragment {

    public Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thirdfrag_layout,container,false);
        toolbar = view.findViewById(R.id.toolbar_add);
        String title = "添加";
        initToolbar(toolbar,title);
        return view;
    }

    private void initToolbar(Toolbar toolbar, String title){
        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(title);
        }
    }
}
