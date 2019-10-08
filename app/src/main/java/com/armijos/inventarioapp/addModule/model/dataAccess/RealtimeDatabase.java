package com.armijos.inventarioapp.addModule.model.dataAccess;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.armijos.inventarioapp.R;
import com.armijos.inventarioapp.addModule.events.AddProductEvent;
import com.armijos.inventarioapp.common.BasicErrorEventCallback;
import com.armijos.inventarioapp.common.model.dataAccess.FirebaseRealtimeDatabaseAPI;
import com.armijos.inventarioapp.common.pojo.Product;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.core.SyncTree;

public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;

    public RealtimeDatabase() {
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    public void addProduct(Product product, final BasicErrorEventCallback callback){
        mDatabaseAPI.getProductsReference().push().setValue(product, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null){
                    callback.onSucces();
                }else {
                    switch (databaseError.getCode()){
                        case DatabaseError.PERMISSION_DENIED:
                            callback.onError(AddProductEvent.ERROR_MAX_VALUE,
                                    R.string.addProduct_message_validate_max_quantity);
                            break;
                        default:
                            callback.onError(AddProductEvent.ERROR_SERVER,
                                    R.string.addProduct_message_added_error);
                            break;
                    }
                }

            }

        });
    }
}
