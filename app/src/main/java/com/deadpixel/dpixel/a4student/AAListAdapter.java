package com.deadpixel.dpixel.a4student;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AAListAdapter extends ArrayAdapter<Aula> {

    private ArrayList<Aula> listAulas;
    Context mContext;

    private static class ViewHolder {
        TextView nomeDT;
        TextView horasaula;
        TextView salaaula;
    }

    public AAListAdapter(ArrayList<Aula> data, Context context) {
        super(context, R.layout.row_aalist, data);
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
            convertView = inflater.inflate(R.layout.row_aalist, parent, false);
            viewHolder.nomeDT = convertView.findViewById(R.id.nomeDT);
            viewHolder.horasaula = convertView.findViewById(R.id.horasaula);
            viewHolder.salaaula = convertView.findViewById(R.id.salaaula);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.nomeDT.setText(aula.getCod());
        Activity activity = (Activity) getContext();
        String txt = activity.getString(R.string.das) + " " + aula.getHoraIni() + " " + activity.getString(R.string.as) + " " + aula.getHoraFim();
        viewHolder.horasaula.setText(txt);
        viewHolder.salaaula.setText(aula.getSala());

        return convertView;
    }
}
