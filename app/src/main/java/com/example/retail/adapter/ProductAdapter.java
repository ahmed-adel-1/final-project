package com.example.retail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.retail.ProductModel;
import com.example.retail.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    private List<ProductModel> productslist;
    private Context context;
    private OnProductClickListener onProductClickListener;
    private OnAddProductClickListener onAddProductClickListener;

    public interface OnProductClickListener {
        void OnProductClickListener(View view , int position);
    }


    public interface OnAddProductClickListener {
        void OnAddProductClickListener(View view , int position);
    }

    public ProductAdapter(List<ProductModel> productslist, Context context, OnProductClickListener onProductClickListener, OnAddProductClickListener onAddProductClickListener) {
        this.productslist = productslist;
        this.context = context;
        this.onProductClickListener = onProductClickListener;
        this.onAddProductClickListener = onAddProductClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.product_item,parent, false );
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductModel productModel = productslist.get(position);
        holder.productTitleTv.setText(productModel.getTitle());
        holder.productDetailsTv.setText(productModel.getDetails());
        holder.productPriceTv.setText(productModel.getPrice() + "");
        Glide.with(context).load(productModel.getImage()).into(holder.productIv);


        /*ImageView imageView = (ImageView) findViewById(R.id.my_image_view);

        Glide.with(this).load("http://goo.gl/gEgYUd").into(imageView);*/



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProductClickListener.OnProductClickListener(v, holder.getAdapterPosition());
            }
        });

        holder.addProductIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddProductClickListener.OnAddProductClickListener(v,holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return productslist.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{

        ImageView productIv;
        TextView productTitleTv;
        TextView productDetailsTv;
        TextView productPriceTv;
        ImageButton addProductIb;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            productIv = itemView.findViewById(R.id.product_iv);
            productTitleTv = itemView.findViewById(R.id.product_title_tv);
            productDetailsTv = itemView.findViewById(R.id.product_descrption_tv);
            productPriceTv = itemView.findViewById(R.id.price_tv);
            addProductIb = itemView.findViewById(R.id.addproduct_ib);

        }
    }
}
