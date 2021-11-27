package com.deadpixel.dpixel.a4student;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;

public class Agenda extends AppCompatActivity {

    BaseDados db = new BaseDados(this);

    CalendarView calendar;
    Button btnDiaAtual, btnAddEve, btnSalvarA, btnLimparA;
    LinearLayout lleventos, llaulas, llanotacoes;
    ConstraintLayout cleventos, claulas;
    ListView listEventos, listAulas;
    TextView verAddEve;
    EditText edtAnot;

    ArrayList<Aula> arrayAulas;
    private static AAListAdapter adaAulas;

    ArrayList<Evento> arrayEventos;
    private static EListAdapter adaEventos;

    String diaAnteriors, selecteds;
    int diaSemana, diaS;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navi_eventos:
                    lleventos.setVisibility(View.VISIBLE);
                    llaulas.setVisibility(View.GONE);
                    llanotacoes.setVisibility(View.GONE);
                    return true;
                case R.id.navi_aulas:
                    lleventos.setVisibility(View.GONE);
                    llaulas.setVisibility(View.VISIBLE);
                    llanotacoes.setVisibility(View.GONE);
                    return true;
                case R.id.navi_anotacoes:
                    lleventos.setVisibility(View.GONE);
                    llaulas.setVisibility(View.GONE);
                    llanotacoes.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        BottomNavigationView navi = (BottomNavigationView) findViewById(R.id.navi);
        navi.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        calendar = findViewById(R.id.calendar);
        long time = System.currentTimeMillis();
        calendar.setDate(time, false, true);


        lleventos = findViewById(R.id.lleventos);
        lleventos.setVisibility(View.VISIBLE);
        llaulas = findViewById(R.id.llaulas);
        llaulas.setVisibility(View.GONE);
        llanotacoes = findViewById(R.id.llanotacoes);
        llanotacoes.setVisibility(View.GONE);

        cleventos = findViewById(R.id.cleventos);
        claulas = findViewById(R.id.claulas);
        listEventos = findViewById(R.id.listEventos);
        listAulas = findViewById(R.id.listAulas);
        verAddEve = findViewById(R.id.verAddEve);
        edtAnot = findViewById(R.id.edtAnot);
        btnSalvarA = findViewById(R.id.btnSalvarA);
        btnLimparA = findViewById(R.id.btnLimparA);

        Calendar diaAtual = Calendar.getInstance();
        int dayOfWeek = diaAtual.get(Calendar.DAY_OF_WEEK);
        diaSemana = dayOfWeek;
        listarAulas(dayOfWeek);
        final String dia;
        if (diaAtual.get(Calendar.DAY_OF_MONTH) < 10) {
            dia = "0" + Integer.toString(diaAtual.get(Calendar.DAY_OF_MONTH));
        } else {
            dia = Integer.toString(diaAtual.get(Calendar.DAY_OF_MONTH));
        }
        String mes;
        if (diaAtual.get(Calendar.MONTH) < 10) {
            mes = "0" + Integer.toString(diaAtual.get(Calendar.MONTH) + 1);
        } else {
            mes = Integer.toString(diaAtual.get(Calendar.MONTH) + 1);
        }
        selecteds = dia + mes + Integer.toString(diaAtual.get(Calendar.YEAR));
        if (db.nExistDia(selecteds)) {
            db.insertCalendario(new Calendario(selecteds, 0, "", "", "", "", 0, "", ""));
        }

        diaAnteriors = selecteds;

        btnDiaAtual = findViewById(R.id.btnDiaAtual);
        btnDiaAtual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long atual = System.currentTimeMillis();
                calendar.setDate(atual, false, true);

                Calendar diaAtual = Calendar.getInstance();
                int dayOfWeek = diaAtual.get(Calendar.DAY_OF_WEEK);
                diaSemana = dayOfWeek;
                listarAulas(dayOfWeek);
                String dia;
                if (diaAtual.get(Calendar.DAY_OF_MONTH) < 10) {
                    dia = "0" + Integer.toString(diaAtual.get(Calendar.DAY_OF_MONTH));
                } else {
                    dia = Integer.toString(diaAtual.get(Calendar.DAY_OF_MONTH));
                }
                String mes;
                if (diaAtual.get(Calendar.MONTH) < 10) {
                    mes = "0" + Integer.toString(diaAtual.get(Calendar.MONTH) + 1);
                } else {
                    mes = Integer.toString(diaAtual.get(Calendar.MONTH) + 1);
                }
                selecteds = dia + mes + Integer.toString(diaAtual.get(Calendar.YEAR));
                if (db.nExistDia(selecteds)) {
                    db.insertCalendario(new Calendario(selecteds, 0, "", "", "", "", 0, "", ""));
                }
                if (!selecteds.equals(diaAnteriors)) {
                    Calendario dA = db.selectCalendario(diaAnteriors);
                    if (dA.getNeve() == 0 && dA.getAnot() == 0) {
                        db.deleteCalendario(dA);
                    }
                }
                Calendario calendario = db.selectCalendario(selecteds);
                if (calendario.getNeve() != 0) {
                    cleventos.setVisibility(View.GONE);
                    setListEventos(selecteds);
                } else {
                    cleventos.setVisibility(View.VISIBLE);
                }
                if (calendario.getAnot() == 1) {
                    String titanot = calendario.getDia() + ".txt";
                    try {
                        FileInputStream fileIn = openFileInput(titanot);
                        InputStreamReader InputRead = new InputStreamReader(fileIn, "UTF-8");
                        BufferedReader reader = new BufferedReader(InputRead);
                        int c;
                        String temp = "";
                        while ( (c = reader.read()) != -1) {
                            temp += Character.toString((char)c);
                        }
                        fileIn.close();
                        edtAnot.setText(temp);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    edtAnot.setText("");
                }
                diaAnteriors = selecteds;
            }
        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar selected = Calendar.getInstance();
                selected.set(year, month, dayOfMonth);
                int dayOfWeek = selected.get(Calendar.DAY_OF_WEEK);
                diaSemana = dayOfWeek;
                listarAulas(dayOfWeek);
                String dia;
                if (selected.get(Calendar.DAY_OF_MONTH) < 10) {
                    dia = "0" + Integer.toString(selected.get(Calendar.DAY_OF_MONTH));
                } else {
                    dia = Integer.toString(selected.get(Calendar.DAY_OF_MONTH));
                }
                String mes;
                if (selected.get(Calendar.MONTH) < 10) {
                    mes = "0" + Integer.toString(selected.get(Calendar.MONTH) + 1);
                } else {
                    mes = Integer.toString(selected.get(Calendar.MONTH) + 1);
                }
                selecteds = dia + mes + Integer.toString(selected.get(Calendar.YEAR));
                if (db.nExistDia(selecteds)) {
                    db.insertCalendario(new Calendario(selecteds, 0, "", "", "", "", 0, "", ""));
                }
                if (!selecteds.equals(diaAnteriors)) {
                    Calendario dA = db.selectCalendario(diaAnteriors);
                    if (dA.getNeve() == 0 && dA.getAnot() == 0) {
                        db.deleteCalendario(dA);
                    }
                }
                Calendario calendario = db.selectCalendario(selecteds);
                if (calendario.getNeve() != 0) {
                    cleventos.setVisibility(View.GONE);
                    setListEventos(selecteds);
                } else {
                    cleventos.setVisibility(View.VISIBLE);
                }
                if (calendario.getAnot() == 1) {
                    String titanot = calendario.getDia() + ".txt";
                    try {
                        FileInputStream fileIn = openFileInput(titanot);
                        InputStreamReader InputRead = new InputStreamReader(fileIn, "UTF-8");
                        BufferedReader reader = new BufferedReader(InputRead);
                        int c;
                        String temp = "";
                        while ( (c = reader.read()) != -1) {
                            temp += Character.toString((char)c);
                        }
                        fileIn.close();
                        edtAnot.setText(temp);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    edtAnot.setText("");
                }
                diaAnteriors = selecteds;
            }
        });

        verAddEve.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                Calendario calendario = db.selectCalendario(selecteds);
                if (calendario.getNeve() != 0) {
                    setListEventos(selecteds);
                } else {
                    listEventos.setAdapter(null);
                }
            }
        });

        btnAddEve = findViewById(R.id.btnAddEve);
        btnAddEve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEveDialog dialog = new AddEveDialog();
                dialog.showDialog(Agenda.this, selecteds);
            }
        });

        final Calendario calendario = db.selectCalendario(selecteds);
        if (calendario.getNeve() != 0) {
            cleventos.setVisibility(View.GONE);
            setListEventos(selecteds);
        } else {
            cleventos.setVisibility(View.VISIBLE);
        }

        listEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EveDetailsDialog dialog = new EveDetailsDialog();
                dialog.showDialog(Agenda.this, selecteds, position);
            }
        });
        listEventos.setLongClickable(true);
        listEventos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                OptionEventoDialog dialog = new OptionEventoDialog();
                dialog.showDialog(Agenda.this, selecteds, position);
                return true;
            }
        });

        final User user = db.selectUser(1);

        listAulas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (diaSemana) {
                    case Calendar.SUNDAY:
                        diaS = 0;
                        break;
                    case Calendar.MONDAY:
                        diaS = 1;
                        break;
                    case Calendar.TUESDAY:
                        diaS = 2;
                        break;
                    case Calendar.WEDNESDAY:
                        diaS = 3;
                        break;
                    case Calendar.THURSDAY:
                        diaS = 4;
                        break;
                    case Calendar.FRIDAY:
                        diaS = 5;
                        break;
                    case Calendar.SATURDAY:
                        diaS = 6;
                        break;
                }
                if (diaS != 0) {
                    Aulas aulas = db.selectAulas(diaS);
                    int ida = 0;
                    if (aulas.getNaulas() == 1) {
                        ida = Integer.parseInt(aulas.getAulaid());
                    } else if (aulas.getNaulas() > 1) {
                        String[] aulasid = aulas.getAulaid().split("/0/1/");
                        ida = Integer.parseInt(aulasid[position]);
                    }
                    Intent intent = new Intent(Agenda.this, DT.class);
                    String nome, cod;
                    if (user.getTipo().equals("prof")) {
                        Turma turma = db.selectTurmaById(ida);
                        nome = turma.getTnome();
                        cod = turma.getTcod();
                    } else {
                        Disciplina disciplina = db.selectDisciplinaById(ida);
                        nome = disciplina.getDnome();
                        cod = disciplina.getDcod();
                    }
                    intent.putExtra("NOMEDT", nome);
                    intent.putExtra("CODDT", cod);
                    startActivity(intent);
                }
            }
        });

        if (calendario.getAnot() == 1) {
            String titanot = calendario.getDia() + ".txt";
            try {
                FileInputStream fileIn = openFileInput(titanot);
                InputStreamReader InputRead = new InputStreamReader(fileIn, "UTF-8");
                BufferedReader reader = new BufferedReader(InputRead);
                int c;
                String temp = "";
                while ( (c = reader.read()) != -1) {
                    temp += Character.toString((char)c);
                }
                fileIn.close();
                edtAnot.setText(temp);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            edtAnot.setText("");
        }

        btnSalvarA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendario calendario1 = db.selectCalendario(selecteds);
                String titanot = calendario1.getDia() + ".txt";
                try {
                    FileOutputStream fileout = openFileOutput(titanot, MODE_PRIVATE);
                    OutputStreamWriter outputWriter = new OutputStreamWriter(fileout, "UTF-8");
                    outputWriter.write(edtAnot.getText().toString());
                    outputWriter.close();
                    db.updateCalendario(new Calendario(calendario1.getIdc(), calendario1.getDia(), calendario1.getNeve(), calendario1.getTiteve(), calendario1.getDesceve(), calendario1.getTipoeve(), calendario1.getNoteve(), 1, titanot, calendario1.getHoraeve()));

                    Toast.makeText(Agenda.this, getString(R.string.salvo_com_sucesso), Toast.LENGTH_SHORT).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnLimparA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendario calendario1 = db.selectCalendario(selecteds);
                String titanot = calendario1.getDia() + ".txt";
                Agenda.this.deleteFile(titanot);
                db.updateCalendario(new Calendario(calendario1.getIdc(), calendario1.getDia(), calendario1.getNeve(), calendario1.getTiteve(), calendario1.getDesceve(), calendario1.getTipoeve(), calendario1.getNoteve(), 0, "", calendario1.getHoraeve()));
                edtAnot.setText("");

                Toast.makeText(Agenda.this, getString(R.string.eliminado_com_sucesso), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            Intent intent = new Intent(Agenda.this, PaginaInicial.class);
            startActivity(intent);
            finishAffinity();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Agenda.this, PaginaInicial.class);
        startActivity(intent);
        finishAffinity();
    }

    public void setListAulas(int dia) {
        User user = db.selectUser(1);
        String tipo = user.getTipo();
        arrayAulas = db.listAulas2(dia, tipo);
        adaAulas = new AAListAdapter(arrayAulas, Agenda.this);
        listAulas.setAdapter(adaAulas);
    }

    public void setListEventos(String dia) {
        arrayEventos = db.listEventos(dia);
        adaEventos = new EListAdapter(arrayEventos, Agenda.this, 1);
        listEventos.setAdapter(adaEventos);
    }

    public void listarAulas(int dayOfWeek) {
        Aulas aulas;
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                claulas.setVisibility(View.VISIBLE);
                listAulas.setVisibility(View.GONE);
                break;
            case Calendar.MONDAY:
                aulas = db.selectAulas(1);
                if (aulas.getNaulas() == 0) {
                    claulas.setVisibility(View.VISIBLE);
                    listAulas.setVisibility(View.GONE);
                } else {
                    claulas.setVisibility(View.GONE);
                    listAulas.setVisibility(View.VISIBLE);
                    setListAulas(1);
                }
                break;
            case Calendar.TUESDAY:
                aulas = db.selectAulas(2);
                if (aulas.getNaulas() == 0) {
                    claulas.setVisibility(View.VISIBLE);
                    listAulas.setVisibility(View.GONE);
                } else {
                    claulas.setVisibility(View.GONE);
                    listAulas.setVisibility(View.VISIBLE);
                    setListAulas(2);
                }
                break;
            case Calendar.WEDNESDAY:
                aulas = db.selectAulas(3);
                if (aulas.getNaulas() == 0) {
                    claulas.setVisibility(View.VISIBLE);
                    listAulas.setVisibility(View.GONE);
                } else {
                    claulas.setVisibility(View.GONE);
                    listAulas.setVisibility(View.VISIBLE);
                    setListAulas(3);
                }
                break;
            case Calendar.THURSDAY:
                aulas = db.selectAulas(4);
                if (aulas.getNaulas() == 0) {
                    claulas.setVisibility(View.VISIBLE);
                    listAulas.setVisibility(View.GONE);
                } else {
                    claulas.setVisibility(View.GONE);
                    listAulas.setVisibility(View.VISIBLE);
                    setListAulas(4);
                }
                break;
            case Calendar.FRIDAY:
                aulas = db.selectAulas(5);
                if (aulas.getNaulas() == 0) {
                    claulas.setVisibility(View.VISIBLE);
                    listAulas.setVisibility(View.GONE);
                } else {
                    claulas.setVisibility(View.GONE);
                    listAulas.setVisibility(View.VISIBLE);
                    setListAulas(5);
                }
                break;
            case Calendar.SATURDAY:
                aulas = db.selectAulas(6);
                if (aulas.getNaulas() == 0) {
                    claulas.setVisibility(View.VISIBLE);
                    listAulas.setVisibility(View.GONE);
                } else {
                    claulas.setVisibility(View.GONE);
                    listAulas.setVisibility(View.VISIBLE);
                    setListAulas(6);
                }
                break;
        }
    }
}
