package com.project2_newsapp;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class NewsActivity extends AppCompatActivity {

    private WebView webdata;
    private ImageView imgDetail;
    private TextView txtTitleDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // Inisialisasi view
        imgDetail = findViewById(R.id.imgDetail);
        txtTitleDetail = findViewById(R.id.txtTitleDetail);
        webdata = findViewById(R.id.webData);

        // Menampilkan judul berita
        txtTitleDetail.setText(Constans.TITLE);

        // Menampilkan gambar berita
        Picasso.get()
                .load(Constans.IMAGE)
                .into(imgDetail);

        // Menampilkan deskripsi berita dalam WebView
        String htmlContent = "<html><head><style>img { max-width: 90%; width: auto; height: auto; }</style></head><body>"
                + Constans.DESCRIPTION +
                "</body></html>";

        webdata.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
    }
}