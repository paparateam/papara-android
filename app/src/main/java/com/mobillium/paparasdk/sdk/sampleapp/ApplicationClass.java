package com.mobillium.paparasdk.sdk.sampleapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobillium.paparasdk.sdk.sampleapp.utils.MyVolley;

public class ApplicationClass extends Application {

    private static MyVolley myVolley;
    public static Gson gson;
    public static SharedPreferences mSharedPrefs;
    public static SharedPreferences.Editor mPrefsEditor;
    private static ApplicationClass instance;
    public static int SCREEN_WIDTH = 0;
    public static boolean isTest = false;


    //Tests
    private static RequestQueue requestQueue;
    private static Application appInstance;

    // SharedPref Datalari
    // responseAuth
    // duyurular
    // Otto Bus
    // Google Analytics
    public ApplicationClass() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myVolley = new MyVolley(this);
        instance = this;
        mSharedPrefs = getSharedPreferences("uygulamaVerileri", MODE_PRIVATE);
        mPrefsEditor = mSharedPrefs.edit();

        appInstance = this;
        requestQueue = Volley.newRequestQueue(this);

    }

    public synchronized static RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(appInstance);
        }
        Log.d("TEST", "entering getRequestQueue");
        Log.d("TEST", "Application instance: " + appInstance);
        Log.d("TEST", "requestQueue instance: " + requestQueue);


        return requestQueue;

    }

    public static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .create();
        }
        return gson;
    }


    public static SharedPreferences getmSharedPrefs() {
        if (mSharedPrefs == null) {
            mSharedPrefs = getContext().getSharedPreferences("uygulamaVerileri",
                    MODE_PRIVATE);
        }
        return mSharedPrefs;
    }

    public static SharedPreferences.Editor getmPrefsEditor() {

        if (mPrefsEditor == null || mSharedPrefs == null) {
            mSharedPrefs = getContext().getSharedPreferences("uygulamaVerileri",
                    MODE_PRIVATE);
            mPrefsEditor = mSharedPrefs.edit();
        }
        return mPrefsEditor;
    }

    public static MyVolley getMyVolley(Context ctx) {
        if (myVolley == null) {
            myVolley = new MyVolley(ctx);
        }
        return myVolley;
    }

    public static synchronized MyVolley getMyVolley() {
        if (myVolley == null) {
            myVolley = new MyVolley(getContext());
        }
        return myVolley;
    }

    public static int getScreenWidth(Context ctx) {
        int width = 0;
        try {
            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager wm = (WindowManager) ctx
                    .getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            width = metrics.widthPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return width;
    }

    public static int getScreenHeight(Context ctx) {
        int height = 0;
        try {
            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager wm = (WindowManager) ctx
                    .getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            height = metrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return height;
    }

    public static int convertDpiToPixel(int dpi) {
        float pixel = 0;
        try {
            Resources r = getContext().getResources();
            pixel = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpi,
                    r.getDisplayMetrics());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (int) pixel;
    }

    public static Context getContext() {
        if (instance == null) {
            instance = new ApplicationClass();
        }
        return instance;
    }





}
