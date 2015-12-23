package com.example.jino.jsontest2;

import android.os.AsyncTask;
import android.provider.SyncStateContract;

import java.util.HashMap;

public class JsonLoadingTask extends AsyncTask<String, Void, String> {
    private HttpRequest httpRequest;
    private AsyncCallback asyncCallback;
    private int responseCode;
    private HashMap<String, String> params;

    public JsonLoadingTask(AsyncCallback asyncCallback) {
        this.asyncCallback = asyncCallback;
        params = new HashMap<>();
    }

    @Override
    protected String doInBackground(String... v) {
        httpRequest = httpRequest.get("http://trueclever.cafe24.com/board/test_input.jsp", params, true);
        responseCode = httpRequest.code();
        return httpRequest.body();
    } // doInBackground : 백그라운드 작업을 진행한다.

    public void setParams(String name, String title, String message){
        params.put("name", name);
        params.put("title", title);
        params.put("message", message);
    }

    @Override
    protected void onPostExecute(String body) {

        asyncCallback.responseBody(body, responseCode);
    }
}