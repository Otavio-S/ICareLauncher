package com.example.otavio.tcc.SQLite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.otavio.tcc.Constant.CamposAlarmes;
import com.example.otavio.tcc.Model.Alarme;

import java.util.ArrayList;
import java.util.List;

public class TabelaAlarmes extends SQLiteOpenHelper {

    private static final String NOME_TABELA = "Alarme.db";
    private static final int VERSAO_BANCO = 2;

    public TabelaAlarmes(Context context) {
        super(context, NOME_TABELA, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + CamposAlarmes.NOME_TABELA + " (" +
                CamposAlarmes.COLUNA_ID + " TEXT," +
                CamposAlarmes.COLUNA_NOME + " TEXT," +
                CamposAlarmes.COLUNA_HORA_INICIAL + " TEXT," +
                CamposAlarmes.COLUNA_QUANTIDADE_VEZES + " TEXT," +
                CamposAlarmes.COLUNA_ESPACO_TEMPO + " TEXT," +
                CamposAlarmes.COLUNA_DESCRICAO + " TEXT," +
                CamposAlarmes.COLUNA_ON_OFF + " TEXT" + " )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + CamposAlarmes.NOME_TABELA);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public String insereDado(Alarme alarme) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues valores = new ContentValues();
        long resultado;

        valores.put(CamposAlarmes.COLUNA_ID, alarme.getID());
        valores.put(CamposAlarmes.COLUNA_NOME, alarme.getNome());
        valores.put(CamposAlarmes.COLUNA_HORA_INICIAL, alarme.getHoraInicial());
        valores.put(CamposAlarmes.COLUNA_QUANTIDADE_VEZES, alarme.getQuantidade());
        valores.put(CamposAlarmes.COLUNA_ESPACO_TEMPO, alarme.getTempo());
        valores.put(CamposAlarmes.COLUNA_DESCRICAO, alarme.getDescricao());
        valores.put(CamposAlarmes.COLUNA_ON_OFF, alarme.getLigado());

        resultado = db.insert(
                CamposAlarmes.NOME_TABELA,
                null,
                valores);

        if (resultado == -1)
            return "Erro ao inserir registro";
        else {
            return "Registro Inserido com sucesso";
        }
    }

    public List<Alarme> carregaDados() {

        List<Alarme> alarmes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + CamposAlarmes.NOME_TABELA, null);

        if (cursor.moveToFirst()) {
            do {
                Alarme alarme = new Alarme();
                alarme.setID(cursor.getString(cursor.getColumnIndex(CamposAlarmes.COLUNA_ID)));
                alarme.setNome(cursor.getString(cursor.getColumnIndex(CamposAlarmes.COLUNA_NOME)));
                alarme.setHoraInicial(cursor.getString(cursor.getColumnIndex(CamposAlarmes.COLUNA_HORA_INICIAL)));
                alarme.setQuantidade(cursor.getString(cursor.getColumnIndex(CamposAlarmes.COLUNA_QUANTIDADE_VEZES)));
                alarme.setTempo(cursor.getString(cursor.getColumnIndex(CamposAlarmes.COLUNA_ESPACO_TEMPO)));
                alarme.setDescricao(cursor.getString(cursor.getColumnIndex(CamposAlarmes.COLUNA_DESCRICAO)));
                alarme.setLigado(cursor.getString(cursor.getColumnIndex(CamposAlarmes.COLUNA_ON_OFF)));
                alarmes.add(alarme);
            } while (cursor.moveToNext());
        }

        return alarmes;
    }

    public String alteraRegistro(Alarme alarme) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(CamposAlarmes.COLUNA_NOME, alarme.getNome());
        valores.put(CamposAlarmes.COLUNA_HORA_INICIAL, alarme.getHoraInicial());
        valores.put(CamposAlarmes.COLUNA_QUANTIDADE_VEZES, alarme.getQuantidade());
        valores.put(CamposAlarmes.COLUNA_ESPACO_TEMPO, alarme.getTempo());
        valores.put(CamposAlarmes.COLUNA_DESCRICAO, alarme.getDescricao());
        valores.put(CamposAlarmes.COLUNA_ON_OFF, alarme.getLigado());

        String selecao = CamposAlarmes.COLUNA_ID + " LIKE ?";
        String[] selecaoArgs = {alarme.getID()};

        int count = db.update(
                CamposAlarmes.NOME_TABELA,
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

    public String alteraSituacao(String id, String ligado) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(CamposAlarmes.COLUNA_ON_OFF, ligado);

        String selecao = CamposAlarmes.COLUNA_ID + " LIKE ?";
        String[] selecaoArgs = {id};

        int count = db.update(
                CamposAlarmes.NOME_TABELA,
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

    public String deletaRegistro(Alarme alarme) {
        SQLiteDatabase db = getReadableDatabase();

        String sql = CamposAlarmes.COLUNA_ID + " LIKE ?";
        String[] selecaoArgs = {alarme.getID()};
        int count = db.delete(
                CamposAlarmes.NOME_TABELA,
                sql,
                selecaoArgs);

        if (count == -1)
            return "Erro ao deletar registro";
        else {
            return "Registro deletado com sucesso";
        }

    }

}
