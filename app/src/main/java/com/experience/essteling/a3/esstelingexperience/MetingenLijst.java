package com.experience.essteling.a3.esstelingexperience;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MetingenLijst extends AppCompatActivity {
    RecyclerView lv_attracties;
    ArrayList<Attractie> attracties = new ArrayList<>();
    public static View.OnClickListener Click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metingen_lijst);

        Click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = lv_attracties.getChildLayoutPosition(v);
                Attractie attractie = attracties.get(position);
                Intent intent = new Intent(getApplicationContext(), DataAttractieLijst.class);

                intent.putExtra("ATTRACTIE", attractie);

                startActivity(intent);
            }
        };

        setTitle("Metingen");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_attracties = (RecyclerView) findViewById(R.id.lv_metingen_lijstAttracties);
        lv_attracties.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        lv_attracties.setLayoutManager(llm);




        attracties.add(new Attractie("4D-film",R.drawable.movie));
        attracties.add(new Attractie("Bobslee",R.drawable.bobslee));
        attracties.add(new Attractie("Doolhof",R.drawable.doolhof));
        attracties.add(new Attractie("Botsauto's",R.drawable.bobslee));
        attracties.add(new Attractie("Rupsje blij",R.drawable.rupsje));
        attracties.add(new Attractie("Spookhuis,Dracula",R.drawable.spookhuis));
        attracties.add(new Attractie("Klimmuur",R.drawable.klimmuur));
        attracties.add(new Attractie("uitkijktoren",R.drawable.uitkijktoren));
        attracties.add(new Attractie("Zweefmolen",R.drawable.zweefmolen));
        attracties.add(new Attractie("Megatron",R.drawable.achtbaana));
        attracties.add(new Attractie("Mini trein",R.drawable.mini_trein));
        attracties.add(new Attractie("Silver rush",R.drawable.silver));
        attracties.add(new Attractie("Reuzenrad",R.drawable.reuzenras));
        attracties.add(new Attractie("You Pirate!",R.drawable.piraat));
        attracties.add(new Attractie("Cobra",R.drawable.cobr));
        attracties.add(new Attractie("Kabouterrit",R.drawable.kabouter));
        attracties.add(new Attractie("Treintje",R.drawable.treintjes));
        attracties.add(new Attractie("Wilde leeuw",R.drawable.leeuw));
        attracties.add(new Attractie("Jungle",R.drawable.jungle));
        attracties.add(new Attractie("the force", R.drawable.achtbaan));

        lv_attracties.setAdapter(new LijstAdapterMetingen(attracties));

    }
}
