package com.deadpixel.dpixel.a4student;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DListAdapter extends ArrayAdapter<Disciplina> implements View.OnClickListener {

    private ArrayList<Disciplina> listDisciplina;
    Context mContext;

    private static class ViewHolder {
        TextView txtNomeD;
        TextView txtCodD;
        ImageView del_dlist;
        ImageView edit_dlist;
    }

    public DListAdapter(ArrayList<Disciplina> data, Context context) {
        super(context, R.layout.row_dlist, data);
        this.listDisciplina = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        final BaseDados db = new BaseDados(mContext);
        final User user = db.selectUser(1);

        final int position = (Integer) v.getTag();
        Object object = getItem(position);
        Disciplina disciplina = (Disciplina) object;
        // Botões de Editar e Eliminar
        switch (v.getId()) {
            case R.id.del_dlist:
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(mContext.getString(R.string.titulo_eliminar_disciplina));
                builder.setMessage(mContext.getString(R.string.certeza_del_disciplina));
                builder.setPositiveButton(mContext.getString(R.string.sim), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Disciplina disciplina1 = db.selectDisciplinaByPosition(user.getNdisc(), position);
                        db.updateUser(new User(1, user.getPnome(), user.getUnome(), user.getNperiodos(), user.getIperiodos(), user.getFperiodos(), user.getSclassificacao(), user.getTipo(), user.getVer(), user.getNdisc() - 1, user.getNtur(), user.getNca(), user.getNpor(), user.getNot(), user.getNotd(), user.getNoth()));
                        db.deleteDisciplina(disciplina1);
                        Activity activity = (Activity) mContext;
                        TextView verReg3 = activity.findViewById(R.id.verReg3);
                        String txt = verReg3.getText() + " ";
                        verReg3.setText(txt);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton(mContext.getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.edit_dlist:
                Activity activity = (Activity) mContext;
                Disciplina disciplina1 = db.selectDisciplinaByPosition(user.getNdisc(), position);
                int ny = activity.getIntent().getIntExtra("NY", 2);
                Intent intent = new Intent(activity, EditDT.class);
                intent.putExtra("NY", ny);
                intent.putExtra("DTCOD", disciplina1.getDcod());
                intent.putExtra("DTID", disciplina1.getIdd());
                activity.startActivity(intent);
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Disciplina disciplina = getItem(position);
        ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_dlist, parent, false);
            viewHolder.txtNomeD = convertView.findViewById(R.id.txtNomeD);
            viewHolder.txtCodD = convertView.findViewById(R.id.txtCodD);
            viewHolder.del_dlist = convertView.findViewById(R.id.del_dlist);
            viewHolder.edit_dlist = convertView.findViewById(R.id.edit_dlist);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.txtNomeD.setText(disciplina.getDnome());
        viewHolder.txtCodD.setText(disciplina.getDcod());
        viewHolder.del_dlist.setOnClickListener(this);
        viewHolder.del_dlist.setTag(position);
        viewHolder.edit_dlist.setOnClickListener(this);
        viewHolder.edit_dlist.setTag(position);

        return convertView;
    }
}
