package com.example.oneplus.opnew.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.oneplus.opnew.R;
import com.example.oneplus.opnew.bean.HistoryBean;


import java.util.ArrayList;
import java.util.List;

public class HistroyAdapter extends BaseAdapter {

    private List<HistoryBean> historyBeanList;
    private Context context;


    public HistroyAdapter (Context context, List<HistoryBean> historyBeanList){
        this.context = context;
        if (historyBeanList == null){
            this.historyBeanList = new ArrayList<>();
        }else {
            this.historyBeanList = historyBeanList;
        }
    }

    public void setHistoryBeanList(List<HistoryBean> historyBeanList){
        this.historyBeanList = historyBeanList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return historyBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return historyBeanList.get(position);
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
            view = LayoutInflater.from(context).inflate(R.layout.item_history,null);
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

    public class ViewHolder{
        TextView history_title;
        TextView history_author_name;
        TextView history_date;
    }
}
