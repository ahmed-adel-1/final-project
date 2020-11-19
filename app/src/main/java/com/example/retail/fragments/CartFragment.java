package com.example.retail.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.retail.AsyncTask.DeleteAsyncTask;
import com.example.retail.AsyncTask.GetAsyncTask;
import com.example.retail.AsyncTask.UpdateAsyncTask;
import com.example.retail.ProductModel;
import com.example.retail.R;
import com.example.retail.Room.RoomFactory;
import com.example.retail.adapter.CartAdapter;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class CartFragment extends Fragment {

    private RecyclerView cartRv;
    private CartAdapter cartAdapter;

    ArrayList<ProductModel> productList = new ArrayList<>();

    private Button clearbtn;
    private Button checkoutbtn;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        cartRv = view.findViewById(R.id.cart_rv);
        checkoutbtn = view.findViewById(R.id.checkout_btn);
        clearbtn = view.findViewById(R.id.clear_btn);

        return  view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpRecyclerView();
        setUpClikListener();
        getAllProductsDB();
    }

    private void getAllProductsDB() {
        try {
            productList.addAll(new GetAsyncTask(RoomFactory.getRoomDatabase(requireContext()).getProductDao()).execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cartAdapter.notifyDataSetChanged();
    }

    private void setUpClikListener() {
        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteAsyncTask(RoomFactory.getRoomDatabase(requireContext()).getProductDao()).execute();
                productList.clear();
                cartAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        cartRv.setLayoutManager(layoutManager);
        cartRv.addItemDecoration(new DividerItemDecoration(requireContext(),LinearLayoutManager.VERTICAL));

        cartAdapter = new CartAdapter(requireContext(), productList, new CartAdapter.OnIncClickListener() {
            @Override
            public void onIncClick(View view, int position) {
                productList.get(position).setQuantity(productList.get(position).getQuantity() + 1);
                new UpdateAsyncTask(RoomFactory.getRoomDatabase(requireContext()).getProductDao()).execute(productList.get(position));
                cartAdapter.notifyDataSetChanged();

            }
        }, new CartAdapter.OnDecClickListener() {
            @Override
            public void onDecClick(View view, int position) {
                productList.get(position).setQuantity(productList.get(position).getQuantity() - 1);
                new UpdateAsyncTask(RoomFactory.getRoomDatabase(requireContext()).getProductDao()).execute(productList.get(position));
                cartAdapter.notifyDataSetChanged();
            }
        });
        cartRv.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
    }
}