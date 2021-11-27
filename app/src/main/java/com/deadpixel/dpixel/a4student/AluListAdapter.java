package com.deadpixel.dpixel.a4student;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AluListAdapter extends ArrayAdapter<Aluno> {

    private ArrayList<Aluno> listAluno;
    Context mContext;
    int nper;

    private static class ViewHolder {
        TextView txtAluNum;
        TextView txtAluNome;
        TextView anot1per;
        TextView anot2per;
        TextView anot3per;
    }

    public AluListAdapter(ArrayList<Aluno> data, Context context, int nper) {
        super(context, R.layout.row_alulist, data);
        this.listAluno = data;
        this.mContext = context;
        this.nper = nper;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aluno aluno = getItem(position);
        AluListAdapter.ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new AluListAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_alulist, parent, false);
            viewHolder.txtAluNum = convertView.findViewById(R.id.txtAluNum);
            viewHolder.txtAluNome = convertView.findViewById(R.id.txtAluNome);
            viewHolder.anot1per = convertView.findViewById(R.id.anot1per);
            viewHolder.anot2per = convertView.findViewById(R.id.anot2per);
            viewHolder.anot3per = convertView.findViewById(R.id.anot3per);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.txtAluNum.setText(Integer.toString(aluno.getAnum()));
        viewHolder.txtAluNome.setText(aluno.getAnome());
        Activity activity = (Activity) mContext;
        if (nper == 1) {
            if (aluno.getAva1() == 1) {
                viewHolder.anot1per.setText(Float.toString(aluno.getNota1per()));
            } else {
                viewHolder.anot1per.setText(activity.getString(R.string.n_a));
            }
        } else if (nper == 2) {
            if (aluno.getAva1() == 1) {
                viewHolder.anot1per.setText(Float.toString(aluno.getNota1per()));
            } else {
                viewHolder.anot1per.setText(activity.getString(R.string.n_a));
            }
            if (aluno.getAva2() == 1) {
                viewHolder.anot2per.setText(Float.toString(aluno.getNota2per()));
            }else {
                viewHolder.anot2per.setText(activity.getString(R.string.n_a));
            }
        } else {
            if (aluno.getAva1() == 1) {
                viewHolder.anot1per.setText(Float.toString(aluno.getNota1per()));
            } else {
                viewHolder.anot1per.setText(activity.getString(R.string.n_a));
            }
            if (aluno.getAva2() == 1) {
                viewHolder.anot2per.setText(Float.toString(aluno.getNota2per()));
            } else {
                viewHolder.anot2per.setText(activity.getString(R.string.n_a));
            }
            if (aluno.getAva3() == 1) {
                viewHolder.anot3per.setText(Float.toString(aluno.getNota3per()));
            } else {
                viewHolder.anot3per.setText(activity.getString(R.string.n_a));
            }
        }

        return convertView;
    }
}
