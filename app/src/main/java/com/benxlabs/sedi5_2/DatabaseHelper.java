package com.benxlabs.sedi5_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

   public static final String TAG = "DatabaseHelper";

    public static final String TABLE_NAME = "tasks_table";
    public static final String COL1 = "ID";
    public static final String COL2= "title";
    public static final String COL3= "body";
    public static final String COL4= "date";
    public static final String COL5= "time";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+ TABLE_NAME + " ( "
                + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL2 + " TEXT, "
                + COL3 + " TEXT, "
                + COL4 + " TEXT, "
                + COL5 + " TEXT "+")";

        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String title, String body, String date, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, title);
        contentValues.put(COL3, body);
        contentValues.put(COL4, date);
        contentValues.put(COL5, time);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){return false;}else{return true;}
    }


    public Cursor getData(){
        SQLiteDatabase db  = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }



    public Cursor getItemID(String title){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "+ COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + title +"'";
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public Cursor getItemData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = " + id;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public void updateItem(int id, String title, String body, String date, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        String query  = "UPDATE "+ TABLE_NAME + " SET "+ COL2 + " = '" + title + "', "+ COL3 + " = '" + body + "', " + COL4 + " = '" + date + "', "+ COL5 + " = '" + time + "' " + " WHERE " + COL1 + " = " + id;
        db.execSQL(query);
    }

    public void deleteItem(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+ TABLE_NAME + " WHERE " + COL1 + " = "+id;
        db.execSQL(query);
    }

}
