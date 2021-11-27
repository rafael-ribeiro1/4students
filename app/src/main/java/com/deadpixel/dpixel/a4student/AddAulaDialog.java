package com.deadpixel.dpixel.a4student;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddAulaDialog implements AdapterView.OnItemSelectedListener {

    boolean e1=false, e2=false, e3=false, eh=false, es=false;
    int dia;
    String cod;

    private int mHour, mMinute;

    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;

    public void showDialog(final Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_aula_dialog);

        final BaseDados db = new BaseDados(activity);

        final Spinner diaSemana = dialog.findViewById(R.id.diaSemana);
        final TextView DTAula = dialog.findViewById(R.id.DTAula);
        final TextView DTSelect = dialog.findViewById(R.id.DTSelect);
        final ListView listDTAula = dialog.findViewById(R.id.listDTAula);
        final TextView SalaAula = dialog.findViewById(R.id.SalaAula);
        final EditText edtSalaAula = dialog.findViewById(R.id.edtSalaAula);
        final TextView txtHoraIni = dialog.findViewById(R.id.txtHoraIni);
        final EditText edtHoraIni = dialog.findViewById(R.id.edtHoraIni);
        final TextView txtHoraFim = dialog.findViewById(R.id.txtHoraFim);
        final EditText edtHoraFim = dialog.findViewById(R.id.edtHoraFim);
        final TextView erroAddAula = dialog.findViewById(R.id.erroAddAula);
        Button btnAddAula = dialog.findViewById(R.id.btnAddAula);

        final User user = db.selectUser(1);

        ArrayAdapter<CharSequence> sadapter = ArrayAdapter.createFromResource(activity, R.array.sdia, android.R.layout.simple_spinner_item);
        sadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diaSemana.setAdapter(sadapter);
        diaSemana.setOnItemSelectedListener(this);

        if (user.getTipo().equals("prof")) {
            DTAula.setText(activity.getString(R.string.turma_selecionada));
            List<Turma> turmaList = db.turmaList();
            arrayList = new ArrayList<>();
            adapter = new ArrayAdapter<String>(dialog.getContext(), android.R.layout.simple_list_item_1, arrayList);
            listDTAula.setAdapter(adapter);
            for (Turma turma : turmaList) {
                arrayList.add(turma.getTcod());
                adapter.notifyDataSetChanged();
            }
        } else {
            DTAula.setText(activity.getString(R.string.disciplina_selecionada));
            List<Disciplina> disciplinaList = db.disciplinaList();
            arrayList = new ArrayList<>();
            adapter = new ArrayAdapter<>(dialog.getContext(), android.R.layout.simple_list_item_1, arrayList);
            listDTAula.setAdapter(adapter);
            for (Disciplina disciplina : disciplinaList) {
                arrayList.add(disciplina.getDcod());
                adapter.notifyDataSetChanged();
            }
        }

        listDTAula.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cod = parent.getItemAtPosition(position).toString();
                DTSelect.setText(cod);
            }
        });

        edtHoraIni.setOnClickListener(new View.OnClickListener() {
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
                        edtHoraIni.setText(txtHora);
                    }
                }, mHour, mMinute, true);

                timePickerDialog.show();
            }
        });
        edtHoraFim.setOnClickListener(new View.OnClickListener() {
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
                        edtHoraFim.setText(txtHora);
                    }
                }, mHour, mMinute, true);

                timePickerDialog.show();
            }
        });

        btnAddAula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((edtSalaAula.getText().toString()).trim().equals("") || edtHoraIni.getText().toString().equals("") || edtHoraFim.getText().toString().equals("")) {
                    erroAddAula.setText(activity.getString(R.string.preencher_todos_os_campos));
                    if ((edtSalaAula.getText().toString()).trim().equals("")) {
                        SalaAula.setText(activity.getString(R.string.sala_erro));
                        SalaAula.setTextColor(0xffff4444);
                        e1 = true;
                    }
                    if (edtHoraIni.getText().toString().equals("")) {
                        txtHoraIni.setText(activity.getString(R.string.hora_inicio_erro));
                        txtHoraIni.setTextColor(0xffff4444);
                        e2 = true;
                    }
                    if (edtHoraFim.getText().toString().equals("")) {
                        txtHoraFim.setText(activity.getString(R.string.hora_fim_erro));
                        txtHoraFim.setTextColor(0xffff4444);
                        e3 = true;
                    }
                } else {
                    if (DTSelect.getText().toString().equals("Selecionar")) {
                        erroAddAula.setText(activity.getString(R.string.selecionar_uma_disciplina));
                        es = true;
                    } else {
                        if (isHoraIniMenorFim(edtHoraIni.getText().toString(), edtHoraFim.getText().toString())) {
                            Aulas aulas = db.selectAulas(dia + 1);
                            int id;
                            String aulaid;
                            if (user.getTipo().equals("prof")) {
                                Turma turma = db.selectTurma(cod);
                                id = turma.getIdt();
                            } else {
                                Disciplina disciplina = db.selectDisciplina(cod);
                                id = disciplina.getIdd();
                            }
                            String iniaula, fimaula, sala;
                            if (aulas.getNaulas() == 0) {
                                aulaid = Integer.toString(id);
                                iniaula = edtHoraIni.getText().toString();
                                fimaula = edtHoraFim.getText().toString();
                                sala = edtSalaAula.getText().toString();
                            } else {
                                aulaid = "/0/1/" + Integer.toString(id);
                                iniaula = "/0/1/" + edtHoraIni.getText().toString();
                                fimaula = "/0/1/" + edtHoraFim.getText().toString();
                                sala = "/0/1/" + edtSalaAula.getText().toString();
                            }
                            db.updateAulas(new Aulas(dia + 1, aulas.getNaulas() + 1, aulas.getAulaid() + aulaid, aulas.getIniaula() + iniaula, aulas.getFimaula() + fimaula, aulas.getSalaa() + sala));
                            insertionSort(activity, dia + 1);
                            dialog.dismiss();
                            TextView verAddAula = activity.findViewById(R.id.verAddAula);
                            String txtVer = verAddAula.getText().toString() + " ";
                            verAddAula.setText(txtVer);
                        } else {
                            erroAddAula.setText(activity.getString(R.string.erro_horas));
                            txtHoraIni.setText(activity.getString(R.string.hora_inicio_erro));
                            txtHoraIni.setTextColor(0xffff4444);
                            txtHoraFim.setText(activity.getString(R.string.hora_fim_erro));
                            txtHoraFim.setTextColor(0xffff4444);
                            e2 = true;
                            e3 = true;
                        }
                    }
                }
            }
        });

        edtSalaAula.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (e1) {
                    SalaAula.setText(activity.getString(R.string.sala));
                    SalaAula.setTextColor(0xff000000);
                    e1 = false;
                }
                if (!e1 && !e2 && !e3) {
                    erroAddAula.setText("");
                }
            }
        });
        edtHoraIni.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (e2) {
                    txtHoraIni.setText(activity.getString(R.string.hora_de_in_cio));
                    txtHoraIni.setTextColor(0xff000000);
                    e2 = false;
                }
                if (!e1 && !e2 && !e3) {
                    erroAddAula.setText("");
                }
            }
        });
        edtHoraFim.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (e3) {
                    txtHoraFim.setText(activity.getString(R.string.hora_de_fim));
                    txtHoraFim.setTextColor(0xff000000);
                    e3 = false;
                }
                if (!e1 && !e2 && !e3) {
                    erroAddAula.setText("");
                }
            }
        });
        DTSelect.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (es) {
                    erroAddAula.setText("");
                    es = false;
                }
            }
        });

        dialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        dia = position;
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

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
            /*String key1, key2, key3, key4;
            for (j = 1; j < tam; j++) {
                key1 = aulasid[j];
                key2 = horasIni[j];
                key3 = horasFim[j];
                key4 = salas[j];
                i = j - 1;
                while ((i > 0) && (!isHoraIniMenorFim(horasIni[i], key2))) {
                    aulasid[i+1] = aulasid[i];
                    horasIni[i+1] = horasIni[i];
                    horasFim[i+1] = horasFim[i];
                    salas[i+1] = salas[i];
                    i -= 1;
                    aulasid[i+1] = key1;
                    horasIni[i+1] = key2;
                    horasFim[i+1] = key3;
                    salas[i+1] = key4;
                }
            }*/
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
