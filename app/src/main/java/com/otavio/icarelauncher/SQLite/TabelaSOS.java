package com.otavio.icarelauncher.SQLite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.otavio.icarelauncher.Constant.CamposIcones;
import com.otavio.icarelauncher.Constant.CamposSOS;
import com.otavio.icarelauncher.Model.SOS;

import java.util.ArrayList;
import java.util.List;

public class TabelaSOS extends SQLiteOpenHelper {

    private static final String NOME_TABELA = "Sos.db";
    private static final int VERSAO_BANCO = 2;

    public TabelaSOS(Context context) {
        super(context, NOME_TABELA, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + CamposSOS.NOME_TABELA + " (" +
                CamposSOS.COLUNA_ID + " TEXT," +
                CamposSOS.COLUNA_NUMERO + " TEXT," +
                CamposSOS.COLUNA_MENSAGEM + " TEXT" + " )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + CamposSOS.NOME_TABELA);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public String insereDado(SOS sos) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues valores = new ContentValues();
        long resultado;

        valores.put(CamposSOS.COLUNA_ID, sos.getId());
        valores.put(CamposSOS.COLUNA_NUMERO, sos.getNumero());
        valores.put(CamposSOS.COLUNA_MENSAGEM, sos.getMensagem());

        resultado = db.insert(
                CamposSOS.NOME_TABELA,
                null,
                valores);

        if (resultado == -1)
            return "Erro ao inserir registro";
        else {
            return "Registro Inserido com sucesso";
        }
    }

    public List<SOS> carregaDados() {

        List<SOS> sos = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + CamposSOS.NOME_TABELA, null);

        if (cursor.moveToFirst()) {
            do {
                SOS sos1 = new SOS();
                sos1.setNumero(cursor.getString(cursor.getColumnIndex(CamposSOS.COLUNA_NUMERO)));
                sos1.setMensagem(cursor.getString(cursor.getColumnIndex(CamposSOS.COLUNA_MENSAGEM)));
                sos.add(sos1);
            } while (cursor.moveToNext());
        }

        return sos;
    }

    public SOS carregaDadosPorID(int id) {

        SOS sos = new SOS();
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + CamposSOS.NOME_TABELA + " WHERE " + CamposSOS.COLUNA_ID + " = " + String.valueOf(id), null);

        if (cursor.moveToFirst()) {
            sos.setNumero(cursor.getString(cursor.getColumnIndex(CamposSOS.COLUNA_NUMERO)));
            sos.setMensagem(cursor.getString(cursor.getColumnIndex(CamposSOS.COLUNA_MENSAGEM)));
        }

        return sos;
    }

    public void alteraRegistro(SOS sos) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(CamposSOS.COLUNA_NUMERO, sos.getNumero());
        valores.put(CamposSOS.COLUNA_MENSAGEM, sos.getMensagem());

        String selecao = CamposSOS.COLUNA_ID + " LIKE ?";
        String[] selecaoArgs = {sos.getId()};

        db.update(
                CamposSOS.NOME_TABELA,
                valores,
                selecao,
                selecaoArgs
        );

    }

    public String deletaRegistro(SOS sos) {
        SQLiteDatabase db = getReadableDatabase();

        String sql = CamposIcones.COLUNA_ID + " LIKE ?";
        String[] selecaoArgs = {sos.getId()};
        int count = db.delete(
                CamposIcones.NOME_TABELA,
                sql,
                selecaoArgs);

        if (count == -1)
            return "Erro ao deletar registro";
        else {
            return "Registro deletado com sucesso";
        }

    }

}
