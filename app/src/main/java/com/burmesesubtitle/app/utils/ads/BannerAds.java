package com.burmesesubtitle.app.utils.ads;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.ixidev.gdpr.GDPRChecker;
import com.burmesesubtitle.app.utils.Constants;

import static android.content.Context.MODE_PRIVATE;

public class BannerAds {
    public static void ShowBannerAds(Context context, RelativeLayout mAdViewLayout) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.APP_CONFIG, MODE_PRIVATE);

        AdView mAdView = new AdView(context);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(preferences.getString(Constants.ADMOB_BANNER_ID, ""));
        AdRequest.Builder builder = new AdRequest.Builder();
        GDPRChecker.Request request = GDPRChecker.getRequest();

        if (request == GDPRChecker.Request.NON_PERSONALIZED) {
            // load non Personalized ads
            Bundle extras = new Bundle();
            extras.putString("npa", "1");
            builder.addNetworkExtrasBundle(AdMobAdapter.class, extras);
        } // else do nothing , it will load PERSONALIZED ads
        mAdView.loadAd(builder.build());
        mAdViewLayout.addView(mAdView);
        }

}
