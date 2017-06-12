package com.experience.essteling.a3.esstelingexperience.Helpers;

import android.app.Activity;

/**
 * Created by Jaap-Jan on 12-6-2017.
 */

public class Widget {
    public static <T> T find(Activity act, int id) {
        return (T) act.findViewById(id);
    }
}
