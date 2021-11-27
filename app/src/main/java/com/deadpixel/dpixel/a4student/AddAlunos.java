package com.deadpixel.dpixel.a4student;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddAlunos extends AppCompatActivity {

    BaseDados db = new BaseDados(this);

    TextView txtAluS, tvalunos, VerAddA;
    Button btnAva, btnAddAlu, btnEdit, btnDel, btnAddAluno;
    ListView listVAlu;
    ConstraintLayout clalu;

    String TABELA_ALUNO;

    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;

    String al_sele;
    int num_al_sele = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alunos);

        User user = db.selectUser(1);
        if (user.getVer() == 1) {
            finish();
        }

        final int ny = getIntent().getIntExtra("NY", 0);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.registar_alunos_titulo);

        txtAluS = findViewById(R.id.txtAluS);
        tvalunos = findViewById(R.id.tvalunos);
        btnAva = findViewById(R.id.btnAva);
        btnAddAlu = findViewById(R.id.btnAddAlu);
        btnEdit = findViewById(R.id.btnEdit);
        btnDel = findViewById(R.id.btnDel);
        listVAlu = findViewById(R.id.listVAlu);
        clalu = findViewById(R.id.clalu);
        btnAddAluno = findViewById(R.id.btnAddAluno);
        VerAddA = findViewById(R.id.VerAddA);

        final Temp select_temp = db.selectTemp(1);
        TABELA_ALUNO = "abcde" + select_temp.getDts1() + "edcba";

        listAluno();

        // Botão para avançar
        btnAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updateTemp(new Temp(1, "", "", "", 0, 0, 0, 0));
                Intent intentAva = new Intent(AddAlunos.this, Registo3.class);
                if (ny == 0) {
                    intentAva.putExtra("NY", 0);
                } else {
                    intentAva.putExtra("NY", 1);
                }
                startActivity(intentAva);
                finish();
            }
        });

        // Botão para adicionar aluno
        btnAddAlu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAlunoDialog dialog = new AddAlunoDialog();
                dialog.showDialog(AddAlunos.this, select_temp.getDts1());
            }
        });
        listVAlu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                al_sele = parent.getItemAtPosition(position).toString();
                txtAluS.setText(al_sele);
                num_al_sele = Integer.parseInt(al_sele.substring(0, al_sele.indexOf("-") - 1));
                //Toast.makeText(AddAlunos.this, String.valueOf(num_al_sele), Toast.LENGTH_SHORT).show();
            }
        });
        VerAddA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                listAluno();
            }
        });

        // Botão de editar aluno selecionado
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtAluS.getText().toString().equals("Nenhum") || txtAluS.getText().toString().equals("None")) {
                    Toast.makeText(getApplicationContext(), getString(R.string.nenhum_aluno_selecionado), Toast.LENGTH_LONG).show();
                } else {
                    Aluno aluno = db.selectAluno(TABELA_ALUNO, num_al_sele);
                    AddAlunoDialog dialog = new AddAlunoDialog();
                    dialog.showDialogEdit(AddAlunos.this, select_temp.getDts1(), num_al_sele, aluno.getAnome());
                }
            }
        });

        // Botão de apagar aluno selecionado
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtAluS.getText().toString().equals("Nenhum") || txtAluS.getText().toString().equals("None")) {
                    Toast.makeText(getApplicationContext(), getString(R.string.nenhum_aluno_selecionado), Toast.LENGTH_LONG).show();
                } else {
                    Aluno sel_al = db.selectAluno(TABELA_ALUNO, num_al_sele);
                    Aluno del_aluno = new Aluno();
                    del_aluno.setIda(sel_al.getIda());
                    db.deleteAluno(del_aluno, TABELA_ALUNO);
                    Turma turma_sel = db.selectTurma(select_temp.getDts1());
                    db.updateTurma(new Turma(turma_sel.getIdt(), turma_sel.getTnome(), turma_sel.getTcod(), turma_sel.getTsala(), turma_sel.getTidca(), turma_sel.getTscla(), turma_sel.getTnalu() - 1));
                    txtAluS.setText(R.string.nenhum);
                    Turma turma = db.selectTurma(select_temp.getDts1());
                    if (turma.getTnalu() == 0) {
                        clalu.setVisibility(View.VISIBLE);
                    } else {
                        listAluno();
                    }
                }
            }
        });


    }

    // Função para listar alunos
    public void listAluno() {
        List<Aluno> alunoList = db.alunoList(TABELA_ALUNO);
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(AddAlunos.this, android.R.layout.simple_list_item_1, arrayList);
        listVAlu.setAdapter(adapter);
        for (Aluno aluno : alunoList) {
            arrayList.add(aluno.getAnum() + " - " + aluno.getAnome());
            adapter.notifyDataSetChanged();
        }
    }
}
