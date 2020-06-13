package com.example.teamproject;

public class review {

    private String title;
    private String poster_path;
    private String relase_date;
    private String review_data;

    public review(String title, String poster_path, String relase_date, String review_data){
        this.title = title;
        this.poster_path = poster_path;
        this.relase_date = relase_date;
        this.review_data = review_data;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getPoster_path()
    {
        return this.poster_path;
    }

    public String getRelase_date()
    {
        return this.relase_date;
    }

    public String getReview_data()
    {
        return this.review_data;
    }
}
