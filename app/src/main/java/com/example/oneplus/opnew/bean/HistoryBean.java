package com.example.oneplus.opnew.bean;

public class HistoryBean {

    private String history_title;
    private String history_author_name;
    private String history_date;
    private String history_url;
    private long history_time;

    public String getHistory_title() {
        return history_title;
    }

    public String getHistory_date() {
        return history_date;
    }

    public String getHistory_author_name() {
        return history_author_name;
    }

    public String getHistory_url() {
        return history_url;
    }

    public long getHistory_time() {
        return history_time;
    }

    public void setHistory_title(String history_title) {
        this.history_title = history_title;
    }

    public void setHistory_date(String history_date) {
        this.history_date = history_date;
    }

    public void setHistory_author_name(String history_author_name) {
        this.history_author_name = history_author_name;
    }

    public void setHistory_url(String history_url) {
        this.history_url = history_url;
    }

    public void setHistory_time(long history_time) {
        this.history_time = history_time;
    }
}
