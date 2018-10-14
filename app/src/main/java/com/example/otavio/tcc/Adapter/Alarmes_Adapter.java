package com.example.otavio.tcc.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.otavio.tcc.Model.Alarme;
import com.example.otavio.tcc.R;

import java.util.List;

public class Alarmes_Adapter extends RecyclerView.Adapter<Alarmes_Adapter.MyViewHolder> {

    Context context;
    List<Alarme> alarmeList;

    public Alarmes_Adapter(Context context, List<Alarme> alarmeList) {
        this.context = context;
        this.alarmeList = alarmeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_alarme, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.nome.setText(alarmeList.get(position).getNome());
        holder.descicao.setText(alarmeList.get(position).getDescricao());

    }

    @Override
    public int getItemCount() {
        return alarmeList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome;
        TextView descicao;
        Switch switchOnOff;
        ImageButton imageButton;


        public MyViewHolder(View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.nome);
            descicao = itemView.findViewById(R.id.descricao);
            switchOnOff = itemView.findViewById(R.id.switchOnOff);
            imageButton = itemView.findViewById(R.id.iButonDelete);
        }
    }

}