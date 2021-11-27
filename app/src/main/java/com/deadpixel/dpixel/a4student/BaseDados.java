package com.deadpixel.dpixel.a4student;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BaseDados extends SQLiteOpenHelper {

    // Atributos da Base de Dados
    private static final int VERSAO_BASE = 1;
    private static final String NOME_BASE = "db_deadpixel";

    // Atributos da tabela User
    private static final String TABELA_USER = "tb_user";
    private static final String COLUNA_IDU = "idu";
    private static final String COLUNA_PNOME = "pnome";
    private static final String COLUNA_UNOME = "unome";
    private static final String COLUNA_NPERIODOS = "nperiodos";
    private static final String COLUNA_IPERIODOS = "iperiodos";
    private static final String COLUNA_FPERIODOS = "fperiodos";
    private static final String COLUNA_SCLASSIFICACAO = "sclassificacao";
    private static final String COLUNA_TIPO = "tipo";
    private static final String COLUNA_VER = "ver";
    private static final String COLUNA_NDISC = "ndisc";
    private static final String COLUNA_NTUR = "ntur";
    private static final String COLUNA_NCA = "nca";
    private static final String COLUNA_NPOR = "npor";
    private static final String COLUNA_NOT = "noti";
    private static final String COLUNA_NOTD = "notd";
    private static final String COLUNA_NOTH = "noth";

    // Atributos da tabela Disciplina
    private static final String TABELA_DISC = "tb_disc";
    private static final String COLUNA_IDD = "idd";
    private static final String COLUNA_DNOME = "dnome";
    private static final String COLUNA_DCOD = "dcod";
    private static final String COLUNA_DSALA = "dsala";
    private static final String COLUNA_DIDCA = "didca";
    private static final String COLUNA_AAVA1 = "aava1";
    private static final String COLUNA_AAVA2 = "aava2";
    private static final String COLUNA_AAVA3 = "aava3";
    private static final String COLUNA_CAAAVA1 = "caaava1";
    private static final String COLUNA_CAAAVA2 = "caaava2";
    private static final String COLUNA_CAAAVA3 = "caaava3";
    private static final String COLUNA_NOTAPER1 = "notaper1";
    private static final String COLUNA_NOTAPER2 = "notaper2";
    private static final String COLUNA_NOTAPER3 = "notaper3";
    private static final String COLUNA_NOTAFINALD = "notafinald";

    // Atributos da tabela Turma
    private static final String TABELA_TUR = "tb_tur";
    private static final String COLUNA_IDT = "idt";
    private static final String COLUNA_TNOME = "tnome";
    private static final String COLUNA_TCOD = "tcod";
    private static final String COLUNA_TSALA = "tsala";
    private static final String COLUNA_TIDCA = "tidca";
    private static final String COLUNA_TSCLA = "tscla";
    private static final String COLUNA_TNALU = "tnalu";

    // Atributos das tabelas Aluno
    private static final String COLUNA_IDA = "ida";
    private static final String COLUNA_ANUM = "anum";
    private static final String COLUNA_ANOME = "anome";
    private static final String COLUNA_NOTA1PER = "nota1per";
    private static final String COLUNA_NOTA2PER = "nota2per";
    private static final String COLUNA_NOTA3PER = "nota3per";
    private static final String COLUNA_NOTAFINAL = "notafinal";
    private static final String COLUNA_AVA1 = "ava1";
    private static final String COLUNA_AVA2 = "ava2";
    private static final String COLUNA_AVA3 = "ava3";
    private static final String COLUNA_CAAVA1 = "caava1";
    private static final String COLUNA_CAAVA2 = "caava2";
    private static final String COLUNA_CAAVA3 = "caava3";

    // Atributos da tabela CriterioAvaliacao
    private static final String TABELA_CA = "tb_ca";
    private static final String COLUNA_IDCA = "idca";
    private static final String COLUNA_CANOME = "canome";
    private static final String COLUNA_CANPARA = "canpara";
    private static final String COLUNA_CAPARA = "capara";
    private static final String COLUNA_CACOT = "cacot";

    // Atributos da tabela de dados temporários
    private static final String TABELA_DT = "tb_dt";
    private static final String COLUNA_IDDT = "iddt";
    private static final String COLUNA_DTS1 = "dts1";
    private static final String COLUNA_DTS2 = "dts2";
    private static final String COLUNA_DTS3 = "dts3";
    private static final String COLUNA_DTI1 = "dti1";
    private static final String COLUNA_DTI2 = "dti2";
    private static final String COLUNA_DTF1 = "dtf1";
    private static final String COLUNA_DTF2 = "dtf2";

    // Atributos da tabela Aulas
    private static final String TABELA_AULAS = "tb_aulas";
    private static final String COLUNA_IDAULAS = "idaulas";
    private static final String COLUNA_NAULAS = "naulas";
    private static final String COLUNA_AULAID = "aulaid";
    private static final String COLUNA_INIAULA = "iniaula";
    private static final String COLUNA_FIMAULA = "fimaula";
    private static final String COLUNA_SALAA = "salaa";

    // Atributos da tabela Calendario
    private static final String TABELA_CALENDARIO = "tb_cal";
    private static final String COLUNA_IDC = "idc";
    private static final String COLUNA_DIA = "dia";
    private static final String COLUNA_NEVE = "neve";
    private static final String COLUNA_TITEVE = "titeve";
    private static final String COLUNA_DESCEVE = "desceve";
    private static final String COLUNA_TIPOEVE = "tipoeve";
    private static final String COLUNA_NOTEVE = "noteve";
    private static final String COLUNA_ANOT = "anot";
    private static final String COLUNA_TITANOT = "titanot";
    private static final String COLUNA_HORAEVE = "horaeve";

    // Atributos da tabela Portfolio
    private static final String TABELA_PORTFOLIO = "tb_por";
    private static final String COLUNA_IDP = "idp";
    private static final String COLUNA_NOMEP = "nomep";
    private static final String COLUNA_NFILES = "nfiles";
    private static final String COLUNA_FILENAMES = "filenames";

    // Criar Base de Dados
    public BaseDados(Context context) {
        super(context, NOME_BASE, null, VERSAO_BASE);
    }

    // Criar tabelas
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criação da tabela User
        String QUERY_USER = "CREATE TABLE " + TABELA_USER + "(" + COLUNA_IDU + " INTEGER PRIMARY KEY, "
                + COLUNA_PNOME + " TEXT, " + COLUNA_UNOME + " INTEGER, "
                + COLUNA_NPERIODOS + " INTEGER, " + COLUNA_IPERIODOS + " TEXT, "
                + COLUNA_FPERIODOS + " TEXT, " + COLUNA_SCLASSIFICACAO + " INTEGER, "
                + COLUNA_TIPO + " TEXT, " + COLUNA_VER + " INTEGER, "
                + COLUNA_NDISC + " INTEGER, " + COLUNA_NTUR + " INTEGER, "
                + COLUNA_NCA + " INTEGER, " + COLUNA_NPOR + " INTEGER, "
                + COLUNA_NOT + " INTEGER, " + COLUNA_NOTD + " TEXT, " + COLUNA_NOTH + " TEXT" + ")";
        db.execSQL(QUERY_USER);
        // Criação da tabela Disciplina
        String QUERY_DISC = "CREATE TABLE " + TABELA_DISC + "(" + COLUNA_IDD + " INTEGER PRIMARY KEY, "
                + COLUNA_DNOME + " TEXT, " + COLUNA_DCOD + " TEXT, "
                + COLUNA_DSALA + " TEXT, " + COLUNA_DIDCA + " INTEGER, "
                + COLUNA_AAVA1 + " INTEGER, " + COLUNA_AAVA2 + " INTEGER, "
                + COLUNA_AAVA3 + " INTEGER, " + COLUNA_CAAAVA1 + " TEXT, "
                + COLUNA_CAAAVA2 + " TEXT, " + COLUNA_CAAAVA3 + " TEXT, "
                + COLUNA_NOTAPER1 + " REAL, " + COLUNA_NOTAPER2 + " REAL, "
                + COLUNA_NOTAPER3 + " REAL, " + COLUNA_NOTAFINALD + " REAL" + ")";
        db.execSQL(QUERY_DISC);
        // Criação da tabela Turma
        String QUERY_TUR = "CREATE TABLE " + TABELA_TUR + "(" + COLUNA_IDT + " INTEGER PRIMARY KEY, "
                + COLUNA_TNOME + " TEXT, " + COLUNA_TCOD + " TEXT, "
                + COLUNA_TSALA + " TEXT, " + COLUNA_TIDCA + " INTEGER, "
                + COLUNA_TSCLA + " INTEGER, " + COLUNA_TNALU + " INTEGER" + ")";
        db.execSQL(QUERY_TUR);
        // Criação da tabela CriterioAvaliacao
        String QUERY_CA = "CREATE TABLE " + TABELA_CA + "(" + COLUNA_IDCA + " INTEGER PRIMARY KEY, "
                + COLUNA_CANOME + " TEXT, " + COLUNA_CANPARA + " INTEGER, " + COLUNA_CAPARA + " TEXT, "
                + COLUNA_CACOT + " TEXT" + ")";
        db.execSQL(QUERY_CA);
        // Criação da tabela Temp
        String QUERY_DT = "CREATE TABLE " + TABELA_DT + "(" + COLUNA_IDDT + " INTEGER PRIMARY KEY, "
                + COLUNA_DTS1 + " TEXT, " + COLUNA_DTS2 + " TEXT, " + COLUNA_DTS3 + " TEXT, "
                + COLUNA_DTI1 + " INTEGER, " + COLUNA_DTI2 + " INTEGER, "
                + COLUNA_DTF1 + " REAL, " + COLUNA_DTF2 + " REAL" + ")";
        db.execSQL(QUERY_DT);
        // Criação da tabela Aulas
        String QUERY_AULAS = "CREATE TABLE " + TABELA_AULAS + "(" + COLUNA_IDAULAS + " INTEGER PRIMARY KEY, "
                + COLUNA_NAULAS + " INTEGER, " + COLUNA_AULAID + " TEXT, "
                + COLUNA_INIAULA + " TEXT, " + COLUNA_FIMAULA + " TEXT,"
                + COLUNA_SALAA + " TEXT" + ")";
        db.execSQL(QUERY_AULAS);
        // Criação da tabela Calendario
        String QUERY_CAL = "CREATE TABLE " + TABELA_CALENDARIO + "(" + COLUNA_IDC + " INTEGER PRIMARY KEY, "
                + COLUNA_DIA + " TEXT, " + COLUNA_NEVE + " INTEGER, " + COLUNA_TITEVE + " TEXT, "
                + COLUNA_DESCEVE + " TEXT, " + COLUNA_TIPOEVE + " TEXT, " + COLUNA_NOTEVE + " TEXT, "
                + COLUNA_ANOT + " INTEGER, " + COLUNA_TITANOT + " TEXT, " + COLUNA_HORAEVE + " TEXT" + ")";
        db.execSQL(QUERY_CAL);
        // Criação da tabela Portfolio
        String QUERY_POR = "CREATE TABLE " + TABELA_PORTFOLIO + "(" + COLUNA_IDP + " INTEGER PRIMARY KEY, "
                + COLUNA_NOMEP + " TEXT, " + COLUNA_NFILES + " INTEGER, " + COLUNA_FILENAMES + " TEXT" + ")";
        db.execSQL(QUERY_POR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    // Criação de tabelas de Aluno
    public void createTableAluno(String TABELA_ALUNO) {
        SQLiteDatabase db = this.getWritableDatabase();
        String QUERY_ALUNO = "CREATE TABLE IF NOT EXISTS " + TABELA_ALUNO + "(" + COLUNA_IDA + " INTEGER PRIMARY KEY, "
                + COLUNA_ANUM + " INTEGER, " + COLUNA_ANOME + " TEXT, "
                + COLUNA_NOTA1PER + " REAL, " + COLUNA_NOTA2PER + " REAL, "
                + COLUNA_NOTA3PER + " REAL, " + COLUNA_NOTAFINAL + " REAL, "
                + COLUNA_AVA1 + " INTEGER, " + COLUNA_AVA2 + " INTEGER, "
                + COLUNA_AVA3 + " INTEGER, " + COLUNA_CAAVA1 + " TEXT, "
                + COLUNA_CAAVA2 + " TEXT, " + COLUNA_CAAVA3 + " TEXT" + ")";
        db.execSQL(QUERY_ALUNO);
        db.close();
    }

    // Mudar nome de tabela de Aluno
    public void changeTableAluno(String TABELA_ALUNO, String EX_TABELA_ALUNO) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("ALTER TABLE " + EX_TABELA_ALUNO + " RENAME TO " + TABELA_ALUNO + ";");
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    // Apagar tabela de Alunos
    public void deleteTableAluno(String TABELA_ALUNO) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_ALUNO);
        db.close();
    }

    /* -----------------------------------
       -------------- CRUD ---------------
       ----------------------------------- */

    // Métodos CRUD da tabela User
    void insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_PNOME, user.getPnome());
        values.put(COLUNA_UNOME, user.getUnome());
        values.put(COLUNA_NPERIODOS, user.getNperiodos());
        values.put(COLUNA_IPERIODOS, user.getIperiodos());
        values.put(COLUNA_FPERIODOS, user.getFperiodos());
        values.put(COLUNA_SCLASSIFICACAO, user.getSclassificacao());
        values.put(COLUNA_TIPO, user.getTipo());
        values.put(COLUNA_VER, user.getVer());
        values.put(COLUNA_NDISC, user.getNdisc());
        values.put(COLUNA_NTUR, user.getNtur());
        values.put(COLUNA_NCA, user.getNca());
        values.put(COLUNA_NPOR, user.getNpor());
        values.put(COLUNA_NOT, user.getNot());
        values.put(COLUNA_NOTD, user.getNotd());
        values.put(COLUNA_NOTH, user.getNoth());

        db.insert(TABELA_USER, null, values);
        db.close();
    }
    void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_USER, COLUNA_IDU + " = ?", new String[] {String.valueOf(user.getIdu())});
        db.close();
    }
    User selectUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_USER, new String[] {COLUNA_IDU, COLUNA_PNOME, COLUNA_UNOME,
        COLUNA_NPERIODOS, COLUNA_IPERIODOS, COLUNA_FPERIODOS, COLUNA_SCLASSIFICACAO, COLUNA_TIPO, COLUNA_VER, COLUNA_NDISC, COLUNA_NTUR, COLUNA_NCA,
                COLUNA_NPOR, COLUNA_NOT, COLUNA_NOTD, COLUNA_NOTH}, COLUNA_IDU + " = ?",
                new String[] {String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        User user = new User(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                Integer.parseInt(cursor.getString(3)), cursor.getString(4), cursor.getString(5),
                Integer.parseInt(cursor.getString(6)), cursor.getString(7), Integer.parseInt(cursor.getString(8)),
                Integer.parseInt(cursor.getString(9)), Integer.parseInt(cursor.getString(10)),
                Integer.parseInt(cursor.getString(11)), Integer.parseInt(cursor.getString(12)),
                Integer.parseInt(cursor.getString(13)), cursor.getString(14), cursor.getString(15));
        return user;
    }
    void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_PNOME, user.getPnome());
        values.put(COLUNA_UNOME, user.getUnome());
        values.put(COLUNA_NPERIODOS, user.getNperiodos());
        values.put(COLUNA_IPERIODOS, user.getIperiodos());
        values.put(COLUNA_FPERIODOS, user.getFperiodos());
        values.put(COLUNA_SCLASSIFICACAO, user.getSclassificacao());
        values.put(COLUNA_TIPO, user.getTipo());
        values.put(COLUNA_VER, user.getVer());
        values.put(COLUNA_NDISC, user.getNdisc());
        values.put(COLUNA_NTUR, user.getNtur());
        values.put(COLUNA_NCA, user.getNca());
        values.put(COLUNA_NPOR, user.getNpor());
        values.put(COLUNA_NOT, user.getNot());
        values.put(COLUNA_NOTD, user.getNotd());
        values.put(COLUNA_NOTH, user.getNoth());

        db.update(TABELA_USER, values, COLUNA_IDU + " = ?", new String[] {String.valueOf(user.getIdu())});
    }

    // Métodos CRUD da tabela Disciplina
    void insertDisciplina(Disciplina disciplina) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_DNOME, disciplina.getDnome());
        values.put(COLUNA_DCOD, disciplina.getDcod());
        values.put(COLUNA_DSALA, disciplina.getDsala());
        values.put(COLUNA_DIDCA, disciplina.getDidca());
        values.put(COLUNA_AAVA1, disciplina.getAava1());
        values.put(COLUNA_AAVA2, disciplina.getAava2());
        values.put(COLUNA_AAVA3, disciplina.getAava3());
        values.put(COLUNA_CAAAVA1, disciplina.getCaaava1());
        values.put(COLUNA_CAAAVA2, disciplina.getCaaava2());
        values.put(COLUNA_CAAAVA3, disciplina.getCaaava3());
        values.put(COLUNA_NOTAPER1, disciplina.getNotaper1());
        values.put(COLUNA_NOTAPER2, disciplina.getNotaper2());
        values.put(COLUNA_NOTAPER3, disciplina.getNotaper3());
        values.put(COLUNA_NOTAFINALD, disciplina.getNotafinald());

        db.insert(TABELA_DISC, null, values);
        db.close();
    }
    void deleteDisciplina(Disciplina disciplina) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_DISC, COLUNA_IDD + " = ?", new String[] {String.valueOf(disciplina.getIdd())});
        db.close();
    }
    Disciplina selectDisciplina(String cod) {
        Disciplina disciplina;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_DISC + " WHERE " + COLUNA_DCOD + " =?" , new String[] {cod});
        if (cursor.moveToFirst()) {
            disciplina = new Disciplina(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), Integer.parseInt(cursor.getString(4)),
                    Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)),
                    Integer.parseInt(cursor.getString(7)), cursor.getString(8),
                    cursor.getString(9), cursor.getString(10), Float.parseFloat(cursor.getString(11)),
                    Float.parseFloat(cursor.getString(12)), Float.parseFloat(cursor.getString(13)),
                    Float.parseFloat(cursor.getString(14)));
            return disciplina;
        } else {
            disciplina = new Disciplina();
            Log.e("error not found", "user can't be found or database empty");
            return disciplina;
        }
    }
    Disciplina selectDisciplinaById(int idd) {
        Disciplina disciplina;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_DISC + " WHERE " + COLUNA_IDD + " =?" , new String[] {Integer.toString(idd)});
        if (cursor.moveToFirst()) {
            disciplina = new Disciplina(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), Integer.parseInt(cursor.getString(4)),
                    Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)),
                    Integer.parseInt(cursor.getString(7)), cursor.getString(8),
                    cursor.getString(9), cursor.getString(10), Float.parseFloat(cursor.getString(11)),
                    Float.parseFloat(cursor.getString(12)), Float.parseFloat(cursor.getString(13)),
                    Float.parseFloat(cursor.getString(14)));
            return disciplina;
        } else {
            disciplina = new Disciplina();
            Log.e("error not found", "user can't be found or database empty");
            return disciplina;
        }
    }
    Disciplina selectDisciplinaByPosition(int ndisc, int position) {
        Disciplina[] disciplinas = new Disciplina[ndisc];
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_DISC, null);
        if (cursor.moveToFirst()) {
            int c = 0;
            do {
                Disciplina disciplina = new Disciplina();
                disciplina.setIdd(Integer.parseInt(cursor.getString(0)));
                disciplina.setDnome(cursor.getString(1));
                disciplina.setDcod(cursor.getString(2));
                disciplina.setDsala(cursor.getString(3));
                disciplina.setDidca(Integer.parseInt(cursor.getString(4)));
                disciplina.setAava1(Integer.parseInt(cursor.getString(5)));
                disciplina.setAava2(Integer.parseInt(cursor.getString(6)));
                disciplina.setAava3(Integer.parseInt(cursor.getString(7)));
                disciplina.setCaaava1(cursor.getString(8));
                disciplina.setCaaava2(cursor.getString(9));
                disciplina.setCaaava3(cursor.getString(10));
                disciplina.setNotaper1(Float.parseFloat(cursor.getString(11)));
                disciplina.setNotaper2(Float.parseFloat(cursor.getString(12)));
                disciplina.setNotaper3(Float.parseFloat(cursor.getString(13)));
                disciplina.setNotafinald(Float.parseFloat(cursor.getString(14)));

                disciplinas[c] = disciplina;
                c++;
            } while (cursor.moveToNext());
        }
        return disciplinas[position];
    }
    void updateDisciplina(Disciplina disciplina) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_DNOME, disciplina.getDnome());
        values.put(COLUNA_DCOD, disciplina.getDcod());
        values.put(COLUNA_DSALA, disciplina.getDsala());
        values.put(COLUNA_DIDCA, disciplina.getDidca());
        values.put(COLUNA_AAVA1, disciplina.getAava1());
        values.put(COLUNA_AAVA2, disciplina.getAava2());
        values.put(COLUNA_AAVA3, disciplina.getAava3());
        values.put(COLUNA_CAAAVA1, disciplina.getCaaava1());
        values.put(COLUNA_CAAAVA2, disciplina.getCaaava2());
        values.put(COLUNA_CAAAVA3, disciplina.getCaaava3());
        values.put(COLUNA_NOTAPER1, disciplina.getNotaper1());
        values.put(COLUNA_NOTAPER2, disciplina.getNotaper2());
        values.put(COLUNA_NOTAPER3, disciplina.getNotaper3());
        values.put(COLUNA_NOTAFINALD, disciplina.getNotafinald());

        db.update(TABELA_DISC, values, COLUNA_IDD + " = ?", new String[] {String.valueOf(disciplina.getIdd())});
    }

    // Métodos CRUD da tabela Turma
    void insertTurma(Turma turma) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_TNOME, turma.getTnome());
        values.put(COLUNA_TCOD, turma.getTcod());
        values.put(COLUNA_TSALA, turma.getTsala());
        values.put(COLUNA_TIDCA, turma.getTidca());
        values.put(COLUNA_TSCLA, turma.getTscla());
        values.put(COLUNA_TNALU, turma.getTnalu());

        db.insert(TABELA_TUR, null, values);
        db.close();
    }
    void deleteTurma(Turma turma) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_TUR, COLUNA_IDT + " = ?", new String[] {String.valueOf(turma.getIdt())});
        db.close();
    }
    Turma selectTurma(String cod) {
        Turma turma;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_TUR + " WHERE " + COLUNA_TCOD + " =?" , new String[] {cod});
        if (cursor.moveToFirst()) {
            turma = new Turma(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), Integer.parseInt(cursor.getString(4)),
                    Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)));
            return turma;
        } else {
            turma = new Turma();
            Log.e("error not found", "user can't be found or database empty");
            return turma;
        }
    }
    Turma selectTurmaById(int idt) {
        Turma turma;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_TUR + " WHERE " + COLUNA_IDT + " =?" , new String[] {Integer.toString(idt)});
        if (cursor.moveToFirst()) {
            turma = new Turma(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), Integer.parseInt(cursor.getString(4)),
                    Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)));
            return turma;
        } else {
            turma = new Turma();
            Log.e("error not found", "user can't be found or database empty");
            return turma;
        }
    }
    Turma selectTurmaByPosition(int ntur, int position) {
        Turma[] turmas = new Turma[ntur];
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_TUR, null);
        if (cursor.moveToFirst()) {
            int c = 0;
            do {
                Turma turma = new Turma();
                turma.setIdt(Integer.parseInt(cursor.getString(0)));
                turma.setTnome(cursor.getString(1));
                turma.setTcod(cursor.getString(2));
                turma.setTsala(cursor.getString(3));
                turma.setTidca(Integer.parseInt(cursor.getString(4)));
                turma.setTscla(Integer.parseInt(cursor.getString(5)));
                turma.setTnalu(Integer.parseInt(cursor.getString(6)));

                turmas[c] = turma;
                c++;
            } while (cursor.moveToNext());
        }
        return turmas[position];
    }
    void updateTurma(Turma turma) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_TNOME, turma.getTnome());
        values.put(COLUNA_TCOD, turma.getTcod());
        values.put(COLUNA_TSALA, turma.getTsala());
        values.put(COLUNA_TIDCA, turma.getTidca());
        values.put(COLUNA_TSCLA, turma.getTscla());
        values.put(COLUNA_TNALU, turma.getTnalu());

        db.update(TABELA_TUR, values, COLUNA_IDT + " = ?", new String[] {String.valueOf(turma.getIdt())});
    }

    // Métodos CRUD das tabelas de Aluno
    void insertAluno(Aluno aluno, String TABELA_ALUNO) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_ANUM, aluno.getAnum());
        values.put(COLUNA_ANOME, aluno.getAnome());
        values.put(COLUNA_NOTA1PER, aluno.getNota1per());
        values.put(COLUNA_NOTA2PER, aluno.getNota2per());
        values.put(COLUNA_NOTA3PER, aluno.getNota3per());
        values.put(COLUNA_NOTAFINAL, aluno.getNotafinal());
        values.put(COLUNA_AVA1, aluno.getAva1());
        values.put(COLUNA_AVA2, aluno.getAva2());
        values.put(COLUNA_AVA3, aluno.getAva3());
        values.put(COLUNA_CAAVA1, aluno.getCaava1());
        values.put(COLUNA_CAAVA2, aluno.getCaava2());
        values.put(COLUNA_CAAVA3, aluno.getCaava3());

        db.insert(TABELA_ALUNO, null, values);
        db.close();
    }
    void deleteAluno(Aluno aluno, String TABELA_ALUNO) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_ALUNO, COLUNA_IDA + " = ?", new String[] {String.valueOf(aluno.getIda())});
        db.close();
    }
    Aluno selectAluno(String TABELA_ALUNO, int anum) {
        Aluno aluno;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_ALUNO + " WHERE " + COLUNA_ANUM + " =?" , new String[] {String.valueOf(anum)});
        if (cursor.moveToFirst()) {
            aluno = new Aluno(Integer.parseInt(cursor.getString(0)),
                    Integer.parseInt(cursor.getString(1)), cursor.getString(2),
                    Float.parseFloat(cursor.getString(3)),
                    Float.parseFloat(cursor.getString(4)),
                    Float.parseFloat(cursor.getString(5)),
                    Float.parseFloat(cursor.getString(6)),
                    Integer.parseInt(cursor.getString(7)),
                    Integer.parseInt(cursor.getString(8)),
                    Integer.parseInt(cursor.getString(9)),
                    cursor.getString(10), cursor.getString(11), cursor.getString(12));
            return aluno;
        } else {
            aluno = new Aluno();
            Log.e("error not found", "user can't be found or database empty");
            return aluno;
        }
    }
    Aluno selectAlunoByPosition(String TABELA_ALUNO, int nalu, int position) {
        Aluno[] alunos = new Aluno[nalu];
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_ALUNO, null);
        if (cursor.moveToFirst()) {
            int c = 0;
            do {
                Aluno aluno = new Aluno();
                aluno.setIda(Integer.parseInt(cursor.getString(0)));
                aluno.setAnum(Integer.parseInt(cursor.getString(1)));
                aluno.setAnome(cursor.getString(2));
                aluno.setNota1per(Float.parseFloat(cursor.getString(3)));
                aluno.setNota2per(Float.parseFloat(cursor.getString(4)));
                aluno.setNota3per(Float.parseFloat(cursor.getString(5)));
                aluno.setNotafinal(Float.parseFloat(cursor.getString(6)));
                aluno.setAva1(Integer.parseInt(cursor.getString(7)));
                aluno.setAva2(Integer.parseInt(cursor.getString(8)));
                aluno.setAva3(Integer.parseInt(cursor.getString(9)));
                aluno.setCaava1(cursor.getString(10));
                aluno.setCaava2(cursor.getString(11));
                aluno.setCaava3(cursor.getString(12));

                alunos[c] = aluno;
                c++;
            } while (cursor.moveToNext());
        }
        return alunos[position];
    }
    void updateAluno(Aluno aluno, String TABELA_ALUNO) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_ANUM, aluno.getAnum());
        values.put(COLUNA_ANOME, aluno.getAnome());
        values.put(COLUNA_NOTA1PER, aluno.getNota1per());
        values.put(COLUNA_NOTA2PER, aluno.getNota2per());
        values.put(COLUNA_NOTA3PER, aluno.getNota3per());
        values.put(COLUNA_NOTAFINAL, aluno.getNotafinal());
        values.put(COLUNA_AVA1, aluno.getAva1());
        values.put(COLUNA_AVA2, aluno.getAva2());
        values.put(COLUNA_AVA3, aluno.getAva3());
        values.put(COLUNA_CAAVA1, aluno.getCaava1());
        values.put(COLUNA_CAAVA2, aluno.getCaava2());
        values.put(COLUNA_CAAVA3, aluno.getCaava3());

        db.update(TABELA_ALUNO, values, COLUNA_IDA + " = ?", new String[] {String.valueOf(aluno.getIda())});
    }

    // Métodos CRUD da tabela CriterioAvaliacao
    void insertCriterioAvaliacao(CriterioAvaliacao criterioAvaliacao) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_CANOME, criterioAvaliacao.getCanome());
        values.put(COLUNA_CANPARA, criterioAvaliacao.getCanpara());
        values.put(COLUNA_CAPARA, criterioAvaliacao.getCapara());
        values.put(COLUNA_CACOT, criterioAvaliacao.getCacot());

        db.insert(TABELA_CA, null, values);
        db.close();
    }
    void deleteCriterioAvaliacao(CriterioAvaliacao criterioAvaliacao) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_CA, COLUNA_IDCA + " = ?", new String[] {String.valueOf(criterioAvaliacao.getIdca())});
        db.close();
    }
    CriterioAvaliacao selectCriterioAvaliacao(String cod) {
        CriterioAvaliacao criterioAvaliacao;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_CA + " WHERE " + COLUNA_CANOME + " =?" , new String[] {cod});
        if (cursor.moveToFirst()) {
            criterioAvaliacao = new CriterioAvaliacao(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), Integer.parseInt(cursor.getString(2)),
                    cursor.getString(3), cursor.getString(4));
            return criterioAvaliacao;
        } else {
            criterioAvaliacao = new CriterioAvaliacao();
            Log.e("error not found", "user can't be found or database empty");
            return criterioAvaliacao;
        }
    }
    CriterioAvaliacao selectCriterioAvaliacaoId(int idca) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_CA, new String[] {COLUNA_IDCA, COLUNA_CANOME, COLUNA_CANPARA, COLUNA_CAPARA,
                        COLUNA_CACOT}, COLUNA_IDCA + " = ?",
                new String[] {String.valueOf(idca)}, null, null, null, null);
        if (cursor.moveToFirst()) {
            CriterioAvaliacao criterioAvaliacao = new CriterioAvaliacao(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), Integer.parseInt(cursor.getString(2)),
                    cursor.getString(3), cursor.getString(4));
            return criterioAvaliacao;
        } else {
            CriterioAvaliacao criterioAvaliacao = new CriterioAvaliacao(0, "", 0, "", "");
            return criterioAvaliacao;
        }

    }
    void updateCriterioAvaliacao(CriterioAvaliacao criterioAvaliacao) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_CANOME, criterioAvaliacao.getCanome());
        values.put(COLUNA_CANPARA, criterioAvaliacao.getCanpara());
        values.put(COLUNA_CAPARA, criterioAvaliacao.getCapara());
        values.put(COLUNA_CACOT, criterioAvaliacao.getCacot());

        db.update(TABELA_CA, values, COLUNA_IDCA + " = ?", new String[] {String.valueOf(criterioAvaliacao.getIdca())});
    }

    // Métodos CRUD da tabela de dados temporários
    void insertTemp(Temp temp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_DTS1, temp.getDts1());
        values.put(COLUNA_DTS2, temp.getDts2());
        values.put(COLUNA_DTS3, temp.getDts3());
        values.put(COLUNA_DTI1, temp.getDti1());
        values.put(COLUNA_DTI2, temp.getDti2());
        values.put(COLUNA_DTF1, temp.getDtf1());
        values.put(COLUNA_DTF2, temp.getDtf2());

        db.insert(TABELA_DT, null, values);
        db.close();
    }
    void deleteTemp(Temp temp) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_DT, COLUNA_IDDT + " = ?", new String[] {String.valueOf(temp.getIddt())});
        db.close();
    }
    Temp selectTemp(int iddt) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_DT, new String[] {COLUNA_IDDT, COLUNA_DTS1, COLUNA_DTS2, COLUNA_DTS3,
                        COLUNA_DTI1, COLUNA_DTI2, COLUNA_DTF1, COLUNA_DTF2}, COLUNA_IDDT + " = ?",
                new String[] {String.valueOf(iddt)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Temp temp = new Temp(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                cursor.getString(3),
                Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)),
                Float.parseFloat(cursor.getString(6)), Float.parseFloat(cursor.getString(7)));
        return temp;
    }
    void updateTemp(Temp temp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_DTS1, temp.getDts1());
        values.put(COLUNA_DTS2, temp.getDts2());
        values.put(COLUNA_DTS3, temp.getDts3());
        values.put(COLUNA_DTI1, temp.getDti1());
        values.put(COLUNA_DTI2, temp.getDti2());
        values.put(COLUNA_DTF1, temp.getDtf1());
        values.put(COLUNA_DTF2, temp.getDtf2());

        db.update(TABELA_DT, values, COLUNA_IDDT + " = ?", new String[] {String.valueOf(temp.getIddt())});
    }

    // Métodos CRUD da tabela Aulas
    void insertAulas(Aulas aulas) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_NAULAS, aulas.getNaulas());
        values.put(COLUNA_AULAID, aulas.getAulaid());
        values.put(COLUNA_INIAULA, aulas.getIniaula());
        values.put(COLUNA_FIMAULA, aulas.getFimaula());
        values.put(COLUNA_SALAA, aulas.getSalaa());

        db.insert(TABELA_AULAS, null, values);
        db.close();
    }
    void deleteAulas(Aulas aulas) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_AULAS, COLUNA_IDAULAS + " = ?", new String[] {String.valueOf(aulas.getIdaulas())});
        db.close();
    }
    Aulas selectAulas(int idaulas) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_AULAS, new String[] {COLUNA_IDAULAS, COLUNA_NAULAS, COLUNA_AULAID, COLUNA_INIAULA,
                        COLUNA_FIMAULA, COLUNA_SALAA}, COLUNA_IDAULAS + " = ?",
                new String[] {String.valueOf(idaulas)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Aulas aulas = new Aulas(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)),
                cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
        return aulas;
    }
    void updateAulas(Aulas aulas) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_NAULAS, aulas.getNaulas());
        values.put(COLUNA_AULAID, aulas.getAulaid());
        values.put(COLUNA_INIAULA, aulas.getIniaula());
        values.put(COLUNA_FIMAULA, aulas.getFimaula());
        values.put(COLUNA_SALAA, aulas.getSalaa());

        db.update(TABELA_AULAS, values, COLUNA_IDAULAS + " = ?", new String[] {String.valueOf(aulas.getIdaulas())});
    }

    // Métodos CRUD da tabela Calendario
    void insertCalendario(Calendario calendario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_DIA, calendario.getDia());
        values.put(COLUNA_NEVE, calendario.getNeve());
        values.put(COLUNA_TITEVE, calendario.getTiteve());
        values.put(COLUNA_DESCEVE, calendario.getDesceve());
        values.put(COLUNA_TIPOEVE, calendario.getTiteve());
        values.put(COLUNA_NOTEVE, calendario.getNoteve());
        values.put(COLUNA_ANOT, calendario.getAnot());
        values.put(COLUNA_TITANOT, calendario.getTitanot());
        values.put(COLUNA_HORAEVE, calendario.getHoraeve());

        db.insert(TABELA_CALENDARIO, null, values);
        db.close();
    }
    void deleteCalendario(Calendario calendario) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_CALENDARIO, COLUNA_DIA + " = ?", new String[] {calendario.getDia()});
        db.close();
    }
    Calendario selectCalendario(String dia) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_CALENDARIO, new String[] {COLUNA_IDC, COLUNA_DIA, COLUNA_NEVE, COLUNA_TITEVE,
                        COLUNA_DESCEVE, COLUNA_TIPOEVE, COLUNA_NOTEVE, COLUNA_ANOT, COLUNA_TITANOT, COLUNA_HORAEVE}, COLUNA_DIA + " = ?",
                new String[] {dia}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Calendario calendario = new Calendario(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Integer.parseInt(cursor.getString(2)),
                cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                Integer.parseInt(cursor.getString(7)), cursor.getString(8), cursor.getString(9));
        return calendario;
    }
    void updateCalendario(Calendario calendario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_DIA, calendario.getDia());
        values.put(COLUNA_NEVE, calendario.getNeve());
        values.put(COLUNA_TITEVE, calendario.getTiteve());
        values.put(COLUNA_DESCEVE, calendario.getDesceve());
        values.put(COLUNA_TIPOEVE, calendario.getTipoeve());
        values.put(COLUNA_NOTEVE, calendario.getNoteve());
        values.put(COLUNA_ANOT, calendario.getAnot());
        values.put(COLUNA_TITANOT, calendario.getTitanot());
        values.put(COLUNA_HORAEVE, calendario.getHoraeve());

        db.update(TABELA_CALENDARIO, values, COLUNA_IDC + " = ?", new String[] {String.valueOf(calendario.getIdc())});
    }

    // Métodos CRUD da tebala Portfolio
    void insertPortfolio(Portfolio portfolio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOMEP, portfolio.getNomep());
        values.put(COLUNA_NFILES, portfolio.getNfiles());
        values.put(COLUNA_FILENAMES, portfolio.getFilenames());

        db.insert(TABELA_PORTFOLIO, null, values);
        db.close();
    }
    void deletePortfolio(Portfolio portfolio) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_PORTFOLIO, COLUNA_IDP + " = ?", new String[] {Integer.toString(portfolio.getIdp())});
        db.close();
    }
    Portfolio selectPortfolio(String nomep) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_PORTFOLIO, new String[] {COLUNA_IDP, COLUNA_NOMEP, COLUNA_NFILES, COLUNA_FILENAMES}, COLUNA_NOMEP + " = ?",
                new String[] {nomep}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Portfolio portfolio = new Portfolio(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                Integer.parseInt(cursor.getString(2)), cursor.getString(3));
        return portfolio;
    }
    Portfolio selectPortfolioByPosition(int npor, int position) {
        Portfolio[] portfolios = new Portfolio[npor];
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_PORTFOLIO, null);
        if (cursor.moveToFirst()) {
            int c = 0;
            do {
                Portfolio portfolio = new Portfolio();
                portfolio.setIdp(Integer.parseInt(cursor.getString(0)));
                portfolio.setNomep(cursor.getString(1));
                portfolio.setNfiles(Integer.parseInt(cursor.getString(2)));
                portfolio.setFilenames(cursor.getString(3));

                portfolios[c] = portfolio;
                c++;
            } while (cursor.moveToNext());
        }
        return portfolios[position];
    }
    void updatePortfolio(Portfolio portfolio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOMEP, portfolio.getNomep());
        values.put(COLUNA_NFILES, portfolio.getNfiles());
        values.put(COLUNA_FILENAMES, portfolio.getFilenames());

        db.update(TABELA_PORTFOLIO, values, COLUNA_IDP + " = ?", new String[] {String.valueOf(portfolio.getIdp())});
    }
    /* ------------------------------------
    ------------- Fim do CRUD -------------
    ------------------------------------ */

    public List<CriterioAvaliacao> listCriterioAvaliacao() {
        List<CriterioAvaliacao> criterioAvaliacaoList = new ArrayList<CriterioAvaliacao>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_CA, null);
        if (cursor.moveToFirst()) {
            do {
                CriterioAvaliacao criterioAvaliacao = new CriterioAvaliacao();
                criterioAvaliacao.setIdca(Integer.parseInt(cursor.getString(0)));
                criterioAvaliacao.setCanome(cursor.getString(1));
                criterioAvaliacao.setCanpara(Integer.parseInt(cursor.getString(2)));
                criterioAvaliacao.setCapara(cursor.getString(3));
                criterioAvaliacao.setCacot(cursor.getString(4));

                criterioAvaliacaoList.add(criterioAvaliacao);
            } while (cursor.moveToNext());
        }
        return criterioAvaliacaoList;
    }

    public boolean verificarNExiTCod(String cod) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_TUR + " WHERE " + COLUNA_TCOD + " =?" , new String[] {cod});
        if (cursor.moveToFirst()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean verificarNExiDCod(String cod) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_DISC + " WHERE " + COLUNA_DCOD + " =?" , new String[] {cod});
        if (cursor.moveToFirst()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean verificarNExiAlu(String TABELA_ALUNO, int num) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_ALUNO + " WHERE " + COLUNA_ANUM + " =?" , new String[] {String.valueOf(num)});
        if (cursor.moveToFirst()) {
            return false;
        } else {
            return true;
        }
    }

    public List<Aluno> alunoList(String TABELA_ALUNO) {
        List<Aluno> alunoList = new ArrayList<Aluno>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_ALUNO + " ORDER BY " + COLUNA_ANUM, null);
        if (cursor.moveToFirst()) {
            do {
                Aluno aluno = new Aluno();
                aluno.setIda(Integer.parseInt(cursor.getString(0)));
                aluno.setAnum(Integer.parseInt(cursor.getString(1)));
                aluno.setAnome(cursor.getString(2));
                aluno.setNota1per(Float.parseFloat(cursor.getString(3)));
                aluno.setNota2per(Float.parseFloat(cursor.getString(4)));
                aluno.setNota3per(Float.parseFloat(cursor.getString(5)));
                aluno.setNotafinal(Float.parseFloat(cursor.getString(6)));

                alunoList.add(aluno);
            } while (cursor.moveToNext());
        }
        return alunoList;
    }

    public boolean verificarNExiCANome(String nome) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_CA + " WHERE " + COLUNA_CANOME + " =?" , new String[] {nome});
        if (cursor.moveToFirst()) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Turma> turmaList() {
        ArrayList<Turma> turmaList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_TUR, null);
        if (cursor.moveToFirst()) {
            do {
                Turma turma = new Turma();
                turma.setIdt(Integer.parseInt(cursor.getString(0)));
                turma.setTnome(cursor.getString(1));
                turma.setTcod(cursor.getString(2));
                turma.setTsala(cursor.getString(3));
                turma.setTidca(Integer.parseInt(cursor.getString(4)));
                turma.setTscla(Integer.parseInt(cursor.getString(5)));
                turma.setTnalu(Integer.parseInt(cursor.getString(6)));

                turmaList.add(turma);
            } while (cursor.moveToNext());
        }
        return turmaList;
    }

    public ArrayList<Disciplina> disciplinaList() {
        ArrayList<Disciplina> disciplinaList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_DISC, null);
        if (cursor.moveToFirst()) {
            do {
                Disciplina disciplina = new Disciplina();
                disciplina.setIdd(Integer.parseInt(cursor.getString(0)));
                disciplina.setDnome(cursor.getString(1));
                disciplina.setDcod(cursor.getString(2));
                disciplina.setDsala(cursor.getString(3));
                disciplina.setDidca(Integer.parseInt(cursor.getString(4)));

                disciplinaList.add(disciplina);
            } while (cursor.moveToNext());
        }
        return disciplinaList;
    }

    public ArrayList<Aula> listAulas(int dia, String tipo) {
        ArrayList<Aula> aulasList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_AULAS + " WHERE " + COLUNA_IDAULAS + " = ?", new String[] {Integer.toString(dia)});
        Aulas aulas = new Aulas();
        if (cursor.moveToFirst()) {
            aulas.setIdaulas(Integer.parseInt(cursor.getString(0)));
            aulas.setNaulas(Integer.parseInt(cursor.getString(1)));
            aulas.setAulaid(cursor.getString(2));
            aulas.setIniaula(cursor.getString(3));
            aulas.setFimaula(cursor.getString(4));
            aulas.setSalaa(cursor.getString(5));
        }
        if (aulas.getNaulas() == 1) {
            Aula aula = new Aula();
            if (tipo.equals("prof")) {
                Turma turma = selectTurmaById(Integer.parseInt(aulas.getAulaid()));
                aula.setCod(turma.getTcod());
            } else {
                Disciplina disciplina = selectDisciplinaById(Integer.parseInt(aulas.getAulaid()));
                aula.setCod(disciplina.getDcod());
            }
            aula.setHoraIni(aulas.getIniaula());
            aula.setHoraFim(aulas.getFimaula());
            aula.setSala(aulas.getSalaa());

            aulasList.add(aula);
        } else if (aulas.getNaulas() > 1) {
            String[] aulasid = aulas.getAulaid().split("/0/1/");
            String[] horasIni = aulas.getIniaula().split("/0/1/");
            String[] horasFim = aulas.getFimaula().split("/0/1/");
            String[] salas = aulas.getSalaa().split("/0/1/");
            for (int c = 0; c < aulasid.length; c++) {
                Aula aula = new Aula();
                if (tipo.equals("prof")) {
                    Turma turma = selectTurmaById(Integer.parseInt(aulasid[c]));
                    aula.setCod(turma.getTcod());
                } else {
                    Disciplina disciplina = selectDisciplinaById(Integer.parseInt(aulasid[c]));
                    aula.setCod(disciplina.getDcod());
                }
                aula.setHoraIni(horasIni[c]);
                aula.setHoraFim(horasFim[c]);
                aula.setSala(salas[c]);

                aulasList.add(aula);
            }
        }
        return aulasList;
    }

    public ArrayList<Aula> listAulas2(int dia, String tipo) {
        ArrayList<Aula> aulasList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_AULAS + " WHERE " + COLUNA_IDAULAS + " = ?", new String[] {Integer.toString(dia)});
        Aulas aulas = new Aulas();
        if (cursor.moveToFirst()) {
            aulas.setIdaulas(Integer.parseInt(cursor.getString(0)));
            aulas.setNaulas(Integer.parseInt(cursor.getString(1)));
            aulas.setAulaid(cursor.getString(2));
            aulas.setIniaula(cursor.getString(3));
            aulas.setFimaula(cursor.getString(4));
            aulas.setSalaa(cursor.getString(5));
        }
        if (aulas.getNaulas() == 1) {
            Aula aula = new Aula();
            aula.setDia(dia);
            if (tipo.equals("prof")) {
                Turma turma = selectTurmaById(Integer.parseInt(aulas.getAulaid()));
                aula.setCod(turma.getTnome());
            } else {
                Disciplina disciplina = selectDisciplinaById(Integer.parseInt(aulas.getAulaid()));
                aula.setCod(disciplina.getDnome());
            }
            aula.setHoraIni(aulas.getIniaula());
            aula.setHoraFim(aulas.getFimaula());
            aula.setSala(aulas.getSalaa());

            aulasList.add(aula);
        } else if (aulas.getNaulas() > 1) {
            String[] aulasid = aulas.getAulaid().split("/0/1/");
            String[] horasIni = aulas.getIniaula().split("/0/1/");
            String[] horasFim = aulas.getFimaula().split("/0/1/");
            String[] salas = aulas.getSalaa().split("/0/1/");
            for (int c = 0; c < aulasid.length; c++) {
                Aula aula = new Aula();
                aula.setDia(dia);
                if (tipo.equals("prof")) {
                    Turma turma = selectTurmaById(Integer.parseInt(aulasid[c]));
                    aula.setCod(turma.getTnome());
                } else {
                    Disciplina disciplina = selectDisciplinaById(Integer.parseInt(aulasid[c]));
                    aula.setCod(disciplina.getDnome());
                }
                aula.setHoraIni(horasIni[c]);
                aula.setHoraFim(horasFim[c]);
                aula.setSala(salas[c]);

                aulasList.add(aula);
            }
        }
        return aulasList;
    }

    public boolean nExistDia(String dia) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_CALENDARIO, new String[] {COLUNA_IDC, COLUNA_DIA, COLUNA_NEVE, COLUNA_TITEVE,
                        COLUNA_DESCEVE, COLUNA_TIPOEVE, COLUNA_NOTEVE, COLUNA_ANOT, COLUNA_TITANOT, COLUNA_HORAEVE}, COLUNA_DIA + " = ?",
                new String[] {dia}, null, null, null, null);
        if (cursor.moveToFirst()) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Evento> listEventos(String dia) {
        ArrayList<Evento> eventosList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_CALENDARIO + " WHERE " + COLUNA_DIA + " = ?", new String[] {dia});
        Calendario calendario = new Calendario();
        if (cursor.moveToFirst()) {
            calendario.setIdc(Integer.parseInt(cursor.getString(0)));
            calendario.setDia(cursor.getString(1));
            calendario.setNeve(Integer.parseInt(cursor.getString(2)));
            calendario.setTiteve(cursor.getString(3));
            calendario.setDesceve(cursor.getString(4));
            calendario.setTipoeve(cursor.getString(5));
            calendario.setNoteve(cursor.getString(6));
            calendario.setAnot(Integer.parseInt(cursor.getString(7)));
            calendario.setTitanot(cursor.getString(8));
            calendario.setHoraeve(cursor.getString(9));
        }
        if (calendario.getNeve() == 1) {
            Evento evento = new Evento();
            evento.setTit(calendario.getTiteve());
            evento.setHoras(calendario.getHoraeve());
            evento.setTipoe(calendario.getTipoeve());

            eventosList.add(evento);
        } else if (calendario.getNeve() > 1) {
            String[] titulos = calendario.getTiteve().split("/0/1/");
            String[] horas = calendario.getHoraeve().split("/0/1/");
            String[] tipos = calendario.getTipoeve().split("/0/1/");
            for (int c = 0; c < titulos.length; c++) {
                Evento evento = new Evento();
                evento.setTit(titulos[c]);
                evento.setHoras(horas[c]);
                evento.setTipoe(tipos[c]);

                eventosList.add(evento);
            }
        }
        return eventosList;
    }

    public ArrayList<Evento> listEventos2(String dia, ArrayList<Evento> eventosList) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_CALENDARIO + " WHERE " + COLUNA_DIA + " = ?", new String[] {dia});
        Calendario calendario = new Calendario();
        if (cursor.moveToFirst()) {
            calendario.setIdc(Integer.parseInt(cursor.getString(0)));
            calendario.setDia(cursor.getString(1));
            calendario.setNeve(Integer.parseInt(cursor.getString(2)));
            calendario.setTiteve(cursor.getString(3));
            calendario.setDesceve(cursor.getString(4));
            calendario.setTipoeve(cursor.getString(5));
            calendario.setNoteve(cursor.getString(6));
            calendario.setAnot(Integer.parseInt(cursor.getString(7)));
            calendario.setTitanot(cursor.getString(8));
            calendario.setHoraeve(cursor.getString(9));
        }
        if (calendario.getNeve() == 1) {
            Evento evento = new Evento();
            evento.setTit(calendario.getTiteve());
            evento.setHoras(calendario.getHoraeve());
            evento.setTipoe(calendario.getTipoeve());
            evento.setDia(dia);

            if (calendario.getTipoeve().equals("1")) {
                eventosList.add(evento);
            }
        } else if (calendario.getNeve() > 1) {
            String[] titulos = calendario.getTiteve().split("/0/1/");
            String[] horas = calendario.getHoraeve().split("/0/1/");
            String[] tipos = calendario.getTipoeve().split("/0/1/");
            for (int c = 0; c < titulos.length; c++) {
                Evento evento = new Evento();
                evento.setTit(titulos[c]);
                evento.setHoras(horas[c]);
                evento.setTipoe(tipos[c]);
                evento.setDia(dia);


                if (tipos[c].equals("1")) {
                    eventosList.add(evento);
                }
            }
        }
        return eventosList;
    }

    public ArrayList<Aula> listAulas3 (ArrayList<Aula> aulasList, int dia, int iddt) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_AULAS + " WHERE " + COLUNA_IDAULAS + " = ?", new String[] {Integer.toString(dia)});
        Aulas aulas = new Aulas();
        if (cursor.moveToFirst()) {
            aulas.setIdaulas(Integer.parseInt(cursor.getString(0)));
            aulas.setNaulas(Integer.parseInt(cursor.getString(1)));
            aulas.setAulaid(cursor.getString(2));
            aulas.setIniaula(cursor.getString(3));
            aulas.setFimaula(cursor.getString(4));
            aulas.setSalaa(cursor.getString(5));
        }
        if (aulas.getNaulas() == 1) {
            Aula aula = new Aula();
            aula.setDia(dia);
            aula.setHoraIni(aulas.getIniaula());
            aula.setHoraFim(aulas.getFimaula());

            if (Integer.parseInt(aulas.getAulaid()) == iddt) {
                aulasList.add(aula);
            }
        } else if (aulas.getNaulas() > 1) {
            String[] aulasid = aulas.getAulaid().split("/0/1/");
            String[] horasIni = aulas.getIniaula().split("/0/1/");
            String[] horasFim = aulas.getFimaula().split("/0/1/");
            String[] salas = aulas.getSalaa().split("/0/1/");
            for (int c = 0; c < aulasid.length; c++) {
                Aula aula = new Aula();
                aula.setDia(dia);
                aula.setHoraIni(horasIni[c]);
                aula.setHoraFim(horasFim[c]);

                if (Integer.parseInt(aulasid[c]) == iddt) {
                    aulasList.add(aula);
                }
            }
        }
        return aulasList;
    }

    public ArrayList<Aluno> listAlunos(String TABELA_ALUNO) {
        ArrayList<Aluno> alunoList = new ArrayList<Aluno>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_ALUNO + " ORDER BY " + COLUNA_ANUM, null);
        if (cursor.moveToFirst()) {
            do {
                Aluno aluno = new Aluno();
                aluno.setIda(Integer.parseInt(cursor.getString(0)));
                aluno.setAnum(Integer.parseInt(cursor.getString(1)));
                aluno.setAnome(cursor.getString(2));
                aluno.setNota1per(Float.parseFloat(cursor.getString(3)));
                aluno.setNota2per(Float.parseFloat(cursor.getString(4)));
                aluno.setNota3per(Float.parseFloat(cursor.getString(5)));
                aluno.setNotafinal(Float.parseFloat(cursor.getString(6)));
                aluno.setAva1(Integer.parseInt(cursor.getString(7)));
                aluno.setAva2(Integer.parseInt(cursor.getString(8)));
                aluno.setAva3(Integer.parseInt(cursor.getString(9)));
                aluno.setCaava1(cursor.getString(10));
                aluno.setCaava2(cursor.getString(11));
                aluno.setCaava3(cursor.getString(12));

                alunoList.add(aluno);
            } while (cursor.moveToNext());
        }
        return alunoList;
    }

    public Disciplina[] disciplinas(int ndisc) {
        Disciplina[] disciplinaList = new Disciplina[ndisc];
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_DISC, null);
        if (cursor.moveToFirst()) {
            int c = 0;
            do {
                Disciplina disciplina = new Disciplina();
                disciplina.setIdd(Integer.parseInt(cursor.getString(0)));
                disciplina.setDnome(cursor.getString(1));
                disciplina.setDcod(cursor.getString(2));
                disciplina.setDsala(cursor.getString(3));
                disciplina.setDidca(Integer.parseInt(cursor.getString(4)));
                disciplina.setAava1(Integer.parseInt(cursor.getString(5)));
                disciplina.setAava2(Integer.parseInt(cursor.getString(6)));
                disciplina.setAava3(Integer.parseInt(cursor.getString(7)));
                disciplina.setCaaava1(cursor.getString(8));
                disciplina.setCaaava2(cursor.getString(9));
                disciplina.setCaaava3(cursor.getString(10));
                disciplina.setNotaper1(Float.parseFloat(cursor.getString(11)));
                disciplina.setNotaper2(Float.parseFloat(cursor.getString(12)));
                disciplina.setNotaper3(Float.parseFloat(cursor.getString(13)));
                disciplina.setNotafinald(Float.parseFloat(cursor.getString(14)));

                disciplinaList[c] = disciplina;
                c++;
            } while (cursor.moveToNext());
        }
        return disciplinaList;
    }

    public Turma[] turmas(int ntur) {
        Turma[] turmaList = new Turma[ntur];
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_TUR, null);
        if (cursor.moveToFirst()) {
            int c = 0;
            do {
                Turma turma = new Turma();
                turma.setIdt(Integer.parseInt(cursor.getString(0)));
                turma.setTnome(cursor.getString(1));
                turma.setTcod(cursor.getString(2));
                turma.setTsala(cursor.getString(3));
                turma.setTidca(Integer.parseInt(cursor.getString(4)));
                turma.setTscla(Integer.parseInt(cursor.getString(5)));
                turma.setTnalu(Integer.parseInt(cursor.getString(6)));

                turmaList[c] = turma;
                c++;
            } while (cursor.moveToNext());
        }
        return turmaList;
    }

    public Aluno[] alunos(String TABELA_ALUNO, int nalu) {
        Aluno[] alunoList = new Aluno[nalu];
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_ALUNO + " ORDER BY " + COLUNA_ANUM, null);
        if (cursor.moveToFirst()) {
            int c = 0;
            do {
                Aluno aluno = new Aluno();
                aluno.setIda(Integer.parseInt(cursor.getString(0)));
                aluno.setAnum(Integer.parseInt(cursor.getString(1)));
                aluno.setAnome(cursor.getString(2));
                aluno.setNota1per(Float.parseFloat(cursor.getString(3)));
                aluno.setNota2per(Float.parseFloat(cursor.getString(4)));
                aluno.setNota3per(Float.parseFloat(cursor.getString(5)));
                aluno.setNotafinal(Float.parseFloat(cursor.getString(6)));
                aluno.setAva1(Integer.parseInt(cursor.getString(7)));
                aluno.setAva2(Integer.parseInt(cursor.getString(8)));
                aluno.setAva3(Integer.parseInt(cursor.getString(9)));
                aluno.setCaava1(cursor.getString(10));
                aluno.setCaava2(cursor.getString(11));
                aluno.setCaava3(cursor.getString(12));

                alunoList[c] = aluno;
                c++;
            } while (cursor.moveToNext());
        }
        return alunoList;
    }

    public void deleteEventos() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABELA_CALENDARIO);
        db.close();
    }

    public boolean verificarNExiPorNome(String nome) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_PORTFOLIO + " WHERE " + COLUNA_NOMEP + " =?" , new String[] {nome});
        if (cursor.moveToFirst()) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Portfolio> portfolioList() {
        ArrayList<Portfolio> portfolioList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_PORTFOLIO, null);
        if (cursor.moveToFirst()) {
            do {
                Portfolio portfolio = new Portfolio();
                portfolio.setIdp(Integer.parseInt(cursor.getString(0)));
                portfolio.setNomep(cursor.getString(1));
                portfolio.setNfiles(Integer.parseInt(cursor.getString(2)));
                portfolio.setFilenames(cursor.getString(3));

                portfolioList.add(portfolio);
            } while (cursor.moveToNext());
        }
        return portfolioList;
    }
}
