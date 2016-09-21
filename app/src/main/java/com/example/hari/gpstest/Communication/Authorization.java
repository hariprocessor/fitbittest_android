package com.example.hari.gpstest.Communication;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hari on 2016-09-18.
 * request token
 */
public class Authorization {
    private Context context;
    private static String TAG = "*** Authorization ************* ";

    private String code;

    public Authorization(Context context){
        this.context = context;
    }


    public void getToken(final String code) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://api.fitbit.com/oauth2/token";

        Map<String, String>  params = new HashMap<String, String>();
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        params.put("clientId", "227ZFL");
        params.put("redirect_uri", "http://166.104.231.227:8080/callback");

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Authorization", "Basic MjI3WkZMOmQyZDI3NjBmZWZiOTU0ZGVjZTZhNDI3OTk1OTRiYjYw");

        JSONObjectRequest jsObjRequest = new JSONObjectRequest(Request.Method.POST, url,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        try {
                            Log.i(TAG, (String) response.get("access_token"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(new String(error.networkResponse.data));
                        // error
                    }
                }, params, headers);
        /*
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            Log.i(TAG, response);
                            jsonParsing(response);

                            //access_token, expires_in, refresh_token, scope, token_type, user_id
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(new String(error.networkResponse.data));
                        // error
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("code", code);
                params.put("grant_type", "authorization_code");
                params.put("clientId", "227ZFL");
                params.put("redirect_uri", "http://166.104.231.227:8080/callback");
                return params;
            }
            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                headers.put("Authorization", "Basic MjI3WkZMOmQyZDI3NjBmZWZiOTU0ZGVjZTZhNDI3OTk1OTRiYjYw");
                return headers;
            }
        };
        queue.add(postRequest);
        */
    }

    private Map<String, String> jsonParsing(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        String access_token = (String) jsonObject.get("access_token");
        String expires_in = jsonObject.get("expires_in").toString();
        String refresh_token = (String) jsonObject.get("refresh_token");
        String scope = (String) jsonObject.get("scope");
        String token_type = (String) jsonObject.get("token_type");
        String user_id = (String) jsonObject.get("user_id");

        Map<String, String> result = new HashMap<String, String>();
        result.put("access_token", access_token);
        result.put("expires_in", expires_in);
        result.put("refresh_token", refresh_token);
        result.put("scope", scope);
        result.put("token_type", token_type);
        result.put("user_id", user_id);
        return result;
    }

}
