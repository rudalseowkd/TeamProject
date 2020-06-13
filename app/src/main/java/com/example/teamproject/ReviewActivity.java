package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "moview_review2.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        // invoke super constructor.
        super(context, DATABASE_NAME, null,DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create two tables, vertices and triangles

                db.execSQL("create table movie_review ( _id integer primary key autoincrement, title String, poster_path String, release_date String, review_data String); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop the two tables, vertices and triangles
        db.execSQL("drop table if exists movie_review");
        onCreate(db);
    }
}

public class ReviewActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_review);

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
        final EditText edt = (EditText)findViewById(R.id.editReview);


                finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String review_data;
                review_data = edt.getText().toString();
                Log.d("OUT", "onCreate: " + review_data);
                db.execSQL("INSERT INTO movie_review VALUES (null, '" + title + "','" + poster_path + "','" + release_date +"','" + review_data + "');");
//                finish();
            }
        });

        Button saveBtn = findViewById(R.id.button_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor;

//                cursor = db.rawQuery("SELECT title, poster_path, release_date  FROM movie_review WHERE title == ;",null);
                cursor = db.rawQuery("SELECT title, poster_path, release_date, review_data  FROM movie_review ;",null);
                while (cursor.moveToNext()) {
                    String title_1 = cursor.getString(0);
                    String poster_path_1 = cursor.getString(1);
                    String release_date_1 = cursor.getString(2);
                    String review_data_1 = cursor.getString(3);
                    Log.v("OUT", "vertex pos (" + title_1 + ", " + poster_path_1 + ", " + release_date_1 + ", " + review_data_1 );

//                    items.add(x + ", " + y + ", " + z);
                }

//                finish();
            }
        });

    }


}
