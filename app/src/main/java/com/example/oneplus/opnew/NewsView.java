package com.example.oneplus.opnew;

import com.example.oneplus.opnew.bean.NewsBean;

import java.util.List;

public interface NewsView {

    void showProgress();
    void addNews(List<NewsBean> newsList);
    void hideProgress();
    void showLoadFailMsg();
}
