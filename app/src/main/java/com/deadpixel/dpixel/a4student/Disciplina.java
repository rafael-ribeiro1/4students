package com.deadpixel.dpixel.a4student;

public class Disciplina {

    // Atributos (Colunas da tabela)
    int idd;
    String dnome;
    String dcod;
    String dsala;
    int didca;
    int aava1;
    int aava2;
    int aava3;
    String caaava1;
    String caaava2;
    String caaava3;
    float notaper1;
    float notaper2;
    float notaper3;
    float notafinald;


    // Método para instanciamento
    public Disciplina() {}

    // Método para UPDATE
    public Disciplina(int idd, String dnome, String dcod, String dsala, int didca, int aava1, int aava2, int aava3, String caaava1, String caaava2, String caaava3, float notaper1, float notaper2, float notaper3, float notafinald) {
        this.idd = idd;
        this.dnome = dnome;
        this.dcod = dcod;
        this.dsala = dsala;
        this.didca = didca;
        this.aava1 = aava1;
        this.aava2 = aava2;
        this.aava3 = aava3;
        this.caaava1 = caaava1;
        this.caaava2 = caaava2;
        this.caaava3 = caaava3;
        this.notaper1 = notaper1;
        this.notaper2 = notaper2;
        this.notaper3 = notaper3;
        this.notafinald = notafinald;
    }

    // Método para INSERT

    public Disciplina(String dnome, String dcod, String dsala, int didca, int aava1, int aava2, int aava3, String caaava1, String caaava2, String caaava3, float notaper1, float notaper2, float notaper3, float notafinald) {
        this.dnome = dnome;
        this.dcod = dcod;
        this.dsala = dsala;
        this.didca = didca;
        this.aava1 = aava1;
        this.aava2 = aava2;
        this.aava3 = aava3;
        this.caaava1 = caaava1;
        this.caaava2 = caaava2;
        this.caaava3 = caaava3;
        this.notaper1 = notaper1;
        this.notaper2 = notaper2;
        this.notaper3 = notaper3;
        this.notafinald = notafinald;
    }


    // Métodos Getters and Setters

    public int getIdd() {
        return idd;
    }

    public void setIdd(int idd) {
        this.idd = idd;
    }

    public String getDnome() {
        return dnome;
    }

    public void setDnome(String dnome) {
        this.dnome = dnome;
    }

    public String getDcod() {
        return dcod;
    }

    public void setDcod(String dcod) {
        this.dcod = dcod;
    }

    public String getDsala() {
        return dsala;
    }

    public void setDsala(String dsala) {
        this.dsala = dsala;
    }

    public int getDidca() {
        return didca;
    }

    public void setDidca(int didca) {
        this.didca = didca;
    }

    public int getAava1() {
        return aava1;
    }

    public void setAava1(int aava1) {
        this.aava1 = aava1;
    }

    public int getAava2() {
        return aava2;
    }

    public void setAava2(int aava2) {
        this.aava2 = aava2;
    }

    public int getAava3() {
        return aava3;
    }

    public void setAava3(int aava3) {
        this.aava3 = aava3;
    }

    public String getCaaava1() {
        return caaava1;
    }

    public void setCaaava1(String caaava1) {
        this.caaava1 = caaava1;
    }

    public String getCaaava2() {
        return caaava2;
    }

    public void setCaaava2(String caaava2) {
        this.caaava2 = caaava2;
    }

    public String getCaaava3() {
        return caaava3;
    }

    public void setCaaava3(String caaava3) {
        this.caaava3 = caaava3;
    }

    public float getNotaper1() {
        return notaper1;
    }

    public void setNotaper1(float notaper1) {
        this.notaper1 = notaper1;
    }

    public float getNotaper2() {
        return notaper2;
    }

    public void setNotaper2(float notaper2) {
        this.notaper2 = notaper2;
    }

    public float getNotaper3() {
        return notaper3;
    }

    public void setNotaper3(float notaper3) {
        this.notaper3 = notaper3;
    }

    public float getNotafinald() {
        return notafinald;
    }

    public void setNotafinald(float notafinald) {
        this.notafinald = notafinald;
    }
}
