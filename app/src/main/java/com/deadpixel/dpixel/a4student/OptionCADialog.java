package com.deadpixel.dpixel.a4student;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class OptionCADialog {

    public void showDialog(final Activity activity, final int position, final int idca) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.option_ca_dialog);

        final BaseDados db = new BaseDados(activity);

        Button btnEditarPara = dialog.findViewById(R.id.btnEditarPara);
        Button btnDelPara = dialog.findViewById(R.id.btnDelPara);

        btnEditarPara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                EditarCADialog caDialog = new EditarCADialog();
                caDialog.showDialog(activity, position, idca);
            }
        });

        btnDelPara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CriterioAvaliacao criterioAvaliacao = db.selectCriterioAvaliacaoId(idca);
                String[] para = criterioAvaliacao.getCapara().split("/0/1/");
                String[] cot = criterioAvaliacao.getCacot().split("/0/1/");
                para[position] = "";
                cot[position] = "";
                String[] npara = new String[para.length - 1];
                String[] ncot = new String[cot.length - 1];
                for (int c1 = 0; c1 < para.length; c1++) {
                    if (para[c1].equals("")) {
                        if (c1 != para.length - 1) {
                            npara[c1] = para[c1+1];
                            ncot[c1] = cot[c1+1];
                        }
                    } else {
                        if (c1 != para.length - 1) {
                            npara[c1] = para[c1];
                            ncot[c1] = cot[c1];
                        }
                    }
                }
                String nparas = "";
                String ncots = "";
                for (int c = 0; c < npara.length; c++) {
                    if (c == 0) {
                        nparas += npara[c];
                        ncots += ncot[c];
                    } else {
                        nparas += "/0/1/" + npara[c];
                        ncots += "/0/1/" + ncot[c];
                    }
                }
                db.updateCriterioAvaliacao(new CriterioAvaliacao(criterioAvaliacao.getIdca(), criterioAvaliacao.getCanome(), criterioAvaliacao.getCanpara() - 1, nparas, ncots));
                dialog.dismiss();
                TextView verDelPara = activity.findViewById(R.id.verDelPara);
                String DelPara = verDelPara.getText().toString() + " ";
                verDelPara.setText(DelPara);
            }
        });

        dialog.show();
    }
}
