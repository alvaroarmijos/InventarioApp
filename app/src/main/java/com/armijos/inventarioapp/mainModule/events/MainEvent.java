package com.armijos.inventarioapp.mainModule.events;

import com.armijos.inventarioapp.common.pojo.Product;

public class MainEvent {
    public  static final int SUCCES_ADD =0;
    public  static final int SUCCES_UPDATE =1;
    public  static final int SUCCES_REMOVE =2;
    public  static final int ERROR_SERVER =100;
    public  static final int ERROR_TO_REMOVE =101;

    private Product product;
    private int typeEvent;
    private int resMSG;

    public MainEvent() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(int typeEvent) {
        this.typeEvent = typeEvent;
    }

    public int getResMSG() {
        return resMSG;
    }

    public void setResMSG(int resMSG) {
        this.resMSG = resMSG;
    }
}
