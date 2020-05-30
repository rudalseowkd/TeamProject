package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


// https://api.themoviedb.org/3/tv/popular?api_key=5d40d3bc005b20aaf0126f65ab905344&language=ko-KR&page=1
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecycleAdp adapter;
    private RecycleAdpDrama adapterDrama;
    int searchOption = 1;   // 1=movie, 2=drama
    String movieUrl = "https://api.themoviedb.org/3/movie/upcoming?api_key=5d40d3bc005b20aaf0126f65ab905344&language=ko-KR&page=1";

    ArrayList<Movie> movieList;
    ArrayList<Drama> dramaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        movieList = new ArrayList<Movie>();
        dramaList = new ArrayList<Drama>();

//        String search_url = "https://api.themoviedb.org/3/search/movie?api_key=<5d40d3bc005b20aaf0126f65ab905344>&query=<영화제목 String>&language=ko-KR&page=1";
        String search_url = "https://api.themoviedb.org/3/movie/upcoming?api_key=5d40d3bc005b20aaf0126f65ab905344&language=ko-KR&page=1";
       //* String search_url = "https://api.themoviedb.org/3/tv/popular?api_key=5d40d3bc005b20aaf0126f65ab905344&language=ko-KR&page=1";
        //String[] strings = {search_url};
        String[] strings;
        strings = new String[] {movieUrl};

        AsyTask asyncTask = new AsyTask();
        asyncTask.execute(strings[0]);

        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.my_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("pls", "onCreateOptionsMenu: ??????????????????????????????????????????????????????????????????");
        getMenuInflater().inflate(R.menu.my_menu, menu);

        Log.d("pls", "onCreateOptionsMenu: ??????????????????????????????????????????????????????????????????");

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("영화제목을 입력하세요.");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            //검색어를 다 입력하고 서치 버튼을 눌렀을때
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(MainActivity.this, s + "에 대한 검색을 합니다.", Toast.LENGTH_LONG).show();

              //  https://api.themoviedb.org/3/tv/popular?api_key=5d40d3bc005b20aaf0126f65ab905344&language=ko-KR&page=1
                //여기서 AsyncTask를 이용 검색 리퀘스트로 데이터를 받아 오게 처리 하자. - AsyncTask 공유할것.
                String[] strings;
                String search_url = "";
              //  strings = new String[] {movieUrl};
                if(searchOption==1) {// 영화 검색
                    search_url = "https://api.themoviedb.org/3/search/movie?api_key=5d40d3bc005b20aaf0126f65ab905344&query=" + s;
                    search_url.concat("&language=ko-KR&page=1");
                }else{// 드라마 검색
                    search_url = "https://api.themoviedb.org/3/search/tv?api_key=5d40d3bc005b20aaf0126f65ab905344&query=" + s;
                    search_url.concat("&language=ko-KR&page=1");

                }
                strings = new String[] {search_url};
                AsyTask asyncTask = new AsyTask();
                asyncTask.execute(strings[0]);

                return false;
            }

            //검색 입력창에 새로운 텍스트가 들어갈때 마다 호출 - 여기선 필요 없음
            @Override
            public boolean onQueryTextChange(String s) {
                //Log.d("Search", "keyword: " + s);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.action_search:
                //Toast.makeText(this, "action_search", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "앱설정-준비중", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_tv:
                String tvString = "https://api.themoviedb.org/3/tv/popular?api_key=5d40d3bc005b20aaf0126f65ab905344&language=ko-KR&page=1";
                String[] strings = {tvString};
                AsyTaskDrama asyncTask = new AsyTaskDrama();
                asyncTask.execute(strings[0]);
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                searchOption = 2;// 드라마
                return true;
            case R.id.action_movie:
                String movieString = "https://api.themoviedb.org/3/movie/upcoming?api_key=5d40d3bc005b20aaf0126f65ab905344&language=ko-KR&page=1";
                String[] strings2 = {movieString};
                AsyTask asyncTask2 = new AsyTask();
                asyncTask2.execute(strings2[0]);
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                searchOption = 1;// 영화
                return true;

            default:
                Toast.makeText(this, "default", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }


    public class AsyTask extends AsyncTask<String, Void, Movie[]>{


        ProgressDialog progressDlg = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDlg.setMessage("\t로딩중...");
            progressDlg.show();

            movieList.clear();

        }

        @Override
        protected Movie[] doInBackground(String... strings){

            Log.d("AsyncTask", "url : " + strings[0]);
            OkHttpClient cli = new OkHttpClient();
            Request request = new Request.Builder()
//                    .url("https://api.themoviedb.org/3/movie/upcoming?api_key=5d40d3bc005b20aaf0126f65ab905344&language=ko-KR&page=1")
                    .url(strings[0])
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
            adapter.notifyDataSetChanged();
        }



    }
////////////// 수정
    public class AsyTaskDrama extends AsyncTask<String, Void, Drama[]>{


        ProgressDialog progressDlg = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDlg.setMessage("\t로딩중...");
            progressDlg.show();

            movieList.clear();

        }

        @Override
        protected Drama[] doInBackground(String... strings){

            Log.d("AsyncTask", "url : " + strings[0]);
            OkHttpClient cli = new OkHttpClient();
            Request request = new Request.Builder()
//                    .url("https://api.themoviedb.org/3/movie/upcoming?api_key=5d40d3bc005b20aaf0126f65ab905344&language=ko-KR&page=1")
                    .url(strings[0])
                    .build();
            try{
                Response response = cli.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream()).getAsJsonObject().get("results");
                Drama[] posts = gson.fromJson(rootObject, Drama[].class);
                return posts;
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Drama[] result){
            super.onPostExecute(result);
            progressDlg.dismiss();
            if(result.length>0){
                for(Drama p : result){
                    dramaList.add(p);
                }
            }
            adapterDrama = new RecycleAdpDrama(MainActivity.this,dramaList);
            recyclerView.setAdapter(adapterDrama);
            adapterDrama.notifyDataSetChanged();
        }



    }

////////////////// 수정

}
