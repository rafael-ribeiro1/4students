package com.deadpixel.dpixel.a4student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;

public class SAListAdapter extends ArrayAdapter<Aluno> {

    private ArrayList<Aluno> listAluno;
    Context mContext;
    int nper;

    private static class ViewHolder {
        RelativeLayout rlrow;
        TextView AluNum;
        TextView AluNome;
    }

    public SAListAdapter(ArrayList<Aluno> data, Context context, int nper) {
        super(context, R.layout.row_salist, data);
        this.listAluno = data;
        this.mContext = context;
        this.nper = nper;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aluno aluno = getItem(position);
        ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_salist, parent, false);
            viewHolder.rlrow = convertView.findViewById(R.id.rlrow);
            viewHolder.AluNum = convertView.findViewById(R.id.AluNum);
            viewHolder.AluNome = convertView.findViewById(R.id.AluNome);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.AluNum.setText(Integer.toString(aluno.getAnum()));
        viewHolder.AluNome.setText(aluno.getAnome());
        if (nper == 1) {
            if (aluno.getAva1() == 1) {
                viewHolder.rlrow.setBackgroundColor(0x8f99cc00);
            } else {
                viewHolder.rlrow.setBackgroundColor(0x8fff4444);
            }
        } else if (nper == 2) {
            if (aluno.getAva2() == 1) {
                viewHolder.rlrow.setBackgroundColor(0x8f99cc00);
            } else {
                viewHolder.rlrow.setBackgroundColor(0x8fff4444);
            }
        } else {
            if (aluno.getAva3() == 1) {
                viewHolder.rlrow.setBackgroundColor(0x8f99cc00);
            } else {
                viewHolder.rlrow.setBackgroundColor(0x8fff4444);
            }
        }

        return convertView;
    }
}
