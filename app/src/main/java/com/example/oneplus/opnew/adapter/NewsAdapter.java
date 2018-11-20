package com.example.oneplus.opnew.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.oneplus.opnew.R;
import com.example.oneplus.opnew.bean.NewsListNormalBean;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    public static final int TYPE_RIGHT_IMAGE = 0;
    public static final int TYPE_THREE_IMAGE = 1;
    private OnItemClickListener mOnItemClickListener;
    private ArrayList<NewsListNormalBean.ResultBean.DataBean> mNewsListNormalBeanList;

    public void setmNewsListNormalBeanList(ArrayList<NewsListNormalBean.ResultBean.DataBean> mNewsListNormalBeanList) {
        this.mNewsListNormalBeanList = mNewsListNormalBeanList;
        this.notifyDataSetChanged();
    }

    public NewsListNormalBean.ResultBean.DataBean getItem (int position){
        return mNewsListNormalBeanList == null ? null : mNewsListNormalBeanList.get(position);
    }

    public NewsAdapter (Context context, ArrayList<NewsListNormalBean.ResultBean.DataBean> mNewsListNormalBeanList){
        this.context = context;
        this.mNewsListNormalBeanList = mNewsListNormalBeanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if (i == TYPE_THREE_IMAGE){
            view = View.inflate(viewGroup.getContext(), R.layout.item,null);
            Log.i("RightImage","This is RightImage");
            return new RightImageHolder(view);
        }
        else {
            view = View.inflate(viewGroup.getContext(),R.layout.item_three,null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ThreeImageHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {

        NewsListNormalBean.ResultBean.DataBean data = mNewsListNormalBeanList.get(i);
        String title = data.getTitle();
        String author_name = data.getAuthor_name();
        String date = data.getDate();
        String image = data.getThumbnail_pic_s();
        if (data.getThumbnail_pic_s02() != null && data.getThumbnail_pic_s03() != null){
            String image1 = data.getThumbnail_pic_s02();
            String image2 = data.getThumbnail_pic_s03();
            ThreeImageHolder threeImageHolder = (ThreeImageHolder) viewHolder;
            setNetPicture(image,threeImageHolder.imageView1);
            setNetPicture(image1,threeImageHolder.imageView2);
            setNetPicture(image2,threeImageHolder.imageView3);
            threeImageHolder.title.setText(title);
            threeImageHolder.author_name.setText(author_name);
            threeImageHolder.date.setText(date);
        }else {
            RightImageHolder rightImageHolder = (RightImageHolder) viewHolder;
            setNetPicture(image, rightImageHolder.imageView);
            rightImageHolder.title.setText(title);
            rightImageHolder.author_name.setText(author_name);
            rightImageHolder.date.setText(date);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = viewHolder.getAdapterPosition();
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(viewHolder.itemView,pos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mNewsListNormalBeanList != null){
            return mNewsListNormalBeanList.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        NewsListNormalBean.ResultBean.DataBean newsListNormalBean = mNewsListNormalBeanList.get(position);
        if(newsListNormalBean.getThumbnail_pic_s02() != null && newsListNormalBean.getThumbnail_pic_s03() != null){
            return TYPE_RIGHT_IMAGE;
        }else{
            return TYPE_THREE_IMAGE;
        }
    }

    private class RightImageHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView date;
        TextView author_name;
        ImageView imageView;


        public RightImageHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.view_text);
            date = itemView.findViewById(R.id.date_view);
            author_name = itemView.findViewById(R.id.author_name_view);
            imageView = itemView.findViewById(R.id.news_image);
        }
    }

    private class ThreeImageHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView date;
        TextView author_name;
        ImageView imageView1;
        ImageView imageView2;
        ImageView imageView3;

        public ThreeImageHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.view_text_three);
            date = itemView.findViewById(R.id.date_view_three);
            author_name = itemView.findViewById(R.id.author_name_view_three);
            imageView1 = itemView.findViewById(R.id.news_image1);
            imageView2 = itemView.findViewById(R.id.news_image2);
            imageView3 = itemView.findViewById(R.id.news_image3);
        }
    }

    private void setNetPicture(String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }
}
