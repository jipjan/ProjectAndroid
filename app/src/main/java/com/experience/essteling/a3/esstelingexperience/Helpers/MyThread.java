package com.experience.essteling.a3.esstelingexperience.Helpers;

/**
 * Created by Lois Gussenhoven on 8-6-2017.
 */

public class MyThread {
    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
