package com.example.rkluwer.cleeviofilesearchexercise;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

public class GetPathAsync extends AsyncTask {

    private SharedPreferences sharedPreferences;
    public AsyncResponse response = null;

    public void setPreferences(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    protected void onPreExecute() {
        Log.d("getPathAsync", "Asynctask started");
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        ArrayList<String> pathHistory = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        String path = sharedPreferences.getString(ConstantValues.PREF_KEY_SET_DEFAULT_FOLDER, null);

        String[] splitPath = new String[0];
        if (path != null) {
            splitPath = path.split("/");
        }
        int count = splitPath.length - 2;
        Log.d("getPathAsync", "splitPath length is: " + count);

        int tempCount = -1;
        for (String p: splitPath){
            if (tempCount != -1) {
                stringBuilder.append("/" + p);

                pathHistory.add(tempCount, stringBuilder.toString());

            }
            tempCount++;
        }

        if (count > 0) {
            response.setCountAndHistory(count, pathHistory);
        }
        return null;
    }
}
