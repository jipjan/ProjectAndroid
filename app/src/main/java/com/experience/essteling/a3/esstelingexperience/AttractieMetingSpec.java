package com.experience.essteling.a3.esstelingexperience;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AttractieMetingSpec extends AppCompatActivity {
    public Button btn_attractie_meting_spec_menu;
    public Button btn_attractie_meting_spec_delen;
    public Button btn_attractie_meting_spec_meetmij;
    public Button btn_attractie_meting_spec_verwijderen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractie_meting_spec);

        btn_attractie_meting_spec_menu = (Button) findViewById(R.id.btn_attractie_meting_spec_menu);
        btn_attractie_meting_spec_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Menu.class);
                startActivity(i);
            }
        });
//        btn_attractie_meting_spec_delen = (Button) findViewById(R.id.btn_attractie_meting_spec_delen);
//        btn_attractie_meting_spec_delen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), DataAttractieLijst.class);
//                startActivity(i);
//            }
//        });
        btn_attractie_meting_spec_meetmij = (Button) findViewById(R.id.btn_attractie_meting_spec_meetmij);
        btn_attractie_meting_spec_meetmij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MeetMij.class);
                startActivity(i);
            }
        });
//        btn_attractie_meting_spec_verwijderen = (Button) findViewById(R.id.btn_attractie_meting_spec_verwijderen);
//        btn_attractie_meting_spec_verwijderen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), DataAttractieLijst.class);
//                startActivity(i);
//            }
//        });
    }
}
