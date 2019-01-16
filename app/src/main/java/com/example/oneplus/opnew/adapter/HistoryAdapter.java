package com.example.oneplus.opnew.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.oneplus.opnew.R;
import com.example.oneplus.opnew.bean.HistoryBean;


import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends BaseAdapter {

    private List<HistoryBean> mHistoryBeanList;
    private Context mContext;


    public HistoryAdapter (Context context, List<HistoryBean> historyBeanList){
        this.mContext = context;
        if (historyBeanList == null){
            this.mHistoryBeanList = new ArrayList<>();
        }else {
            this.mHistoryBeanList = historyBeanList;
        }
    }

    @Override
    public int getCount() {
        return mHistoryBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return mHistoryBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HistoryBean bean = (HistoryBean) getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_history,null);
            viewHolder = new ViewHolder();
            viewHolder.history_title = view.findViewById(R.id.history_title);
            viewHolder.history_author_name = view.findViewById(R.id.history_author_name);
            viewHolder.history_date = view.findViewById(R.id.history_date);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.history_title.setText(bean.getHistory_title());
        viewHolder.history_author_name.setText(bean.getHistory_author_name());
        viewHolder.history_date.setText(bean.getHistory_date());
        return view;
    }

     private static class ViewHolder{
        TextView history_title;
        TextView history_author_name;
        TextView history_date;
    }
}
