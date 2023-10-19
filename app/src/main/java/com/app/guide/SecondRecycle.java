package com.app.guide;


import static com.app.guide.Splash.subCategoryTitle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.app.guide.Adsmanager.Applovin;
import com.app.guide.Adsmanager.Facebook;
import com.app.guide.Adsmanager.Manager;
import com.app.guide.adapter.RecycleTow_adapter;
import com.app.guide.model.Second_model;
import com.applovin.mediation.nativeAds.adPlacer.MaxAdPlacerSettings;
import com.applovin.mediation.nativeAds.adPlacer.MaxRecyclerAdapter;
import com.facebook.ads.AdSettings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class SecondRecycle extends AppCompatActivity {

    ArrayList<Second_model> destails;
    RecyclerView recyclerView ;

    Facebook fb = new Facebook();
    ImageView close ;

    TextView appname;

    Activity activity;
    public  static String titled,image1,gamelink,image_detail;

    Applovin applovin= new Applovin();
    Manager mg = new Manager();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_recycle);
        activity = this;
        MaxAdPlacerSettings settings = new MaxAdPlacerSettings( "64a5742e9ff620b2" );
        settings.setRepeatingInterval( 2 );
        // hyd hadi rar tbri dir ads real

        AdSettings.addTestDevice("1b5742e5-b6c3-4bd4-81a7-c592016cce77");
        //        applovin.createInterstitialAd(this);
        recyclerView = findViewById(R.id.secondrecycle);
        close = findViewById(R.id.closes);
        destails = new ArrayList<>();
        mg.showbanner(this);
        appname= findViewById(R.id.appname);
        String btitle = getIntent().getStringExtra("btitle");
        appname.setText(btitle);
        String jsonArrayString = getIntent().getStringExtra("jsonArray");
        try {
            JSONArray myjsonarray = new JSONArray(jsonArrayString);

            for (int i = 0; i < myjsonarray.length(); i++) {
                JSONObject sousCategorieObject = myjsonarray.getJSONObject(i);
                subCategoryTitle = sousCategorieObject.getString("titles");
                String gamelink = sousCategorieObject.getString("gamelink");
                String image_detail = sousCategorieObject.getString("image_detail");
                String imageUrl = sousCategorieObject.getString("img");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    destails.add(new Second_model(imageUrl, gamelink, subCategoryTitle,image_detail));
                }

            }

        }catch (Exception e){


        }

        RecycleTow_adapter adapter = new RecycleTow_adapter(destails, getApplicationContext());
        MaxRecyclerAdapter adAdapter = new MaxRecyclerAdapter( settings, adapter, this );

        recyclerView.setAdapter(adAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.updateData(destails);
        adAdapter.loadAds();



        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                mg.showinter(activity);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {

                int adjustedPosition = position / 2;

                if (adjustedPosition >= 0 && adjustedPosition < destails.size()) {

                    titled = destails.get(adjustedPosition).getTitles();
                    gamelink = destails.get(adjustedPosition).getDescription();
                    image_detail = destails.get(adjustedPosition).getDescription2();
                    image1 = destails.get(adjustedPosition).getImages();
                   mg.showinter(activity);
                    startNextActivity();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });

    }

    private void startNextActivity() {


        Intent intent = new Intent(this, Details.class);
        intent.putExtra("gamelink", gamelink);
        intent.putExtra("image_detail", image_detail);
        intent.putExtra("titled", titled);
        startActivity(intent);

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Save data to SharedPreferences when the activity is paused
        saveRecyclerViewData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void loadRecyclerViewData() {
        SharedPreferences sharedPreferences = getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);
        String jsonRecyclerViewData = sharedPreferences.getString("KEY_RECYCLERVIEW_DATA", null);

        if (!TextUtils.isEmpty(jsonRecyclerViewData)) {
            // Convert the JSON string back to a List of Second_model
            Gson gson = new Gson();
            destails = gson.fromJson(jsonRecyclerViewData, new TypeToken<List<Second_model>>() {}.getType());
        }
    }

    private void saveRecyclerViewData() {
        SharedPreferences sharedPreferences = getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Convert the List of Second_model to a JSON string
        Gson gson = new Gson();
        String jsonRecyclerViewData = gson.toJson(destails);

        editor.putString("KEY_RECYCLERVIEW_DATA", jsonRecyclerViewData);
        editor.apply();
    }
}