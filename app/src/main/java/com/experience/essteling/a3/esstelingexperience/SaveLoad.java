package com.experience.essteling.a3.esstelingexperience;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by jaapj on 18-5-2017.
 */

public class SaveLoad {
    public static void save(Context c, String filename, Serializable object) {
        try {
            FileOutputStream fileOut = c.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(object);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static <T extends Serializable> T load(Context c, String filename)
    {
        T e = null;
        try {
            FileInputStream fileIn = c.openFileInput(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (T) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i) {
            i.printStackTrace();
        } catch(ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return e;
    }

    public static void delete(Context c, String filename) {
        c.deleteFile(filename);
    }
}
