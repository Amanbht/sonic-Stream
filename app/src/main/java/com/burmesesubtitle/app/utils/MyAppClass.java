package com.burmesesubtitle.app.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.ads.AudienceNetworkAds;
import com.onesignal.OneSignal;
import com.burmesesubtitle.app.Config;
import com.burmesesubtitle.app.NotificationClickHandler;
import com.burmesesubtitle.app.network.RetrofitClient;
import com.burmesesubtitle.app.network.apis.AppConfigApi;
import com.burmesesubtitle.app.network.model.AppConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyAppClass extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext=this;

        // Initialize the Audience Network SDK
        AudienceNetworkAds.initialize(this);

        // OneSignal Initialization
        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new NotificationClickHandler(mContext))
                //.inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        SharedPreferences preferences=getSharedPreferences("push",MODE_PRIVATE);
        if (preferences.getBoolean("status",true)){
            OneSignal.setSubscription(true);
        }else {
            OneSignal.setSubscription(false);
        }

        //

        if (!getFirstTimeOpenStatus()) {
            if (Config.DEFAULT_DARK_THEME_ENABLE) {
                changeSystemDarkMode(true);
            } else {
                changeSystemDarkMode(false);
            }
            saveFirstTimeOpenStatus(true);
        }

        getAppConfigInfo();

    }

    public void changeSystemDarkMode(boolean dark) {

        SharedPreferences.Editor editor = getSharedPreferences("push", MODE_PRIVATE).edit();
        editor.putBoolean("dark", dark);
        editor.apply();

    }

    public void saveFirstTimeOpenStatus(boolean dark) {

        SharedPreferences.Editor editor = getSharedPreferences("push", MODE_PRIVATE).edit();
        editor.putBoolean("firstTimeOpen", true);
        editor.apply();

    }

    public boolean getFirstTimeOpenStatus() {
        SharedPreferences preferences=getSharedPreferences("push",MODE_PRIVATE);
        return preferences.getBoolean("firstTimeOpen",false);
    }

    public static Context getContext(){
        return mContext;
    }

    public void getAppConfigInfo() {

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        AppConfigApi appConfigApi = retrofit.create(AppConfigApi.class);
        Call<AppConfig> call = appConfigApi.getAppConfig(Config.API_KEY);
        call.enqueue(new Callback<AppConfig>() {
            @Override
            public void onResponse(Call<AppConfig> call, Response<AppConfig> response) {

                if (response.code() == 200) {

                    AppConfig appConfig = response.body();

                    // save app config info to shared preference
                    saveAppConfigInfo(appConfig);

                }

            }

            @Override
            public void onFailure(Call<AppConfig> call, Throwable t) {
                t.printStackTrace();

                SharedPreferences preferences = getSharedPreferences("appConfig", MODE_PRIVATE);
                Constants.NAV_MENU_STYLE = preferences.getString("navMenuStyle", "grid");
                Constants.IS_ENABLE_PROGRAM_GUIDE = preferences.getBoolean("enableProgramGuide", false);
                Constants.IS_LOGIN_MANDATORY = preferences.getBoolean("loginMandatory", false);
                Constants.IS_GENRE_SHOW = preferences.getBoolean("genreShow", true);
                Constants.IS_COUNTRY_SHOW = preferences.getBoolean("countryShow", true);
                Constants.IS_ENABLE_AD = preferences.getString(Constants.ADS_ENABLE, "");
                Constants.ACTIVE_AD_NETWORK = preferences.getString(Constants.MOBILE_AD_NETWORK, "");
            }
        });

    }

    public void saveAppConfigInfo(AppConfig appConfig) {

        Constants.NAV_MENU_STYLE = appConfig.getMenu();
        Constants.IS_ENABLE_PROGRAM_GUIDE = appConfig.getProgramGuideEnable();
        Constants.IS_LOGIN_MANDATORY = appConfig.getMandatoryLogin();
        Constants.IS_GENRE_SHOW = appConfig.getGenreVisible();
        Constants.IS_COUNTRY_SHOW = appConfig.getCountryVisible();
        Constants.ACTIVE_AD_NETWORK = appConfig.getMobileAdsNetwork();
        Constants.IS_ENABLE_AD = appConfig.getAdsEnable();

        SharedPreferences.Editor editor = getSharedPreferences(Constants.APP_CONFIG, MODE_PRIVATE).edit();
        editor.putString(Constants.NAV_STYLE, appConfig.getMenu());
        editor.putBoolean(Constants.PROGRAM_UIDE_ENABLE, appConfig.getProgramGuideEnable());
        editor.putBoolean(Constants.LOGIN_MANDETORY, appConfig.getMandatoryLogin());
        editor.putBoolean(Constants.GENRE_SHOW, appConfig.getGenreVisible());
        editor.putBoolean(Constants.COUNTRY_SHOW, appConfig.getCountryVisible());
        editor.putString(Constants.ADS_ENABLE, appConfig.getAdsEnable());
        editor.putString(Constants.MOBILE_AD_NETWORK, appConfig.getMobileAdsNetwork());
        editor.putString(Constants.ADMOB_APP_ID, appConfig.getAdmobAppId());
        editor.putString(Constants.ADMOB_BANNER_ID, appConfig.getAdmobBannerAdsId());
        editor.putString(Constants.ADMOB_INTERESTITIAL_ID, appConfig.getAdmobInterstitialAdsId());
        editor.putString(Constants.FAN_NATIVE_AD_ID, appConfig.getFanNativeAdsPlacementId());
        editor.putString(Constants.FAN_BANNER_AD_ID, appConfig.getFanBannerAdsPlacementId());
        editor.putString(Constants.FAN_INTERESTITIAL_AD_ID, appConfig.getFanInterstitialAdsPlacementId());
        editor.putString(Constants.STARTAPP_APP_ID, appConfig.getStartappAppId());
        editor.apply();
        editor.commit();

    }

}
