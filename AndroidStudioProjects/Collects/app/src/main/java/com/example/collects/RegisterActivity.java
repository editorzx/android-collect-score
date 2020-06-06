package com.example.collects;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    String username,password,name;
    EditText ruserText,rpassText,nameText;
    Button reg_btn,cancel_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ruserText = findViewById(R.id.ruserText);
        rpassText = findViewById(R.id.rpassText);
        nameText = findViewById(R.id.nameText);

        reg_btn = findViewById(R.id.reg_btn);
        cancel_btn = findViewById(R.id.cancel_btn);

        reg_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);
    }

    public void success(){
        Toast.makeText(getApplicationContext(), "Register Process , Please one moment .", Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        }, 1500);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reg_btn:
                username = ruserText.getText().toString();
                password = rpassText.getText().toString();
                name = nameText.getText().toString();
                if (name.matches("") || username.matches("") || password.matches("")) {
                    Toast.makeText(this, "Please all field", Toast.LENGTH_SHORT).show();
                    return;
                }
                new MethodRegister().execute();
                break;
            case R.id.cancel_btn:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    public class MethodRegister extends AsyncTask<String, Void , String> {
        String server_response;

        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpsURLConnection urlConnection = null;

            try {
                url = new URL("https://natdanaispace.cf/android/register.php?api_key=KDXWS4z");
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
                    obj.put("name",name);

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
