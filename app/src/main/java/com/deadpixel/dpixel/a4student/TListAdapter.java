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

public class TListAdapter extends ArrayAdapter<Turma> implements View.OnClickListener {

    private ArrayList<Turma> listTurma;
    Context mContext;

    private static class ViewHolder {
        TextView txtNomeT;
        TextView txtCodT;
        ImageView del_tlist;
        ImageView edit_tlist;
    }

    public TListAdapter(ArrayList<Turma> data, Context context) {
        super(context, R.layout.row_tlist, data);
        this.listTurma = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        final BaseDados db = new BaseDados(mContext);
        final User user = db.selectUser(1);

        final int position = (Integer) v.getTag();
        Object object = getItem(position);
        Turma turma = (Turma) object;
        // Botões de Editar e Eliminar
        switch (v.getId()) {
            case R.id.del_tlist:
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(mContext.getString(R.string.titulo_eliminar_turma));
                builder.setMessage(mContext.getString(R.string.certeza_del_turma));
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Turma turma1 = db.selectTurmaByPosition(user.getNtur(), position);
                        db.updateUser(new User(1, user.getPnome(), user.getUnome(), user.getNperiodos(), user.getIperiodos(), user.getFperiodos(), user.getSclassificacao(), user.getTipo(), user.getVer(), user.getNdisc(), user.getNtur() - 1, user.getNca(), user.getNpor(), user.getNot(), user.getNotd(), user.getNoth()));
                        db.deleteTurma(turma1);
                        Activity activity = (Activity) mContext;
                        TextView verReg3 = activity.findViewById(R.id.verReg3);
                        String txt = verReg3.getText() + " ";
                        verReg3.setText(txt);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.edit_tlist:
                Activity activity = (Activity) mContext;
                Turma turma1 = db.selectTurmaByPosition(user.getNtur(), position);
                int ny = activity.getIntent().getIntExtra("NY", 2);
                Intent intent = new Intent(activity, EditDT.class);
                intent.putExtra("NY", ny);
                intent.putExtra("DTCOD", turma1.getTcod());
                intent.putExtra("DTID", turma1.getIdt());
                activity.startActivity(intent);
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Turma turma = getItem(position);
        ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_tlist, parent, false);
            viewHolder.txtNomeT = convertView.findViewById(R.id.txtNomeT);
            viewHolder.txtCodT = convertView.findViewById(R.id.txtCodT);
            viewHolder.del_tlist = convertView.findViewById(R.id.del_tlist);
            viewHolder.edit_tlist = convertView.findViewById(R.id.edit_tlist);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.txtNomeT.setText(turma.getTnome());
        viewHolder.txtCodT.setText(turma.getTcod());
        viewHolder.del_tlist.setOnClickListener(this);
        viewHolder.del_tlist.setTag(position);
        viewHolder.edit_tlist.setOnClickListener(this);
        viewHolder.edit_tlist.setTag(position);

        return convertView;
    }
}
