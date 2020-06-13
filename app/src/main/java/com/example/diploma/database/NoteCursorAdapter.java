package com.example.diploma.database;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.diploma.R;

public class NoteCursorAdapter extends CursorAdapter {

    public NoteCursorAdapter(Context context, Cursor cursor, int flag){

        super(context, cursor, 0);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor){


        TextView title = (TextView) view.findViewById(R.id.item_title);
        TextView changeDate = (TextView) view.findViewById(R.id.item_changeDate);
        ImageView type = view.findViewById(R.id.item_type);
        ImageView password = view.findViewById(R.id.item_password);

int id=cursor.getInt(0);
//        String returnID = cursor.getString(cursor.getColumnIndex("ID"));
        String returnTitle = cursor.getString(cursor.getColumnIndex("title"));
        String returnType = cursor.getString(cursor.getColumnIndex("type"));
        String returnChangeDate = cursor.getString(cursor.getColumnIndex("changeDate"));
        String returnPassword = cursor.getString(cursor.getColumnIndex("password"));



        //Note note = notes.get(position);
        //title.setText(returnTitle+", "+id);
        title.setText(returnTitle);

        changeDate.setTypeface(null, Typeface.ITALIC);
        changeDate.setText(returnChangeDate);
        if (returnType.equals("text"))
            type.setBackgroundResource(R.mipmap.ic_notetext);
        if (returnType.equals("photo"))
            type.setBackgroundResource(R.mipmap.ic_notephoto);
        if (returnType.equals("list"))
            type.setBackgroundResource(R.mipmap.ic_notelist);
        if (returnType.equals("sound"))
            type.setBackgroundResource(R.mipmap.ic_notesound);


        if(!returnPassword.equals(""))
            password.setBackgroundResource(R.mipmap.ic_password);
        else
            password.setVisibility(View.INVISIBLE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent){
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent,false);
    }
}