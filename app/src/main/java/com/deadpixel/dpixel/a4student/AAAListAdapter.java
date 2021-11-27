package com.deadpixel.dpixel.a4student;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AAAListAdapter extends ArrayAdapter<Aula> {

    private ArrayList<Aula> listAulas;
    Context mContext;

    private static class ViewHolder {
        TextView diaDT;
        TextView horasaula;
    }

    public AAAListAdapter(ArrayList<Aula> data, Context context) {
        super(context, R.layout.row_aaalist, data);
        this.listAulas = data;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aula aula = getItem(position);
        AAAListAdapter.ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new AAAListAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_aaalist, parent, false);
            viewHolder.diaDT = convertView.findViewById(R.id.diaDT);
            viewHolder.horasaula = convertView.findViewById(R.id.horasaula);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AAAListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        Activity activity = (Activity) getContext();
        String dia = "";
        switch (aula.getDia()) {
            case 1:
                dia = activity.getString(R.string.segunda_feira);
                break;
            case 2:
                dia = activity.getString(R.string.terca_feira);
                break;
            case 3:
                dia = activity.getString(R.string.quarta_feira);
                break;
            case 4:
                dia = activity.getString(R.string.quinta_feira);
                break;
            case 5:
                dia = activity.getString(R.string.sexta_feira);
                break;
            case 6:
                dia = activity.getString(R.string.s_bado);
                break;
        }
        viewHolder.diaDT.setText(dia);
        String txt = activity.getString(R.string.das) + " " + aula.getHoraIni() + " " + activity.getString(R.string.as) + " " + aula.getHoraFim();
        viewHolder.horasaula.setText(txt);

        return convertView;
    }
}
