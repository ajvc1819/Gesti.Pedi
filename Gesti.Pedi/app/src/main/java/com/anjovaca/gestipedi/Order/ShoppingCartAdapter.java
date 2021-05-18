package com.anjovaca.gestipedi.Order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.DB.Models.ClientModel;
import com.anjovaca.gestipedi.DB.Models.OrderDetailModel;
import com.anjovaca.gestipedi.DB.Models.ProductsModel;
import com.anjovaca.gestipedi.R;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;


public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {
    Context context;
    public List<OrderDetailModel> orderDetailModelList;
    public List<ProductsModel> productsModelList;
    private View.OnClickListener listener;
    public int idOrder;
    public int stock;

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public ShoppingCartAdapter(Context context, List<OrderDetailModel> orderList, List<ProductsModel> productsModelList, int idOrder) {
        this.orderDetailModelList = orderList;
        this.productsModelList = productsModelList;
        this.context = context;
        this.idOrder = idOrder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameProd, quantity, price, idDetail;
        public ImageView imageProd;
        public ImageButton increaseQuantity, decreaseQuantity, deleteDetail;
        Context context;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            idDetail = itemView.findViewById(R.id.tvIdDetail);
            nameProd = itemView.findViewById(R.id.tvNombreSho);
            quantity = itemView.findViewById(R.id.tvCantidadSho);
            price = itemView.findViewById(R.id.tvTotalSho);
            imageProd = itemView.findViewById(R.id.imgProdSho);
            increaseQuantity = itemView.findViewById(R.id.btnIncreaseQuantity);
            decreaseQuantity = itemView.findViewById(R.id.btnDecreaseQuantity);
            deleteDetail = itemView.findViewById(R.id.deleteDetail);
        }

        void setOnClickListeners() {
            increaseQuantity.setOnClickListener(this);
            decreaseQuantity.setOnClickListener(this);
            deleteDetail.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int orderDetailId = Integer.parseInt((String) idDetail.getText());
            DbGestiPedi db = new DbGestiPedi(context);
            double priceDetail;
            double priceProduct;
            List<OrderDetailModel> currentOrderDetail = db.getOrderDetailById(orderDetailId);
            List<ProductsModel> currentProduct = db.selectProductById(currentOrderDetail.get(0).getIdProduct());
            int quantity = currentOrderDetail.get(0).getQuantity();
            priceDetail = currentOrderDetail.get(0).getPrice();
            priceProduct = currentProduct.get(0).getPrice();

            if (v.getId() == R.id.btnIncreaseQuantity || v.getId() == R.id.btnDecreaseQuantity) {

                if (v.getId() == R.id.btnIncreaseQuantity) {
                    db.increaseQuantity(orderDetailId,stock, quantity, priceDetail, priceProduct, idOrder);

                } else {
                    db.decreaseQuantity(orderDetailId, quantity, priceDetail, priceProduct, idOrder);

                }


                orderDetailModelList = db.showOrderDetail(idOrder);

                update(orderDetailModelList);
                quantity = orderDetailModelList.get(0).getQuantity();
                double totalPrice = updateTotalPriceOrder();
                db.updateTotalPrice(idOrder, totalPrice);

            } else if (v.getId() == R.id.deleteDetail) {
                db.deleteOrderDetail(orderDetailId, quantity, priceDetail, priceProduct, idOrder);
                orderDetailModelList = db.showOrderDetail(idOrder);
                double totalPrice = updateTotalPriceOrder();
                db.updateTotalPrice(idOrder, totalPrice);
                update(orderDetailModelList);
            }
        }
    }

    public double updateTotalPriceOrder() {
        double totalPrice = 0;

        for (OrderDetailModel orderDetail : orderDetailModelList) {
            double price = orderDetail.getPrice();
            totalPrice += price;
        }

        return totalPrice;
    }

    @NonNull
    @Override
    public ShoppingCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_shopping, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = "", image = "";

        for (ProductsModel products : productsModelList) {
            if (products.getId() == orderDetailModelList.get(position).getIdProduct()) {
                name = products.getName();
                image = products.getImage();
                stock = products.getStock();
            }
        }

        holder.idDetail.setText(Integer.toString(orderDetailModelList.get(position).getId()));
        holder.quantity.setText(Integer.toString(orderDetailModelList.get(position).getQuantity()));
        holder.nameProd.setText(name);
        holder.price.setText(orderDetailModelList.get(position).getPrice() + "€");
        holder.imageProd.setImageURI(Uri.parse(image));
        holder.setOnClickListeners();
    }

    @Override
    public int getItemCount() {
        return orderDetailModelList.size();
    }

    public void update(List<OrderDetailModel> updatedList) {
        this.orderDetailModelList = updatedList;
        notifyDataSetChanged();
    }

}
