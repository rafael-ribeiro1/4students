package com.deadpixel.dpixel.a4student;

public class Aluno {

    // Atributos (colunas da tabela)
    int ida;
    int anum;
    String anome;
    float nota1per;
    float nota2per;
    float nota3per;
    float notafinal;
    int ava1;
    int ava2;
    int ava3;
    String caava1;
    String caava2;
    String caava3;

    // Método para instanciamento
    public Aluno(){}

    // Método para UPDATE
    public Aluno(int ida, int anum, String anome, float nota1per, float nota2per, float nota3per, float notafinal, int ava1, int ava2, int ava3, String caava1, String caava2, String caava3) {
        this.ida = ida;
        this.anum = anum;
        this.anome = anome;
        this.nota1per = nota1per;
        this.nota2per = nota2per;
        this.nota3per = nota3per;
        this.notafinal = notafinal;
        this.ava1 = ava1;
        this.ava2 = ava2;
        this.ava3 = ava3;
        this.caava1 = caava1;
        this.caava2 = caava2;
        this.caava3 = caava3;
    }

    // Método para INSERT
    public Aluno(int anum, String anome, float nota1per, float nota2per, float nota3per, float notafinal, int ava1, int ava2, int ava3, String caava1, String caava2, String caava3) {
        this.anum = anum;
        this.anome = anome;
        this.nota1per = nota1per;
        this.nota2per = nota2per;
        this.nota3per = nota3per;
        this.notafinal = notafinal;
        this.ava1 = ava1;
        this.ava2 = ava2;
        this.ava3 = ava3;
        this.caava1 = caava1;
        this.caava2 = caava2;
        this.caava3 = caava3;
    }


    // Métodos Getters and Setters

    public int getIda() {
        return ida;
    }

    public void setIda(int ida) {
        this.ida = ida;
    }

    public int getAnum() {
        return anum;
    }

    public void setAnum(int anum) {
        this.anum = anum;
    }

    public String getAnome() {
        return anome;
    }

    public void setAnome(String anome) {
        this.anome = anome;
    }

    public float getNota1per() {
        return nota1per;
    }

    public void setNota1per(float nota1per) {
        this.nota1per = nota1per;
    }

    public float getNota2per() {
        return nota2per;
    }

    public void setNota2per(float nota2per) {
        this.nota2per = nota2per;
    }

    public float getNota3per() {
        return nota3per;
    }

    public void setNota3per(float nota3per) {
        this.nota3per = nota3per;
    }

    public float getNotafinal() {
        return notafinal;
    }

    public void setNotafinal(float notafinal) {
        this.notafinal = notafinal;
    }

    public int getAva1() {
        return ava1;
    }

    public void setAva1(int ava1) {
        this.ava1 = ava1;
    }

    public int getAva2() {
        return ava2;
    }

    public void setAva2(int ava2) {
        this.ava2 = ava2;
    }

    public int getAva3() {
        return ava3;
    }

    public void setAva3(int ava3) {
        this.ava3 = ava3;
    }

    public String getCaava1() {
        return caava1;
    }

    public void setCaava1(String caava1) {
        this.caava1 = caava1;
    }

    public String getCaava2() {
        return caava2;
    }

    public void setCaava2(String caava2) {
        this.caava2 = caava2;
    }

    public String getCaava3() {
        return caava3;
    }

    public void setCaava3(String caava3) {
        this.caava3 = caava3;
    }
}
