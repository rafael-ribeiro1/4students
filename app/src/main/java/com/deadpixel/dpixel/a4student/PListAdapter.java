package com.deadpixel.dpixel.a4student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PListAdapter extends ArrayAdapter<Portfolio> {

    private ArrayList<Portfolio> listPortfolio;
    Context mContext;

    private static class ViewHolder {
        TextView txtPorNome;
        TextView txtNfiles;
    }

    public PListAdapter(ArrayList<Portfolio> data, Context context) {
        super(context, R.layout.row_plist, data);
        this.listPortfolio = data;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Portfolio portfolio = getItem(position);
        PListAdapter.ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new PListAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_plist, parent, false);
            viewHolder.txtPorNome = convertView.findViewById(R.id.txtPorNome);
            viewHolder.txtNfiles = convertView.findViewById(R.id.txtNfiles);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.txtPorNome.setText(portfolio.getNomep());
        viewHolder.txtNfiles.setText(Integer.toString(portfolio.getNfiles()));

        return convertView;
    }
}
