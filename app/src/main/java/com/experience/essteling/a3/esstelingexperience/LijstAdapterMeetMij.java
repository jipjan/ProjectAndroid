package com.experience.essteling.a3.esstelingexperience;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Lois Gussenhoven on 31-5-2017.
 */

public class LijstAdapterMeetMij extends RecyclerView.Adapter<LijstAdapterMeetMij.LijstViewHolder> {
    private List<Attractie> attractieList;

    public LijstAdapterMeetMij(ArrayList<Attractie> attractieList) {
        this.attractieList = attractieList;
    }

    @Override
    public int getItemCount() {
        return attractieList.size();
    }

    @Override
    public void onBindViewHolder(LijstViewHolder attractieViewHolder, int i) {
        Attractie ci = attractieList.get(i);
        attractieViewHolder.vName.setText(ci.getNaam());
        attractieViewHolder.vIcon.setImageResource(ci.getImage());
    }

    @Override
    public LijstViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.lijst_adapter_metingen, viewGroup, false);

        return new LijstViewHolder(itemView);
    }

    public class LijstViewHolder extends RecyclerView.ViewHolder {
        protected TextView vName;
        protected ImageView vIcon;

        public LijstViewHolder(View v) {
            super(v);
            v.setOnClickListener(MeetMijLijst.Click);
            vName = (TextView) v.findViewById(R.id.naam);
            vIcon = (ImageView) v.findViewById(R.id.icon);


        }

    }
}


