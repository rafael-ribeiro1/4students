package com.deadpixel.dpixel.a4student;

public class Turma {

    // Atributos (Colunas da tabela)
    int idt;
    String tnome;
    String tcod;
    String tsala;
    int tidca;
    int tscla;
    int tnalu;

    // Método para instanciamento
    public Turma() {}

    // Método para UPDATE
    public Turma(int idt, String tnome, String tcod, String tsala, int tidca, int tscla, int tnalu) {
        this.idt = idt;
        this.tnome = tnome;
        this.tcod = tcod;
        this.tsala = tsala;
        this.tidca = tidca;
        this.tscla = tscla;
        this.tnalu = tnalu;
    }

    // Método para INSERT
    public Turma(String tnome, String tcod, String tsala, int tidca, int tscla, int tnalu) {
        this.tnome = tnome;
        this.tcod = tcod;
        this.tsala = tsala;
        this.tidca = tidca;
        this.tscla = tscla;
        this.tnalu = tnalu;
    }

    // Métodos Getters and Setters

    public int getIdt() {
        return idt;
    }

    public void setIdt(int idt) {
        this.idt = idt;
    }

    public String getTnome() {
        return tnome;
    }

    public void setTnome(String tnome) {
        this.tnome = tnome;
    }

    public String getTcod() {
        return tcod;
    }

    public void setTcod(String tcod) {
        this.tcod = tcod;
    }

    public String getTsala() {
        return tsala;
    }

    public void setTsala(String tsala) {
        this.tsala = tsala;
    }

    public int getTidca() {
        return tidca;
    }

    public void setTidca(int tidca) {
        this.tidca = tidca;
    }

    public int getTscla() {
        return tscla;
    }

    public void setTscla(int tscla) {
        this.tscla = tscla;
    }

    public int getTnalu() {
        return tnalu;
    }

    public void setTnalu(int tnalu) {
        this.tnalu = tnalu;
    }
}
