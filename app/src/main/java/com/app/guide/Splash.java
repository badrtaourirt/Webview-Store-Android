package com.app.guide;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.app.guide.Adsmanager.Applovin;
import com.app.guide.Adsmanager.Facebook;
import com.app.guide.Adsmanager.Manager;
import com.app.guide.model.First_model;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.facebook.ads.AudienceNetworkAds;

import org.json.JSONArray;
import org.json.JSONObject;


public class Splash extends AppCompatActivity {

    public static boolean adblock=false;
    public Activity activity;
    public static boolean adblock_active=false;
    public static String subCategoryTitle , title ,jsonStr, image , desc,jsonStr1,ads_type,applovin_banner,applovin_inter,applovin_native;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        AudienceNetworkAds.initialize(this);

        // Make sure to set the mediation provider value to "max" to ensure proper functionality
        AppLovinSdk.getInstance( this ).setMediationProvider( "max" );
        AppLovinSdk.initializeSdk( this, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration)
            {
                // AppLovin SDK is initialized, start loading ads
            }
        } );
        activity =this ;
        new GetOutJsonData().execute();
    }


    @SuppressLint("StaticFieldLeak")
    private class GetOutJsonData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            jsonStr = sh.makeServiceCall("https://tripsagency.yassminasud.com/me/game.json");
            jsonStr1 = sh.makeServiceCall("https://tripsagency.yassminasud.com/me/appads.json");

            if (jsonStr1 != null) {
                try {
                    JSONArray jsonObj = new JSONArray(jsonStr1);
                        JSONObject app = jsonObj.getJSONObject(0);
                        Manager.adnetwork = app.getString("ads_type");
                        Applovin.applovin_banner = app.getString("applovin_banner");
                        Applovin.applovin_inter = app.getString("applovin_inter");
                        Applovin.applovin_native = app.getString("applovin_native");
                        Facebook.Fb_banner = app.getString("Fb_banner");
                        Facebook.Fb_inter=app.getString("Fb_inter");
                        Facebook.Fb_native=app.getString("Fb_native");

                }catch (Exception e){}

            }


            if (jsonStr != null && jsonStr1 != null) {
                try {

                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            goNextActivity();
                        }
                    });


                }catch (Exception e){}

            }
             return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

    private void goNextActivity() {
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent  myintent = new Intent(getApplicationContext(), FirstRecycle.class);

                startActivity(myintent);
                finish();
            }
        }, 1000);

    }
}