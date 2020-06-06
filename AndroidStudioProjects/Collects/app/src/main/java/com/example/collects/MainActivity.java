package com.example.collects;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText usernameText,passwordText;
    Button Login_btn,Register_btn;

    String username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);

        Login_btn = findViewById(R.id.Login_btn);
        Login_btn.setOnClickListener(this);

        Register_btn = findViewById(R.id.Register_btn);
        Register_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Login_btn:
                username = usernameText.getText().toString();
                password = passwordText.getText().toString();
                new MethodSelect().execute();
                break;
            case R.id.Register_btn:
                Intent intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
    public class MethodSelect extends AsyncTask<String, Void , String>{
        String server_response;
        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpsURLConnection urlConnection = null;

            try {
                url = new URL("https://natdanaispace.cf/android/select_user.php");
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
                    obj.put("username",username);
                    obj.put("password",password);


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

            if(!(server_response.equals("false"))) {
                Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(MainActivity.this,LoginsucActivity.class);
                        intent.putExtra("data",server_response.toString());
                        startActivity(intent);
                    }
                }, 1500);
            } else
               Toast.makeText(getApplicationContext(), "Login Fail " , Toast.LENGTH_SHORT).show();
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
