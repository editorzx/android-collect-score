package com.example.collects;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class LoginsucActivity extends AppCompatActivity implements View.OnClickListener {
    String data;
    String idglobal;
    String name,user;
    String idforuse,subjectforuse,scoreforuse;

    ListView listView;
    TextView hellotext,usertext;

    String[] values = null;
    String[] subjects ;
    String[] scores;
    String[] time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginsuc);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInsert("DATA ");
            }
        });

        listView = findViewById(R.id.mylistview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String clickedvalue = (String) parent.getItemAtPosition(position);
                idforuse = values[position];
                subjectforuse = subjects[position];
                scoreforuse = scores[position];

                StringBuffer buffer= new StringBuffer();
                buffer.append("Subject : " + subjectforuse + "\n\n");
                buffer.append("Scores : " + scoreforuse + "\n\n");
                buffer.append("Last update : " + time[position] + "\n\n");

                showData("DATA ",buffer.toString());
            }
        });

        Intent intent = getIntent();
        data = intent.getStringExtra("data");

        //GET DATA FROM JSON
        getdata();
        /////////////////////////////////////

        hellotext = findViewById(R.id.hellotext);
        hellotext.setText("Hello : " + name + "\n" + "User : " + user);

        new ShowDataTask().execute();
    }

    private void showInsert(String title)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LinearLayout layout2 = new LinearLayout(this);
        layout2.setOrientation(LinearLayout.VERTICAL);

        final EditText subject = new EditText(this);
        subject.setHint("ชื่อวิชา");
        subject.setEnabled(true);
        layout2.addView(subject);
        final EditText score = new EditText(this);
        score.setHint("คะแนนปัจจุบัน");
        score.setInputType(InputType.TYPE_CLASS_NUMBER |  InputType.TYPE_NUMBER_FLAG_DECIMAL);
        score.setEnabled(true);
        layout2.addView(score);
        builder.setView(layout2);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (score.getText().toString().matches("") || subject.getText().toString().matches("")) {
                    dialog.cancel();
                    return;
                }
                Intent intent = new Intent(LoginsucActivity.this,InsertActivity.class);
                intent.putExtra("uid",idglobal);
                intent.putExtra("score",score.getText().toString());
                intent.putExtra("subject",subject.getText().toString());
                intent.putExtra("data",data);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        builder.setCancelable(true);
        builder.setTitle(title);
        builder.show();

    }

    private void showData(String title, String found)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LinearLayout layout2 = new LinearLayout(this);
        layout2.setOrientation(LinearLayout.VERTICAL);

        final EditText subject = new EditText(this);
        subject.setText(subjectforuse);
        subject.setEnabled(true);
        layout2.addView(subject);
        final EditText score = new EditText(this);
        score.setText(scoreforuse);
        score.setInputType(InputType.TYPE_CLASS_NUMBER |  InputType.TYPE_NUMBER_FLAG_DECIMAL);
        score.setEnabled(true);
        layout2.addView(score);
        builder.setView(layout2);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (score.getText().toString().matches("") || subject.getText().toString().matches("")) {
                    dialog.cancel();
                    return;
                }
                Intent intent = new Intent(LoginsucActivity.this,UpdateActivity.class);
                intent.putExtra("id",idforuse);
                intent.putExtra("score",score.getText().toString());
                intent.putExtra("subject",subject.getText().toString());
                intent.putExtra("data",data);
                startActivity(intent);
            }
        });
        builder.setNeutralButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(LoginsucActivity.this,RemoveActivity.class);
                intent.putExtra("id",idforuse);
                intent.putExtra("data",data);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(found);
        builder.show();

    }

    public void getdata(){
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0 ; i < jsonArray.length(); i++){
                JSONObject contactObject = jsonArray.optJSONObject(i);
                idglobal = contactObject.optString("uid");
                name = contactObject.optString("name");
                user = contactObject.optString("username");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

    }

    public class ShowDataTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                HttpsURLConnection urlConnection = null;
                String line = "";

                StringBuilder sb = new StringBuilder();

                try
                {
                    URL myUrl = new URL("https://natdanaispace.cf/android/select_data.php?api_key=KDXWS4z&id="+idglobal.toString());
                    urlConnection = (HttpsURLConnection)myUrl.openConnection();
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                    while ((line = in.readLine()) != null)
                    {
                        sb.append(line).append('\n');
                    }
                }catch (Exception e){
                    e.printStackTrace();

                }finally {
                    if (urlConnection != null){
                        urlConnection.disconnect();
                    }
                }
                return sb.toString();
            }
            catch (Exception e){
                return e.toString();
            }
        }

        protected void onPostExecute(String Result){
            ArrayList<String> myList = new ArrayList<String>();
            try {

                JSONArray jsonArray = new JSONArray(Result);
                values = new String[jsonArray.length()];
                subjects = new String[jsonArray.length()];
                scores = new String[jsonArray.length()];
                time = new String[jsonArray.length()];
                for (int i = 0 ; i < jsonArray.length(); i++){
                    JSONObject contactObject = jsonArray.optJSONObject(i);
                    myList.add("Subject : (" + contactObject.optString("subject") + ") \t Score : (" + contactObject.optString("score")+")");
                    values[i] = contactObject.optString("id");
                    subjects[i] = contactObject.optString("subject");
                    scores[i] = contactObject.optString("score");
                    time[i] = contactObject.optString("timestamp");
                }
            }catch (Exception ex){}

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(LoginsucActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1,myList);
            listView.setAdapter(adapter);
        }
    }

}
