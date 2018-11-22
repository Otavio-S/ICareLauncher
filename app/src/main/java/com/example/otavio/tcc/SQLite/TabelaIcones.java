package com.example.otavio.tcc.SQLite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.otavio.tcc.Constant.CamposIcones;
import com.example.otavio.tcc.Model.Icone;

import java.util.ArrayList;
import java.util.List;

public class TabelaIcones extends SQLiteOpenHelper {

    private static final String NOME_TABELA = "Icones.db";
    private static final int VERSAO_BANCO = 1;

    public TabelaIcones(Context context) {
        super(context, NOME_TABELA, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + CamposIcones.NOME_TABELA + " (" +
                CamposIcones.COLUNA_ID + " TEXT," +
                CamposIcones.COLUNA_CLASSE + " TEXT," +
                CamposIcones.COLUNA_PACOTE + " TEXT" + " )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + CamposIcones.NOME_TABELA);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public String insereDado(Icone icone) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues valores = new ContentValues();
        long resultado;

        valores.put(CamposIcones.COLUNA_ID, icone.getId());
        valores.put(CamposIcones.COLUNA_CLASSE, icone.getClasse());
        valores.put(CamposIcones.COLUNA_PACOTE, icone.getPacote());

        resultado = db.insert(
                CamposIcones.NOME_TABELA,
                null,
                valores);

        if (resultado == -1)
            return "Erro ao inserir registro";
        else {
            return "Registro Inserido com sucesso";
        }
    }

    public List<Icone> carregaDados() {

        List<Icone> icones = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + CamposIcones.NOME_TABELA, null);

        if (cursor.moveToFirst()) {
            do {
                Icone icone = new Icone();
                icone.setId(cursor.getString(cursor.getColumnIndex(CamposIcones.COLUNA_ID)));
                icone.setClasse(cursor.getString(cursor.getColumnIndex(CamposIcones.COLUNA_CLASSE)));
                icone.setPacote(cursor.getString(cursor.getColumnIndex(CamposIcones.COLUNA_PACOTE)));
                icones.add(icone);
            } while (cursor.moveToNext());
        }

        return icones;
    }

    public String alteraRegistro(Icone icone) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(CamposIcones.COLUNA_CLASSE, icone.getClasse());
        valores.put(CamposIcones.COLUNA_PACOTE, icone.getPacote());

        String selecao = CamposIcones.COLUNA_ID + " LIKE ?";
        String[] selecaoArgs = {icone.getId()};

        int count = db.update(
                CamposIcones.NOME_TABELA,
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

    public String deletaRegistro(Icone icone) {
        SQLiteDatabase db = getReadableDatabase();

        String sql = CamposIcones.COLUNA_ID + " LIKE ?";
        String[] selecaoArgs = {icone.getId()};
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
