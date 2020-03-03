package com.example.diploma;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {


    private LayoutInflater inflater;
    private int layout;
    private List<Note> notes;
    private Context context;


    public NoteAdapter(@NonNull Context context, int resource, List<Note> notes) {
        super(context, resource, notes);
        this.inflater = LayoutInflater.from(context);
        this.layout = resource;
        this.notes = notes;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);

        TextView title = (TextView) view.findViewById(R.id.item_title);
        TextView changeDate = (TextView) view.findViewById(R.id.item_changeDate);
//        CheckBox isDone = (CheckBox) view.findViewById(R.id.isDone);
        ImageView type = view.findViewById(R.id.item_type);


        Note note = notes.get(position);
        title.setText(note.getTitle());
        changeDate.setText(note.getChangeDate());
        if (note.type.equals("text"))
            type.setBackgroundResource(R.mipmap.ic_textnote);
        if (note.type.equals("photo"))
            type.setBackgroundResource(R.mipmap.ic_photonote);
        if (note.type.equals("list"))
            type.setBackgroundResource(R.mipmap.ic_listnote);

//        note.isChecked = isDone.isChecked();


        return view;
    }
}
