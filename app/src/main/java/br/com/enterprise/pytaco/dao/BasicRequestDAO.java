package br.com.enterprise.pytaco.dao;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.activity.BaseActivity;

public abstract class BasicRequestDAO {

    private Response.Listener<String> respListener;
    private Response.ErrorListener errorListener;

    protected String baseUrl;
    protected Boolean useKeyHeader;
    protected BaseActivity activity;

    //region CONSTRUCTOR
    public BasicRequestDAO(final BaseActivity activity) {
        this.activity = activity;

        this.respListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!pSemInternet()) {
                    Log.d(activity.getClass().getSimpleName(), response);
                    activity.onSucess(response);
                } else {
                    pLancarErroSemInternet();
                }
            }
        };

        this.errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (!(error instanceof TimeoutError) || pSemInternet()) {
                    pLancarErroSemInternet();
                } else {
                    activity.onError(error);
                }
            }
        };
    }
    //endregion

    //region PROTECTED METHODS
    protected void pGetRequest(@NonNull String url) {
        pGetRequest(url, null);
    }

    protected void pGetRequest(@NonNull String url, @Nullable final Map<String, String> params) {
        Request<String> request = new Request<String>(Request.Method.GET, baseUrl + url, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (useKeyHeader) {
                    HashMap<String, String> headers = new HashMap<>();
                    /*CHAVE DA API NECESSÁRIA PARA AUTENTICAÇÃO*/
                    headers.put(activity.getString(R.string.api_footbal_header), activity.getString(R.string.api_footbal_key));
                    return headers;
                }

                return super.getHeaders();
            }

            @NotNull
            @Override
            public String getUrl() {
                String computedUrl = super.getUrl() + pGetUrlParams(params);
                Log.d(activity.getClass().getSimpleName(), "URL: " + computedUrl);
                return computedUrl;
            }

            @NotNull
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return Response.success(pNetworkResponseToStr(response), HttpHeaderParser.parseCacheHeaders(response));
            }

            @Override
            protected void deliverResponse(String response) {
                respListener.onResponse(response);
            }
        };

        pMakeRequest(request);
    }

    protected void pPostRequest(@NonNull String url, @Nullable final Map<String, String> params) {
        Request<String> request = new Request<String>(Request.Method.POST, baseUrl + url, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params != null && !params.isEmpty()) {
                    Log.d(activity.getClass().getSimpleName(), "URL: " + this.getUrl() + " - PARAMS: " + params.toString());
                    return params;
                }

                Log.d(activity.getClass().getSimpleName(), "URL: " + this.getUrl());
                return super.getParams();
            }

            @NotNull
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return Response.success(pNetworkResponseToStr(response), HttpHeaderParser.parseCacheHeaders(response));
            }

            @Override
            protected void deliverResponse(String response) {
                respListener.onResponse(response);
            }
        };

        pMakeRequest(request);
    }
    //endregion

    //region PRIVATE METHODS
    private void pLancarErroSemInternet() {
        activity.onError(new VolleyError(activity.getString(R.string.msg_sem_conexao)));
    }

    private boolean pSemInternet() {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null) {
            return true;
        }

        if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
            return activeNetwork.getState() != NetworkInfo.State.CONNECTED;
        }

        return true;
    }

    private void pMakeRequest(@NotNull Request<String> request) {
        DefaultRetryPolicy policy = new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        request.setShouldCache(false);
        activity.onStartRequest();
        RequestQueue queue = Volley.newRequestQueue(activity);
        queue.add(request);
    }

    @NotNull
    private String pGetUrlParams(@Nullable Map<String, String> params) {
        if (params != null) {
            Uri.Builder builder = new Uri.Builder();

            for (Map.Entry<String, String> pair : params.entrySet()) {
                builder.appendQueryParameter(pair.getKey(), pair.getValue());
            }

            return builder.build().toString();
        }

        return "";
    }

    @NotNull
    private String pNetworkResponseToStr(@NotNull NetworkResponse response) {
        try {
            return new String(response.data, HttpHeaderParser.parseCharset(response.headers, String.valueOf(StandardCharsets.UTF_8)));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
    //endregion
}