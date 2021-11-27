package com.deadpixel.dpixel.a4student;

import android.app.Activity;
import android.app.Dialog;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AddAlunoDialog {

    boolean e1=false, e2=false, ea=false;

    public void showDialog(final Activity activity, final String cod) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_aluno_dialog);

        final BaseDados db = new BaseDados(activity);

        final String TABELA_ALUNO = "abcde" + cod + "edcba";

        final TextView txtNumA = (TextView) dialog.findViewById(R.id.txtNumAluno);
        final TextView txtNomeA = (TextView) dialog.findViewById(R.id.txtNomeAluno);
        final TextView erroAddA = (TextView) dialog.findViewById(R.id.erroAddA);
        final EditText edtNumA = (EditText) dialog.findViewById(R.id.edtNumA);
        final EditText edtNomeA = (EditText) dialog.findViewById(R.id.edtNomeA);
        Button btnAddAluno = (Button) dialog.findViewById(R.id.btnAddAluno);

        btnAddAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtNumA.getText().toString().equals("") || (edtNomeA.getText().toString()).trim().equals("")) {
                    erroAddA.setText(R.string.preencher_todos_os_campos);
                    if (edtNumA.getText().toString().equals("")) {
                        txtNumA.setText(R.string.erro_num_aluno);
                        txtNumA.setTextColor(0xffff4444);
                        e1 = true;
                    }
                    if ((edtNomeA.getText().toString()).trim().equals("")) {
                        txtNomeA.setText(R.string.erro_nome_aluno);
                        txtNomeA.setTextColor(0xffff4444);
                        e2 = true;
                    }
                } else {
                    if (db.verificarNExiAlu(TABELA_ALUNO, Integer.parseInt(edtNumA.getText().toString()))) {
                        db.insertAluno(new Aluno(Integer.parseInt(edtNumA.getText().toString()), edtNomeA.getText().toString(), 0.0f, 0.0f, 0.0f, 0.0f, 0, 0, 0, "", "", ""), TABELA_ALUNO);
                        Turma turma = db.selectTurma(cod);
                        db.updateTurma(new Turma(turma.getIdt(), turma.getTnome(), turma.getTcod(), turma.getTsala(), turma.getTidca(), turma.getTscla(), turma.getTnalu() + 1));
                        dialog.dismiss();
                        ConstraintLayout clalu = activity.findViewById(R.id.clalu);
                        clalu.setVisibility(View.GONE);
                        TextView VerAddA = activity.findViewById(R.id.verAddA);
                        String txtVer = VerAddA.getText().toString() + " ";
                        VerAddA.setText(txtVer);
                    } else {
                        erroAddA.setText(R.string.erro_ja_exi_alu_num);
                        txtNumA.setText(R.string.erro_num_aluno);
                        txtNumA.setTextColor(0xffff4444);
                        ea = true;
                    }
                }
            }
        });

        edtNumA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (e1) {
                    txtNumA.setText(R.string.numero);
                    txtNumA.setTextColor(0xff000000);
                    e1 = false;
                }
                if (!e1 && !e2) {
                    erroAddA.setText("");
                }
                if (ea) {
                    txtNumA.setText(R.string.numero);
                    txtNumA.setTextColor(0xff000000);
                    ea = false;
                    erroAddA.setText("");
                }
            }
        });
        edtNomeA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (e2) {
                    txtNomeA.setText(R.string.nome);
                    txtNomeA.setTextColor(0xff000000);
                    e2 = false;
                }
                if (!e1 && !e2) {
                    erroAddA.setText("");
                }
            }
        });

        dialog.show();
    }

    public void showDialogEdit(final Activity activity, final String cod, final int anum, String anome) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_aluno_dialog);

        final BaseDados db = new BaseDados(activity);

        final String TABELA_ALUNO = "abcde" + cod + "edcba";

        final TextView txtAddEditA = (TextView) dialog.findViewById(R.id.txtAddEditA);
        final TextView txtNumA = (TextView) dialog.findViewById(R.id.txtNumAluno);
        final TextView txtNomeA = (TextView) dialog.findViewById(R.id.txtNomeAluno);
        final TextView erroAddA = (TextView) dialog.findViewById(R.id.erroAddA);
        final EditText edtNumA = (EditText) dialog.findViewById(R.id.edtNumA);
        final EditText edtNomeA = (EditText) dialog.findViewById(R.id.edtNomeA);
        Button btnAddAluno = (Button) dialog.findViewById(R.id.btnAddAluno);

        txtAddEditA.setText(activity.getString(R.string.editar_aluno));

        edtNumA.setText(String.valueOf(anum));
        edtNomeA.setText(anome);
        edtNumA.setEnabled(false);

        btnAddAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((edtNomeA.getText().toString()).trim().equals("")) {
                    erroAddA.setText(R.string.preencher_todos_os_campos);
                    if ((edtNomeA.getText().toString()).trim().equals("")) {
                        txtNomeA.setText(R.string.erro_nome_aluno);
                        txtNomeA.setTextColor(0xffff4444);
                        e2 = true;
                    }
                } else {
                    Aluno aluno_sele = db.selectAluno(TABELA_ALUNO, anum);
                    db.updateAluno(new Aluno(aluno_sele.getIda(), anum, edtNomeA.getText().toString(), 0.0f, 0.0f, 0.0f, 0.0f, 0, 0, 0, "", "", ""), TABELA_ALUNO);
                    dialog.dismiss();
                    TextView txtAluS = activity.findViewById(R.id.txtAluS);
                    String noalu = anum + " - " + edtNomeA.getText().toString();
                    txtAluS.setText(noalu);
                    TextView VerAddA = activity.findViewById(R.id.VerAddA);
                    String txtVer = VerAddA.getText().toString() + " ";
                    VerAddA.setText(txtVer);
                }
            }
        });

        edtNomeA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (e2) {
                    txtNomeA.setText(R.string.nome);
                    txtNomeA.setTextColor(0xff000000);
                    e2 = false;
                    erroAddA.setText("");
                }
            }
        });

        dialog.show();
    }
}
