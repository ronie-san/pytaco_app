package br.com.enterprise.pytaco.dao;

import android.app.Application;
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
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import br.com.enterprise.pytaco.activity.IActivity;

public abstract class BasicRequestDAO extends Application {

    protected String baseUrl;
    protected Boolean useKeyHeader;
    private Response.Listener<JSONObject> jsonRespListener;
    private Response.Listener<String> respListener;
    private Response.ErrorListener errorListener;
    protected IActivity activity;

    //region CONSTRUCTOR
    public BasicRequestDAO(final IActivity activity) {
        this.activity = activity;

        this.jsonRespListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(activity.getClass().getSimpleName(), response.toString());
                activity.onSucess(response.toString());
            }
        };

        this.respListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(activity.getClass().getSimpleName(), response);
                activity.onSucess(response);
            }
        };

        this.errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ConnectivityManager cm = (ConnectivityManager) activity.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

                if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() != NetworkInfo.State.CONNECTED &&
                        cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() != NetworkInfo.State.CONNECTED) {
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
                Response<String> result = Response.success(pNetworkResponseToStr(response), HttpHeaderParser.parseCacheHeaders(response));
                return result;
            }

            @Override
            protected void deliverResponse(String response) {
                respListener.onResponse(response);
            }
        };

        pMakeRequest(request);
    }

    protected void pPostRequest(@NonNull String url) {
        pPostRequest(url, null);
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
                Response<String> result = Response.success(pNetworkResponseToStr(response), HttpHeaderParser.parseCacheHeaders(response));
                return result;
            }

            @Override
            protected void deliverResponse(String response) {
                respListener.onResponse(response);
            }
        };

        pMakeRequest(request);
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

            @NotNull
            @Override
            public String getUrl() {
                String computedUrl = super.getUrl() + pGetUrlParams(params);
                return computedUrl;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(@NotNull NetworkResponse response) {
                try {
                    Response<JSONObject> result = Response.success(new JSONObject(pNetworkResponseToStr(response)), HttpHeaderParser.parseCacheHeaders(response));
                    return result;
                } catch (JSONException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };

        pMakeRequest(request);
    }

    private void pMakeRequest(@NotNull Request request) {
        DefaultRetryPolicy policy = new DefaultRetryPolicy(1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        request.setShouldCache(false);
        this.activity.onStartRequest();
        RequestQueue queue = Volley.newRequestQueue(activity.getActivity());
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
            String result = new String(response.data, HttpHeaderParser.parseCharset(response.headers, String.valueOf(StandardCharsets.UTF_8)));
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
    //endregion
}
