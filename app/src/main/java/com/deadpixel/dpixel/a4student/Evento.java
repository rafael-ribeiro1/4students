package com.deadpixel.dpixel.a4student;


public class Evento {

    String tit;
    String horas;
    String tipoe;
    String dia;

    public Evento() {}

    public Evento(String tit, String horas, String tipoe, String dia) {
        this.tit = tit;
        this.horas = horas;
        this.tipoe = tipoe;
        this.dia = dia;
    }

    public String getTit() {
        return tit;
    }

    public void setTit(String tit) {
        this.tit = tit;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public String getTipoe() {
        return tipoe;
    }

    public void setTipoe(String tipoe) {
        this.tipoe = tipoe;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }
}
