package com.deadpixel.dpixel.a4student;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class Registo3 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    BaseDados db = new BaseDados(this);

    Button voltar, fin, btnadd;
    ListView ldisciplinas, lturmas;
    TextView tvdisciplinas, tvturmas, txtReg3, verReg3;
    ConstraintLayout cldisciplinas, clturmas;
    Spinner scspinner;
    int numsc = 1;
    LinearLayout llsc;
    int sc;

    ArrayList<Turma> turmas;
    private static TListAdapter tadapter;

    ArrayList<Disciplina> disciplinas;
    private static DListAdapter dadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo3);

        User user = db.selectUser(1);
        if (user.getVer() == 1) {
            finish();
        }

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        final int ny = getIntent().getIntExtra("NY", 0);

        if (ny == 0) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.registo));
        } else {
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.novo_ano_letivo);
        }

        voltar = findViewById(R.id.voltar);
        fin = findViewById(R.id.fin);
        btnadd = findViewById(R.id.btnadd);
        ldisciplinas = findViewById(R.id.ldisciplinas);
        lturmas = findViewById(R.id.lturmas);
        cldisciplinas = findViewById(R.id.cldisciplinas);
        clturmas = findViewById(R.id.clturmas);
        tvdisciplinas = findViewById(R.id.tvdisciplinas);
        tvturmas = findViewById(R.id.tvturmas);
        txtReg3 = findViewById(R.id.txtReg3);
        scspinner = findViewById(R.id.scspinner);
        llsc = findViewById(R.id.llsc);
        verReg3 = findViewById(R.id.verReg3);

        lturmas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User select_user = db.selectUser(1);
                String tipo = select_user.getTipo();
                Turma turma = db.selectTurmaByPosition(select_user.getNtur(), position);
                String cod = turma.getTcod();
                DTDetailsDialog dialog = new DTDetailsDialog();
                dialog.showDialog(Registo3.this, tipo, cod);
            }
        });
        ldisciplinas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User select_user = db.selectUser(1);
                String tipo = select_user.getTipo();
                Disciplina disciplina = db.selectDisciplinaByPosition(select_user.getNdisc(), position);
                String cod = disciplina.getDcod();
                DTDetailsDialog dialog = new DTDetailsDialog();
                dialog.showDialog(Registo3.this, tipo, cod);
            }
        });

        final User select_user = db.selectUser(1);

        // Diferenciação de Disciplina/Turma
        if (select_user.getTipo().equals("prof")) {
            txtReg3.setText(R.string.txt_reg3_prof);
            btnadd.setText(R.string.adicionar_turma);
            ldisciplinas.setVisibility(View.GONE);
            cldisciplinas.setVisibility(View.GONE);
            clturmas.setVisibility(View.VISIBLE);
            if (select_user.getNtur() == 0) {
                lturmas.setVisibility(View.GONE);
            } else {
                lturmas.setVisibility(View.VISIBLE);
                listTurma();
            }
            llsc.setVisibility(View.GONE);
        } else {
            txtReg3.setText(R.string.txt_reg3_aluno);
            btnadd.setText(R.string.adicionar_disciplina);
            ldisciplinas.setVisibility(View.VISIBLE);
            lturmas.setVisibility(View.GONE);
            cldisciplinas.setVisibility(View.VISIBLE);
            clturmas.setVisibility(View.GONE);
            if (select_user.getNdisc() == 0) {
                ldisciplinas.setVisibility(View.GONE);
            } else {
                ldisciplinas.setVisibility(View.VISIBLE);
                listDisciplina();
            }
        }

        // Spinner de seleção de sistema de classificação
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sclassificacao, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scspinner.setAdapter(adapter);
        if (select_user.getSclassificacao() != 0) {
            scspinner.setSelection(select_user.getSclassificacao() - 1);
        }
        scspinner.setOnItemSelectedListener(this);

        // Programação do Botão Voltar
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registo3.this, Registo2.class);
                if (ny == 0) {
                    intent.putExtra("NY", 0);
                } else {
                    intent.putExtra("NY", 1);
                }
                startActivity(intent);
            }
        });

        // Programação do Botão Finalizar
        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user_select = db.selectUser(1);
                int sc;
                if (user_select.getTipo().equals("prof")) {
                    sc = 0;
                } else {
                    sc = numsc;
                }
                db.updateUser(new User(1, user_select.getPnome(), user_select.getUnome(), user_select.getNperiodos(), user_select.getIperiodos(), user_select.getFperiodos(), sc, user_select.getTipo(), 1, user_select.getNdisc(), user_select.getNtur(), user_select.getNca(), user_select.getNpor(), user_select.getNot(), user_select.getNotd(), user_select.getNoth()));
                Intent intent = new Intent(Registo3.this, PaginaInicial.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finishAffinity();
            }
        });

        // Programação do Botão Adicionar Disciplina/Turma
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user2 = db.selectUser(1);
                if (user2.getTipo().equals("prof")) {
                    sc = 0;
                } else {
                    sc = numsc;
                }
                Temp update_temp = new Temp(1, "", "", "", 0, 0, 0, 0);
                db.updateTemp(update_temp);
                User update_user = new User(1, user2.getPnome(), user2.getUnome(), user2.getNperiodos(), user2.getIperiodos(), user2.getFperiodos(), sc, user2.getTipo(), 0, user2.getNdisc(), user2.getNtur(), user2.getNca(), user2.getNpor(), user2.getNot(), user2.getNotd(), user2.getNoth());
                db.updateUser(update_user);
                Intent intent = new Intent(Registo3.this, AddDT.class);
                if (ny == 0) {
                    intent.putExtra("NY", 0);
                } else {
                    intent.putExtra("NY", 1);
                }
                startActivity(intent);
            }
        });

        verReg3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (select_user.getTipo().equals("prof")) {
                    if (select_user.getNtur() != 0) {
                        listTurma();
                    } else {
                        clturmas.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (select_user.getNdisc() != 0) {
                        listDisciplina();
                    } else {
                        cldisciplinas.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        numsc = position + 1;
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public void listTurma() {
        turmas = db.turmaList();
        tadapter = new TListAdapter(turmas, Registo3.this);
        lturmas.setAdapter(tadapter);
    }

    public void listDisciplina() {
        disciplinas = db.disciplinaList();
        dadapter = new DListAdapter(disciplinas, Registo3.this);
        ldisciplinas.setAdapter(dadapter);
    }
}
