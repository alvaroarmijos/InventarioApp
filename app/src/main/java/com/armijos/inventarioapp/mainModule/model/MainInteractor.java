package com.armijos.inventarioapp.mainModule.model;

import com.armijos.inventarioapp.common.pojo.Product;

public interface MainInteractor {
    void subscribeToProducts();
    void unsubscribeToProducts();

    void removeProduct(Product product);
}
