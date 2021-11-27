package com.deadpixel.dpixel.a4student;

import android.app.Activity;
import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class DadUserDialog implements AdapterView.OnItemSelectedListener {

    boolean e1=false, e2=false;
    int numsc=1;

    public void showDialog(final Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dad_user_dialog);

        final BaseDados db = new BaseDados(activity);

        final TextView tvpnome = dialog.findViewById(R.id.tvpnome);
        final EditText p_nome = dialog.findViewById(R.id.p_nome);
        final TextView tvunome = dialog.findViewById(R.id.tvunome);
        final EditText u_nome = dialog.findViewById(R.id.u_nome);
        final TextView erro = dialog.findViewById(R.id.erro);
        LinearLayout llsc = dialog.findViewById(R.id.llsc);
        Spinner scspinner = dialog.findViewById(R.id.scspinner);
        Button btnSalDU = dialog.findViewById(R.id.btnSalDU);

        final User user = db.selectUser(1);

        if (user.getTipo().equals("prof")) {
            llsc.setVisibility(View.GONE);
        } else {
            llsc.setVisibility(View.VISIBLE);
        }

        p_nome.setText(user.getPnome());
        u_nome.setText(user.getUnome());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity, R.array.sclassificacao, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scspinner.setAdapter(adapter);
        if (user.getSclassificacao() != 0) {
            scspinner.setSelection(user.getSclassificacao() - 1);
        }
        scspinner.setOnItemSelectedListener(this);

        btnSalDU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((String.valueOf(p_nome.getText())).trim().equals("") || (String.valueOf(u_nome.getText())).trim().equals("")) {
                    erro.setText(activity.getString(R.string.preencher_todos_os_campos));
                    if ((String.valueOf(p_nome.getText())).trim().equals("")) {
                        tvpnome.setText(activity.getString(R.string.erro_primeiro_nome));
                        tvpnome.setTextColor(0xffff4444);
                        e1 = true;
                    }
                    if ((String.valueOf(u_nome.getText())).trim().equals("")) {
                        tvunome.setText(activity.getString(R.string.erro_ultimo_nome));
                        tvunome.setTextColor(0xffff4444);
                        e2 = true;
                    }
                } else {
                    int sc;
                    if (user.getTipo().equals("prof")) {
                        sc = 0;
                    } else {
                        sc = numsc;
                    }
                    db.updateUser(new User(1, p_nome.getText().toString(), u_nome.getText().toString(), user.getNperiodos(), user.getIperiodos(), user.getFperiodos(), sc, user.getTipo(), user.getVer(), user.getNdisc(), user.getNtur(), user.getNca(), user.getNpor(), user.getNot(), user.getNotd(), user.getNoth()));
                    dialog.dismiss();
                }
            }
        });

        p_nome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (e1) {
                    tvpnome.setText(activity.getString(R.string.primeiro_nome));
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
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (e2) {
                    tvunome.setText(activity.getString(R.string.ltimo_nome));
                    tvunome.setTextColor(0xff000000);
                    e2 = false;
                }
                if (!e1 && !e2) {
                    erro.setText("");
                }
            }
        });

        dialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        numsc = position + 1;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
