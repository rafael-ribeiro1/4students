package com.deadpixel.dpixel.a4student;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class Horario extends AppCompatActivity {

    BaseDados db = new BaseDados(this);

    FloatingActionButton addAula;
    ListView listSegunda, listTerca, listQuarta, listQuinta, listSexta, listSabado;
    TextView verAddAula;

    ArrayList<Aula> segunda;
    private static AListAdapter adaSegunda;
    ArrayList<Aula> terca;
    private static AListAdapter adaTerca;
    ArrayList<Aula> quarta;
    private static AListAdapter adaQuarta;
    ArrayList<Aula> quinta;
    private static AListAdapter adaQuinta;
    ArrayList<Aula> sexta;
    private static AListAdapter adaSexta;
    ArrayList<Aula> sabado;
    private static AListAdapter adaSabado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.horario));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        addAula = (FloatingActionButton) findViewById(R.id.addAula);
        listSegunda = findViewById(R.id.listSegunda);
        listTerca = findViewById(R.id.listTerca);
        listQuarta = findViewById(R.id.listQuarta);
        listQuinta = findViewById(R.id.listQuinta);
        listSexta = findViewById(R.id.listSexta);
        listSabado = findViewById(R.id.listSabado);
        verAddAula = findViewById(R.id.verAddAula);

        Aulas aulas1 = db.selectAulas(1);
        if (aulas1.getNaulas() != 0) {
            setListSegunda();
        }
        Aulas aulas2 = db.selectAulas(2);
        if (aulas2.getNaulas() != 0) {
            setListTerca();
        }
        Aulas aulas3 = db.selectAulas(3);
        if (aulas3.getNaulas() != 0) {
            setListQuarta();
        }
        Aulas aulas4 = db.selectAulas(4);
        if (aulas4.getNaulas() != 0) {
            setListQuinta();
        }
        Aulas aulas5 = db.selectAulas(5);
        if (aulas5.getNaulas() != 0) {
            setListSexta();
        }
        Aulas aulas6 = db.selectAulas(6);
        if (aulas6.getNaulas() != 0) {
            setListSabado();
        }

        verAddAula.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                Aulas aulas1 = db.selectAulas(1);
                if (aulas1.getNaulas() != 0) {
                    setListSegunda();
                } else {
                    listSegunda.setAdapter(null);
                }
                Aulas aulas2 = db.selectAulas(2);
                if (aulas2.getNaulas() != 0) {
                    setListTerca();
                } else {
                    listTerca.setAdapter(null);
                }
                Aulas aulas3 = db.selectAulas(3);
                if (aulas3.getNaulas() != 0) {
                    setListQuarta();
                } else {
                    listQuarta.setAdapter(null);
                }
                Aulas aulas4 = db.selectAulas(4);
                if (aulas4.getNaulas() != 0) {
                    setListQuinta();
                } else {
                    listQuinta.setAdapter(null);
                }
                Aulas aulas5 = db.selectAulas(5);
                if (aulas5.getNaulas() != 0) {
                    setListSexta();
                } else {
                    listSexta.setAdapter(null);
                }
                Aulas aulas6 = db.selectAulas(6);
                if (aulas6.getNaulas() != 0) {
                    setListSabado();
                } else {
                    listSabado.setAdapter(null);
                }
            }
        });
        addAula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddAulaDialog dialog = new AddAulaDialog();
                dialog.showDialog(Horario.this);
            }
        });

        final User user = db.selectUser(1);

        listSegunda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OnClickListAulas(user, 1, position);
            }
        });
        listSegunda.setLongClickable(true);
        listSegunda.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                OptionAulaDialog dialog = new OptionAulaDialog();
                dialog.showDialog(Horario.this, 1, position);
                return true;
            }
        });
        listTerca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OnClickListAulas(user, 2, position);
            }
        });
        listTerca.setLongClickable(true);
        listTerca.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                OptionAulaDialog dialog = new OptionAulaDialog();
                dialog.showDialog(Horario.this, 2, position);
                return true;
            }
        });
        listQuarta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OnClickListAulas(user, 3, position);
            }
        });
        listQuarta.setLongClickable(true);
        listQuarta.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                OptionAulaDialog dialog = new OptionAulaDialog();
                dialog.showDialog(Horario.this, 3, position);
                return true;
            }
        });
        listQuinta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OnClickListAulas(user, 4, position);
            }
        });
        listQuinta.setLongClickable(true);
        listQuinta.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                OptionAulaDialog dialog = new OptionAulaDialog();
                dialog.showDialog(Horario.this, 4, position);
                return true;
            }
        });
        listSexta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OnClickListAulas(user, 5, position);
            }
        });
        listSexta.setLongClickable(true);
        listSexta.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                OptionAulaDialog dialog = new OptionAulaDialog();
                dialog.showDialog(Horario.this, 5, position);
                return true;
            }
        });
        listSabado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OnClickListAulas(user, 6, position);
            }
        });
        listSabado.setLongClickable(true);
        listSabado.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                OptionAulaDialog dialog = new OptionAulaDialog();
                dialog.showDialog(Horario.this, 6, position);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            Intent intent = new Intent(Horario.this, PaginaInicial.class);
            startActivity(intent);
            finishAffinity();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Horario.this, PaginaInicial.class);
        startActivity(intent);
        finishAffinity();
    }

    public void setListSegunda() {
        User user = db.selectUser(1);
        String tipo = user.getTipo();
        segunda = db.listAulas(1, tipo);
        adaSegunda = new AListAdapter(segunda, Horario.this);
        listSegunda.setAdapter(adaSegunda);
    }
    public void setListTerca() {
        User user = db.selectUser(1);
        String tipo = user.getTipo();
        terca = db.listAulas(2, tipo);
        adaTerca = new AListAdapter(terca, Horario.this);
        listTerca.setAdapter(adaTerca);
    }
    public void setListQuarta() {
        User user = db.selectUser(1);
        String tipo = user.getTipo();
        quarta = db.listAulas(3, tipo);
        adaQuarta = new AListAdapter(quarta, Horario.this);
        listQuarta.setAdapter(adaQuarta);
    }
    public void setListQuinta() {
        User user = db.selectUser(1);
        String tipo = user.getTipo();
        quinta = db.listAulas(4, tipo);
        adaQuinta = new AListAdapter(quinta, Horario.this);
        listQuinta.setAdapter(adaQuinta);
    }
    public void setListSexta() {
        User user = db.selectUser(1);
        String tipo = user.getTipo();
        sexta = db.listAulas(5, tipo);
        adaSexta = new AListAdapter(sexta, Horario.this);
        listSexta.setAdapter(adaSexta);
    }
    public void setListSabado() {
        User user = db.selectUser(1);
        String tipo = user.getTipo();
        sabado = db.listAulas(6, tipo);
        adaSabado = new AListAdapter(sabado, Horario.this);
        listSabado.setAdapter(adaSabado);
    }

    public void OnClickListAulas(User user, int dia, int position) {
        Aulas aulas = db.selectAulas(dia);
        int id = 0;
        if (aulas.getNaulas() == 1) {
            id = Integer.parseInt(aulas.getAulaid());
        } else if (aulas.getNaulas() > 1) {
            String[] aulasid = aulas.getAulaid().split("/0/1/");
            id = Integer.parseInt(aulasid[position]);
        }
        Intent intent = new Intent(Horario.this, DT.class);
        String nome, cod;
        if (user.getTipo().equals("prof")) {
            Turma turma = db.selectTurmaById(id);
            nome = turma.getTnome();
            cod = turma.getTcod();
        } else {
            Disciplina disciplina = db.selectDisciplinaById(id);
            nome = disciplina.getDnome();
            cod = disciplina.getDcod();
        }
        intent.putExtra("NOMEDT", nome);
        intent.putExtra("CODDT", cod);
        startActivity(intent);
    }
}
