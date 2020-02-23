package com.burmesesubtitle.app;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.burmesesubtitle.app.MainActivity;
import com.burmesesubtitle.app.R;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.github.ybq.android.spinkit.style.ThreeBounce;


public class SplashscreenActivity extends AppCompatActivity {

    private int SPLASH_TIME = 2000;
    private ProgressBar progressBar;
    int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splashscreen);



        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress);
//        Sprite fadingCircle = new FadingCircle();
//        progressBar.setIndeterminateDrawable(fadingCircle);
        Sprite threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);


        Thread thred = new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
                startApp();
            }});
        thred.start();
    }
    public void doWork() {
        for (progress=20;progress<100;progress=progress+20){
            try {
                Thread.sleep(1000);
//                    progressBar.setProgress(progress);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }}
    }
    public void startApp(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
