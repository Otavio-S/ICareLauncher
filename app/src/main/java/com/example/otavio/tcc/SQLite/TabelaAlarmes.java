package com.example.otavio.tcc.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TabelaAlarmes extends SQLiteOpenHelper {

    private static final String NOME_TABELA = "Alarmes.db";
    private static final int VERSAO_BANCO = 1;

    public TabelaAlarmes(Context context) {
        super(context, NOME_TABELA, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
