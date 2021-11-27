package com.deadpixel.dpixel.a4student;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.constraint.ConstraintLayout;
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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class AddCAA extends AppCompatActivity {

    BaseDados db = new BaseDados(this);

    TextView erroAddCa, verDelPara;
    EditText edtPara, edtCot;
    Button btnCriarCAF, btnAddCA;
    ConstraintLayout clca;
    ListView listCA;

    boolean em=false, e1=false, e2=false;

    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_caa);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        final int ny = getIntent().getIntExtra("NY", 0);

        final Temp temp = db.selectTemp(1);
        CriterioAvaliacao criterioAvaliacao = db.selectCriterioAvaliacaoId(temp.getDti2());
        Objects.requireNonNull(getSupportActionBar()).setTitle(criterioAvaliacao.getCanome());

        erroAddCa = findViewById(R.id.erroAddCA);
        edtPara = findViewById(R.id.edtPara);
        edtCot = findViewById(R.id.edtCot);
        btnCriarCAF = findViewById(R.id.btnCriarCAF);
        btnAddCA = findViewById(R.id.btnAddCA);
        clca = findViewById(R.id.clca);
        listCA = findViewById(R.id.listCA);
        verDelPara = findViewById(R.id.verDelPara);

        // Botão para Criar os Critérios de Avaliação e voltar à página de adição de Disciplina/Turma
        btnCriarCAF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CriterioAvaliacao criterioAvaliacao2 = db.selectCriterioAvaliacaoId(temp.getDti2());
                if (criterioAvaliacao2.getCanpara() == 0) {
                    erroAddCa.setText(getString(R.string.erro_cotacao_total));
                } else {
                    String[] cots = criterioAvaliacao2.getCacot().split("/0/1/");
                    int tcot = 0;
                    for (int c = 0; c < cots.length; c++) {
                        tcot += Integer.parseInt(cots[c]);
                    }
                    if (tcot != 100) {
                        erroAddCa.setText(getString(R.string.erro_cotacao_total));
                    } else {
                        db.updateTemp(new Temp(1, temp.getDts1(), temp.getDts2(), temp.getDts3(), temp.getDti1(), temp.getDti2(), 1.0f, 0));
                        Intent intent = new Intent(AddCAA.this, AddDTA.class);
                        if (ny == 0) {
                            intent.putExtra("NY", 0);
                        } else {
                            intent.putExtra("NY", 1);
                        }
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        // Botão para adicionar parâmetro aos Critérios de Avaliação
        btnAddCA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((edtPara.getText().toString()).trim().equals("") || edtCot.getText().toString().equals("")) {
                    erroAddCa.setText(getString(R.string.preencher_todos_os_campos));
                    if ((edtPara.getText().toString()).trim().equals("")) {
                        edtPara.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                        e1 = true;
                    }
                    if (edtCot.getText().toString().equals("")) {
                        edtCot.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                        e2 = true;
                    }
                } else {
                    if (Integer.parseInt(edtCot.getText().toString()) > 100 || Integer.parseInt(edtCot.getText().toString()) < 1) {
                        if (Integer.parseInt(edtCot.getText().toString()) > 100) {
                            erroAddCa.setText(getString(R.string.erro_cot_mai_100));
                            edtCot.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                            em = true;
                        }
                        if (Integer.parseInt(edtCot.getText().toString()) < 1) {
                            erroAddCa.setText(getString(R.string.erro_cot_0));
                            edtCot.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                            em = true;
                        }
                    } else {
                        CriterioAvaliacao criterioAvaliacao1 = db.selectCriterioAvaliacaoId(temp.getDti2());
                        if (criterioAvaliacao1.getCanpara() == 0) {
                            db.updateCriterioAvaliacao(new CriterioAvaliacao(criterioAvaliacao1.getIdca(), criterioAvaliacao1.getCanome(), criterioAvaliacao1.getCanpara() + 1, criterioAvaliacao1.getCapara() + edtPara.getText().toString(), criterioAvaliacao1.getCacot() + edtCot.getText().toString()));
                            clca.setVisibility(View.GONE);
                        } else {
                            db.updateCriterioAvaliacao(new CriterioAvaliacao(criterioAvaliacao1.getIdca(), criterioAvaliacao1.getCanome(), criterioAvaliacao1.getCanpara() + 1, criterioAvaliacao1.getCapara() + "/0/1/" + edtPara.getText().toString(), criterioAvaliacao1.getCacot() + "/0/1/" + edtCot.getText().toString()));
                        }
                        listPara();
                        edtPara.setText("");
                        edtCot.setText("");
                    }
                }
            }
        });

        listCA.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OptionCADialog dialog = new OptionCADialog();
                dialog.showDialog(AddCAA.this, position, temp.getDti2());
            }
        });
        verDelPara.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                listPara();
            }
        });

        edtPara.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (e1) {
                    edtPara.getBackground().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
                    e1 = false;
                }
                if (!e1 && !e2) {
                    erroAddCa.setText("");
                }
            }
        });

        edtCot.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (em) {
                    erroAddCa.setText("");
                    edtCot.getBackground().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
                    em = false;
                }
                if (e2) {
                    edtCot.getBackground().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
                    e2 = false;
                }
                if (!e1 && !e2) {
                    erroAddCa.setText("");
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            Temp temp = db.selectTemp(1);
            CriterioAvaliacao criterioAvaliacao = new CriterioAvaliacao();
            criterioAvaliacao.setIdca(temp.getDti2());
            db.deleteCriterioAvaliacao(criterioAvaliacao);
            User select_user = db.selectUser(1);
            db.updateUser(new User(1, select_user.getPnome(), select_user.getUnome(), select_user.getNperiodos(), select_user.getIperiodos(), select_user.getFperiodos(), select_user.getSclassificacao(), select_user.getTipo(), select_user.getVer(), select_user.getNdisc(), select_user.getNtur(), select_user.getNca() - 1, select_user.getNpor(), select_user.getNot(), select_user.getNotd(), select_user.getNoth()));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    // Alteração da programação ao clicar no botão de voltar para trás
    @Override
    public void onBackPressed() {
        Temp temp = db.selectTemp(1);
        CriterioAvaliacao criterioAvaliacao = new CriterioAvaliacao();
        criterioAvaliacao.setIdca(temp.getDti2());
        db.deleteCriterioAvaliacao(criterioAvaliacao);
        User select_user = db.selectUser(1);
        db.updateUser(new User(1, select_user.getPnome(), select_user.getUnome(), select_user.getNperiodos(), select_user.getIperiodos(), select_user.getFperiodos(), select_user.getSclassificacao(), select_user.getTipo(), select_user.getVer(), select_user.getNdisc(), select_user.getNtur(), select_user.getNca() - 1, select_user.getNpor(), select_user.getNot(), select_user.getNotd(), select_user.getNoth()));
        finish();
    }

    // Função para listar
    public void listPara() {
        Temp temp = db.selectTemp(1);
        CriterioAvaliacao criterioAvaliacao = db.selectCriterioAvaliacaoId(temp.getDti2());
        String[] para = criterioAvaliacao.getCapara().split("/0/1/");
        String[] cot = criterioAvaliacao.getCacot().split("/0/1/");
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(AddCAA.this, android.R.layout.simple_list_item_1, arrayList);
        listCA.setAdapter(adapter);
        for (int c = 0; c < criterioAvaliacao.getCanpara(); c++) {
            arrayList.add(cot[c] + "% - " + para[c]);
            adapter.notifyDataSetChanged();
        }
    }
}
