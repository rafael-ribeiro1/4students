package com.deadpixel.dpixel.a4student;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class OptionPorDialog {

    public void showDialog(final Activity activity, final int position) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.option_ca_dialog);

        final BaseDados db = new BaseDados(activity);

        Button btnEditarPara = dialog.findViewById(R.id.btnEditarPara);
        Button btnDelPara = dialog.findViewById(R.id.btnDelPara);

        btnEditarPara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                EditNomePor dialog = new EditNomePor();
                dialog.showDialog(activity, position);
            }
        });

        btnDelPara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(activity.getString(R.string.eliminar_portfolio));
                builder.setMessage(activity.getString(R.string.certeza_del_por));
                builder.setPositiveButton(activity.getString(R.string.sim), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        User user = db.selectUser(1);
                        db.updateUser(new User(1, user.getPnome(), user.getUnome(), user.getNperiodos(), user.getIperiodos(), user.getFperiodos(), user.getSclassificacao(), user.getTipo(), user.getVer(), user.getNdisc(), user.getNtur(), user.getNca(), user.getNpor() - 1, user.getNot(), user.getNotd(), user.getNoth()));
                        Portfolio portfolio = db.selectPortfolioByPosition(user.getNpor(), position);
                        db.deletePortfolio(portfolio);
                        File dir = new File(activity.getFilesDir() + File.separator + Integer.toString(portfolio.getIdp()));
                        if (dir.isDirectory()) {
                            String[] children = dir.list();
                            for (int i = 0; i < children.length; i++) {
                                new File(dir, children[i]).delete();
                            }
                        }
                        dialog.dismiss();
                        TextView verPor = activity.findViewById(R.id.verPor);
                        String txt = verPor.getText() + " ";
                        verPor.setText(txt);
                    }
                });
                builder.setNegativeButton(activity.getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog1 = builder.create();
                dialog1.show();
            }
        });

        dialog.show();
    }
}
