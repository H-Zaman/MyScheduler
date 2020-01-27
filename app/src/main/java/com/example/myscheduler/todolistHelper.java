package com.example.myscheduler;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class todolistHelper {


    public static final String FILE_NAME = "todolist.dat";

    public static void WriteData(ArrayList<String> item, Context context){

        try {
            FileOutputStream fileOutputStream = context.openFileOutput(FILE_NAME,Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(item);
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static  ArrayList<String> ReadData(Context context){
        ArrayList<String> itemlist = null;
        try {
            FileInputStream fileInputStream = context.openFileInput(FILE_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            itemlist = (ArrayList<String>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {

            itemlist = new ArrayList<>();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return itemlist;
    }
}
