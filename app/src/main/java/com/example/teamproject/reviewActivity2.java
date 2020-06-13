package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class reviewActivity2 extends AppCompatActivity {

    ArrayList<review> movieDataList;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review2);

        DBHelper helper = new DBHelper(this);
        try {
            db = helper.getWritableDatabase();
        } catch (SQLException ex) {
            db = helper.getReadableDatabase();
        }


        this.InitializeMovieData();

        ListView listView = (ListView)findViewById(R.id.list_view_review);
        final MyAdapter myAdapter = new MyAdapter(this,movieDataList);

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
//                Toast.makeText(getApplicationContext(),
//                        myAdapter.getItem(position).getTitle(),
//                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(), ReviewdetailActivity.class);
                //예고편
                intent.putExtra("title",myAdapter.getItem(position).getTitle());
                intent.putExtra("poster_path",myAdapter.getItem(position).getPoster_path());
                intent.putExtra("release_date",myAdapter.getItem(position).getRelase_date());
                intent.putExtra("review_data",myAdapter.getItem(position).getReview_data());

                startActivity(intent);
                finish();
            }
        });
    }

    public void InitializeMovieData()
    {

        movieDataList = new ArrayList<review>();

        Cursor cursor;

//                cursor = db.rawQuery("SELECT title, poster_path, release_date  FROM movie_review WHERE title == ;",null);
        cursor = db.rawQuery("SELECT title, poster_path, release_date, review_data  FROM movie_review ;",null);

        while (cursor.moveToNext()) {
            String title_1 = cursor.getString(0);
            String poster_path_1 = cursor.getString(1);
            String release_date_1 = cursor.getString(2);
            String review_data_1 = cursor.getString(3);
            Log.v("OUT", "vertex pos (" + title_1 + ", " + poster_path_1 + ", " + release_date_1 + ", " + review_data_1 );
            movieDataList.add(new review(title_1, poster_path_1,release_date_1,review_data_1));
//                    items.add(x + ", " + y + ", " + z);
        }
//
//        movieDataList.add(new review("123", "미션임파서블","15세 이상관람가","123"));
//        movieDataList.add(new review("123", "아저씨","19세 이상관람가","123"));
//        movieDataList.add(new review("123", "어벤져스","12세 이상관람가","123"));
    }
}
