package com.app.guide.Adsmanager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.app.guide.R;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.sdk.AppLovinSdkUtils;



public class Applovin implements MaxAdListener , MaxAdViewAdListener {
    String Tag ="badrking";

    public static String  applovin_banner="";

    public static String applovin_inter="";

    public static String applovin_native="";

    private MaxAdView adView;

    private MaxInterstitialAd interstitialAd;
    private int retryAttempt;

    private MaxNativeAdLoader nativeAdLoader;
    private MaxAd             nativeAd;
    public void createInterstitialAd(Activity activity)
    {

    }


    public void showinter(Activity activity){

        interstitialAd = new MaxInterstitialAd( applovin_inter, activity );
        interstitialAd.setListener( this );
        interstitialAd.loadAd();
        if ( interstitialAd.isReady() )
        {
            interstitialAd.showAd();
        }else{

            interstitialAd.loadAd();
        }
    }


    @SuppressLint("ResourceAsColor")
    public void createBannerAd(Activity activity)
    {
        adView = new MaxAdView( applovin_banner, activity );
        adView.setListener( this );

        // Stretch to the width of the screen for banners to be fully functional
        int width = ViewGroup.LayoutParams.MATCH_PARENT;

        // Get the adaptive banner height.
        int heightDp = MaxAdFormat.BANNER.getAdaptiveSize( activity ).getHeight();
        int heightPx = AppLovinSdkUtils.dpToPx( activity, heightDp );

        adView.setLayoutParams( new FrameLayout.LayoutParams( width, heightPx ) );
        adView.setExtraParameter( "adaptive_banner", "true" );
        adView.setLocalExtraParameter( "adaptive_banner_width", 400 );
        adView.getAdFormat().getAdaptiveSize( 400, activity ).getHeight(); // Set your ad height to this value

        // Set background or background color for banners to be fully functional

        ViewGroup rootView = activity.findViewById(R.id.bannerContainer );
        rootView.addView( adView );

        // Load the ad
        adView.loadAd();
    }


    public void createNativeAd(Activity activity)
    {
        FrameLayout nativeAdContainer = activity.findViewById( R.id.nativead );

        nativeAdLoader = new MaxNativeAdLoader( applovin_native, activity );
        nativeAdLoader.setNativeAdListener( new MaxNativeAdListener() {
            @Override
            public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad) {
                // Clean up any pre-existing native ad to prevent memory leaks.
                if (nativeAd != null) {
                    nativeAdLoader.destroy(nativeAd);
                }

                nativeAd = ad;

                // Add ad view to view.
                nativeAdContainer.removeAllViews();
                nativeAdContainer.addView(nativeAdView);
            }


            @Override
            public void onNativeAdLoadFailed(final String adUnitId, final MaxError error)
            {
                Log.d(Tag,"native load ");
            }

            @Override
            public void onNativeAdClicked(final MaxAd ad)
            {
                // Optional click callback
            }
        } );

        nativeAdLoader.loadAd();
        }



    @Override
    public void onAdLoaded(MaxAd maxAd) {

        Log.d(Tag,"inter load ");
        retryAttempt = 0;

    }

    @Override
    public void onAdDisplayed(MaxAd maxAd) {

    }

    @Override
    public void onAdHidden(MaxAd maxAd) {
        interstitialAd.loadAd();


    }

    @Override
    public void onAdClicked(MaxAd maxAd) {

    }

    @Override
    public void onAdLoadFailed(String s, MaxError maxError) {
        Log.d(Tag,"inter Failed ");
                interstitialAd.loadAd();
    }

    @Override
    public void onAdDisplayFailed(MaxAd maxAd, MaxError maxError) {
        interstitialAd.loadAd();

    }

    @Override
    public void onAdExpanded(MaxAd maxAd) {

    }

    @Override
    public void onAdCollapsed(MaxAd maxAd) {

    }
}