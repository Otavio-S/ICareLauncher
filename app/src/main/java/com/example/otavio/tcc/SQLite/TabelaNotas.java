package com.example.otavio.tcc.SQLite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.otavio.tcc.Constant.CamposNotas;
import com.example.otavio.tcc.Model.Nota;

import java.util.ArrayList;
import java.util.List;

public class TabelaNotas extends SQLiteOpenHelper {

    private static final String NOME_TABELA = "Notas.db";
    private static final int VERSAO_BANCO = 1;

    public TabelaNotas(Context context) {
        super(context, NOME_TABELA, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + CamposNotas.NOME_TABELA + " (" +
                CamposNotas.COLUNA_ID + " TEXT," +
                CamposNotas.COLUNA_NOME + " TEXT," +
                CamposNotas.COLUNA_DESCRICAO + " TEXT" + " )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + CamposNotas.NOME_TABELA);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public String insereDado(Nota nota) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues valores = new ContentValues();
        long resultado;

        int id = UltimoID();

        id = id + 1;

        valores.put(CamposNotas.COLUNA_ID, id);
        valores.put(CamposNotas.COLUNA_NOME, nota.getNome());
        valores.put(CamposNotas.COLUNA_DESCRICAO, nota.getDescricao());

        resultado = db.insert(
                CamposNotas.NOME_TABELA,
                null,
                valores);

        if (resultado == -1)
            return "Erro ao inserir registro";
        else {
            return "Registro Inserido com sucesso";
        }
    }

    private int UltimoID() {
        int id = 0;
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + CamposNotas.NOME_TABELA, null);

        if (cursor.moveToLast()) {
            Nota nota = new Nota();
            nota.setID(cursor.getString(cursor.getColumnIndex(CamposNotas.COLUNA_ID)));
            id = Integer.valueOf(nota.getID());
        }

        return id;
    }

    public List<Nota> carregaDados() {

        List<Nota> notas = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + CamposNotas.NOME_TABELA, null);

        if (cursor.moveToFirst()) {
            do {
                Nota nota = new Nota();
                nota.setID(cursor.getString(cursor.getColumnIndex(CamposNotas.COLUNA_ID)));
                nota.setNome(cursor.getString(cursor.getColumnIndex(CamposNotas.COLUNA_NOME)));
                nota.setDescricao(cursor.getString(cursor.getColumnIndex(CamposNotas.COLUNA_DESCRICAO)));
                notas.add(nota);
            } while (cursor.moveToNext());
        }

        return notas;
    }

    public List<Nota> carregaDadosPorID(int id) {

        List<Nota> notas = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + CamposNotas.NOME_TABELA + " WHERE " + CamposNotas.COLUNA_ID + " = " + id, null);

        if (cursor.moveToFirst()) {
            Nota nota = new Nota();
            nota.setID(cursor.getString(cursor.getColumnIndex(CamposNotas.COLUNA_ID)));
            nota.setNome(cursor.getString(cursor.getColumnIndex(CamposNotas.COLUNA_NOME)));
            nota.setDescricao(cursor.getString(cursor.getColumnIndex(CamposNotas.COLUNA_DESCRICAO)));
            notas.add(nota);
        }

        return notas;
    }

    public String alteraRegistro(Nota nota) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(CamposNotas.COLUNA_NOME, nota.getNome());
        valores.put(CamposNotas.COLUNA_DESCRICAO, nota.getDescricao());

        String selecao = CamposNotas.COLUNA_ID + " LIKE ?";
        String[] selecaoArgs = {nota.getID()};

        int count = db.update(
                CamposNotas.NOME_TABELA,
                valores,
                selecao,
                selecaoArgs
        );

        if (count == -1)
            return "Erro ao atualizar registro";
        else {
            return "Registro atualizado com sucesso";
        }
    }

    public String deletaRegistro(Nota nota) {
        SQLiteDatabase db = getReadableDatabase();

        String sql = CamposNotas.COLUNA_ID + " LIKE ?";
        String[] selecaoArgs = {nota.getID()};
        int count = db.delete(
                CamposNotas.NOME_TABELA,
                sql,
                selecaoArgs);

        if (count == -1)
            return "Erro ao deletar registro";
        else {
            return "Registro deletado com sucesso";
        }

    }

}
