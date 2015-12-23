package com.example.jino.jsontest1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class MainActivity extends Activity {

    TextView textView; // ����� textView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView); // �ؽ�Ʈ�� ��ü ����
        new JsonLoadingTask().execute();
    } // onCreate


    /**
     * ������ �����͸� ������ JSON ��ü�� ������ ������ ��ü���� ������ Ÿ�Ժ��� �����͸� �о StringBuffer�� �߰��Ѵ�.
     */
    public String getJsonText() {

        // ���������� ���ڿ� ������ ������ StringBuffer ������
        StringBuffer sb = new StringBuffer();

        try {
            String line = getStringFromUrl("http://192.168.0.22:8080/board/test_board1.jsp");



            // ���ݿ��� �о�� �����ͷ� JSON ��ü ����
          // JSONObject object = new JSONObject(line);
    JSONArray array = new JSONArray(line);
            // "kkt_list" �迭�� ���� �Ǿ������Ƿ� JSON �迭����
         //   JSONArray array = new JSONArray(object.getString("kkt_list"));

            for (int i = 0; i < array.length(); i++) {
                // bodylist �迭�ȿ� ���� JSON �̹Ƿ� JSON ���� ��ü ����
                JSONObject insideObject = array.getJSONObject(i);

                // StringBuffer �޼ҵ� ( append : StringBuffer �ν��Ͻ��� �ڿ� �����δ�. )
                // JSONObject �޼ҵ� ( get.String(), getInt(), getBoolean() .. �� : ��ü�κ��� �������� Ÿ�Կ� ���� ���ϴ� �����͸� �д´�. )

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


    // getStringFromUrl : �־��� URL �������� ���ڿ��� ��´�.
    public String getStringFromUrl(String url) throws UnsupportedEncodingException {

        // �Է½�Ʈ���� "UTF-8" �� ����ؼ� ���� ��, ���� ������ �����͸� ���� �� �ִ� BufferedReader �� �����Ѵ�.
        BufferedReader br = new BufferedReader(new InputStreamReader(getInputStreamFromUrl(url), "UTF-8"));

        // ���� �����͸� ������ StringBuffer �� �����Ѵ�.
        StringBuffer sb = new StringBuffer();

        try {
            // ���� ������ ���� �����͸� �ӽ� ������ ���ڿ� ���� line
            String line = null;

            // ���� ������ �����͸� �о StringBuffer �� �����Ѵ�.
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    } // getStringFromUrl


    /**
     *  getInputStreamFromUrl : �־��� URL �� ���� �Է� ��Ʈ��(InputStream)�� ��´�.
     */
    public static InputStream getInputStreamFromUrl(String url) {
        InputStream contentStream = null;
        try {
            // HttpClient �� ����ؼ� �־��� URL�� ���� �Է� ��Ʈ���� ��´�.
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            contentStream = response.getEntity().getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentStream;
    } // getInputStreamFromUrl


    /**
     *	�����忡�� ���� AsyncTask �� �̿��Ͽ�
     * UI ó�� �� Background �۾� ���� �ϳ��� Ŭ�������� �۾� �� �� �ֵ��� �������ش�.
     */
    private class JsonLoadingTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strs) {
            return getJsonText();
        } // doInBackground : ��׶��� �۾��� �����Ѵ�.
        @Override
        protected void onPostExecute(String result) {
            textView.setText(result);
        } // onPostExecute : ��׶��� �۾��� ���� �� UI �۾��� �����Ѵ�.
    } // JsonLoadingTask
} // end