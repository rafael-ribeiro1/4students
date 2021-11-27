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
import android.widget.Toast;

import java.util.ArrayList;

public class DAListAdapter extends ArrayAdapter<Disciplina> implements View.OnClickListener {

    private ArrayList<Disciplina> listDisciplina;
    Context mContext;

    private static class ViewHolder {
        TextView txtNomeD;
        TextView txtCodD;
        ImageView del_dlist;
        ImageView edit_dlist;
    }

    public DAListAdapter(ArrayList<Disciplina> data, Context context) {
        super(context, R.layout.row_dalist, data);
        this.listDisciplina = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        final BaseDados db = new BaseDados(mContext);
        final User user = db.selectUser(1);

        final int position = (Integer) v.getTag();
        Object object = getItem(position);
        final Disciplina disciplina = (Disciplina) object;
        // Botões de Editar e Eliminar
        switch (v.getId()) {
            case R.id.del_dlist:
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(mContext.getString(R.string.titulo_eliminar_disciplina));
                builder.setMessage(mContext.getString(R.string.certeza_del_disciplina));
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Disciplina disciplina1 = db.selectDisciplinaByPosition(user.getNdisc(), position);
                        db.updateUser(new User(1, user.getPnome(), user.getUnome(), user.getNperiodos(), user.getIperiodos(), user.getFperiodos(), user.getSclassificacao(), user.getTipo(), user.getVer(), user.getNdisc() - 1, user.getNtur(), user.getNca(), user.getNpor(), user.getNot(), user.getNotd(), user.getNoth()));
                        db.deleteDisciplina(disciplina1);
                        for (int dia = 1; dia < 7; dia++) {
                            deleteAula(dia, Integer.toString(disciplina1.getIdd()), mContext);
                        }
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
            case R.id.edit_dlist:
                Activity activity = (Activity) mContext;
                Disciplina disciplina1 = db.selectDisciplinaByPosition(user.getNdisc(), position);
                Intent intent = new Intent(activity, EditDT.class);
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
        DAListAdapter.ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new DAListAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_dalist, parent, false);
            viewHolder.txtNomeD = convertView.findViewById(R.id.txtNomeD);
            viewHolder.txtCodD = convertView.findViewById(R.id.txtCodD);
            viewHolder.del_dlist = convertView.findViewById(R.id.del_dlist);
            viewHolder.edit_dlist = convertView.findViewById(R.id.edit_dlist);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (DAListAdapter.ViewHolder) convertView.getTag();
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
