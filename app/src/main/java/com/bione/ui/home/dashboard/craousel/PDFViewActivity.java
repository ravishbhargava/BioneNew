package com.bione.ui.home.dashboard.craousel;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.model.CrouselData;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.schedulecall.CategorySelect;
import com.bione.utils.Log;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PDFViewActivity extends BaseActivity {

    private WebView webView;
    private LinearLayout bottom;
    private AppCompatImageView ivBack;
    private PDFView pdfView;
    private AppCompatTextView tvBook;
    private AppCompatTextView tvTitle;
    private String filename = "";
    private int position = 0;
    private String link = "https://www.bione.in/genetics";

    private String geneticType = "Genetic";

    private ArrayList<CrouselData> crouselDataArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);

        Bundle extras = getIntent().getExtras();


        if (extras != null) {
//            filename = extras.getString("pdfUrl");
            position = extras.getInt("position");
            filename = extras.getString("pdfUrl");
            crouselDataArrayList = extras.getParcelableArrayList("array");
            // and get whatever type user account id is
        }

        Log.d("position", " ------- " + position);
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        tvTitle = findViewById(R.id.tvTitle);
        tvBook = findViewById(R.id.tvBook);
        pdfView = findViewById(R.id.pdfv);
        webView = findViewById(R.id.webView);
        bottom = findViewById(R.id.bottom);

        if (filename != null) {
            if (!filename.equals("")) {
                webView.setVisibility(View.GONE);
                pdfView.setVisibility(View.VISIBLE);
                bottom.setVisibility(View.GONE);
                openUrl();
            }
        } else if (position == 2) {
            bottom.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
            pdfView.setVisibility(View.VISIBLE);
            pdfView.fromAsset("Bione-LongiFit.pdf").load();
            tvTitle.setText("LongiFit");

        }else if (position == 3) {
            bottom.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
            pdfView.setVisibility(View.VISIBLE);
            pdfView.fromAsset("Bione-Suspectibility.pdf").load();
            tvTitle.setText("Genetic Susceptibility Test");

        } else {

            webView.setVisibility(View.VISIBLE);
            pdfView.setVisibility(View.GONE);
            bottom.setVisibility(View.VISIBLE);

            if (position == 4) {
                link = "https://www.bione.in/longevity-plus-test";
                tvTitle.setText("Longevity Plus Test");
                geneticType = "Longevity";
            } else if (position == 1) {
                link = "https://www.bione.in/mymicrobiome-test";
                tvTitle.setText("MyMicroBiome Test");
                geneticType = "MyMicroBiome";
            } else if (position == 5) {
                link = "https://www.bione.in/genetics";
                tvTitle.setText("Clinical Genetic Test");
                geneticType = "Genetic";
            } else {
                link = "https://www.bione.in/";
                tvTitle.setText("Bione");
            }
            openWebview();
        }

        tvBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PDFViewActivity.this, CategorySelect.class);
                intent.putExtra("geneticType", geneticType);
                intent.putExtra("position", position);
                intent.putParcelableArrayListExtra("array", crouselDataArrayList);
                startActivity(intent);
            }
        });

//        openUrl();
//        openPdf();

    }

    private void openWebview() {
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

    public void openUrl() {
        String pdfUrl = "";
        if (filename.equals("")) {
            pdfUrl = "https://github.github.com/training-kit/downloads/github-git-cheat-sheet.pdf";
            //Toast.makeText(this, pdfUrl, Toast.LENGTH_SHORT).show();
        } else {
            pdfUrl = "https://www.bione.in/report/" + filename;
        }

        try {
            new RetrievePdfStream().execute(pdfUrl);
        } catch (Exception e) {
            Toast.makeText(this, "Failed to load Url :" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    class RetrievePdfStream extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());

                }
            } catch (IOException e) {
                return null;

            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).password("Bione@123").load();
        }
    }

    private void openPdf() {

        String pdfUrl = "";
        if (filename.equals("")) {
            pdfUrl = "https://github.github.com/training-kit/downloads/github-git-cheat-sheet.pdf";
            //Toast.makeText(this, pdfUrl, Toast.LENGTH_SHORT).show();
        } else {
            pdfUrl = "https://www.bione.in/report/" + filename;
        }

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(pdfUrl);

//        String url = "https://github.github.com/training-kit/downloads/github-git-cheat-sheet.pdf";
//        webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + url);


//        File file = new File(Environment.getExternalStorageDirectory(),
//                "Report.pdf");
//        Uri path = Uri.fromFile(file);
//        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
//        pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        pdfOpenintent.setDataAndType(path, "application/pdf");
//        try {
//            startActivity(pdfOpenintent);
//        }
//        catch (ActivityNotFoundException e) {
//
//        }
    }
}

