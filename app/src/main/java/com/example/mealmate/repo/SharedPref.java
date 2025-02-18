package com.example.mealmate.repo;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    SharedPreferences sharedPreferences;
    private  static SharedPref instance;
    final static  String FILENAME ="sharedPrefFile";
    SharedPreferences.Editor editor;
    public final String IS_LOGGED="isLogged";

    public final String USERID="USER";

    private SharedPref(Context context){
        sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(FILENAME,0);
        editor = sharedPreferences.edit();
    }
    public static synchronized SharedPref getInstance(Context context){
        if(instance==null){
            instance= new SharedPref(context);
        }
        return instance;
    }
    public void setLogged(Boolean isLogged){
        editor.putBoolean(IS_LOGGED,isLogged);
        editor.commit();
    }
    public  boolean isLogged(){
        Boolean bool;
        bool= sharedPreferences.getBoolean(IS_LOGGED,false);
        return bool;
    }

    public void setUSERID(String uid){
        editor.putString(USERID,uid);
        editor.commit();
    }
    public String getUSERID(){
        return sharedPreferences.getString(USERID,null);

}


}
