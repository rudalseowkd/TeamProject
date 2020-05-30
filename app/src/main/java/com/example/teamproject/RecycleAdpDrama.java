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

public class RecycleAdpDrama extends RecyclerView.Adapter<RecycleAdpDrama.RecyclerViewHoldersDrama> {

    private ArrayList<Drama> DramaList;
    private LayoutInflater Inf;
    private Context Contxt;

    public RecycleAdpDrama(Context Contxt, ArrayList<Drama> itemList) {
        this.Contxt = Contxt;
        this.Inf = LayoutInflater.from(Contxt);
        this.DramaList = itemList;
    }

    @NonNull
    @Override
    public RecyclerViewHoldersDrama onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = Inf.inflate(R.layout.movie_list, parent, false);
        RecyclerViewHoldersDrama viewHolder = new RecyclerViewHoldersDrama(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHoldersDrama holders, final int position){
        //String url = MovieList.get(position).getPoster_path();
        String url = "https://image.tmdb.org/t/p/w500"+DramaList.get(position).getPoster_path();

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
                intent.putExtra("id",DramaList.get(position).getId());

                intent.putExtra("title",DramaList.get(position).getTitle());
                intent.putExtra("original_title", DramaList.get(position).getOriginal_title());
                intent.putExtra("poster_path",DramaList.get(position).getPoster_path());
                intent.putExtra("overview",DramaList.get(position).getOverview());
                intent.putExtra("release_date",DramaList.get(position).getRelease_date());
                Contxt.startActivity(intent);
                Log.d("Adapter","Clicked: " + position);
            }
        });
    }




    @Override
    public int getItemCount(){
        return this.DramaList.size();
    }


    public static class RecyclerViewHoldersDrama extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public RecyclerViewHoldersDrama(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}