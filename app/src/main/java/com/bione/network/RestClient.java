package com.bione.network;


import com.bione.BuildConfig;
import com.bione.MyApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.bione.BuildConfig.BASE_URL;


/**
 * Developer: Bione
 * <p>
 * Rest Client
 */
public final class RestClient {

    private static final int TIME_OUT = 120;
    private static final Integer BKS_KEYSTORE_RAW_FILE_ID = 0;
    // Integer BKS_KEYSTORE_RAW_FILE_ID = R.raw.keystorebks;
    private static final Integer SSL_KEY_PASSWORD_STRING_ID = 0;
    private static Retrofit retrofit = null;
    private static Retrofit retrofit2 = null;
    private static Retrofit retrofitWithIncreaseTimeout = null;
    //Integer SSL_KEY_PASSWORD_STRING_ID = R.string.sslKeyPassword;
    private static boolean isBaseUrl = true;

    /**
     * Prevent instantiation
     */
    private RestClient() {
    }

    /**
     * Gets api interface.
     *
     * @return object of ApiInterface
     */
    public static ApiInterface getApiInterface() {
        isBaseUrl = true;
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(httpClient().build())
//                    .client(secureConnection().build())
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }

    /**
     * Gets api interface.
     *
     * @return object of ApiInterface
     */
    public static ApiInterface getApiInterface2() {
        isBaseUrl = false;
        if (retrofit2 == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit2 = new Retrofit.Builder()
                    .baseUrl("https://lims.bione.in/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(httpClient().build())
//                    .client(secureConnection().build())
                    .build();
        }
        return retrofit2.create(ApiInterface.class);
    }


    /**
     * @return object of OkHttpClient.Builder
     */
    private static OkHttpClient.Builder httpClient() {
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.addInterceptor(getLoggingInterceptor())
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        return httpClient;
    }


    /**
     * Method to get object of HttpLoggingInterceptor
     *
     * @return object of HttpLoggingInterceptor
     */
    private static HttpLoggingInterceptor getLoggingInterceptor() {
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return logging;
    }


    /**
     * Gets retrofit builder.
     *
     * @return object of Retrofit
     */
    static Retrofit getRetrofitBuilder() {
        if (isBaseUrl) {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .client(httpClient().build())
                        .build();
            }
            return retrofit;
        } else {
            if (retrofit2 == null) {
                retrofit2 = new Retrofit.Builder()
                        .baseUrl("https://lims.bione.in/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .client(httpClient().build())
                        .build();
            }
            return retrofit2;
        }
    }

    /**
     * Gets image upload api interface.
     *
     * @return the image upload api interface
     */
    public static ApiInterface getImageUploadApiInterface() {
        if (retrofitWithIncreaseTimeout == null) {
            retrofitWithIncreaseTimeout = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getRequestHeader())
                    //.client(secureConnection().build())
                    .build();
        }
        return retrofitWithIncreaseTimeout.create(ApiInterface.class);
    }


    /**
     * Gets request header.
     *
     * @return the request header
     */
    private static OkHttpClient getRequestHeader() {
        return new OkHttpClient.Builder()
                //.addInterceptor(getLoggingInterceptor())
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }


    /**
     * Method to create secure connection of api call with out own certificate
     *
     * @return object of OkHttpClient.Builder
     * @throws KeyStoreException        throws exception related to key store
     * @throws CertificateException     throws exception related to certificate
     * @throws NoSuchAlgorithmException throws exception if also not found
     * @throws IOException              throws IO exception
     * @throws KeyManagementException   throws key related exception
     */
    private static OkHttpClient.Builder secureConnection() throws
            KeyStoreException,
            CertificateException,
            NoSuchAlgorithmException,
            IOException,
            KeyManagementException {

        InputStream certificateInputStream = null;
        certificateInputStream = MyApplication.getAppContext().getResources().openRawResource(BKS_KEYSTORE_RAW_FILE_ID);
        final KeyStore trustStore = KeyStore.getInstance("BKS");
        try {
            trustStore.load(certificateInputStream,
                    MyApplication.getAppContext().getString(SSL_KEY_PASSWORD_STRING_ID).toCharArray());
        } finally {
            certificateInputStream.close();
        }
        final TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
        tmf.init(trustStore);
        final SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);

        //Retrofit 2.0.x
        final TrustManager[] trustManagers = tmf.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
        }
        final X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        final OkHttpClient.Builder client3 = new OkHttpClient.Builder().sslSocketFactory(sslSocketFactory, trustManager);
        client3.addInterceptor(getLoggingInterceptor());
        return client3;
    }
}
