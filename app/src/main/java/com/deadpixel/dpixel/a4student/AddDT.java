package com.deadpixel.dpixel.a4student;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddDT extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
        setContentView(R.layout.activity_add_dt);

        User user = db.selectUser(1);
        if (user.getVer() == 1) {
            finish();
        }

        final int ny = getIntent().getIntExtra("NY", 0);

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

        final Temp temp = db.selectTemp(1);

        if (temp.getDtf1() == 1.0f) {
            select_ca = db.selectCriterioAvaliacaoId(temp.getDti2());
        } else {
            select_ca = new CriterioAvaliacao();
            select_ca.setIdca(0);
        }

        // Spinner de seleção de sistema de classificação
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sclassificacao, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Tscspinner.setAdapter(adapter);
        if (temp.getDti1() != 0) {
            Tscspinner.setSelection(temp.getDti1() - 1);
        }
        Tscspinner.setOnItemSelectedListener(this);

        // Criação do Dialog para seleção de Critério de Avaliação
        final AlertDialog.Builder builder = new AlertDialog.Builder(AddDT.this);
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
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.adicionar_turma_titulo);
            lltur.setVisibility(View.VISIBLE);
            lldisc.setVisibility(View.GONE);

            Temp temp_select1 = db.selectTemp(1);
            TedtNome.setText(temp_select1.getDts1());
            TedtCod.setText(temp_select1.getDts2());

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
                        if (db.verificarNExiTCod(TedtCod.getText().toString())) {
                            int ca;
                            if (select_ca.getIdca() == 0) {
                                ca = 0;
                            } else {
                                ca = select_ca.getIdca();
                            }
                            db.insertTurma(new Turma(TedtNome.getText().toString(), TedtCod.getText().toString(), "", ca, numsc, 0));
                            db.createTableAluno("abcde" + TedtCod.getText().toString() + "edcba");
                            User select_user = db.selectUser(1);
                            db.updateUser(new User(1, select_user.getPnome(), select_user.getUnome(), select_user.getNperiodos(), select_user.getIperiodos(), select_user.getFperiodos(), select_user.getSclassificacao(), select_user.getTipo(), select_user.getVer(), select_user.getNdisc(), select_user.getNtur() + 1, select_user.getNca(), select_user.getNpor(), select_user.getNot(), select_user.getNotd(), select_user.getNoth()));
                            db.updateTemp(new Temp(1, TedtCod.getText().toString(), "", "", 0, 0, 0, 0));
                            Intent intentAddA = new Intent(AddDT.this, AddAlunos.class);
                            if (ny == 0) {
                                intentAddA.putExtra("NY", 0);
                            } else {
                                intentAddA.putExtra("NY", 1);
                            }
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
                        Toast.makeText(AddDT.this, getString(R.string.nao_tem_ca), Toast.LENGTH_LONG).show();
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
                    dialog.showDialog(AddDT.this, 1, 0, ny);
                }
            });
            /*VerCACriar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable s) {

                }
            });*/

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
                        if (db.verificarNExiTCod(TedtCod.getText().toString())) {
                            int ca;
                            if (select_ca.getIdca() == 0) {
                                ca = 0;
                            } else {
                                ca = select_ca.getIdca();
                            }
                            db.insertTurma(new Turma(TedtNome.getText().toString(), TedtCod.getText().toString(), "", ca, numsc, 0));
                            db.createTableAluno("abcde" + TedtCod.getText().toString() + "edcba");
                            User select_user = db.selectUser(1);
                            db.updateUser(new User(1, select_user.getPnome(), select_user.getUnome(), select_user.getNperiodos(), select_user.getIperiodos(), select_user.getFperiodos(), select_user.getSclassificacao(), select_user.getTipo(), select_user.getVer(), select_user.getNdisc(), select_user.getNtur() + 1, select_user.getNca(), select_user.getNpor(), select_user.getNot(), select_user.getNotd(), select_user.getNoth()));
                            db.updateTemp(new Temp(1, "", "", "", 0, 0, 0, 0));
                            Intent intentTAdd = new Intent(AddDT.this, Registo3.class);
                            if (ny == 0) {
                                intentTAdd.putExtra("NY", 0);
                            } else {
                                intentTAdd.putExtra("NY", 1);
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
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.adicionar_disciplina_titulo);
            lltur.setVisibility(View.GONE);
            lldisc.setVisibility(View.VISIBLE);

            Temp temp_select2 = db.selectTemp(1);
            DedtNome.setText(temp_select2.getDts1());
            DedtCod.setText(temp_select2.getDts2());

            DbtnUsarCA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User select_user = db.selectUser(1);
                    if (select_user.getNca() == 0) {
                        Toast.makeText(AddDT.this, getString(R.string.nao_tem_ca), Toast.LENGTH_LONG).show();
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
                    dialog.showDialog(AddDT.this, 1, 0, ny);
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
                        if (db.verificarNExiDCod(DedtCod.getText().toString())) {
                            db.updateTemp(new Temp(1, "", "", "", 0, 0, 0, 0));
                            int ca;
                            if (select_ca.getIdca() == 0) {
                                ca = 0;
                            } else {
                                ca = select_ca.getIdca();
                            }
                            db.insertDisciplina(new Disciplina(DedtNome.getText().toString(), DedtCod.getText().toString(), "", ca, 0, 0, 0, "", "", "", 0.0f, 0.0f, 0.0f, 0.0f));
                            User select_user = db.selectUser(1);
                            db.updateUser(new User(1, select_user.getPnome(), select_user.getUnome(), select_user.getNperiodos(), select_user.getIperiodos(), select_user.getFperiodos(), select_user.getSclassificacao(), select_user.getTipo(), 0, select_user.getNdisc() + 1, select_user.getNtur(), select_user.getNca(), select_user.getNpor(), select_user.getNot(), select_user.getNotd(), select_user.getNoth()));
                            Intent intentDAdd = new Intent(AddDT.this, Registo3.class);
                            if (ny == 0) {
                                intentDAdd.putExtra("NY", 0);
                            } else {
                                intentDAdd.putExtra("NY", 1);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        numsc = position + 1;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
