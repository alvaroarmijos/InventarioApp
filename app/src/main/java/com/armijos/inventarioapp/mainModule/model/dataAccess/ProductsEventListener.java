package com.armijos.inventarioapp.mainModule.model.dataAccess;

import com.armijos.inventarioapp.common.pojo.Product;

public interface ProductsEventListener {
    void onChildAdded(Product product);
    void onChildUpdated(Product product);
    void onChildRemoved(Product product);

    void onError(int resMag);
}
