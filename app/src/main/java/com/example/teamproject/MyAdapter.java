package com.example.teamproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<review> sample;

    public MyAdapter(Context context, ArrayList<review> data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public review getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.frame_layout, null);

        ImageView imageView = (ImageView)view.findViewById(R.id.poster_view);
        TextView title = (TextView)view.findViewById(R.id.movie_title);
        TextView date = (TextView)view.findViewById(R.id.realese_date);


        ImageView imageView_poster = (ImageView) view.findViewById(R.id.poster_view);

//        Glide.with(this)
//                .load(poster_path)
//                .centerCrop()
//                .crossFade()
//                .into(imageView_poster);

        title.setText(sample.get(position).getTitle());
        date.setText(sample.get(position).getRelase_date());

        return view;
    }
}
