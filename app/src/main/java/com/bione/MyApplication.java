package com.bione;

import android.app.Application;
import android.content.Context;


import java.lang.ref.WeakReference;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.paperdb.Paper;




/**
 * Developer: Bione
 * <p>
 * The Application class
 */

public class MyApplication extends Application {

    private static WeakReference<Context> mWeakReference;
    private static final String TAG = MyApplication.class.getName();
    private String appKey = "XZvO8GN1MbRGsWM1JGgeg4Pu6viEjyr1MCtqCpybpWoTgmKUziZvfHQSEz%2FDTDscwpIPqxSDE%2B5oxJgLStg6MxPtdsHPHvQX_in";
    private String accessKey = "AS1%2FBz1totGY%2FTeDIGNx2oF8S4XGBoYBnudWIif9tjWJLZpHs%2FqILAvw02b73G9CxwP5tH5b9E4wWhWL%2FLoOnxkrmqJXPpesVs4b2A0TNs%2BJL95PkeITbcK6NF0LTRbG4wdX6vX3qryyPUjYzuRFgg%3D%3D";

    /**
     * Getter to access Singleton instance
     *
     * @return instance of MyApplication
     */
    public static Context getAppContext() {
        return mWeakReference.get();
    }

    @Override
    public void onCreate() {
        super.onCreate();

//        Fabric.with(this, new Crashlytics());
//        Foreground.init(this);


        Paper.init(this);
        mWeakReference = new WeakReference<Context>(this);

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/Poppins-Regular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
//        ZohoSalesIQ.init(this, appKey, accessKey);


//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/Poppins-Regular.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );

    }


}
