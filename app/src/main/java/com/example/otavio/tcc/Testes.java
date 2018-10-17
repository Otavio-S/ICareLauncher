package com.example.otavio.tcc;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.otavio.tcc.Model.Alarme;
import com.example.otavio.tcc.Model.Historico;
import com.example.otavio.tcc.SQLite.TabelaAlarmes;
import com.example.otavio.tcc.SQLite.TabelaHistorico;

public class Testes extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        TabelaAlarmes tabelaAlarmes = new TabelaAlarmes(this);

        Alarme alarme = new Alarme();

        alarme.setID("1");
        alarme.setNome("Alarme1");
        alarme.setDescricao("Esse é o primeiro alarme a ser criado.");
        alarme.setHoraInicial("12");
        alarme.setQuantidade("6");
        alarme.setTempo("12");
        alarme.setLigado("1");

        System.out.println(tabelaAlarmes.insereDado(alarme));

        alarme.setID("2");
        alarme.setNome("Alarme2");
        alarme.setDescricao("Esse é o segundo alarme a ser criado.");
        alarme.setHoraInicial("18");
        alarme.setQuantidade("3");
        alarme.setTempo("8");
        alarme.setLigado("1");

        System.out.println(tabelaAlarmes.insereDado(alarme));

        alarme.setID("3");
        alarme.setNome("Alarme3");
        alarme.setDescricao("Esse é o terceiro alarme a ser criado.");
        alarme.setHoraInicial("8");
        alarme.setQuantidade("4");
        alarme.setTempo("24");
        alarme.setLigado("0");

        System.out.println(tabelaAlarmes.insereDado(alarme));

        TabelaHistorico tabelaHistorico = new TabelaHistorico(this);

        Historico historico = new Historico();

        historico.setID("1");
        historico.setNome("Historico1");
        historico.setDescricao("Esse é o primeiro historico a ser criado.");
        historico.setDataInicial("12");
        historico.setDataFinal("16");
        historico.setHoraInicial("14");
        historico.setQuantidade("4");
        historico.setTempo("24");

        System.out.println(tabelaHistorico.insereDado(historico));

        historico.setID("2");
        historico.setNome("Historico2");
        historico.setDescricao("Esse é o segundo historico a ser criado.");
        historico.setDataInicial("3");
        historico.setDataFinal("12");
        historico.setHoraInicial("8");
        historico.setQuantidade("12");
        historico.setTempo("16");

        System.out.println(tabelaHistorico.insereDado(historico));

        System.out.printf("Percorrendo o ArrayList (usando o índice)\n");
        int n = tabelaAlarmes.carregaDados().size();
        for (int i = 0; i < n; i++) {
            System.out.printf("ID %d - %s\n", i, tabelaAlarmes.carregaDados().get(i).getID());
            System.out.printf("Nome %d - %s\n", i, tabelaAlarmes.carregaDados().get(i).getNome());
            System.out.printf("Descricao %d - %s\n", i, tabelaAlarmes.carregaDados().get(i).getDescricao());
            System.out.printf("HoraInicial %d - %s\n", i, tabelaAlarmes.carregaDados().get(i).getHoraInicial());
            System.out.printf("Quantidade %d - %s\n", i, tabelaAlarmes.carregaDados().get(i).getQuantidade());
            System.out.printf("Tempo %d - %s\n", i, tabelaAlarmes.carregaDados().get(i).getTempo());
            System.out.printf("ON/OFF %d - %s\n", i, tabelaAlarmes.carregaDados().get(i).getLigado());


        }

        System.out.printf("Percorrendo o ArrayList (usando o índice)\n");
        int n2 = tabelaHistorico.carregaDados().size();
        for (int i = 0; i < n2; i++) {
            System.out.printf("ID %d - %s\n", i, tabelaHistorico.carregaDados().get(i).getID());
            System.out.printf("Nome %d - %s\n", i, tabelaHistorico.carregaDados().get(i).getNome());
            System.out.printf("Descricao %d - %s\n", i, tabelaHistorico.carregaDados().get(i).getDescricao());
            System.out.printf("DataInicial %d - %s\n", i, tabelaHistorico.carregaDados().get(i).getDataInicial());
            System.out.printf("DataFinal %d - %s\n", i, tabelaHistorico.carregaDados().get(i).getDataFinal());
            System.out.printf("HoraInicial %d - %s\n", i, tabelaHistorico.carregaDados().get(i).getHoraInicial());
            System.out.printf("Quantidade %d - %s\n", i, tabelaHistorico.carregaDados().get(i).getQuantidade());
            System.out.printf("Tempo %d - %s\n", i, tabelaHistorico.carregaDados().get(i).getTempo());

        }

    }

}
