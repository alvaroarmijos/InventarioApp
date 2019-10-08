package com.armijos.inventarioapp.common;

public interface BasicErrorEventCallback {
    void onSucces();
    void onError(int typeEvent, int resMsg);
}
