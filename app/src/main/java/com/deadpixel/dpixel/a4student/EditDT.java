package com.deadpixel.dpixel.a4student;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

public class EditDT extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    BaseDados db = new BaseDados(this);

    LinearLayout lldisc, lltur;
    TextView DtxtNome, DtxtCod, TtxtNome, TtxtCod, DerroAdd, TerroAdd, VerCACriar;
    EditText DedtNome, DedtCod, TedtNome, TedtCod;
    Button DbtnUsarCA, DbtnCriarCA, DbtnAdd, TbtnUsarCA, TbtnCriarCA, TbtnAdd, TbtnAddA;
    boolean de1=false, de2=false, te1=false, te2=false, tea=false, dea=false;
    CriterioAvaliacao select_ca;
    Spinner Tscspinner;
    int numsc = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dt);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        final int ny = getIntent().getIntExtra("NY", 2);

        User user = db.selectUser(1);

        User select_user = db.selectUser(1);

        lldisc = findViewById(R.id.lldisc);
        lltur = findViewById(R.id.lltur);
        DtxtNome = findViewById(R.id.DtxtNome);
        DtxtCod = findViewById(R.id.DtxtCod);
        DedtNome = findViewById(R.id.DedtNome);
        DedtCod = findViewById(R.id.DedtCod);
        DbtnUsarCA = findViewById(R.id.DbtnUsarCA);
        DbtnCriarCA = findViewById(R.id.DbtnCriarCA);
        DbtnAdd = findViewById(R.id.DbtnAdd);
        TtxtNome = findViewById(R.id.TtxtNome);
        TtxtCod = findViewById(R.id.TtxtCod);
        TedtNome = findViewById(R.id.TedtNome);
        TedtCod = findViewById(R.id.TedtCod);
        TbtnUsarCA = findViewById(R.id.TbtnUsarCA);
        TbtnCriarCA = findViewById(R.id.TbtnCriarCA);
        TbtnAdd = findViewById(R.id.TbtnAdd);
        DerroAdd = findViewById(R.id.DerroAdd);
        TerroAdd = findViewById(R.id.TerroAdd);
        TbtnAddA = findViewById(R.id.TbtnAddA);
        Tscspinner = findViewById(R.id.Tscspinner);
        VerCACriar = findViewById(R.id.VerCACriar);

        final Temp temp2 = db.selectTemp(1);

        final int id = getIntent().getIntExtra("DTID", 0);

        final Turma turma = db.selectTurmaById(id);

        db.updateTemp(new Temp(temp2.getIddt(), temp2.getDts1(), temp2.getDts2(), temp2.getDts3(), turma.getTscla(), temp2.getDti2(), temp2.getDtf1(), temp2.getDtf2()));

        final Temp temp = db.selectTemp(1);

        // Spinner de seleção de sistema de classificação
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sclassificacao, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Tscspinner.setAdapter(adapter);
        if (temp.getDti1() != 0) {
            Tscspinner.setSelection(temp.getDti1() - 1);
        }
        Tscspinner.setOnItemSelectedListener(this);

        // Criação do Dialog para seleção de Critério de Avaliação
        final AlertDialog.Builder builder = new AlertDialog.Builder(EditDT.this);
        builder.setTitle(R.string.escolha_os_criterios);
        List<CriterioAvaliacao> criterioAvaliacaoList = db.listCriterioAvaliacao();
        final String[] caArray = new String[select_user.getNca()];
        int c = 0;
        for (CriterioAvaliacao ca : criterioAvaliacaoList) {
            caArray[c] = ca.getCanome();
            c++;
        }
        builder.setItems(caArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ca_selected = caArray[which];
                select_ca = db.selectCriterioAvaliacao(ca_selected);
            }
        });

        if (select_user.getTipo().equals("prof")) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.editar_turma);
            lltur.setVisibility(View.VISIBLE);
            lldisc.setVisibility(View.GONE);

            TedtNome.setText(turma.getTnome());
            TedtCod.setText(turma.getTcod());
            if (temp.getDtf1() == 1.0f) {
                select_ca = db.selectCriterioAvaliacaoId(temp.getDti2());
            } else {
                select_ca = db.selectCriterioAvaliacaoId(turma.getTidca());
            }

            Temp temp_select1 = db.selectTemp(1);
            if (!temp_select1.getDts1().equals("")) {
                TedtNome.setText(temp_select1.getDts1());
                TedtCod.setText(temp_select1.getDts2());
            }

            final String ex_table_aluno = getIntent().getStringExtra("DTCOD");

            TbtnAddA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (String.valueOf(TedtNome.getText()).trim().equals("") || String.valueOf(TedtCod.getText()).trim().equals("")) {
                        TerroAdd.setText(R.string.preencher_todos_os_campos);
                        if (String.valueOf(TedtNome.getText()).trim().equals("")) {
                            TtxtNome.setText(getString(R.string.nome_turma_erro));
                            TtxtNome.setTextColor(0xffff4444);
                            te1 = true;
                        }
                        if (String.valueOf(TedtCod.getText()).trim().equals("")) {
                            TtxtCod.setText(getString(R.string.identificacao_turma_erro));
                            TtxtCod.setTextColor(0xffff4444);
                            te2 = true;
                        }
                    } else {
                        if (db.verificarNExiTCod(TedtCod.getText().toString()) || TedtCod.getText().toString().equals(turma.getTcod())) {
                            int ca;
                            if (select_ca.getIdca() == 0) {
                                ca = 0;
                            } else {
                                ca = select_ca.getIdca();
                            }
                            Turma turma = db.selectTurmaById(id);
                            db.updateTurma(new Turma(id, TedtNome.getText().toString(), TedtCod.getText().toString(), "", ca, numsc, turma.getTnalu()));
                            if (!("abcde" + TedtCod.getText().toString() + "edcba").equals("abcde" + ex_table_aluno + "edcba")) {
                                db.changeTableAluno("abcde" + TedtCod.getText().toString() + "edcba", "abcde" + ex_table_aluno + "edcba");
                            }
                            User select_user = db.selectUser(1);
                            db.updateTemp(new Temp(1, TedtCod.getText().toString(), "", "", 0, 0, 0, 0));
                            Intent intentAddA = new Intent(EditDT.this, EditAlunos.class);
                            intentAddA.putExtra("NY", ny);
                            startActivity(intentAddA);
                            finish();
                        } else {
                            TerroAdd.setText(getString(R.string.erro_codturmajregistado));
                            TtxtCod.setText(getString(R.string.identificacao_turma_erro));
                            TtxtCod.setTextColor(0xffff4444);
                            tea = true;
                        }
                    }
                }
            });

            TbtnUsarCA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User select_user = db.selectUser(1);
                    if (select_user.getNca() == 0) {
                        Toast.makeText(EditDT.this, getString(R.string.nao_tem_ca), Toast.LENGTH_LONG).show();
                    } else {
                        AlertDialog dialog1 = builder.create();
                        dialog1.show();
                    }
                }
            });

            TbtnCriarCA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.updateTemp(new Temp(1, TedtNome.getText().toString(), TedtCod.getText().toString(), "", numsc, temp.getDti2(), temp.getDtf1(), temp.getDtf2()));
                    CANomeDialog dialog = new CANomeDialog();
                    dialog.showDialog(EditDT.this, 3, id, 0);
                }
            });

            TbtnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (String.valueOf(TedtNome.getText()).trim().equals("") || String.valueOf(TedtCod.getText()).trim().equals("")) {
                        TerroAdd.setText(R.string.preencher_todos_os_campos);
                        if (String.valueOf(TedtNome.getText()).trim().equals("")) {
                            TtxtNome.setText(getString(R.string.nome_turma_erro));
                            TtxtNome.setTextColor(0xffff4444);
                            te1 = true;
                        }
                        if (String.valueOf(TedtCod.getText()).trim().equals("")) {
                            TtxtCod.setText(getString(R.string.identificacao_turma_erro));
                            TtxtCod.setTextColor(0xffff4444);
                            te2 = true;
                        }
                    } else {
                        if (db.verificarNExiTCod(TedtCod.getText().toString()) || TedtCod.getText().toString().equals(turma.getTcod())) {
                            int ca;
                            if (select_ca.getIdca() == 0) {
                                ca = 0;
                            } else {
                                ca = select_ca.getIdca();
                            }
                            Turma turma = db.selectTurmaById(id);
                            db.updateTurma(new Turma(id, TedtNome.getText().toString(), TedtCod.getText().toString(), "", ca, numsc, turma.getTnalu()));
                            if (!("abcde" + TedtCod.getText().toString() + "edcba").equals("abcde" + ex_table_aluno + "edcba")) {
                                db.changeTableAluno("abcde" + TedtCod.getText().toString() + "edcba", "abcde" + ex_table_aluno + "edcba");
                            }
                            User select_user = db.selectUser(1);
                            db.updateTemp(new Temp(1, "", "", "", 0, 0, 0, 0));
                            Intent intentTAdd;
                            if (ny == 0 || ny == 1) {
                                intentTAdd = new Intent(EditDT.this, Registo3.class);
                                intentTAdd.putExtra("NY", ny);
                            } else {
                                intentTAdd = new Intent(EditDT.this, Avaliacao.class);
                            }
                            startActivity(intentTAdd);
                            finish();
                        } else {
                            TerroAdd.setText(getString(R.string.erro_codturmajregistado));
                            TtxtCod.setText(getString(R.string.identificacao_turma_erro));
                            TtxtCod.setTextColor(0xffff4444);
                            tea = true;
                        }
                    }
                }
            });

            TedtNome.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    if (te1) {
                        TtxtNome.setText(getString(R.string.nome_da_turma));
                        TtxtNome.setTextColor(0xff000000);
                        te1 = false;
                    }
                    if (!te1 && !te2) {
                        TerroAdd.setText("");
                    }
                }
            });
            TedtCod.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    if (te2) {
                        TtxtCod.setText(getString(R.string.identifica_o_da_turma));
                        TtxtCod.setTextColor(0xff000000);
                        te2 = false;
                    }
                    if (!te1 && !te2) {
                        TerroAdd.setText("");
                    }
                    if (tea) {
                        TtxtCod.setText(getString(R.string.identifica_o_da_turma));
                        TtxtCod.setTextColor(0xff000000);
                        tea = false;
                        TerroAdd.setText("");
                    }
                }
            });

        } else {
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.editar_disciplina);
            lltur.setVisibility(View.GONE);
            lldisc.setVisibility(View.VISIBLE);

            final Disciplina disciplina = db.selectDisciplinaById(id);
            DedtNome.setText(disciplina.getDnome());
            DedtCod.setText(disciplina.getDcod());
            if (temp.getDtf1() == 1.0f) {
                select_ca = db.selectCriterioAvaliacaoId(temp.getDti2());
            } else {
                select_ca = db.selectCriterioAvaliacaoId(disciplina.getDidca());
            }

            Temp temp_select2 = db.selectTemp(1);
            if (!temp_select2.getDts1().equals("")) {
                DedtNome.setText(temp_select2.getDts1());
                DedtCod.setText(temp_select2.getDts2());
            }

            DbtnUsarCA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User select_user = db.selectUser(1);
                    if (select_user.getNca() == 0) {
                        Toast.makeText(EditDT.this, getString(R.string.nao_tem_ca), Toast.LENGTH_LONG).show();
                    } else {
                        AlertDialog dialog2 = builder.create();
                        dialog2.show();
                    }
                }
            });

            DbtnCriarCA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.updateTemp(new Temp(1, DedtNome.getText().toString(), DedtCod.getText().toString(), "", temp.getDti1(), temp.getDti2(), temp.getDtf1(), temp.getDtf2()));
                    CANomeDialog dialog = new CANomeDialog();
                    dialog.showDialog(EditDT.this, 3, id, 0);
                }
            });

            DbtnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (String.valueOf(DedtNome.getText()).trim().equals("") || String.valueOf(DedtCod.getText()).trim().equals("")) {
                        DerroAdd.setText(R.string.preencher_todos_os_campos);
                        if (String.valueOf(DedtNome.getText()).trim().equals("")) {
                            DtxtNome.setText(getString(R.string.nome_disciplina_erro));
                            DtxtNome.setTextColor(0xffff4444);
                            de1 = true;
                        }
                        if (String.valueOf(DedtCod.getText()).trim().equals("")) {
                            DtxtCod.setText(getString(R.string.abreviatura_disciplina_erro));
                            DtxtCod.setTextColor(0xffff4444);
                            de2 = true;
                        }
                    } else {
                        if (db.verificarNExiDCod(DedtCod.getText().toString()) || DedtCod.getText().toString().equals(disciplina.getDcod())) {
                            db.updateTemp(new Temp(1, "", "", "", 0, 0, 0, 0));
                            int ca;
                            if (select_ca.getIdca() == 0) {
                                ca = 0;
                            } else {
                                ca = select_ca.getIdca();
                            }
                            Disciplina disciplina = db.selectDisciplinaById(id);
                            db.updateDisciplina(new Disciplina(id, DedtNome.getText().toString(), DedtCod.getText().toString(), "", ca, disciplina.getAava1(), disciplina.getAava2(), disciplina.getAava3(), disciplina.getCaaava1(), disciplina.getCaaava2(), disciplina.getCaaava3(), disciplina.getNotaper1(), disciplina.getNotaper2(), disciplina.getNotaper3(), disciplina.getNotafinald()));
                            User select_user = db.selectUser(1);
                            Intent intentDAdd;
                            if (ny == 0 || ny == 1) {
                                intentDAdd = new Intent(EditDT.this, Registo3.class);
                                intentDAdd.putExtra("NY", ny);
                            } else {
                                intentDAdd = new Intent(EditDT.this, Avaliacao.class);
                            }
                            startActivity(intentDAdd);
                            finish();
                        } else {
                            DerroAdd.setText(getString(R.string.erro_codalujaregistado));
                            DtxtCod.setText(getString(R.string.abreviatura_disciplina_erro));
                            DtxtCod.setTextColor(0xffff4444);
                            dea = true;
                        }
                    }
                }
            });

            DedtNome.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    if (de1) {
                        DtxtNome.setText(getString(R.string.nome_da_disciplina));
                        DtxtNome.setTextColor(0xff000000);
                        de1 = false;
                    }
                    if (!de1 && !de2) {
                        DerroAdd.setText("");
                    }
                }
            });
            DedtCod.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    if (de2) {
                        DtxtCod.setText(getString(R.string.abreviatura_da_disciplina));
                        DtxtCod.setTextColor(0xff000000);
                        de2 = false;
                    }
                    if (!de1 && !de2) {
                        DerroAdd.setText("");
                    }
                    if (dea) {
                        DtxtCod.setText(getString(R.string.abreviatura_da_disciplina));
                        DtxtCod.setTextColor(0xff000000);
                        dea = false;
                        DerroAdd.setText("");
                    }
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            finish();
            db.updateTemp(new Temp(1, "", "", "", 0, 0, 0, 0));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        db.updateTemp(new Temp(1, "", "", "", 0, 0, 0, 0));
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        numsc = position + 1;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
