package com.armijos.inventarioapp.mainModule.model;

import com.armijos.inventarioapp.common.BasicErrorEventCallback;
import com.armijos.inventarioapp.common.pojo.Product;
import com.armijos.inventarioapp.mainModule.events.MainEvent;
import com.armijos.inventarioapp.mainModule.model.dataAccess.ProductsEventListener;
import com.armijos.inventarioapp.mainModule.model.dataAccess.RealtimeDatabase;

import org.greenrobot.eventbus.EventBus;

public class MainInteractorClass implements MainInteractor {
    private RealtimeDatabase mDatabase;

    public MainInteractorClass() {
        mDatabase = new RealtimeDatabase();
    }

    @Override
    public void subscribeToProducts() {
        mDatabase.subscribeToProducts(new ProductsEventListener() {
            @Override
            public void onChildAdded(Product product) {
                post(product, MainEvent.SUCCES_ADD);
            }

            @Override
            public void onChildUpdated(Product product) {
                post(product, MainEvent.SUCCES_UPDATE);
            }

            @Override
            public void onChildRemoved(Product product) {
                post(product, MainEvent.SUCCES_REMOVE);
            }

            @Override
            public void onError(int resMag) {
                post(MainEvent.ERROR_SERVER, resMag);
            }
        });

    }

    @Override
    public void unsubscribeToProducts() {
        mDatabase.unsubscribeToProducts();
    }

    @Override
    public void removeProduct(Product product) {
        mDatabase.removeProduct(product, new BasicErrorEventCallback() {
            @Override
            public void onSucces() {
                post(MainEvent.SUCCES_REMOVE);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {
                post(typeEvent, resMsg);
            }
        });
    }

    private void post (int typeEvent){
        post(null, typeEvent, 0);
    }

    private void post(int typeEvent, int resMsg){
        post(null, typeEvent, resMsg);
    }

    private void post(Product product, int typeEvent){
        post(product, typeEvent, 0);
    }

    private void post(Product product, int typeEvent, int resMsg) {
        MainEvent event = new MainEvent();
        event.setProduct(product);
        event.setTypeEvent(typeEvent);
        event.setResMSG(resMsg);
        EventBus.getDefault().post(event);
    }
}
