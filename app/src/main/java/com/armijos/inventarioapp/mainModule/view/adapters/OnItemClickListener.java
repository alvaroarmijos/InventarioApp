package com.armijos.inventarioapp.mainModule.view.adapters;

import com.armijos.inventarioapp.common.pojo.Product;

public interface OnItemClickListener {
    void onItemClick(Product product);
    void onLongItemClick(Product product);
}
