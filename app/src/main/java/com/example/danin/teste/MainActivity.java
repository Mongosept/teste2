package com.example.danin.teste;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.OrientationEventListener;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //vars
    //private ArrayList<String> mNames = new ArrayList<>();
    //private ArrayList<String> mImagesURL = new ArrayList<>();
    //private Context mContext;
    List<Movie> lstMovie;
    RecyclerView myrv;
    recyclerViewAdapter myAdapter;
    Context present=this;
    MainActivity activity;
   // OrientationEventListener mOrientationListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstMovie = new ArrayList<>();

        new DataLoader().execute();
    }

   /* private void getImages(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImagesURL.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        mNames.add("Havasu Falls");

        mImagesURL.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mNames.add("Trondheim");

        mImagesURL.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mNames.add("Portugal");

        mImagesURL.add("https://i.redd.it/j6myfqglup501.jpg");
        mNames.add("Rocky Mountain National Park");


        mImagesURL.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Mahahual");

        mImagesURL.add("https://i.redd.it/k98uzl68eh501.jpg");
        mNames.add("Frozen Lake");


        mImagesURL.add("https://i.redd.it/glin0nwndo501.jpg");
        mNames.add("White Sands Desert");

        mImagesURL.add("https://i.redd.it/obx4zydshg601.jpg");
        mNames.add("Austrailia");

        mImagesURL.add("https://i.imgur.com/ZcLLrkY.jpg");
        mNames.add("Washington");

        initRecyclerView();

    } */

    class DataLoader extends AsyncTask<Void, Void, String> {

        private Exception exception;

        private String API_KEY="b84f68ff07bfbb69c9b82d27f4b7d0b5";

        protected void onPreExecute() {
            //progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(Void... urls) {

            try {
                URL url = new URL("https://api.themoviedb.org/3/movie/popular?api_key="+API_KEY+"&language=en-US&page=1");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }


            try {
                JSONObject result =  (JSONObject) new JSONTokener(response).nextValue();
                JSONArray list=result.getJSONArray("results");

                for (int i=0; i<5;i++){

                    String url=list.getJSONObject(i).getString("poster_path");
                    if(url!=null){
                        String poster_url = "http://image.tmdb.org/t/p/w185/"+url;
                        int id=list.getJSONObject(i).getInt("id");
                        String title=list.getJSONObject(i).getString("title");
                        String description=list.getJSONObject(i).getString("overview");
                        List<Integer> categories=new ArrayList<>();
                        JSONArray genders=list.getJSONObject(i).getJSONArray("genre_ids");
                        Log.d("v","array - "+genders);
                        for (int w=0; w<genders.length();w++){
                            categories.add(genders.getInt(w));
                        }

                        lstMovie.add(new Movie(id,title,categories,description,poster_url));

                    }



                }
                initRecyclerView();




            } catch (JSONException e) {
                e.printStackTrace();
            }




        }


    }







    private void initRecyclerView(){

        activity=this;
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        myrv=(RecyclerView) findViewById(R.id.recyclerview);
        //myrv.setLayoutManager(layoutManager);
        myrv.setLayoutManager(new GridLayoutManager(present,5,GridLayoutManager.VERTICAL, false));
        myAdapter = new recyclerViewAdapter(present,lstMovie);
        myrv.setItemAnimator(new DefaultItemAnimator());
        myrv.setAdapter(myAdapter);


       /* LinearLayoutManager LinearLayoutManger = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(LinearLayoutManger);
        recyclerViewAdapter adapter = new recyclerViewAdapter(this,);
        recyclerView.setAdapter(adapter);*/



    }

}
