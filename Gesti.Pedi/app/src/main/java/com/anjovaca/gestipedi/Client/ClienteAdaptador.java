package com.anjovaca.gestipedi.Client;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anjovaca.gestipedi.DB.Models.ClienteModelo;
import com.anjovaca.gestipedi.R;

import java.util.List;

public class ClienteAdaptador extends RecyclerView.Adapter<ClienteAdaptador.ViewHolder> implements View.OnClickListener {

    public List<ClienteModelo> clienteModeloList;
    private View.OnClickListener listener;
    public ClienteAdaptador(List<ClienteModelo> clienteLista) {
        this.clienteModeloList = clienteLista;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public static  class  ViewHolder extends  RecyclerView.ViewHolder{
       private TextView empresa, nombre, telefono, correo;

       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           empresa = (TextView)itemView.findViewById(R.id.tvEmpresa);
           nombre= (TextView)itemView.findViewById(R.id.tvNombre);
           telefono = (TextView)itemView.findViewById(R.id.tvTelefono);
           correo = (TextView)itemView.findViewById(R.id.tvCorreo);
       }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler,parent,false);
        view.setOnClickListener(this);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.empresa.setText(clienteModeloList.get(position).getEmpresa());
        holder.nombre.setText(clienteModeloList.get(position).getNombre() + " " + clienteModeloList.get(position).getApellidos());
        holder.telefono.setText(clienteModeloList.get(position).getTelefono());
        holder.correo.setText(clienteModeloList.get(position).getCorreo());
    }

    public void  setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
    @Override
    public int getItemCount() {
        return clienteModeloList.size();
    }
}
