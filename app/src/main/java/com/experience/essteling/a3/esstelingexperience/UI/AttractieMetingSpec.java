package com.experience.essteling.a3.esstelingexperience.UI;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.experience.essteling.a3.esstelingexperience.Entities.Attractie;
import com.experience.essteling.a3.esstelingexperience.Entities.Data;
import com.experience.essteling.a3.esstelingexperience.Entities.MetingenData;
import com.experience.essteling.a3.esstelingexperience.Entities.SensorData;
import com.experience.essteling.a3.esstelingexperience.Helpers.Widget;
import com.experience.essteling.a3.esstelingexperience.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;
import java.util.ArrayList;

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

        DecimalFormat df = new DecimalFormat("#.##");

        final SensorData data = new SensorData((ArrayList<Data>) getIntent().getSerializableExtra("DATA"));
        final Attractie attractie = (Attractie) getIntent().getSerializableExtra("ATTRACTIE1");

        Typeface typeFace= Typeface.createFromAsset(getAssets(),"fonts/Verner.ttf");

        Typeface typeFace2 = Typeface.createFromAsset(getAssets(),"fonts/ErnestineWeb-Bold.ttf");

        TextView tv = (TextView) findViewById(R.id.tv_attractie_meting_spec_naamAttractie);
        tv.setText(String.valueOf(attractie.getNaam()));
        tv.setTypeface(typeFace);

        TextView tvgemh = (TextView) findViewById(R.id.tv_attractie_meting_spec_h_gem_i);
        tvgemh.setText(String.valueOf(df.format(data.getAverageHeight())) + " meter");
        tvgemh.setTypeface(typeFace2);

        TextView tvgems = (TextView) findViewById(R.id.tv_attractie_meting_spec_s_gem_i);
        tvgems.setText(String.valueOf(df.format(data.getAverageSpeed() * 3.6)) + " km/u");
        tvgems.setTypeface(typeFace2);

        TextView tvhs = (TextView) findViewById(R.id.tv_attractie_meting_spec_s_max_i);
        tvhs.setText(String.valueOf(df.format(data.getHighestSpeed() * 3.6)) + " km/u");
        tvhs.setTypeface(typeFace2);

        TextView tvhh = (TextView) findViewById(R.id.tv_attractie_meting_spec_h_max_i);
        tvhh.setText(String.valueOf(df.format(data.getHighestPoint())) + " meter");
        tvhh.setTypeface(typeFace2);

        ImageView im1 = (ImageView) findViewById(R.id.im_meeting_attractie);
        im1.setImageResource(Integer.parseInt(String.valueOf(attractie.getImage())));

        setTitle("Metingen");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_attractie_meting_spec_delen = (Button) findViewById(R.id.btn_attractie_meting_spec_delen);
        btn_attractie_meting_spec_delen.setTypeface(typeFace2);
        btn_attractie_meting_spec_delen.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"My app");
                TextView hoogte = (TextView) findViewById(R.id.tv_attractie_meting_spec_h_max_i);
                TextView snel = (TextView) findViewById(R.id.tv_attractie_meting_spec_s_max_i);
                shareBody = "Ik ging vandaag " + snel.getText() + " en ik kwam " + hoogte.getText() + " hoog!!!";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });

        btn_attractie_meting_spec_meetmij = (Button) findViewById(R.id.btn_attractie_meting_spec_meetmij);
        btn_attractie_meting_spec_meetmij.setTypeface(typeFace2);
        btn_attractie_meting_spec_meetmij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MeetMij.class);
                i.putExtra("ATTRACTIE", attractie);
                startActivity(i);
                finish();
            }
        });

        btn_attractie_meting_spec_verwijderen = Widget.find(this, R.id.btn_attractie_meting_spec_verwijderen);
        btn_attractie_meting_spec_verwijderen.setTypeface(typeFace2);
        btn_attractie_meting_spec_verwijderen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MetingenData.ITEMS.getListOrNew(attractie.getId()).remove(data);
                MetingenData.ITEMS.save(getApplicationContext());
                Intent i = new Intent(getApplicationContext(), MeetMij.class);
                i.putExtra("ATTRACTIE", attractie);
                startActivity(i);
                finish();
            }
        });

        fillGraphs(data);
    }

    private void fillGraphs(ArrayList<Data> list) {
        GraphView graphSpeed = Widget.find(this, R.id.im_meeting_gr_s);
        GraphView graphHeight = Widget.find(this, R.id.im_meeting_gr_h);

        DataPoint[] dataSpeed = new DataPoint[list.size()];
        DataPoint[] dataHeight = new DataPoint[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Data d = list.get(i);
            dataSpeed[i] = new DataPoint(d.Time, d.Speed);
            dataHeight[i] = new DataPoint(d.Time, d.Height);
        }

        LineGraphSeries<DataPoint> sSeries = new LineGraphSeries<>(dataSpeed);
        GridLabelRenderer sR = graphSpeed.getGridLabelRenderer();
        sR.setHorizontalAxisTitle("Tijd");
        sR.setVerticalAxisTitle("Snelheid in km/u");
        graphSpeed.addSeries(sSeries);
        graphSpeed.getViewport().setScrollable(true);

        LineGraphSeries<DataPoint> hSeries = new LineGraphSeries<>(dataHeight);
        GridLabelRenderer hR = graphHeight.getGridLabelRenderer();
        hR.setHorizontalAxisTitle("Tijd");
        hR.setVerticalAxisTitle("Snelheid in km/u");
        graphHeight.addSeries(hSeries);
        graphHeight.getViewport().setScrollable(true);

    }
}
