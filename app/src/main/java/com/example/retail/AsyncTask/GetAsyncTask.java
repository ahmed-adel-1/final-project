package com.example.retail.AsyncTask;

import android.os.AsyncTask;

import com.example.retail.ProductModel;
import com.example.retail.Room.ProductDAO;

import java.util.List;

public class GetAsyncTask extends AsyncTask<Void,Void, List<ProductModel>> {
    private ProductDAO productDAO;

    public GetAsyncTask(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    protected List<ProductModel> doInBackground(Void... voids) {
        return productDAO.getAllProducts();
    }
}
