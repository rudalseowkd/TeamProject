package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ReviewdetailActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewdetail);


        DBHelper helper = new DBHelper(this);
        try {
            db = helper.getWritableDatabase();
        } catch (SQLException ex) {
            db = helper.getReadableDatabase();
        }


        Intent intent = getIntent();

        final String title = intent.getStringExtra("title");
        final String original_title = intent.getStringExtra("original_title");
        final String poster_path = intent.getStringExtra("poster_path");
        final String overview = intent.getStringExtra("overview");
        final String release_date = intent.getStringExtra("release_date");
        final String review_data = intent.getStringExtra("review_data");



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

        final EditText edt = (EditText)findViewById(R.id.editReview);
        edt.setText(review_data);



        Button finishBtn = findViewById(R.id.button_delete);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("OUT", "onCreate: " + review_data);
//                db.execSQL("INSERT INTO movie_review VALUES (null, '" + title + "','" + poster_path + "','" + release_date +"','" + review_data + "');");
//                finish();
//                db.execSQL("DELETE FROM tableName WHERE name = '" + name + "';");

                db.execSQL("DELETE FROM movie_review WHERE title = '" + title +"';");
                Intent intent = new Intent(getBaseContext(), reviewActivity2.class);
                startActivity(intent);
                finish();
            }
        });

        Button saveBtn = findViewById(R.id.button_edit);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor;
                String data = edt.getText().toString();
                Log.d("OUT", "onCreate: " + data);
//                cursor = db.rawQuery("SELECT title, poster_path, release_date  FROM movie_review WHERE title == ;",null);
                db.execSQL("UPDATE movie_review set review_data = '" + data + "' where title = '" + title + "';");

                Intent intent = new Intent(getBaseContext(), reviewActivity2.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
