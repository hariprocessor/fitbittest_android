package com.example.hari.gpstest;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hari on 2016-09-20.
 */
public class KeyManagement {
    private Context context;

    public KeyManagement(Context context){
        this.context = context;
    }
    public String getPreferences(){
        SharedPreferences pref = context.getSharedPreferences("key", context.MODE_PRIVATE);
        String key = null;
        key = pref.getString("key", null);
        return key;
    }

    public void setPreferences(String key){
        SharedPreferences pref = context.getSharedPreferences("key", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("key", key);
        editor.commit();
    }

    public void removePreferences(){
        SharedPreferences pref = context.getSharedPreferences("key", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("hi");
        editor.commit();
    }

    // 값(ALL Data) 삭제하기
    /*
    private void removeAllPreferences(){
        SharedPreferences pref = context.getSharedPreferences("key", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
    */
}
