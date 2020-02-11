package com.oxoo.spagreen.utils.ads;

import android.content.Context;
import android.content.SharedPreferences;

import android.util.Log;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.oxoo.spagreen.utils.Constants;


import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class PopUpAds {

    public static void ShowInterstitialAds(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.APP_CONFIG, MODE_PRIVATE);

        final InterstitialAd mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(preferences.getString(Constants.ADMOB_INTERESTITIAL_ID, ""));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

                Random rand = new Random();
                int i = rand.nextInt(10)+1;

                if (i%2==0){
                    mInterstitialAd.show();
                }
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);

            }
        });

        }
}
