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
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Objects;

public class AAva extends AppCompatActivity {

    BaseDados db = new BaseDados(this);

    Button btnApaAAva, btnSalAAva;
    LinearLayout lllist;
    TextView txtAAva, erroAAva;

    String cod2, tit;
    int length;
    boolean[] e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aava);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        btnApaAAva = findViewById(R.id.btnApaAAva);
        btnSalAAva = findViewById(R.id.btnSalAAva);
        lllist = findViewById(R.id.lllist);
        txtAAva = findViewById(R.id.txtAAva);
        erroAAva = findViewById(R.id.erroAAva);

        erroAAva.setVisibility(View.GONE);

        String cod = getIntent().getStringExtra("COD");
        cod2 = cod;

        final int nper = getIntent().getIntExtra("NPER", 0);

        if (nper == 1) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.tit1per));
        } else if (nper == 2) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.tit2per));
        } else {
            Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.tit3per));
        }

        final Disciplina disciplina = db.selectDisciplina(cod);

        tit = disciplina.getDnome();

        final User user = db.selectUser(1);

        CriterioAvaliacao criterioAvaliacao = db.selectCriterioAvaliacaoId(disciplina.getDidca());
        length = criterioAvaliacao.getCanpara();
        final String[] para = criterioAvaliacao.getCapara().split("/0/1/");
        final String[] cot = criterioAvaliacao.getCacot().split("/0/1/");

        e = new boolean[length];
        for (int c = 0; c < length; c++) {
            e[c] = false;
        }

        String txt;
        if (length == 1) {
            txt = getString(R.string.autoavalie_o_seguinte_par_metro_entre);
        } else {
            txt = getString(R.string.autoavalie_os_seguintes_par_metros_entre);
        }
        switch (user.getSclassificacao()) {
            case 1:
                txt += " 0 e 20";
                txtAAva.setText(txt);
                break;
            case 2:
                txt += " 1 e 5";
                txtAAva.setText(txt);
                break;
            case 3:
                txt += " 0 e 100";
                txtAAva.setText(txt);
                break;
            case 4:
                txt += " 0 e 200";
                txtAAva.setText(txt);
                break;
            case 5:
                txt += " 0 e 10";
                txtAAva.setText(txt);
                break;
        }

        for (int i = 0; i < length; i++) {
            final int ii = i;

            LinearLayout linearLayout = new LinearLayout(AAva.this);
            linearLayout.setTag("ll" + Integer.toString(i));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(params);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            final TextView textView = new TextView(AAva.this);
            textView.setTag("tv" + Integer.toString(i));
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            textView.setLayoutParams(params2);
            textView.setTextSize(12f);
            textView.setTextColor(0xff000000);
            String txt2 = cot[i] + "% - " + para[i];
            textView.setText(txt2);
            linearLayout.addView(textView);

            EditText editText = new EditText(AAva.this);
            editText.setTag("edt" + Integer.toString(i));
            LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics()), LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            textView.setLayoutParams(params3);
            editText.setEms(5);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editText.setMaxLines(1);
            switch (user.getSclassificacao()) {
                case 1:
                    InputFilter[] filterArray = new InputFilter[1];
                    filterArray[0] = new InputFilter.LengthFilter(4);
                    editText.setFilters(filterArray);
                    ifIsAva(nper, disciplina, editText, i);
                    break;
                case 2:
                    InputFilter[] filterArray2 = new InputFilter[1];
                    filterArray2[0] = new InputFilter.LengthFilter(3);
                    editText.setFilters(filterArray2);
                    ifIsAva(nper, disciplina, editText, i);
                    break;
                case 3:
                    InputFilter[] filterArray3 = new InputFilter[1];
                    filterArray3[0] = new InputFilter.LengthFilter(5);
                    editText.setFilters(filterArray3);
                    ifIsAva(nper, disciplina, editText, i);
                    break;
                case 4:
                    InputFilter[] filterArray4 = new InputFilter[1];
                    filterArray4[0] = new InputFilter.LengthFilter(5);
                    editText.setFilters(filterArray4);
                    ifIsAva(nper, disciplina, editText, i);
                    break;
                case 5:
                    InputFilter[] filterArray5 = new InputFilter[1];
                    filterArray5[0] = new InputFilter.LengthFilter(4);
                    editText.setFilters(filterArray5);
                    ifIsAva(nper, disciplina, editText, i);
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
                        erroAAva.setVisibility(View.GONE);
                        erroAAva.setText("");
                    }
                }
            });
        }

        btnApaAAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < length; i++) {
                    EditText editText = lllist.findViewWithTag("edt" + Integer.toString(i));
                    editText.setText("");
                }
                if (nper == 1) {
                    db.updateDisciplina(new Disciplina(disciplina.getIdd(), disciplina.getDnome(), disciplina.getDcod(), disciplina.getDsala(), disciplina.getDidca(), 0, disciplina.getAava2(), disciplina.getAava3(), "", disciplina.getCaaava2(), disciplina.getCaaava3(), 0f, disciplina.getNotaper2(), disciplina.getNotaper3(), disciplina.getNotafinald()));
                } else if (nper == 2) {
                    db.updateDisciplina(new Disciplina(disciplina.getIdd(), disciplina.getDnome(), disciplina.getDcod(), disciplina.getDsala(), disciplina.getDidca(), disciplina.getAava1(), 0, disciplina.getAava3(), disciplina.getCaaava1(), "", disciplina.getCaaava3(), disciplina.getNotaper1(), 0f, disciplina.getNotaper3(), disciplina.getNotafinald()));
                } else {
                    db.updateDisciplina(new Disciplina(disciplina.getIdd(), disciplina.getDnome(), disciplina.getDcod(), disciplina.getDsala(), disciplina.getDidca(), disciplina.getAava1(), disciplina.getAava2(), 0, disciplina.getCaaava1(), disciplina.getCaaava2(), "", disciplina.getNotaper1(), disciplina.getNotaper2(), 0f, disciplina.getNotafinald()));
                }
                Intent intent = new Intent(AAva.this, DT.class);
                intent.putExtra("NOMEDT", tit);
                intent.putExtra("CODDT", cod2);
                startActivity(intent);
                finish();
            }
        });

        btnSalAAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float tot = 0f;
                int ver = 0;
                String ca = "";
                for (int i = 0; i < length; i++) {
                    EditText editText = lllist.findViewWithTag("edt" + Integer.toString(i));
                    if ((editText.getText().toString()).trim().equals("")) {
                        erroAAva.setVisibility(View.VISIBLE);
                        erroAAva.setText(getString(R.string.preencher_todos_os_campos));
                        TextView textView = lllist.findViewWithTag("tv" + Integer.toString(i));
                        textView.setText(cot[i] + "% - " + para[i] + "*");
                        textView.setTextColor(0xffff4444);
                        e[i] = true;
                    } else {
                        if (isFloat(editText)) {
                            float num = Float.valueOf(editText.getText().toString().trim());
                            switch (user.getSclassificacao()) {
                                case 1:
                                    if (num < 0 || num > 20) {
                                        erroAAva.setVisibility(View.VISIBLE);
                                        erroAAva.setText(getString(R.string.erro_num_invalido));
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
                                        erroAAva.setVisibility(View.VISIBLE);
                                        erroAAva.setText(getString(R.string.erro_num_invalido));
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
                                        erroAAva.setVisibility(View.VISIBLE);
                                        erroAAva.setText(getString(R.string.erro_num_invalido));
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
                                        erroAAva.setVisibility(View.VISIBLE);
                                        erroAAva.setText(getString(R.string.erro_num_invalido));
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
                                        erroAAva.setVisibility(View.VISIBLE);
                                        erroAAva.setText(getString(R.string.erro_num_invalido));
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
                            erroAAva.setVisibility(View.VISIBLE);
                            erroAAva.setText(getString(R.string.erro_num_invalido));
                            TextView textView = lllist.findViewWithTag("tv" + Integer.toString(i));
                            textView.setText(cot[i] + "% - " + para[i] + "*");
                            textView.setTextColor(0xffff4444);
                            e[i] = true;
                        }
                    }
                }
                if (ver == length) {
                    if (nper == 1) {
                        db.updateDisciplina(new Disciplina(disciplina.getIdd(), disciplina.getDnome(), disciplina.getDcod(), disciplina.getDsala(), disciplina.getDidca(), 1, disciplina.getAava2(), disciplina.getAava3(), ca, disciplina.getCaaava2(), disciplina.getCaaava3(), tot, disciplina.getNotaper2(), disciplina.getNotaper3(), disciplina.getNotafinald()));
                    } else if (nper == 2) {
                        db.updateDisciplina(new Disciplina(disciplina.getIdd(), disciplina.getDnome(), disciplina.getDcod(), disciplina.getDsala(), disciplina.getDidca(), disciplina.getAava1(), 1, disciplina.getAava3(), disciplina.getCaaava1(), ca, disciplina.getCaaava3(), disciplina.getNotaper1(), tot, disciplina.getNotaper3(), disciplina.getNotafinald()));
                    } else {
                        db.updateDisciplina(new Disciplina(disciplina.getIdd(), disciplina.getDnome(), disciplina.getDcod(), disciplina.getDsala(), disciplina.getDidca(), disciplina.getAava1(), disciplina.getAava2(), 1, disciplina.getCaaava1(), disciplina.getCaaava2(), ca, disciplina.getNotaper1(), disciplina.getNotaper2(), tot, disciplina.getNotafinald()));
                    }
                    Intent intent = new Intent(AAva.this, DT.class);
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
            Intent intent = new Intent(AAva.this, DT.class);
            intent.putExtra("NOMEDT", tit);
            intent.putExtra("CODDT", cod2);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AAva.this, DT.class);
        intent.putExtra("NOMEDT", tit);
        intent.putExtra("CODDT", cod2);
        startActivity(intent);
        finish();
    }

    public void ifIsAva(int nper, Disciplina disciplina, EditText editText, int i) {
        if (nper == 1) {
            if (disciplina.getAava1() == 1) {
                String[] ava1 = disciplina.getCaaava1().split("/0/1/");
                editText.setText(ava1[i]);
            }
        } else if (nper == 2) {
            if (disciplina.getAava2() == 1) {
                String[] ava2 = disciplina.getCaaava2().split("/0/1/");
                editText.setText(ava2[i]);
            }
        } else {
            if (disciplina.getAava3() == 1) {
                String[] ava3 = disciplina.getCaaava3().split("/0/1/");
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
