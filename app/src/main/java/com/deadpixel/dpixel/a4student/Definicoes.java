package com.deadpixel.dpixel.a4student;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Definicoes extends AppCompatActivity {

    BaseDados db = new BaseDados(this);

    Button btnDadUser, btnNot, btnNovoAno, btnWebsite, btnDenBug, btnDarOpi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definicoes);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        btnDadUser = findViewById(R.id.btnDadUser);
        btnNot = findViewById(R.id.btnNot);
        btnNovoAno = findViewById(R.id.btnNovoAno);
        btnWebsite = findViewById(R.id.btnWebsite);
        btnDenBug = findViewById(R.id.btnDenBug);
        btnDarOpi = findViewById(R.id.btnDarOpi);

        btnDadUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DadUserDialog dialog = new DadUserDialog();
                dialog.showDialog(Definicoes.this);
            }
        });

        btnNot.setVisibility(View.GONE);
        /*btnNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        btnNovoAno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Definicoes.this);
                builder.setTitle(getString(R.string.title_ny));
                builder.setMessage(getString(R.string.message_ny));
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        User user = db.selectUser(1);
                        db.updateUser(new User(1, user.getPnome(), user.getUnome(), 0, "", "",user.getSclassificacao(), user.getTipo(), 0, user.getNdisc(), user.getNtur(), user.getNca(), user.getNpor(), user.getNot(), user.getNotd(), user.getNoth()));
                        if (user.getTipo().equals("prof")) {
                            Turma[] turmas = db.turmas(user.getNtur());
                            for (int i1 = 0; i1 < turmas.length; i1++) {
                                String TABELA_ALUNO = "abcde" + turmas[i1].getTcod() + "edcba";
                                Aluno[] alunos = db.alunos(TABELA_ALUNO, turmas[i1].getTnalu());
                                for (int i2 = 0; i2 < alunos.length; i2++) {
                                    db.updateAluno(new Aluno(alunos[i2].getIda(), alunos[i2].getAnum(), alunos[i2].getAnome(), 0f, 0f, 0f, 0f, 0, 0, 0, "", "", ""), TABELA_ALUNO);
                                }
                            }
                        } else {
                            Disciplina[] disciplinas = db.disciplinas(user.getNdisc());
                            for (int i = 0; i < disciplinas.length; i++) {
                                db.updateDisciplina(new Disciplina(disciplinas[i].getIdd(), disciplinas[i].getDnome(), disciplinas[i].getDcod(), disciplinas[i].getDsala(), disciplinas[i].getDidca(), 0, 0, 0, "", "", "", 0f, 0f, 0f, 0f));
                            }
                        }
                        for (int c = 1; c < 7; c++) {
                            Aulas aulas = db.selectAulas(c);
                            db.updateAulas(new Aulas(aulas.getIdaulas(), 0, "", "", "", ""));
                        }
                        db.deleteEventos();
                        dialog.dismiss();
                        Intent intent = new Intent(Definicoes.this, Registo.class);
                        intent.putExtra("NY", 1);
                        startActivity(intent);
                        finishAffinity();
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btnWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSite = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.4students.epizy.com"));
                startActivity(intentSite);
            }
        });

        btnDenBug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBug = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.4students.epizy.com/bug.html"));
                startActivity(intentBug);
            }
        });

        btnDarOpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentOpi = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.4students.epizy.com/opiniao.html"));
                startActivity(intentOpi);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            Intent intent = new Intent(Definicoes.this, PaginaInicial.class);
            startActivity(intent);
            finishAffinity();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Definicoes.this, PaginaInicial.class);
        startActivity(intent);
        finishAffinity();
    }
}
