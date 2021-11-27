package com.deadpixel.dpixel.a4student;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;

public class Registo2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    BaseDados db = new BaseDados(this);

    Button voltar, seg;
    Spinner nPeriodos;
    int numPer = 1;
    RelativeLayout periodo1, periodo2, periodo3;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    EditText dinicio1, dinicio2, dinicio3, dfim1, dfim2, dfim3;
    TextView erroReg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo2);

        final User user = db.selectUser(1);
        if (user.getVer() == 1) {
            finish();
        }

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        final int ny = getIntent().getIntExtra("NY", 0);

        if (ny == 0) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.registo));
        } else {
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.novo_ano_letivo);
        }

        voltar = findViewById(R.id.voltar);
        seg = findViewById(R.id.seg);
        nPeriodos = findViewById(R.id.nPeriodos);
        dinicio1 = findViewById(R.id.dinicio1);
        dinicio2 = findViewById(R.id.dinicio2);
        dinicio3 = findViewById(R.id.dinicio3);
        dfim1 = findViewById(R.id.dfim1);
        dfim2 = findViewById(R.id.dfim2);
        dfim3 = findViewById(R.id.dfim3);
        erroReg2 = findViewById(R.id.erroReg2);

        // Spinner de seleção de nº de períodos
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.nperiodos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nPeriodos.setAdapter(adapter);
        final User select_user = db.selectUser(1);
        if (select_user.getNperiodos() == 0) {
            nPeriodos.setSelection(2);
        } else {
            nPeriodos.setSelection(select_user.getNperiodos() - 1);
            if (select_user.getNperiodos() == 1) {
                dinicio1.setText(select_user.getIperiodos());
                dfim1.setText(select_user.getFperiodos());
            } else if (select_user.getNperiodos() == 2) {
                String[] di2per = select_user.getIperiodos().split("-");
                String[] df2per = select_user.getFperiodos().split("-");
                dinicio1.setText(di2per[0]);
                dfim1.setText(df2per[0]);
                dinicio2.setText(di2per[1]);
                dfim2.setText(df2per[1]);
            } else {
                String[] di3per = select_user.getIperiodos().split("-");
                String[] df3per = select_user.getFperiodos().split("-");
                dinicio1.setText(di3per[0]);
                dfim1.setText(df3per[0]);
                dinicio2.setText(di3per[1]);
                dfim2.setText(df3per[1]);
                dinicio3.setText(di3per[2]);
                dfim3.setText(df3per[2]);
            }
        }
        nPeriodos.setOnItemSelectedListener(this);

        // Programação do botão de Voltar para a página anterior do registo
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registo2.this, Registo.class);
                if (ny == 0) {
                    intent.putExtra("NY", 0);
                } else {
                    intent.putExtra("NY", 1);
                }
                startActivity(intent);
            }
        });

        // Programação do botão Seguir para a página seguinte do registo
        seg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String di1 = dinicio1.getText().toString();
                String di2 = dinicio2.getText().toString();
                String di3 = dinicio3.getText().toString();
                String df1 = dfim1.getText().toString();
                String df2 = dfim2.getText().toString();
                String df3 = dfim3.getText().toString();
                if (numPer == 3 && ((di1.equals("")) || (di2.equals("")) || (di3.equals("")) || (df1.equals("")) || (df2.equals("")) || (df3.equals("")))
                        || (numPer == 2 && ((di1.equals("")) || (di2.equals("")) || (df1.equals("")) || (df2.equals(""))))
                        || (numPer == 1 && ((di1.equals("")) || (df1.equals("")) ))) {
                    erroReg2.setText(R.string.preencher_todos_os_campos);
                } else {
                    User user_select2 = db.selectUser(1);
                    Intent intent3 = new Intent(Registo2.this, Registo3.class);
                    if (ny == 0) {
                        intent3.putExtra("NY", 0);
                    } else {
                        intent3.putExtra("NY", 1);
                    }
                    if (numPer == 1) {
                        boolean v1 = verificarDatas(di1, df1);
                        if (v1) {
                            User update_user = new User(1, user_select2.getPnome(), user_select2.getUnome(), numPer, di1, df1, user_select2.getSclassificacao(), user_select2.getTipo(), 0, user_select2.getNdisc(), user_select2.getNtur(), user_select2.getNca(), user_select2.getNpor(), user_select2.getNot(), user_select2.getNotd(), user_select2.getNoth());
                            db.updateUser(update_user);
                            startActivity(intent3);
                        } else {
                            erroReg2.setText(getString(R.string.erro_datas_seguintes_maiores_anteriores));
                        }
                    } else if (numPer == 2) {
                        boolean v1 = verificarDatas(di1, df1);
                        boolean v2 = verificarDatas(df1, di2);
                        boolean v3 = verificarDatas(di2, df2);
                        if (v1 && v2 && v3) {
                            String di2per = di1 + "-" + di2;
                            String df2per = df1 + "-" + df2;
                            User update_user = new User(1, user_select2.getPnome(), user_select2.getUnome(), numPer, di2per, df2per, user_select2.getSclassificacao(), user_select2.getTipo(), 0, user_select2.getNdisc(), user_select2.getNtur(), user_select2.getNca(), user_select2.getNpor(), user_select2.getNot(), user_select2.getNotd(), user_select2.getNoth());
                            db.updateUser(update_user);
                            startActivity(intent3);
                        } else {
                            erroReg2.setText(getString(R.string.erro_datas_seguintes_maiores_anteriores));
                        }
                    } else {
                        boolean v1 = verificarDatas(di1, df1);
                        boolean v2 = verificarDatas(df1, di2);
                        boolean v3 = verificarDatas(di2, df2);
                        boolean v4 = verificarDatas(df2, di3);
                        boolean v5 = verificarDatas(di3, df3);
                        if (v1 && v2 && v3 && v4 && v5) {
                            String di3per = di1 + "-" + di2 + "-" + di3;
                            String df3per = df1 + "-" + df2 + "-" + df3;
                            User update_user = new User(1, user_select2.getPnome(), user_select2.getUnome(), numPer, di3per, df3per, user_select2.getSclassificacao(), user_select2.getTipo(), 0, user_select2.getNdisc(), user_select2.getNtur(), user_select2.getNca(), user_select2.getNpor(), user_select2.getNot(), user_select2.getNotd(), user_select2.getNoth());
                            db.updateUser(update_user);
                            startActivity(intent3);
                        } else {
                            erroReg2.setText(getString(R.string.erro_datas_seguintes_maiores_anteriores));
                        }
                    }
                }
            }
        });

        dinicio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(Registo2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mmonth, int mday) {
                        dinicio1.setText(Integer.toString(mday) + "/" + Integer.toString(mmonth + 1) + "/" + Integer.toString(myear));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        dinicio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(Registo2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mmonth, int mday) {
                        dinicio2.setText(Integer.toString(mday) + "/" + Integer.toString(mmonth + 1) + "/" + Integer.toString(myear));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        dinicio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(Registo2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mmonth, int mday) {
                        dinicio3.setText(Integer.toString(mday) + "/" + Integer.toString(mmonth + 1) + "/" + Integer.toString(myear));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        dfim1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(Registo2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mmonth, int mday) {
                        dfim1.setText(Integer.toString(mday) + "/" + Integer.toString(mmonth + 1) + "/" + Integer.toString(myear));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        dfim2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(Registo2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mmonth, int mday) {
                        dfim2.setText(Integer.toString(mday) + "/" + Integer.toString(mmonth + 1) + "/" + Integer.toString(myear));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        dfim3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(Registo2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mmonth, int mday) {
                        dfim3.setText(Integer.toString(mday) + "/" + Integer.toString(mmonth + 1) + "/" + Integer.toString(myear));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        numPer = Integer.parseInt(parent.getItemAtPosition(position).toString());

        periodo1 = findViewById(R.id.periodo1);
        periodo2 = findViewById(R.id.periodo2);
        periodo3 = findViewById(R.id.periodo3);
        if (numPer == 1) {
            periodo2.setVisibility(View.GONE);
            dinicio2.setText("");
            dfim2.setText("");
            periodo3.setVisibility(View.GONE);
            dinicio3.setText("");
            dfim3.setText("");
        } else if (numPer == 2) {
            periodo2.setVisibility(View.VISIBLE);
            periodo3.setVisibility(View.GONE);
            dinicio3.setText("");
            dfim3.setText("");
        } else {
            periodo2.setVisibility(View.VISIBLE);
            periodo3.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public boolean verificarDatas(String data1, String data2) {
        String[] data1a = data1.split("/");
        String[] data2a = data2.split("/");
        if (Integer.parseInt(data1a[2]) < Integer.parseInt(data2a[2])) {
            return true;
        } else if ((Integer.parseInt(data1a[2]) > Integer.parseInt(data2a[2]))) {
            return false;
        } else {
            if (Integer.parseInt(data1a[1]) < Integer.parseInt(data2a[1])) {
                return true;
            } else if (Integer.parseInt(data1a[1]) > Integer.parseInt(data2a[1])) {
                return false;
            } else {
                if (Integer.parseInt(data1a[0]) < Integer.parseInt(data2a[0])) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }
}
