package com.mobillium.paparasdk.sdk.sampleapp.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.AndroidHttpClient;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;
import com.mobillium.paparasdk.sdk.sampleapp.ApplicationClass;
import com.mobillium.paparasdk.sdk.sampleapp.BuildConfig;
import com.mobillium.paparasdk.sdk.sampleapp.R;
import com.mobillium.paparasdk.sdk.sampleapp.webservice.models.BaseResponse;
import com.mobillium.paparasdk.sdk.sampleapp.webservice.models.RequestModel;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import static com.mobillium.paparasdk.sdk.sampleapp.ApplicationClass.getContext;
import static com.mobillium.paparasdk.sdk.sampleapp.ApplicationClass.getGson;


/**
 * Created by oguzhandongul on 31/05/2017.
 */

public class ServiceOperations {
    private static String baseUrl = "https://merchantapi-test-master.papara.com/";
    public static ProgressDialog pd;
    public static final boolean DEBUG = BuildConfig.DEBUG;

    public static <T> void makeRequest(final Context mContext,
                                       final RequestModel requestModel, final ServiceCallback<T> callback, final TypeToken typeToken) {
        if (!isOnline(mContext)) {
            if (mContext != null) {
                try {
                    callback.error(new ServiceException(mContext.getString(R.string.error_internet)));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return;
        }

        final WeakReference<Context> contextRef = new WeakReference<>(mContext);

        final String url = baseUrl + requestModel.getOffsetUrl();

        if (DEBUG) {
            Log.d("Papara", "İstek yapılan url: " + url);
            if (requestModel.getData() != null) {
                Log.d("Papara", "İstek yapılan model: " + getGson().toJson(requestModel.getData()));
            }
        }

        if (requestModel.getPdMessage() != null && !TextUtils.isEmpty(requestModel.getPdMessage())) {
            try {
                pd = ProgressDialog.show(mContext, null, requestModel.getPdMessage());
                pd.setCancelable(false);
                pd.setCanceledOnTouchOutside(false);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


        final PaparaRequest request = new PaparaRequest(requestModel.getServiceType(), url, requestModel.getData() == null ? null : getGson().toJson(requestModel.getData()), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (requestModel.getPdMessage() != null || !TextUtils.isEmpty(requestModel.getPdMessage())) {
                    try {
                        pd.dismiss();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                if (DEBUG) {
                    Log.d("Papara", "Servis cevabı : " + response);
                }
                if (callback != null) {

                    if (TextUtils.isEmpty(response)) {
                        callback.success(null);
                        return;
                    }

                    BaseResponse<T> baseResponse = null;
                    try {
                        baseResponse = getGson().fromJson(response, typeToken.getType());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        callback.error(new ServiceException(getContext().getString(R.string.error_bilinmeyen)));
                        return;
                    }
                    if (baseResponse.isSucceeded() && baseResponse.getError() == null) {
                        callback.success(baseResponse.getData());
                    } else {
                        try {
                            callback.error(new ServiceException(baseResponse.getError().getMessage()));

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            callback.error(new ServiceException("Bir Hata Olustu"));
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                String volleyErrorString = "";
                ServiceError serviceError = null;
                ServiceException serviceException = null;


                if (requestModel.getPdMessage() != null) {
                    try {
                        pd.dismiss();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }


                if (DEBUG) {
                    Log.d("Papara", "Servis cevabı : " + volleyError);
                }

                final Context context = contextRef.get();
                if (context == null) {
                    return;
                }

                // volleyError null döndüyse
                if (volleyError == null) {
                    serviceException = new ServiceException(context.getString(R.string.error_bilinmeyen));
                    if (callback != null) {
                        callback.error(serviceException);
                    }
                    return;
                }

                // Timeout'a girildiyse
                if (volleyError instanceof TimeoutError) {
                    serviceException = new ServiceException(context.getString(R.string.error_cevap_yok));
                    if (callback != null) {
                        callback.error(serviceException);
                    }
                    return;
                }

                try {
                    volleyErrorString = new String(volleyError.networkResponse.data, "UTF-8");
                    serviceError = getGson()
                            .fromJson(volleyErrorString, ServiceError.class);

                } catch (Exception e) {
                    volleyErrorString = "";
                    e.printStackTrace();
                }

                if (DEBUG) {
                    Log.d("Papara", "Servis cevabı : " + volleyErrorString);
                }

                //Hata mesajı decode edilemediyse veya boşsa
                if (TextUtils.isEmpty(volleyErrorString)) {
                    serviceException = new ServiceException(context.getString(R.string.error_bilinmeyen));
                    if (callback != null) {
                        callback.error(serviceException);
                    }
                } else {
                    serviceException = new ServiceException(serviceError.getMessage());
                    if (callback != null) {
                        callback.error(serviceException);
                    }

                }
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ApiKey", "ApiKey123"); //Android
                Log.d("HEADER", "getHeaders: " + params.toString());
                return params;
            }
        };

        request.setShouldCache(false);
        request.setRetryPolicy(new DefaultRetryPolicy(15000, 1, 1.0f));

        if (requestModel.getServiceType() == Request.Method.DELETE || requestModel.getServiceType() == Request.Method.PUT) {
            String userAgent = "volley/0";
            try {
                String packageName = mContext.getPackageName();
                PackageInfo info = mContext.getPackageManager().getPackageInfo(packageName, 0);
                userAgent = packageName + "/" + info.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
            }
            HttpStack httpStack = new PaparaHttpClientStack(AndroidHttpClient.newInstance(userAgent));
            RequestQueue requesQueue = Volley.newRequestQueue(mContext, httpStack);
            requesQueue.add(request);
        } else {
            ApplicationClass.getMyVolley(mContext)
                    .getRequestQueue()
                    .add(request);
        }
    }


    /**
     * Checks the device is online or not
     */
    public static boolean isOnline(Context mContext) {
        if (mContext != null) {
            ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            }
        }
        return false;
    }

}
