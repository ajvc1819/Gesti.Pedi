package com.anjovaca.gestipedi.Order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anjovaca.gestipedi.R;

import java.util.ArrayList;
import java.util.List;


public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> implements View.OnClickListener{
    Context context;
    public List<OrderDetailModel> orderDetailModelList;
    private View.OnClickListener listener;

    public ShoppingCartAdapter(Context context, List<OrderDetailModel> orderList) {
        this.orderDetailModelList = orderList;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public static  class  ViewHolder extends  RecyclerView.ViewHolder{
        public TextView date, state, total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = (TextView)itemView.findViewById(R.id.tvFecha);
            state= (TextView)itemView.findViewById(R.id.tvEstado);
            total = (TextView)itemView.findViewById(R.id.tvTotal);
        }
    }

    @NonNull
    @Override
    public ShoppingCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_shopping,parent,false);
        view.setOnClickListener(this);
        ShoppingCartAdapter.ViewHolder viewHolder = new ShoppingCartAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.date.setText(orderDetailModelList.get(position).getQuantity());
        holder.state.setText(orderDetailModelList.get(position).getIdProduct());
        holder.total.setText(Double.toString(orderDetailModelList.get(position).getPrice()));
    }

    public void  setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
    @Override
    public int getItemCount() {
        return orderDetailModelList.size();
    }

    public void filter(ArrayList<OrderDetailModel> filterList){
        this.orderDetailModelList = filterList;
        notifyDataSetChanged();
    }
}
