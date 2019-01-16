package com.example.oneplus.opnew.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.oneplus.opnew.NewsActivity;
import com.example.oneplus.opnew.NewsDB;
import com.example.oneplus.opnew.R;
import com.example.oneplus.opnew.adapter.HistoryAdapter;
import com.example.oneplus.opnew.bean.HistoryBean;

import java.util.ArrayList;
import java.util.List;

public class HistoryRecordFragment extends Fragment implements AdapterView.OnItemClickListener {

    private NewsDB mNewsDB;
    private Toolbar mToolbar;
    private ListView mListView;
    private HistoryAdapter mHistoryAdapter;
    private List<HistoryBean> historyBeanLists = new ArrayList<>();
    private boolean isFirstLoading = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.secondfrag_layout,container,false);
        mToolbar = view.findViewById(R.id.toolbar_history);
        mListView = view.findViewById(R.id.history_listView);
        mNewsDB = new NewsDB(getActivity());
        initData();
        initView();
        if (isAdded()){
            initToolbar(mToolbar,this.getString(R.string.historyRecord_tab_name));
        }
        return view;
    }

    private void initToolbar (Toolbar toolbar, String title){
        AppCompatActivity appCompatActivity =  (AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(title);
        }
    }

    public void initData() {
        historyBeanLists = mNewsDB.historyBeanList();
        List<HistoryBean> list = new ArrayList<>();
        for(int i = historyBeanLists.size() - 1; i >= 0; i--){
            list.add(historyBeanLists.get(i));
        }
        historyBeanLists.clear();
        historyBeanLists.addAll(list);
    }

    public void initView(){
        mHistoryAdapter = new HistoryAdapter(getActivity(),historyBeanLists);
        mListView.setAdapter(mHistoryAdapter);
        mListView.setOnItemClickListener(this);
     }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (historyBeanLists.size() <= 0) {
            return;
        }
        HistoryBean historyBean = historyBeanLists.get(position);
        Intent intent;
        String url = historyBean.getHistory_url();
        intent = new Intent(getActivity(), NewsActivity.class);
        intent.putExtra("Url", url);
        getActivity().startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirstLoading){
            initData();
            initView();
        }
        isFirstLoading = false;
    }
}
