package com.experience.essteling.a3.esstelingexperience;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lois Gussenhoven on 18-5-2017.
 */

public class Lijstadapter extends ArrayAdapter<Attractie>{

    public Lijstadapter(Context context, ArrayList<Attractie> attracties) {
        super(context,0, attracties);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Attractie attractie = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_lijst_attractie,parent,false);
        }

        TextView naamAttractie = (TextView) convertView.findViewById(R.id.tv_adapter_naam_a);
        naamAttractie.setText(String.valueOf((attractie.getNaam())));

        ImageView fotoAttractie = (ImageView) convertView.findViewById(R.id.iv_adapter_foto_a);
        fotoAttractie.setImageResource(R.drawable.achtbaan);
        return convertView;

    }
}
