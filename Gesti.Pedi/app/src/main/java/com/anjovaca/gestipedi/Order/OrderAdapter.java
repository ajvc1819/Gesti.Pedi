package com.anjovaca.gestipedi.Order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anjovaca.gestipedi.DB.Models.ClientModel;
import com.anjovaca.gestipedi.DB.Models.OrderModel;
import com.anjovaca.gestipedi.DB.Models.ProductsModel;
import com.anjovaca.gestipedi.R;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> implements View.OnClickListener {
    Context context;
    public List<OrderModel> orderModelList;
    public List<ClientModel> clientModelList;
    private View.OnClickListener listener;

    public OrderAdapter(Context context, List<OrderModel> orderList, List<ClientModel> clientList) {
        this.orderModelList = orderList;
        this.clientModelList = clientList;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView id, nameClient, date, state, total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.tvIdPedido);
            nameClient = itemView.findViewById(R.id.tvNombreCliente);
            date = itemView.findViewById(R.id.tvFecha);
            state = itemView.findViewById(R.id.tvEstado);
            total = itemView.findViewById(R.id.tvTotal);
        }
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_order, parent, false);
        view.setOnClickListener(this);
        OrderAdapter.ViewHolder viewHolder = new OrderAdapter.ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String total = Double.toString((orderModelList.get(position).getTotal()));
        String name = "";
        for (ClientModel client : clientModelList) {
            if (client.getId() == orderModelList.get(position).getIdClient()) {
                name = client.getEnterprise();
            }
        }

        holder.id.setText("NºPedido: " + orderModelList.get(position).getId());
        holder.nameClient.setText("Cliente: " + name);
        holder.date.setText(orderModelList.get(position).getDate());
        holder.state.setText(orderModelList.get(position).getState());
        holder.total.setText(total + "€");
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return orderModelList.size();
    }

    public void filter(ArrayList<OrderModel> filterList) {
        this.orderModelList = filterList;
        notifyDataSetChanged();
    }
}
