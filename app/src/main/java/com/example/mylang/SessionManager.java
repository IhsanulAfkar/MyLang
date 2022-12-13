package com.example.mylang;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context _context;

    int Private_mode = 0;

    private static final String PREF_NAME = "AndroidHivePref";

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String USERNAME = "username";

    public static final String LEVEL_JP = "level_jp";
    public static final String LEVEL_EN = "level_en";
    public static final String QUIZ_JP = "quiz_jp";
    public static final String QUIZ_EN = "quiz_en";
    public static final String TEMP = "temp";



    public SessionManager (Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Private_mode);
        editor = pref.edit();
    }

    public void createLoginSession(String username, String lv_jp, String lv_en, String q_jp, String q_en){
        editor.putBoolean(IS_LOGIN,true);
        editor.putString(USERNAME, username);
        editor.putString(LEVEL_JP, lv_jp);
        editor.putString(LEVEL_EN, lv_en);
        editor.putString(QUIZ_JP, q_jp);
        editor.putString(QUIZ_EN, q_en);
        editor.commit();
    }
    public void updateLevelJp(String lv_jp, String q_jp){
        editor.putString(LEVEL_JP, lv_jp);
        editor.putString(QUIZ_JP, q_jp);
        editor.commit();
    }
    public void updateLevelEn(String lv_en, String q_en){
        editor.putString(LEVEL_EN, lv_en);
        editor.putString(QUIZ_EN, q_en);
        editor.commit();
    }
    public void createTemp(String temp){
        editor.putString(TEMP, temp);
        editor.commit();
    }
    public void deleteTemp() {
        editor.putString(TEMP, null);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            _context.startActivity(i);
        }
    }

    public HashMap<String , String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(USERNAME, pref.getString(USERNAME,null));
        user.put(LEVEL_JP, pref.getString(LEVEL_JP,null));
        user.put(LEVEL_EN, pref.getString(LEVEL_EN,null));
        user.put(QUIZ_JP, pref.getString(QUIZ_JP,null));
        user.put(QUIZ_EN, pref.getString(QUIZ_EN,null));
        user.put(TEMP, pref.getString(TEMP,null));
        return user;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        _context.startActivity(i);
    }
}
