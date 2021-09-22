package com.bione.ui.dashboard.bottomFragments.report;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.bione.R;
import com.bione.ui.base.BaseActivity;
import com.bione.utils.Log;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReportPdfViewActivity extends BaseActivity {

    private PDFView pdfView;
    private AppCompatTextView tvShare;

    private String pdfUrl = "";
    private String pdfPassword = "";
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            pdfUrl = extras.getString("pdfUrl");
            pdfPassword = extras.getString("password");
            // and get whatever type user account id is
        }
        pdfView = findViewById(R.id.pdfView);
        tvShare = findViewById(R.id.tvShare);
//        pdfView.fromUri(Uri.parse(pdfUrl));
        openUrl(pdfPassword);
//        openDialog();

        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareData();
            }
        });
    }

    @Override
    public void onClick(View view) {

    }


    public void openUrl(final String password) {
//        dialog.dismiss();
        pdfPassword = password;
        Log.d("pdfUrl---", "    ----- " + pdfUrl);
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
            pdfView.fromStream(inputStream).password(pdfPassword).load();
        }
    }

    private void openDialog() {
        // custom dialog
//        final Dialog dialog = new Dialog(this);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_pdf_password);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setTitle("Title...");


        AppCompatEditText etPassword = dialog.findViewById(R.id.etPassword);

        AppCompatTextView tvPassword = dialog.findViewById(R.id.tvPassword);
        AppCompatTextView tvOk = dialog.findViewById(R.id.tvOk);


        // if button is clicked, close the custom dialog
        tvOk.setOnClickListener(v -> openUrl(etPassword.getText().toString()));


        dialog.show();
    }

    private void shareData() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, pdfUrl);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
}
