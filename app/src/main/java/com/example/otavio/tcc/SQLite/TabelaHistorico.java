package com.example.otavio.tcc.SQLite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.otavio.tcc.Constant.CamposHistorico;
import com.example.otavio.tcc.Model.Historico;

import java.util.ArrayList;
import java.util.List;

public class TabelaHistorico extends SQLiteOpenHelper {

    private static final String NOME_TABELA = "Historico.db";
    private static final int VERSAO_BANCO = 6;

    public TabelaHistorico(Context context) {
        super(context, NOME_TABELA, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + CamposHistorico.NOME_TABELA + " (" +
                CamposHistorico.COLUNA_ID + " TEXT," +
                CamposHistorico.COLUNA_NOME + " TEXT," +
                CamposHistorico.COLUNA_DESCRICAO + " TEXT," +
                CamposHistorico.COLUNA_HORARIO_REMEDIO + " TEXT " + ") ";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + CamposHistorico.NOME_TABELA);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public String insereDado(Historico historico) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues valores = new ContentValues();
        long resultado;

        int id = UltimoID();

        id = id + 1;

        valores.put(CamposHistorico.COLUNA_ID, id);
        valores.put(CamposHistorico.COLUNA_NOME, historico.getNome());
        valores.put(CamposHistorico.COLUNA_DESCRICAO, historico.getDescricao());
        valores.put(CamposHistorico.COLUNA_HORARIO_REMEDIO, historico.getHorarioRemedio());

        resultado = db.insert(
                CamposHistorico.NOME_TABELA,
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
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + CamposHistorico.NOME_TABELA, null);

        if (cursor.moveToLast()) {
            Historico historico = new Historico();
            historico.setID(cursor.getString(cursor.getColumnIndex(CamposHistorico.COLUNA_ID)));
            id = Integer.valueOf(historico.getID());
        }

        return id;
    }

    public List<Historico> carregaDados() {

        List<Historico> historicos = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + CamposHistorico.NOME_TABELA, null);

        if (cursor.moveToFirst()) {
            do {
                Historico historico = new Historico();
                historico.setID(cursor.getString(cursor.getColumnIndex(CamposHistorico.COLUNA_ID)));
                historico.setNome(cursor.getString(cursor.getColumnIndex(CamposHistorico.COLUNA_NOME)));
                historico.setDescricao(cursor.getString(cursor.getColumnIndex(CamposHistorico.COLUNA_DESCRICAO)));
                historico.setHorarioRemedio(cursor.getString(cursor.getColumnIndex(CamposHistorico.COLUNA_HORARIO_REMEDIO)));
                historicos.add(historico);
            } while (cursor.moveToNext());
        }

        return historicos;
    }

    public Historico carregaDadosPorID(int id) {

        Historico historico = new Historico();
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + CamposHistorico.NOME_TABELA + " WHERE " + CamposHistorico.COLUNA_ID + " = " + String.valueOf(id), null);

        if (cursor.moveToLast()) {
            historico.setID(cursor.getString(cursor.getColumnIndex(CamposHistorico.COLUNA_ID)));
            historico.setNome(cursor.getString(cursor.getColumnIndex(CamposHistorico.COLUNA_NOME)));
            historico.setDescricao(cursor.getString(cursor.getColumnIndex(CamposHistorico.COLUNA_DESCRICAO)));
            historico.setHorarioRemedio(cursor.getString(cursor.getColumnIndex(CamposHistorico.COLUNA_HORARIO_REMEDIO)));
        }

        return historico;
    }

    public String alteraRegistro(Historico historico) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(CamposHistorico.COLUNA_NOME, historico.getNome());
        valores.put(CamposHistorico.COLUNA_DESCRICAO, historico.getDescricao());
        valores.put(CamposHistorico.COLUNA_HORARIO_REMEDIO, historico.getHorarioRemedio());

        String selecao = CamposHistorico.COLUNA_ID + " LIKE ?";
        String[] selecaoArgs = {historico.getID()};

        int count = db.update(
                CamposHistorico.NOME_TABELA,
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

    @SuppressWarnings("UnusedReturnValue")
    public String deletaRegistro(Historico historico) {
        SQLiteDatabase db = getReadableDatabase();

        String sql = CamposHistorico.COLUNA_ID + " LIKE ?";
        String[] selecaoArgs = {historico.getID()};
        int count = db.delete(
                CamposHistorico.NOME_TABELA,
                sql,
                selecaoArgs);

        if (count == -1)
            return "Erro ao deletar registro";
        else {
            return "Registro deletado com sucesso";
        }

    }

}
