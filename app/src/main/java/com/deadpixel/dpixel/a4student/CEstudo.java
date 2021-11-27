package com.deadpixel.dpixel.a4student;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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

import java.util.ArrayList;

public class CEstudo extends AppCompatActivity {

    BaseDados db = new BaseDados(this);

    TextView txtCEstudo, verPor;
    ConstraintLayout clpor;
    ListView listPor;

    ArrayList<Portfolio> arrayList;
    private static PListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cestudo);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        FloatingActionButton fabAddPor = findViewById(R.id.fabAddPor);
        fabAddPor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPorDialog dialog = new AddPorDialog();
                dialog.showDialog(CEstudo.this);
            }
        });

        txtCEstudo = findViewById(R.id.txtCEstudo);
        clpor = findViewById(R.id.clpor);
        listPor = findViewById(R.id.listPor);
        verPor = findViewById(R.id.verPor);

        final User user = db.selectUser(1);

        if (user.getNpor() != 0) {
            clpor.setVisibility(View.GONE);
            listPortfolio();
        } else {
            clpor.setVisibility(View.VISIBLE);
        }

        if (user.getTipo().equals("prof")) {
            txtCEstudo.setText(getString(R.string.txt_cestudo_prof));
        } else {
            txtCEstudo.setText(getString(R.string.txt_cestudo_aluno));
        }

        listPor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Portfolio portfolio = db.selectPortfolioByPosition(user.getNpor(), position);
                Intent intent = new Intent(CEstudo.this, Por.class);
                intent.putExtra("NOMEP", portfolio.getNomep());
                startActivity(intent);
                finish();
            }
        });
        listPor.setLongClickable(true);
        listPor.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                OptionPorDialog dialog = new OptionPorDialog();
                dialog.showDialog(CEstudo.this, position);
                return true;
            }
        });

        verPor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                User user1 = db.selectUser(1);
                if (user1.getNpor() != 0) {
                    clpor.setVisibility(View.GONE);
                    listPortfolio();
                } else {
                    clpor.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            Intent intent = new Intent(CEstudo.this, PaginaInicial.class);
            startActivity(intent);
            finishAffinity();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CEstudo.this, PaginaInicial.class);
        startActivity(intent);
        finishAffinity();
    }

    public void listPortfolio() {
        arrayList = db.portfolioList();
        adapter = new PListAdapter(arrayList, CEstudo.this);
        listPor.setAdapter(adapter);
    }
}
