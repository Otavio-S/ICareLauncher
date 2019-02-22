package com.example.otavio.tcc.Model;

public class Historico {

    private String ID;
    private String Nome;
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
