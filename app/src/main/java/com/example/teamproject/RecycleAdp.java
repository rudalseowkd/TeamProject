package com.example.teamproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

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
    public void onBindViewHolder(@NonNull RecyclerViewHolders holders, final int position){
        //String url = MovieList.get(position).getPoster_path();
        String url = "https://image.tmdb.org/t/p/w500"+MovieList.get(position).getPoster_path();

        Glide.with(Contxt)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(holders.imageView);

        holders.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Contxt, DetailActivity.class);

                //예고편
                intent.putExtra("id",MovieList.get(position).getId());

                intent.putExtra("title",MovieList.get(position).getTitle());
                intent.putExtra("original_title", MovieList.get(position).getOriginal_title());
                intent.putExtra("poster_path",MovieList.get(position).getPoster_path());
                intent.putExtra("overview",MovieList.get(position).getOverview());
                intent.putExtra("release_date",MovieList.get(position).getRelease_date());
                Contxt.startActivity(intent);
                Log.d("Adapter","Clicked: " + position);
            }
        });
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