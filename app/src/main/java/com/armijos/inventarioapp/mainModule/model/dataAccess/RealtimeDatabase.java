package com.armijos.inventarioapp.mainModule.model.dataAccess;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.armijos.inventarioapp.R;
import com.armijos.inventarioapp.common.BasicErrorEventCallback;
import com.armijos.inventarioapp.common.model.dataAccess.FirebaseRealtimeDatabaseAPI;
import com.armijos.inventarioapp.common.pojo.Product;
import com.armijos.inventarioapp.mainModule.events.MainEvent;
import com.armijos.inventarioapp.mainModule.model.dataAccess.ProductsEventListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class RealtimeDatabase {


    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;
    private ChildEventListener mProductsChildEventListener;

    public RealtimeDatabase () {
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }



    public void subscribeToProducts(ProductsEventListener listener){
        if (mProductsChildEventListener == null){
            mProductsChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    listener.onChildAdded(getProduct(dataSnapshot));
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    listener.onChildUpdated(getProduct(dataSnapshot));
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    listener.onChildRemoved(getProduct(dataSnapshot));
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    switch (databaseError.getCode()){
                        case DatabaseError.PERMISSION_DENIED:
                            listener.onError(R.string.main_error_permission_denied);
                            break;
                        default:
                            listener.onError(R.string.main_error_server);
                    }
                }
            };
        }

        mDatabaseAPI.getProductsReference().addChildEventListener(mProductsChildEventListener);

    }

    private Product getProduct(DataSnapshot dataSnapshot) {
        Product product = dataSnapshot.getValue(Product.class);
        if (product != null){
            product.setId(dataSnapshot.getKey());
        }
        return product;
    }

    public void unsubscribeToProducts(){
        if (mProductsChildEventListener != null){
            mDatabaseAPI.getProductsReference().removeEventListener(mProductsChildEventListener);
        }
    }

    public void removeProduct (Product product, BasicErrorEventCallback callback){
        mDatabaseAPI.getProductsReference().child(product.getId())
                .removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError == null){
                            callback.onSucces();
                        } else {
                            switch (databaseError.getCode()){
                                case DatabaseError.PERMISSION_DENIED:
                                    callback.onError(MainEvent.ERROR_TO_REMOVE, R.string.main_error_remove);
                                    break;
                                default:
                                    callback.onError(MainEvent.ERROR_SERVER, R.string.main_error_server);
                            }
                        }
                    }
                });

    }
}
