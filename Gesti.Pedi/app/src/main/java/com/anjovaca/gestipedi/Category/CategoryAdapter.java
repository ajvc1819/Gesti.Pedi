package com.anjovaca.gestipedi.Category;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anjovaca.gestipedi.DB.Models.CategoryModel;
import com.anjovaca.gestipedi.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> implements View.OnClickListener{
    Context context;
    public List<CategoryModel> categoryModelList;
    private View.OnClickListener listener;


    public CategoryAdapter(Context context, List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvNombreCatR);
        }
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_recycler_item, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        holder.name.setText(categoryModelList.get(position).getName());
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public void filter(ArrayList<CategoryModel> filterList) {
        this.categoryModelList = filterList;
        notifyDataSetChanged();
    }
}
