package com.deadpixel.dpixel.a4student;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DTDetailsDialog {

    public void showDialog(Activity activity, String tipo, String cod) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dt_details_dialog);

        final BaseDados db = new BaseDados(activity);

        LinearLayout llturdet = dialog.findViewById(R.id.llturdet);
        LinearLayout lldiscdet = dialog.findViewById(R.id.lldiscdet);
        TextView txtTitDet = dialog.findViewById(R.id.txtTitDet);
        Button btnFecharDDet = dialog.findViewById(R.id.btnFecharDDet);

        if (tipo.equals("prof")) {
            llturdet.setVisibility(View.VISIBLE);
            lldiscdet.setVisibility(View.GONE);
            txtTitDet.setText(activity.getString(R.string.detalhes_da_turma));

            TextView txtNomeTDet = dialog.findViewById(R.id.txtNomeTDet);
            TextView txtCodTDet = dialog.findViewById(R.id.txtCodTDet);
            TextView txtCaTDet = dialog.findViewById(R.id.txtCATDet);
            TextView txtSCTDet = dialog.findViewById(R.id.txtSCTDet);
            TextView txtNumATDet = dialog.findViewById(R.id.txtNumATDet);

            Turma turma = db.selectTurma(cod);
            txtNomeTDet.setText(turma.getTnome());
            txtCodTDet.setText(turma.getTcod());
            CriterioAvaliacao ca1 = db.selectCriterioAvaliacaoId(turma.getTidca());
            txtCaTDet.setText(ca1.getCanome());
            switch (turma.getTscla()) {
                case 1:
                    txtSCTDet.setText(activity.getString(R.string.sc1));
                    break;
                case 2:
                    txtSCTDet.setText(activity.getString(R.string.sc2));
                    break;
                case 3:
                    txtSCTDet.setText(activity.getString(R.string.sc3));
                    break;
                case 4:
                    txtSCTDet.setText(activity.getString(R.string.sc4));
                    break;
                case 5:
                    txtSCTDet.setText(activity.getString(R.string.sc5));
                    break;
            }
            txtNumATDet.setText(Integer.toString(turma.getTnalu()));
        } else {
            llturdet.setVisibility(View.GONE);
            lldiscdet.setVisibility(View.VISIBLE);
            txtTitDet.setText(activity.getString(R.string.detalhes_da_disciplina));

            TextView txtNomeDDet = dialog.findViewById(R.id.txtNomeDDet);
            TextView txtCodDDet = dialog.findViewById(R.id.txtCodDDet);
            TextView txtCaDDet = dialog.findViewById(R.id.txtCATDDet);

            Disciplina disciplina = db.selectDisciplina(cod);
            txtNomeDDet.setText(disciplina.getDnome());
            txtCodDDet.setText(disciplina.getDcod());
            CriterioAvaliacao ca2 = db.selectCriterioAvaliacaoId(disciplina.getDidca());
            txtCaDDet.setText(ca2.getCanome());
        }

        btnFecharDDet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
