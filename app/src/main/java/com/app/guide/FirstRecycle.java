package com.app.guide;

import static com.app.guide.Adsmanager.Facebook.interstitialAd;
import static com.app.guide.Splash.desc;
import static com.app.guide.Splash.image;
import static com.app.guide.Splash.jsonStr;
import static com.app.guide.Splash.title;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.app.guide.Adsmanager.Applovin;
import com.app.guide.Adsmanager.Facebook;
import com.app.guide.Adsmanager.Manager;
import com.app.guide.adapter.Rcycleone_Adapter;
import com.app.guide.model.First_model;
import com.facebook.ads.AdSettings;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class FirstRecycle extends AppCompatActivity {

    public static JSONArray sousCategorieArray;
    public static  String souscategoriestring,souscategoriestring_p,background_image,btitle;
    ArrayList<First_model> destails;
    RecyclerView recyclerView ;

    Activity activity;

    Applovin applovin = new Applovin();
    Facebook fb = new Facebook();

    Manager mg = new Manager();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_recycle);

        applovin.createInterstitialAd(this);
        activity = this ;
        fb.fbinit(this);
        AdSettings.addTestDevice("1b5742e5-b6c3-4bd4-81a7-c592016cce77");
        mg.showbanner(this);
        mg.show_native(this);
        recyclerView = findViewById(R.id.recycle);
        destails = new ArrayList<>();

        if (jsonStr != null) {
            try {
                JSONArray jsonObj = new JSONArray(jsonStr);
                for(int i= 0; i<jsonObj.length();i++ ){
                    JSONObject app = jsonObj.getJSONObject(i);
                    title = app.getString("title");
                    image = app.getString("image");
                    desc = app.getString("desc");
                    background_image= app.getString("background_image");
                    sousCategorieArray = app.getJSONArray("sous_categorie");
                    souscategoriestring = sousCategorieArray.toString();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        destails.add(new First_model(image, desc, title,souscategoriestring,background_image));
                    }
                }

            }catch (Exception e){}

        }

        Rcycleone_Adapter adapter = new Rcycleone_Adapter(destails);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.updateData(destails);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                souscategoriestring_p = destails.get(position).getMyjsonarray();
                btitle = destails.get(position).getTitle();
                mg.showinter(activity);
                startNextActivity();
            }
        });


    }
    private void startNextActivity() {
        Intent intent = new Intent(this, SecondRecycle.class);
        intent.putExtra("jsonArray", souscategoriestring_p);
        intent.putExtra("btitle", btitle);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

    }

    @Override
    protected void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }
}