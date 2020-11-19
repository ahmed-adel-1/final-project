package com.example.retail;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "products")
public class ProductModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long incrementalid;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "details")
    @SerializedName("name")
    private String details;

    @ColumnInfo(name = "price")
    @SerializedName("price")
    private  double price;

    @ColumnInfo(name = "image")
    @SerializedName("avatar")
    private String image;

    @ColumnInfo(name = "description")
    @SerializedName("description")
    private  String description;


    @ColumnInfo(name = "quantity")
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductModel(String title, String details, double price, String description) {
        this.title = title;
        this.details = details;
        this.price = price;
        this.description = description;
    }

    public long getIncrementalid() {
        return incrementalid;
    }

    public void setIncrementalid(long incrementalid) {
        this.incrementalid = incrementalid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

