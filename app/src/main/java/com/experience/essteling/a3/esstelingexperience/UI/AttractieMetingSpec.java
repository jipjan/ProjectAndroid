package com.experience.essteling.a3.esstelingexperience.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.experience.essteling.a3.esstelingexperience.Entities.Attractie;
import com.experience.essteling.a3.esstelingexperience.R;

public class AttractieMetingSpec extends AppCompatActivity {
    public Button btn_attractie_meting_spec_delen;
    public Button btn_attractie_meting_spec_meetmij;
    public Button btn_attractie_meting_spec_verwijderen;
    public Intent shareIntent;
    public String shareBody = "" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractie_meting_spec);

        final Attractie attractie = (Attractie) getIntent().getSerializableExtra("ATTRACTIE1");

        TextView tv = (TextView) findViewById(R.id.tv_attractie_meting_spec_naamAttractie);
        tv.setText(String.valueOf(attractie.getNaam()));

        ImageView im1 = (ImageView) findViewById(R.id.im_meeting_attractie);
        im1.setImageResource(Integer.parseInt(String.valueOf(attractie.getImage())));

        setTitle("Metingen");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_attractie_meting_spec_delen = (Button) findViewById(R.id.btn_attractie_meting_spec_delen);
        btn_attractie_meting_spec_delen.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"My app");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });

        btn_attractie_meting_spec_meetmij = (Button) findViewById(R.id.btn_attractie_meting_spec_meetmij);
        btn_attractie_meting_spec_meetmij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MeetMij.class);
                i.putExtra("ATTRACTIE", attractie);
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
