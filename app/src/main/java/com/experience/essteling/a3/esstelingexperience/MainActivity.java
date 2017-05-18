package com.experience.essteling.a3.esstelingexperience;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.File;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LijstMetingen lijst = new LijstMetingen();
        lijst.add(new Meting(new Date(), 50));

        SaveLoad.save(this, "metingen", lijst);

        for (File f : this.getFilesDir().listFiles())
            System.out.println(f.getName());

        LijstMetingen lijst2 = SaveLoad.load(this, "metingen");

        TextView text = (TextView) findViewById(R.id.kaas);
        text.setText(""+lijst2.get(0).getSpeed());
    }
}
