package com.experience.essteling.a3.esstelingexperience.Adapters;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.experience.essteling.a3.esstelingexperience.Entities.Attractie;
import com.experience.essteling.a3.esstelingexperience.Entities.AttractieData;
import com.experience.essteling.a3.esstelingexperience.Entities.SensorData;
import com.experience.essteling.a3.esstelingexperience.Helpers.Widget;
import com.experience.essteling.a3.esstelingexperience.R;
import com.experience.essteling.a3.esstelingexperience.UI.MeetMijLijst;
import com.experience.essteling.a3.esstelingexperience.UI.Menu;

import java.text.DecimalFormat;

/**
 * Created by jaapj on 15-6-2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.LijstViewHolder>  {

    private AttractieData _data;

    public DataAdapter(AttractieData data) {
        _data = data;
    }

    @Override
    public void onBindViewHolder(DataAdapter.LijstViewHolder attractieViewHolder, int i) {
        SensorData ci = _data.get(i);
        DecimalFormat df = new DecimalFormat("#.##");
        attractieViewHolder.maxSnelheid.setText(""+df.format(ci.getHighestSpeed()));
        attractieViewHolder.maxHoogte.setText(""+df.format(ci.getHighestPoint()));
        attractieViewHolder.gemSnelheid.setText(""+df.format(ci.getAverageSpeed()));
        attractieViewHolder.gemHoogte.setText(""+df.format(ci.getAverageHeight()));
    }

    @Override
    public DataAdapter.LijstViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.lijst_adapter_data, viewGroup, false);

        return new DataAdapter.LijstViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return _data.size();
    }

    public class LijstViewHolder extends RecyclerView.ViewHolder {
        protected TextView maxSnelheid;
        protected TextView maxHoogte;
        protected TextView gemSnelheid;
        protected TextView gemHoogte;

        public LijstViewHolder(View v) {
            super(v);
            maxSnelheid = Widget.find(v, R.id.data_snelheid_max);
            maxHoogte = Widget.find(v,R.id.data_hoogte_max);
            gemHoogte = Widget.find(v, R.id.data_hoogte_gemiddeld);
            gemSnelheid = Widget.find(v,R.id.data_snelheid_gemiddelde);
        }
    }
}
