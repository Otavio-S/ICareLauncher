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
    private static final int VERSAO_BANCO = 2;

    public TabelaHistorico(Context context) {
        super(context, NOME_TABELA, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + CamposHistorico.NOME_TABELA + " (" +
                CamposHistorico.COLUNA_ID + " TEXT," +
                CamposHistorico.COLUNA_NOME + " TEXT," +
                CamposHistorico.COLUNA_DATA_INICIAL + " TEXT," +
                CamposHistorico.COLUNA_DATA_FINAL + " TEXT," +
                CamposHistorico.COLUNA_HORA_INICIAL + " TEXT," +
                CamposHistorico.COLUNA_QUANTIDADE_VEZES + " TEXT," +
                CamposHistorico.COLUNA_ESPACO_TEMPO + " TEXT," +
                CamposHistorico.COLUNA_DESCRICAO + " TEXT" + " )";
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

        valores.put(CamposHistorico.COLUNA_ID, historico.getID());
        valores.put(CamposHistorico.COLUNA_NOME, historico.getNome());
        valores.put(CamposHistorico.COLUNA_DATA_INICIAL, historico.getDataInicial());
        valores.put(CamposHistorico.COLUNA_DATA_FINAL, historico.getDataFinal());
        valores.put(CamposHistorico.COLUNA_HORA_INICIAL, historico.getHoraInicial());
        valores.put(CamposHistorico.COLUNA_QUANTIDADE_VEZES, historico.getQuantidade());
        valores.put(CamposHistorico.COLUNA_ESPACO_TEMPO, historico.getTempo());
        valores.put(CamposHistorico.COLUNA_DESCRICAO, historico.getDescricao());

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

    public List<Historico> carregaDados() {

        List<Historico> historicos = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + CamposHistorico.NOME_TABELA, null);

        if (cursor.moveToFirst()) {
            do {
                Historico historico = new Historico();
                historico.setID(cursor.getString(cursor.getColumnIndex(CamposHistorico.COLUNA_ID)));
                historico.setNome(cursor.getString(cursor.getColumnIndex(CamposHistorico.COLUNA_NOME)));
                historico.setDataInicial(cursor.getString(cursor.getColumnIndex(CamposHistorico.COLUNA_DATA_INICIAL)));
                historico.setDataFinal(cursor.getString(cursor.getColumnIndex(CamposHistorico.COLUNA_DATA_FINAL)));
                historico.setHoraInicial(cursor.getString(cursor.getColumnIndex(CamposHistorico.COLUNA_HORA_INICIAL)));
                historico.setQuantidade(cursor.getString(cursor.getColumnIndex(CamposHistorico.COLUNA_QUANTIDADE_VEZES)));
                historico.setTempo(cursor.getString(cursor.getColumnIndex(CamposHistorico.COLUNA_ESPACO_TEMPO)));
                historico.setDescricao(cursor.getString(cursor.getColumnIndex(CamposHistorico.COLUNA_DESCRICAO)));
                historicos.add(historico);
            } while (cursor.moveToNext());
        }

        return historicos;
    }

    public String alteraRegistro(Historico historico) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(CamposHistorico.COLUNA_NOME, historico.getNome());
        valores.put(CamposHistorico.COLUNA_DATA_INICIAL, historico.getDataInicial());
        valores.put(CamposHistorico.COLUNA_HORA_INICIAL, historico.getHoraInicial());
        valores.put(CamposHistorico.COLUNA_DATA_FINAL, historico.getDataFinal());
        valores.put(CamposHistorico.COLUNA_QUANTIDADE_VEZES, historico.getQuantidade());
        valores.put(CamposHistorico.COLUNA_ESPACO_TEMPO, historico.getTempo());
        valores.put(CamposHistorico.COLUNA_DESCRICAO, historico.getDescricao());

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
