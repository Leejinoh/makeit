package com.example.jino.jsontest2;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


public class MainActivity2Activity extends Activity {

    TextView textView; // 출력할 textView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);

        textView = (TextView) findViewById(R.id.textView); // 텍스트뷰 객체 참조
        new JsonLoadingTask().execute();
    } // onCreate


    /**
     * 원격의 데이터를 가지고 JSON 객체를 생성한 다음에 객체에서 데이터 타입별로 데이터를 읽어서 StringBuffer에 추가한다.
     */
    public String getJsonText() {

        // 내부적으로 문자열 편집이 가능한 StringBuffer 생성자
        StringBuffer sb = new StringBuffer();

        try {
            String line = getStringFromUrl("http://trueclever.cafe24.com/board/test_board1.jsp");



            // 원격에서 읽어온 데이터로 JSON 객체 생성
            // JSONObject object = new JSONObject(line);
            JSONArray array = new JSONArray(line);
            // "kkt_list" 배열로 구성 되어있으므로 JSON 배열생성
            //   JSONArray array = new JSONArray(object.getString("kkt_list"));

            for (int i = 0; i < array.length(); i++) {
                // bodylist 배열안에 내부 JSON 이므로 JSON 내부 객체 생성
                JSONObject insideObject = array.getJSONObject(i);

                // StringBuffer 메소드 ( append : StringBuffer 인스턴스에 뒤에 덧붙인다. )
                // JSONObject 메소드 ( get.String(), getInt(), getBoolean() .. 등 : 객체로부터 데이터의 타입에 따라 원하는 데이터를 읽는다. )

                sb.append("sequence : ").append(insideObject.getString("sequence")).append("\n");
                sb.append("author : ").append(insideObject.getString("author")).append("\n");
                sb.append("title : ").append(insideObject.getString("title")).append("\n");
                sb.append("message : ").append(insideObject.getString("message")).append("\n");
               /* sb.append("body : ").append(insideObject.getString("body")).append("\n");
                sb.append("store : ").append(insideObject.getString("store")).append("\n");
                sb.append("category : ").append(insideObject.getString("category")).append("\n");*/

                sb.append("\n");
                sb.append("\n");

            } // for
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    } // getJsonText


    // getStringFromUrl : 주어진 URL 페이지를 문자열로 얻는다.
    public String getStringFromUrl(String url) throws UnsupportedEncodingException {

        // 입력스트림을 "UTF-8" 를 사용해서 읽은 후, 라인 단위로 데이터를 읽을 수 있는 BufferedReader 를 생성한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(getInputStreamFromUrl(url), "UTF-8"));

        // 읽은 데이터를 저장한 StringBuffer 를 생성한다.
        StringBuffer sb = new StringBuffer();

        try {
            // 라인 단위로 읽은 데이터를 임시 저장한 문자열 변수 line
            String line = null;

            // 라인 단위로 데이터를 읽어서 StringBuffer 에 저장한다.
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    } // getStringFromUrl


    /**
     *  getInputStreamFromUrl : 주어진 URL 에 대한 입력 스트림(InputStream)을 얻는다.
     */
    public static InputStream getInputStreamFromUrl(String url) {
        InputStream contentStream = null;
        try {
            // HttpClient 를 사용해서 주어진 URL에 대한 입력 스트림을 얻는다.
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            contentStream = response.getEntity().getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentStream;
    } // getInputStreamFromUrl


    /**
     *	스레드에서 향상된 AsyncTask 를 이용하여
     * UI 처리 및 Background 작업 등을 하나의 클래스에서 작업 할 수 있도록 지원해준다.
     */
    private class JsonLoadingTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strs) {
            return getJsonText();
        } // doInBackground : 백그라운드 작업을 진행한다.
        @Override
        protected void onPostExecute(String result) {
            textView.setText(result);
        } // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
    } // JsonLoadingTask
} // end
