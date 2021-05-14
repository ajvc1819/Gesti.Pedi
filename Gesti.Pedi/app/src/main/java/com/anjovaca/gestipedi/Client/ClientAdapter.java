package com.anjovaca.gestipedi.Client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anjovaca.gestipedi.DB.Models.ClientModel;
import com.anjovaca.gestipedi.R;

import java.util.ArrayList;
import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ViewHolder> implements View.OnClickListener {
    Context context;
    public List<ClientModel> clientModelList;
    private View.OnClickListener listener;


    public ClientAdapter(Context context, List<ClientModel> clientList) {
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
        private final TextView enterprise;
        private final TextView name;
        private final TextView phone;
        private final TextView email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            enterprise = itemView.findViewById(R.id.tvEmpresa);
            name = itemView.findViewById(R.id.tvStockProd);
            phone = itemView.findViewById(R.id.tvTelefono);
            email = itemView.findViewById(R.id.tvPrecioProd);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.enterprise.setText(clientModelList.get(position).getEnterprise());
        holder.name.setText(clientModelList.get(position).getName() + " " + clientModelList.get(position).getLastname());
        holder.phone.setText(clientModelList.get(position).getPhone());
        holder.email.setText(clientModelList.get(position).getEmail());
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return clientModelList.size();
    }

    public void filter(ArrayList<ClientModel> filterList) {
        this.clientModelList = filterList;
        notifyDataSetChanged();
    }
}
