package com.example.retail.Room;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.retail.ProductModel;

import java.util.List;

@Dao
public interface ProductDAO {


    @Insert
    void insertProduct(ProductModel productModel);

    @Query("SELECT * FROM products")
    List<ProductModel> getAllProducts();


    @Query("DELETE FROM products")
    void deleteAllProducts();

    @Update
    void updateProduct(ProductModel productModel);
}
