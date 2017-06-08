package com.experience.essteling.a3.esstelingexperience.UI;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.experience.essteling.a3.esstelingexperience.R;

public class Menu extends AppCompatActivity {

    public Button btn_menu_meetmij;
    public Button btn_menu_metingen;
    public ImageView imageBackground1;
    public ImageView imageBackground2;
    public ImageView imageBackground3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        imageBackground1 = (ImageView) findViewById(R.id.background1);
        imageBackground2 = (ImageView) findViewById(R.id.background2);
        imageBackground3 = (ImageView) findViewById(R.id.background3);

        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = imageBackground1.getWidth() + 200;
                final float hight = imageBackground1.getHeight();
                final float translationX = width * progress;
                final float translationY = hight * progress;
                int angle = (int)Math.toDegrees(Math.sin(hight/width));
                imageBackground1.setRotation(angle);
                imageBackground2.setRotation(angle);
                imageBackground3.setRotation(angle);
                imageBackground1.setTranslationX(translationX);
                imageBackground1.setTranslationY(translationY);
                imageBackground2.setTranslationX(translationX - width);
                imageBackground2.setTranslationY(translationY - hight);
            }
        });
        animator.start();

        btn_menu_meetmij = (Button) findViewById(R.id.btn_menu_meetmij);
        btn_menu_meetmij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MeetMijLijst.class);
                i.putExtra("FROM", "meetmij");
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
