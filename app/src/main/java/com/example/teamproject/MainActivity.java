package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecycleAdp adapter;
    ArrayList<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        movieList = new ArrayList<Movie>();


        AsyTask asyncTask = new AsyTask();
        asyncTask.execute();

        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
    }

    public class AsyTask extends AsyncTask<String, Void, Movie[]>{


        ProgressDialog progressDlg = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDlg.setMessage("\t로딩중...");
            progressDlg.show();

        }

        @Override
        protected Movie[] doInBackground(String... strings){
            OkHttpClient cli = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/movie/upcoming?api_key=5d40d3bc005b20aaf0126f65ab905344&language=ko-KR&page=1")
                    .build();

            try{
                Response response = cli.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream()).getAsJsonObject().get("results");
                Movie[] posts = gson.fromJson(rootObject, Movie[].class);
                return posts;
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Movie[] result){
            super.onPostExecute(result);
            progressDlg.dismiss();
            if(result.length>0){
                for(Movie p : result){
                    movieList.add(p);
                }
            }

            adapter = new RecycleAdp(MainActivity.this,movieList);
            recyclerView.setAdapter(adapter);
        }

    }

}
