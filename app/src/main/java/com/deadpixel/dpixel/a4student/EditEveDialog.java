package com.deadpixel.dpixel.a4student;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class EditEveDialog {

    boolean e1=false, e2=false;
    int rb = 1;

    public void showDialog(final Activity activity, final String dia, final int position) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_eve_dialog);

        final BaseDados db = new BaseDados(activity);

        final TextView txtTitEve = dialog.findViewById(R.id.txtTitEve);
        final EditText edtTitEve = dialog.findViewById(R.id.edtTitEve);
        final Switch sNot = dialog.findViewById(R.id.sNot);
        final RadioButton rAva = dialog.findViewById(R.id.rAva);
        final RadioButton rOut = dialog.findViewById(R.id.rOut);
        final TextView txtHoraEve = dialog.findViewById(R.id.txtHoraEve);
        final EditText edtHoraEve = dialog.findViewById(R.id.edtHoraEve);
        final EditText edtDescEve = dialog.findViewById(R.id.edtDescEve);
        final TextView erroEditEve = dialog.findViewById(R.id.erroEditEve);
        Button btnEditEve = dialog.findViewById(R.id.btnEditEve);

        final Calendario calendario = db.selectCalendario(dia);

        final String[] titulos = calendario.getTiteve().split("/0/1/");
        final String[] horas = calendario.getHoraeve().split("/0/1/");
        final String[] tipos = calendario.getTipoeve().split("/0/1/");
        final String[] descs = calendario.getDesceve().split("/0/1/");
        final String[] nots = calendario.getNoteve().split("/0/1/");

        sNot.setVisibility(View.GONE);
        edtTitEve.setText(titulos[position]);
        edtHoraEve.setText(horas[position]);
        edtDescEve.setText(descs[position]);
        if (tipos[position].equals("1")) {
            //sNot.setVisibility(View.GONE);
            rAva.setChecked(true);
            rOut.setChecked(false);
            rb = 1;
        } else {
            //sNot.setVisibility(View.VISIBLE);
            rAva.setChecked(false);
            rOut.setChecked(true);
            rb = 2;
            /*if (nots[position].equals("1")) {
                sNot.setChecked(true);
            } else {
                sNot.setChecked(false);
            }*/
        }

        rAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked();

                switch (v.getId()) {
                    case (R.id.rAva):
                        if (checked) {
                            rb = 1;
                            //sNot.setVisibility(View.GONE);
                        }
                        break;
                    case (R.id.rOut):
                        if (checked) {
                            rb = 2;
                            //sNot.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            }
        });
        rOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked();

                switch (v.getId()) {
                    case (R.id.rAva):
                        if (checked) {
                            rb = 1;
                            //sNot.setVisibility(View.GONE);
                        }
                        break;
                    case (R.id.rOut):
                        if (checked) {
                            rb = 2;
                            //sNot.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            }
        });

        edtHoraEve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hora = Integer.toString(hourOfDay);
                        if (hora.length() == 1) {
                            hora = "0" + hora;
                        }
                        String minuto = Integer.toString(minute);
                        if (minuto.length() == 1) {
                            minuto = "0" + minuto;
                        }
                        String txtHora = hora + ":" + minuto;
                        edtHoraEve.setText(txtHora);
                    }
                }, mHour, mMinute, true);

                timePickerDialog.show();
            }
        });

        btnEditEve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((edtTitEve.getText().toString()).trim().equals("") || edtHoraEve.getText().toString().equals("")) {
                    erroEditEve.setText(activity.getString(R.string.preencher_todos_os_campos));
                    if ((edtTitEve.getText().toString()).trim().equals("")) {
                        txtTitEve.setText(activity.getString(R.string.erro_titulo));
                        txtTitEve.setTextColor(0xffff4444);
                        e1 = true;
                    }
                    if (edtHoraEve.getText().toString().equals("")) {
                        txtHoraEve.setText(activity.getString(R.string.erro_hora));
                        txtHoraEve.setTextColor(0xffff4444);
                        e2 = true;
                    }
                } else {
                    titulos[position] = edtTitEve.getText().toString();
                    horas[position] = edtHoraEve.getText().toString();
                    if (rb == 1) {
                        tipos[position] = "1";
                        nots[position] = "0";
                    } else {
                        tipos[position] = "2";
                        nots[position] = "0";
                        /*if (sNot.isChecked()) {
                            nots[position] = "1";
                        } else {
                            nots[position] = "2";
                        }*/
                    }
                    if ((edtDescEve.getText().toString()).trim().equals("")) {
                        descs[position] = " ";
                    } else {
                        descs[position] = edtDescEve.getText().toString();
                    }
                    String tituloss="", horass="", tiposs="", descss="", notss = "";
                    for (int c = 0; c < calendario.getNeve(); c++) {
                        if (c == 0) {
                            tituloss += titulos[c];
                            horass += horas[c];
                            tiposs += tipos[c];
                            descss += descs[c];
                            notss += nots[c];
                        } else {
                            tituloss += "/0/1/" + titulos[c];
                            horass += "/0/1/" + horas[c];
                            tiposs += "/0/1/" + tipos[c];
                            descss += "/0/1/" + descs[c];
                            notss += "/0/1/" + nots[c];
                        }
                    }
                    db.updateCalendario(new Calendario(calendario.getIdc(), dia, calendario.getNeve(), tituloss, descss, tiposs, notss, calendario.getAnot(), calendario.getTitanot(), horass));
                    insertionSort(activity, dia);
                    dialog.dismiss();
                    TextView verAddEve = activity.findViewById(R.id.verAddEve);
                    String txtVer = verAddEve.getText().toString() + " ";
                    verAddEve.setText(txtVer);
                }
            }
        });

        edtTitEve.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (e1) {
                    txtTitEve.setText(activity.getString(R.string.titulo));
                    txtTitEve.setTextColor(0xff000000);
                    e1 = false;
                }
                if (!e1 && !e2) {
                    erroEditEve.setText("");
                }
            }
        });
        edtHoraEve.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (e2) {
                    txtHoraEve.setText(activity.getString(R.string.hora));
                    txtHoraEve.setTextColor(0xff000000);
                    e2 = false;
                }
                if (!e1 && !e2) {
                    erroEditEve.setText("");
                }
            }
        });

        dialog.show();
    }

    public boolean isHoraIniMenorFim(String horaIni, String horaFim) {
        String[] horaInis = horaIni.split(":");
        String[] horaFims = horaFim.split(":");
        Integer[] horaInii = new Integer[2];
        horaInii[0] = Integer.parseInt(horaInis[0]);
        horaInii[1] = Integer.parseInt(horaInis[1]);
        Integer[] horaFimi = new Integer[2];
        horaFimi[0] = Integer.parseInt(horaFims[0]);
        horaFimi[1] = Integer.parseInt(horaFims[1]);
        if (horaInii[0] < horaFimi[0]) {
            return true;
        } else if (horaInii[0] > horaFimi[0]) {
            return false;
        } else {
            if (horaInii[1] < horaFimi[1]) {
                return true;
            } else {
                return false;
            }
        }
    }

    public void insertionSort(Activity activity, String dia) {
        BaseDados db = new BaseDados(activity);
        Calendario calendario = db.selectCalendario(dia);
        if (calendario.getNeve() > 1) {
            String[] titulos = calendario.getTiteve().split("/0/1/");
            String[] horas = calendario.getHoraeve().split("/0/1/");
            String[] tipos = calendario.getTipoeve().split("/0/1/");
            String[] descs = calendario.getDesceve().split("/0/1/");
            String[] nots = calendario.getNoteve().split("/0/1/");
            int tam = calendario.getNeve();
            int i, j;
            String temp1, temp2, temp3, temp4, temp5;
            for (i = 0; i < tam; i++) {
                for (j = tam-1; j > i; j--) {
                    if (isHoraIniMenorFim(horas[j], horas[j-1])) {
                        temp1 = titulos[j];
                        temp2 = horas[j];
                        temp3 = tipos[j];
                        temp4 = descs[j];
                        temp5 = nots[j];
                        titulos[j] = titulos[j-1];
                        horas[j] = horas[j-1];
                        tipos[j] = tipos[j-1];
                        descs[j] = descs[j-1];
                        nots[j] = nots[j-1];
                        titulos[j-1] = temp1;
                        horas[j-1] = temp2;
                        tipos[j-1] = temp3;
                        descs[j-1] = temp4;
                        nots[j-1] = temp5;
                    }
                }
            }
            String tituloss="", horass="", tiposs="", descss="", notss = "";
            for (int c = 0; c < tam; c++) {
                if (c == 0) {
                    tituloss += titulos[c];
                    horass += horas[c];
                    tiposs += tipos[c];
                    descss += descs[c];
                    notss += nots[c];
                } else {
                    tituloss += "/0/1/" + titulos[c];
                    horass += "/0/1/" + horas[c];
                    tiposs += "/0/1/" + tipos[c];
                    descss += "/0/1/" + descs[c];
                    notss += "/0/1/" + nots[c];
                }
            }
            db.updateCalendario(new Calendario(calendario.getIdc(), dia, tam, tituloss, descss, tiposs, notss, calendario.getAnot(), calendario.getTitanot(), horass));
        }
    }
}
