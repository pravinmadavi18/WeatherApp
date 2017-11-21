package com.virtusa.weatherapp.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * This Class handles the network/service request using volley network library
 */

public class WANetworkManager {

    public static final String TAG = WANetworkManager.class.getSimpleName();
    private static WANetworkManager mInstance;
    private static Context mContext;
    private RequestQueue mRequestQueue;

    private WANetworkManager() {
        //create the private constructor to support the singleton pattern
    }

    /**
     * This method gives the singleton instance of the class to access the methods
     *
     * @return WANetworkManager instance
     */
    public static synchronized WANetworkManager getInstance(Context context) {
        if (mInstance == null) {
            mContext = context;
            mInstance = new WANetworkManager();
        }
        return mInstance;
    }//end of getInstance

    /**
     * This method gives the singleton instance of the volley request queue
     *
     * @return RequestQueue
     */
    public RequestQueue getNetworkRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }


    /**
     * This method adds the network request to the volley queue
     *
     * @param req
     * @param tag
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getNetworkRequestQueue().add(req);
    }//end of addToRequestQueue

    /**
     * This method adds the network request to the volley queue
     *
     * @param req
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getNetworkRequestQueue().add(req);
    }//end of addToRequestQueue

    private ImageLoader mImageLoader = new ImageLoader(getNetworkRequestQueue(), new ImageLoader.ImageCache() {
        private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);

        @Override
        public Bitmap getBitmap(String url) {

            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }
    });

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
