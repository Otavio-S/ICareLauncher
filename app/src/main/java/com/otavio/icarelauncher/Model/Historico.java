package com.otavio.icarelauncher.Model;

public class Historico {

    private String ID;
    private String Nome;
    private String Descricao;
    private int HoraRemedio;
    private int MinutoRemedio;
    private String DataRemedio;

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

    public int getHoraRemedio() {
        return HoraRemedio;
    }

    public void setHoraRemedio(int horaRemedio) {
        HoraRemedio = horaRemedio;
    }

    public int getMinutoRemedio() {
        return MinutoRemedio;
    }

    public void setMinutoRemedio(int minutoRemedio) {
        MinutoRemedio = minutoRemedio;
    }

    public String getDataRemedio() {
        return DataRemedio;
    }

    public void setDataRemedio(String dataRemedio) {
        DataRemedio = dataRemedio;
    }
}
