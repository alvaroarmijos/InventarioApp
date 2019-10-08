package com.armijos.inventarioapp.addModule.model;

import com.armijos.inventarioapp.addModule.events.AddProductEvent;
import com.armijos.inventarioapp.addModule.model.dataAccess.RealtimeDatabase;
import com.armijos.inventarioapp.common.BasicErrorEventCallback;
import com.armijos.inventarioapp.common.pojo.Product;

import org.greenrobot.eventbus.EventBus;

public class AddProductInteractorClass implements AddProductinteractor {
    private RealtimeDatabase mDatabase;

    public AddProductInteractorClass() {
        mDatabase = new RealtimeDatabase();
    }

    @Override
    public void addProduct(Product product) {
        mDatabase.addProduct(product, new BasicErrorEventCallback() {
            @Override
            public void onSucces() {
                post(AddProductEvent.SUCCESS_ADD);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {

            }
        });

    }

    private void post(int typeEvent) {
        post(typeEvent, 0);

    }

    private void post(int typeEvent, int resMsg) {
        AddProductEvent event = new AddProductEvent();
        event.setTypeEvent(typeEvent);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }
}
