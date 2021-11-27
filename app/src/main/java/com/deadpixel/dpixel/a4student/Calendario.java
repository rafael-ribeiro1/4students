package com.deadpixel.dpixel.a4student;

public class Calendario {

    // Atributos (Colunas da tabela)
    int idc;
    String dia;
    int neve;
    String titeve;
    String desceve;
    String tipoeve;
    String noteve;
    int anot;
    String titanot;
    String horaeve;

    // Método para instânciamento
    public Calendario() {}

    // Método para UPDATE
    public Calendario(int idc, String dia, int neve, String titeve, String desceve, String tipoeve, String noteve, int anot, String titanot, String horaeve) {
        this.idc = idc;
        this.dia = dia;
        this.neve = neve;
        this.titeve = titeve;
        this.desceve = desceve;
        this.tipoeve = tipoeve;
        this.noteve = noteve;
        this.anot = anot;
        this.titanot = titanot;
        this.horaeve = horaeve;
    }

    // Método para INSERT
    public Calendario(String dia, int neve, String titeve, String desceve, String tipoeve, String noteve, int anot, String titanot, String horaeve) {
        this.dia = dia;
        this.neve = neve;
        this.titeve = titeve;
        this.desceve = desceve;
        this.tipoeve = tipoeve;
        this.noteve = noteve;
        this.anot = anot;
        this.titanot = titanot;
        this.horaeve = horaeve;
    }

    // Métodos Getters and Setters

    public int getIdc() {
        return idc;
    }

    public void setIdc(int idc) {
        this.idc = idc;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public int getNeve() {
        return neve;
    }

    public void setNeve(int neve) {
        this.neve = neve;
    }

    public String getTiteve() {
        return titeve;
    }

    public void setTiteve(String titeve) {
        this.titeve = titeve;
    }

    public String getDesceve() {
        return desceve;
    }

    public void setDesceve(String desceve) {
        this.desceve = desceve;
    }

    public String getTipoeve() {
        return tipoeve;
    }

    public void setTipoeve(String tipoeve) {
        this.tipoeve = tipoeve;
    }

    public String getNoteve() {
        return noteve;
    }

    public void setNoteve(String noteve) {
        this.noteve = noteve;
    }

    public int getAnot() {
        return anot;
    }

    public void setAnot(int anot) {
        this.anot = anot;
    }

    public String getTitanot() {
        return titanot;
    }

    public void setTitanot(String titanot) {
        this.titanot = titanot;
    }

    public String getHoraeve() {
        return horaeve;
    }

    public void setHoraeve(String horaeve) {
        this.horaeve = horaeve;
    }
}
