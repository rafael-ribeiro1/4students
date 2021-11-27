package com.deadpixel.dpixel.a4student;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class DT extends AppCompatActivity {

    BaseDados db = new BaseDados(this);

    Button btnAva1Per, btnAva2Per, btnAva3Per, btnAAva1Per, btnAAva2Per, btnAAva3Per;
    TextView caDadT, scDadT, naDadT, caDadD, scDadD;
    LinearLayout llnot1per, llnot2per, llnot3per, llturmaa, lldisca;
    ConstraintLayout clAulasHojeT, clAulasHojeD, clAvaAlu;
    ListView listAulasST, listAulasSD, listAvaAlu;
    TextView not1per, not2per, not3per;

    ArrayList<Aula> listAulasT;
    private static AAAListAdapter Tadapter;

    ArrayList<Aula> listAulasD;
    private static AAAListAdapter Dadapter;

    ArrayList<Aluno> listAlu;
    private static AluListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dt);

        String tit = getIntent().getStringExtra("NOMEDT");
        Objects.requireNonNull(getSupportActionBar()).setTitle(tit);

        String cod = getIntent().getStringExtra("CODDT");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        llturmaa = findViewById(R.id.llturmaa);
        lldisca = findViewById(R.id.lldisca);
        btnAva1Per = findViewById(R.id.btnAva1Per);
        btnAva2Per = findViewById(R.id.btnAva2Per);
        btnAva3Per = findViewById(R.id.btnAva3Per);
        btnAAva1Per = findViewById(R.id.btnAAva1Per);
        btnAAva2Per = findViewById(R.id.btnAAva2Per);
        btnAAva3Per = findViewById(R.id.btnAAva3Per);
        caDadT = findViewById(R.id.caDadT);
        scDadT = findViewById(R.id.scDadT);
        naDadT = findViewById(R.id.naDadT);
        caDadD = findViewById(R.id.caDadD);
        scDadD = findViewById(R.id.scDadD);
        llnot1per = findViewById(R.id.llnot1per);
        llnot2per = findViewById(R.id.llnot2per);
        llnot3per = findViewById(R.id.llnot3per);
        clAulasHojeT = findViewById(R.id.clAulasHojeT);
        clAulasHojeD = findViewById(R.id.clAulasHojeD);
        listAulasST = findViewById(R.id.listAulasST);
        listAulasSD = findViewById(R.id.listAulasSD);
        not1per = findViewById(R.id.not1per);
        not2per = findViewById(R.id.not2per);
        not3per = findViewById(R.id.not3per);
        clAvaAlu = findViewById(R.id.clAvaAlu);
        listAvaAlu = findViewById(R.id.listAvaAlu);

        listAulasT = new ArrayList<>();
        listAulasD = new ArrayList<>();

        User user = db.selectUser(1);

        if (user.getTipo().equals("prof")) {
            llturmaa.setVisibility(View.VISIBLE);
            lldisca.setVisibility(View.GONE);
            if (user.getNperiodos() == 1) {
                btnAva1Per.setVisibility(View.VISIBLE);
                btnAva2Per.setVisibility(View.GONE);
                btnAva3Per.setVisibility(View.GONE);
            } else if (user.getNperiodos() == 2) {
                btnAva1Per.setVisibility(View.VISIBLE);
                btnAva2Per.setVisibility(View.VISIBLE);
                btnAva3Per.setVisibility(View.GONE);
            } else {
                btnAva1Per.setVisibility(View.VISIBLE);
                btnAva2Per.setVisibility(View.VISIBLE);
                btnAva3Per.setVisibility(View.VISIBLE);
            }

            final Turma turma = db.selectTurma(cod);
            int idt = turma.getIdt();

            final String TABELA_ALUNO = "abcde" + cod + "edcba";

            for (int c = 1; c < 7; c++) {
                listAulasT = db.listAulas3(listAulasT, c, idt);
            }
            if (listAulasT.size() != 0) {
                Tadapter = new AAAListAdapter(listAulasT, DT.this);
                listAulasST.setAdapter(Tadapter);
                clAulasHojeT.setVisibility(View.GONE);
            }
            listAulasST.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(DT.this, Horario.class);
                    startActivity(intent);
                    finishAffinity();
                }
            });

            if (turma.getTidca() == 0) {
                caDadT.setText(getString(R.string.n_a));
            } else {
                CriterioAvaliacao criterioAvaliacao = db.selectCriterioAvaliacaoId(turma.getTidca());
                caDadT.setText(criterioAvaliacao.getCanome());
            }
            switch (turma.getTscla()) {
                case 1:
                    scDadT.setText(getString(R.string.sc1));
                    break;
                case 2:
                    scDadT.setText(getString(R.string.sc2));
                    break;
                case 3:
                    scDadT.setText(getString(R.string.sc3));
                case 4:
                    scDadT.setText(getString(R.string.sc4));
                    break;
                case 5:
                    scDadT.setText(getString(R.string.sc5));
                    break;
            }
            naDadT.setText(Integer.toString(turma.getTnalu()));

            if (turma.getTnalu() == 0) {
                clAvaAlu.setVisibility(View.VISIBLE);
            } else {
                clAvaAlu.setVisibility(View.GONE);
                listAlu = db.listAlunos(TABELA_ALUNO);
                adapter = new AluListAdapter(listAlu, DT.this, user.getNperiodos());
                listAvaAlu.setAdapter(adapter);
            }

            btnAva1Per.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (turma.getTidca() == 0) {
                        Toast.makeText(DT.this, "Sem Critérios de Avaliação", Toast.LENGTH_SHORT).show();
                    } else {
                        if (turma.getTnalu() == 0) {
                            Toast.makeText(DT.this, "Sem Alunos Registados", Toast.LENGTH_SHORT).show();
                        } else {
                            AvaAluDialog dialog = new AvaAluDialog();
                            dialog.showDialog(DT.this, turma.getTcod(), 1);
                        }
                    }
                }
            });
            btnAva2Per.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (turma.getTidca() == 0) {
                        Toast.makeText(DT.this, "Sem Critérios de Avaliação", Toast.LENGTH_SHORT).show();
                    } else {
                        if (turma.getTnalu() == 0) {
                            Toast.makeText(DT.this, "Sem Alunos Registados", Toast.LENGTH_SHORT).show();
                        } else {
                            AvaAluDialog dialog = new AvaAluDialog();
                            dialog.showDialog(DT.this, turma.getTcod(), 2);
                        }
                    }
                }
            });
            btnAva3Per.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (turma.getTidca() == 0) {
                        Toast.makeText(DT.this, "Sem Critérios de Avaliação", Toast.LENGTH_SHORT).show();
                    } else {
                        if (turma.getTnalu() == 0) {
                            Toast.makeText(DT.this, "Sem Alunos Registados", Toast.LENGTH_SHORT).show();
                        } else {
                            AvaAluDialog dialog = new AvaAluDialog();
                            dialog.showDialog(DT.this, turma.getTcod(), 3);
                        }
                    }
                }
            });
        } else {
            llturmaa.setVisibility(View.GONE);
            lldisca.setVisibility(View.VISIBLE);
            if (user.getNperiodos() == 1) {
                btnAAva1Per.setVisibility(View.VISIBLE);
                btnAAva2Per.setVisibility(View.GONE);
                btnAAva3Per.setVisibility(View.GONE);
                llnot1per.setVisibility(View.VISIBLE);
                llnot2per.setVisibility(View.GONE);
                llnot3per.setVisibility(View.GONE);
            } else if (user.getNperiodos() == 2) {
                btnAAva1Per.setVisibility(View.VISIBLE);
                btnAAva2Per.setVisibility(View.VISIBLE);
                btnAAva3Per.setVisibility(View.GONE);
                llnot1per.setVisibility(View.VISIBLE);
                llnot2per.setVisibility(View.VISIBLE);
                llnot3per.setVisibility(View.GONE);
            } else {
                btnAAva1Per.setVisibility(View.VISIBLE);
                btnAAva2Per.setVisibility(View.VISIBLE);
                btnAAva3Per.setVisibility(View.VISIBLE);
                llnot1per.setVisibility(View.VISIBLE);
                llnot2per.setVisibility(View.VISIBLE);
                llnot3per.setVisibility(View.VISIBLE);
            }

            final Disciplina disciplina = db.selectDisciplina(cod);
            int idd = disciplina.getIdd();

            for (int c = 1; c < 7; c++) {
                listAulasD = db.listAulas3(listAulasD, c, idd);
            }
            if (listAulasD.size() != 0) {
                Dadapter = new AAAListAdapter(listAulasD, DT.this);
                listAulasSD.setAdapter(Dadapter);
                clAulasHojeD.setVisibility(View.GONE);
            }
            listAulasSD.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(DT.this, Horario.class);
                    startActivity(intent);
                    finishAffinity();
                }
            });

            if (disciplina.getDidca() == 0) {
                caDadD.setText(getString(R.string.n_a));
            } else {
                CriterioAvaliacao criterioAvaliacao = db.selectCriterioAvaliacaoId(disciplina.getDidca());
                caDadD.setText(criterioAvaliacao.getCanome());
            }
            switch (user.getSclassificacao()) {
                case 1:
                    scDadD.setText(getString(R.string.sc1));
                    break;
                case 2:
                    scDadD.setText(getString(R.string.sc2));
                    break;
                case 3:
                    scDadD.setText(getString(R.string.sc3));
                    break;
                case 4:
                    scDadD.setText(getString(R.string.sc4));
                    break;
                case 5:
                    scDadD.setText(getString(R.string.sc5));
                    break;
            }

            if (disciplina.getAava1() == 1) {
                not1per.setText(Float.toString(disciplina.getNotaper1()));
            }
            if (disciplina.getAava2() == 1) {
                not2per.setText(Float.toString(disciplina.getNotaper2()));
            }
            if (disciplina.getAava3() == 1) {
                not3per.setText(Float.toString(disciplina.getNotaper3()));
            }

            btnAAva1Per.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (disciplina.getDidca() == 0) {
                        Toast.makeText(DT.this, "Sem Critérios de Avaliação", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(DT.this, AAva.class);
                        intent.putExtra("COD", disciplina.getDcod());
                        intent.putExtra("NPER", 1);
                        startActivity(intent);
                        finish();
                    }
                }
            });
            btnAAva2Per.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (disciplina.getDidca() == 0) {
                        Toast.makeText(DT.this, "Sem Critérios de Avaliação", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(DT.this, AAva.class);
                        intent.putExtra("COD", disciplina.getDcod());
                        intent.putExtra("NPER", 2);
                        startActivity(intent);
                        finish();
                    }
                }
            });
            btnAAva3Per.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (disciplina.getDidca() == 0) {
                        Toast.makeText(DT.this, "Sem Critérios de Avaliação", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(DT.this, AAva.class);
                        intent.putExtra("COD", disciplina.getDcod());
                        intent.putExtra("NPER", 3);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
