package com.example.javaasyncapps;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RetrieveUrl extends AsyncTask<URL,String,String> {


    interface RetrieveUrlInterface {
        void callbackResp(String resp);
    }

    private RetrieveUrlInterface listener;

    public RetrieveUrl(RetrieveUrlInterface listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(URL... urls) {
        String data = "";
        try {
            URL url = new URL("https://api.jikan.moe/v4/anime");

            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setConnectTimeout(60000);
            urlConn.setRequestMethod("GET");
            urlConn.connect();
            Log.d("data_check",String.valueOf(urlConn.getResponseCode()));
            if(urlConn.getResponseCode() == 200) {
                InputStream inputstream = new BufferedInputStream(urlConn.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputstream));
                StringBuilder sb = new StringBuilder();
                String line;
                try {
                    while((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                data = sb.toString();

            } else {
                data = "failure response";
            }
        } catch (Exception e) {
            data = e.getMessage();
        }
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        listener.callbackResp(s);
    }
}
