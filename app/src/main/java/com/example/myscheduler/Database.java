package com.example.myscheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public static final String DB_name = "temp.db";
    public static final String Table_1 = "day_Count";
    public static final String col_1_1 = "current";
    public static final String col_1_2 = "last_reset";
    public static final String col_1_3 = "date";
    public static final Integer reset_val = 0;

    public static final String Table_1_create_Query = "create table "+Table_1+" ("+col_1_1+","+col_1_2+","+col_1_3+")";

    public Database(@Nullable Context context) {
        super(context, DB_name, null, 1);
        //SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Table_1_create_Query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Table_1+"");
        onCreate(sqLiteDatabase);

    }

    public boolean InsertMainData(int currentDay, int lastReset, String date_today) {
        
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_1_1,currentDay);
        contentValues.put(col_1_2,lastReset);
        contentValues.put(col_1_3,date_today);

        long result = sqLiteDatabase.insert(Table_1,null,contentValues);
        
        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getdata() {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor alldata = sqLiteDatabase.rawQuery("select * from "+Table_1,null);
        return alldata;
    }

    public void resetData(int lastreset){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_1_1,0);
        contentValues.put(col_1_2,lastreset);
        sqLiteDatabase.insert(Table_1,null,contentValues);
    }

    //extra

    public Cursor getalldata() {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor result = sqLiteDatabase.rawQuery("select * from " + Table_1, null);
        return result;
    }

    public void deltable(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM "+Table_1);
    }
}
