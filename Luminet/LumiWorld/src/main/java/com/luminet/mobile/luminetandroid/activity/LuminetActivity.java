package com.luminet.mobile.luminetandroid.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.luminet.mobile.luminetandroid.R;
import com.luminet.mobile.luminetandroid.adapter.LuminetFragmentPagerAdapter;
import com.luminet.mobile.luminetandroid.screenViewsFragments.ConnectFeedsFragment;

/**
 * Created by chris on 2017/10/17.
 */

public class LuminetActivity extends AppCompatActivity{
    private ViewPager mPager;
    PagerSlidingTabStrip strip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        setContentView(R.layout.activity_main);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
        //        LinearLayout.LayoutParams.WRAP_CONTENT,
        //        LinearLayout.LayoutParams.WRAP_CONTENT);
        //int viewPagerHeight = viewPager.getHeight() - 50;
       // int viewPagerWidth = viewPager.getHeight() - 5;
        //setting margins around imageview
        //layoutParams.height= viewPagerHeight; //left, top, right, bottom

        //adding attributes to the imageview
        //viewPager.setLayoutParams(layoutParams);
        viewPager.setAdapter(new LuminetFragmentPagerAdapter(getSupportFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
        Bundle extras = getIntent().getExtras();
        String userId = null;
        if (extras != null) {
            userId = extras.getString("userId");
        }
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        // set Fragmentclass Arguments
        ConnectFeedsFragment cff = new ConnectFeedsFragment();
        cff.setArguments(bundle);
    }

    public static final String TAG = LuminetActivity.class.getSimpleName();
}
