package com.armijos.inventarioapp.mainModule;

import com.armijos.inventarioapp.common.pojo.Product;
import com.armijos.inventarioapp.mainModule.events.MainEvent;

public interface MainPresenter {
    void onCreate();
    void onPause();
    void onResume();
    void onDestroy();

    void remove(Product product);

    void onEventListener(MainEvent event);
}
