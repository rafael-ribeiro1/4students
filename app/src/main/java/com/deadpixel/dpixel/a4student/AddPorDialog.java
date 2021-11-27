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

import java.io.File;

public class AddPorDialog {

    boolean e1=false, ea=false;

    public void showDialog(final Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_por_dialog);

        final BaseDados db = new BaseDados(activity);

        final EditText edtNomePor = dialog.findViewById(R.id.edtNomePor);
        final TextView erroAddPor = dialog.findViewById(R.id.erroAddPor);
        Button btnAddPor = dialog.findViewById(R.id.btnAddPor);

        final User user = db.selectUser(1);

        btnAddPor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtNomePor.getText().toString().trim().equals("")) {
                    erroAddPor.setText(activity.getString(R.string.preencher_o_campo));
                    e1 = true;
                } else {
                    if (db.verificarNExiPorNome(edtNomePor.getText().toString())) {
                        db.updateUser(new User(1, user.getPnome(), user.getUnome(), user.getNperiodos(), user.getIperiodos(), user.getFperiodos(), user.getSclassificacao(), user.getTipo(), user.getVer(), user.getNdisc(), user.getNtur(), user.getNca(), user.getNpor() + 1, user.getNot(), user.getNotd(), user.getNoth()));
                        db.insertPortfolio(new Portfolio(edtNomePor.getText().toString(), 0, ""));
                        Portfolio portfolio = db.selectPortfolio(edtNomePor.getText().toString());
                        File dir = new File(activity.getFilesDir() + File.separator + Integer.toString(portfolio.getIdp()));
                        if (!dir.exists()) {
                            dir.mkdir();
                        }
                        dialog.dismiss();
                        Intent intent = new Intent(activity, Por.class);
                        intent.putExtra("NOMEP", edtNomePor.getText().toString());
                        activity.startActivity(intent);
                        activity.finish();
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
