package com.deadpixel.dpixel.a4student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AListAdapter extends ArrayAdapter<Aula> {

    private ArrayList<Aula> listAulas;
    Context mContext;

    private static class ViewHolder {
        TextView AulaCod;
        TextView horaIni;
        TextView horaFim;
        TextView salaAula;
    }

    public AListAdapter(ArrayList<Aula> data, Context context) {
        super(context, R.layout.row_alist, data);
        this.listAulas = data;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aula aula = getItem(position);
        ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_alist, parent, false);
            viewHolder.AulaCod = convertView.findViewById(R.id.AulaCod);
            viewHolder.horaIni = convertView.findViewById(R.id.horaIni);
            viewHolder.horaFim = convertView.findViewById(R.id.horaFim);
            viewHolder.salaAula = convertView.findViewById(R.id.salaAula);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.AulaCod.setText(aula.getCod());
        viewHolder.horaIni.setText(aula.getHoraIni());
        viewHolder.horaFim.setText(aula.getHoraFim());
        viewHolder.salaAula.setText(aula.getSala());

        return convertView;
    }
}
