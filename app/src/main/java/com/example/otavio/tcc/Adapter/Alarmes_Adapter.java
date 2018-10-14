package com.example.otavio.tcc.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.otavio.tcc.Model.Alarme;
import com.example.otavio.tcc.R;

import java.util.List;

public class Alarmes_Adapter extends RecyclerView.Adapter<Alarmes_Adapter.ViewHolder> {

    private List<Alarme> alarmeList;

    public Alarmes_Adapter(List<Alarme> alarmes) {
        this.alarmeList = alarmes;
    }

    @Override
    public Alarmes_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.alarmes_adapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Alarmes_Adapter.ViewHolder holder, int position) {

        Alarme alarme = alarmeList.get(position);

        TextView txtNome = holder.txtNome;
        txtNome.setText(alarme.getNome());

        TextView txtDescricao = holder.txtDescricao;
        txtDescricao.setText(alarme.getDescricao());
    }

    @Override
    public int getItemCount() {
        return alarmeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNome;
        TextView txtDescricao;
        Switch aSwitch;

        ViewHolder(View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtNome);
            txtDescricao = itemView.findViewById(R.id.txtDescricao);
            aSwitch = itemView.findViewById(R.id.switchOnOff);
        }
    }


}