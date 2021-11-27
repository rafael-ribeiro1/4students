package com.deadpixel.dpixel.a4student;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class OptionEventoDialog {

    public void showDialog(final Activity activity, final String dia, final int position) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.option_ca_dialog);

        final BaseDados db = new BaseDados(activity);

        Button btnEditarPara = dialog.findViewById(R.id.btnEditarPara);
        Button btnDelPara = dialog.findViewById(R.id.btnDelPara);

        btnEditarPara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditEveDialog dialog1 = new EditEveDialog();
                dialog1.showDialog(activity, dia, position);
                dialog.dismiss();
            }
        });

        btnDelPara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendario calendario = db.selectCalendario(dia);
                if (calendario.getNeve() == 1) {
                    db.updateCalendario(new Calendario(calendario.getIdc(), dia, 0, "", "", "", "", calendario.getAnot(), calendario.getTitanot(), ""));
                } else {
                    String[] titulos = calendario.getTiteve().split("/0/1/");
                    String[] horas = calendario.getHoraeve().split("/0/1/");
                    String[] tipos = calendario.getTipoeve().split("/0/1/");
                    String[] descs = calendario.getDesceve().split("/0/1/");
                    String[] nots = calendario.getNoteve().split("/0/1/");

                    String[] titulos2 = new String[calendario.getNeve() - 1];
                    String[] horas2 = new String[calendario.getNeve() - 1];
                    String[] tipos2 = new String[calendario.getNeve() - 1];
                    String[] descs2 = new String[calendario.getNeve() - 1];
                    String[] nots2 = new String[calendario.getNeve() - 1];
                    boolean verp = false;
                    for (int c = 0; c < calendario.getNeve()-1; c++) {
                        if (!verp) {
                            if (c == position) {
                                titulos2[c] = titulos[c+1];
                                horas2[c] = horas[c+1];
                                tipos2[c] = tipos[c+1];
                                descs2[c] = descs[c+1];
                                nots2[c] = nots[c+1];
                                verp = true;
                            } else {
                                titulos2[c] = titulos[c];
                                horas2[c] = horas[c];
                                tipos2[c] = tipos[c];
                                descs2[c] = descs[c];
                                nots2[c] = nots[c];
                            }
                        } else {
                            titulos2[c] = titulos[c+1];
                            horas2[c] = horas[c+1];
                            tipos2[c] = tipos[c+1];
                            descs2[c] = descs[c+1];
                            nots2[c] = nots[c+1];
                        }
                    }
                    String tituloss="", horass="", tiposs="", descss="", notss = "";
                    for (int c = 0; c < calendario.getNeve() - 1; c++) {
                        if (c == 0) {
                            tituloss += titulos2[c];
                            horass += horas2[c];
                            tiposs += tipos2[c];
                            descss += descs2[c];
                            notss += nots2[c];
                        } else {
                            tituloss += "/0/1/" + titulos2[c];
                            horass += "/0/1/" + horas2[c];
                            tiposs += "/0/1/" + tipos2[c];
                            descss += "/0/1/" + descs2[c];
                            notss += "/0/1/" + nots2[c];
                        }
                    }
                    db.updateCalendario(new Calendario(calendario.getIdc(), dia, calendario.getNeve() - 1, tituloss, descss, tiposs, notss, calendario.getAnot(), calendario.getTitanot(), horass));
                }
                dialog.dismiss();
                TextView verAddEve = activity.findViewById(R.id.verAddEve);
                String txtVer = verAddEve.getText().toString() + " ";
                verAddEve.setText(txtVer);
            }
        });

        dialog.show();
    }
}
