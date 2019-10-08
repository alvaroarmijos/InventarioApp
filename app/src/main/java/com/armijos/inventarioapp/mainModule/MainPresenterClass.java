package com.armijos.inventarioapp.mainModule;

import com.armijos.inventarioapp.common.pojo.Product;
import com.armijos.inventarioapp.mainModule.events.MainEvent;
import com.armijos.inventarioapp.mainModule.model.MainInteractor;
import com.armijos.inventarioapp.mainModule.model.MainInteractorClass;
import com.armijos.inventarioapp.mainModule.view.MainView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainPresenterClass implements MainPresenter {
    private MainView mView;
    private MainInteractor mInteractor;

    public MainPresenterClass(MainView mView) {
        this.mView = mView;
        this.mInteractor = new MainInteractorClass();
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        mInteractor.unsubscribeToProducts();
    }

    @Override
    public void onResume() {
        mInteractor.subscribeToProducts();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mView = null;
    }

    @Override
    public void remove(Product product) {
        if (setProgress()){
            mInteractor.removeProduct(product);
        }
    }

    private boolean setProgress() {
        if (mView != null){
            mView.showProgress();
            return true;
        }
        return false;
    }

    @Subscribe
    @Override
    public void onEventListener(MainEvent event) {
        if (mView != null){
            mView.hideProgress();

            switch (event.getTypeEvent()){
                case MainEvent.SUCCES_ADD:
                    mView.add(event.getProduct());
                    break;
                case MainEvent.SUCCES_UPDATE:
                    mView.update(event.getProduct());
                    break;
                case MainEvent.SUCCES_REMOVE:
                    mView.remove(event.getProduct());
                    break;
                case MainEvent.ERROR_SERVER:
                    mView.onShowError(event.getResMSG());
                    break;
                case MainEvent.ERROR_TO_REMOVE:
                    mView.removeFail();
                    break;
            }
        }
    }
}
