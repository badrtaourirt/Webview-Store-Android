package com.app.guide.Adsmanager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.app.guide.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;

import java.util.ArrayList;
import java.util.List;

public class Facebook {

    public static InterstitialAd interstitialAd;

    private AdView adView;

    private NativeAd nativeAd;

    String TAG = "badrfb";

    public static String  Fb_banner="";

    public static String Fb_inter="";

    public static String Fb_native="";
    public void fbinit(Activity activity) {
        interstitialAd = new InterstitialAd(activity, Fb_inter);
        interstitialAd.loadAd();
    }

    public void showinterfb() {

        if (interstitialAd.isAdLoaded()) {

            interstitialAd.show();
        } else {

            interstitialAd.loadAd();
        }
    }

    public void showbanner(Activity activity) {

        // Instantiate an AdView object.
// NOTE: The placement ID from the Facebook Monetization Manager identifies your App.
// To get test ads, add IMG_16_9_APP_INSTALL# to your placement id. Remove this when your app is ready to serve real ads.

        adView = new AdView(activity, "IMG_16_9_APP_INSTALL#"+Fb_banner, AdSize.BANNER_HEIGHT_50);

// Find the Ad Container
        FrameLayout adContainer = (FrameLayout) activity.findViewById(R.id.bannerContainer);

// Add the ad view to your activity layout
        adContainer.addView(adView);

// Request an ad
        adView.loadAd();

        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {


            @Override
            public void onError(Ad ad, AdError adError) {

                Log.d("badrfb", "inter fb erorr  ");

                interstitialAd.loadAd();

            }

            @Override
            public void onAdLoaded(Ad ad) {

                Log.d("badrfb", "inter fb load  ");
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }

            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {

                interstitialAd.loadAd();

            }
        };

    }





    private NativeAdLayout nativeAdLayout;


    public LottieAnimationView loti;

    public void loadNativeAd(Activity activity) {

        nativeAd = new NativeAd(activity, Fb_native);

        loti = activity.findViewById(R.id.animationView);


        NativeAdListener nativeAdListener = new NativeAdListener() {

            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Race condition, load() called again before last ad was displayed
                if (nativeAd == null || nativeAd != ad) {

                    return;
                }
                // Inflate Native Ad into Container
                inflateAd(nativeAd,activity);
                loti.setVisibility(View.GONE);

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };

        // Request an ad
        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());
    }

    private LinearLayout adViewl;

    private void inflateAd(NativeAd nativeAd , Activity activity) {

        nativeAd.unregisterView();
        // Add the Ad view into the ad container.
        nativeAdLayout = activity.findViewById(R.id.native_ad_container);
        LayoutInflater inflater = LayoutInflater.from(activity);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        adViewl = (LinearLayout) inflater.inflate(R.layout.native_ad_layout, nativeAdLayout, false);
        nativeAdLayout.addView(adViewl);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = activity.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(activity, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        MediaView nativeAdIcon = activity.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = activity.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = activity.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = activity.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = activity.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = activity.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = activity.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                adViewl, nativeAdMedia, nativeAdIcon, clickableViews);
    }
}
