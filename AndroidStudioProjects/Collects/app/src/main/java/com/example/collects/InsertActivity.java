package com.example.collects;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class InsertActivity extends AppCompatActivity {

    String uid,score,subject,data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        score = intent.getStringExtra("score");
        data = intent.getStringExtra("data");
        subject = intent.getStringExtra("subject");
        new MethodInsert().execute();
    }

    public void success(){

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(InsertActivity.this,LoginsucActivity.class);
                intent.putExtra("data",data);
                startActivity(intent);
            }
        }, 100);
    }

    public class MethodInsert extends AsyncTask<String, Void , String> {
        String server_response;

        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpsURLConnection urlConnection = null;

            try {
                url = new URL("https://natdanaispace.cf/android/insert_data.php?api_key=KDXWS4z");
                urlConnection = (HttpsURLConnection) url.openConnection();

                urlConnection.setReadTimeout(15000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                try {
                    JSONObject obj = new JSONObject();
                    obj.put("uid",uid);
                    obj.put("subject",subject);
                    obj.put("score",Double.parseDouble(score));

                    writer.write(getPostDataString(obj));
                    writer.flush();
                    writer.close();
                    os.close();
                }catch (JSONException ex){
                    ex.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                }

                urlConnection.connect();

                int responseCode = urlConnection.getResponseCode();
                if(responseCode == HttpsURLConnection.HTTP_OK){
                    server_response = readStream(urlConnection.getInputStream());
                }

            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String s){
            super.onPostExecute(s);
            success(); // GO TO LOGIN SUC
        }

        public String getPostDataString(JSONObject params) throws Exception{
            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();
            while (itr.hasNext()){
                String key = itr.next();
                Object value = params.get(key);

                if(first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key,"UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(),"UTF-8"));
            }
            return  result.toString();
        }

        public String readStream(InputStream in){
            BufferedReader reader = null;
            StringBuffer response = new StringBuffer();

            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null){
                    response.append(line);
                }
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                if(reader != null){
                    try {
                        reader.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
            return response.toString();
        }

    }
}
