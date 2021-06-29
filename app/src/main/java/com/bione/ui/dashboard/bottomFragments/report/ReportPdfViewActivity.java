package com.bione.ui.dashboard.bottomFragments.report;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

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

    private String pdfUrl = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            pdfUrl = extras.getString("pdfUrl");
            // and get whatever type user account id is
        }
        pdfView = findViewById(R.id.pdfView);
//        pdfView.fromUri(Uri.parse(pdfUrl));
        openUrl();
    }

    @Override
    public void onClick(View view) {

    }


    public void openUrl() {

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
            pdfView.fromStream(inputStream).password("Bione@123").load();
        }
    }
}
