package com.bione.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;



/**
 * Developer: Bione
 */

public final class CommonUtil {

    public static String EMAIL_SUPPORT = "support@bione.in";
    public static String mobileNumber = "+91 6366 754 050";

    /**
     * Prevent instantiation
     */
    private CommonUtil() {
    }

    /**
     * Method to check if internet is connected
     *
     * @param context context of calling class
     * @return true if connected to any network else return false
     */
    public static boolean isNetworkAvailable(final Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) (context.getApplicationContext()).getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Method to show toast
     *
     * @param message  that display in the Toast
     * @param mContext context of calling activity or fragment
     */
    public static void showToast(final Context mContext, final String message) {
        if (mContext == null
                || message == null
                || message.isEmpty()) {
            return;
        }
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }


    /**
     * @param context of calling activity or fragment
     * @return pixel scale factor
     */
    private static float getPixelScaleFactor(final Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT;
    }


    /**
     * Method to convert dp into pixel
     *
     * @param context of calling activity or fragment
     * @param dp      dp value that need to convert into px
     * @return px converted dp into px
     */
    public static int dpToPx(final Context context, final int dp) {
        return Math.round(dp * getPixelScaleFactor(context));
    }

    /**
     * Method to convert pixel into dp
     *
     * @param context of calling activity or fragment
     * @param px      pixel value that need to convert into dp
     * @return dp converted px into dp
     */
    public static int pxToDp(final Context context, final int px) {
        return Math.round(px / getPixelScaleFactor(context));
    }

    /**
     * Get the app version code
     *
     * @param context calling context
     * @return the app version code
     */
    public static int getAppVersionCode(final Context context) {
        try {
            return context.getApplicationContext().getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }


    /**
     * Get base 64 encoded string
     *
     * @param file the file
     * @return base64 encoded string
     */
    public static String getBase64EncodedString(final File file) {

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        byte[] bytes;
        byte[] buffer = new byte[(int) file.length()];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = output.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

//    public static String getCurrentDate() {
//        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
//        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//        Date today = Calendar.getInstance().getTime();
//        return dateFormat.format(today);
//    }
//
//    public static String getDayMonth(final String date) {
//
//        Date date1 = null;
//        try {
//            date1 = new SimpleDateFormat(DATE_FORMAT).parse(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        System.out.println(date + "\t" + date1);
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_DAY_MONTH);
////        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//        Date today = date1;
//        return dateFormat.format(today);
//    }


    public static void makeCall(Context mContext) {
        try {
            String number = ("tel:" + mobileNumber);
            Intent mIntent = new Intent(Intent.ACTION_CALL);
            mIntent.setData(Uri.parse(number));
            mContext.startActivity(mIntent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public static void sendEmail(Context mContext, String text) {
        Log.i("Send email", "");
        String[] TO = {EMAIL_SUPPORT};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here \n" + text);

        try {
            mContext.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {

            Toast.makeText(mContext, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
