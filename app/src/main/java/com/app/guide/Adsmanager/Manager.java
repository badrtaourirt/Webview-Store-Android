package com.app.guide.Adsmanager;

import android.app.Activity;

public class Manager {

    Facebook fb = new Facebook();
    Applovin applovin = new Applovin();

    public static String adnetwork="fan";
    public void showbanner(Activity activity){

        switch (adnetwork){

            case "fan":

                fb.showbanner(activity);

                break;

            case "applovin"  :

                applovin.createBannerAd(activity);

                break;
        }

    }

    public void showinter(Activity activity){

        switch (adnetwork){

            case "fan":

                fb.showinterfb();
                break;
            case "applovin"  :

                applovin.showinter(activity);
                break;
        }

    }


    public void show_native(Activity activity){

        switch (adnetwork){

            case "fan":

                fb.loadNativeAd(activity);

                break;
            case "applovin"  :

                applovin.createNativeAd(activity);
                break;
        }

    }
}
