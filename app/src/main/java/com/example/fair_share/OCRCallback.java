package com.example.fair_share;

public interface OCRCallback {
    void onSuccess(String result);
    void onError(String error);
}