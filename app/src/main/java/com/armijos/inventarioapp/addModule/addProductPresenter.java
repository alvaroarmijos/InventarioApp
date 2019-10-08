package com.armijos.inventarioapp.addModule;

import com.armijos.inventarioapp.addModule.events.AddProductEvent;
import com.armijos.inventarioapp.common.pojo.Product;

public interface addProductPresenter {

    void onShow();
    void onDestroy();

    void addProduct(Product product);

    void onEventListener(AddProductEvent event);
}
