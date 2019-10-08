package com.armijos.inventarioapp.mainModule.view;

import com.armijos.inventarioapp.common.pojo.Product;

public interface MainView {
    void showProgress();
    void hideProgress();

    void add(Product product);
    void update(Product product);
    void remove(Product product);

    void onShowError(int resMessage);
    void removeFail();
}
