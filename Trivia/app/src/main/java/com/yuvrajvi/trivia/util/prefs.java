package com.yuvrajvi.trivia.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class prefs {
    private SharedPreferences preferences;

    public prefs(Activity activity) {
        this.preferences=activity.getPreferences(Context.MODE_PRIVATE);
    }
    public void savehighscore(Double score){
        Double currentscore=score;
        Double lastscore= Double.valueOf(preferences.getFloat("HighScore",0));
        if(currentscore>lastscore){
            preferences.edit().putFloat("HighScore",Float.valueOf(String.valueOf(currentscore))).apply();
        }
    }

    public Double getHighScore(){
        return Double.valueOf(preferences.getFloat("HighScore",0));
    }
    public void saveindex(int index){
        preferences.edit().putInt("index",index).apply();
    }
    public int getindex(){
        return preferences.getInt("index",0);
    }
    public void savecurrentscore(Double currentscore){
        preferences.edit().putFloat("currentscore",Float.valueOf(String.valueOf(currentscore))).apply();
    }
    public Double getcurrentscore(){
        return Double.valueOf(preferences.getFloat("currentscore",0));
    }
    public void savearray(int array[],int size){
        for(int i=0;i<size;i++){
            preferences.edit().putString("savedarray_"+i,String.valueOf(array[i])).apply();
        }
    }
    public int[] getarray(int size){
        int[] a=new int[size];
        for(int i=0;i<size;i++){
            a[i]=Integer.valueOf(preferences.getString("savedarray_"+i,"0"));
        }
        return a;
    }
}
