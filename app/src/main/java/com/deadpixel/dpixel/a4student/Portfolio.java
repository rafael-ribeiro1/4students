package com.deadpixel.dpixel.a4student;

public class Portfolio {

    // Atributos (Colunas da Tabela)
    int idp;
    String nomep;
    int nfiles;
    String filenames;

    // Método para Instanciamento
    public Portfolio() {}

    // Método para UPDATE
    public Portfolio(int idp, String nomep, int nfiles, String filenames) {
        this.idp = idp;
        this.nomep = nomep;
        this.nfiles = nfiles;
        this.filenames = filenames;
    }

    // Método para INSERT
    public Portfolio(String nomep, int nfiles, String filenames) {
        this.nomep = nomep;
        this.nfiles = nfiles;
        this.filenames = filenames;
    }

    // Métodos Getters and Setters
    public int getIdp() {
        return idp;
    }

    public void setIdp(int idp) {
        this.idp = idp;
    }

    public String getNomep() {
        return nomep;
    }

    public void setNomep(String nomep) {
        this.nomep = nomep;
    }

    public int getNfiles() {
        return nfiles;
    }

    public void setNfiles(int nfiles) {
        this.nfiles = nfiles;
    }

    public String getFilenames() {
        return filenames;
    }

    public void setFilenames(String filenames) {
        this.filenames = filenames;
    }
}
