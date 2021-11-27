package com.deadpixel.dpixel.a4student;

public class User {

    // Atributos (Colunas da Tabela)
    int idu;
    String pnome;
    String unome;
    int nperiodos;
    String iperiodos;
    String fperiodos;
    int sclassificacao;
    String tipo;
    int ver;
    int ndisc;
    int ntur;
    int nca;
    int npor;
    int not;
    String notd;
    String noth;

    // Método para Instanciamento
    public User() {}

    // Método para UPDATE
    public User(int _idu, String _pnome, String _unome, int _nperiodos, String _iperiodos, String _fperiodos, int _sclassificacao, String _tipo, int _ver, int _ndisc, int _ntur, int _nca, int npor, int not, String notd, String noth) {
        this.idu = _idu;
        this.pnome = _pnome;
        this.unome = _unome;
        this.nperiodos = _nperiodos;
        this.iperiodos = _iperiodos;
        this.fperiodos = _fperiodos;
        this.sclassificacao = _sclassificacao;
        this.tipo = _tipo;
        this.ver = _ver;
        this.ndisc = _ndisc;
        this.ntur = _ntur;
        this.nca = _nca;
        this.npor = npor;
        this.not = not;
        this.notd = notd;
        this.noth = noth;
    }

    // Método para INSERT
    public User(String _pnome, String _unome, int _nperiodos, String _iperiodos, String _fperiodos, int _sclassificacao, String _tipo, int _ver, int _ndisc, int _ntur, int _nca, int npor, int not, String notd, String noth) {
        this.pnome = _pnome;
        this.unome = _unome;
        this.nperiodos = _nperiodos;
        this.iperiodos = _iperiodos;
        this.fperiodos = _fperiodos;
        this.sclassificacao = _sclassificacao;
        this.tipo = _tipo;
        this.ver = _ver;
        this.ndisc = _ndisc;
        this.ntur = _ntur;
        this.nca = _nca;
        this.npor = npor;
        this.not = not;
        this.notd = notd;
        this.noth = noth;
    }

    // Métodos Getters and Setters
    public int getIdu() {
        return idu;
    }

    public void setIdu(int idu) {
        this.idu = idu;
    }

    public String getPnome() {
        return pnome;
    }

    public void setPnome(String pnome) {
        this.pnome = pnome;
    }

    public String getUnome() {
        return unome;
    }

    public void setUnome(String unome) {
        this.unome = unome;
    }

    public int getNperiodos() {
        return nperiodos;
    }

    public void setNperiodos(int nperiodos) {
        this.nperiodos = nperiodos;
    }

    public String getIperiodos() {
        return iperiodos;
    }

    public void setIperiodos(String iperiodos) {
        this.iperiodos = iperiodos;
    }

    public String getFperiodos() {
        return fperiodos;
    }

    public void setFperiodos(String fperiodos) {
        this.fperiodos = fperiodos;
    }

    public int getSclassificacao() {
        return sclassificacao;
    }

    public void setSclassificacao(int sclassificacao) {
        this.sclassificacao = sclassificacao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getVer() {
        return ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    public int getNdisc() {
        return ndisc;
    }

    public void setNdisc(int ndisc) {
        this.ndisc = ndisc;
    }

    public int getNtur() {
        return ntur;
    }

    public void setNtur(int ntur) {
        this.ntur = ntur;
    }

    public int getNca() {
        return nca;
    }

    public void setNca(int nca) {
        this.nca = nca;
    }

    public int getNpor() {
        return npor;
    }

    public void setNpor(int npor) {
        this.npor = npor;
    }

    public int getNot() {
        return not;
    }

    public void setNot(int not) {
        this.not = not;
    }

    public String getNotd() {
        return notd;
    }

    public void setNotd(String notd) {
        this.notd = notd;
    }

    public String getNoth() {
        return noth;
    }

    public void setNoth(String noth) {
        this.noth = noth;
    }
}
