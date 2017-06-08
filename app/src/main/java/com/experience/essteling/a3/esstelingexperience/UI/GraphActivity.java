package com.experience.essteling.a3.esstelingexperience.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.experience.essteling.a3.esstelingexperience.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by jaapj on 18-5-2017.
 */

public class GraphActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(5, 10),
                new DataPoint(10, 25),
                new DataPoint(15, 25),
                new DataPoint(20, 50),
                new DataPoint(25, 100),
                new DataPoint(30, 120),
                new DataPoint(35, 60),
                new DataPoint(40, 50),
                new DataPoint(45, 20),
                new DataPoint(50, 10),
                new DataPoint(55, 5),
                new DataPoint(60, 0)
        });
        GridLabelRenderer r = graph.getGridLabelRenderer();
        r.setHorizontalAxisTitle("Tijd in seconden");
        r.setVerticalAxisTitle("Snelheid in km/u");
        graph.addSeries(series);
    }
}
