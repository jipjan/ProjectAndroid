package com.experience.essteling.a3.esstelingexperience;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

public class Menu extends AppCompatActivity {

    public Button btn_menu_meetmij;
    public Button btn_menu_metingen;
    public ImageView imageBackground1;
    public ImageView imageBackground2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        imageBackground1 = (ImageView) findViewById(R.id.background1);
        imageBackground2 = (ImageView) findViewById(R.id.background2);;

        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(40000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float height = imageBackground1.getHeight() - 200;
                final float translationY = height * progress;
                imageBackground1.setTranslationY(translationY);
                imageBackground2.setTranslationY(translationY - height);
            }
        });
        animator.start();

        btn_menu_meetmij = (Button) findViewById(R.id.btn_menu_meetmij);
        btn_menu_meetmij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MeetMijLijst.class);
                startActivity(i);
            }
        });

        btn_menu_metingen = (Button) findViewById(R.id.btn_menu_metingen);
        btn_menu_metingen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MetingenLijst.class);
                startActivity(i);
            }
        });
    }
}
