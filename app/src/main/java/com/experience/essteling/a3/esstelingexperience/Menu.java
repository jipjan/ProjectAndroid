package com.experience.essteling.a3.esstelingexperience;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {
    public Button btn_menu_meetmij;
    public Button btn_menu_metingen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btn_menu_meetmij = (Button) findViewById(R.id.btn_menu_meetmij);
        btn_menu_meetmij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Lijst_attractie.class);
                startActivity(i);
            }
        });

        btn_menu_metingen = (Button) findViewById(R.id.btn_menu_metingen);
        btn_menu_metingen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MetingenAchtbaan.class);
                startActivity(i);
            }
        });
    }
}
