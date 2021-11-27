package com.deadpixel.dpixel.a4student;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Objects;

public class Registo extends AppCompatActivity {

    EditText p_nome, u_nome;
    Button seg;
    TextView erro, tvpnome, tvunome;
    RadioButton rAluno, rProfessor;
    boolean e1 = false, e2 = false;
    int rb = 1;

    BaseDados db = new BaseDados(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);

        User user = db.selectUser(1);
        if (user.getVer() == 1) {
            finish();
        }

        final int ny = getIntent().getIntExtra("NY", 0);

        // Mudar texto da barra superior
        if (ny == 0) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.registo));
        } else {
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.novo_ano_letivo);
        }

        p_nome = findViewById(R.id.p_nome);
        u_nome = findViewById(R.id.u_nome);
        seg = findViewById(R.id.seg);
        erro = findViewById(R.id.erro);
        tvpnome = findViewById(R.id.tvpnome);
        tvunome = findViewById(R.id.tvunome);
        rAluno = findViewById(R.id.rAluno);
        rProfessor = findViewById(R.id.rProfessor);

        // Select para autopreenchimento dos campos quando já foram atualizados
        final User select_user = db.selectUser(1);
        p_nome.setText(select_user.getPnome());
        u_nome.setText(select_user.getUnome());
        if (select_user.getTipo().equals("prof")) {
            rAluno.setChecked(false);
            rProfessor.setChecked(true);
            rb = 2;
        }

        // Programação do botão para ir para a página seguinte do registo
        seg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se algum campo estiver vazio, vai dar erro a pedir para preencher...
                if ((String.valueOf(p_nome.getText())).trim().equals("") || (String.valueOf(u_nome.getText())).trim().equals("")) {
                    erro.setText(getString(R.string.preencher_todos_os_campos));
                    if ((String.valueOf(p_nome.getText())).trim().equals("")) {
                        tvpnome.setText(getString(R.string.erro_primeiro_nome));
                        tvpnome.setTextColor(0xffff4444);
                        e1 = true;
                    }
                    if ((String.valueOf(u_nome.getText())).trim().equals("")) {
                        tvunome.setText(getString(R.string.erro_ultimo_nome));
                        tvunome.setTextColor(0xffff4444);
                        e2 = true;
                    }
                } else {
                    // ... senão vai para a página seguinte do registo, fazendo update da linha do User com os valores inseridos
                    String tipo;
                    if (rb == 1) {
                        tipo = "aluno";
                    } else {
                        tipo = "prof";
                    }
                    User select_user = db.selectUser(1);
                    User user = new User(1, String.valueOf(p_nome.getText()), String.valueOf(u_nome.getText()), select_user.getNperiodos(), select_user.getIperiodos(), select_user.getFperiodos(), select_user.getSclassificacao(), tipo,0, select_user.getNdisc(), select_user.getNtur(), select_user.getNca(), select_user.getNpor(), select_user.getNot(), select_user.getNotd(), select_user.getNoth());
                    db.updateUser(user);
                    Intent intent = new Intent(Registo.this, Registo2.class);
                    if (ny == 0) {
                        intent.putExtra("NY", 0);
                    } else {
                        intent.putExtra("NY", 1);
                    }
                    startActivity(intent);
                }
            }
        });

        // Verificação de campo de texto modificado
        p_nome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (e1) {
                    tvpnome.setText(getString(R.string.primeiro_nome));
                    tvpnome.setTextColor(0xff000000);
                    e1 = false;
                }
                if (!e1 && !e2) {
                    erro.setText("");
                }
            }
        });

        u_nome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (e2) {
                    tvunome.setText(getString(R.string.ltimo_nome));
                    tvunome.setTextColor(0xff000000);
                    e2 = false;
                }
                if (!e1 && !e2) {
                    erro.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case (R.id.rAluno):
                if (checked) {
                    rb = 1;
                }
                break;
            case (R.id.rProfessor):
                if (checked) {
                    rb = 2;
                }
                break;
        }
    }
}
