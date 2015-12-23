/*
 *
 * Created By KangSan, Lee / 2015.9.22
 * Copyright (c) Make-IT 2015.
 *
 * ===================================
 *
 * AsyncCallback.java
 *
 * ===================================
 */


package com.example.jino.jsontest2;

/**
 * AsyncCallback interface
 */
public interface AsyncCallback {
    void responseBody(String body, int responseCode);
}
