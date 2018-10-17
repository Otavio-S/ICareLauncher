package com.example.otavio.tcc.Model;

public class Historico {

    private String ID;
    private String Nome;
    private String DataInicial;
    private String DataFinal;
    private String HoraInicial;
    private String Quantidade;
    private String Tempo;
    private String Descricao;

    public Historico() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getDataInicial() {
        return DataInicial;
    }

    public void setDataInicial(String dataInicial) {
        DataInicial = dataInicial;
    }

    public String getDataFinal() {
        return DataFinal;
    }

    public void setDataFinal(String dataFinal) {
        DataFinal = dataFinal;
    }

    public String getHoraInicial() {
        return HoraInicial;
    }

    public void setHoraInicial(String horaInicial) {
        HoraInicial = horaInicial;
    }

    public String getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(String quantidade) {
        Quantidade = quantidade;
    }

    public String getTempo() {
        return Tempo;
    }

    public void setTempo(String tempo) {
        Tempo = tempo;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }
}
