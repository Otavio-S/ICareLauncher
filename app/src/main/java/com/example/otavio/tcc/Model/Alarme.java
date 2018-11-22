package com.example.otavio.tcc.Model;

public class Alarme {

    private String ID;
    private String Nome;
    private String HoraInicial;
    private String Quantidade;
    private String Tempo;
    private String Descricao;
    private String Ligado;

    public Alarme() {
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

    public String getLigado() {
        return Ligado;
    }

    public void setLigado(String ligado) {
        Ligado = ligado;
    }

}
