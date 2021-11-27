package com.deadpixel.dpixel.a4student;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EListAdapter extends ArrayAdapter<Evento> {

    private ArrayList<Evento> listEventos;
    Context mContext;
    private int tList;

    private static class ViewHolder {
        ImageView barra;
        TextView titEve;
        TextView horasEve;
    }

    public EListAdapter(ArrayList<Evento> data, Context context, int tList) {
        super(context, R.layout.row_elist, data);
        this.listEventos = data;
        this.mContext = context;
        this.tList = tList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Evento evento = getItem(position);
        ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_elist, parent, false);
            viewHolder.barra = convertView.findViewById(R.id.barra);
            viewHolder.titEve = convertView.findViewById(R.id.titEve);
            viewHolder.horasEve = convertView.findViewById(R.id.horasEve);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        if (Integer.parseInt(evento.getTipoe()) == 1) {
            viewHolder.barra.setBackgroundResource(R.drawable.elist);
        } else {
            viewHolder.barra.setBackgroundResource(R.drawable.elist2);
        }
        viewHolder.titEve.setText(evento.getTit());
        if (tList == 1) {
            Activity activity = (Activity) getContext();
            String txt = activity.getString(R.string.as2) + " " + evento.getHoras();
            viewHolder.horasEve.setText(txt);
        } else {
            Activity activity = (Activity) getContext();
            String dias = evento.getDia().substring(0, 2) + "/" + evento.getDia().substring(2, 4) + "/" + evento.getDia().substring(4);
            String txt = dias + " " + activity.getString(R.string.as3) + " " + evento.getHoras();
            viewHolder.horasEve.setText(txt);
        }

        return convertView;
    }
}
