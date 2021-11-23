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

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.bione.BuildConfig.BASE_URL;
import static com.bione.BuildConfig.BASE_URL_LIMBS;
import static com.bione.BuildConfig.BASE_URL_LONGIFIT;
import static com.bione.BuildConfig.BASE_URL_MYMICRO;


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
    private static Retrofit retrofit3 = null;
    private static Retrofit retrofit4 = null;
    private static Retrofit retrofit5 = null;
    private static Retrofit retrofitWithIncreaseTimeout = null;
    //Integer SSL_KEY_PASSWORD_STRING_ID = R.string.sslKeyPassword;
    private static int isBaseUrl = 0;

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
        isBaseUrl = 0;
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
        isBaseUrl = 1;
        if (retrofit2 == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit2 = new Retrofit.Builder()
                    .baseUrl(BASE_URL_LIMBS)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(httpClient().build())
//                    .client(secureConnection().build())
                    .build();
        }
        return retrofit2.create(ApiInterface.class);
    }

    /**
     * Gets api interface.
     *
     * @return object of ApiInterface
     */
    public static ApiInterface getApiInterface3() {
        isBaseUrl = 2;
        if (retrofit3 == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit3 = new Retrofit.Builder()
                    .baseUrl(BASE_URL_MYMICRO)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(httpClient().build())
//                    .client(secureConnection().build())
                    .build();
        }
        return retrofit3.create(ApiInterface.class);
    }



    /**
     * Gets api interface.
     *
     * @return object of ApiInterface
     */
    public static ApiInterface getApiInterface4(String baseUrl) {
        isBaseUrl = 3;
        if (retrofit4 == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit4 = new Retrofit.Builder()
                    .baseUrl(baseUrl) //BASE_URL_MYMICRO
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(httpClient2().build())
//                    .client(secureConnection().build())
                    .build();
        }
        return retrofit4.create(ApiInterface.class);
    }
    /**
     * Gets api interface.
     *
     * @return object of ApiInterface
     */
    public static ApiInterface getApiInterface5(String baseUrl) {
        isBaseUrl = 4;
        if (retrofit5 == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit5 = new Retrofit.Builder()
                    .baseUrl(BASE_URL_LONGIFIT) //BASE_URL_MYMICRO
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(httpClient2().build())
//                    .client(secureConnection().build())
                    .build();
        }
        return retrofit5.create(ApiInterface.class);
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
     * @return object of OkHttpClient.Builder
     */
    private static OkHttpClient.Builder httpClient2() {
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.addInterceptor(getLoggingInterceptor())
                .addInterceptor(new BasicAuthInterceptor("admin", "1234"))
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
        if (isBaseUrl == 0) {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .client(httpClient().build())
                        .build();
            }
            return retrofit;
        } else if (isBaseUrl == 1) {
            if (retrofit2 == null) {
                retrofit2 = new Retrofit.Builder()
                        .baseUrl(BASE_URL_LIMBS)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .client(httpClient().build())
                        .build();
            }
            return retrofit2;
        } else if (isBaseUrl == 3) {
            if (retrofit4 == null) {
                retrofit4 = new Retrofit.Builder()
                        .baseUrl(BASE_URL_MYMICRO)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .client(httpClient2().build())
                        .build();
            }
            return retrofit4;
        }else if (isBaseUrl == 4) {
            if (retrofit5 == null) {
                retrofit5 = new Retrofit.Builder()
                        .baseUrl(BASE_URL_LONGIFIT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .client(httpClient2().build())
                        .build();
            }
            return retrofit5;
        }
        else {
            if (retrofit3 == null) {
                retrofit3 = new Retrofit.Builder()
                        .baseUrl(BASE_URL_MYMICRO)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .client(httpClient().build())
                        .build();
            }
            return retrofit3;
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

// Basic auth interceptor
    public static class BasicAuthInterceptor implements Interceptor {

        private String credentials;

        public BasicAuthInterceptor(String Username, String Password) {
            this.credentials = Credentials.basic(Username, Password);
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request authenticatedRequest = request.newBuilder()
                    .header("Authorization", credentials).build();
            return chain.proceed(authenticatedRequest);
        }

    }
}
//    OkHttpClient client = new OkHttpClient.Builder()
//            .addInterceptor(new BasicAuthInterceptor(username, password))
//            .build();