package com.armijos.inventarioapp.addModule.view;

public interface AddProductView {
    void enableUIElements();
    void disableUIElements();
    void showProgress();
    void hideProgress();

    void productAdded();
    void showError(int resMsg);
    void maxValueError(int resMSG);
}
