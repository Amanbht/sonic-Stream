package com.burmesesubtitle.app.utils.ads;

import android.content.Context;
import android.widget.RelativeLayout;
import com.startapp.android.publish.ads.banner.Banner;


public class StartappAds {

    public static void showBannerAd(Context context, final RelativeLayout adview){

        Banner startAppBanner = new Banner(context);
        RelativeLayout.LayoutParams bannerParameters =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        bannerParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
        bannerParameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        // Add to main Layout
        adview.addView(startAppBanner, bannerParameters);

    }
}
