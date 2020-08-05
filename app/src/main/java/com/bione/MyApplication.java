package com.bione;

import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.paperdb.Paper;
//import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Developer: Bione
 * <p>
 * The Application class
 */

public class MyApplication extends Application {

    private static WeakReference<Context> mWeakReference;
    private static final String TAG = MyApplication.class.getName();

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

//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/Poppins-Regular.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );

    }


}
