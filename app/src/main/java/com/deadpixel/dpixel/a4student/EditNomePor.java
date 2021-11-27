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

public class EditNomePor {

    boolean e1=false, ea=false;

    public void showDialog(final Activity activity, final int position) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_por_dialog);

        final BaseDados db = new BaseDados(activity);
        User user = db.selectUser(1);
        final Portfolio portfolio = db.selectPortfolioByPosition(user.getNpor(), position);

        TextView txtAddPor = dialog.findViewById(R.id.txtAddPor);
        final EditText edtNomePor = dialog.findViewById(R.id.edtNomePor);
        final TextView erroAddPor = dialog.findViewById(R.id.erroAddPor);
        Button btnAddPor = dialog.findViewById(R.id.btnAddPor);

        txtAddPor.setText(activity.getString(R.string.editar_nome_portfolio));
        edtNomePor.setText(portfolio.getNomep());
        btnAddPor.setText(activity.getString(R.string.editar));

        btnAddPor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtNomePor.getText().toString().trim().equals("")) {
                    erroAddPor.setText(activity.getString(R.string.preencher_o_campo));
                    e1 = true;
                } else {
                    if (db.verificarNExiPorNome(edtNomePor.getText().toString()) || edtNomePor.getText().toString().equals(portfolio.getNomep())) {
                        db.updatePortfolio(new Portfolio(portfolio.getIdp(), edtNomePor.getText().toString(), portfolio.getNfiles(), portfolio.getFilenames()));
                        dialog.dismiss();
                        TextView verPor = activity.findViewById(R.id.verPor);
                        String txt = verPor.getText() + " ";
                        verPor.setText(txt);
                    } else {
                        erroAddPor.setText(activity.getString(R.string.erro_jaexipor));
                        ea = true;
                    }
                }
            }
        });

        edtNomePor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (e1) {
                    e1 = false;
                    erroAddPor.setText("");
                }
                if (ea) {
                    ea = false;
                    erroAddPor.setText("");
                }
            }
        });

        dialog.show();
    }
}
