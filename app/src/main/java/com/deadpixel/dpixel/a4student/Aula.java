package com.deadpixel.dpixel.a4student;

public class Aula {

    int dia;
    String Cod;
    String horaIni;
    String horaFim;
    String sala;

    public Aula() {}

    public Aula(int dia, String Cod, String horaIni, String horaFim, String sala) {
        this.dia = dia;
        this.Cod = Cod;
        this.horaIni = horaIni;
        this.horaFim = horaFim;
        this.sala = sala;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public String getCod() {
        return Cod;
    }

    public void setCod(String cod) {
        Cod = cod;
    }

    public String getHoraIni() {
        return horaIni;
    }

    public void setHoraIni(String horaIni) {
        this.horaIni = horaIni;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }
}
