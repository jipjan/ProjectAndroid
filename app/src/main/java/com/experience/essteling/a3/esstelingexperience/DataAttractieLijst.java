package com.experience.essteling.a3.esstelingexperience;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DataAttractieLijst extends AppCompatActivity {

    public Button btn_metingen_achtbaan_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_attractie_lijst);

        btn_metingen_achtbaan_menu = (Button) findViewById(R.id.btn_data_attractie_lijst_menu);
        btn_metingen_achtbaan_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    Intent i = new Intent(getApplicationContext(), .class);
            //    startActivity(i);
            }
        });
    }
}
