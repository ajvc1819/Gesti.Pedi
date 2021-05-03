package com.anjovaca.gestipedi.Stock;

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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements View.OnClickListener{
    public List<ProductsModel> productsModelList;
    private View.OnClickListener listener;

    public ProductAdapter(List<ProductsModel> productList) {
        this.productsModelList = productList;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public static  class  ViewHolder extends  RecyclerView.ViewHolder{
        private TextView nombre, precio, stock;
        //private ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            precio = (TextView)itemView.findViewById(R.id.tvPrecioProd);
            nombre= (TextView)itemView.findViewById(R.id.tvNombreProd);
            stock = (TextView)itemView.findViewById(R.id.tvStockProd);
            //image = (ImageView) itemView.findViewById(R.id.imgImageProd);
        }
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_products,parent,false);
        view.setOnClickListener(this);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        String precio = Double.toString(productsModelList.get(position).getPrecio());
        String stock = Integer.toString(productsModelList.get(position).getStock());
        holder.precio.setText(precio);
        holder.nombre.setText(productsModelList.get(position).getNombre());
        holder.stock.setText(stock);
        //holder.image.setImageDrawable(productsModelList.get(position).getImg());
    }

    public void  setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
    @Override
    public int getItemCount() {
        return productsModelList.size();
    }
}
