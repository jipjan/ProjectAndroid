package com.experience.essteling.a3.esstelingexperience.Entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Bobsk on 8-6-2017.
 */

public class Data implements Serializable {
    public final long Time;
    public final double Speed;
    public final double Height;

    public Data(double speed, double height, long ms) {
        Time = ms;
        Speed = speed < 0 ? 0 : speed;
        Height = height;
    }
}
