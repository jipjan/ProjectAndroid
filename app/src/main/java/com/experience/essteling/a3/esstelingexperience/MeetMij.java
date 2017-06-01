package com.experience.essteling.a3.esstelingexperience;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

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
        btn_meet_mij_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AttractieMetingSpec.class);

                i.putExtra("ATTRACTIE1", attractie);

                startActivity(i);
            }
        });


        pb_meet_mij = (ProgressBar) findViewById(R.id.progressBar);
        btn_meet_mij_start = (Button) findViewById(R.id.btn_meet_mij_start);
        btn_meet_mij_start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                pb_meet_mij.setVisibility(View.VISIBLE);

            }
        });

        TextView tv = (TextView) findViewById(R.id.tv_meet_mij_attractie);
        tv.setText(String.valueOf(attractie.getNaam()));
    }
}
