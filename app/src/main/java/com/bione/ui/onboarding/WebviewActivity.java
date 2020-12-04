package com.bione.ui.onboarding;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.bione.R;
import com.bione.ui.base.BaseActivity;
import com.bione.utils.Log;

public class WebviewActivity extends BaseActivity {

    private String link = "https://www.bione.in/terms-of-service";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            link = extras.getString("link");
        }

        WebView webView = findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        showLoading();
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("", "Processing webview url click...");
                view.loadUrl(url);
                hideLoading();
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.i("", "Finished loading URL: " + url);
                hideLoading();
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e("", "Error: " + description);
                hideLoading();
                showErrorMessage(description);
            }
        });
        webView.loadUrl(link);

    }

    @Override
    public void onClick(View view) {

    }
}
