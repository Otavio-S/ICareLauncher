package com.example.otavio.tcc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otavio.tcc.Model.Nota;
import com.example.otavio.tcc.R;
import com.example.otavio.tcc.SQLite.TabelaNotas;
import com.example.otavio.tcc.Telas.Nota.EditarNotas;

import java.util.List;

public class Notas_Adapter extends RecyclerView.Adapter<Notas_Adapter.MyViewHolder> {

    private Context context;
    private List<Nota> notaList;
    private TabelaNotas tabelaNotas;
    private String id;

    public Notas_Adapter(Context context, List<Nota> notaList) {
        this.context = context;
        this.notaList = notaList;
    }

    @NonNull
    @Override
    public Notas_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_nota, parent, false);
        tabelaNotas = new TabelaNotas(context);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditarNotas.class);
                intent.putExtra("ID", Integer.valueOf(id));
                context.startActivity(intent);
            }
        });

        return new Notas_Adapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Notas_Adapter.MyViewHolder holder, final int position) {

        holder.txtNome.setText(notaList.get(position).getNome());
        holder.txtDescricao.setText(notaList.get(position).getDescricao());
        id = notaList.get(position).getID();

        holder.btnDeletaNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*new AlertDialog.Builder(context)
                        .setTitle("Excluir")
                        .setMessage("Tem certeza que quer excluir a nota?")
                        .setPositiveButton("Exluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tabelaNotas.deletaRegistro(notaList.get(position));
                                Toast.makeText(context, "Nota deletada com sucesso!", Toast.LENGTH_SHORT).show();

                                try {
                                    notaList.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, getItemCount());
                                } catch (SQLException ignored) {
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();*/
                tabelaNotas.deletaRegistro(notaList.get(position));
                Toast.makeText(context, "Nota deletada com sucesso!", Toast.LENGTH_SHORT).show();

                try {
                    notaList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());
                } catch (SQLException ignored) {
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return notaList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtNome;
        TextView txtDescricao;
        ImageButton btnDeletaNota;


        public MyViewHolder(View itemView) {
            super(itemView);

            txtNome = itemView.findViewById(R.id.txtEditarNota);
            txtDescricao = itemView.findViewById(R.id.txtDescricaoNota);
            btnDeletaNota = itemView.findViewById(R.id.btnDeleteNota);
        }
    }
}

