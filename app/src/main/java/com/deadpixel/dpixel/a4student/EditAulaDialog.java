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
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class EditAulaDialog {

    boolean e1=false, e2=false, e3=false, eh= false;

    private int mHour, mMinute;

    public void showDialog(final Activity activity, final int dia, final int position) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_aula_dialog);

        final BaseDados db = new BaseDados(activity);

        final TextView txtEditAula = dialog.findViewById(R.id.txtEditAula);
        final TextView SalaAulaE = dialog.findViewById(R.id.SalaAulaE);
        final EditText edtSalaAulaE = dialog.findViewById(R.id.edtSalaAulaE);
        final TextView txtHoraIniE = dialog.findViewById(R.id.txtHoraIniE);
        final EditText edtHoraIniE = dialog.findViewById(R.id.edtHoraIniE);
        final TextView txtHoraFimE = dialog.findViewById(R.id.txtHoraFimE);
        final EditText edtHoraFimE = dialog.findViewById(R.id.edtHoraFimE);
        final TextView erroEditAula = dialog.findViewById(R.id.erroEditAula);
        Button btnEditAula = dialog.findViewById(R.id.btnEditAula);

        final User user = db.selectUser(1);
        final Aulas aulas = db.selectAulas(dia);

        String[] aulasid = aulas.getAulaid().split("/0/1/");
        final String[] horasIni = aulas.getIniaula().split("/0/1/");
        final String[] horasFim = aulas.getFimaula().split("/0/1/");
        final String[] salas = aulas.getSalaa().split("/0/1/");

        String titulo;
        if (user.getTipo().equals("prof")) {
            Turma turma = db.selectTurmaById(Integer.parseInt(aulasid[position]));
            titulo = activity.getString(R.string.editar_aula) + " - " + turma.getTcod();
            txtEditAula.setText(titulo);
        } else {
            Disciplina disciplina = db.selectDisciplinaById(Integer.parseInt(aulasid[position]));
            titulo = activity.getString(R.string.editar_aula) + " - " + disciplina.getDcod();
            txtEditAula.setText(titulo);
        }
        edtSalaAulaE.setText(salas[position]);
        edtHoraIniE.setText(horasIni[position]);
        edtHoraFimE.setText(horasFim[position]);

        edtHoraIniE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

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
                        edtHoraIniE.setText(txtHora);
                    }
                }, mHour, mMinute, true);

                timePickerDialog.show();
            }
        });
        edtHoraFimE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

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
                        edtHoraFimE.setText(txtHora);
                    }
                }, mHour, mMinute, true);

                timePickerDialog.show();
            }
        });

        btnEditAula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((edtSalaAulaE.getText().toString()).trim().equals("") || edtHoraIniE.getText().toString().equals("") || edtHoraFimE.getText().toString().equals("")) {
                    erroEditAula.setText(activity.getString(R.string.preencher_todos_os_campos));
                    if ((edtSalaAulaE.getText().toString()).trim().equals("")) {
                        SalaAulaE.setText(activity.getString(R.string.sala_erro));
                        SalaAulaE.setTextColor(0xffff4444);
                        e1 = true;
                    }
                    if (edtHoraIniE.getText().toString().equals("")) {
                        txtHoraIniE.setText(activity.getString(R.string.hora_inicio_erro));
                        txtHoraIniE.setTextColor(0xffff4444);
                        e2 = true;
                    }
                    if (edtHoraFimE.getText().toString().equals("")) {
                        txtHoraFimE.setText(activity.getString(R.string.hora_fim_erro));
                        txtHoraFimE.setTextColor(0xffff4444);
                        e3 = true;
                    }
                } else {
                        if (isHoraIniMenorFim(edtHoraIniE.getText().toString(), edtHoraFimE.getText().toString())) {
                            Aulas aulas = db.selectAulas(dia);
                            String iniaula="", fimaula="", sala="";
                            if (aulas.getNaulas() == 1) {
                                iniaula = edtHoraIniE.getText().toString();
                                fimaula = edtHoraFimE.getText().toString();
                                sala = edtSalaAulaE.getText().toString();
                            } else {
                                for (int c = 0; c < aulas.getNaulas(); c++) {
                                    if (c == 0) {
                                        if (c == position) {
                                            iniaula += edtHoraIniE.getText().toString();
                                            fimaula += edtHoraFimE.getText().toString();
                                            sala += edtSalaAulaE.getText().toString();
                                        } else {
                                            iniaula += horasIni[c];
                                            fimaula += horasFim[c];
                                            sala += salas[c];
                                        }
                                    } else {
                                        if (c == position) {
                                            iniaula += "/0/1/" + edtHoraIniE.getText().toString();
                                            fimaula += "/0/1/" + edtHoraFimE.getText().toString();
                                            sala += "/0/1/" + edtSalaAulaE.getText().toString();
                                        } else {
                                            iniaula += "/0/1/" + horasIni[c];
                                            fimaula += "/0/1/" + horasFim[c];
                                            sala += "/0/1/" + salas[c];
                                        }
                                    }
                                }
                            }
                            db.updateAulas(new Aulas(dia, aulas.getNaulas(), aulas.getAulaid(), iniaula, fimaula, sala));
                            insertionSort(activity, dia);
                            dialog.dismiss();
                            TextView verAddAula = activity.findViewById(R.id.verAddAula);
                            String txtVer = verAddAula.getText().toString() + " ";
                            verAddAula.setText(txtVer);
                        } else {
                            erroEditAula.setText(activity.getString(R.string.erro_horas));
                            txtHoraIniE.setText(activity.getString(R.string.hora_inicio_erro));
                            txtHoraIniE.setTextColor(0xffff4444);
                            txtHoraFimE.setText(activity.getString(R.string.hora_fim_erro));
                            txtHoraFimE.setTextColor(0xffff4444);
                            e2 = true;
                            e3 = true;
                        }
                }
            }
        });

        edtSalaAulaE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (e1) {
                    SalaAulaE.setText(activity.getString(R.string.sala));
                    SalaAulaE.setTextColor(0xff000000);
                    e1 = false;
                }
                if (!e1 && !e2 && !e3) {
                    erroEditAula.setText("");
                }
            }
        });
        edtHoraIniE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (e2) {
                    txtHoraIniE.setText(activity.getString(R.string.hora_de_in_cio));
                    txtHoraIniE.setTextColor(0xff000000);
                    e2 = false;
                }
                if (!e1 && !e2 && !e3) {
                    erroEditAula.setText("");
                }
            }
        });
        edtHoraFimE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (e3) {
                    txtHoraFimE.setText(activity.getString(R.string.hora_de_fim));
                    txtHoraFimE.setTextColor(0xff000000);
                    e3 = false;
                }
                if (!e1 && !e2 && !e3) {
                    erroEditAula.setText("");
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

    public boolean isHoraMaior(String hora1, String hora2) {
        String[] hora1s = hora1.split(":");
        String[] hora2s = hora2.split(":");
        Integer[] hora1i = new Integer[2];
        hora1i[0] = Integer.parseInt(hora1s[0]);
        hora1i[1] = Integer.parseInt(hora1s[1]);
        Integer[] hora2i = new Integer[2];
        hora2i[0] = Integer.parseInt(hora2s[0]);
        hora2i[1] = Integer.parseInt(hora2s[1]);
        if (hora1i[0] > hora2i[0]) {
            return true;
        } else if (hora1i[0] > hora2i[0]) {
            return false;
        } else {
            if (hora1i[1] > hora2i[1]) {
                return true;
            } else {
                return false;
            }
        }
    }

    public void insertionSort(Activity activity, int dia) {
        BaseDados db = new BaseDados(activity);
        Aulas aulas = db.selectAulas(dia);
        if (aulas.getNaulas() > 1) {
            String[] aulasid = aulas.getAulaid().split("/0/1/");
            String[] horasIni = aulas.getIniaula().split("/0/1/");
            String[] horasFim = aulas.getFimaula().split("/0/1/");
            String[] salas = aulas.getSalaa().split("/0/1/");
            int tam = aulas.getNaulas();
            int i, j;
            String temp1, temp2, temp3, temp4;
            for (i = 0; i < tam; i++) {
                for (j = tam-1; j > i; j--) {
                    if (isHoraIniMenorFim(horasIni[j], horasIni[j-1])) {
                        temp1 = aulasid[j];
                        temp2 = horasIni[j];
                        temp3 = horasFim[j];
                        temp4 = salas[j];
                        aulasid[j] = aulasid[j-1];
                        horasIni[j] = horasIni[j-1];
                        horasFim[j] = horasFim[j-1];
                        salas[j] = salas[j-1];
                        aulasid[j-1] = temp1;
                        horasIni[j-1] = temp2;
                        horasFim[j-1] = temp3;
                        salas[j-1] = temp4;
                    }
                }
            }
            String aulasids="", horasInis="", horasFims="", salass="";
            for (int c = 0; c < tam; c++) {
                if (c == 0) {
                    aulasids += aulasid[c];
                    horasInis += horasIni[c];
                    horasFims += horasFim[c];
                    salass += salas[c];
                } else {
                    aulasids += "/0/1/" + aulasid[c];
                    horasInis += "/0/1/" + horasIni[c];
                    horasFims += "/0/1/" + horasFim[c];
                    salass += "/0/1/" + salas[c];
                }
            }
            db.updateAulas(new Aulas(dia, tam, aulasids, horasInis, horasFims, salass));
        }
    }
}
