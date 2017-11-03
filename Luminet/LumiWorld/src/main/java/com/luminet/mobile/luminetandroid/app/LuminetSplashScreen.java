package com.luminet.mobile.luminetandroid.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.luminet.mobile.luminetandroid.R;
import com.luminet.mobile.luminetandroid.activity.LuminetActivity;
import com.luminet.mobile.luminetandroid.api.DataService;
import com.luminet.mobile.luminetandroid.login.LoginMain;

/**
 * Created by chris on 2017/10/11.
 */

public class LuminetSplashScreen extends Activity{
    public static final String MyPREFERENCES = "LumiPrefs" ;
    String userNumber;
    String password;

    SharedPreferences sharedpreferences;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slash_logo);
        StartAnimations();
        sharedpreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }
    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.logo);
        iv.clearAnimation();
        iv.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                userNumber = sharedpreferences.getString("useId", null);
                password = sharedpreferences.getString("pWord", null);

                if(userNumber != null && password != null){
                    DataService dataService = new DataService(LuminetSplashScreen.this);
                    dataService.getNodeByRelationship();
                    Intent intent = new Intent(LuminetSplashScreen.this, LuminetActivity.class);
                    intent.putExtra("userId", userNumber);
                    startActivity(intent);

                }else{
                    Intent i = new Intent(getBaseContext(), LoginMain.class);
                    startActivity(i);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

}

