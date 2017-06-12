package com.experience.essteling.a3.esstelingexperience.UI;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.experience.essteling.a3.esstelingexperience.DataRetriever.DataHandler;
import com.experience.essteling.a3.esstelingexperience.DataRetriever.IDataListener;
import com.experience.essteling.a3.esstelingexperience.Entities.Attractie;
import com.experience.essteling.a3.esstelingexperience.Entities.MetingenData;
import com.experience.essteling.a3.esstelingexperience.Entities.SensorData;
import com.experience.essteling.a3.esstelingexperience.Helpers.MyThread;
import com.experience.essteling.a3.esstelingexperience.R;
import com.experience.essteling.a3.esstelingexperience.Entities.Data;
import com.experience.essteling.a3.esstelingexperience.Entities.AttractieData;
import com.experience.essteling.a3.esstelingexperience.Helpers.WifiConnection;

public class MeetMij extends AppCompatActivity {

    public Button btn_meet_mij_stop;
    public ProgressBar pb_meet_mij;
    public Button btn_meet_mij_start;
    public ImageView imageBackground1;
    public ImageView imageBackground2;
    public ImageView imageBackground3;
    private MyThread sensorData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_mij);
        final Attractie attractie = (Attractie) getIntent().getSerializableExtra("ATTRACTIE");

        imageBackground1 = (ImageView) findViewById(R.id.background1_meetmij);
        imageBackground2 = (ImageView) findViewById(R.id.background2_meetmij);
        imageBackground3 = (ImageView) findViewById(R.id.background3_meetmij);

        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = imageBackground1.getWidth() + 200;
                final float translationX = width * progress;
                imageBackground1.setTranslationX(translationX);
                imageBackground2.setTranslationX(translationX - width);
            }
        });


        setTitle("Meet mij");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_meet_mij_stop = (Button) findViewById(R.id.btn_meet_mij_stop);
        btn_meet_mij_stop.setEnabled(false);
        btn_meet_mij_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sensorData == null) return;

                SensorData data = sensorData.stop();

                Intent i = new Intent(getApplicationContext(), AttractieMetingSpec.class);
                Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(1000); // 5000 miliseconds = 5 seconds
                pb_meet_mij.setVisibility(View.VISIBLE);
                i.putExtra("ATTRACTIE1", attractie);
                i.putExtra("DATA", data);
                WifiConnection.Disconnect(getApplicationContext());

                startActivity(i);
            }
        });


        pb_meet_mij = (ProgressBar) findViewById(R.id.progressBar);
        btn_meet_mij_start = (Button) findViewById(R.id.btn_meet_mij_start);

        btn_meet_mij_start.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(1000); // 5000 miliseconds = 5 seconds
                animator.start();
                WifiConnection.Connect(getApplicationContext());

                // TODO: Dit in een task systeem
                while (!WifiConnection.isConnected(getApplication())) {
                    MyThread.sleep(250);
                }

                sensorData = new MyThread(attractie.getId());
                sensorData.start();

                btn_meet_mij_start.setEnabled(false);
                btn_meet_mij_stop.setEnabled(true);
                pb_meet_mij.setVisibility(View.VISIBLE);
            }
        });

        TextView tv = (TextView) findViewById(R.id.tv_meet_mij_attractie);
        tv.setText(String.valueOf(attractie.getNaam()));
    }
}
