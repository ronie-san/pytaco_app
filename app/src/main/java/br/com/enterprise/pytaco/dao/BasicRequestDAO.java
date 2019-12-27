package br.com.enterprise.pytaco.dao;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

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
import java.util.HashMap;
import java.util.Map;

import br.com.enterprise.pytaco.activity.IActivity;

public abstract class BasicRequestDAO extends Application {

    protected String baseUrl;
    protected Boolean useKeyHeader;
    private Response.Listener<JSONObject> jsonRespListener;
    private Response.Listener<String> respListener;
    private Response.ErrorListener errorListener;
    private IActivity activity;

    //region CONSTRUCTOR
    public BasicRequestDAO(final IActivity activity) {
        this.activity = activity;

        this.jsonRespListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                BasicRequestDAO.this.activity.onJsonSuccess(response);
            }
        };

        this.respListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                BasicRequestDAO.this.activity.onSucess(response);
            }
        };

        this.errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ConnectivityManager cm = (ConnectivityManager) activity.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

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
            @Override
            public String getUrl() {
                return super.getUrl() + pGetUrlParams(params);
            }

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
            public String getUrl() {
                return super.getUrl() + pGetUrlParams(params);
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(@NotNull NetworkResponse response) {
                try {
                    return Response.success(new JSONObject(pNetworkResponseToStr(response)), HttpHeaderParser.parseCacheHeaders(response));
                } catch (JSONException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };

        pMakeRequest(request);
    }

    private void pMakeRequest(@NotNull Request request) {
        DefaultRetryPolicy policy = new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        request.setShouldCache(false);
        this.activity.onStartRequest();
        RequestQueue queue = Volley.newRequestQueue(activity.getContext());
        queue.add(request);
    }

    @NotNull
    private String pGetUrlParams(@Nullable Map<String, String> params){

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
    private String pNetworkResponseToStr(@NotNull NetworkResponse response){
        try {
            return new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
    //endregion
}
