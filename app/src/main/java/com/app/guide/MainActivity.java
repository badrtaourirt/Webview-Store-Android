package com.app.guide;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    WebView webview ;

    public  String gamelinks;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdBlocker.init(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        gamelinks = getIntent().getStringExtra("gamelink");
        webview = findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setDatabaseEnabled(true);
        webview.getSettings().setGeolocationEnabled(true);

        webview.getSettings().setSupportZoom( true );
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);

        webview.getSettings().setAllowFileAccessFromFileURLs(true);
        webview.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webview.setWebViewClient(new MyBrowser() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true; // Disable all links
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                //Log.d("IronDev.Ma", "Loading resource: " + url);

                if (url.contains("logo1") || url.contains("logo2")) {
                    Log.v("IronDev.Ma", "Replacing [" + url + "] with [R.raw.tmp_replacement]");
                    ContentResolver contentResolver = getContentResolver();
                    return new WebResourceResponse(contentResolver.getType(Uri.parse(url)), "UTF-8", getResources().openRawResource(R.raw.logo));
                }

                if (url.contains("funny_pet_haircut.js")) {
                    Log.v("IronDev.Ma", "Replacing [" + url + "] with [R.raw.tmp_replacement]");
                    ContentResolver contentResolver = getContentResolver();
                    return new WebResourceResponse(contentResolver.getType(Uri.parse(url)), "UTF-8", getResources().openRawResource(R.raw.hacked_js_file));
                }
                return super.shouldInterceptRequest(view, url);
            }
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

                if (request.getUrl().getEncodedPath().contains("logo1") || request.getUrl().getEncodedPath().contains("logo2")) {
                    Log.v("IronDev.Ma", "Replacing [" + request.getUrl() + "] with [R.raw.tmp_replacement]");
                    ContentResolver contentResolver = getContentResolver();
                    return new WebResourceResponse(contentResolver.getType(request.getUrl()), "UTF-8", getResources().openRawResource(R.raw.logo));
                }

                if (request.getUrl().getEncodedPath().contains("jsfile")) {
                    Log.v("IronDev.Ma", "Replacing [" + request.getUrl() + "] with [R.raw.tmp_replacement]");
                    ContentResolver contentResolver = getContentResolver();
                    return new WebResourceResponse(contentResolver.getType(request.getUrl()), "UTF-8", getResources().openRawResource(R.raw.hacked_js_file));
                }
                return super.shouldInterceptRequest(view, request);
            }




            @Override
            public void onLoadResource(WebView view, String url) {


                super.onLoadResource(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view,String url) {

               /* if(Objects.equals(url, disable_link1)||Objects.equals(url, disable_link2)||Objects.equals(url, disable_link3)||Objects.equals(url, disable_link4)) {
                    url = gameurl;
                    view.loadUrl(url);

                } */
                CookieSyncManager.createInstance(webview.getContext());
                CookieManager cookieManager = CookieManager.getInstance();
                //cookieManager.removeSessionCookie();

                cookieManager.setCookie(gamelinks,"cookies-state=accepted");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    cookieManager.flush();
                } else {
                    CookieSyncManager.getInstance().sync();
                }


                super.onPageFinished(view,url);
            }


        });

        webview.loadUrl(gamelinks);



    }

    public class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            return true;
        }

        private Map<String, Boolean> loadedUrls = new HashMap<>();
        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            boolean ad;
            if (!loadedUrls.containsKey(url)) {
                ad = AdBlocker.isAd(url);
                loadedUrls.put(url, ad);
            } else {
                ad = loadedUrls.get(url);
            }
            return ad ? AdBlocker.createEmptyResource() :
                    super.shouldInterceptRequest(view, url);
        }
    }


    @Override
    protected void onStart() {


        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}