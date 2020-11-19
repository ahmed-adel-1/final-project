package com.example.retail.AsyncTask;

import android.os.AsyncTask;

import com.example.retail.ProductModel;
import com.example.retail.Room.ProductDAO;

public class InsertAsyncTask extends AsyncTask<ProductModel,Void,Void> {

    private ProductDAO productDAO;

    public InsertAsyncTask(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    protected Void doInBackground(ProductModel... productModels) {

        productDAO.insertProduct(productModels[0]);
        return null;
    }
}
