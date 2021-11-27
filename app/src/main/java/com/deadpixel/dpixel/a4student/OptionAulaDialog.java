package com.deadpixel.dpixel.a4student;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class OptionAulaDialog {

    public void showDialog(final Activity activity, final int dia, final int position) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.option_ca_dialog);

        final BaseDados db = new BaseDados(activity);

        Button btnEditarPara = dialog.findViewById(R.id.btnEditarPara);
        Button btnDelPara = dialog.findViewById(R.id.btnDelPara);

        btnEditarPara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditAulaDialog edit_dialog = new EditAulaDialog();
                edit_dialog.showDialog(activity, dia, position);
                dialog.dismiss();
            }
        });

        btnDelPara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Aulas aulas = db.selectAulas(dia);
                if (aulas.getNaulas() == 1) {
                    db.updateAulas(new Aulas(dia, 0, "", "", "", ""));
                } else {
                    String[] aulasid = aulas.getAulaid().split("/0/1/");
                    String[] horasIni = aulas.getIniaula().split("/0/1/");
                    String[] horasFim = aulas.getFimaula().split("/0/1/");
                    String[] salas = aulas.getSalaa().split("/0/1/");

                    String[] aulasid2 = new String[aulas.getNaulas() - 1];
                    String[] horasIni2 = new String[aulas.getNaulas() - 1];
                    String[] horasFim2 = new String[aulas.getNaulas() - 1];
                    String[] salas2 = new String[aulas.getNaulas() - 1];
                    boolean verp = false;
                    for (int c = 0; c < aulas.getNaulas()-1; c++) {
                        if (!verp) {
                            if (c == position) {
                                aulasid2[c] = aulasid[c+1];
                                horasIni2[c] = horasIni[c+1];
                                horasFim2[c] = horasFim[c+1];
                                salas2[c] = salas[c+1];
                                verp = true;
                            } else {
                                aulasid2[c] = aulasid[c];
                                horasIni2[c] = horasIni[c];
                                horasFim2[c] = horasFim[c];
                                salas2[c] = salas[c];
                            }
                        } else {
                            aulasid2[c] = aulasid[c+1];
                            horasIni2[c] = horasIni[c+1];
                            horasFim2[c] = horasFim[c+1];
                            salas2[c] = salas[c+1];
                        }
                    }
                    String aulasids="", horasInis="", horasFims="", salass="";
                    for (int c = 0; c < aulas.getNaulas() - 1; c++) {
                        if (c == 0) {
                            aulasids += aulasid2[c];
                            horasInis += horasIni2[c];
                            horasFims += horasFim2[c];
                            salass += salas2[c];
                        } else {
                            aulasids += "/0/1/" + aulasid2[c];
                            horasInis += "/0/1/" + horasIni2[c];
                            horasFims += "/0/1/" + horasFim2[c];
                            salass += "/0/1/" + salas2[c];
                        }
                    }
                    db.updateAulas(new Aulas(dia, aulas.getNaulas() - 1, aulasids, horasInis, horasFims, salass));
                }
                dialog.dismiss();
                TextView verAddAula = activity.findViewById(R.id.verAddAula);
                String txtVer = verAddAula.getText().toString() + " ";
                verAddAula.setText(txtVer);
            }
        });

        dialog.show();
    }
}
