package com.example.oneplus.opnew.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.oneplus.opnew.NewsHelper;
import com.example.oneplus.opnew.R;
import com.example.oneplus.opnew.adapter.HistroyAdapter;
import com.example.oneplus.opnew.bean.HistoryBean;

import java.util.ArrayList;
import java.util.List;

public class HistoryRecordFragment extends Fragment {

    private NewsDB newsDB;
    private SQLiteDatabase db;
    private NewsHelper newsHelper;
    private Toolbar toolbar;
    private ListView listView;
    private HistroyAdapter historyAdapter;
    private List<String> dataList = new ArrayList<>();
    private List<HistoryBean> historyBeanList = new ArrayList<>();
    private boolean isFirstLoading = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.secondfrag_layout,container,false);
        newsHelper = new NewsHelper(getActivity(),"History",null,1);
        db = newsHelper.getReadableDatabase();
        toolbar = view.findViewById(R.id.toolbar_history);
        listView = view.findViewById(R.id.history_listView);
        newsDB = new NewsDB(getActivity());
        initData();
        initView();
        String title = "历史记录";
        initToolbar(toolbar,title);
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
        historyBeanList = newsDB.historyBeanList();
        List<HistoryBean> list = new ArrayList<>();
        for(int i = historyBeanList.size() - 1; i >= 0; i--){
            list.add(historyBeanList.get(i));
        }
        historyBeanList.clear();
        historyBeanList.addAll(list);
    }

    public void initView(){
        historyAdapter = new HistroyAdapter(getActivity(),historyBeanList);
        listView.setAdapter(historyAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (historyBeanList.size() <= 0) {
                    return;
                }
                HistoryBean historyBean = historyBeanList.get(position);
                Intent intent;
                String url = historyBean.getHistory_url();
                intent = new Intent(getActivity(), NewsActivity.class);
                intent.putExtra("Url", url);
                getActivity().startActivity(intent);
            }
        });
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

    public void updateTable(){
        
    }
}
