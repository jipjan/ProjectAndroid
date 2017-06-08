package com.experience.essteling.a3.esstelingexperience.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.experience.essteling.a3.esstelingexperience.Adapters.LijstAdapterMeetMij;
import com.experience.essteling.a3.esstelingexperience.Entities.Attractie;
import com.experience.essteling.a3.esstelingexperience.R;
import com.experience.essteling.a3.esstelingexperience.UI.MeetMij;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MeetMijLijst extends AppCompatActivity {
    ArrayList<Attractie> attracties = new ArrayList<>();
    RecyclerView lv_attracties;
    public static View.OnClickListener Click;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_mij_lijst);

        Click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = lv_attracties.getChildLayoutPosition(v);
                Attractie attractie = attracties.get(position);
                Intent intent = new Intent(getApplicationContext(), MeetMij.class);

                intent.putExtra("ATTRACTIE", attractie);

                startActivity(intent);
            }
        };

        setTitle("Meet Mij");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_attracties = (RecyclerView) findViewById(R.id.lv_meet_mij_lijst_lijstAttracties);
        lv_attracties.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        lv_attracties.setLayoutManager(llm);


        attracties.add(new Attractie("4D-film",R.drawable.movie, "Show",1));
        attracties.add(new Attractie("Bobslee",R.drawable.bobslee, "Attractie",2));
        attracties.add(new Attractie("Doolhof",R.drawable.doolhof, "Kinder attractie",3));
        attracties.add(new Attractie("Botsauto's",R.drawable.botsautos,"Kinder attractie",4));
        attracties.add(new Attractie("Rupsje blij",R.drawable.rupsje,"Kinder attractie",5));
        attracties.add(new Attractie("Spookhuis",R.drawable.spookhuis,"Kinder attractie",6));
        attracties.add(new Attractie("Klimmuur",R.drawable.klimmuur,"Kinder attractie",7));
        attracties.add(new Attractie("Uitkijktoren",R.drawable.uitkijktoren, "Attractie",8));
        attracties.add(new Attractie("Zweefmolen",R.drawable.zweefmolen, "Attractie",9));
        attracties.add(new Attractie("Megatron",R.drawable.achtbaana,"Attractie",10));
        attracties.add(new Attractie("Mini trein",R.drawable.mini_trein,"Kinder attractie",11));
        attracties.add(new Attractie("Silver rush",R.drawable.silver,"Attractie",12));
        attracties.add(new Attractie("Reuzenrad",R.drawable.reuzenras, "Attractie",13));
        attracties.add(new Attractie("You Pirate!",R.drawable.piraat, "Attractie",14));
        attracties.add(new Attractie("Cobra",R.drawable.cobr, "Attractie",15));
        attracties.add(new Attractie("Kabouterrit",R.drawable.kabouter,"Kinder attractie",16));
        attracties.add(new Attractie("Treintje",R.drawable.treintjes, "Kinder attractie", 17));
        attracties.add(new Attractie("Wilde leeuw",R.drawable.leeuw, "Attractie", 18));
        attracties.add(new Attractie("Jungle",R.drawable.jungle,"Attractie",19));
        attracties.add(new Attractie("the Force", R.drawable.achtbaan,"Attractie",20));



        lv_attracties.setAdapter(new LijstAdapterMeetMij(attracties));

        if (attracties.size() > 0) {
            Collections.sort(attracties, new Comparator<Attractie>() {
                @Override
                public int compare(final Attractie object1, final Attractie object2) {
                    return object1.getNaam().compareTo(object2.getNaam());
                }
            });
        }
    }
}
