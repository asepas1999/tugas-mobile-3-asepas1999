package com.project2_newsapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    RecyclerView recData;
    NewsAdapter postAdapter;
    ArrayList<DataNews> newsList;
    ImageView imgCover;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgCover = findViewById(R.id.imgCover);
        recData = findViewById(R.id.recData);
        recData.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        recData.setLayoutManager(mLayoutManager);
        newsList = new ArrayList<>();
        getDataNews();
    }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("news.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public void getDataNews() {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray = jsonObject.getJSONArray("DATA_NEWS");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                DataNews developers = new DataNews();
                developers.title = jo.getString("title_news");
                developers.link_image = jo.getString("image_link");
                developers.description = jo.getString("link_news");
                newsList.add(developers);
            }
            Random r = new Random();
            int numberCover = r.nextInt(newsList.size());
            //Toast.makeText(this,"Link: " + newsList.get(numberCover).getLink_image(), Toast.LENGTH_LONG).show();
            Picasso.get().load(newsList.get(numberCover).getLink_image()).resize(1080, 600).into(imgCover, new Callback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(MainActivity.this, "Gambar berhasil dimuat!", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(MainActivity.this, "Gambar gagal dimuat!", Toast.LENGTH_LONG).show();
                }
            });
            imgCover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Constans.TITLE = newsList.get(numberCover).getTitle();
                    Constans.IMAGE = newsList.get(numberCover).getLink_image();
                    Constans.DESCRIPTION = newsList.get(numberCover).getDescription();
                    Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                    startActivity(intent);
                }
            });
            postAdapter = new NewsAdapter( newsList, MainActivity.this);
            recData.setAdapter(postAdapter);
        } catch (JSONException e) {
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}