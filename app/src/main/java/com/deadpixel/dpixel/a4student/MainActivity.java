package com.deadpixel.dpixel.a4student;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView logo, empresa;

    BaseDados db = new BaseDados(this);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Verificação Primeiro Uso
        final String PREFS_NAME = "MyPrefsFile";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getBoolean("first_time", true)) {
            // Inserir linha na tabela User
            db.insertUser(new User("", "", 0, "", "", 0, "", 0, 0, 0, 0, 0, 0, "", ""));
            // Inserir linha na tabela de dados temporários
            db.insertTemp(new Temp("", "", "", 0, 0, 0, 0));
            // Inserir linhas na tabela Aulas
            for (int c = 0; c < 7; c++) {
                db.insertAulas(new Aulas(0, "", "", "", ""));
            }
            settings.edit().putBoolean("first_time", false).commit();
        }

        final User user = db.selectUser(1);

        logo = findViewById(R.id.logo);
        empresa = findViewById(R.id.empresa);

        Temp temp = db.selectTemp(1);
        CriterioAvaliacao criterioAvaliacao = db.selectCriterioAvaliacaoId(temp.getDti2());
        if (criterioAvaliacao.getIdca() != 0) {
            CriterioAvaliacao deleteCA = new CriterioAvaliacao();
            deleteCA.setIdca(temp.getDti2());
            db.deleteCriterioAvaliacao(deleteCA);
            User user1 = db.selectUser(1);
            db.updateUser(new User(1, user1.getPnome(), user1.getUnome(), user1.getNperiodos(), user1.getIperiodos(), user1.getFperiodos(), user1.getSclassificacao(), user1.getTipo(), user1.getVer(), user1.getNdisc(), user1.getNtur(), user1.getNca() - 1, user.getNpor(), user.getNot(), user.getNotd(), user.getNoth()));
        }

        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Fade in de imagens
                ObjectAnimator fadeIn_logo = ObjectAnimator.ofFloat(logo, View.ALPHA, 0.0f, 1.0f);
                fadeIn_logo.setDuration(1000);
                AnimatorSet animatorSet1 = new AnimatorSet();
                animatorSet1.play(fadeIn_logo);
                animatorSet1.start();
                ObjectAnimator fadeIn_empresa = ObjectAnimator.ofFloat(empresa, View.ALPHA, 0.0f, 1.0f);
                fadeIn_empresa.setDuration(300);
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.play(fadeIn_empresa);
                animatorSet2.start();
            }
        }, 500);
        /*// Fade in de imagens
        ObjectAnimator fadeIn_logo = ObjectAnimator.ofFloat(logo, View.ALPHA, 0.0f, 1.0f);
        fadeIn_logo.setDuration(1000);
        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.play(fadeIn_logo);
        animatorSet1.start();
        ObjectAnimator fadeIn_empresa = ObjectAnimator.ofFloat(empresa, View.ALPHA, 0.0f, 1.0f);
        fadeIn_empresa.setDuration(1000);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.play(fadeIn_empresa);
        animatorSet2.start();*/

        // 2 segundos de espera
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Estrutura condicional para ir para página de registo ou inicial
                if (user.getVer() == 0) {
                    Intent intent = new Intent(MainActivity.this, BoasVindas.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(MainActivity.this, PaginaInicial.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2500);
    }
}
