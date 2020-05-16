package com.example.diploma.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.diploma.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DatabaseVersion=1;
    private static final String DatabaseName="db_diploma";
    private static final String CreateTable_Notes="Create Table IF NOT EXISTS Notes(_id Integer Primary Key AutoIncrement,title Text, path Text,type Text, changeDate Text, password Text, isPin Integer DEFAULT 0)";

    private static  DatabaseHandler mInstance = null;
    public static DatabaseHandler getmInstance(Context context){
        if(mInstance==null){
            mInstance = new DatabaseHandler(context.getApplicationContext());
        }
        return mInstance;
    }

    public DatabaseHandler(Context context){
        super(context, DatabaseName, null, DatabaseVersion);
    }
    @Override
    public  void onCreate(SQLiteDatabase db){
        db.execSQL(CreateTable_Notes);
      //  db.execSQL(CreateTable_PinNotes);
    }
    @Override
    public  void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onCreate(db);
    }


    public void deleteById(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("notes", "_id = " + id, null);
    }

    public void updateNoteById(long id, String title, String path, String type, String changeDate, String password ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("path",path);
        values.put("type",type  );
        values.put("changeDate",changeDate);
        values.put("password",password);

        db.update("notes", values, "_id="+id, null);
    }

    public  void updatePinStatus(long id, int pinStatus)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("isPin",pinStatus);
        db.update("notes", values, "_id="+id, null);
    }


    private String getDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault() );
        Date date= new Date();
        return  dateFormat.format(date);
    }

    public long insertIntoNote(String title, String path, String type, String changeDate, String password){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("path",path);
        values.put("type",type  );
        values.put("changeDate",changeDate);
        values.put("password",password);

        long id = db.insert("notes",null,values);

        return id;
    }


    public int getNotelistCount()
    {
        String countQuery = "SELECT  * FROM notes" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public Cursor getNoteList(int pinStatus){
        SQLiteDatabase db   = this.getWritableDatabase();
        String query = "Select * from Notes where isPin = "+pinStatus;
        Cursor cursor = db.rawQuery(query, null);
        return  cursor;

    }





    public ArrayList<Note> getNoteArrayList(int pinStatus)
    {
        SQLiteDatabase db   = this.getWritableDatabase();
        String query = "Select * from Notes where isPin = "+pinStatus;
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Note> notes=new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {

            int idColumn = cursor.getColumnIndex("_id");
            int titleColumn = cursor.getColumnIndex("title");
            int pathColumn = cursor.getColumnIndex("path");
            int typeColumn = cursor.getColumnIndex("type");
            int changeDateColumn = cursor.getColumnIndex("changeDate");
            int passwordColumn = cursor.getColumnIndex("password");

            //add row to list
            do {
                int thisId = cursor.getInt(idColumn);
                String thisTitle = cursor.getString(titleColumn);
                String thisPath = cursor.getString(pathColumn);
                String thisType = cursor.getString(typeColumn);
                String thisChangeDate = cursor.getString(changeDateColumn);
                String thisPassword = cursor.getString(passwordColumn);
//                if (thisAlarm + thisNoti + thisRing==0)
                notes.add(new Note(thisId, thisTitle, thisPath, thisType, thisChangeDate, thisPassword));
            }
            while (cursor.moveToNext());
            cursor.close();
        }

        return notes;
    }

}