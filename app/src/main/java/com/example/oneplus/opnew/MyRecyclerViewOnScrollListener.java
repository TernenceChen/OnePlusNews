package com.example.oneplus.opnew;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MyRecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager linearLayoutManager;
    //屏幕上可见的item数量
    private int visibleItemCount;
    //已经加载出来的item数量
    private int totalItemCount;
    //屏幕上可见的第一个item
    private int firstVisibleItem;
    //是否正在上拉加载数据中
    private boolean isLoadingMore = false;
    //记录之前的数据总数
    private int agoneTotle;

    public MyRecyclerViewOnScrollListener(LinearLayoutManager linearLayoutManager){
        this.linearLayoutManager = linearLayoutManager;
    }

    /**
     * 滑动状态改变
     *
     * @param recyclerView  当前滚动的RecyclerView
     * @param newState      当前滚动的状态，有三个值
     *                      public static final int SCROLL_STATE_IDLE = 0;静止没滚动
     *                      public static final int SCROLL_STATE_DRAGGING = 1;用户正在用手指滚动
     *                      public static final int SCROLL_STATE_SETTLING = 2;自动滚动
     */
    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //向下滑动
        if (dy > 0){
            visibleItemCount = linearLayoutManager.getChildCount();
            totalItemCount = linearLayoutManager.getItemCount();
            firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
        }
        //如果正在加载中
        if (isLoadingMore){
            if (totalItemCount > agoneTotle){
                isLoadingMore = false;
                agoneTotle = totalItemCount;
            }
        }
        //如果没有正在加载中，并且，当权屏幕上可见item的总数 + 屏幕上可见第一条item大于等于目前加载出来的数据总数
        if (!isLoadingMore && (visibleItemCount + firstVisibleItem) >= totalItemCount){
            isLoadingMore = true;
            loadMoreData();
        }
    }

    public void loadMoreData() {

    }
}
