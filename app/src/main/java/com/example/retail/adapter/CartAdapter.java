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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{

    private Context context;
    private List<ProductModel> productList;
    private OnIncClickListener onIncClickListener;
    private OnDecClickListener onDecClickListener;

    public interface OnIncClickListener{
        void onIncClick(View view, int position);
    }
    public interface OnDecClickListener{
        void onDecClick(View view, int position);
    }

    public CartAdapter(Context context, List<ProductModel> productList, OnIncClickListener onIncClickListener, OnDecClickListener onDecClickListener) {
        this.context = context;
        this.productList = productList;
        this.onIncClickListener = onIncClickListener;
        this.onDecClickListener = onDecClickListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cart_rv_item,parent,false);

        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        ProductModel productModel = productList.get(position);
        Glide.with(context).load(productModel.getImage()).into(holder.productiv);
        holder.titletv.setText(productModel.getTitle());
        holder.detailstv.setText(productModel.getDetails());
        holder.pricetv.setText(productModel.getPrice() + "");
        holder.quantitytv.setText(productModel.getQuantity()+"");

        holder.incib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          onIncClickListener.onIncClick(v,holder.getAdapterPosition());
            }
        }

        );

        holder.decib.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                onDecClickListener.onDecClick(v,holder.getAdapterPosition());
                                            }
                                        }

        );
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder{

        ImageView productiv;
        TextView titletv;
        TextView detailstv;
        TextView pricetv;
        TextView quantitytv;
        ImageButton incib;
        ImageButton decib;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            productiv = itemView.findViewById(R.id.product_cart_iv);
            titletv = itemView.findViewById(R.id.product_title_cart_tv);
            detailstv = itemView.findViewById(R.id.product_details_cart_tv);
            pricetv = itemView.findViewById(R.id.product_price_cart_tv);
            quantitytv = itemView.findViewById(R.id.quantity_tv);
            incib = itemView.findViewById(R.id.inc_ib);
            decib = itemView.findViewById(R.id.dec_ib);

        }
    }
}
