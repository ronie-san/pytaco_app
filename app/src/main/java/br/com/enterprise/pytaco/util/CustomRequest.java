package br.com.enterprise.pytaco.util;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NoCache;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import br.com.enterprise.pytaco.activity.IActivity;

public abstract class CustomRequest extends Application {

    protected String baseUrl;
    protected Boolean useKeyHeader;
    private Response.Listener<JSONObject> jsonRespListener;
    private Response.Listener<String> respListener;
    private Response.ErrorListener errorListener;
    private IActivity activity;

    //region CONSTRUCTOR
    public CustomRequest(final IActivity activity) {
        this.activity = activity;

        this.jsonRespListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                CustomRequest.this.activity.onJsonSuccess(response);
            }
        };

        this.respListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                CustomRequest.this.activity.onSucess(response);
            }
        };

        this.errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ConnectivityManager connectivityManager = (ConnectivityManager) activity.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() != NetworkInfo.State.CONNECTED &&
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() != NetworkInfo.State.CONNECTED) {
                    activity.onError(new VolleyError("Sem conexão com a internet."));
                } else {
                    activity.onError(error);
                }
            }
        };
    }
    //endregion

    //region PRIVATE METHODS
    protected void pGetRequest(@NonNull String url) {
        pGetRequest(url, null);
    }

    protected void pGetRequest(@NonNull String url, @Nullable final Map<String, String> params) {
        Request<String> request = new Request<String>(Request.Method.GET, baseUrl + url, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params != null) {
                    return params;
                }

                return super.getParams();
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                try {
//                    String strResponse;
//                    strResponse = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
//                    return Response.success(strResponse, HttpHeaderParser.parseCacheHeaders(response));
//                } catch (UnsupportedEncodingException e) {
//                    return Response.error(new VolleyError(e.getMessage()));
//                }

                Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);

                if (entry != null) {
                    entry = new Cache.Entry();
                }

                final long cacheHitButRefreshed = 50;
                final long cacheExpired = 50;

                long now = System.currentTimeMillis();
                final long softExpire = now + cacheHitButRefreshed;
                final long ttl = now + cacheExpired;

                entry.data = response.data;
                entry.softTtl = softExpire;
                entry.ttl = ttl;
                String headerValue = response.headers.get("Date");

                if (headerValue != null) {
                    entry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                }

                headerValue = response.headers.get("Last-Modified");

                if (headerValue != null) {
                    entry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                }

                entry.responseHeaders = response.headers;

                try {
                    return Response.success(new String(entry.data, HttpHeaderParser.parseCharset(response.headers)), entry);
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }


            @Override
            protected void deliverResponse(String response) {
                respListener.onResponse(response);
            }
        };

        makeRequest(request);
    }

    protected void pGetJsonRequest(@NonNull String url) {
        pGetJsonRequest(url, null);
    }

    protected void pGetJsonRequest(@NonNull String url, @Nullable final Map<String, String> params) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, baseUrl + url, null, jsonRespListener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (useKeyHeader) {
                    HashMap<String, String> headers = new HashMap<>();
                    /*CHAVE DA API NECESSÁRIA PARA AUTENTICAÇÃO*/
                    headers.put("X-RapidAPI-Key", "fdecd8152bmsh85452b4495c9e18p113131jsnba0f6981f4d3");
                    return headers;
                }

                return super.getHeaders();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params != null) {
                    return params;
                }

                return super.getParams();
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);

                if (entry != null) {
                    entry = new Cache.Entry();
                }

                final long cacheHitButRefreshed = 50;
                final long cacheExpired = 50;

                long now = System.currentTimeMillis();
                final long softExpire = now + cacheHitButRefreshed;
                final long ttl = now + cacheExpired;

                entry.data = response.data;
                entry.softTtl = softExpire;
                entry.ttl = ttl;
                String headerValue = response.headers.get("Date");

                if (headerValue != null) {
                    entry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                }

                headerValue = response.headers.get("Last-Modified");

                if (headerValue != null) {
                    entry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                }

                entry.responseHeaders = response.headers;

                try {
                    String json = new String(entry.data, HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(new JSONObject(json), entry);
                } catch (JSONException | UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };

        makeRequest(request);
    }

    private void makeRequest(Request request) {
        DefaultRetryPolicy policy = new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        request.setShouldCache(false);
        this.activity.onStartRequest();
        RequestQueue queue = Volley.newRequestQueue(activity.getContext());
        queue.add(request);
    }
    //endregion
}
