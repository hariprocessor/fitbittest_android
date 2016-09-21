package com.example.hari.gpstest;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.hari.gpstest.Communication.Authorization;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity{
    static final String oauth2Code = "https://www.fitbit.com/oauth2/authorize?" +
            "response_type=code&" +
            "client_id=227ZFL&" +
            "redirect_uri=http%3A%2F%2F166.104.231.227%3A8080%2Fcallback" +
            "&scope=activity%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight&" +
            "expires_in=604800";
    private String code;
    private WebView webView;

    private KeyManagement keyManagement;
    private static String TAG = "*** MainActivity ******************** ";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keyManagement = new KeyManagement(this);


        if(keyManagement.getPreferences() == null) {
            loginPage();
            Toast.makeText(this, keyManagement.getPreferences(), Toast.LENGTH_LONG).show();
        }
        else{
            Log.i(TAG, keyManagement.getPreferences());

        }


    }

    public void loginPage(){
        code = "";

        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVisibility(View.VISIBLE);

        webView.setHorizontalScrollBarEnabled(false);
        webView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                try {
                    if (url.contains("code=")) {
                        webView.setVisibility(View.GONE);
                        URL codeUrl = new URL(url.toString());
                        code = codeUrl.getQuery().split("=")[1];
                        System.out.println(url);
                        System.out.println(code);
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("code", code);
                        params.put("grant_type", "authorization_code");
                        params.put("clientId", "227ZFL");
                        params.put("redirect_uri", "http://166.104.231.227:8080/callback");
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());

                        Authorization auth = new Authorization(getApplicationContext());
                        auth.getToken(code);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        });
        webView.loadUrl(oauth2Code);
    }

}
