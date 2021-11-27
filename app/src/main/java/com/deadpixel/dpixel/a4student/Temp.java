package com.deadpixel.dpixel.a4student;

public class Temp {

    // Atributos (colunas da tabela)
    int iddt;
    String dts1;
    String dts2;
    String dts3;
    int dti1;
    int dti2;
    float dtf1;
    float dtf2;

    // Método para instanciamento
    public Temp() {}

    // Método para UPDATE
    public Temp(int iddt, String dts1, String dts2, String dts3, int dti1, int dti2, float dtf1, float dtf2) {
        this.iddt = iddt;
        this.dts1 = dts1;
        this.dts2 = dts2;
        this.dts3 = dts3;
        this.dti1 = dti1;
        this.dti2 = dti2;
        this.dtf1 = dtf1;
        this.dtf2 = dtf2;
    }

    // Método para INSERT
    public Temp(String dts1, String dts2, String dts3, int dti1, int dti2, float dtf1, float dtf2) {
        this.dts1 = dts1;
        this.dts2 = dts2;
        this.dts3 = dts3;
        this.dti1 = dti1;
        this.dti2 = dti2;
        this.dtf1 = dtf1;
        this.dtf2 = dtf2;
    }

    // Métodos Getters and Setters

    public int getIddt() {
        return iddt;
    }

    public void setIddt(int iddt) {
        this.iddt = iddt;
    }

    public String getDts1() {
        return dts1;
    }

    public void setDts1(String dts1) {
        this.dts1 = dts1;
    }

    public String getDts2() {
        return dts2;
    }

    public void setDts2(String dts2) {
        this.dts2 = dts2;
    }

    public int getDti1() {
        return dti1;
    }

    public void setDti1(int dti1) {
        this.dti1 = dti1;
    }

    public int getDti2() {
        return dti2;
    }

    public void setDti2(int dti2) {
        this.dti2 = dti2;
    }

    public float getDtf1() {
        return dtf1;
    }

    public void setDtf1(float dtf1) {
        this.dtf1 = dtf1;
    }

    public float getDtf2() {
        return dtf2;
    }

    public void setDtf2(float dtf2) {
        this.dtf2 = dtf2;
    }

    public String getDts3() {
        return dts3;
    }

    public void setDts3(String dts3) {
        this.dts3 = dts3;
    }
}
