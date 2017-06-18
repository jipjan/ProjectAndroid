package com.experience.essteling.a3.esstelingexperience.Adapters;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.experience.essteling.a3.esstelingexperience.Entities.Attractie;
import com.experience.essteling.a3.esstelingexperience.Entities.MetingenData;
import com.experience.essteling.a3.esstelingexperience.R;
import com.experience.essteling.a3.esstelingexperience.UI.Menu;
import com.experience.essteling.a3.esstelingexperience.UI.MetingenLijst;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lois Gussenhoven on 18-5-2017.
 */

public class LijstAdapterMetingen extends RecyclerView.Adapter<LijstAdapterMetingen.LijstViewHolder> {

    private List<Attractie> attractieList;

    TextView vMassage;

    public LijstAdapterMetingen(ArrayList<Attractie> attractieList) {
        this.attractieList = attractieList;
    }

    @Override
    public int getItemCount() {
        return attractieList.size();
    }

    @Override
    public void onBindViewHolder(LijstViewHolder attractieViewHolder, int i) {
        Attractie ci = attractieList.get(i);

        Typeface typeFace1 = Typeface.createFromAsset(Menu.Assests,"fonts/ErnestineWeb-Bold.ttf");
        Typeface typeFace2 = Typeface.createFromAsset(Menu.Assests,"fonts/Verner.ttf");

        attractieViewHolder.vName.setTypeface(typeFace2);
        attractieViewHolder.vSize.setTypeface(typeFace1);

        attractieViewHolder.vName.setText(ci.getNaam());
        attractieViewHolder.vIcon.setImageResource(ci.getImage());
        int aantal = MetingenData.ITEMS.getListOrNew(ci.getId()).size();
        if(aantal == 0)
            attractieViewHolder.vSize.setText("Geen metingen");
        else
            attractieViewHolder.vSize.setText(aantal + " metingen");
    }

    @Override
    public LijstViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.lijst_adapter_metingen, viewGroup, false);

        return new LijstViewHolder(itemView);
    }

    public class LijstViewHolder extends RecyclerView.ViewHolder
    {
        protected TextView vName;
        protected ImageView vIcon;
        protected TextView vSize;

        public LijstViewHolder(View v) {
            super(v);
            v.setOnClickListener(MetingenLijst.Click);
            vName = (TextView) v.findViewById(R.id.naam);
            vIcon = (ImageView) v.findViewById(R.id.icon);
            vSize = (TextView) v.findViewById(R.id.aantal);
        }
    }
}
