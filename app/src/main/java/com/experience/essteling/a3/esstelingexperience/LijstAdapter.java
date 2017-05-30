package com.experience.essteling.a3.esstelingexperience;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lois Gussenhoven on 18-5-2017.
 */

public class LijstAdapter extends RecyclerView.Adapter<LijstAdapter.LijstViewHolder> {

    private List<Attractie> contactList;

    public LijstAdapter(ArrayList<Attractie> contactList) {
        this.contactList = contactList;

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(LijstViewHolder contactViewHolder, int i) {
        Attractie ci = contactList.get(i);
        contactViewHolder.vName.setText(ci.getNaam());
        contactViewHolder.vIcon.setImageResource(ci.getImage());;
    }

    @Override
    public LijstViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.lijst_adapter, viewGroup, false);

        return new LijstViewHolder(itemView);
    }

    public class LijstViewHolder extends RecyclerView.ViewHolder
    {
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
