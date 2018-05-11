package com.example.matti.svegliamultifunzione.news;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetLatestNews {

    public interface AsyncResponse {

        void processFinish(JSONArray articles);
    }

    static class DownloadNews extends AsyncTask<Object, Object, Object> {

        private final String URL = "https://newsapi.org/v2/top-headlines?country=it&apiKey=b166bc49d84740ad99e0dd0d85980d1f";

        public AsyncResponse delegate = null;


        public DownloadNews(AsyncResponse asyncResponse) {
            Log.d("DELEGATE", "ISDELEGATING");
            delegate = asyncResponse;//Assigning call back interfacethrough constructor
        }

        @Override
        protected JSONArray doInBackground(Object[] objects) {
            try {
                URL url = new URL(String.format(URL));
                Log.d("URL", url.toString());
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));

                StringBuffer json = new StringBuffer();
                String tmp = "";
                while ((tmp = reader.readLine()) != null)
                    json.append(tmp);
                reader.close();
                JSONArray articles = new JSONObject(json.toString()).getJSONArray("articles");
                return articles;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(o!=null)
            delegate.processFinish((JSONArray)o);
        }
    }
}
