package com.experience.essteling.a3.esstelingexperience.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.experience.essteling.a3.esstelingexperience.Entities.Attractie;
import com.experience.essteling.a3.esstelingexperience.R;

public class DataAttractieLijst extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_attractie_lijst);


        Attractie attractie = (Attractie) getIntent().getSerializableExtra("ATTRACTIE");

        setTitle("Metingen");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tv = (TextView) findViewById(R.id.tv_data_attractie_lijst_attractie);
        tv.setText(String.valueOf(attractie.getNaam()));

        ImageView im1 = (ImageView) findViewById(R.id.im_data_attractie_lijst_attractie);
        im1.setImageResource(attractie.getImage());
    }


}
