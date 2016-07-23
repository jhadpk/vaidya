package com.inmobi.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.inmobi.app.bloodbank.Check;
import com.inmobi.app.bloodbank.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.PushService;
import com.parse.SaveCallback;

public class Splash extends Activity {
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        StartAnimations();
        Thread background = new Thread() {
            public void run() {

                try {
                    Parse.initialize(Splash.this, "HfCpx4IZo1Eklb6h2oDQKdHJHQbaPTA8Uegndn8N",
                            "V0jYWCNzkefVAQOFxCVHPhZ9iWyW0BziPuEeWFNk");
                    ParseInstallation.getCurrentInstallation().saveInBackground();
                    System.out.println("Parse version is "+ParseInstallation.getCurrentInstallation().saveInBackground());
                    Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);
                    ParsePush.subscribeInBackground("", new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.d("com.parse.push", "You are successfully subscribed to the broadcast channel.");
                            } else {
                                Log.e("com.parse.push", "failed to subscribe for push", e);
                            }
                        }
                    });
                    sleep(3*1000);
                    Intent i=new Intent(getBaseContext(),Check.class);
                    startActivity(i);
                    finish();

                } catch (Exception e) {

                }
            }
        };


        background.start();
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.linearlayout);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splashimg);
        iv.clearAnimation();
        iv.startAnimation(anim);

    }


}