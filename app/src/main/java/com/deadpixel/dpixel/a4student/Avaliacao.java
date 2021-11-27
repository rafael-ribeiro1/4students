package com.deadpixel.dpixel.a4student;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Avaliacao extends AppCompatActivity {

    FloatingActionButton fabAddDT;
    LinearLayout lldisciplina, llturma;
    ConstraintLayout cldisciplinas, clturmas;
    ListView listDisciplina, listTurma;
    TextView verAva;

    BaseDados db = new BaseDados(this);

    ArrayList<Turma> turmas;
    private static TAListAdapter tadapter;

    ArrayList<Disciplina> disciplinas;
    private static DAListAdapter dadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        db.updateTemp(new Temp(1, "", "", "", 0, 0, 0, 0));

        fabAddDT = (FloatingActionButton) findViewById(R.id.fabAddDT);
        fabAddDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Avaliacao.this, AddDTA.class);
                startActivity(intent);
            }
        });

        lldisciplina = findViewById(R.id.lldisciplina);
        cldisciplinas = findViewById(R.id.cldisciplinas);
        listDisciplina = findViewById(R.id.listDisciplina);
        llturma = findViewById(R.id.llturma);
        clturmas = findViewById(R.id.clturmas);
        listTurma = findViewById(R.id.listTurma);
        verAva = findViewById(R.id.verAva);

        final User user = db.selectUser(1);

        if (user.getTipo().equals("prof")) {
            lldisciplina.setVisibility(View.GONE);
            llturma.setVisibility(View.VISIBLE);
            if (user.getNtur() == 0) {
                clturmas.setVisibility(View.VISIBLE);
            } else {
                clturmas.setVisibility(View.GONE);
                setListTurma();
            }
        } else {
            llturma.setVisibility(View.GONE);
            lldisciplina.setVisibility(View.VISIBLE);
            if (user.getNdisc() == 0) {
                cldisciplinas.setVisibility(View.VISIBLE);
            } else {
                cldisciplinas.setVisibility(View.GONE);
                setListDisciplina();
            }
        }

        listTurma.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Avaliacao.this, DT.class);
                Turma turma = db.selectTurmaByPosition(user.getNtur(), position);
                intent.putExtra("NOMEDT", turma.getTnome());
                intent.putExtra("CODDT", turma.getTcod());
                startActivity(intent);
            }
        });

        listDisciplina.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Avaliacao.this, DT.class);
                Disciplina disciplina = db.selectDisciplinaByPosition(user.getNdisc(), position);
                intent.putExtra("NOMEDT", disciplina.getDnome());
                intent.putExtra("CODDT", disciplina.getDcod());
                startActivity(intent);
            }
        });

        verAva.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                User user1 = db.selectUser(1);
                if (user1.getTipo().equals("prof")) {
                    if (user1.getNtur() != 0) {
                        setListTurma();
                    } else {
                        clturmas.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (user1.getNdisc() != 0) {
                        setListDisciplina();
                    } else {
                        cldisciplinas.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            Intent intent = new Intent(Avaliacao.this, PaginaInicial.class);
            startActivity(intent);
            finishAffinity();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Avaliacao.this, PaginaInicial.class);
        startActivity(intent);
        finishAffinity();
    }

    public void setListTurma() {
        turmas = db.turmaList();
        tadapter = new TAListAdapter(turmas, Avaliacao.this);
        listTurma.setAdapter(tadapter);
    }

    public void setListDisciplina() {
        disciplinas = db.disciplinaList();
        dadapter = new DAListAdapter(disciplinas, Avaliacao.this);
        listDisciplina.setAdapter(dadapter);
    }
}
