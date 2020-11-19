package com.example.retail.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.retail.ProductModel;
import com.example.retail.R;


public class DetailsFragment extends Fragment {

    private ImageView productiv;
    private TextView  producTitle;
    private TextView  producDetails;
    private TextView  producPrice;
    private TextView  productMoreDetails;
    private Button addbtn;
    ProductModel productModel = null;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        productiv = view.findViewById(R.id.product_title_iv);
        producTitle = view.findViewById(R.id.product_title_details_tv);
        producDetails = view.findViewById(R.id.product_details_details_tv);
        producPrice = view.findViewById(R.id.product_price_details_tv);
        productMoreDetails = view.findViewById(R.id.more_details_details_tv);
        addbtn = view.findViewById(R.id.addtocart_title_btn);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getProductObjectFromHomeFragment();
    }

    private void getProductObjectFromHomeFragment() {

        Bundle args =getArguments();
        Log.d("args",args.toString());

        if(args != null){
            productModel =(ProductModel) args.getSerializable("current_product");

            producTitle.setText(productModel.getTitle());
            producDetails.setText(productModel.getDetails());
            productMoreDetails.setText(productModel.getDescription());
            producPrice.setText(productModel.getPrice() + "");
            Glide.with(requireContext()).load(productModel.getImage()).into(productiv);

        }
    }
}