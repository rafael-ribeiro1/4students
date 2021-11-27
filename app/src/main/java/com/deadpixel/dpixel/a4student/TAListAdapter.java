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

public class TAListAdapter extends ArrayAdapter<Turma> implements View.OnClickListener {

    private ArrayList<Turma> listTurma;
    Context mContext;

    private static class ViewHolder {
        TextView txtNomeT;
        TextView txtCodT;
        ImageView del_tlist;
        ImageView edit_tlist;
    }

    public TAListAdapter(ArrayList<Turma> data, Context context) {
        super(context, R.layout.row_talist, data);
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
                        for (int dia = 1; dia < 7; dia++) {
                            deleteAula(dia, Integer.toString(turma1.getIdt()), mContext);
                        }
                        db.deleteTableAluno("abcde" + turma1.getTcod() + "edcba");
                        Activity activity = (Activity) mContext;
                        TextView verAva = activity.findViewById(R.id.verAva);
                        String txt = verAva.getText() + " ";
                        verAva.setText(txt);
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
                Intent intent = new Intent(activity, EditDT.class);
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
        TAListAdapter.ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new TAListAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_talist, parent, false);
            viewHolder.txtNomeT = convertView.findViewById(R.id.txtNomeT);
            viewHolder.txtCodT = convertView.findViewById(R.id.txtCodT);
            viewHolder.del_tlist = convertView.findViewById(R.id.del_tlist);
            viewHolder.edit_tlist = convertView.findViewById(R.id.edit_tlist);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TAListAdapter.ViewHolder) convertView.getTag();
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

    public void deleteAula(int dia, String id, Context mContext) {
        BaseDados db = new BaseDados(mContext);
        Aulas aulas = db.selectAulas(dia);
        String[] aulasid = aulas.getAulaid().split("/0/1/");
        String[] horasIni = aulas.getIniaula().split("/0/1/");
        String[] horasFim = aulas.getFimaula().split("/0/1/");
        String[] salas = aulas.getSalaa().split("/0/1/");
        int nid = 0;
        for (int c = 0; c < aulasid.length; c++) {
            if (aulasid[c].equals(id)) {
                nid++;
            }
        }
        if (nid > 1) {
            String[] aulasid2 = new String[aulas.getNaulas() - nid];
            String[] horasIni2 = new String[aulas.getNaulas() - nid];
            String[] horasFim2 = new String[aulas.getNaulas() - nid];
            String[] salas2 = new String[aulas.getNaulas() - nid];
            int temp = 0;
            for (int i = 0; i < aulasid2.length; i++) {
                while (aulasid[temp].equals(id)){
                    temp++;
                }
                if (temp < aulasid.length) {
                    aulasid2[i] = aulasid[temp];
                    horasIni2[i] = horasIni[temp];
                    horasFim2[i] = horasFim[temp];
                    salas2[i] = salas[temp];
                    temp++;
                }
            }
            String aulasids="", horasInis="", horasFims="", salass="";
            for (int c = 0; c < aulas.getNaulas() - nid; c++) {
                if (c == 0) {
                    aulasids += aulasid2[c];
                    horasInis += horasIni2[c];
                    horasFims += horasFim2[c];
                    salass += salas2[c];
                } else {
                    aulasids += "/0/1/" + aulasid2[c];
                    horasInis += "/0/1/" + horasIni2[c];
                    horasFims += "/0/1/" + horasFim2[c];
                    salass += "/0/1/" + salas2[c];
                }
            }
            db.updateAulas(new Aulas(aulas.getIdaulas(), aulas.getNaulas() - nid, aulasids, horasInis, horasFims, salass));
        } else if (nid == 1) {
            if (aulasid.length == 1) {
                db.updateAulas(new Aulas(aulas.getIdaulas(), 0, "", "", "", ""));
            } else {
                String[] aulasid2 = new String[aulas.getNaulas() - 1];
                String[] horasIni2 = new String[aulas.getNaulas() - 1];
                String[] horasFim2 = new String[aulas.getNaulas() - 1];
                String[] salas2 = new String[aulas.getNaulas() - 1];
                boolean verp = false;
                for (int c = 0; c < aulas.getNaulas()-1; c++) {
                    if (!verp) {
                        if (aulasid[c].equals(id)) {
                            aulasid2[c] = aulasid[c+1];
                            horasIni2[c] = horasIni[c+1];
                            horasFim2[c] = horasFim[c+1];
                            salas2[c] = salas[c+1];
                            verp = true;
                        } else {
                            aulasid2[c] = aulasid[c];
                            horasIni2[c] = horasIni[c];
                            horasFim2[c] = horasFim[c];
                            salas2[c] = salas[c];
                        }
                    } else {
                        aulasid2[c] = aulasid[c+1];
                        horasIni2[c] = horasIni[c+1];
                        horasFim2[c] = horasFim[c+1];
                        salas2[c] = salas[c+1];
                    }
                }
                String aulasids="", horasInis="", horasFims="", salass="";
                for (int c = 0; c < aulas.getNaulas() - 1; c++) {
                    if (c == 0) {
                        aulasids += aulasid2[c];
                        horasInis += horasIni2[c];
                        horasFims += horasFim2[c];
                        salass += salas2[c];
                    } else {
                        aulasids += "/0/1/" + aulasid2[c];
                        horasInis += "/0/1/" + horasIni2[c];
                        horasFims += "/0/1/" + horasFim2[c];
                        salass += "/0/1/" + salas2[c];
                    }
                }
                db.updateAulas(new Aulas(aulas.getIdaulas(), aulas.getNaulas() - 1, aulasids, horasInis, horasFims, salass));
            }
        }
    }
}
