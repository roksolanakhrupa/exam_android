//package com.example.diploma.database;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//
//import com.example.diploma.Note;
////
//import java.util.List;
//
//public class NoteAdapter extends ArrayAdapter<Note> {
//
//
//    private LayoutInflater inflater;
//    private int layout;
//    private List<Note> notes;
//    private Context context;
//
//
//    public NoteAdapter(@NonNull Context context, int resource, List<Note> notes) {
//        super(context, resource, notes);
//        this.inflater = LayoutInflater.from(context);
//        this.layout = resource;
//        this.notes = notes;
//        this.context = context;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        View view = inflater.inflate(this.layout, parent, false);
//
//        TextView title = (TextView) view.findViewById(R.id.item_title);
//        TextView changeDate = (TextView) view.findViewById(R.id.item_changeDate);
//        ImageView type = view.findViewById(R.id.item_type);
//
//
//        Note note = notes.get(position);
//        title.setText(note.getTitle()+", "+note.getId());
//        changeDate.setText(note.getChangeDate());
//        if (note.type.equals("text"))
//            type.setBackgroundResource(R.mipmap.ic_textnote);
//        if (note.type.equals("photo"))
//            type.setBackgroundResource(R.mipmap.ic_photonote);
//        if (note.type.equals("list"))
//            type.setBackgroundResource(R.mipmap.ic_listnote);
//        if (note.type.equals("sound"))
//            type.setBackgroundResource(R.mipmap.ic_soundnote);
//
//
//        return view;
//    }
//}
