package com.deadpixel.dpixel.a4student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

public class Ava extends AppCompatActivity {

    BaseDados db = new BaseDados(this);

    Button btnApaAva, btnSalAva;
    LinearLayout lllist;
    TextView txtAva, erroAva;

    String cod2, tit;
    int length;
    boolean[] e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ava);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        btnApaAva = findViewById(R.id.btnApaAva);
        btnSalAva = findViewById(R.id.btnSalAva);
        lllist = findViewById(R.id.lllist);
        txtAva = findViewById(R.id.txtAva);
        erroAva = findViewById(R.id.erroAva);

        erroAva.setVisibility(View.GONE);

        String cod = getIntent().getStringExtra("COD");
        cod2 = cod;

        final int nper = getIntent().getIntExtra("NPER", 0);

        final int numalu = getIntent().getIntExtra("NUMALU", 0);

        if (nper == 1) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.tit1per));
        } else if (nper == 2) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.tit2per));
        } else {
            Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.tit3per));
        }

        final Turma turma = db.selectTurma(cod);

        final String TABELA_ALUNO = "abcde" + cod + "edcba";

        final Aluno aluno = db.selectAluno(TABELA_ALUNO, numalu);

        tit = turma.getTnome();

        final User user = db.selectUser(1);

        CriterioAvaliacao criterioAvaliacao = db.selectCriterioAvaliacaoId(turma.getTidca());
        length = criterioAvaliacao.getCanpara();
        final String[] para = criterioAvaliacao.getCapara().split("/0/1/");
        final String[] cot = criterioAvaliacao.getCacot().split("/0/1/");

        e = new boolean[length];
        for (int c = 0; c < length; c++) {
            e[c] = false;
        }

        String txt;
        if (length == 1) {
            txt = getString(R.string.avalie_o_seguinte_par_metro_entre);
        } else {
            txt = getString(R.string.avalie_os_seguintes_par_metros_entre);
        }
        switch (turma.getTscla()) {
            case 1:
                txt += " 0 e 20";
                txtAva.setText(txt);
                break;
            case 2:
                txt += " 1 e 5";
                txtAva.setText(txt);
                break;
            case 3:
                txt += " 0 e 100";
                txtAva.setText(txt);
                break;
            case 4:
                txt += " 0 e 200";
                txtAva.setText(txt);
                break;
            case 5:
                txt += " 0 e 10";
                txtAva.setText(txt);
                break;
        }

        for (int i = 0; i < length; i++) {
            final int ii = i;

            LinearLayout linearLayout = new LinearLayout(Ava.this);
            linearLayout.setTag("ll" + Integer.toString(i));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(params);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            final TextView textView = new TextView(Ava.this);
            textView.setTag("tv" + Integer.toString(i));
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            textView.setLayoutParams(params2);
            textView.setTextSize(12f);
            textView.setTextColor(0xff000000);
            String txt2 = cot[i] + "% - " + para[i];
            textView.setText(txt2);
            linearLayout.addView(textView);

            EditText editText = new EditText(Ava.this);
            editText.setTag("edt" + Integer.toString(i));
            LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics()), LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            textView.setLayoutParams(params3);
            editText.setEms(5);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editText.setMaxLines(1);
            switch (turma.getTscla()) {
                case 1:
                    InputFilter[] filterArray = new InputFilter[1];
                    filterArray[0] = new InputFilter.LengthFilter(4);
                    editText.setFilters(filterArray);
                    ifIsAva(nper, aluno, editText, i);
                    break;
                case 2:
                    InputFilter[] filterArray2 = new InputFilter[1];
                    filterArray2[0] = new InputFilter.LengthFilter(3);
                    editText.setFilters(filterArray2);
                    ifIsAva(nper, aluno, editText, i);
                    break;
                case 3:
                    InputFilter[] filterArray3 = new InputFilter[1];
                    filterArray3[0] = new InputFilter.LengthFilter(5);
                    editText.setFilters(filterArray3);
                    ifIsAva(nper, aluno, editText, i);
                    break;
                case 4:
                    InputFilter[] filterArray4 = new InputFilter[1];
                    filterArray4[0] = new InputFilter.LengthFilter(5);
                    editText.setFilters(filterArray4);
                    ifIsAva(nper, aluno, editText, i);
                    break;
                case 5:
                    InputFilter[] filterArray5 = new InputFilter[1];
                    filterArray5[0] = new InputFilter.LengthFilter(4);
                    editText.setFilters(filterArray5);
                    ifIsAva(nper, aluno, editText, i);
                    break;
            }
            linearLayout.addView(editText);

            lllist.addView(linearLayout);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable s) {
                    if (e[ii]) {
                        textView.setText(cot[ii] + "% - " + para[ii]);
                        textView.setTextColor(0xff000000);
                        e[ii] = false;
                    }
                    int ev = 0;
                    for (int cc = 0; cc < e.length; cc++) {
                        if (e[cc]) {
                            ev++;
                        }
                    }
                    if (ev == 0) {
                        erroAva.setVisibility(View.GONE);
                        erroAva.setText("");
                    }
                }
            });
        }

        btnApaAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < length; i++) {
                    EditText editText = lllist.findViewWithTag("edt" + Integer.toString(i));
                    editText.setText("");
                }
                if (nper == 1) {
                    db.updateAluno(new Aluno(aluno.getIda(), aluno.getAnum(), aluno.getAnome(), 0, aluno.getNota2per(), aluno.getNota3per(), aluno.getNotafinal(), 0, aluno.getAva2(), aluno.getAva3(), "", aluno.getCaava2(), aluno.getCaava3()), TABELA_ALUNO);
                } else if (nper == 2) {
                    db.updateAluno(new Aluno(aluno.getIda(), aluno.getAnum(), aluno.getAnome(), aluno.getNota1per(), 0, aluno.getNota3per(), aluno.getNotafinal(), aluno.getAva1(), 0, aluno.getAva3(), aluno.getCaava1(), "", aluno.getCaava3()), TABELA_ALUNO);
                } else {
                    db.updateAluno(new Aluno(aluno.getIda(), aluno.getAnum(), aluno.getAnome(), aluno.getNota1per(), aluno.getNota2per(), 0, aluno.getNotafinal(), aluno.getAva1(), aluno.getAva2(), 0, aluno.getCaava1(), aluno.getCaava2(), ""), TABELA_ALUNO);
                }
                Intent intent = new Intent(Ava.this, DT.class);
                intent.putExtra("NOMEDT", tit);
                intent.putExtra("CODDT", cod2);
                startActivity(intent);
                finish();
            }
        });

        btnSalAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float tot = 0f;
                int ver = 0;
                String ca = "";
                for (int i = 0; i < length; i++) {
                    EditText editText = lllist.findViewWithTag("edt" + Integer.toString(i));
                    if ((editText.getText().toString()).trim().equals("")) {
                        erroAva.setVisibility(View.VISIBLE);
                        erroAva.setText(getString(R.string.preencher_todos_os_campos));
                        TextView textView = lllist.findViewWithTag("tv" + Integer.toString(i));
                        textView.setText(cot[i] + "% - " + para[i] + "*");
                        textView.setTextColor(0xffff4444);
                        e[i] = true;
                    } else {
                        if (isFloat(editText)) {
                            float num = Float.valueOf(editText.getText().toString().trim());
                            switch (turma.getTscla()) {
                                case 1:
                                    if (num < 0 || num > 20) {
                                        erroAva.setVisibility(View.VISIBLE);
                                        erroAva.setText(getString(R.string.erro_num_invalido));
                                        TextView textView = lllist.findViewWithTag("tv" + Integer.toString(i));
                                        textView.setText(cot[i] + "% - " + para[i] + "*");
                                        textView.setTextColor(0xffff4444);
                                        e[i] = true;
                                    } else {
                                        tot += (Float.valueOf(cot[i]) * 0.01) * num;
                                        ver++;
                                        if (i == 0) {
                                            ca = editText.getText().toString().trim();
                                        } else {
                                            ca += "/0/1/" + editText.getText().toString().trim();
                                        }
                                    }
                                    break;
                                case 2:
                                    if (num < 1 || num > 5) {
                                        erroAva.setVisibility(View.VISIBLE);
                                        erroAva.setText(getString(R.string.erro_num_invalido));
                                        TextView textView = lllist.findViewWithTag("tv" + Integer.toString(i));
                                        textView.setText(cot[i] + "% - " + para[i] + "*");
                                        textView.setTextColor(0xffff4444);
                                        e[i] = true;
                                    } else {
                                        tot += (Float.valueOf(cot[i]) * 0.01) * num;
                                        ver++;
                                        if (i == 1) {
                                            ca += editText.getText().toString().trim();
                                        } else {
                                            ca += "/0/1/" + editText.getText().toString().trim();
                                        }
                                    }
                                    break;
                                case 3:
                                    if (num < 0 || num > 100) {
                                        erroAva.setVisibility(View.VISIBLE);
                                        erroAva.setText(getString(R.string.erro_num_invalido));
                                        TextView textView = lllist.findViewWithTag("tv" + Integer.toString(i));
                                        textView.setText(cot[i] + "% - " + para[i] + "*");
                                        textView.setTextColor(0xffff4444);
                                        e[i] = true;
                                    } else {
                                        tot += (Float.valueOf(cot[i]) * 0.01) * num;
                                        ver++;
                                        if (i == 1) {
                                            ca += editText.getText().toString().trim();
                                        } else {
                                            ca += "/0/1/" + editText.getText().toString().trim();
                                        }
                                    }
                                    break;
                                case 4:
                                    if (num < 0 || num > 200) {
                                        erroAva.setVisibility(View.VISIBLE);
                                        erroAva.setText(getString(R.string.erro_num_invalido));
                                        TextView textView = lllist.findViewWithTag("tv" + Integer.toString(i));
                                        textView.setText(cot[i] + "% - " + para[i] + "*");
                                        textView.setTextColor(0xffff4444);
                                        e[i] = true;
                                    } else {
                                        tot += (Float.valueOf(cot[i]) * 0.01) * num;
                                        ver++;
                                        if (i == 1) {
                                            ca += editText.getText().toString().trim();
                                        } else {
                                            ca += "/0/1/" + editText.getText().toString().trim();
                                        }
                                    }
                                    break;
                                case 5:
                                    if (num < 0 || num > 10) {
                                        erroAva.setVisibility(View.VISIBLE);
                                        erroAva.setText(getString(R.string.erro_num_invalido));
                                        TextView textView = lllist.findViewWithTag("tv" + Integer.toString(i));
                                        textView.setText(cot[i] + "% - " + para[i] + "*");
                                        textView.setTextColor(0xffff4444);
                                        e[i] = true;
                                    } else {
                                        tot += (Float.valueOf(cot[i]) * 0.01) * num;
                                        ver++;
                                        if (i == 1) {
                                            ca += editText.getText().toString().trim();
                                        } else {
                                            ca += "/0/1/" + editText.getText().toString().trim();
                                        }
                                    }
                                    break;
                            }
                        } else {
                            erroAva.setVisibility(View.VISIBLE);
                            erroAva.setText(getString(R.string.erro_num_invalido));
                            TextView textView = lllist.findViewWithTag("tv" + Integer.toString(i));
                            textView.setText(cot[i] + "% - " + para[i] + "*");
                            textView.setTextColor(0xffff4444);
                            e[i] = true;
                        }
                    }
                }
                if (ver == length) {
                    if (nper == 1) {
                        db.updateAluno(new Aluno(aluno.getIda(), aluno.getAnum(), aluno.getAnome(), tot, aluno.getNota2per(), aluno.getNota3per(), aluno.getNotafinal(), 1, aluno.getAva2(), aluno.getAva3(), ca, aluno.getCaava2(), aluno.getCaava3()), TABELA_ALUNO);
                    } else if (nper == 2) {
                        db.updateAluno(new Aluno(aluno.getIda(), aluno.getAnum(), aluno.getAnome(), aluno.getNota1per(), tot, aluno.getNota3per(), aluno.getNotafinal(), aluno.getAva1(), 1, aluno.getAva3(), aluno.getCaava1(), ca, aluno.getCaava3()), TABELA_ALUNO);
                    } else {
                        db.updateAluno(new Aluno(aluno.getIda(), aluno.getAnum(), aluno.getAnome(), aluno.getNota1per(), aluno.getNota2per(), tot, aluno.getNotafinal(), aluno.getAva1(), aluno.getAva2(), 1, aluno.getCaava1(), aluno.getCaava2(), ca), TABELA_ALUNO);
                    }
                    Intent intent = new Intent(Ava.this, DT.class);
                    intent.putExtra("NOMEDT", tit);
                    intent.putExtra("CODDT", cod2);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            Intent intent = new Intent(Ava.this, DT.class);
            intent.putExtra("NOMEDT", tit);
            intent.putExtra("CODDT", cod2);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Ava.this, DT.class);
        intent.putExtra("NOMEDT", tit);
        intent.putExtra("CODDT", cod2);
        startActivity(intent);
        finish();
    }

    public void ifIsAva(int nper, Aluno aluno, EditText editText, int i) {
        if (nper == 1) {
            if (aluno.getAva1() == 1) {
                String[] ava1 = aluno.getCaava1().split("/0/1/");
                editText.setText(ava1[i]);
            }
        } else if (nper == 2) {
            if (aluno.getAva2() == 1) {
                String[] ava2 = aluno.getCaava2().split("/0/1/");
                editText.setText(ava2[i]);
            }
        } else {
            if (aluno.getAva3() == 1) {
                String[] ava3 = aluno.getCaava3().split("/0/1/");
                editText.setText(ava3[i]);
            }
        }
    }

    boolean isFloat(EditText txt) {
        try {
            //float num = NumberFormat.getInstance().parse(txt.getText().toString()).floatValue();
            float num = Float.valueOf(txt.getText().toString().trim());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
