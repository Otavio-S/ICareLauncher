package com.example.otavio.tcc.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.SQLException;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otavio.tcc.Model.Historico;
import com.example.otavio.tcc.R;
import com.example.otavio.tcc.SQLite.TabelaHistorico;

import java.util.List;

public class Historico_Adapter extends RecyclerView.Adapter<Historico_Adapter.MyViewHolder> {

    private Context context;
    private List<Historico> historicoList;
    private TabelaHistorico tabelaHistorico;

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
        holder.txtDescricao.setText(historicoList.get(position).getDescricao());

        holder.btnDeleteHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Excluir")
                        .setMessage("Tem certeza que quer exluir o histórico?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                tabelaHistorico.deletaRegistro(historicoList.get(position));
                                Toast.makeText(context, "Histórico deletado com sucesso!", Toast.LENGTH_SHORT).show();

                                try {
                                    historicoList.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, getItemCount());
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
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

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtNome;
        TextView txtDescricao;
        ImageButton btnDeleteHistorico;


        public MyViewHolder(View itemView) {
            super(itemView);

            txtNome = itemView.findViewById(R.id.txtNome);
            txtDescricao = itemView.findViewById(R.id.txtDescricao);
            btnDeleteHistorico = itemView.findViewById(R.id.btnDelete);
        }
    }
}

