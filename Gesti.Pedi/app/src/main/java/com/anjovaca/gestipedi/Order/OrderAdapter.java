package com.anjovaca.gestipedi.Order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anjovaca.gestipedi.DB.Models.OrderModel;
import com.anjovaca.gestipedi.R;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> implements View.OnClickListener {
    Context context;
    public List<OrderModel> orderModelList;
    private View.OnClickListener listener;

    public OrderAdapter(Context context, List<OrderModel> orderList) {
        this.orderModelList = orderList;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView date, state, total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.tvFecha);
            state = (TextView) itemView.findViewById(R.id.tvEstado);
            total = (TextView) itemView.findViewById(R.id.tvTotal);
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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String total = Double.toString((orderModelList.get(position).getTotal()));
        holder.date.setText(orderModelList.get(position).getDate());
        holder.state.setText(orderModelList.get(position).getState());
        holder.total.setText(total);
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
