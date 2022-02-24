package com.mys.www.webdemo.view;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.mys.www.webdemo.R;

public class WebActivity extends AppCompatActivity {

    public String url, title;
    private WebView mWebView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getIntent().getStringExtra("landscape") != null) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_web);
        super.onCreate(savedInstanceState);
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        if (title == null) {
            title = "";
        }
        if (url == null) {
            finish();
            return;
        }
//        String androidId = "" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
//        if (!url.startsWith("file")) {
//            if (url.contains("?")) {
//                url = url + "&devicesId=" + androidId;
//            } else {
//                url = url + "?devicesId=" + androidId;
//            }
//        }
        Log.e("-----1", "open web url=" + url);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            if (getIntent().getStringExtra("landscape") != null) {
                actionBar.hide();
            }
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setDisplayShowHomeEnabled(false);
        }
        mWebView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        mWebView.addJavascriptInterface(new JSInterface(new JSInterface.JavascriptCallback() {
            @Override
            public void onHtmlClickUrl(String url) {
                try {
                    runOnUiThread(() -> {
                        if (url != null) {
                            mWebView.loadUrl(url);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onEvent(String key) {
                Log.e("-----1", "AndroidEM key=" + key);
            }

            @Override
            public void customEvent(String eventUrl, String eventValue) {
                Log.e("-----1", "AndroidEM eventUrl=" + eventUrl + ";eventValue=" + eventValue);
            }
        }), "AndroidEM");
        mWebView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(Uri.parse(url), mimetype);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar.setProgress(newProgress);//设置进度值
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.e("-----1", consoleMessage.message());
                return true;
            }
        });
        mWebView.setWebViewClient(new WBViewClient(getApplicationContext()) {

            @Override
            public void openNewActivity(Intent intent) {
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("-----1", "open new activity error e = " + e);
                }
            }
        });
        if (url != null) {
            mWebView.loadUrl(url);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backClicked();
    }

    public void backClicked() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

}
