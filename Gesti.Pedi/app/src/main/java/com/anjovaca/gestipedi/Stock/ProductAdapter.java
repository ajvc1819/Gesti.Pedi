package com.anjovaca.gestipedi.Stock;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anjovaca.gestipedi.DB.Models.ProductsModel;
import com.anjovaca.gestipedi.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements View.OnClickListener {
    public List<ProductsModel> productsModelList;
    private View.OnClickListener listener;

    public ProductAdapter(List<ProductsModel> productList) {
        this.productsModelList = productList;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView price;
        private final TextView stock;
        private final ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.tvPrecioProd);
            name = itemView.findViewById(R.id.tvNombreProd);
            stock = itemView.findViewById(R.id.tvStockProd);
            image = itemView.findViewById(R.id.imgProd);
        }
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_products, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        holder.price.setText(productsModelList.get(position).getPrice() + "€");
        holder.name.setText(productsModelList.get(position).getName());
        holder.stock.setText("Stock: " + productsModelList.get(position).getStock());
        holder.image.setImageURI(Uri.parse(productsModelList.get(position).getImage()));
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return productsModelList.size();
    }

    public void filter(List<ProductsModel> filterList) {
        this.productsModelList = filterList;
        notifyDataSetChanged();
    }
}
