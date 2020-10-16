package com.bione.ui.mymicrobiome.report;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.mymicrobiome.CategorySelect2;
import com.bione.utils.CommonUtil;
import com.bione.utils.Log;
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

public class ReportPDFViewActivity extends BaseActivity {

    private WebView webView;
    private LinearLayout bottom;
    private RelativeLayout relProfile;
    private AppCompatImageView ivBack;
    private AppCompatImageView ivShare;
    private PDFView pdfView;
    private AppCompatTextView tvBook;
    private AppCompatTextView tvTitle;
    private AppCompatTextView tvExtra;
    private String filename = "";

    private String link = "https://www.bione.in/genetics";

    private String barCode = "bar_code";
    private String password = "";
    public static String TAG = "AAA";
    private String pdfUrl = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);

        Bundle extras = getIntent().getExtras();


        if (extras != null) {
//            filename = extras.getString("pdfUrl");
            barCode = extras.getString("barCode");

            // and get whatever type user account id is
        }

        relProfile = findViewById(R.id.relProfile);
        ivBack = findViewById(R.id.ivBack);
        ivShare = findViewById(R.id.ivProfile);
        tvExtra = findViewById(R.id.tvExtra);
        tvTitle = findViewById(R.id.tvTitle);
        tvBook = findViewById(R.id.tvBook);
        pdfView = findViewById(R.id.pdfv);
        webView = findViewById(R.id.webView);
        bottom = findViewById(R.id.bottom);
        tvExtra.setVisibility(View.GONE);
        bottom.setVisibility(View.GONE);
//        relProfile.setVisibility(View.VISIBLE);

        tvBook.setText("Book Counselling");

        openDialog();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtil.sendEmail(getApplicationContext(), pdfUrl);
            }
        });
        tvBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(ReportPDFViewActivity.this, CategorySelect2.class);
                intent2.putExtra("fromFlow", "MyMicroBiome");
                intent2.putExtra("geneticType", "MyMicroBiome");
                startActivity(intent2);
//                sendEmail();
            }
        });

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
        showLoading();
        pdfUrl = "";
        pdfUrl = "https://www.bione.in/pub/media/reports/" + barCode + ".pdf";

        try {
            new RetrievePdfStream().execute(pdfUrl);
        } catch (Exception e) {
            hideLoading();
            Toast.makeText(this, "Failed to load Url :" + e.toString(), Toast.LENGTH_SHORT).show();
            openDialog();
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
//                    hideLoading();
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                } else {
//                    hideLoading();
                    errorMessage();
                    openDialog();
                }
            } catch (IOException e) {
//                hideLoading();
                e.printStackTrace();
                errorMessage();
                openDialog();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream)
                    .password(password)
                    .onError(throwable -> LogUtils.e(TAG, "Error in opening PDF"))
                    .onError(new OnErrorListener() {
                        @Override
                        public void onError(Throwable t) {
                            errorMessage();
                            hideLoading();
                            openDialog();
                        }
                    })
                    .onPageError(new OnPageErrorListener() {
                        @Override
                        public void onPageError(int page, Throwable t) {
                            errorMessage();
                            hideLoading();
                            openDialog();
                        }
                    })
                    .onLoad(new OnLoadCompleteListener() {
                        @Override
                        public void loadComplete(int nbPages) {
                            hideLoading();
                            bottom.setVisibility(View.VISIBLE);
                            relProfile.setVisibility(View.VISIBLE);
                        }
                    })
                    .load();
//            hideLoading();
        }
    }

    private void errorMessage() {
        Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show();
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

    private void openDialog() {
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_pdf_protector);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        AppCompatEditText etPassword = dialog.findViewById(R.id.etPassword);
        AppCompatTextView tvOk = dialog.findViewById(R.id.tvOk);

        // if button is clicked, close the custom dialog
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password = etPassword.getText().toString();
                if (!password.equals("")) {
                    openUrl();
                    dialog.dismiss();
                } else {
                    showErrorMessage("Please enter password");
                }

            }
        });

        dialog.show();
    }

//    protected void sendEmail() {
//        Log.i("Send email", "");
//        String[] TO = {""};
//        String[] CC = {""};
//        Intent emailIntent = new Intent(Intent.ACTION_SEND);
//
//        emailIntent.setData(Uri.parse("mailto:"));
//        emailIntent.setType("text/plain");
//        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//        emailIntent.putExtra(Intent.EXTRA_CC, CC);
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
//        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here\n" + pdfUrl);
//
//        try {
//            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
////            finish();
//            Log.i("Finished sending email...", "");
//        } catch (android.content.ActivityNotFoundException ex) {
//
//            Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
//        }
//    }

}

