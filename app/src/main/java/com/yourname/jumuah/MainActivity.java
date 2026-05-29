package com.yourname.jumuah;

import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private AdView adView;
    private boolean webViewReady = false;

    private static final String APP_URL =
        "https://jumuah-blessings--flalimustapha16.replit.app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(() -> !webViewReady);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, status -> {});
        adView = findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().build());

        webView = findViewById(R.id.webView);
        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);
        s.setLoadWithOverviewMode(true);
        s.setUseWideViewPort(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView v, WebResourceRequest r) {
                v.loadUrl(r.getUrl().toString());
                return true;
            }
            @Override
            public void onPageFinished(WebView v, String url) {
                super.onPageFinished(v, url);
                webViewReady = true;
            }
        });

        webView.loadUrl(APP_URL);
    }

    @Override protected void onPause()   { if (adView != null) adView.pause();   super.onPause(); }
    @Override protected void onResume()  { super.onResume(); if (adView != null) adView.resume(); }
    @Override protected void onDestroy() { if (adView != null) adView.destroy(); super.onDestroy(); }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }
}
