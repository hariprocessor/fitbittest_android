package com.example.hari.gpstest.Communication;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * Created by hari on 2016-09-21.
 */
public class JSONObjectRequest extends Request<JSONObject> {

    private Response.Listener<JSONObject> listener;
    private Map<String, String> params;
    private Map<String, String> headers;

    public JSONObjectRequest(String url,  Listener<JSONObject> reponseListener, ErrorListener errorListener,
                             Map<String, String> params, Map<String, String> headers) {
        super(Method.GET, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
        this.headers = headers;
    }

    public JSONObjectRequest(int method, String url, Listener<JSONObject> reponseListener, ErrorListener errorListener,
                             Map<String, String> params, Map<String, String> headers) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
        this.headers = headers;
    }

    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return params;
    };

    public Map<String, String> getHeaders()
            throws com.android.volley.AuthFailureError {
        return headers;
    };


    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        // TODO Auto-generated method stub
        listener.onResponse(response);
    }

}