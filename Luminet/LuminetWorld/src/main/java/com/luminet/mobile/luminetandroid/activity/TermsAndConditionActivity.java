package com.luminet.mobile.luminetandroid.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.luminet.mobile.luminetandroid.R;

import okhttp3.OkHttpClient;

/**
 * Created by chris on 2017/10/26.
 */

public class TermsAndConditionActivity extends AppCompatActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_layout);
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.loadUrl("file:///android_asset/termsAndConditions.html");

    }
}