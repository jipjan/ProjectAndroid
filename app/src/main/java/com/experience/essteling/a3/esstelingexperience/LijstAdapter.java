package com.experience.essteling.a3.esstelingexperience;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
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

public class LijstAdapter extends ArrayAdapter<Attractie>{

    public LijstAdapter(Context context, ArrayList<Attractie> attracties) {
        super(context,0, attracties);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Attractie attractie = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lijst_adapter,parent,false);
        }

        TextView naamAttractie = (TextView) convertView.findViewById(R.id.tv_adapter_naam_a);
        naamAttractie.setText(attractie.getNaam());

        ImageView fotoAttractie = (ImageView) convertView.findViewById(R.id.iv_adapter_foto_a);
        fotoAttractie.setImageResource(attractie.getImage());
        return convertView;

    }
}
