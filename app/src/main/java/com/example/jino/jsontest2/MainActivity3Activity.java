package com.example.jino.jsontest2;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.HTTP;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class MainActivity3Activity extends Activity {
    EditText name;
    EditText title;
    EditText message;
    private JsonLoadingTask jsonLoadingTask;
    String n;
    String t;
    String m;
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity3);

       //  sequence = (EditText)findViewById(R.id.editText);


        Button save = (Button)findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = (EditText)findViewById(R.id.editText2);
                title = (EditText)findViewById(R.id.editText3);
                message = (EditText)findViewById(R.id.editText4);

                n = name.getText().toString();
                t = title.getText().toString();
                m = message.getText().toString();

                //Toast.makeText(getApplicationContext(),m,Toast.LENGTH_SHORT).show();


                jsonLoadingTask = new JsonLoadingTask(new AsyncCallback() {
                    @Override
                    public void responseBody(String body, int responseCode) {

                    }
                });
                jsonLoadingTask.setParams(n,t,m);
                jsonLoadingTask.execute();
             }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
