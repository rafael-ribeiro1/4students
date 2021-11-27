package com.deadpixel.dpixel.a4student;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CANomeDialog {

    boolean e1=false, ea=false;

    public void showDialog(final Activity activity, final int cod, final int id, final int ny) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.ca_nome_dialog);

        final BaseDados db = new BaseDados(activity);

        final TextView txtNomeCA = dialog.findViewById(R.id.txtNomeCA);
        final EditText edtNomeCA = dialog.findViewById(R.id.edtNomeCA);
        Button btnCriarCa = dialog.findViewById(R.id.btnCriarCA);
        final TextView erroCriarCA = dialog.findViewById(R.id.erroCriarCA);

        User select_user = db.selectUser(1);
        String nCA = activity.getString(R.string.CA_nome_predefinido) + " " + String.valueOf(select_user.getNca() + 1);
        edtNomeCA.setText(nCA);

        btnCriarCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((edtNomeCA.getText().toString()).trim().equals("")) {
                    erroCriarCA.setText(activity.getString(R.string.preencher_todos_os_campos));
                    txtNomeCA.setText(R.string.erro_nome_aluno);
                    txtNomeCA.setTextColor(0xffff4444);
                    e1 = true;
                } else {
                    if (db.verificarNExiCANome(edtNomeCA.getText().toString())) {
                        db.insertCriterioAvaliacao(new CriterioAvaliacao(edtNomeCA.getText().toString(), 0, "", ""));
                        User select_user = db.selectUser(1);
                        db.updateUser(new User(1, select_user.getPnome(), select_user.getUnome(), select_user.getNperiodos(), select_user.getIperiodos(), select_user.getFperiodos(), select_user.getSclassificacao(), select_user.getTipo(), select_user.getVer(), select_user.getNdisc(), select_user.getNtur(), select_user.getNca() + 1, select_user.getNpor(), select_user.getNot(), select_user.getNotd(), select_user.getNoth()));
                        CriterioAvaliacao criterioAvaliacao = db.selectCriterioAvaliacao(edtNomeCA.getText().toString());
                        int idca = criterioAvaliacao.getIdca();
                        Temp temp = db.selectTemp(1);
                        db.updateTemp(new Temp(1, temp.getDts1(), temp.getDts2(), temp.getDts3(), temp.getDti1(), idca, 0, 0));
                        dialog.dismiss();
                        /*TextView VerCACriar = activity.findViewById(R.id.VerCACriar);
                        String ver = VerCACriar.getText().toString() + " ";
                        VerCACriar.setText(ver);*/
                        Intent intent;
                        if (cod == 1) {
                            intent = new Intent(activity, AddCA.class);
                        } else if (cod == 2){
                            intent = new Intent(activity, AddCAA.class);
                        } else {
                            intent = new Intent(activity, AddCAAE.class);
                            intent.putExtra("DTID", id);
                        }
                        if (ny == 0) {
                            intent.putExtra("NY", 0);
                        } else {
                            intent.putExtra("NY", 1);
                        }
                        activity.startActivity(intent);
                    } else {
                        erroCriarCA.setText(activity.getString(R.string.erro_ca_jareg));
                        txtNomeCA.setText(R.string.erro_nome_aluno);
                        txtNomeCA.setTextColor(0xffff4444);
                        ea = true;
                    }
                }
            }
        });

        edtNomeCA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (e1) {
                    txtNomeCA.setText(R.string.nome);
                    txtNomeCA.setTextColor(0xff000000);
                    e1 = false;
                    erroCriarCA.setText("");
                }
                if (ea) {
                    txtNomeCA.setText(R.string.nome);
                    txtNomeCA.setTextColor(0xff000000);
                    ea = false;
                    erroCriarCA.setText("");
                }
            }
        });

        dialog.show();
    }
}
