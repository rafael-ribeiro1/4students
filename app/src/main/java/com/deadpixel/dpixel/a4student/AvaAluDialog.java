package com.deadpixel.dpixel.a4student;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class AvaAluDialog {

    SAListAdapter adapter;
    private static ArrayList<Aluno> arrayList;

    public void showDialog(final Activity activity, final String cod, final int nper) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.ava_alu_dialog);

        final BaseDados db = new BaseDados(activity);

        final String TABELA_ALUNO = "abcde" + cod + "edcba";
        final Turma turma = db.selectTurma(cod);

        ListView listSelAlu = dialog.findViewById(R.id.listSelAlu);

        arrayList = db.listAlunos(TABELA_ALUNO);
        adapter = new SAListAdapter(arrayList, activity, nper);
        listSelAlu.setAdapter(adapter);

        listSelAlu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aluno aluno = db.selectAlunoByPosition(TABELA_ALUNO, turma.getTnalu(), position);
                Intent intent = new Intent(activity, Ava.class);
                intent.putExtra("COD", cod);
                intent.putExtra("NPER", nper);
                intent.putExtra("NUMALU", aluno.getAnum());
                dialog.dismiss();
                activity.startActivity(intent);
                activity.finish();

            }
        });

        dialog.show();
    }
}
