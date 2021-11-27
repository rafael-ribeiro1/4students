package com.deadpixel.dpixel.a4student;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BoasVindas extends AppCompatActivity {

    TextView txt1, txt2;
    ImageView img;
    Button cont;

    BaseDados db = new BaseDados(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boas_vindas);

        User user = db.selectUser(1);
        if (user.getVer() == 1) {
            finish();
        }

        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        img = findViewById(R.id.img);
        cont = findViewById(R.id.cont);

        cont.setEnabled(false);

        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator fadeIn1 = ObjectAnimator.ofFloat(txt1, View.ALPHA, 0.0f, 1.0f);
                fadeIn1.setDuration(1000);
                AnimatorSet animatorSet1 = new AnimatorSet();
                animatorSet1.play(fadeIn1);
                animatorSet1.start();
                final Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ObjectAnimator fadeIn2 = ObjectAnimator.ofFloat(img, View.ALPHA, 0.0f, 1.0f);
                        fadeIn2.setDuration(1000);
                        AnimatorSet animatorSet1 = new AnimatorSet();
                        animatorSet1.play(fadeIn2);
                        animatorSet1.start();
                        final Handler handler3 = new Handler();
                        handler3.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ObjectAnimator fadeIn3 = ObjectAnimator.ofFloat(txt2, View.ALPHA, 0.0f, 1.0f);
                                fadeIn3.setDuration(1000);
                                AnimatorSet animatorSet1 = new AnimatorSet();
                                animatorSet1.play(fadeIn3);
                                animatorSet1.start();
                                final Handler handler4 = new Handler();
                                handler4.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        ObjectAnimator fadeIn4 = ObjectAnimator.ofFloat(cont, View.ALPHA, 0.0f, 1.0f);
                                        fadeIn4.setDuration(1000);
                                        AnimatorSet animatorSet1 = new AnimatorSet();
                                        animatorSet1.play(fadeIn4);
                                        animatorSet1.start();
                                        cont.setEnabled(true);
                                    }
                                }, 1000);
                            }
                        }, 1000);
                    }
                }, 1000);
            }
        }, 500);

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoasVindas.this, Registo.class);
                intent.putExtra("NY", 0);
                startActivity(intent);
                finish();
            }
        });
    }
}
