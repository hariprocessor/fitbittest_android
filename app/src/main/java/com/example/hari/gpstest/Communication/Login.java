package com.example.hari.gpstest.Communication;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hari on 2016-09-20.
 */
public class Login {
    private Context context;
    private Map<String, String> data;
    public Login(Context context, Map<String, String> data){
        this.context = context;
        this.data = data;
    }

    public void getKey(final String code) {
        RequestQueue queue = Volley.newRequestQueue(context);

        String url = "https://166.104.231.227:8080/login";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                       // jsonParsing(response);
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
                params.put("access_token", data.get("access_token"));
                params.put("expires_in", data.get("expires_in"));
                params.put("refresh_token", data.get("refresh_token"));
                params.put("scope", data.get("scope"));
                params.put("token_type", data.get("token_type"));
                params.put("user_id", data.get("user_id"));
                return params;
            }
        };
        queue.add(getRequest);
    }

    private Map<String, String> jsonParsing(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        String access_token = (String) jsonObject.get("access_token");
        String expires_in = (String) jsonObject.get("expires_in");
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
