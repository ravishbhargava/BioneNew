package com.bione.ui.dashboard.paymentreceipt;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.ui.base.BaseActivity;
import com.bione.utils.CommonUtil;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.kbeanie.multipicker.utils.LogUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PaymentReceiptViewActivity extends BaseActivity {

    private AppCompatTextView tvShare;
    private String link = "https://www.bione.in/terms-of-service";
    private String pdfUrl = "";
    private PDFView pdfView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_receipt_view);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            link = extras.getString("link");
        }
////        link = "https://lims.bione.in/pdfreceipts/bione_100061.pdf";
//        WebView webView = findViewById(R.id.webView);
//        String myPdfUrl = link;
//        String url = "http://docs.google.com/gview?embedded=true&url=" + myPdfUrl;
//
//        link = url;
//
//        Log.i("TAG", "Opening PDF: " + link);
//        link = url;
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl(link);
//
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//
//        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
//
//        showLoading();
//        webView.setWebViewClient(new WebViewClient() {
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Log.i("", "Processing webview url click...");
//                view.loadUrl(url);
//                hideLoading();
//                return true;
//            }
//
//            public void onPageFinished(WebView view, String url) {
//                Log.i("", "Finished loading URL: " + url);
//                hideLoading();
//            }
//
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                Log.e("", "Error: " + description);
//                hideLoading();
//                showErrorMessage(description);
//            }
//        });
//        webView.loadUrl(link);

        pdfView = findViewById(R.id.pdfView);
        tvShare = findViewById(R.id.tvShare);
        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtil.sendEmail(PaymentReceiptViewActivity.this, link);
            }
        });

        openUrl();
    }

    public void openUrl() {
        showLoading();
        pdfUrl = "";
        pdfUrl = link;

        try {
            new RetrievePdfStream().execute(pdfUrl);
        } catch (Exception e) {
            hideLoading();
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
                } else {
                    errorMessage();
                }
            } catch (IOException e) {
                e.printStackTrace();
                errorMessage();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream)
                    .onError(throwable -> LogUtils.e("", "Error in opening PDF"))
                    .onError(new OnErrorListener() {
                        @Override
                        public void onError(Throwable t) {
                            errorMessage();
                            hideLoading();
                        }
                    })
                    .onPageError(new OnPageErrorListener() {
                        @Override
                        public void onPageError(int page, Throwable t) {
                            errorMessage();
                            hideLoading();
                        }
                    })
                    .onLoad(new OnLoadCompleteListener() {
                        @Override
                        public void loadComplete(int nbPages) {
                            hideLoading();
                        }
                    })
                    .load();

        }
    }

    private void errorMessage() {
        Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

    }
}
