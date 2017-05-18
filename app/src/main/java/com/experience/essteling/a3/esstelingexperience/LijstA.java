package com.experience.essteling.a3.esstelingexperience;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static com.experience.essteling.a3.esstelingexperience.R.id.parent;

public class LijstA extends AppCompatActivity {

    ArrayList<Attractie> attracties = new ArrayList<>();
    ListView lv_attracties;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lijsta);

//        attracties.add(new Attractie("4D-film","dhdhd"));
//        attracties.add(new Attractie("Bobslee","dhdhd"));
//        attracties.add(new Attractie("Doolhof","dhdhd"));
//        attracties.add(new Attractie("Botsauto's","dhdhd"));
//        attracties.add(new Attractie("Rupsje blij","dhdhd"));
//        attracties.add(new Attractie("Spookhuis,Dracula","dhdhd"));
//        attracties.add(new Attractie("Klimmuur","dhdhd"));
//        attracties.add(new Attractie("uitkijktoren","dhdhd"));
//        attracties.add(new Attractie("Zweefmolen","dhdhd"));
//        attracties.add(new Attractie("Megatron","dhdhd"));
//        attracties.add(new Attractie("Mini trein","dhdhd"));
//        attracties.add(new Attractie("Silver rush","dhdhd"));
//        attracties.add(new Attractie("Birdfly","dhdhd"));
//        attracties.add(new Attractie("You Pirate!","dhdhd"));
//        attracties.add(new Attractie("Cobra","dhdhd"));
//        attracties.add(new Attractie("kabouterrit","dhdhd"));
//        attracties.add(new Attractie("baby express","dhdhd"));
//        attracties.add(new Attractie("Wilde leeuw","dhdhd"));
//        attracties.add(new Attractie("Jungle","dhdhd"));
        attracties.add(new Attractie("the force", R.drawable.achtbaan));

        lv_attracties = (ListView) findViewById(R.id.lv_lijstA_lijstProjecten);
        lv_attracties.setOnItemClickListener({
        Attractie attractie = attracties.get(position);
        Intent intent = new Intent(getApplicationContext(),));
        });
