package com.deadpixel.dpixel.a4student;

public class CriterioAvaliacao {

    // Atributos (colunas da tabela)
    int idca;
    String canome;
    int canpara;
    String capara;
    String cacot;

    // Método para instanciamento
    public CriterioAvaliacao() {}

    // Método para UPDATE
    public CriterioAvaliacao(int idca, String canome, int canpara, String capara, String cacot) {
        this.idca = idca;
        this.canome = canome;
        this.canpara = canpara;
        this.capara = capara;
        this.cacot = cacot;
    }

    // Método para INSERT
    public CriterioAvaliacao(String canome, int canpara, String capara, String cacot) {
        this.canome = canome;
        this.canpara = canpara;
        this.capara = capara;
        this.cacot = cacot;
    }

    // Métodos Getters and Setters

    public int getIdca() {
        return idca;
    }

    public void setIdca(int idca) {
        this.idca = idca;
    }

    public String getCanome() {
        return canome;
    }

    public void setCanome(String canome) {
        this.canome = canome;
    }

    public int getCanpara() {
        return canpara;
    }

    public void setCanpara(int canpara) {
        this.canpara = canpara;
    }

    public String getCapara() {
        return capara;
    }

    public void setCapara(String capara) {
        this.capara = capara;
    }

    public String getCacot() {
        return cacot;
    }

    public void setCacot(String cacot) {
        this.cacot = cacot;
    }
}
