package com.deadpixel.dpixel.a4student;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EveDetailsDialog {

    public void showDialog(Activity activity, String dia, int position) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.eve_details_dialog);

        final BaseDados db = new BaseDados(activity);
        Calendario calendario = db.selectCalendario(dia);

        TextView txtTitDetE = dialog.findViewById(R.id.txtTitDetE);
        TextView txtTipoDetE = dialog.findViewById(R.id.txtTipoDetE);
        LinearLayout llNot = dialog.findViewById(R.id.llNot);
        TextView txtNotDetE = dialog.findViewById(R.id.txtNotDetE);
        TextView txtHoraDetE = dialog.findViewById(R.id.txtHoraDetE);
        TextView txtDescDetE = dialog.findViewById(R.id.txtDescDetE);
        Button btnFecharDetE = dialog.findViewById(R.id.btnFecharDetE);

        String[] titulos = calendario.getTiteve().split("/0/1/");
        String[] horas = calendario.getHoraeve().split("/0/1/");
        String[] tipos = calendario.getTipoeve().split("/0/1/");
        String[] descs = calendario.getDesceve().split("/0/1/");
        String[] nots = calendario.getNoteve().split("/0/1/");

        llNot.setVisibility(View.GONE);
        txtTitDetE.setText(titulos[position]);
        txtHoraDetE.setText(horas[position]);
        txtDescDetE.setText(descs[position]);
        if (tipos[position].equals("1")) {
            //llNot.setVisibility(View.GONE);
            txtTipoDetE.setText(activity.getString(R.string.avalia_o));
        } else {
            //llNot.setVisibility(View.VISIBLE);
            txtTipoDetE.setText(activity.getString(R.string.outro));
            /*if (nots[position].equals("1")) {
                txtNotDetE.setText(activity.getString(R.string.ativado));
            } else {
                txtNotDetE.setText(activity.getString(R.string.desativado));
            }*/
        }

        btnFecharDetE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
