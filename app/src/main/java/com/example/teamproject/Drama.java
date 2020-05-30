package com.example.teamproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Drama {


    //예고편
    private String id;

    private String name;
    private String original_name;
    private String poster_path;
    private String overview;
    private String backdrop_path;
    private String first_air_date;

    public Drama(String name, String original_name, String poster_path,
                 String overview, String backdrop_path, String first_air_date, String id){

        this.name = name;
        this.original_name = original_name;
        this.poster_path = poster_path;
        this.overview = overview;
        this.backdrop_path = backdrop_path;
        this.first_air_date = first_air_date;

        this.id = id;
    }

    public String getTitle(){
        return name;
    }

    public String getOriginal_title(){
        return original_name;
    }

    public String getPoster_path(){
        return poster_path;
    }

    public String getOverview(){
        return overview;
    }

    public String getBackdrop_path(){
        return backdrop_path;
    }

    public String getRelease_date(){
        return first_air_date;
    }

    public String getId() { return id; }

}

