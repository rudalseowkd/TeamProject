package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_review);
        Intent intent = getIntent();

        final String title = intent.getStringExtra("title");
        final String original_title = intent.getStringExtra("original_title");
        final String poster_path = intent.getStringExtra("poster_path");
        final String overview = intent.getStringExtra("overview");
        final String release_date = intent.getStringExtra("release_date");

        TextView textView_title = (TextView)findViewById(R.id.textView2);
        textView_title.setText(title);
//        TextView textView_original_title = (TextView)findViewById(R.id.tv_original_title);
//        textView_original_title.setText(original_title);
        ImageView imageView_poster = (ImageView) findViewById(R.id.iv_poster_2);

        Glide.with(this)
                .load(poster_path)
                .centerCrop()
                .crossFade()
                .into(imageView_poster);


//        TextView textView_overview = (TextView)findViewById(R.id.tv_overview);
//        textView_overview.setText(overview);
        TextView textView_release_date = (TextView)findViewById(R.id.textView);
        textView_release_date.setText(release_date);


        Button finishBtn = findViewById(R.id.button_finish);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button saveBtn = findViewById(R.id.button_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}
