package com.example.retail.webServices;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebServices {


    @GET("products")
    Call<ProductsResponse> getProducts();
}
