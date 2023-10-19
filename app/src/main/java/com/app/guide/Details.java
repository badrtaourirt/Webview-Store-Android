package com.app.guide;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.guide.Adsmanager.Applovin;
import com.app.guide.Adsmanager.Facebook;
import com.app.guide.Adsmanager.Manager;
import com.bumptech.glide.Glide;
import com.facebook.ads.AdSettings;

public class Details extends AppCompatActivity {

    ImageView close, image_details, play_btn;


    public String gamelinks,image_detail;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        close=findViewById(R.id.closedetails);

        image_details = findViewById(R.id.image_details);
        AdSettings.addTestDevice("1b5742e5-b6c3-4bd4-81a7-c592016cce77");

        play_btn= findViewById(R.id.play_btn);
        gamelinks = getIntent().getStringExtra("gamelink");
        image_detail = getIntent().getStringExtra("image_detail");

        Glide
                .with(this)
                .load(image_detail)
                .centerCrop()
                .into(image_details);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startNextActivity();

            }
        });


    }

    private void startNextActivity() {


        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("gamelink", gamelinks);
        intent.putExtra("image_detail", image_detail);
        startActivity(intent);


    }
}