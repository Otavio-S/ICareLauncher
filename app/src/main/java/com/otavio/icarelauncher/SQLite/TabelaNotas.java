package com.otavio.icarelauncher.SQLite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.otavio.icarelauncher.Constant.CamposNotas;
import com.otavio.icarelauncher.Model.Nota;

import java.util.ArrayList;
import java.util.List;

public class TabelaNotas extends SQLiteOpenHelper {

    private static final String NOME_TABELA = "Notas.db";
    private static final int VERSAO_BANCO = 2;

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

    public void insereDado(Nota nota) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues valores = new ContentValues();

        int id = UltimoID();

        id = id + 1;

        valores.put(CamposNotas.COLUNA_ID, id);
        valores.put(CamposNotas.COLUNA_NOME, nota.getNome());
        valores.put(CamposNotas.COLUNA_DESCRICAO, nota.getDescricao());

        db.insert(
                CamposNotas.NOME_TABELA,
                null,
                valores);

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

    public Nota carregaDadosPorID(int id) {

        Nota nota = new Nota();
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + CamposNotas.NOME_TABELA + " WHERE " + CamposNotas.COLUNA_ID + " = " + id, null);

        if (cursor.moveToFirst()) {
            nota.setID(cursor.getString(cursor.getColumnIndex(CamposNotas.COLUNA_ID)));
            nota.setNome(cursor.getString(cursor.getColumnIndex(CamposNotas.COLUNA_NOME)));
            nota.setDescricao(cursor.getString(cursor.getColumnIndex(CamposNotas.COLUNA_DESCRICAO)));
        }

        return nota;
    }

    public void alteraRegistro(Nota nota) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(CamposNotas.COLUNA_NOME, nota.getNome());
        valores.put(CamposNotas.COLUNA_DESCRICAO, nota.getDescricao());

        String selecao = CamposNotas.COLUNA_ID + " LIKE ?";
        String[] selecaoArgs = {nota.getID()};

        db.update(
                CamposNotas.NOME_TABELA,
                valores,
                selecao,
                selecaoArgs
        );

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
