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

public class RecycleAdp extends RecyclerView.Adapter<RecycleAdp.RecyclerViewHolders> {

    private ArrayList<Movie> MovieList;
    private LayoutInflater Inf;
    private Context Contxt;

    public RecycleAdp(Context Contxt, ArrayList<Movie> itemList) {
        this.Contxt = Contxt;
        this.Inf = LayoutInflater.from(Contxt);
        this.MovieList = itemList;
    }

    @NonNull
    @Override
    public RecyclerViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = Inf.inflate(R.layout.movie_list, parent, false);
        RecyclerViewHolders viewHolder = new RecyclerViewHolders(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolders holders, int position){
        String url = "http://image.tmdb.org/t/p/w500" + MovieList.get(position).getPoster_path();
        Glide.with(Contxt)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(holders.imageView);
    }

    @Override
    public int getItemCount(){
        return this.MovieList.size();
    }


    public static class RecyclerViewHolders extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

}