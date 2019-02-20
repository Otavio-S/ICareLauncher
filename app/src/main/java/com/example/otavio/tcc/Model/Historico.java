package com.example.otavio.tcc.Model;

public class Historico {

    private String ID;
    private String Nome;
    private String Quantidade;
    private String Tempo;
    private String Descricao;
    private String HorarioRemedio;

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

    public String getHorarioRemedio() {
        return HorarioRemedio;
    }

    public void setHorarioRemedio(String horarioRemedio) {
        HorarioRemedio = horarioRemedio;
    }
}
