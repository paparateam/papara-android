package com.mobillium.paparasdk.sdk.sampleapp.utils;

import android.app.ActivityManager;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Helper class that is used to provide references to initialized
 * RequestQueue(s) and ImageLoader(s)
 * 
 */
public class MyVolley {
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public MyVolley(Context mContext) {
        init(mContext);
    }

    void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        int memory = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        int cacheSize = 1024 * 1024 * memory / 8;
        mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache(cacheSize));

        // int memClass = ((ActivityManager)
        // context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        // // Use 1/8th of the available memory for this memory cache.
        // int cacheSize = 1024 * 1024 * memClass / 8;
        // mImageLoader = new ImageLoader(mRequestQueue, new
        // BitmapLruCache(cacheSize));
        
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }

    /**
     * Returns instance of ImageLoader initialized with {@see FakeImageCache}
     * which effectively means that no memory caching is used. This is useful
     * for images that you know that will be show only once.
     * 
     * @return
     */
    public ImageLoader getImageLoader() {
        if (mImageLoader != null) {
            return mImageLoader;
        } else {
            throw new IllegalStateException("ImageLoader not initialized");
        }
    }
}
