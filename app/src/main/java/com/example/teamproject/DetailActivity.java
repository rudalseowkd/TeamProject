package com.example.teamproject;

import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DetailActivity extends YouTubeBaseActivity {

    ArrayList<Youtube> youtubeList;

    private YouTubePlayerView youTubeView;
    private String trailer01;
    private String m_id;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        youtubeList = new ArrayList<Youtube>();

        Intent intent = getIntent();
        m_id = intent.getStringExtra("id");
        final String title = intent.getStringExtra("title");
        final String original_title = intent.getStringExtra("original_title");
        String poster_path ="https://image.tmdb.org/t/p/w500"+intent.getStringExtra("poster_path");
        String overview = intent.getStringExtra("overview");
        String release_date = intent.getStringExtra("release_date");

        TextView textView_title = (TextView)findViewById(R.id.tv_title);
        textView_title.setText(title);
        TextView textView_original_title = (TextView)findViewById(R.id.tv_original_title);
        textView_original_title.setText(original_title);
        ImageView imageView_poster = (ImageView) findViewById(R.id.iv_poster);

        Glide.with(this)
                .load(poster_path)
                .centerCrop()
                .crossFade()
                .into(imageView_poster);


        TextView textView_overview = (TextView)findViewById(R.id.tv_overview);
        textView_overview.setText(overview);
        TextView textView_release_date = (TextView)findViewById(R.id.tv_release_date);
        textView_release_date.setText(release_date);

        YoutubeAsyncTask mProcessTask = new YoutubeAsyncTask();

        Log.d(TAG, "m_id??????????????????????? " + m_id);

        mProcessTask.execute(m_id);

        Button bt_search = (Button) findViewById(R.id.bt_search);
        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //웹브라우저를 열어서 검색한다.
                String term = original_title +" "+title;
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, term);
                startActivity(intent);
            }
        });
/*
        Button bt_jamak = (Button) findViewById(R.id.bt_jamak);
        bt_jamak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String term = original_title +" "+title + " 자막";
//                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
//                intent.putExtra(SearchManager.QUERY, term);
//                startActivity(intent);
            }
        });*/
    }

    public void playVideo(final String videoId, YouTubePlayerView youTubePlayerView) {
        //initialize youtube player view
        Log.d("Youtube", "trailer: " + videoId);
        youTubePlayerView.initialize("AIzaSyClkpByW5ZZQkY_R3MMDmNTJbTa5NzFVlw",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.cueVideo(videoId);

                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }

    public class YoutubeAsyncTask extends AsyncTask<String, Void, Youtube[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Youtube[] youtubes) {
            super.onPostExecute(youtubes);

            //ArrayList에 차례대로 집어 넣는다.

            Log.d(TAG, "onPostExecute: youtube? " + youtubes);
            if(youtubes.length > 0){
                for(Youtube p : youtubes){
                    youtubeList.add(p);
                }

                //유튜브뷰어를 이용 화면에 출력하자.
                trailer01 = youtubeList.get(0).getKey();
                Log.d("Youtube", "trailer : " + trailer01);
                youTubeView = findViewById(R.id.youtube_view);
                playVideo(trailer01, youTubeView);
            }
        }

        @Override
        protected Youtube[] doInBackground(String... strings) {
            String m_id = strings[0];

            Log.d(TAG, "doInBackground: m_id???????" + m_id);

            OkHttpClient client = new OkHttpClient();
            Request request;
            if(Integer.parseInt(m_id) > 250000) {
                    request = new Request.Builder()
                        .url("https://api.themoviedb.org/3/movie/" + m_id + "/videos?api_key=5d40d3bc005b20aaf0126f65ab905344")

                        .build();
            }else{
                request = new Request.Builder()
                         .url("https://api.themoviedb.org/3/tv/"+m_id+"/videos?api_key=5d40d3bc005b20aaf0126f65ab905344")

                        .build();
            }

            try {
                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("results");
                Youtube[] posts = gson.fromJson(rootObject, Youtube[].class);

                Log.d(TAG, "doInBackground: post??????????" + posts);
                return posts;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}