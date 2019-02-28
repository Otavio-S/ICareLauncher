package com.example.otavio.tcc.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otavio.tcc.Model.Historico;
import com.example.otavio.tcc.R;
import com.example.otavio.tcc.SQLite.TabelaHistorico;
import com.example.otavio.tcc.Telas.Alarmes.VisualizarHistorico;

import java.util.List;

public class Historico_Adapter extends RecyclerView.Adapter<Historico_Adapter.MyViewHolder> {

    private Context context;
    private List<Historico> historicoList;
    private TabelaHistorico tabelaHistorico;
    private String id;

    public Historico_Adapter(Context context, List<Historico> historicoList) {
        this.context = context;
        this.historicoList = historicoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_historico, parent, false);
        tabelaHistorico = new TabelaHistorico(context);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.txtNome.setText(historicoList.get(position).getNome());
        holder.txtHora.setText(historicoList.get(position).getDataRemedio());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VisualizarHistorico.class);
                Historico historico = historicoList.get(position);
                id = historico.getID();
                intent.putExtra("ID", Integer.valueOf(id));
                context.startActivity(intent);
            }
        });

        holder.btnDeleteHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AlertDialog))
                        .setTitle(R.string.excluir)
                        .setMessage(R.string.certeza_excluir)
                        .setPositiveButton(R.string.excluir, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                tabelaHistorico.deletaRegistro(historicoList.get(position));
                                Toast.makeText(context, R.string.historico_deletado, Toast.LENGTH_SHORT).show();

                                try {
                                    historicoList.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, getItemCount());
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }

                            }
                        })
                        .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //não exclui, apenas fecha a mensagem
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return historicoList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtNome;
        TextView txtHora;
        ImageButton btnDeleteHistorico;


        MyViewHolder(View itemView) {
            super(itemView);

            txtNome = itemView.findViewById(R.id.txtEditar);
            txtHora = itemView.findViewById(R.id.txtHora);
            btnDeleteHistorico = itemView.findViewById(R.id.btnDelete);

        }
    }

}

