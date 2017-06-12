package com.experience.essteling.a3.esstelingexperience.Entities;

import android.content.Context;

import com.experience.essteling.a3.esstelingexperience.Helpers.SaveLoad;
import com.experience.essteling.a3.esstelingexperience.UI.Menu;

import java.io.Serializable;
import java.util.HashMap;

import static com.experience.essteling.a3.esstelingexperience.Entities.Constants.FILENAME;

/**
 * Created by Bobsk on 8-6-2017.
 */

public class MetingenData extends HashMap<Integer, AttractieData> implements Serializable {
    public static MetingenData ITEMS = new MetingenData();

    private MetingenData() {}

    public AttractieData getListOrNew(int id) {
        if (containsKey(id)) {
            return get(id);
        } else {
            AttractieData data = new AttractieData();
            put(id, data);
            return data;
        }
    }

    public void save(Context c) {
        SaveLoad.save(c, FILENAME, this);
    }

    public void load(Context c) {
        ITEMS = SaveLoad.load(c, FILENAME);
    }
}
