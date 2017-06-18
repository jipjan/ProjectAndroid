package com.experience.essteling.a3.esstelingexperience.UI;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.experience.essteling.a3.esstelingexperience.Adapters.DataAdapter;
import com.experience.essteling.a3.esstelingexperience.Entities.Attractie;
import com.experience.essteling.a3.esstelingexperience.Entities.AttractieData;
import com.experience.essteling.a3.esstelingexperience.Entities.MetingenData;
import com.experience.essteling.a3.esstelingexperience.Helpers.Widget;
import com.experience.essteling.a3.esstelingexperience.R;

public class DataAttractieLijst extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_attractie_lijst);
        setTitle("Metingen");

        Attractie attractie = (Attractie) getIntent().getSerializableExtra("ATTRACTIE");
        AttractieData data = MetingenData.ITEMS.getListOrNew(attractie.getId());
        RecyclerView list = Widget.find(this, R.id.lv_metingen_lijstAttracties);

        list.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(llm);
        list.setAdapter(new DataAdapter(data));

        Typeface typeFace= Typeface.createFromAsset(getAssets(),"fonts/Verner.ttf");

        Typeface typeFace2 = Typeface.createFromAsset(getAssets(),"fonts/ErnestineWeb-Bold.ttf");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tv = Widget.find(this, R.id.tv_data_attractie_lijst_attractie);
        tv.setTypeface(typeFace);
        tv.setText(String.valueOf(attractie.getNaam()));


        ImageView im1 = (ImageView) findViewById(R.id.im_data_attractie_lijst_attractie);
        im1.setImageResource(attractie.getImage());
    }





}
