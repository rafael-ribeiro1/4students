package com.deadpixel.dpixel.a4student;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.PorterDuff;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditarCADialog {

    boolean e1=false, e2=false, em=false;

    public void showDialog(final Activity activity, final int position, final int idca) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.editar_ca_dialog);

        final BaseDados db = new BaseDados(activity);

        final TextView erroEditPara = dialog.findViewById(R.id.erroEditPara);
        final EditText DedtPara = dialog.findViewById(R.id.DedtPara);
        final EditText DedtCot = dialog.findViewById(R.id.DedtCot);
        Button btnEditPara = dialog.findViewById(R.id.btnEditPara);

        CriterioAvaliacao criterioAvaliacao1 = db.selectCriterioAvaliacaoId(idca);
        String[] para = criterioAvaliacao1.getCapara().split("/0/1/");
        String[] cot = criterioAvaliacao1.getCacot().split("/0/1/");
        DedtPara.setText(para[position]);
        DedtCot.setText(cot[position]);

        btnEditPara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((DedtPara.getText().toString()).trim().equals("") || DedtCot.getText().toString().equals("")) {
                    erroEditPara.setText(activity.getString(R.string.preencher_todos_os_campos));
                    if ((DedtPara.getText().toString()).trim().equals("")) {
                        DedtPara.getBackground().setColorFilter(activity.getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                        e1 = true;
                    }
                    if (DedtCot.getText().toString().equals("")) {
                        DedtCot.getBackground().setColorFilter(activity.getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                        e2 = true;
                    }
                } else {
                    if (Integer.parseInt(DedtCot.getText().toString()) > 100 || Integer.parseInt(DedtCot.getText().toString()) < 1) {
                        if (Integer.parseInt(DedtCot.getText().toString()) > 100) {
                            erroEditPara.setText(activity.getString(R.string.erro_cot_mai_100));
                            DedtCot.getBackground().setColorFilter(activity.getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                            em = true;
                        }
                        if (Integer.parseInt(DedtCot.getText().toString()) < 1) {
                            erroEditPara.setText(activity.getString(R.string.erro_cot_0));
                            DedtCot.getBackground().setColorFilter(activity.getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                            em = true;
                        }
                    } else {
                        CriterioAvaliacao criterioAvaliacao = db.selectCriterioAvaliacaoId(idca);
                        String[] para = criterioAvaliacao.getCapara().split("/0/1/");
                        String[] cot = criterioAvaliacao.getCacot().split("/0/1/");
                        para[position] = DedtPara.getText().toString();
                        cot[position] = DedtCot.getText().toString();
                        String paras = "";
                        String cots = "";
                        for (int c = 0; c < para.length; c++) {
                            if (c == 0) {
                                paras += para[c];
                                cots += cot[c];
                            } else {
                                paras += "/0/1/" + para[c];
                                cots += "/0/1/" + cot[c];
                            }
                        }
                        db.updateCriterioAvaliacao(new CriterioAvaliacao(criterioAvaliacao.getIdca(), criterioAvaliacao.getCanome(), criterioAvaliacao.getCanpara(), paras, cots));
                        dialog.dismiss();
                        TextView verDelPara = activity.findViewById(R.id.verDelPara);
                        String DelPara = verDelPara.getText().toString() + " ";
                        verDelPara.setText(DelPara);
                    }
                }
            }
        });
        DedtPara.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (e1) {
                    DedtPara.getBackground().setColorFilter(activity.getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
                    e1 = false;
                }
                if (!e1 && !e2) {
                    erroEditPara.setText("");
                }
            }
        });
        DedtCot.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (em) {
                    erroEditPara.setText("");
                    DedtCot.getBackground().setColorFilter(activity.getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
                    em = false;
                }
                if (e2) {
                    DedtCot.getBackground().setColorFilter(activity.getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
                    e2 = false;
                }
                if (!e1 && !e2) {
                    erroEditPara.setText("");
                }
            }
        });

        dialog.show();
    }
}
