package com.example.retail.fragments;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.retail.AsyncTask.InsertAsyncTask;
import com.example.retail.Room.RoomFactory;
import com.example.retail.adapter.ProductAdapter;
import com.example.retail.ProductModel;
import com.example.retail.R;
import com.example.retail.webServices.ProductsResponse;
import com.example.retail.webServices.RetrofitFactory;
import com.example.retail.webServices.WebServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private RecyclerView homerv;
    private List<ProductModel> productlist = new ArrayList<>();
    private ProductAdapter productAdapter;
    private WebServices webServices;
    private ProgressDialog dialog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        homerv = view.findViewById(R.id.home_rv);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpProgressDialog();
        dialog.show();

        setUpRecyclerView();

        callProductsAPI();


    }

    private void setUpProgressDialog() {

        dialog = new ProgressDialog(requireContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    private void callProductsAPI() {

        webServices = RetrofitFactory.getRetrofit().create(WebServices.class);

        Call<ProductsResponse> getProducts = webServices.getProducts();

        getProducts.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {

                dialog.dismiss();
                productlist.clear();
                productlist.addAll(response.body().getProductsList());
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {

                dialog.dismiss();
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);

        homerv.setLayoutManager(layoutManager);
        homerv.addItemDecoration(new GridSpacingItemDecoration(2,dbToPx(12),true));
        homerv.setItemAnimator(new DefaultItemAnimator());


        productAdapter = new ProductAdapter(productlist, requireContext(), new ProductAdapter.OnProductClickListener() {
            @Override
            public void OnProductClickListener(View view, int position) {
                ProductModel selectedmodel = productlist.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("current_product", selectedmodel);

                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_detailsFragment2, bundle);
            }
        }, new ProductAdapter.OnAddProductClickListener() {
            @Override
            public void OnAddProductClickListener(View view, int position) {
                ProductModel productModel = productlist.get(position);
                productModel.setQuantity(1);
                new InsertAsyncTask(RoomFactory.getRoomDatabase(requireContext()).getProductDao()).execute(productModel);
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_cartFragment);
            }
        });
        homerv.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();

    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dbToPx(int db){
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, db,r.getDisplayMetrics()));
    }

}