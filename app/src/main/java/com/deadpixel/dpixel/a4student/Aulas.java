package com.deadpixel.dpixel.a4student;

public class Aulas {
    // Atributos (Colunas da Tabela)
    int idaulas;
    int naulas;
    String aulaid;
    String iniaula;
    String fimaula;
    String salaa;

    // Método para instanciamento
    public Aulas() {}

    // Método para UPDATE
    public Aulas(int idaulas, int naulas, String aulaid, String iniaula, String fimaula, String salaa) {
        this.idaulas = idaulas;
        this.naulas = naulas;
        this.aulaid = aulaid;
        this.iniaula = iniaula;
        this.fimaula = fimaula;
        this.salaa = salaa;
    }

    // Método para INSERT
    public Aulas(int naulas, String aulaid, String iniaula, String fimaula, String salaa) {
        this.naulas = naulas;
        this.aulaid = aulaid;
        this.iniaula = iniaula;
        this.fimaula = fimaula;
        this.salaa = salaa;
    }

    // Métodos Getters and Setters

    public int getIdaulas() {
        return idaulas;
    }

    public void setIdaulas(int idaulas) {
        this.idaulas = idaulas;
    }

    public int getNaulas() {
        return naulas;
    }

    public void setNaulas(int naulas) {
        this.naulas = naulas;
    }

    public String getAulaid() {
        return aulaid;
    }

    public void setAulaid(String aulaid) {
        this.aulaid = aulaid;
    }

    public String getIniaula() {
        return iniaula;
    }

    public void setIniaula(String iniaula) {
        this.iniaula = iniaula;
    }

    public String getFimaula() {
        return fimaula;
    }

    public void setFimaula(String fimaula) {
        this.fimaula = fimaula;
    }

    public String getSalaa() {
        return salaa;
    }

    public void setSalaa(String salaa) {
        this.salaa = salaa;
    }
}
