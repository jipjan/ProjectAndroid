package com.experience.essteling.a3.esstelingexperience;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MeetMij extends AppCompatActivity {

    public Button btn_meet_mij_menu;
    public Button btn_meet_mij_stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_mij);


        btn_meet_mij_stop = (Button) findViewById(R.id.btn_meet_mij_stop);
        btn_meet_mij_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),AttractieMetingSpec.class);
                startActivity(i);
            }
        });
    }
}
