package com.deadpixel.dpixel.a4student;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.EventLog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class PaginaInicial extends AppCompatActivity {

    BaseDados db = new BaseDados(this);

    Button btnAgenda, btnHorario, btnCEstudo, btnAvaliacao, btnDefinicoes;
    ConstraintLayout clAulasHoje, clAulasAmanha, clAva7Dias;
    ListView listHoje, listAmanha, listAva7Dias;

    ArrayList<Aula> arrayHoje;
    private static AListAdapter adaHoje;
    ArrayList<Aula> arrayAmanha;
    private static AListAdapter adaAmanha;

    ArrayList<Evento> arrayEvento;
    private static EListAdapter adaEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicial);

        btnAgenda = findViewById(R.id.btnAgenda);
        btnHorario = findViewById(R.id.btnHorario);
        btnCEstudo = findViewById(R.id.btnCEstudo);
        btnAvaliacao = findViewById(R.id.btnAvaliacao);
        btnDefinicoes = findViewById(R.id.btnDefinicoes);
        clAulasHoje = findViewById(R.id.clAulasHoje);
        listHoje = findViewById(R.id.listHoje);
        clAulasAmanha = findViewById(R.id.clAulasAmanha);
        listAmanha = findViewById(R.id.listAmanha);
        clAva7Dias = findViewById(R.id.clAva7Dias);
        listAva7Dias = findViewById(R.id.listAva7Dias);

        int diah, diaa;
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        Aulas aulash, aulasa;
        switch (day) {
            case Calendar.SUNDAY:
                    diah = 7;
                    diaa = 1;
                    clAulasHoje.setVisibility(View.VISIBLE);
                    aulasa = db.selectAulas(diaa);
                    if (aulasa.getNaulas() == 0) {
                        clAulasAmanha.setVisibility(View.VISIBLE);
                    } else {
                        clAulasAmanha.setVisibility(View.GONE);
                        setListAmanha(diaa);
                    }
                break;
            case Calendar.MONDAY:
                diah = 1;
                diaa = 2;
                aulash = db.selectAulas(diah);
                if (aulash.getNaulas() == 0) {
                    clAulasHoje.setVisibility(View.VISIBLE);
                } else {
                    clAulasHoje.setVisibility(View.GONE);
                    setListHoje(diah);
                }
                aulasa = db.selectAulas(diaa);
                if (aulasa.getNaulas() == 0) {
                    clAulasAmanha.setVisibility(View.VISIBLE);
                } else {
                    clAulasAmanha.setVisibility(View.GONE);
                    setListAmanha(diaa);
                }
                break;
            case Calendar.TUESDAY:
                diah = 2;
                diaa = 3;
                aulash = db.selectAulas(diah);
                if (aulash.getNaulas() == 0) {
                    clAulasHoje.setVisibility(View.VISIBLE);
                } else {
                    clAulasHoje.setVisibility(View.GONE);
                    setListHoje(diah);
                }
                aulasa = db.selectAulas(diaa);
                if (aulasa.getNaulas() == 0) {
                    clAulasAmanha.setVisibility(View.VISIBLE);
                } else {
                    clAulasAmanha.setVisibility(View.GONE);
                    setListAmanha(diaa);
                }
                break;
            case Calendar.WEDNESDAY:
                diah = 3;
                diaa = 4;
                aulash = db.selectAulas(diah);
                if (aulash.getNaulas() == 0) {
                    clAulasHoje.setVisibility(View.VISIBLE);
                } else {
                    clAulasHoje.setVisibility(View.GONE);
                    setListHoje(diah);
                }
                aulasa = db.selectAulas(diaa);
                if (aulasa.getNaulas() == 0) {
                    clAulasAmanha.setVisibility(View.VISIBLE);
                } else {
                    clAulasAmanha.setVisibility(View.GONE);
                    setListAmanha(diaa);
                }
                break;
            case Calendar.THURSDAY:
                diah = 4;
                diaa = 5;
                aulash = db.selectAulas(diah);
                if (aulash.getNaulas() == 0) {
                    clAulasHoje.setVisibility(View.VISIBLE);
                } else {
                    clAulasHoje.setVisibility(View.GONE);
                    setListHoje(diah);
                }
                aulasa = db.selectAulas(diaa);
                if (aulasa.getNaulas() == 0) {
                    clAulasAmanha.setVisibility(View.VISIBLE);
                } else {
                    clAulasAmanha.setVisibility(View.GONE);
                    setListAmanha(diaa);
                }
                break;
            case Calendar.FRIDAY:
                diah = 5;
                diaa = 6;
                aulash = db.selectAulas(diah);
                if (aulash.getNaulas() == 0) {
                    clAulasHoje.setVisibility(View.VISIBLE);
                } else {
                    clAulasHoje.setVisibility(View.GONE);
                    setListHoje(diah);
                }
                aulasa = db.selectAulas(diaa);
                if (aulasa.getNaulas() == 0) {
                    clAulasAmanha.setVisibility(View.VISIBLE);
                } else {
                    clAulasAmanha.setVisibility(View.GONE);
                    setListAmanha(diaa);
                }
                break;
            case Calendar.SATURDAY:
                diah = 6;
                diaa = 7;
                aulash = db.selectAulas(diah);
                if (aulash.getNaulas() == 0) {
                    clAulasHoje.setVisibility(View.VISIBLE);
                } else {
                    clAulasHoje.setVisibility(View.GONE);
                    setListHoje(diah);
                }
                clAulasAmanha.setVisibility(View.VISIBLE);
                break;
        }

        listHoje.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent ihorario = new Intent(PaginaInicial.this, Horario.class);
                startActivity(ihorario);
            }
        });
        listAmanha.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent ihorario = new Intent(PaginaInicial.this, Horario.class);
                startActivity(ihorario);
            }
        });

        String dia;
        if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
            dia = "0" + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        } else {
            dia = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        }
        String mes;
        if (calendar.get(Calendar.MONTH) < 10) {
            mes = "0" + Integer.toString(calendar.get(Calendar.MONTH) + 1);
        } else {
            mes = Integer.toString(calendar.get(Calendar.MONTH) + 1);
        }
        String diaAtuals = dia + mes + Integer.toString(calendar.get(Calendar.YEAR));
        arrayEvento = new ArrayList<>();
        if (!db.nExistDia(diaAtuals)) {
            Calendario diaAtual = db.selectCalendario(diaAtuals);
            if (diaAtual.getNeve() != 0) {
                arrayEvento = db.listEventos2(diaAtuals, arrayEvento);
            }
        }
        String dia2s = diaSeguinte(diaAtuals);
        if (!db.nExistDia(dia2s)) {
            Calendario dia2 = db.selectCalendario(dia2s);
            if (dia2.getNeve() != 0) {
                arrayEvento = db.listEventos2(dia2s, arrayEvento);
            }
        }
        String dia3s = diaSeguinte(dia2s);
        if (!db.nExistDia(dia3s)) {
            Calendario dia3 = db.selectCalendario(dia3s);
            if (dia3.getNeve() != 0) {
                arrayEvento = db.listEventos2(dia3s, arrayEvento);
            }
        }
        String dia4s = diaSeguinte(dia3s);
        if (!db.nExistDia(dia4s)) {
            Calendario dia4 = db.selectCalendario(dia4s);
            if (dia4.getNeve() != 0) {
                arrayEvento = db.listEventos2(dia4s, arrayEvento);
            }
        }
        String dia5s = diaSeguinte(dia4s);
        if (!db.nExistDia(dia5s)) {
            Calendario dia5 = db.selectCalendario(dia5s);
            if (dia5.getNeve() != 0) {
                arrayEvento = db.listEventos2(dia5s, arrayEvento);
            }
        }
        String dia6s = diaSeguinte(dia5s);
        if (!db.nExistDia(dia6s)) {
            Calendario dia6 = db.selectCalendario(dia6s);
            if (dia6.getNeve() != 0) {
                arrayEvento = db.listEventos2(dia6s, arrayEvento);
            }
        }
        String dia7s = diaSeguinte(dia6s);
        if (!db.nExistDia(dia7s)) {
            Calendario dia7 = db.selectCalendario(dia7s);
            if (dia7.getNeve() != 0) {
                arrayEvento = db.listEventos2(dia7s, arrayEvento);
            }
        }
        if (arrayEvento.size() > 0) {
            clAva7Dias.setVisibility(View.GONE);
            adaEvento = new EListAdapter(arrayEvento, PaginaInicial.this, 0);
            listAva7Dias.setAdapter(adaEvento);
        } else {
            clAva7Dias.setVisibility(View.VISIBLE);
        }


        listAva7Dias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent iagenda = new Intent(PaginaInicial.this, Agenda.class);
                startActivity(iagenda);
            }
        });

        // Programação dos botões do menu
        btnAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iagenda = new Intent(PaginaInicial.this, Agenda.class);
                startActivity(iagenda);
            }
        });
        btnHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ihorario = new Intent(PaginaInicial.this, Horario.class);
                startActivity(ihorario);
            }
        });
        btnCEstudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent icestudo = new Intent(PaginaInicial.this, CEstudo.class);
                startActivity(icestudo);
            }
        });
        btnAvaliacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iavaliacao = new Intent(PaginaInicial.this, Avaliacao.class);
                startActivity(iavaliacao);
            }
        });
        btnDefinicoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent idefinicoes = new Intent(PaginaInicial.this, Definicoes.class);
                startActivity(idefinicoes);
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PaginaInicial.this);
        builder.setTitle(getString(R.string.deseja_sair));
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void setListHoje(int dia) {
        User user = db.selectUser(1);
        String tipo = user.getTipo();
        arrayHoje = db.listAulas(dia, tipo);
        adaHoje = new AListAdapter(arrayHoje, PaginaInicial.this);
        listHoje.setAdapter(adaHoje);
    }

    public void setListAmanha(int dia) {
        User user = db.selectUser(1);
        String tipo = user.getTipo();
        arrayAmanha = db.listAulas(dia, tipo);
        adaAmanha = new AListAdapter(arrayAmanha, PaginaInicial.this);
        listAmanha.setAdapter(adaAmanha);
    }

    public String diaSeguinte(String dia) {
        String diaSeguinte;
        int[] anbi = new int[40];
        anbi[0] = 2020;
        for (int c = 1; c < anbi.length; c++) {
            anbi[c] = anbi[c-1] + 4;
        }
        int dayOfMonth;
        if (dia.substring(0, 0).equals("0")) {
            dayOfMonth = Integer.parseInt(dia.substring(1, 2));
        } else {
            dayOfMonth = Integer.parseInt(dia.substring(0, 2));
        }
        int month;
        if (dia.substring(2, 2).equals("0")) {
            month = Integer.parseInt(dia.substring(3, 4));
        } else {
            month = Integer.parseInt(dia.substring(2, 4));
        }
        int year = Integer.parseInt(dia.substring(4));
        int dayOfMonths, months, years;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10) {
            if (dayOfMonth == 31) {
                dayOfMonths = 1;
                months = month + 1;
                years = year;
            } else {
                dayOfMonths = dayOfMonth + 1;
                months = month;
                years = year;
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (dayOfMonth == 30) {
                dayOfMonths = 1;
                months = month + 1;
                years = year;
            } else {
                dayOfMonths = dayOfMonth + 1;
                months = month;
                years = year;
            }
        } else if (month == 2) {
            if (Arrays.asList(anbi).contains(year)) {
                if (dayOfMonth == 29) {
                    dayOfMonths = 1;
                    months = month + 1;
                    years = year;
                } else {
                    dayOfMonths = dayOfMonth + 1;
                    months = month;
                    years = year;
                }
            } else {
                if (dayOfMonth == 28) {
                    dayOfMonths = 1;
                    months = month + 1;
                    years = year;
                } else {
                    dayOfMonths = dayOfMonth + 1;
                    months = month;
                    years = year;
                }
            }
        } else {
            if (dayOfMonth == 31) {
                dayOfMonths = 1;
                months = 1;
                years = year + 1;
            } else {
                dayOfMonths = dayOfMonth + 1;
                months = month;
                years = year;
            }
        }
        String diaM;
        if (dayOfMonths < 10) {
            diaM = "0" + Integer.toString(dayOfMonths);
        } else {
            diaM = Integer.toString(dayOfMonths);
        }
        String mes;
        if (months < 10) {
            mes = "0" + Integer.toString(months);
        } else {
            mes = Integer.toString(months);
        }
        diaSeguinte = diaM + mes + Integer.toString(years);
        return diaSeguinte;
    }
}
