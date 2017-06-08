package com.experience.essteling.a3.esstelingexperience.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.experience.essteling.a3.esstelingexperience.DataRetriever.DataHandler;
import com.experience.essteling.a3.esstelingexperience.DataRetriever.IDataListener;
import com.experience.essteling.a3.esstelingexperience.Entities.Attractie;
import com.experience.essteling.a3.esstelingexperience.Entities.SensorDataAttractie;
import com.experience.essteling.a3.esstelingexperience.R;
import com.experience.essteling.a3.esstelingexperience.Entities.SensorData;
import com.experience.essteling.a3.esstelingexperience.Entities.SensorDataList;
import com.experience.essteling.a3.esstelingexperience.Helpers.WifiConnection;

public class MeetMij extends AppCompatActivity {

    public Button btn_meet_mij_stop;
    public ProgressBar pb_meet_mij;
    public Button btn_meet_mij_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_mij);
        final Attractie attractie = (Attractie) getIntent().getSerializableExtra("ATTRACTIE");


        setTitle("Meet mij");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_meet_mij_stop = (Button) findViewById(R.id.btn_meet_mij_stop);
        btn_meet_mij_stop.setEnabled(false);
        btn_meet_mij_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AttractieMetingSpec.class);
                Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(1000); // 5000 miliseconds = 5 seconds
                pb_meet_mij.setVisibility(View.VISIBLE);
                i.putExtra("ATTRACTIE1", attractie);
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

                WifiConnection.Connect(getApplicationContext());

                // TODO: Dit in een task systeem
                while (!WifiConnection.isConnected(getApplication())) {
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // meten tot op stop gedrukt
                // 2x per seconde
                // opslaan in een nieuwe lijst die onderdeel wordt van de sensordataattractie


                SensorDataList list = SensorDataAttractie.ITEMS.getListOrNew()

                DataHandler d = new DataHandler(list, new IDataListener() {
                    @Override
                    public void onFinish(SensorData meting) {
                        System.out.println("Something went right!");
                    }
                    @Override
                    public void onError() {
                        System.out.println("Errors everywhere!");
                    }
                });
                d.execute();
                btn_meet_mij_start.setEnabled(false);
                btn_meet_mij_stop.setEnabled(true);
                pb_meet_mij.setVisibility(View.VISIBLE);
            }
        });

        TextView tv = (TextView) findViewById(R.id.tv_meet_mij_attractie);
        tv.setText(String.valueOf(attractie.getNaam()));
    }
}
